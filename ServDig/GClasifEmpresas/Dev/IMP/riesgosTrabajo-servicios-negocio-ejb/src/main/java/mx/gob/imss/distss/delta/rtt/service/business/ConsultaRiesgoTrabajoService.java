package mx.gob.imss.distss.delta.rtt.service.business;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.ReporteRiesgoTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgosPatron;
import mx.gob.imss.distss.delta.rtt.service.entity.ConsultaRiesgosTrabajoLocal;
import mx.gob.imss.distss.delta.rtt.service.entity.GenerarReportesRiesgosTrabajoLocal;
import mx.gob.imss.distss.delta.rtt.service.entity.GenerararSolicitudRiesgosTrabajoLocal;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultalRiesgoTrabajoServiceRemote;
import mx.gob.imss.distss.delta.rtt.service.util.UtilRTT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "consultaRTTServiceBusiness", mappedName = "consultaRTTServiceBusiness")
public class ConsultaRiesgoTrabajoService implements ConsultalRiesgoTrabajoServiceRemote {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConsultaRiesgoTrabajoService.class);

    @EJB
    private ConsultaRiesgosTrabajoLocal consultaRiesgosTrabajoLocal;

    @EJB
    private GenerarReportesRiesgosTrabajoLocal generarReportesRiesgosTrabajo;

    @EJB
    private GenerararSolicitudRiesgosTrabajoLocal GenerararSolicitudRiesgosTrabajo;

    @Override
    public List<RiesgoTrabajo> obtenerRiesgosTrabajoPatronalesPeriodo(PatronRiesgosTrabajo patron, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {

        LOGGER.debug("Buscamos los riesgos de trabajo");

        //Buscamos los iesgos de trabajo
        return consultaRiesgosTrabajoLocal.findRiesgoTrabajoRegPatronPeriodo(patron.getNrp(), patron.getInicioPeriodo(), patron.getFinPeriodo(), origen);
    }

    @Override
    public RiesgosPatron obtenerRiesgosTrabajoPatronalesXNSS(String nss, Date inicioPeriodo, Date finPeriodo, OrigenSolicitudEnum origen, Long idDelegacion, Long idSubdelegacion) throws RiesgosTrabajoException {

        LOGGER.debug("Buscamos los riesgos de trabajo por NSS");
        return consultaRiesgosTrabajoLocal.findRiesgoTrabajoRegPatronXNSS(nss, inicioPeriodo, finPeriodo, origen, idDelegacion, idSubdelegacion);
    }

    @Override
    public PatronRiesgosTrabajo buscarPatron(String nrp, Integer anioPeriodo, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {

        LOGGER.debug("Buscamos al patron");

        PatronRiesgosTrabajo patron;
        List<Date> periodo;

        //Obtenemos las fe has del periodo
        periodo = UtilRTT.obtenerPeriodoCorrespondiente(origen, anioPeriodo);

        //Buscamos al patron por el nrp
        patron = consultaRiesgosTrabajoLocal.findPatron(nrp, idDelegacion, idSubdelegacion);

        //Seteamos valores del patron
        patron.setNrp(nrp);
        patron.setInicioPeriodo(periodo.get(0));
        patron.setFinPeriodo(periodo.get(1));

        LOGGER.debug("Los datos del patron son: {}", patron);
        return patron;
    }

    @Override
    public PatronRiesgosTrabajo buscarPersona(String rfc, Integer anioPeriodo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        LOGGER.debug("Se busca a la persona");
        PatronRiesgosTrabajo patron = new PatronRiesgosTrabajo();
        List<Date> periodo;
        //Obtenemos las fe has del periodo
        periodo = UtilRTT.obtenerPeriodoCorrespondiente(origen, anioPeriodo);

        if(rfc.length() == 12)
            patron = consultaRiesgosTrabajoLocal.findPersona(rfc, 1);

        else if (rfc.length() == 13)
            patron = consultaRiesgosTrabajoLocal.findPersona(rfc, 2);

        //Seteamos valores del periodo
        patron.setInicioPeriodo(periodo.get(0));
        patron.setFinPeriodo(periodo.get(1));

        return patron;
    }

    @Override
    public byte[] generarDocumentoRiesgosTrabajo(PatronRiesgosTrabajo patron, TipoDescargaArchivo tipoArchivo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {

        LOGGER.debug("Generamos reporte");
        Solicitud solicitud = null;
        byte[] reporte = null;

        //obtenemos los riegos de trabajo
        List<RiesgoTrabajo> riesgosTrabajo;
        if (patron.getNss() == null) {
            riesgosTrabajo = obtenerRiesgosTrabajoPatronalesPeriodo(patron, origen);
        } else {
            riesgosTrabajo = obtenerRiesgosTrabajoPatronalesXNSS(patron.getNss(), patron.getInicioPeriodo(), patron.getFinPeriodo(), origen,
                    patron.getSubdelegacion().getDelegacion().getId(), patron.getSubdelegacion().getId()).getRiesgos();
        }


        switch (tipoArchivo) {
            case PDF:
                LOGGER.debug("generamos PDF");

                //Creamos la solicitud
                solicitud = GenerararSolicitudRiesgosTrabajo.crearSolicitudRiesgosTrabajoPatronales(patron, riesgosTrabajo, origen, tipoArchivo);

                //Generamos el PDF
                reporte = generarReportesRiesgosTrabajo.generarDocumentoPDF(solicitud, patron, riesgosTrabajo);
                break;

            case XLS:
                LOGGER.debug("Generamos Excel");
                //Creamos la solicitud
                solicitud = GenerararSolicitudRiesgosTrabajo.crearSolicitudRiesgosTrabajoPatronales(patron, riesgosTrabajo, origen, tipoArchivo);

                //Generamos el Excel
                if (patron.getNss() != null) {
                    reporte = generarReportesRiesgosTrabajo.generarDocumentoXLSxRFC(riesgosTrabajo, true);
                } else {
                    reporte = generarReportesRiesgosTrabajo.generarDocumentoXLS(riesgosTrabajo);
                }
                break;

            case XML:
                LOGGER.debug("Generamos XML");
                //Generamos el XML
                reporte = generarReportesRiesgosTrabajo.generarDocumentoXML(riesgosTrabajo);
                break;
        }

        LOGGER.debug("Enviamos reporte");
        return reporte;
    }

    @Override
    public byte[] generarDocumentoRiesgosTrabajoRfc(PatronRiesgosTrabajo patron, List<RiesgoTrabajo> riesgosTrabajo, String rfc, TipoDescargaArchivo tipoArchivo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {

        LOGGER.debug("Generamos reporte por RFC");
        Solicitud solicitud = null;
        byte[] reporte = null;

        switch (tipoArchivo) {
            case PDF:
                LOGGER.debug("generamos PDF");
                //Creamos la solicitud
                solicitud = GenerararSolicitudRiesgosTrabajo.crearSolicitudRTRFC(patron, riesgosTrabajo, origen, rfc, tipoArchivo);
                //Generamos el PDF
                reporte = generarReportesRiesgosTrabajo.generarDocumentoPDFxRFC(solicitud, patron, riesgosTrabajo, rfc);
                break;

            case XLS:
                LOGGER.debug("Generamos Excel por RFC");
                //Creamos la solicitud
                solicitud = GenerararSolicitudRiesgosTrabajo.crearSolicitudRTRFC(patron, riesgosTrabajo, origen, rfc, tipoArchivo);
                //Generamos el Excel
                reporte = generarReportesRiesgosTrabajo.generarDocumentoXLSxRFC(riesgosTrabajo, false);
                break;
        }


        LOGGER.debug("Enviamos reporte");
        return reporte;
    }

    /**
     * Validamos si el patron puede relizar una solicitud
     *
     * @param patron
     * @throws RiesgosTrabajoException
     */
    @Override
    public void validarSolicitud(PatronRiesgosTrabajo patron) throws RiesgosTrabajoException {
        LOGGER.debug("Validamos si puede hacer una solicitud");

        List<Solicitud> solicitudes;

        //Buscamos las solicitudes de la persona
        solicitudes = consultaRiesgosTrabajoLocal.findSolicitud(patron.getIdPersona(),
                TipoSolicitudEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS, EstadoSolicitudEnum.ATENDIDA,
                TipoTramiteEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS.getCodigo().longValue(), TipoDescargaArchivo.PDF);

        //Validamos las solicitudes
        if (solicitudes != null && !solicitudes.isEmpty()) {
            for (Solicitud solicitud : solicitudes) {
                Calendar fechaActual = Calendar.getInstance();
                Calendar fechaSolicitud = Calendar.getInstance();
                fechaSolicitud.setTime(solicitud.getFechaSolicitud());

                //Si trae la obeservacion null le seteamos un valor
                if (solicitud.getObservacion() == null) {
                    solicitud.setObservacion("SIN NRP");
                }

                //Validamos si ya tiene una solicitud este mes
                if (fechaActual.get(Calendar.MONTH) == fechaSolicitud.get(Calendar.MONTH) &&
                        fechaActual.get(Calendar.YEAR) == fechaSolicitud.get(Calendar.YEAR) &&
                        solicitud.getObservacion().equals(patron.getNrp())) {
                    LOGGER.debug("Ya cuenta con una solicitud este mes");
                    throw new RiesgosTrabajoException("No es posible generar el documento comprobante nuevamente. El sistema genera el documento comprobante solo una vez al mes.");
                }
            }
        }

        LOGGER.debug("Puede realizar la solicitud");
    }

    /**
     * Validamos si el patron puede relizar una solicitud
     *
     * @param rfc
     * @throws RiesgosTrabajoException
     */
    @Override
    public void validarSolicitudRfc(String rfc) throws RiesgosTrabajoException {
        LOGGER.debug("Validamos si puede hacer una solicitud");
        List<Solicitud> solicitudes;

        //Buscamos las solicitudes de la persona
        solicitudes = consultaRiesgosTrabajoLocal.findSolicitudesRFC(rfc,
                TipoSolicitudEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS, EstadoSolicitudEnum.ATENDIDA,
                TipoTramiteEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS.getCodigo().longValue(), TipoDescargaArchivo.PDF);

        //Validamos las solicitudes
        if (solicitudes != null && !solicitudes.isEmpty()) {
            for (Solicitud solicitud : solicitudes) {
                Calendar fechaActual = Calendar.getInstance();
                Calendar fechaSolicitud = Calendar.getInstance();
                fechaSolicitud.setTime(solicitud.getFechaSolicitud());

                //Si trae la obeservacion null le seteamos un valor
                if (solicitud.getObservacion() == null) {
                    solicitud.setObservacion("SIN NRP");
                }

                //Validamos si ya tiene una solicitud este mes
                if (fechaActual.get(Calendar.MONTH) == fechaSolicitud.get(Calendar.MONTH) &&
                        fechaActual.get(Calendar.YEAR) == fechaSolicitud.get(Calendar.YEAR) &&
                        solicitud.getObservacion().equals(rfc)) {
                    LOGGER.debug("Ya cuenta con una solicitud este mes");
                    throw new RiesgosTrabajoException("No es posible generar el documento comprobante nuevamente. El sistema genera el documento comprobante solo una vez al mes.");
                }
            }
        }

        LOGGER.debug("Puede realizar la solicitud");
    }

    @Override
    public List<Object[]> buscarPatronPorNombre(String nrs, OrigenSolicitudEnum origen, String tipo, Long idDelegacion, Long idSubdelegacion, Integer periodo)
            throws RiesgosTrabajoException {
        LOGGER.debug("Buscamos al patron por Nombre");

        List<Object[]> patrones;

        //Buscamos patrones por el nrs
        patrones = consultaRiesgosTrabajoLocal.findPatronByNombre(nrs, tipo, idDelegacion, idSubdelegacion, periodo, origen);

        LOGGER.debug("Patrones encontrados: {}", patrones.size());
        return patrones;
    }

    @Override
    public PatronRiesgosTrabajo buscarPatronPorCveSujeto(long cve, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        LOGGER.debug("Buscamos al patron por cvesujeto");

        PatronRiesgosTrabajo patron;
        List<Date> periodo;

        //Obtenemos las fe has del periodo
        periodo = UtilRTT.obtenerPeriodoCorrespondiente(origen, 0);

        //Buscamos al patron por el nrp
        patron = consultaRiesgosTrabajoLocal.findPatronBySujeto(cve);

        //Seteamos valores del patron
        patron.setInicioPeriodo(periodo.get(0));
        patron.setFinPeriodo(periodo.get(1));

        LOGGER.debug("Los datos del patron son: {}", patron);
        return patron;
    }

    @Override
    public byte[] aceptarTerminosCondiciones(PatronRiesgosTrabajo patron) throws RiesgosTrabajoException {
        LOGGER.debug("TYC generamos Sol");
        Solicitud solicitud = GenerararSolicitudRiesgosTrabajo.crearSolicitudTerminosCondiciones(patron, TipoDescargaArchivo.PDF);
        LOGGER.debug("TYC generamos PDF");
        generarReportesRiesgosTrabajo.generarTerminosCondicionesPDF(solicitud, patron, "0");
        LOGGER.debug("Enviamos reporte");
        return null;
    }

    @Override
    public byte[] aceptarTerminosCondicionesRfc(PatronRiesgosTrabajo patron, String rfc) throws RiesgosTrabajoException {
        LOGGER.debug("TYC generamos Sol");
        Solicitud solicitud = GenerararSolicitudRiesgosTrabajo.crearSolicitudTerminosCondicionesRfc(patron, rfc, TipoDescargaArchivo.PDF);
        LOGGER.debug("TYC generamos PDF");
        generarReportesRiesgosTrabajo.generarTerminosCondicionesPDF(solicitud, patron, rfc);
        LOGGER.debug("Enviamos reporte");
        return null;
    }

    @Override
    public boolean validarSolicitudMes(PatronRiesgosTrabajo patron, String rfc) throws RiesgosTrabajoException {
        boolean respuesta = true;
        LOGGER.debug("Se valida si puede hacer una solicitud");
        List<Solicitud> solicitudes;

        if (rfc != "0") {
            //Buscamos las solicitudes de la persona por RFC
            solicitudes = consultaRiesgosTrabajoLocal.findSolicitudesRFC(rfc,
                    TipoSolicitudEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS, EstadoSolicitudEnum.ATENDIDA,
                    TipoTramiteEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS.getCodigo().longValue(), TipoDescargaArchivo.PDF);

            //Validamos las solicitudes
            if (solicitudes != null && !solicitudes.isEmpty()) {
                for (Solicitud solicitud : solicitudes) {
                    Calendar fechaActual = Calendar.getInstance();
                    Calendar fechaSolicitud = Calendar.getInstance();
                    fechaSolicitud.setTime(solicitud.getFechaSolicitud());

                    //Si trae la obeservacion null le seteamos un valor
                    if (solicitud.getObservacion() == null) {
                        solicitud.setObservacion("SIN NRP");
                    }

                    //Validamos si ya tiene una solicitud este mes
                    if (fechaActual.get(Calendar.MONTH) == fechaSolicitud.get(Calendar.MONTH) &&
                            fechaActual.get(Calendar.YEAR) == fechaSolicitud.get(Calendar.YEAR) &&
                            solicitud.getObservacion().equals(rfc)) {
                        LOGGER.debug("Ya cuenta con una solicitud este mes");
                        respuesta = false;
                    }
                }
            }
        } else {
            //Buscamos las solicitudes de la persona por NRP
            solicitudes = consultaRiesgosTrabajoLocal.findSolicitud(patron.getIdPersona(),
                    TipoSolicitudEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS, EstadoSolicitudEnum.ATENDIDA,
                    TipoTramiteEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS.getCodigo().longValue(), TipoDescargaArchivo.PDF);

            //Validamos las solicitudes
            if (solicitudes != null && !solicitudes.isEmpty()) {
                for (Solicitud solicitud : solicitudes) {
                    Calendar fechaActual = Calendar.getInstance();
                    Calendar fechaSolicitud = Calendar.getInstance();
                    fechaSolicitud.setTime(solicitud.getFechaSolicitud());

                    //Si trae la obeservacion null le seteamos un valor
                    if (solicitud.getObservacion() == null) {
                        solicitud.setObservacion("SIN NRP");
                    }

                    //Validamos si ya tiene una solicitud este mes
                    if (fechaActual.get(Calendar.MONTH) == fechaSolicitud.get(Calendar.MONTH) &&
                            fechaActual.get(Calendar.YEAR) == fechaSolicitud.get(Calendar.YEAR) &&
                            solicitud.getObservacion().equals(patron.getNrp())) {
                        LOGGER.debug("Ya cuenta con una solicitud este mes");
                        respuesta = false;
                    }
                }
            }
        }

        LOGGER.debug("Puede realizar la solicitud");
        return respuesta;
    }

    @Override
    public boolean validarTerminosCondiciones(PatronRiesgosTrabajo patron) throws RiesgosTrabajoException {
        List<Solicitud> solicitudes;
        solicitudes = consultaRiesgosTrabajoLocal.findSolicitud(patron.getIdPersona(),
                TipoSolicitudEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS, EstadoSolicitudEnum.ATENDIDA,
                TipoTramiteEnum.ACEPTACION_TERMINOS_CONDICIONES.getCodigo().longValue(), TipoDescargaArchivo.PDF);
        if (solicitudes != null && !solicitudes.isEmpty()) {
            for (Solicitud solicitud : solicitudes) {
                LOGGER.debug("ID Solicitud {}", solicitud.getSolicitudId());
                if (solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
                    for (Tramite tramite : solicitud.getTramites()) {
                        LOGGER.debug("ID Tramite {}", tramite.getTramiteId());
                        if (tramite.getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.ACTIVO.getCodigo())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean validarTerminosCondicionesRfc(String rfc) throws RiesgosTrabajoException {
        List<Solicitud> solicitudes;
        solicitudes = consultaRiesgosTrabajoLocal.findSolicitudesRFC(rfc,
                TipoSolicitudEnum.CONSULTA_RIESGOS_TRABAJO_TERMINADOS, EstadoSolicitudEnum.ATENDIDA,
                TipoTramiteEnum.ACEPTACION_TERMINOS_CONDICIONES.getCodigo().longValue(), TipoDescargaArchivo.PDF);
        if (solicitudes != null && !solicitudes.isEmpty()) {
            for (Solicitud solicitud : solicitudes) {
                LOGGER.debug("ID Solicitud {}", solicitud.getSolicitudId());
                if (solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
                    for (Tramite tramite : solicitud.getTramites()) {
                        LOGGER.debug("ID Tramite {}", tramite.getTramiteId());
                        if (tramite.getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.ACTIVO.getCodigo())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<Object[]> buscarPatronPorRFC(String rfc, Long idDelegacion, Long idSubdelegacion, Integer periodo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        LOGGER.debug("Buscamos al patron por RFC");

        List<Object[]> patrones;

        //Buscamos patrones por el rfc
        patrones = consultaRiesgosTrabajoLocal.findPatronByRFC(rfc, idDelegacion, idSubdelegacion, periodo, origen);

        return patrones;
    }

    @Override
    public List<Object[]> buscarRpPorRFC(String rfc, Long idDelegacion, Long idSubdelegacion, Integer periodo, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        LOGGER.debug("Buscamos al patron por RFC");
        List<Object[]> regPatrones;
        //Buscamos patrones por el rfc
        regPatrones = consultaRiesgosTrabajoLocal.findRpPorRFC(rfc, idDelegacion, idSubdelegacion, periodo, origen);

        return regPatrones;
    }

    @Override
    public List<RiesgoTrabajo> buscarRtXLisRp(Integer periodo, String rfc, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        LOGGER.debug("Buscamos lod registros por lista de patrones.");
        List<RiesgoTrabajo> regPatrones;
        //Buscamos patrones por el rfc
        regPatrones = consultaRiesgosTrabajoLocal.findRtXLisRp(periodo, rfc, idDelegacion, idSubdelegacion, origen);

        return regPatrones;
    }

    @Override
    public ReporteRiesgoTrabajo buscarReportesXrfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        ReporteRiesgoTrabajo reportes = consultaRiesgosTrabajoLocal.findReportesXrfc(rfc, origen);
        return reportes;
    }

    @Override
    public ReporteRiesgoTrabajo crearReporteRfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        ReporteRiesgoTrabajo reportes = consultaRiesgosTrabajoLocal.createReporteRfc(rfc, origen);
        return reportes;
    }

    @Override
    public ReporteRiesgoTrabajo actualizaReportesRfc(ReporteRiesgoTrabajo reporte, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        LOGGER.debug("Se Actualiza el reporte con los datos del reporte completo");
        ReporteRiesgoTrabajo reportes = consultaRiesgosTrabajoLocal.updateReportesRfc(reporte, origen);
        return reportes;
    }

    @Override
    public int bajaReporteRfc(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        int reportes = consultaRiesgosTrabajoLocal.downReporteRfc(rfc, origen);
        return reportes;
    }

    @Override
    public int encuentraRFC(String rfc) throws RiesgosTrabajoException {
        int valid = consultaRiesgosTrabajoLocal.findExiRfc(rfc);
        return valid;
    }

    @Override
    public int encuentraNRP(String nrp) throws RiesgosTrabajoException {
        int valid = consultaRiesgosTrabajoLocal.findExiNrp(nrp);
        return valid;
    }

    @Override
    public List<Delegacion> findDelegacionesActivas() {
        return consultaRiesgosTrabajoLocal.findDelegacionesActivas();
    }

    @Override
    public List<Subdelegacion> findSubDelegacionesActivas(Long idDelegacion) {
        return consultaRiesgosTrabajoLocal.findSubDelegacionesActivas(idDelegacion);
    }

    @Override
    public Subdelegacion getSubdelegacionUsuario(Long idSubdelegacion) {
        return consultaRiesgosTrabajoLocal.getSubdelegacionUsuario(idSubdelegacion);
    }

    @Override
    public Delegacion getDelegacionUsuario(Long idDelegacion) {
        return consultaRiesgosTrabajoLocal.getDelegacionUsuario(idDelegacion);
    }

    @Override
    public PatronRiesgosTrabajo findPatron(String regPatron, Long idDelegacion,
                                           Long idSubdelegacion) throws RiesgosTrabajoException {

        return consultaRiesgosTrabajoLocal.findPatron(regPatron, idDelegacion, idSubdelegacion);
    }

    @Override
    public PatronRiesgosTrabajo findPersona(String rfc, int tipoPersona) throws RiesgosTrabajoException {

        return consultaRiesgosTrabajoLocal.findPersona(rfc, tipoPersona);
    }

    @Override
    public String buscarPatronGral(String regPatron) throws RiesgosTrabajoException {
        return consultaRiesgosTrabajoLocal.findPatronGeneral(regPatron);
    }

    @Override
    public ReporteRiesgoTrabajo buscarReportesXrfcEstados(String rfc, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        ReporteRiesgoTrabajo reportes = consultaRiesgosTrabajoLocal.findReportesXrfc(rfc, origen);
        return reportes;
    }

    @Override
    public PatronRiesgosTrabajo buscarPatronXRfc(String rfc, Integer anioPeriodo, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        LOGGER.debug("Buscamos al patron");
        PatronRiesgosTrabajo patron;
        List<Date> periodo;

        //Obtenemos las fe has del periodo
        periodo = UtilRTT.obtenerPeriodoCorrespondiente(origen, anioPeriodo);

        //Buscamos al patron por el nrp
        patron = consultaRiesgosTrabajoLocal.findPatronXRfc(rfc, idDelegacion, idSubdelegacion);

        //Seteamos valores del patron
        patron.setInicioPeriodo(periodo.get(0));
        patron.setFinPeriodo(periodo.get(1));
        LOGGER.debug("Los datos del patron son: {}", patron);

        return patron;
    }

    @Override
    public int actualizaDocumentOnlyRfc(ReporteRiesgoTrabajo reporte, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        LOGGER.debug("Se Actualiza el reporte con los datos del reporte completo");
        return consultaRiesgosTrabajoLocal.updateDocumentOnlyRfc(reporte, origen);
    }

    @Override
    public ReporteRiesgoTrabajo descargaDocumentOnlyRfc(String rfc, int posicion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        LOGGER.debug("Se Actualiza el reporte con los datos del reporte completo");
        return consultaRiesgosTrabajoLocal.downloadDocumentOnlyRfc(rfc, posicion, origen);
    }


    @Override
    public int buscarTamanListaRtxRp(Integer periodo, String rfc, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        LOGGER.debug("Se busca primero el tamano de la lista de RT");
        return consultaRiesgosTrabajoLocal.findTamanRiesgosxRps(periodo, rfc, idDelegacion, idSubdelegacion, origen);
    }


    @Override
    public int guardarxlsGeneradoBack(Integer periodo, String rfc, Long idDelegacion, Long idSubdelegacion, OrigenSolicitudEnum origen, PatronRiesgosTrabajo patron) throws RiesgosTrabajoException {
        LOGGER.debug("Buscamos al patron por RFC");
        List<RiesgoTrabajo> regPatrones;
        ReporteRiesgoTrabajo reportObtain = new ReporteRiesgoTrabajo();
        int ac = 0;

        regPatrones = consultaRiesgosTrabajoLocal.findRtXLisRp(periodo, rfc, idDelegacion, idSubdelegacion, origen);

        byte[] excelObtain = generarDocumentoRiesgosTrabajoRfc(patron, regPatrones, rfc, TipoDescargaArchivo.XLS, origen);

        if (excelObtain != null) {
            reportObtain.setDocumento(excelObtain);
            int paquetes = 4;
            int valorPaquete = (int) Math.ceil((float) reportObtain.getDocumento().length / paquetes);
            int i = 0;
            ReporteRiesgoTrabajo docuEnv = new ReporteRiesgoTrabajo();
            docuEnv.setRfc(rfc);
            for (i = 0; i < (paquetes - 1); i++) {
                byte[] aux = Arrays.copyOfRange(reportObtain.getDocumento(), i * valorPaquete, (i + 1) * valorPaquete);
                //enviar a guardar a la DB
                docuEnv.setDocumento(aux);
                ac += actualizaDocumentOnlyRfc(docuEnv, origen);
            }

            byte[] aux2 = Arrays.copyOfRange(reportObtain.getDocumento(), i * valorPaquete, reportObtain.getDocumento().length);
            //enviar a guardar a la DB
            docuEnv.setDocumento(aux2);
            ac += actualizaDocumentOnlyRfc(docuEnv, origen);
        }

        return ac;
    }

    @Override
    public boolean validaConexDB(){
        return consultaRiesgosTrabajoLocal.validateConectDB();
    }
}
