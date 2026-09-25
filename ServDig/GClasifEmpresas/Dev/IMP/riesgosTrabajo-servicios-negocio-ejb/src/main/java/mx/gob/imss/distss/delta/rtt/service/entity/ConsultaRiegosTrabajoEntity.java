package mx.gob.imss.distss.delta.rtt.service.entity;

import java.util.Calendar;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.ReporteRiesgoTrabajo;

import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgosPatron;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitLlavePatron;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.RttRegistro;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.distss.delta.rtt.service.util.UtilRTT;
import weblogic.jms.utils.Simple;

@Stateless(name = "consultaRiegosTrabajoEntity", mappedName = "consultaRiegosTrabajoEntity")
public class ConsultaRiegosTrabajoEntity extends AbstractServiceEntity implements
        ConsultaRiesgosTrabajoLocal {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConsultaRiegosTrabajoEntity.class);

    /**
     * Metodo para buscar los riesgos de trabajo por el registro patronal por
     * periodo
     *
     * @param regPatron
     * @param fechaInicio
     * @param fechaFin
     * @return
     * @throws RiesgosTrabajoException
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RiesgoTrabajo> findRiesgoTrabajoRegPatronPeriodo(String regPatron, Date fechaInicio, Date fechaFin, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {

        LOGGER.debug("Consultando riesgos de trabajo");

        List<RttRegistro> result = null;
        List<RiesgoTrabajo> riesgosTrabajo = new ArrayList<RiesgoTrabajo>();

        //Validamos que el registro patronal sea de 10 caracteres
        if (regPatron.length() > 10) {
            regPatron = regPatron.substring(0, regPatron.length() - 1);
        }

        //Realizamos la consulta

        Query query = this.getSession()
            .createSQLQuery(
                "select * " +
                "from RTT_REGISTRO_RT " +
                "where RTT_REG_PATRON = :rp " +
                "and RTT_FEC_FIN_ACCIDENTE between :fi and :ff"
            )
            .addEntity(RttRegistro.class);

        query.setString("rp", regPatron);
        //estandarizacion para 00 milisegundos
        Calendar cal = Calendar.getInstance();

        cal.setTime(fechaInicio);
        cal.set(Calendar.MILLISECOND, 0);
        fechaInicio = cal.getTime();

        cal.setTime(fechaFin);
        cal.set(Calendar.MILLISECOND, 0);
        fechaFin = cal.getTime();
       

        query.setTimestamp(
            "fi",
            new java.sql.Timestamp(fechaInicio.getTime())
        );

        query.setTimestamp(
            "ff",
            new java.sql.Timestamp(fechaFin.getTime())
        );

        result = query.list();

        //Si es de internet filtramos por tipo de riesgo 1 y 3
//        if (origen.equals(OrigenSolicitudEnum.INTERNET)) {
//            //lenamos la lista con los tipos de riesgos
//            List<Integer> tiposRiesgos = new ArrayList<Integer>();
//            tiposRiesgos.add(1);
//            tiposRiesgos.add(3);
//            criteria.add(Restrictions.in("rttNumTipoRiesgo", tiposRiesgos));
//            
//        }
//        criteria.addOrder(Order.asc("rttFecFinAccidente"));

        
        
        LOGGER.debug("Coincidencias:{}", result.size());
        
        //Convertimos los datos de la consulta a objetos RiesgosTrabajo
        if (result != null && !result.isEmpty()) {
            riesgosTrabajo = UtilRTT.getDataRiesgosTrabajo(result);

        }
        /*else {
            throw new RiesgosTrabajoException("No existen Riesgos de Trabajo");
        }*/

        LOGGER.debug("Se finaliza la consulta");
        return riesgosTrabajo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RiesgosPatron findRiesgoTrabajoRegPatronXNSS(String nss, Date fechaInicio, Date fechaFin, OrigenSolicitudEnum origen, Long idDelegacion, Long idSubdelegacion) throws RiesgosTrabajoException {

        LOGGER.debug("Consultando riesgos de trabajo");

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        RiesgosPatron riesgosPatron = new RiesgosPatron();
        List<RiesgoTrabajo> riesgosTrabajo = new ArrayList<RiesgoTrabajo>();
        //Realizamos la consulta
        StringBuilder query = new StringBuilder();
        query.append("SELECT rtt.RTT_NUM_NSS, ")
                .append("rtt.RTT_REF_CURP, ")
                .append("rtt.RTT_NOMBRE || ' ' || rtt.RTT_REF_APELLIDO_PATERNO || ' ' || rtt.RTT_REF_APELLIDO_MATERNO, ")
                .append("rtt.RTT_NUM_CONSECUENCIA, ")
                .append("TO_CHAR(rtt.RTT_FEC_INICIO_ACCIDENTE, 'dd/MM/yyyy'), ")
                .append("rtt.RTT_NUM_TIPO_RIESGO, ")
                .append("rtt.RTT_DIAS_SUBSIDIADOS, ")
                .append("rtt.RTT_POR_INCAPACIDAD, ")
                .append("rtt.RTT_NUM_CONSECUENCIA, ")
                .append("TO_CHAR(rtt.RTT_FEC_FIN_ACCIDENTE, 'dd/MM/yyyy'), ")
                .append("dpg.REG_PATRON, ")
                .append("dm.NUM_MODALIDAD, ")
                .append("CAST(dpg.DIG_VER AS VARCHAR2(1)), ")
                .append("dpso.CVE_ID_PATRON_SUJETO_OBLIGADO, ")
                .append("ds.CVE_ID_SUBDELEGACION ")
                .append("FROM MGPBDTU9X.RTT_REGISTRO_RT rtt ")
                .append("INNER JOIN MGPBDTU9X.DIT_LLAVE_PATRON llave ON llave.REF_BUSCA = rtt.RTT_REG_PATRON ")
                .append("INNER JOIN MGPBDTU9X.DIT_PATRON_SUJETO_OBLIGADO dpso on llave.CVE_ID_PATRON_SUJETO_OBLIGADO=dpso.CVE_ID_PATRON_SUJETO_OBLIGADO ")
                .append("INNER JOIN MGPBDTU9X.DIT_PATRON_GENERAL dpg ON dpg.CVE_ID_PATRON_SUJETO_OBLIGADO=dpso.CVE_ID_PATRON_SUJETO_OBLIGADO ")
                .append("INNER JOIN MGPBDTU9X.DIC_MODALIDAD dm ON dm.CVE_ID_MODALIDAD = dpso.CVE_ID_MODALIDAD ")
                .append("INNER JOIN MGPBDTU9X.DIT_DELSUB_PAT_SUJ_OBLIG ddpso on dpso.CVE_ID_PATRON_SUJETO_OBLIGADO=ddpso.CVE_ID_PATRON_SUJETO_OBLIGADO ")
                .append("INNER JOIN MGPBDTU9X.DIC_SUBDELEGACION ds on ddpso.CVE_ID_SUBDELEGACION=ds.CVE_ID_SUBDELEGACION ")
                .append("INNER JOIN MGPBDTU9X.DIC_DELEGACION dd on ds.CVE_ID_DELEGACION=dd.CVE_ID_DELEGACION ")
                .append("WHERE 1=1 ");
        if (idDelegacion != null && idDelegacion != 0) {
            query.append("AND dd.CLAVE_DELEGACION = ").append(idDelegacion).append(" ");
        }

        if (idSubdelegacion != null && idSubdelegacion != 0) {
            query.append("AND ds.CLAVE_SUBDELEGACION = ").append(idSubdelegacion).append(" ");
        }

        if (origen.equals(OrigenSolicitudEnum.INTERNET)) {
            query.append("AND rtt.RTT_NUM_TIPO_RIESGO IN (1,3) ");
        }

        query.append("AND rtt.RTT_NUM_NSS = ").append("'").append(nss).append("' ").append("and rtt.RTT_FEC_FIN_ACCIDENTE between TO_DATE('").append(format.format(fechaInicio)).append(" 00:00:00','dd/MM/yyyy HH24:mi:ss') ")
                .append("AND TO_DATE('").append(format.format(fechaFin)).append(" 23:59:59','dd/MM/yyyy HH24:mi:ss')");

        List<Object[]> result = this.getEntityManager().createNativeQuery(query.toString()).getResultList();

        LOGGER.debug("Coincidencias:{}", result.size());

        //Convertimos los datos de la consulta a objetos RiesgosTrabajo
        PatronRiesgosTrabajo patron = null;
        if (!result.isEmpty()) {
            riesgosTrabajo = UtilRTT.getDataRiesgosTrabajoFromResulset(result);
            patron = new PatronRiesgosTrabajo();
            Object[] row = result.get(0);
            patron.setNss(nss);
            patron.setInicioPeriodo(fechaInicio);
            patron.setFinPeriodo(fechaFin);
            patron.setIdPersona(((Number) row[13]).intValue());
            patron.setIdPatronSujetoObligado(((Number) row[13]).longValue());
            patron.setNrp((String) row[11] + row[12]);
            patron.setDelegacion(((Number) row[14]).longValue());
            patron.setSubdelegacion(new Subdelegacion());
            patron.getSubdelegacion().setId(idSubdelegacion);
            patron.getSubdelegacion().setDelegacion(new Delegacion());
            patron.getSubdelegacion().getDelegacion().setId(idSubdelegacion);
        }
        riesgosPatron.setRiesgos(riesgosTrabajo);
        riesgosPatron.setPatron(patron);
        LOGGER.debug("Se finaliza la consulta");
        return riesgosPatron;
    }

    /**
     * Obtiene los datos del patron
     *
     * @param regPatron
     * @return
     * @throws RiesgosTrabajoException
     */
    @SuppressWarnings("rawtypes")
    @Override
    public PatronRiesgosTrabajo findPatron(String regPatron, Long idDelegacion, Long idSubdelegacion) throws RiesgosTrabajoException {

        LOGGER.debug("Obtener patron");

        List<DitLlavePatron> result;
        PatronRiesgosTrabajo patron;
        idDelegacion = idDelegacion != null && idDelegacion.intValue() != 0 ? idDelegacion : null;
        idSubdelegacion = idSubdelegacion != null && idSubdelegacion.intValue() != 0 ? idSubdelegacion : null;

        //Validamos que el registro patronal sea de 10 caracteres
        if (regPatron.length() > 10) {
            regPatron = regPatron.substring(0, regPatron.length() - 1);
        }

        //Realizamos la consulta
        Criteria criteria = this.getSession().createCriteria(DitLlavePatron.class);
        criteria.add(Restrictions.eq("refBusca", regPatron));
        if (idDelegacion != null) {
            Criteria queryPatron = criteria.createCriteria("ditPatronSujetoObligado");
            Criteria queryParSubdel = queryPatron.createCriteria("ditSubdelPatSujOblig");
            Criteria querySubdel = queryParSubdel.createCriteria("dicSubdelegacion");
            if (idSubdelegacion != null) {
                querySubdel.add(Restrictions.eq("cveIdSubdelegacion", idSubdelegacion));
            }
            querySubdel.createAlias("dicDelegacion", "delegacion");
            querySubdel.add(Restrictions.eq("delegacion.cveIdDelegacion", idDelegacion));

        }
        result = criteria.list();

        LOGGER.debug("Coincidencias:{}", result.size());

        //Convertimos los datos de la consulta a objeto PatronRiesgosTrabajo
        if (result != null && !result.isEmpty()) {
            DitLlavePatron llavePatron = result.get(0);

            //traemos de sesion los objetos lazy
            llavePatron.getDitPersona();
            llavePatron.getDitPersonaMoral();
            llavePatron.getDitPatronSujetoObligado();
            llavePatron.getDitPatronSujetoObligado().getDitSubdelPatSujOblig();


            //Convertimos el objeto Llavepatron a  PatronRiesgosTabajo
            patron = UtilRTT.converterPatronRiesgosTrabajo(llavePatron);
        } else {
            throw new RiesgosTrabajoException("El NRP que ingreso no existe");
        }

        return patron;
    }


    @Override
    public PatronRiesgosTrabajo findPersona(String rfc, int tipoPersona) throws RiesgosTrabajoException {
        LOGGER.debug("Obtener persona");
        List<DitPersonaMoral> resultPmoral;
        List<DitPersonaFisica> resultPfisica;
        PatronRiesgosTrabajo patron = null;

        //Se valida que tipoo de persona es, 1 - fisica o moral
        if (tipoPersona == 1) {
            //Realizamos la consulta
            Criteria criteria = this.getSession().createCriteria(DitPersonaMoral.class);
            criteria.add(Restrictions.eq("rfc", rfc));
            criteria.addOrder(Order.asc("cveIdPersonaMoral"));
            resultPmoral = criteria.list();

            //Convertimos los datos de la consulta a objeto PatronRiesgosTrabajo
            if (resultPmoral != null && !resultPmoral.isEmpty()) {
                //Convertimos el objeto Llavepatron a  PatronRiesgosTabajo
                patron = UtilRTT.converterPersonaMoral(resultPmoral.get(0));
            } else {
                throw new RiesgosTrabajoException("El RFC no cuenta con idpersona Moral");
            }
        }else {
            //Realizamos la consulta
            Criteria criteria = this.getSession().createCriteria(DitPersonaFisica.class);
            criteria.add(Restrictions.eq("rfc", rfc));
            criteria.addOrder(Order.asc("cveIdPersonaFisica"));
            resultPfisica = criteria.list();

            //Convertimos los datos de la consulta a objeto PatronRiesgosTrabajo
            if (resultPfisica != null && !resultPfisica.isEmpty()) {
                //Convertimos el objeto Llavepatron a  PatronRiesgosTabajo
                patron = UtilRTT.converterPersonaFisica(resultPfisica.get(0));
            } else {
                throw new RiesgosTrabajoException("El RFC no cuenta con idpersona Fisica");
            }
        }

        return patron;
    }


    /**
     * Obtiene las solicitudes de un patron
     *
     * @param idPatron
     * @return
     * @throws RiesgosTrabajoException
     */
    @Override
    public List<Solicitud> findSolicitud(long idPatron, TipoSolicitudEnum tipo, EstadoSolicitudEnum estado, Long tipoTramite, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException {
        LOGGER.debug("Obtener patron");
        StringBuilder bfr = new StringBuilder();

        List<DitSolicitud> listDitSolicitud;

        //Realizamos la consulta
        bfr.append(" Select solicitud from DitTramitePatSujObligado tps ");
        bfr.append(" join tps.ditTramite as tramite ");
        bfr.append(" join tramite.ditSolicitud as solicitud ");
        bfr.append(" where tps.id.cveIdPatronSujetoObligado = :idPatron ");
        bfr.append(" and solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud");
        bfr.append(" and solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
        bfr.append(" and tramite.dicTipoTramite.cveIdTipoTramite = :idTipoTramite ");
        bfr.append(" and tramite.refObservacion = :tipoArchivo ");

        //Ordenamiento
        bfr.append(" order by solicitud.fecSolicitud desc");

        Query query = this.getSession().createQuery(bfr.toString());
        query.setParameter("idPatron", idPatron);
        query.setParameter("idTipoSolicitud", tipo.getValor().longValue());
        query.setParameter("idEstadoSolicitud", estado.getCodigo().longValue());
        query.setParameter("idTipoTramite", tipoTramite);
        query.setParameter("tipoArchivo", Long.toString(tipoArchivo.getId()));
        listDitSolicitud = query.list();
        LOGGER.debug("Solicitudes encontradas: {}", listDitSolicitud != null ? listDitSolicitud.size() : 0);
        return UtilRTT.convertirSolicitud(listDitSolicitud);
    }

    /**
     * Obtiene las solicitudes de un patron
     *
     * @param rfc
     * @param tipo
     * @param estado
     * @param tipoTramite
     * @param tipoArchivo
     * @return
     * @throws RiesgosTrabajoException
     */
    @Override
    public List<Solicitud> findSolicitudesRFC(String rfc, TipoSolicitudEnum tipo, EstadoSolicitudEnum estado, Long tipoTramite, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException {
        LOGGER.debug("Obtener solicitudes por RFC");
        StringBuilder bfr = new StringBuilder();
        List<DitSolicitud> listDitSolicitud;

        //Realizamos la consulta
        bfr.append(" Select solicitud from DitTramite as tramite ");
        bfr.append(" join tramite.ditSolicitud as solicitud ");
        bfr.append(" where solicitud.refObservacion = :rfc ");
        bfr.append(" and solicitud.dicTipoSolicitud.cveIdTipoSolicitud = :idTipoSolicitud");
        bfr.append(" and solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
        bfr.append(" and tramite.dicTipoTramite.cveIdTipoTramite = :idTipoTramite ");
        bfr.append(" and tramite.refObservacion = :tipoArchivo ");
        //Ordenamiento
        bfr.append(" order by solicitud.fecSolicitud desc");

        Query query = this.getSession().createQuery(bfr.toString());
        query.setParameter("rfc", rfc);
        query.setParameter("idTipoSolicitud", tipo.getValor().longValue());
        query.setParameter("idEstadoSolicitud", estado.getCodigo().longValue());
        query.setParameter("idTipoTramite", tipoTramite);
        query.setParameter("tipoArchivo", Long.toString(tipoArchivo.getId()));
        listDitSolicitud = query.list();
        LOGGER.debug("Solicitudes encontradas: {}", listDitSolicitud != null ? listDitSolicitud.size() : 0);
        return UtilRTT.convertirSolicitud(listDitSolicitud);
    }

    @Override
    public List<Object[]> findPatronByNombre(String nrs, String tipo, Long idDelegacion, Long idSubdelegacion, Integer periodo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {

        return this.findPatronRfcRs(nrs, 2, periodo, idDelegacion, idSubdelegacion, tipo, origen);

    }

    @Override
    public PatronRiesgosTrabajo findPatronBySujeto(long cveSujetoObligado) throws RiesgosTrabajoException {
        LOGGER.debug("Obtener patron");

        List<DitLlavePatron> result;
        List<DitPatronSujetoObligado> resultNombre;
        PatronRiesgosTrabajo patron = null;

        //Realizamos la consulta
        Criteria criteria = this.getSession().createCriteria(DitPatronSujetoObligado.class);
        criteria.add(Restrictions.eq("cveIdPatronSujetoObligado", cveSujetoObligado));
        resultNombre = criteria.list();
        LOGGER.debug("Coincidencias Patron Por Sujeto:{}", resultNombre.size());
        if (resultNombre != null && !resultNombre.isEmpty()) {
            DitPatronSujetoObligado sujeto = resultNombre.get(0);
            //Realizamos la consulta
            criteria = this.getSession().createCriteria(DitLlavePatron.class);
            criteria.add(Restrictions.eq("ditPatronSujetoObligado", sujeto));
            result = criteria.list();
            LOGGER.debug("Coincidencias Patron:{}", result.size());
            //Convertimos los datos de la consulta a objeto PatronRiesgosTrabajo
            if (result != null && !result.isEmpty()) {
                DitLlavePatron llavePatron = result.get(0);
                //traemos de sesion los objetos lazy
                llavePatron.getDitPersona();
                llavePatron.getDitPersonaMoral();
                llavePatron.getDitPatronSujetoObligado();
                llavePatron.getDitPatronSujetoObligado().getDitSubdelPatSujOblig();
                //Convertimos el objeto Llavepatron a  PatronRiesgosTabajo
                patron = UtilRTT.converterPatronRiesgosTrabajo(llavePatron);
                patron.setNrp(llavePatron.getRefBusca());
            } else {
                throw new RiesgosTrabajoException("El NRS que ingreso no existe");
            }
        }

        return patron;
    }

    @Override
    public List<Object[]> findPatronByRFC(String rfc, Long idDelegacion, Long idSubdelegacion, Integer periodo, OrigenSolicitudEnum origen) {
        return findPatronRfcRs(rfc, 1, periodo, idDelegacion, idSubdelegacion, null, origen);
    }

    @Override
    public List<Object[]> findRpPorRFC(String rfc, Long idDelegacion, Long idSubdelegacion, Integer periodo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        return findRpxRfc(rfc, 1, periodo, idDelegacion, idSubdelegacion, null, origen);
    }

    @Override
    public List<RiesgoTrabajo> findRtXLisRp(Integer periodo, String rfc, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        return findRiesgosxRps(periodo, rfc, idDelegacion, idSubdelegacion, origen);
    }

    @Override
    public ReporteRiesgoTrabajo findReportesXrfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        StringBuilder queryRep = new StringBuilder();
        log.debug("Se busca si existe un reporte vivo solicitado del RFC: " + rfc);
        queryRep.append(UtilRTT.FIND_REPORTE_RTT.toString());
        Query qRepEje = this.getSession().createSQLQuery(queryRep.toString());
        qRepEje.setString("rfc", rfc);
        qRepEje.setLong("origen", origen.getId());
        Object[] reportes = (Object[]) qRepEje.uniqueResult();
        ReporteRiesgoTrabajo reportesolicitado = null;

        //Estados
        //0 - Sin generar, 1 - Solicitado, 2 - Generado
        if (reportes != null) {
            try {
                reportesolicitado = UtilRTT.reporteObjectToModel(reportes);
            } catch (SQLException e) {
                e.printStackTrace();
                reportesolicitado = null;
            }
        }
        return reportesolicitado;
    }

    @Override
    public ReporteRiesgoTrabajo findReportesXrfcEstados(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        StringBuilder queryRep = new StringBuilder();
        log.debug("Se busca si existe un reporte vivo solicitado del RFC: " + rfc);
        queryRep.append(UtilRTT.FIND_REPORTE_EDO_RTT.toString());
        Query qRepEje = this.getSession().createSQLQuery(queryRep.toString());
        qRepEje.setString("rfc", rfc);
        qRepEje.setLong("origen", origen.getId());
        Object[] reportes = (Object[]) qRepEje.uniqueResult();
        ReporteRiesgoTrabajo reportesolicitado = null;

        //Estados
        //0 - Sin generar, 1 - Solicitado, 2 - Generado
        if (reportes != null) {
            try {
                reportesolicitado = UtilRTT.reporteObjectToModelEdo(reportes);
            } catch (SQLException e) {
                e.printStackTrace();
                reportesolicitado = null;
            }
        }
        return reportesolicitado;
    }

    @Override
    public ReporteRiesgoTrabajo createReporteRfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        StringBuilder queryRep = new StringBuilder();
        int vals = 0;
        //Estados
        //0 - Sin generar, 1 - Solicitado, 2 - Generado
        ReporteRiesgoTrabajo reportesolicitado = new ReporteRiesgoTrabajo();
        log.debug("Se crea un nuevo reporte para el RFC: " + rfc);
        StringBuilder queryCreaRep = new StringBuilder();

        queryCreaRep.append(UtilRTT.CREAR_REPORTE_RTT.toString());
        Query qCreaRep = this.getSession().createSQLQuery(queryCreaRep.toString());
        qCreaRep.setString("rfc", rfc);
        qCreaRep.setLong("generaReporte", 0);
        qCreaRep.setLong("estado", 0);
        qCreaRep.setLong("origen", origen.getId());
        vals = qCreaRep.executeUpdate();

        if (vals != 0) {
            reportesolicitado = findReportesXrfc(rfc, origen);
        } else {
            throw new RiesgosTrabajoException("No existe un reporte creado.");
        }

        return reportesolicitado;
    }

    @Override
    public ReporteRiesgoTrabajo updateReportesRfc(ReporteRiesgoTrabajo reporte, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        StringBuilder queryRep = new StringBuilder();
        queryRep.append(UtilRTT.UPDATE_REPORTE_RTT.toString());
        Query qRepEje = this.getSession().createSQLQuery(queryRep.toString());
        qRepEje.setString("urlReporte", reporte.getUrlReporte());
        qRepEje.setLong("generaReporte", reporte.getGeneraReporte());
        qRepEje.setLong("estado", reporte.getEstadoReporte());
        qRepEje.setString("rfc", reporte.getRfc());
        qRepEje.setLong("origen", origen.getId());
        int vals = qRepEje.executeUpdate();

        //Estados
        //0 - Sin generar, 1 - Solicitado, 2 - Generado
        ReporteRiesgoTrabajo reportesolicitado = new ReporteRiesgoTrabajo();
        if (vals != 0) {
            reportesolicitado = findReportesXrfcEstados(reporte.getRfc(), origen);
        } else {
            throw new RiesgosTrabajoException("No existe un reporte creado.");
        }

        return reportesolicitado;
    }

    @Override
    public int updateDocumentOnlyRfc(ReporteRiesgoTrabajo reporte, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        StringBuilder queryRep = new StringBuilder();
        ReporteRiesgoTrabajo reporteActualiza = findReportesXrfc(reporte.getRfc(), origen);

        queryRep.append(UtilRTT.UPDATE_REPORTE_ONLY_DOC_RTT.toString());
        Query qRepEje = this.getSession().createSQLQuery(queryRep.toString());

        if (reporteActualiza.getDocumento() == null) {
            qRepEje.setParameter("documento", reporte.getDocumento());
        } else {
            int taman = reporteActualiza.getDocumento().length + reporte.getDocumento().length;
            byte[] acumulado = new byte[taman];
            try {
                System.arraycopy(reporteActualiza.getDocumento(), 0, acumulado, 0, reporteActualiza.getDocumento().length);
                System.arraycopy(reporte.getDocumento(), 0, acumulado, reporteActualiza.getDocumento().length, reporte.getDocumento().length);
                qRepEje.setParameter("documento", acumulado);
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                log.debug("Imposible guardar el archivo");
            }
        }
        qRepEje.setString("rfc", reporte.getRfc());
        qRepEje.setLong("origen", origen.getId());
        int result = qRepEje.executeUpdate();

        return result;
    }

    @Override
    public ReporteRiesgoTrabajo downloadDocumentOnlyRfc(String rfc, int posicion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {

        try {
            ReporteRiesgoTrabajo reporteActualiza = findReportesXrfc(rfc, origen);
            int paquetes = 4;
            int valorPaquete = (int) Math.ceil((float) reporteActualiza.getDocumento().length / paquetes);
            ReporteRiesgoTrabajo docuEnv = new ReporteRiesgoTrabajo();

            if (posicion == 3) {
                byte[] aux2 = Arrays.copyOfRange(reporteActualiza.getDocumento(), posicion * valorPaquete, reporteActualiza.getDocumento().length);
                docuEnv.setDocumento(aux2);
            } else {
                byte[] aux = Arrays.copyOfRange(reporteActualiza.getDocumento(), posicion * valorPaquete, (posicion + 1) * valorPaquete);
                docuEnv.setDocumento(aux);
            }

            return docuEnv;
        } catch (RiesgosTrabajoException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int downReporteRfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        StringBuilder queryRep = new StringBuilder();
        int vals = 0;

        queryRep.append(UtilRTT.BAJA_REPORTE_RTT.toString());
        Query qRepEje = this.getSession().createSQLQuery(queryRep.toString());
        qRepEje.setString("rfc", rfc);
        qRepEje.setLong("origen", origen.getId());
        vals = qRepEje.executeUpdate();

        return vals;
    }

    private List<Object[]> findPatronRfcRs(String param, Integer tipoBusqueda, Integer periodo, Long idDelegacion, Long idSubdelegacion, String tipoBusquedaRS, OrigenSolicitudEnum origen) {
        StringBuilder queryPat = new StringBuilder();
        String concatNom = "";
        String concatTipoPersona = "";
        periodo = periodo == null || periodo.intValue() == 0 ? null : periodo;
        idDelegacion = idDelegacion == null || idDelegacion.intValue() == 0 ? null : idDelegacion;
        idSubdelegacion = idSubdelegacion == null || idSubdelegacion.intValue() == 0 ? null : idSubdelegacion;

        if ((tipoBusqueda == 1 && param.length() == 12) || tipoBusqueda == 2) {
            concatNom = "persona.DENOMINACION_RAZON_SOCIAL";
            concatTipoPersona = "MORAL";
        } else if (tipoBusqueda == 1 && param.length() == 13) {
            concatNom = "persona.NOMBRE_COMERCIAL";
            concatTipoPersona = "FISICA";
        } else {
            return null;
        }

        queryPat.append("select ref_busca rp, ").append(concatNom);
        //si nos proporcionan periodo buscaremos que el rp tenga registros en la tabla de riesgos
        if (periodo != null) {
            queryPat.append(", count(rttReg.CVE_ID_REGISTRO_RT) riesgos");
        }
        queryPat.append(" from DIT_LLAVE_PATRON llave")
                .append(" inner join DIT_PATRON_SUJETO_OBLIGADO so on so.CVE_ID_PATRON_SUJETO_OBLIGADO = llave.CVE_ID_PATRON_SUJETO_OBLIGADO")
                .append(" inner join DIT_PERSONA_").append(concatTipoPersona).append(" persona")
                .append(" on persona.CVE_ID_PERSONA_").append(concatTipoPersona).append("= llave.CVE_ID_PERSONA_").append(concatTipoPersona);
        if (idDelegacion != null) {
            queryPat.append(" inner join DIT_DELSUB_PAT_SUJ_OBLIG subpat on subpat.CVE_ID_PATRON_SUJETO_OBLIGADO = so.CVE_ID_PATRON_SUJETO_OBLIGADO")
                    .append(" inner join DIC_SUBDELEGACION subdel on subpat.CVE_ID_SUBDELEGACION = subdel.CVE_ID_SUBDELEGACION")
                    .append(" inner join DIC_DELEGACION del on subdel.CVE_ID_DELEGACION = del.CVE_ID_DELEGACION");
        }

        if (periodo != null) {
            queryPat.append(" inner join RTT_REGISTRO_RT rttReg on rttReg.RTT_REG_PATRON = llave.REF_BUSCA");
        }

        if (tipoBusqueda == 1) {
            queryPat.append(" where persona.rfc='").append(param).append("' and ").append(concatNom).append(" is not null");
        } else {
            String paramBusqueda = (tipoBusquedaRS.equals("like") ? "%" : "") + param + "%";
            queryPat.append(" where persona.DENOMINACION_RAZON_SOCIAL like '").append(paramBusqueda).append("'");
        }

        if (idDelegacion != null) {
            queryPat.append(" and del.CVE_ID_DELEGACION=").append(idDelegacion);
            if (idSubdelegacion != null) {
                queryPat.append(" and subdel.CVE_ID_SUBDELEGACION=").append(idSubdelegacion);
            }
        }

        if (periodo != null) {
            List<Date> periodos = UtilRTT.obtenerPeriodoCorrespondiente(origen, periodo);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            queryPat.append(" and rttReg.RTT_FEC_FIN_ACCIDENTE BETWEEN")
                    .append(" to_date('" + simpleDateFormat.format(periodos.get(0)) + "','dd/mm/yyyy hh24:mi:ss') and ")
                    .append("to_date('" + simpleDateFormat.format(periodos.get(1)) + "','dd/mm/yyyy hh24:mi:ss')").
                    append(" group by ref_busca, persona.DENOMINACION_RAZON_SOCIAL");
        }

        Query query = this.getSession().createSQLQuery(queryPat.toString());
        List<Object[]> patrones = (List<Object[]>) query.list();
        LOGGER.debug("Patrones encontrados: {}", patrones.size());
        return patrones;
    }

    @Override
    public int findExiRfc(String param) {
        StringBuilder queryPat = new StringBuilder();
        String concatNom = "";
        String concatTipoPersona = "";
        int valid = 0;

        if (param.length() == 12) {
            concatNom = "persona.DENOMINACION_RAZON_SOCIAL";
            concatTipoPersona = "MORAL";
        } else if (param.length() == 13) {
            concatNom = "persona.NOMBRE_COMERCIAL";
            concatTipoPersona = "FISICA";
        } else {
            return 0;
        }

        queryPat.append("select persona.CVE_ID_PERSONA_").append(concatTipoPersona).append(" ");
        queryPat.append(" from DIT_PERSONA_").append(concatTipoPersona).append(" persona");
        queryPat.append(" where persona.rfc='").append(param).append("' and ").append(concatNom).append(" is not null");

        Query query = this.getSession().createSQLQuery(queryPat.toString());
        List<Object[]> validacion = (List<Object[]>) query.list();
        if (validacion.isEmpty() || validacion.size() == 0 || validacion.equals(null)) {
            LOGGER.debug("El RFC no existe");
            return 0;
        } else valid = 1;
        return valid;
    }

    @Override
    public int findExiNrp(String param) {
        StringBuilder queryPat = new StringBuilder();
        String nrp = "";
        String mod = "";
        int valid = 0;

        LOGGER.debug("param: "+param);

        if (param.length() >= 10) {
            nrp = param.substring(0, 8);
            mod = param.substring(8, 10);
        } else if (param.length() < 10) {
            return valid;
        }

        LOGGER.debug("nrp: "+nrp);
        LOGGER.debug("mod: "+mod);

        queryPat.append("SELECT pg.reg_patron||dm.num_modalidad||pg.dig_ver AS patron ");
        queryPat.append("FROM DIT_PATRON_GENERAL pg ");
        queryPat.append("INNER JOIN DIT_PATRON_SUJETO_OBLIGADO so ON so.CVE_ID_PATRON_SUJETO_OBLIGADO = pg.CVE_ID_PATRON_SUJETO_OBLIGADO ");
        queryPat.append("INNER JOIN DIC_MODALIDAD dm ON dm.CVE_ID_MODALIDAD = so.CVE_ID_MODALIDAD ");
        queryPat.append("WHERE pg.REG_PATRON = '").append(nrp).append("' ");
        queryPat.append("AND dm.NUM_MODALIDAD = '").append(mod).append("'");

        LOGGER.debug("queryPat.toString(): "+queryPat.toString());

        Query query = this.getSession().createSQLQuery(queryPat.toString());
        List<Object[]> validacion = (List<Object[]>) query.list();
        LOGGER.debug("validacion.size(): "+validacion.size());

        if (validacion.isEmpty() || validacion.size() == 0 || validacion.equals(null)) {
            LOGGER.debug("El NRP no existe");
            return valid;
        } else valid = 1;
        return valid;
    }

    private List<Object[]> findRpxRfc(String param, Integer tipoBusqueda, Integer periodo, Long idDelegacion, Long idSubdelegacion, String tipoBusquedaRS, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        StringBuilder queryPat = new StringBuilder();
        String concatNom = "";
        String concatTipoPersona = "";
        periodo = periodo == null || periodo.intValue() == 0 ? null : periodo;
        idDelegacion = idDelegacion == null || idDelegacion.intValue() == 0 ? null : idDelegacion;
        idSubdelegacion = idSubdelegacion == null || idSubdelegacion.intValue() == 0 ? null : idSubdelegacion;

        if ((tipoBusqueda == 1 && param.length() == 12) || tipoBusqueda == 2) {
            concatNom = "persona.DENOMINACION_RAZON_SOCIAL";
            concatTipoPersona = "MORAL";
        } else if (tipoBusqueda == 1 && param.length() == 13) {
            concatNom = "persona.NOMBRE_COMERCIAL";
            concatTipoPersona = "FISICA";
        } else {
            return null;
        }

        queryPat.append("select DISTINCT(ref_busca) rp, pg.reg_patron, so.cve_id_tipo_pago_modalidad, pg.dig_ver ");
        queryPat.append(" from DIT_LLAVE_PATRON llave")
                .append(" inner join DIT_PATRON_SUJETO_OBLIGADO so on so.CVE_ID_PATRON_SUJETO_OBLIGADO = llave.CVE_ID_PATRON_SUJETO_OBLIGADO")
                .append(" inner join DIT_PATRON_GENERAL pg on pg.CVE_ID_PATRON_SUJETO_OBLIGADO = so.CVE_ID_PATRON_SUJETO_OBLIGADO")
                .append(" inner join DIT_PERSONA_").append(concatTipoPersona).append(" persona")
                .append(" on persona.CVE_ID_PERSONA_").append(concatTipoPersona).append("= llave.CVE_ID_PERSONA_").append(concatTipoPersona);
        if (idDelegacion != null) {
            queryPat.append(" inner join DIT_DELSUB_PAT_SUJ_OBLIG subpat on subpat.CVE_ID_PATRON_SUJETO_OBLIGADO = so.CVE_ID_PATRON_SUJETO_OBLIGADO")
                    .append(" inner join DIC_SUBDELEGACION subdel on subpat.CVE_ID_SUBDELEGACION = subdel.CVE_ID_SUBDELEGACION")
                    .append(" inner join DIC_DELEGACION del on subdel.CVE_ID_DELEGACION = del.CVE_ID_DELEGACION");
        }

        //if(periodo != null) {
        queryPat.append(" inner join RTT_REGISTRO_RT rttReg on rttReg.RTT_REG_PATRON = llave.REF_BUSCA");
        //}

        if (tipoBusqueda == 1) {
            queryPat.append(" where persona.rfc='").append(param).append("' and ").append(concatNom).append(" is not null");
        } else {
            String paramBusqueda = (tipoBusquedaRS.equals("like") ? "%" : "") + param + "%";
            queryPat.append(" where persona.DENOMINACION_RAZON_SOCIAL like '").append(paramBusqueda).append("'");
        }

        if (idDelegacion != null) {
            queryPat.append(" and del.CVE_ID_DELEGACION=").append(idDelegacion);
            if (idSubdelegacion != null) {
                queryPat.append(" and subdel.CVE_ID_SUBDELEGACION=").append(idSubdelegacion);
            }
        }

        List<Date> periodos = null;
        //if(periodo != null) {
        periodos = UtilRTT.obtenerPeriodoCorrespondiente(origen, periodo);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        queryPat.append(" and rttReg.RTT_FEC_FIN_ACCIDENTE BETWEEN")
                .append(" to_date('" + simpleDateFormat.format(periodos.get(0)) + "','dd/mm/yyyy hh24:mi:ss') and ")
                .append("to_date('" + simpleDateFormat.format(periodos.get(1)) + "','dd/mm/yyyy hh24:mi:ss')");
        //}

        LOGGER.debug("Query a ejecutar: " + queryPat.toString());
        Query query = this.getSession().createSQLQuery(queryPat.toString());
        List<Object[]> patrones = (List<Object[]>) query.list();
        LOGGER.debug("Patrones encontrados: {}", patrones.size());

        LOGGER.debug("Se finaliza la consulta");

        return patrones;
    }

    private List<RiesgoTrabajo> findRiesgosxRps(Integer periodo, String param, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        periodo = periodo == null || periodo.intValue() == 0 ? null : periodo;
        List<Date> periodos = null;
        periodos = UtilRTT.obtenerPeriodoCorrespondiente(origen, periodo);
        List<RttRegistro> result = null;
        List<RiesgoTrabajo> riesgosTrabajo = new ArrayList<RiesgoTrabajo>();


        List<Object[]> objectPatrones = findRpxRfc(param, 1, periodo, idDelegacion, idSubdelegacion, null, origen);
        if (objectPatrones.size() != 0) {
            List<String> patrones = UtilRTT.patronesObjToMod(objectPatrones);
            List<RiesgoTrabajo> riesgosTraUno = UtilRTT.riegosObjToMod(objectPatrones);

            LOGGER.debug("Consultando riesgos de trabajo de acuerdo a los patrones encontrados");
            //Realizamos la consulta de RT
            Criteria criteria = this.getSession().createCriteria(RttRegistro.class);
            criteria.add(Restrictions.in("rttRegPatron", patrones));
            if (periodos != null) {
                criteria.add(Restrictions.between("rttFecFinAccidente", periodos.get(0), periodos.get(1)));
            }

            //Si es de internet filtramos por tipo de riesgo 1 y 3
            if(origen.equals(OrigenSolicitudEnum.INTERNET)){
                //llenamos la lista con los tipos de riesgos
                List<Integer> tiposRiesgos = new ArrayList<Integer>();
                tiposRiesgos.add(1);
                tiposRiesgos.add(3);
                criteria.add(Restrictions.in("rttNumTipoRiesgo", tiposRiesgos));
            }
            criteria.addOrder(Order.asc("rttFecFinAccidente"));
            result = criteria.list();
            LOGGER.debug("Coincidencias:{}", result.size());

            //Convertimos los datos de la consulta a objetos RiesgosTrabajo
            if (result != null && !result.isEmpty()) {
                riesgosTrabajo = addModalidadRT(UtilRTT.getDataRiesgosTrabajoXRfc(result), riesgosTraUno);
            } else {
                throw new RiesgosTrabajoException("No existen Riesgos de Trabajo");
            }
            LOGGER.debug("Se finaliza la consulta");
        } else {
            RiesgoTrabajo nuevo = new RiesgoTrabajo();
            nuevo.setDv("9999");
            riesgosTrabajo.add(nuevo);
        }

        return riesgosTrabajo;
    }

    private List<RiesgoTrabajo> addModalidadRT(List<RiesgoTrabajo> riesgosTrabajo, List<RiesgoTrabajo> riesgosTraUno) throws RiesgosTrabajoException {
        List<RiesgoTrabajo> riesgosTraDos = new ArrayList<RiesgoTrabajo>();
        TreeMap<String, String> table = new TreeMap<String, String>();
        TreeMap<String, String> tableDv = new TreeMap<String, String>();

        for (RiesgoTrabajo riesgotrabajo : riesgosTraUno) {
            table.put(riesgotrabajo.getRpReg(), riesgotrabajo.getModalidad());
            tableDv.put(riesgotrabajo.getRpReg(), riesgotrabajo.getDvRp());
        }

        for (RiesgoTrabajo riesgotrabajo : riesgosTrabajo) {
            Map.Entry<String, String> floorEntry = table.floorEntry(riesgotrabajo.getRpReg());
            Map.Entry<String, String> floorEntryDv = tableDv.floorEntry(riesgotrabajo.getRpReg());
            if (floorEntry != null)
                riesgotrabajo.setModalidad(floorEntry.getValue());

            if (floorEntryDv != null)
                riesgotrabajo.setDvRp(floorEntryDv.getValue());

            riesgosTraDos.add(riesgotrabajo);
        }


        return riesgosTraDos;
    }

    @Override
    public List<Delegacion> findDelegacionesActivas() {
        List<Delegacion> delegaciones = new ArrayList<Delegacion>();
        Criteria queryDel = this.getSession().createCriteria(DicDelegacion.class);
        queryDel.add(Restrictions.isNull("fecRegistroBaja"));

        List<DicDelegacion> dicDelegaciones = queryDel.list();
        for (DicDelegacion dicDelegacion : dicDelegaciones) {
            Delegacion delegacion = convertirDicDelegacion(dicDelegacion);
            delegaciones.add(delegacion);
        }

        return delegaciones;
    }

    @Override
    public List<Subdelegacion> findSubDelegacionesActivas(Long idDelegacion) {
        List<Subdelegacion> subdelegaciones = new ArrayList<Subdelegacion>();
        Criteria querySubDel = this.getSession().createCriteria(DicSubdelegacion.class);
        querySubDel.createAlias("dicDelegacion", "delegacion");
        querySubDel.add(Restrictions.eq("delegacion.cveIdDelegacion", idDelegacion));
        querySubDel.add(Restrictions.isNull("fecRegistroBaja"));
        List<DicSubdelegacion> dicSubDelegaciones = querySubDel.list();

        for (DicSubdelegacion dicSubDelegacion : dicSubDelegaciones) {
            Subdelegacion subdelegacion = convertirDicSubdelegacion(dicSubDelegacion, false);
            subdelegaciones.add(subdelegacion);
        }

        return subdelegaciones;
    }

    @Override
    public Subdelegacion getSubdelegacionUsuario(Long idSubdelegacion) {
        Subdelegacion subdelegacion = null;
        Criteria querySubDel = this.getSession().createCriteria(DicSubdelegacion.class);
        querySubDel.add(Restrictions.eq("cveIdSubdelegacion", idSubdelegacion));
        querySubDel.add(Restrictions.isNull("fecRegistroBaja"));

        subdelegacion = convertirDicSubdelegacion((DicSubdelegacion) querySubDel.uniqueResult(), true);

        return subdelegacion;
    }

    @Override
    public Delegacion getDelegacionUsuario(Long idDelegacion) {
        Delegacion delegacion = null;
        Criteria queryDel = this.getSession().createCriteria(DicDelegacion.class);
        queryDel.add(Restrictions.eq("cveIdDelegacion", idDelegacion));
        queryDel.add(Restrictions.isNull("fecRegistroBaja"));

        delegacion = convertirDicDelegacion((DicDelegacion) queryDel.uniqueResult());


        return delegacion;
    }

    private Subdelegacion convertirDicSubdelegacion(DicSubdelegacion dicSubDelegacion, boolean incluirDelegacion) {

        Subdelegacion subdelegacion = null;
        if (dicSubDelegacion != null) {
            subdelegacion = new Subdelegacion();
            subdelegacion.setId(dicSubDelegacion.getCveIdSubdelegacion());
            subdelegacion.setClave(dicSubDelegacion.getClaveSubdelegacion());
            subdelegacion.setDescripcion(dicSubDelegacion.getDesSubdelegacion());
            if (incluirDelegacion) {
                DicDelegacion dicDelegacion = dicSubDelegacion.getDicDelegacion();
                subdelegacion.setDelegacion(convertirDicDelegacion(dicDelegacion));
            }
        }

        return subdelegacion;
    }

    private Delegacion convertirDicDelegacion(DicDelegacion dicDelegacion) {
        Delegacion delegacion = null;
        if (dicDelegacion != null) {
            delegacion = new Delegacion();
            delegacion.setId(dicDelegacion.getCveIdDelegacion());
            delegacion.setClave(dicDelegacion.getClaveDelegacion());
            delegacion.setDescripcion(dicDelegacion.getDesDeleg());
        }

        return delegacion;
    }

    @Override
    public String findPatronGeneral(String regPatron) throws RiesgosTrabajoException {
        DitPatronGeneral ditPatronGeneral = (DitPatronGeneral) this.getSession().createCriteria(DitPatronGeneral.class)
                .add(Restrictions.ilike("regPatron", regPatron))
                .uniqueResult();

        LOGGER.debug("Se finaliza la consulta con digito verificador: " + ditPatronGeneral.getDigVer());
        if (ditPatronGeneral != null) {
            return ditPatronGeneral.getDigVer();
        } else {
            throw new RiesgosTrabajoException(
                    "No se localizó digito verificador relacionado al RP");
        }
    }

    @Override
    public PatronRiesgosTrabajo findPatronXRfc(String rfc, Long idDelegacion, Long idSubdelegacion) throws RiesgosTrabajoException { //findPatronRfcRs
        StringBuilder queryPat = new StringBuilder();
        int tipoBusqueda = 1;
        String concatTipoPersona = "";

        LOGGER.debug("Se busca el patron");
        if ((tipoBusqueda == 1 && rfc.length() == 12) || tipoBusqueda == 2) {
            concatTipoPersona = "MORAL";
        } else if (tipoBusqueda == 1 && rfc.length() == 13) {
            concatTipoPersona = "FISICA";
        } else {
            return null;
        }

        queryPat.append("SELECT llave.REF_BUSCA, PG.DIG_VER ")
                .append(" FROM DIT_PERSONA_").append(concatTipoPersona).append(" persona")
                .append(" inner join DIT_PATRON_SUJETO_OBLIGADO so on so.CVE_ID_PATRON_SUJETO_OBLIGADO = persona.CVE_ID_PERSONA_").append(concatTipoPersona)
                .append(" INNER JOIN MGPBDTU9X.DIT_PATRON_GENERAL PG ON PG.CVE_ID_PATRON_SUJETO_OBLIGADO = so.CVE_ID_PATRON_SUJETO_OBLIGADO ")
                .append(" INNER JOIN MGPBDTU9X.DIT_LLAVE_PATRON llave on llave.CVE_ID_PATRON_SUJETO_OBLIGADO = so.CVE_ID_PATRON_SUJETO_OBLIGADO ")
                .append(" where persona.rfc='").append(rfc).append("' and llave.CVE_ID_PERSONA_").append(concatTipoPersona).append(" is not null order BY so.FEC_REGISTRO_ALTA DESC ");

        Query query = this.getSession().createSQLQuery(queryPat.toString());
        List<Object[]> patrones = (List<Object[]>) query.list();
        LOGGER.debug("Patrones encontrados: {}", patrones.size());

        PatronRiesgosTrabajo patronEnc = new PatronRiesgosTrabajo();
        String nrp = (String) patrones.get(0)[0];
        patronEnc = findPatron(nrp, idDelegacion, idSubdelegacion);
        patronEnc.setNrp(nrp);

        return patronEnc;
    }

    @Override
    public int findTamanRiesgosxRps(Integer periodo, String rfc, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        periodo = periodo == null || periodo.intValue() == 0 ? null : periodo;
        List<Date> periodos = null;
        periodos = UtilRTT.obtenerPeriodoCorrespondiente(origen, periodo);
        List<RttRegistro> result = null;
        int tamanho = 0;

        List<Object[]> objectPatrones = findRpxRfc(rfc, 1, periodo, idDelegacion, idSubdelegacion, null, origen);
        if (objectPatrones.size() != 0) {
            List<String> patrones = UtilRTT.patronesObjToMod(objectPatrones);
            LOGGER.debug("Consultando riesgos de trabajo de acuerdo con los patrones encontrados");

            //Se realiza la consulta de RT
            Criteria criteria = this.getSession().createCriteria(RttRegistro.class);
            criteria.add(Restrictions.in("rttRegPatron", patrones));
            if (periodos != null) {
                criteria.add(Restrictions.between("rttFecFinAccidente", periodos.get(0), periodos.get(1)));
            }

            //Si es de internet filtramos por tipo de riesgo 1 y 3
            if (origen.equals(OrigenSolicitudEnum.INTERNET)) {
                //llenamos la lista con los tipos de riesgos
                List<Integer> tiposRiesgos = new ArrayList<Integer>();
                tiposRiesgos.add(1);
                tiposRiesgos.add(3);
                criteria.add(Restrictions.in("rttNumTipoRiesgo", tiposRiesgos));
            }

            criteria.addOrder(Order.asc("rttNumNss"));
            result = criteria.list();
            LOGGER.debug("Coincidencias:{}", result.size());

            //Convertimos los datos de la consulta a objetos RiesgosTrabajo
            if (result != null && !result.isEmpty()) {
                tamanho = result.size();
            } else {
                throw new RiesgosTrabajoException("No existen Riesgos de Trabajo");
            }
            LOGGER.debug("Se finaliza la consulta");
        }

        return tamanho;
    }

    @Override
    public boolean validateConectDB(){
        return this.getSession().isConnected();
    }

}