package mx.gob.imss.cit.gestion.solicitud.flujo.service.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.EstadoInstanciaEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.EstadoTareaUsuarioEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Asignacion;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Instancia;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Tarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaTransicion;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaUsuario;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TransicionTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoLocal;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionSolicitudCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "flujoTrabajoEntity", mappedName = "flujoTrabajoEntity")
public class FlujoTrabajoEntity implements FlujoTrabajoLocal {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat FORMATTER_HOUR = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final Logger LOGGERBPM = LoggerFactory.getLogger(FlujoTrabajoEntity.class);
    private static final int DIAS_MAXIMOS_ESPERA = 40;
    private static final String FECHA_INICIO_SPIC = "18/12/2023";

    private static final boolean isStage = false;
    private static final String DB_USER = ""; //"MGPBDTU9X.";
    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;


    /**
     * Metodo para obtener tarea por id
     */
    @SuppressWarnings("unchecked")
    public Tarea obtenerTareaPorId(final Long id) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT t FROM Tarea t ");
        sql.append("WHERE t.idTarea =:id ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("id", id);
        return (Tarea) query.getSingleResult();
    }


    /**
     * Metodo para obtener tarea inicial
     */
    @SuppressWarnings("unchecked")
    public List<Tarea> obtenerTareaInicial(final Long bp) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT p FROM Tarea p ");
        sql.append("WHERE p.inicial =:inicial and  p.proceso.bp =:bp ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("inicial", true);
        query.setParameter("bp", bp);
        return query.getResultList();
    }

    /**
     * Metodo para guardar instancia
     */
    public Instancia guardarInstancia(Instancia instancia) {
        entityManager.persist(instancia);
        return instancia;
    }

    /**
     * Metodo para actualizar instancia
     */
    public Instancia actualizarInstancia(Instancia instancia) {
        entityManager.merge(instancia);
        return instancia;
    }

    /**
     * Metodo para guardar una lista de asignaciones
     */
    public void guardarAsignaciones(List<Asignacion> asignaciones) {
        for (Asignacion asignacion : asignaciones) {
            entityManager.persist(asignacion);
        }
    }

    /**
     * Metodo para guardar una asignacion
     */
    public void guardarAsignacion(Asignacion asignacion) {
        entityManager.persist(asignacion);
    }

    /**
     * Metodo para guardar una tarea de usuario
     */
    public TareaUsuario guardarTareaUsuario(TareaUsuario tareaUsuario) {
        entityManager.merge(tareaUsuario);
        return tareaUsuario;
    }

    /**
     * Metodo para guardar una lista de tareas de usuario
     */
    public void guardarTareasUsuario(List<TareaUsuario> tareasUsuario) {
        for (TareaUsuario tareaUsuario : tareasUsuario) {
            entityManager.persist(tareaUsuario);
        }
    }

    /**
     * Metodo para buscar asignacions por llave primaria
     */
    public Asignacion buscarAsignacionById(final Long idAsignacion) {
        return entityManager.find(Asignacion.class, idAsignacion);
    }

    /**
     * Metodo para buscar asignacions por Instancia y Participante
     */
    public Asignacion buscarAsignacionByInsPart(final Asignacion asignacion) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT a FROM Asignacion a ");
        sql.append("WHERE a.instancia.bpId =:bpId and  a.participante.idParticipante =:idParticipante ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("bpId", asignacion.getInstancia().getBpId());
        query.setParameter("idParticipante", asignacion.getParticipante().getIdParticipante());
        return (query.getResultList() != null && query.getResultList().size() > 0)
                ? (Asignacion) query.getSingleResult() : null;
    }

    public Asignacion buscarAsignacion(final Asignacion asignacion) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT a FROM Asignacion a ");
        sql.append("WHERE a.instancia.bpId =:bpId and  a.participante.idParticipante =:idParticipante ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("bpId", asignacion.getInstancia().getBpId());
        query.setParameter("idParticipante", asignacion.getParticipante().getIdParticipante());
        List<Asignacion> asignaciones = query.getResultList();
        return (asignaciones != null && asignaciones.size() > 0)
                ? asignaciones.get(0) : null;
    }

    /**
     * Metodo para buscar asignacions por Instancia y Rol
     */
    public Asignacion buscarAsignacionByInsRol(final Asignacion asignacion) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT a FROM Asignacion a ");
        sql.append("WHERE a.instancia.bpId =:bpId and  a.participante.nombre =:nombre ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("bpId", asignacion.getInstancia().getBpId());
        query.setParameter("nombre", asignacion.getParticipante().getNombre());
        return (query.getResultList() != null && query.getResultList().size() > 0)
                ? (Asignacion) query.getSingleResult() : null;
    }

    /**
     * Metodo para buscar una tarea de usuario por Id de la tarea
     */
    public TareaUsuario buscarTareaUsuarioById(final Long idTareaUsuario) {
        return entityManager.find(TareaUsuario.class, idTareaUsuario);
    }

    /**
     * Metodo para buscar una instancia por Id de la instancia
     */
    public Instancia buscarInstanciaById(final Long bpId) {
        return entityManager.find(Instancia.class, bpId);
    }

    /**
     * Metodo para buscar una lista de tareas de transicion
     */
    @SuppressWarnings("unchecked")
    public List<TareaTransicion> buscarTareaTransicion(final Long idTarea, final String tipoTransicion) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT t FROM TareaTransicion t ");
        sql.append("WHERE t.tareaTransicionPK.idTarea =:idTarea ");
        sql.append("AND t.transicion.tipoTransicion =:tipoTransicion ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idTarea", idTarea);
        query.setParameter("tipoTransicion", tipoTransicion);
        return query.getResultList();
    }

    /**
     * Metodo para buscar una lista de tareas activas
     */
    @SuppressWarnings("unchecked")
    public List<TareaUsuario> buscarTareasActivasByBpId(final Long bpId, List<Long> estadosTareaUsuario,
                                                        boolean subProceso) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT t FROM TareaUsuario t ");
        sql.append("WHERE t.instancia.bpId =:bpId ");
        sql.append("AND t.estadoTarea.idEstadoTarea in (:estadosTareaUsuario) ");
        sql.append("AND t.tarea.subProceso = :subProceso ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("bpId", bpId);
        query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
        query.setParameter("subProceso", subProceso);
        return query.getResultList();
    }

    /**
     * Metodo para buscar una lista de tareas de transicion
     */
    @SuppressWarnings("unchecked")
    public List<TransicionTarea> buscarTransicionTarea(final Long idTransicion) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT t FROM TransicionTarea t ");
        sql.append("WHERE t.transicionTareaPK.idTransicion =:idTransicion ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idTransicion", idTransicion);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public DataPage obtenerBitacoraEstatus(DataPage dataPage, final Integer idTramite, final List<Long> estadosTareaUsuario) {
        StringBuffer sql = new StringBuffer();
        long maxResults = obtenerTotalEstatus(idTramite);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }
        if (maxResults > 0) {
            sql.append("SELECT t FROM TareaUsuario t ");
            sql.append("WHERE t.instancia.idTramite = :idTramite ");
            sql.append("and t.estadoTarea.idEstadoTarea in (:estadosTareaUsuario) ");
            sql.append("order by t.idTareaUsuario asc  ");
            Query query = entityManager.createQuery(sql.toString());
            query.setParameter("idTramite", idTramite);
            query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
            List<TareaUsuario> list = new ArrayList<TareaUsuario>();
            list = (List<TareaUsuario>) query.getResultList();

            for (TareaUsuario t : list) {
                t.getEstadoTarea().getDescripcion();
            }

            dataPage.setData(list);
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);

        return dataPage;
    }

    private long obtenerTotalEstatus(final Integer idTramite) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT count(t.idTareaUsuario) FROM TareaUsuario t ");
        sql.append("WHERE t.instancia.idTramite = :idTramite ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idTramite", idTramite);
        return Long.parseLong(query.getSingleResult().toString());
    }

    /**
     * Metodo para obtener una lista de tareas por usuario
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerTareasPorUsuario(DataPage dataPage, final String usuario,
                                            final List<Long> estadosTareaUsuario, List<Long> idsProcesos) {
        List<HashMap<String, String>> listFilter = (List<HashMap<String, String>>) dataPage.getData();

        String folio = listFilter.get(0).get("filtroFolio");
        String nss = listFilter.get(0).get("filtroNss");
        String fecha = listFilter.get(0).get("filtroFecha");
        String estado = listFilter.get(0).get("filtroEstado");

        StringBuffer sql = new StringBuffer(50);
        long maxResults = obtenerTotalTareasPorUsuarioNativo(usuario, estadosTareaUsuario, folio, nss, fecha, estado, 0L, idsProcesos);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }

        if (maxResults > 0) {

            sql.append(" select * from ( ");
            sql.append(" select tarUsu.* from DIT_TAREA_USUARIO tarusu ");
            sql.append(" inner join DIT_INSTANCIA inst on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA ");
            sql.append(" where (tarusu.CVE_ID_EDO_TAREA = :estadoA or tarusu.CVE_ID_EDO_TAREA = :estadoB ) ");
            sql.append(" and inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia and tarusu.CVE_USUARIO= :usuario ");

            if (StringUtils.isNotBlank(folio)) {
                sql.append("AND inst.DES_BDOC_INSTANCIA like :folio ");
            }
            if (StringUtils.isNotBlank(nss)) {
                sql.append("AND inst.DES_BDOC_INSTANCIA like :nss ");
            }
            if (StringUtils.isNotBlank(estado)) {
                sql.append(generarQueryEstadoUsuario(estado));
            }

            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                sql.append(agregarQueryList("inst.cve_id_proceso", idsProcesos.size()));
            }

            sql.append(" union ");

            sql.append(" select tarUsu.* from DIT_TAREA_USUARIO tarusu ");
            sql.append(" inner join DIT_INSTANCIA inst on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA ");
            sql.append(" where tarusu.CVE_ID_EDO_TAREA  = :estadoC ");
            sql.append(" and inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia and tarusu.CVE_USUARIO= :usuario ");

            if (StringUtils.isNotBlank(folio)) {
                sql.append(" AND inst.DES_BDOC_INSTANCIA like :folio ");
            }
            if (StringUtils.isNotBlank(nss)) {
                sql.append(" AND inst.DES_BDOC_INSTANCIA like :nss ");
            }
            if (StringUtils.isNotBlank(estado)) {
                sql.append(generarQueryEstadoUsuario(estado));
            }
            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                sql.append(agregarQueryList("inst.cve_id_proceso", idsProcesos.size()));
            }
            sql.append(" and tarusu.CVE_ID_INSTANCIA not in (select tarusu.CVE_ID_INSTANCIA from DIT_TAREA_USUARIO tarusu ");
            sql.append(" inner join DIT_INSTANCIA inst on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA ");
            sql.append(" where (tarusu.CVE_ID_EDO_TAREA = :estadoA or tarusu.CVE_ID_EDO_TAREA = :estadoB ) ");
            sql.append(" and inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia and tarusu.CVE_USUARIO= :usuario ");
            if (StringUtils.isNotBlank(folio)) {
                sql.append(" AND inst.DES_BDOC_INSTANCIA like :folio ");
            }
            if (StringUtils.isNotBlank(nss)) {
                sql.append(" AND inst.DES_BDOC_INSTANCIA like :nss ");
            }
            if (StringUtils.isNotBlank(estado)) {
                sql.append(generarQueryEstadoUsuario(estado));
            }
            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                sql.append(agregarQueryList("inst.cve_id_proceso", idsProcesos.size()));
            }
            sql.append(" ) )");
            sql.append(" order by FEC_REGISTRO_ACTUALIZADO desc  ");

            System.out.println("Query : " + sql.toString());
            Query query = entityManager.createNativeQuery(sql.toString(), TareaUsuario.class);
            query.setParameter("estadoA", EstadoTareaUsuarioEnum.ACTIVA.getClave());
            query.setParameter("estadoB", EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
            query.setParameter("estadoC", EstadoTareaUsuarioEnum.COMPLETADA.getClave());
            query.setParameter("usuario", usuario.trim());
            query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.ACTIVA.getClave());
            if (StringUtils.isNotBlank(folio)) {
                query.setParameter("folio", "%folio>" + folio + "%");
            }
            if (StringUtils.isNotBlank(nss)) {
                query.setParameter("nss", "%data>{%nssInvolucrados%" + nss + "%");
            }
            if (StringUtils.isNotBlank(estado)) {
                asignarParametroUsuario(query, estado);
            }
            agregarParametrosList(query, idsProcesos);
            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    /**
     * Metodo para obtener una lista de tareas para CDA
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage consultaTareasCDA(DataPage dataPage, String usuario, String subdelegacion, Long idsProceso) {
        List<HashMap<String, String>> listFilter = (List<HashMap<String, String>>) dataPage.getData();
        long maxResults = 0;

        String folio = listFilter.get(0).get("filtroFolio");
        String nss = listFilter.get(0).get("filtroNss");
        String fecha = listFilter.get(0).get("filtroFecha");
        String estado = listFilter.get(0).get("filtroEstado");
        //Filtros adicionales CDA
        String origen = listFilter.get(0).get("filtroOrigen");
        String responsable = listFilter.get(0).get("filtroResponsable");
        String autorizador = listFilter.get(0).get("filtroAutorizo");
        String curp = listFilter.get(0).get("filtroCurp");
        String tipoTramite = listFilter.get(0).get("filtroTramite");
        String fechaActualizacion = listFilter.get(0).get("filtroFechaActualizacion");
        String fechaSolicitud = listFilter.get(0).get("filtroFechaSolicitud");
        String vencido = listFilter.get(0).get("filtroVencido");
        String asignadas = listFilter.get(0).get("filtroUsuario");
        StringBuffer sql = new StringBuffer(50);

        if (StringUtils.isBlank(asignadas)) {
            usuario = null;
        } else {
            subdelegacion = null;
        }

        maxResults = obtenerTotalTareasNativoCDA(usuario, subdelegacion, folio, nss, estado, origen, responsable, autorizador, curp, tipoTramite, fechaActualizacion, fechaSolicitud, vencido, 0L, idsProceso);

        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }

        if (maxResults > 0) {

            //SI CONSULTA POR NSS, OBTIENE EL FOLIO PARA OBTENER TODOS LOS TRAMITES
            if (StringUtils.isNotBlank(nss)) {
                folio = obtenerFolioParaBusquedaPorNss(usuario, subdelegacion, nss, false, idsProceso);
                nss = StringUtils.EMPTY;
            }

            sql.append(" select distinct sol.* from DIT_SOLICITUD sol");
            sql.append(" inner join DIT_TRAMITE tra on sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD");
            sql.append(" inner join DIT_INSTANCIA inst on tra.CVE_ID_TRAMITE = inst.CVE_ID_TRAMITE");
            sql.append(" inner join DIT_TAREA_USUARIO tarusu on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA");
            if (StringUtils.isNotBlank(responsable) || StringUtils.isNotBlank(autorizador)) {
                sql.append(" inner join DIT_INST_PARTICIP part on part.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA");
            }
            sql.append(" inner join DIT_CORRECCION_DATOS_ASEG corr on corr.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE");
            sql.append(" where (tarusu.CVE_ID_EDO_TAREA = :estadoA or tarusu.CVE_ID_EDO_TAREA = :estadoB  or tarusu.CVE_ID_EDO_TAREA = :estadoC) ");
            sql.append(" AND inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia");
            sql.append(" AND sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitud");
            sql.append(" AND inst.cve_id_proceso = :idProceso");

            if (StringUtils.isNotBlank(usuario)) {
                sql.append(" and tarusu.CVE_USUARIO= :usuario ");
            } else {
                if (StringUtils.isNotBlank(subdelegacion)) {
                    sql.append(" AND inst.DES_BDOC_INSTANCIA like :subdelegacion ");
                }
            }
            if (StringUtils.isNotBlank(estado)) {
                sql.append(generarQueryEstadoUsuario(estado));
            }

            if (StringUtils.isNotBlank(folio)) {
                sql.append(" AND sol.REF_FOLIO = :folio");
            }
            if (StringUtils.isNotBlank(origen)) {
                sql.append(" AND sol.CVE_ID_ORIGEN_SOLICITUD = :idOrigen");
            }
            if (StringUtils.isNotBlank(fechaActualizacion)) {
                sql.append(" AND( sol.FEC_REGISTRO_ACTUALIZADO BETWEEN TO_DATE(:fechaActualizacionInicial,'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaActualizacionFinal,'dd/MM/YYYY HH24:mi:ss') )");
            }
            if (StringUtils.isNotBlank(fechaSolicitud)) {
                sql.append(" AND( sol.FEC_SOLICITUD BETWEEN TO_DATE(:fechaSolicitudInicial,'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaSolicitudFinal,'dd/MM/YYYY HH24:mi:ss') )");
            }
            if (StringUtils.isNotBlank(responsable)) {
                sql.append(" AND part.CVE_USUARIO = :responsable");
            }
            if (StringUtils.isNotBlank(autorizador)) {
                sql.append(" AND part.CVE_USUARIO = :autorizador");
            }
            if (StringUtils.isNotBlank(vencido)) {
                sql.append(" AND( tra.CVE_ID_ESTADO_TRAMITE = :vencido");
            }

            if (StringUtils.isNotBlank(curp)) {
                sql.append(" AND corr.REF_CURP = :curp");
            }
            if (StringUtils.isNotBlank(tipoTramite)) {
                sql.append(" AND tra.CVE_ID_TIPO_TRAMITE = :tipoTramite");
            }
            sql.append(" order by sol.CVE_ID_SOLICITUD desc  ");

            System.out.println("Query : " + sql.toString());
            Query query = entityManager.createNativeQuery(sql.toString(), DitSolicitud.class);
            query.setParameter("estadoA", EstadoTareaUsuarioEnum.ACTIVA.getClave());
            query.setParameter("estadoB", EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
            query.setParameter("estadoC", EstadoTareaUsuarioEnum.COMPLETADA.getClave());
            query.setParameter("estadoSolicitud", EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
            query.setParameter("idProceso", idsProceso);
            if (StringUtils.isNotBlank(usuario)) {
                query.setParameter("usuario", usuario.trim());
            }
            query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.ACTIVA.getClave());
            if (StringUtils.isNotBlank(estado)) {
                asignarParametroUsuario(query, estado);
            }
            //PARAMETROS ADICIONALES de CDA
            if (StringUtils.isNotBlank(folio)) {
                query.setParameter("folio", folio);
            }

            if (StringUtils.isNotBlank(origen)) {
                if (origen.equals("INTERNET")) {
                    query.setParameter("idOrigen", OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
                } else {
                    query.setParameter("idOrigen", OrigenSolicitudEnum.getByDesc(origen).getId());
                }
            }
            if (StringUtils.isNotBlank(responsable)) {
                query.setParameter("responsable", responsable);
            }
            if (StringUtils.isNotBlank(autorizador)) {
                query.setParameter("autorizador", autorizador);
            }
            if (StringUtils.isNotBlank(curp)) {
                query.setParameter("curp", curp);
            }
            if (StringUtils.isNotBlank(tipoTramite)) {
                query.setParameter("tipoTramite", TipoRegularizacionSolicitudCDAEnum.fromDesc(tipoTramite).getIdTipoTramite());
            }
            if (StringUtils.isNotBlank(fechaActualizacion)) {
                query.setParameter("fechaActualizacionInicial", fechaActualizacion + " 00:00:00");
                query.setParameter("fechaActualizacionFinal", fechaActualizacion + " 23:59:59");
            }
            if (StringUtils.isNotBlank(fechaSolicitud)) {
                query.setParameter("fechaSolicitudInicial", fechaSolicitud + " 00:00:00");
                query.setParameter("fechaSolicitudFinal", fechaSolicitud + " 23:59:59");
            }
            if (StringUtils.isNotBlank(vencido)) {
                query.setParameter("vencido", EstadoTramiteEnum.VENCIDA.getCodigo());
            }
            if (StringUtils.isNotBlank(subdelegacion)) {
                query.setParameter("subdelegacion", "%data>{%subdelegacion%" + subdelegacion + "%");
            }

            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            List<DitSolicitud> ditSolicitudes = query.getResultList();
            List<String> solicitudes = new ArrayList<String>();
            for (DitSolicitud solicitud : ditSolicitudes) {
                solicitudes.add(Long.toString(solicitud.getCveIdSolicitud()));
            }

            System.out.println("Solicitudes " + solicitudes.toString());

            //CONSULTA LAS TAREAS POR SOLICITUD

            sql.delete(0, sql.length());
            sql.append(" select * from (");
            sql.append(" select tarusu.* from DIT_TAREA_USUARIO tarusu ");
            sql.append(" inner join DIT_INSTANCIA inst on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA ");
            sql.append(" inner join DIT_TRAMITE tra on inst.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE ");
            sql.append(" inner join DIT_SOLICITUD sol on sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD ");
            sql.append(" where (tarusu.CVE_ID_EDO_TAREA = :estadoA or tarusu.CVE_ID_EDO_TAREA = :estadoB) ");
            sql.append(" and inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia ");
            sql.append(" AND sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitud");
            sql.append(" AND inst.cve_id_proceso = :idProceso ");
            sql.append(agregarQueryList("sol.CVE_ID_SOLICITUD", "solicitud", solicitudes.size()));

            if (StringUtils.isNotBlank(usuario)) {
                sql.append(" and tarusu.CVE_USUARIO= :usuario ");
            } else {
                if (StringUtils.isNotBlank(subdelegacion)) {
                    sql.append(" AND inst.DES_BDOC_INSTANCIA like :subdelegacion ");
                }
            }
            sql.append(" union ");

            sql.append(" select tarusu.* from DIT_TAREA_USUARIO tarusu ");
            sql.append(" inner join DIT_INSTANCIA inst on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA ");
            sql.append(" inner join DIT_TRAMITE tra on inst.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE ");
            sql.append(" inner join DIT_SOLICITUD sol on sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD ");
            sql.append(" where tarusu.CVE_ID_EDO_TAREA  = :estadoC ");
            sql.append(" and inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia ");
            sql.append(" AND sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitud");
            sql.append(" AND inst.cve_id_proceso = :idProceso ");
            sql.append(agregarQueryList("sol.CVE_ID_SOLICITUD", "solicitud", solicitudes.size()));

            if (StringUtils.isNotBlank(usuario)) {
                sql.append(" and tarusu.CVE_USUARIO= :usuario ");
            } else {
                if (StringUtils.isNotBlank(subdelegacion)) {
                    sql.append(" AND inst.DES_BDOC_INSTANCIA like :subdelegacion ");
                }
            }
            sql.append(" and tarusu.CVE_ID_INSTANCIA not in (select tarusu.CVE_ID_INSTANCIA from DIT_TAREA_USUARIO tarusu ");
            sql.append(" inner join DIT_INSTANCIA inst on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA ");
            sql.append(" inner join DIT_TRAMITE tra on inst.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE ");
            sql.append(" inner join DIT_SOLICITUD sol on sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD ");
            sql.append(" where (tarusu.CVE_ID_EDO_TAREA = :estadoA or tarusu.CVE_ID_EDO_TAREA = :estadoB ) ");
            sql.append(" and inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia ");
            sql.append(" AND sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitud");
            sql.append(" AND inst.cve_id_proceso = :idProceso ");
            sql.append(agregarQueryList("sol.CVE_ID_SOLICITUD", "solicitud", solicitudes.size()));

            if (StringUtils.isNotBlank(usuario)) {
                sql.append(" and tarusu.CVE_USUARIO= :usuario ");
            } else {
                if (StringUtils.isNotBlank(subdelegacion)) {
                    sql.append(" AND inst.DES_BDOC_INSTANCIA like :subdelegacion ");
                }
            }
            sql.append(" ))");
            System.out.println("Query : " + sql.toString());
            query = entityManager.createNativeQuery(sql.toString(), TareaUsuario.class);
            query.setParameter("estadoA", EstadoTareaUsuarioEnum.ACTIVA.getClave());
            query.setParameter("estadoB", EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
            query.setParameter("estadoC", EstadoTareaUsuarioEnum.COMPLETADA.getClave());
            query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.ACTIVA.getClave());
            query.setParameter("estadoSolicitud", EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
            query.setParameter("idProceso", idsProceso);
            agregarParametrosList(query, "solicitud", solicitudes);

            if (StringUtils.isNotBlank(usuario)) {
                query.setParameter("usuario", usuario.trim());
            }
            if (StringUtils.isNotBlank(subdelegacion)) {
                query.setParameter("subdelegacion", "%data>{%subdelegacion%" + subdelegacion + "%");
            }
            dataPage.setData(query.getResultList());

        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    private long obtenerTotalTareasPorUsuario(final String usuario, final List<Long> estadosTareaUsuario,
                                              final String folio, final String curp, final String nss, final String fecha, final Long idTramite) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT count(t.idTareaUsuario) FROM TareaUsuario t ");
        sql.append("WHERE t.estadoTarea.idEstadoTarea in (:estadosTareaUsuario) ");
        sql.append("AND t.instancia.estadoInstancia.idEstadoInstancia = :idEstadoInstancia ");
        if (StringUtils.isNotBlank(usuario)) {
            sql.append("AND t.usuario = :usuario ");
        }
        if (idTramite != null && idTramite > 0) {
            sql.append("AND t.instancia.idTramite like :idTramite ");
        }
        if (StringUtils.isNotBlank(folio)) {
            sql.append("AND t.instancia.bDoc like :folio ");
        }
        if (StringUtils.isNotBlank(nss)) {
            sql.append("AND t.instancia.bDoc like :nss ");
        }
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
        if (StringUtils.isNotBlank(usuario)) {
            query.setParameter("usuario", usuario.trim());
        }
        query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.ACTIVA.getClave());
        if (StringUtils.isNotBlank(folio)) {
            query.setParameter("folio", "%folio>" + folio + "%");
        }
        if (StringUtils.isNotBlank(nss)) {
            query.setParameter("nss", "%data>{%nssInvolucrados%" + nss + "%");
        }
        if (idTramite != null && idTramite > 0) {
            query.setParameter("idTramite", idTramite.intValue());
        }
        return Long.parseLong(query.getSingleResult().toString());
    }

    /**
     * Metodo para obtener una lista de tareas por subdelegacion
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerTareasPorSubdelegacion(DataPage dataPage, final String subdelegacion, final List<Long> estadosTareaUsuario,
                                                  List<Long> idsProcesos) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        long maxResults = obtenerTotalTareasPorSubdelegacion(dataPage, subdelegacion, estadosTareaUsuario, idsProcesos);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }

        if (maxResults > 0) {
            sql.append("SELECT t FROM TareaUsuario t ");
            sql.append("WHERE t.estadoTarea.idEstadoTarea in (:estadosTareaUsuario) ");
            sql.append("AND t.instancia.estadoInstancia.idEstadoInstancia = :idEstadoInstancia ");
            if (StringUtils.isNotBlank(subdelegacion)) {
                sql.append("AND t.instancia.bDoc like :subdelegacion ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
                sql.append("AND t.instancia.bDoc like :folio ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
                sql.append("AND t.instancia.bDoc like :nss ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
                sql.append(generarQueryEstadoSubdelegacion((String) listFilter.get(0).get("filtroEstado")));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroUsuario"))) {
                sql.append("AND t.usuario = :usuario ");
            }
            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                sql.append("AND t.instancia.proceso.bp in (:idsProceso) ");
            }

            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroBDoc"))) {
                agregarFiltrosBDoc(sql, (HashMap<String, Object>) listFilter.get(1));
            }

            sql.append(" order by  ");
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroUsuario"))) {
                sql.append(" case when t.usuario = :usuario then 0 ");
                sql.append(" else 1 ");
                sql.append(" end asc , ");
            }

            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroBDoc"))) {
                sql.append(" t.instancia.bpId asc, ");
            }

            sql.append(" t.instancia.datosAuditoria.fecRegistroActualizado desc  ");
            Query query = entityManager.createQuery(sql.toString());
            query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
            query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.ACTIVA.getClave());
            if (StringUtils.isNotBlank(subdelegacion)) {
                query.setParameter("subdelegacion", "%subdelegacion&quot;:&quot;" + subdelegacion + "&quot;%");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
                query.setParameter("folio", "%folio>" + listFilter.get(0).get("filtroFolio") + "%");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
                query.setParameter("nss", "%data>{%nssInvolucrados%" + listFilter.get(0).get("filtroNss") + "%");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
                asignarParametroSubdelegacion(query, (String) listFilter.get(0).get("filtroEstado"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroUsuario"))) {
                query.setParameter("usuario", String.valueOf(listFilter.get(0).get("filtroUsuario")).trim());
            }
            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                query.setParameter("idsProceso", idsProcesos);
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroBDoc"))) {
                asignarParametrosBDoc(query, (HashMap<String, Object>) listFilter.get(1));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("sinPaginacion"))) {
                dataPage.setCurrentPage(1);
            }
            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    @SuppressWarnings("unchecked")
    private long obtenerTotalTareasPorSubdelegacion(DataPage dataPage, final String subdelegacion, final List<Long> estadosTareaUsuario,
                                                    List<Long> idsProcesos) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT count(t.idTareaUsuario) FROM TareaUsuario t ");
        sql.append("WHERE t.estadoTarea.idEstadoTarea in (:estadosTareaUsuario) ");
        sql.append("AND t.instancia.estadoInstancia.idEstadoInstancia = :idEstadoInstancia ");
        if (StringUtils.isNotBlank(subdelegacion)) {
            sql.append("AND t.instancia.bDoc like :subdelegacion ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            sql.append("AND t.instancia.bDoc like :folio ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
            sql.append("AND t.instancia.bDoc like :nss ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
            sql.append(generarQueryEstadoSubdelegacion((String) listFilter.get(0).get("filtroEstado")));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroUsuario"))) {
            sql.append("AND t.usuario = :usuario ");
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            sql.append("AND t.instancia.proceso.bp in (:idsProceso) ");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroBDoc"))) {
            agregarFiltrosBDoc(sql, (HashMap<String, Object>) listFilter.get(1));
        }
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
        if (StringUtils.isNotBlank(subdelegacion)) {
            query.setParameter("subdelegacion", "%data>{%subdelegacion&quot;:&quot;" + subdelegacion + "&quot;%");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            query.setParameter("folio", "%folio>" + listFilter.get(0).get("filtroFolio") + "%");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
            query.setParameter("nss", "%data>{%nssInvolucrados%" + listFilter.get(0).get("filtroNss") + "%");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroBDoc"))) {
            asignarParametrosBDoc(query, (HashMap<String, Object>) listFilter.get(1));
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
            asignarParametroSubdelegacion(query, (String) listFilter.get(0).get("filtroEstado"));
        }
        query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.ACTIVA.getClave());
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroUsuario"))) {
            query.setParameter("usuario", String.valueOf(listFilter.get(0).get("filtroUsuario")).trim());
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            query.setParameter("idsProceso", idsProcesos);
        }

        return Long.parseLong(query.getSingleResult().toString());
    }

    /**
     * Metodo para obtener una lista de tramites por subdelegacion
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerTramitesPorSubdelegacion(DataPage dataPage, final String subdelegacion, final List<Long> estadosTareaUsuario,
                                                    List<Long> idsProcesos) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstadoTramite"))) {
            estadosTareaUsuario.clear();
            estadosTareaUsuario.add(EstadoTareaUsuarioEnum.COMPLETADA.getClave());

        }

        long maxResults = obtenerTotalTramitesPorSubdelegacion(dataPage, subdelegacion, estadosTareaUsuario, idsProcesos);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }

        if (maxResults > 0) {
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroPortabilidad"))) {
                sql.append("SELECT DISTINCT folio,nss,tipoTramite,fechaSolicitud,estadoTramite FROM (");
            } else {
                sql.append("SELECT DISTINCT folio,nss,tipoTramite,fechaSolicitud,estadoTramite,subdelegacion FROM (");
            }
            armarQuerySecundario(sql, listFilter.get(0), idsProcesos, subdelegacion);
            sql.append(")");
            sql.append("WHERE idInstancia_t = idInstancia_i ");
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
                sql.append("AND folio = :folio ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroTipoTramite"))) {
                sql.append("AND idtipoTramite = :tipoTramite ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                sql.append("AND TO_DATE (fechaSolicitud, 'DD/MM/YYYY') >= :fechaInicial ");
                sql.append("AND TO_DATE (fechaSolicitud, 'DD/MM/YYYY') <= :fechaFinal ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
                sql.append("AND estado = :estado OR estadotarea = :estado ");
            }
            sql.append("ORDER BY folio DESC");

            Query query = entityManager.createNativeQuery(sql.toString());
            query.setParameter("estadosTareaUsuario", estadosTareaUsuario);

            List<Long> estadosInstancia = new ArrayList<Long>();


            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstadoTramite"))) {
                estadosInstancia.add(EstadoInstanciaEnum.TERMINADA.getClave());
            } else {
                estadosInstancia.add(EstadoInstanciaEnum.ACTIVA.getClave());
            }

            query.setParameter("idEstadoInstancia", estadosInstancia);


            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroUsuario"))) {
                query.setParameter("usuario", String.valueOf(listFilter.get(0).get("filtroUsuario")).trim());
            }
            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                query.setParameter("idsProceso", idsProcesos);
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacionControl"))) {
                query.setParameter("subdelegacionControl", "%subdelegacionControl&quot;:&quot;" + listFilter.get(0).get("filtroSubdelegacionControl") + "&quot;%");
            }
            if (StringUtils.isNotBlank(subdelegacion)) {
                query.setParameter("subdelegacion", "%subdelegacion&quot;:&quot;" + subdelegacion + "&quot;%");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
                query.setParameter("folio", listFilter.get(0).get("filtroFolio"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroTipoTramite"))) {
                query.setParameter("tipoTramite", listFilter.get(0).get("filtroTipoTramite"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstadoTramite"))) {
                query.setParameter("estadoTramite", listFilter.get(0).get("filtroEstadoTramite"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
                query.setParameter("nss", "%nss&quot;:&quot;" + listFilter.get(0).get("filtroNss") + "&quot;%");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
                query.setParameter("curp", "%curp&quot;:&quot;" + listFilter.get(0).get("filtroCurp") + "&quot;%");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                try {
                    query.setParameter("fechaInicial", FORMATTER.parse((String) listFilter.get(0).get("fechaInicial")));
                    query.setParameter("fechaFinal", FORMATTER.parse((String) listFilter.get(0).get("fechaFinal")));
                } catch (ParseException ex) {
                    LOGGERBPM.info("Error al convertir las fechas, formato incorrecto", ex);
                }
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
                query.setParameter("estado", listFilter.get(0).get("filtroEstado"));
            }

            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    @SuppressWarnings("unchecked")
    private long obtenerTotalTramitesPorSubdelegacion(DataPage dataPage, final String subdelegacion, final List<Long> estadosTareaUsuario,
                                                      List<Long> idsProcesos) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT count(*) FROM  (");
        sql.append("SELECT DISTINCT folio,nss,tipoTramite,fechaSolicitud FROM (");
        armarQuerySecundario(sql, listFilter.get(0), idsProcesos, subdelegacion);
        sql.append(")");
        sql.append("WHERE idInstancia_t = idInstancia_i ");
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            sql.append("AND folio = :folio ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroTipoTramite"))) {
            sql.append("AND idtipoTramite = :tipoTramite ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("AND TO_DATE (fechaSolicitud, 'DD/MM/YYYY') >= :fechaInicial ");
            sql.append("AND TO_DATE (fechaSolicitud, 'DD/MM/YYYY') <= :fechaFinal ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
            sql.append("AND estado = :estado OR estadotarea = :estado");
        }
        sql.append(")");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
        List<Long> estadosInstancia = new ArrayList<Long>();

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstadoTramite"))) {
            estadosInstancia.add(EstadoInstanciaEnum.TERMINADA.getClave());
        } else {
            estadosInstancia.add(EstadoInstanciaEnum.ACTIVA.getClave());
        }

        query.setParameter("idEstadoInstancia", estadosInstancia);


        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroUsuario"))) {
            query.setParameter("usuario", String.valueOf(listFilter.get(0).get("filtroUsuario")).trim());
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            query.setParameter("idsProceso", idsProcesos);
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacionControl"))) {
            query.setParameter("subdelegacionControl", "%subdelegacionControl&quot;:&quot;" + listFilter.get(0).get("filtroSubdelegacionControl") + "&quot;%");
        }
        if (StringUtils.isNotBlank(subdelegacion)) {
            query.setParameter("subdelegacion", "%subdelegacion&quot;:&quot;" + subdelegacion + "&quot;%");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            query.setParameter("folio", listFilter.get(0).get("filtroFolio"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroTipoTramite"))) {
            query.setParameter("tipoTramite", listFilter.get(0).get("filtroTipoTramite"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstadoTramite"))) {
            query.setParameter("estadoTramite", listFilter.get(0).get("filtroEstadoTramite"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
            query.setParameter("nss", "%nss&quot;:&quot;" + listFilter.get(0).get("filtroNss") + "&quot;%");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
            query.setParameter("curp", "%curp&quot;:&quot;" + listFilter.get(0).get("filtroCurp") + "&quot;%");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            try {
                query.setParameter("fechaInicial", FORMATTER.parse((String) listFilter.get(0).get("fechaInicial")));
                query.setParameter("fechaFinal", FORMATTER.parse((String) listFilter.get(0).get("fechaFinal")));
            } catch (ParseException ex) {
                LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
            }
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
            query.setParameter("estado", listFilter.get(0).get("filtroEstado"));
        }

        return Long.parseLong(query.getSingleResult().toString());
    }

    private void armarQuerySecundario(StringBuffer sql, HashMap<String, Object> listFilter, List<Long> idsProcesos, String subdelegacion) {
        String query = "SELECT SUBSTR(SUBSTR(i.DES_BDOC_INSTANCIA, INSTR(i.DES_BDOC_INSTANCIA, 'folioTramite&quot;:&quot;')+25),0, "
                + "INSTR(SUBSTR(i.DES_BDOC_INSTANCIA, INSTR(i.DES_BDOC_INSTANCIA, 'folioTramite&quot;:&quot;')+25), '&quot;')-1)AS FOLIO, "
                + "SUBSTR(SUBSTR(i.DES_BDOC_INSTANCIA, INSTR(i.DES_BDOC_INSTANCIA, 'nss&quot;:&quot;')+16),0, "
                + "INSTR(SUBSTR(i.DES_BDOC_INSTANCIA, INSTR(i.DES_BDOC_INSTANCIA, 'nss&quot;:&quot;')+16), '&quot;')-1)AS NSS, "
                + "SUBSTR(SUBSTR(i.DES_BDOC_INSTANCIA, INSTR(i.DES_BDOC_INSTANCIA, 'tipoTramite&quot;:&quot;')+24),0, "
                + "INSTR(SUBSTR(i.DES_BDOC_INSTANCIA, INSTR(i.DES_BDOC_INSTANCIA, 'tipoTramite&quot;:&quot;')+24), '&quot;')-1)AS TIPOTRAMITE, "
                + "SUBSTR(SUBSTR(i.DES_BDOC_INSTANCIA, INSTR(i.DES_BDOC_INSTANCIA, 'subdelegacion&quot;:&quot;')+26),0, "
                + "INSTR(SUBSTR(i.DES_BDOC_INSTANCIA, INSTR(i.DES_BDOC_INSTANCIA, 'subdelegacion&quot;:&quot;')+26), '&quot;')-1)AS SUBDELEGACION, "
                + "SUBSTR(SUBSTR(i.DES_BDOC_INSTANCIA,INSTR(i.DES_BDOC_INSTANCIA, 'idTipoTramite&quot;:&quot;')+26),0,"
                + "INSTR(SUBSTR(i.DES_BDOC_INSTANCIA,INSTR(i.DES_BDOC_INSTANCIA, 'idTipoTramite&quot;:&quot;')+26),'&quot;')-1)AS IDTIPOTRAMITE, "
                + "TO_CHAR(i.FEC_REGISTRO_ALTA,'DD/MM/YYYY') AS FECHASOLICITUD, "
                + "t.CVE_ID_INSTANCIA AS idInstancia_t, "
                + "i.CVE_ID_INSTANCIA AS idInstancia_i, "
                + "dt.CVE_ID_ESTADO_TRAMITE AS estadoTramite, "
                + "SUBSTR( SUBSTR( t.DES_BDOC_TAREA, INSTR( t.DES_BDOC_TAREA, '<estado>' )+ 8 ), 0, INSTR( SUBSTR( t.DES_BDOC_TAREA, INSTR( t.DES_BDOC_TAREA, '<estado>' )+ 8 ), '</estado>' )- 1 ) AS ESTADO, "
                + "SUBSTR( SUBSTR(i.DES_BDOC_INSTANCIA, INSTR( i.DES_BDOC_INSTANCIA,'tarea&quot;:&quot;' )+ 18 ),0,INSTR( SUBSTR( i.DES_BDOC_INSTANCIA,INSTR( i.DES_BDOC_INSTANCIA,'tarea&quot;:&quot;' )+ 18 ),'&quot;' )- 1 ) AS ESTADOTAREA ";
        sql.append(query);
        sql.append("FROM DIT_TAREA_USUARIO t, DIT_INSTANCIA i, DIT_TRAMITE dt ");
        sql.append("WHERE t.CVE_ID_EDO_TAREA in (:estadosTareaUsuario) ");
        sql.append("AND dt.CVE_ID_TRAMITE = i.CVE_ID_TRAMITE ");
        sql.append("AND i.CVE_ID_EDO_INSTANCIA in (:idEstadoInstancia )");

        if (StringUtils.isNotBlank((String) listFilter.get("filtroEstadoTramite"))) {
            sql.append("AND dt.CVE_ID_ESTADO_TRAMITE = :estadoTramite ");
        }


        if (StringUtils.isNotBlank((String) listFilter.get("filtroPortabilidad"))) {
            sql.append("AND t.CVE_ID_TAREA in (8,9) ");
        } else {
            sql.append("AND t.CVE_ID_TAREA not in (8,9) ");
        }

        if (StringUtils.isNotBlank((String) listFilter.get("filtroUsuario"))) {
            sql.append("AND t.CVE_USUARIO = :usuario ");
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            sql.append("AND i.CVE_ID_PROCESO in (:idsProceso) ");
        }
        sql.append("AND t.CVE_ID_INSTANCIA=i.CVE_ID_INSTANCIA ");
        if (StringUtils.isNotBlank((String) listFilter.get("filtroSubdelegacionControl"))) {
            sql.append("AND i.DES_BDOC_INSTANCIA LIKE :subdelegacionControl");
        }
        if (StringUtils.isNotBlank(subdelegacion)) {
            sql.append("AND i.DES_BDOC_INSTANCIA LIKE :subdelegacion");
        }
        if (StringUtils.isNotBlank((String) listFilter.get("filtroNss"))) {
            sql.append("AND i.DES_BDOC_INSTANCIA LIKE :nss");
        }
        if (StringUtils.isNotBlank((String) listFilter.get("filtroCurp"))) {
            sql.append("AND i.DES_BDOC_INSTANCIA LIKE :curp");
        }
    }


    /**
     * Metodo para obtener una lista de tareas por subdelegacion obteniendo un
     * registro por instancia, ya sea que tenga tareas activas o completadas
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerTareasPorSubdelegacionIncluyendoCompletadas(DataPage dataPage, final String subdelegacion, final List<Long> estadosTareaUsuario,
                                                                       List<Long> idsProcesos) {
        List<HashMap<String, String>> listFilter = (List<HashMap<String, String>>) dataPage.getData();
        StringBuilder sql = new StringBuilder(50);
        long maxResults = obtenerTotalTareasPorSubdelegacionIncluyendoCompletadas(dataPage, subdelegacion, estadosTareaUsuario, idsProcesos);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }

        if (maxResults > 0) {

            sql.append("SELECT tu FROM TareaUsuario tu WHERE tu.idTareaUsuario  IN (");

            sql.append("SELECT max(t.idTareaUsuario) FROM TareaUsuario t ");
            sql.append("WHERE t.estadoTarea.idEstadoTarea in (:estadosTareaUsuario) ");
            sql.append("AND t.instancia.estadoInstancia.idEstadoInstancia in (:idEstadoInstancia) ");
            if (StringUtils.isNotBlank(subdelegacion)) {
                sql.append("AND t.instancia.bDoc like :subdelegacion ");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
                sql.append("AND t.instancia.bDoc like :folio ");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
                sql.append("AND t.instancia.bDoc like :nss ");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
                sql.append(generarQueryEstadoSubdelegacion(listFilter.get(0).get("filtroEstado")));
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroUsuario"))) {
                sql.append("AND t.usuario = :usuario ");
            }
            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                sql.append("AND t.instancia.proceso.bp in (:idsProceso) ");
            }

            sql.append(" group by  t.instancia.bpId )");

            sql.append(" order by  ");
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroUsuario"))) {
                sql.append(" case when tu.usuario = :usuario then 0 ");
                sql.append(" else 1 ");
                sql.append(" end asc , ");
            }
            sql.append(" tu.instancia.datosAuditoria.fecRegistroActualizado desc  ");

            Query query = entityManager.createQuery(sql.toString());
            query.setParameter("estadosTareaUsuario", estadosTareaUsuario);

            List<Long> estadosInstancia = new ArrayList<Long>();
            estadosInstancia.add(EstadoInstanciaEnum.ACTIVA.getClave());
            estadosInstancia.add(EstadoInstanciaEnum.TERMINADA.getClave());
            query.setParameter("idEstadoInstancia", estadosInstancia);

            if (StringUtils.isNotBlank(subdelegacion)) {
                query.setParameter("subdelegacion", "%subdelegacion&quot;:&quot;" + subdelegacion + "&quot;%");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
                query.setParameter("folio", "%folio>" + listFilter.get(0).get("filtroFolio") + "%");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
                query.setParameter("nss", "%data>{%nssInvolucrados%" + listFilter.get(0).get("filtroNss") + "%");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
                asignarParametroSubdelegacion(query, listFilter.get(0).get("filtroEstado"));
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroUsuario"))) {
                query.setParameter("usuario", listFilter.get(0).get("filtroUsuario").trim());
            }
            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                query.setParameter("idsProceso", idsProcesos);
            }

            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    @SuppressWarnings("unchecked")
    private long obtenerTotalTareasPorSubdelegacionIncluyendoCompletadas(DataPage dataPage, final String subdelegacion, final List<Long> estadosTareaUsuario,
                                                                         List<Long> idsProcesos) {
        List<HashMap<String, String>> listFilter = (List<HashMap<String, String>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT count(tu.idTareaUsuario) FROM TareaUsuario tu WHERE tu.idTareaUsuario  IN (");
        sql.append("SELECT max(t.idTareaUsuario) FROM TareaUsuario t ");
        sql.append("WHERE t.estadoTarea.idEstadoTarea in (:estadosTareaUsuario) ");
        sql.append("AND t.instancia.estadoInstancia.idEstadoInstancia in (:idEstadoInstancia) ");
        if (StringUtils.isNotBlank(subdelegacion)) {
            sql.append("AND t.instancia.bDoc like :subdelegacion ");
        }
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
            sql.append("AND t.instancia.bDoc like :folio ");
        }
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
            sql.append("AND t.instancia.bDoc like :nss ");
        }
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
            sql.append(generarQueryEstadoSubdelegacion(listFilter.get(0).get("filtroEstado")));
        }
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroUsuario"))) {
            sql.append("AND t.usuario = :usuario ");
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            sql.append("AND t.instancia.proceso.bp in (:idsProceso) ");
        }
        sql.append(" group by  t.instancia.bpId )");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
        if (StringUtils.isNotBlank(subdelegacion)) {
            query.setParameter("subdelegacion", "%data>{%subdelegacion&quot;:&quot;" + subdelegacion + "&quot;%");
        }
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
            query.setParameter("folio", "%folio>" + listFilter.get(0).get("filtroFolio") + "%");
        }
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
            query.setParameter("nss", "%data>{%nssInvolucrados%" + listFilter.get(0).get("filtroNss") + "%");
        }
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
            asignarParametroSubdelegacion(query, listFilter.get(0).get("filtroEstado"));
        }

        List<Long> estadosInstancia = new ArrayList<Long>();
        estadosInstancia.add(EstadoInstanciaEnum.ACTIVA.getClave());
        estadosInstancia.add(EstadoInstanciaEnum.TERMINADA.getClave());
        query.setParameter("idEstadoInstancia", estadosInstancia);

        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroUsuario"))) {
            query.setParameter("usuario", listFilter.get(0).get("filtroUsuario").trim());
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            query.setParameter("idsProceso", idsProcesos);
        }

        return Long.parseLong(query.getSingleResult().toString());
    }

    /**
     * Metodo para obtener tareas a reasignar
     */
    @SuppressWarnings("unchecked")
    public List<TareaUsuario> obtenerTareasReasignar(final String rfcUsuario, final String folioTramite,
                                                     final List<String> estadosProcesal, final List<String> unidadesAdmin,
                                                     final List<String> estadosTareaUsuario) {
        StringBuffer sql = new StringBuffer(100);
        sql.append("SELECT t FROM TareaUsuario t ");
        sql.append("WHERE t.estadoTarea.idEstadoTarea in (:estadosTareaUsuario) ");
        if (folioTramite != null && !folioTramite.isEmpty()) {
            sql.append("AND t.instancia.bDoc like :folioTramite ");
        }
        if (rfcUsuario != null && !rfcUsuario.isEmpty()) {
            sql.append("AND t.usuario like :usuario ");
        }
        if (estadosProcesal != null && !estadosProcesal.isEmpty()) {
            sql.append("AND (t.instancia.bDoc like :estadoProcesal").append(0).append(" ");
            for (int i = 1; i < estadosProcesal.size(); i++) {
                sql.append("OR t.instancia.bDoc like :estadoProcesal").append(i).append(" ");
            }
            sql.append(" )");
        }
        if (unidadesAdmin != null && !unidadesAdmin.isEmpty()) {
            sql.append("AND (t.instancia.bDoc like :unidadAdmin").append(0).append(" ");
            for (int i = 1; i < unidadesAdmin.size(); i++) {
                sql.append("OR t.instancia.bDoc like :unidadAdmin").append(i).append(" ");
            }
            sql.append(" )");
        }
        //ORDENAR POR FECHA DE REGISTRO ACTUALIZADO
        sql.append(" ORDER BY t.instancia.datosAuditoria.fecRegistroActualizado DESC");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
        if (folioTramite != null && !folioTramite.isEmpty()) {
            query.setParameter("folioTramite", "%folioTramite>" + folioTramite + "%");
        }
        if (rfcUsuario != null && !rfcUsuario.isEmpty()) {
            query.setParameter("usuario", rfcUsuario + "%");
        }
        if (estadosProcesal != null && !estadosProcesal.isEmpty()) {
            for (int i = 0; i < estadosProcesal.size(); i++) {
                query.setParameter("estadoProcesal" + i, "%estadoProcesal>" + estadosProcesal.get(i) + "%");
            }
        }
        if (unidadesAdmin != null && !unidadesAdmin.isEmpty()) {
            for (int i = 0; i < unidadesAdmin.size(); i++) {
                query.setParameter("unidadAdmin" + i, "%unidadAdmin>" + unidadesAdmin.get(i) + "%");
            }
        }
        return query.getResultList();
    }

    /**
     * Metodo para obtener una lista de tareas de ususario venciadas
     */
    @SuppressWarnings("unchecked")
    public List<TareaUsuario> obtenerTareasUsuarioVencidas(final List<Long> estadosTareaUsuario) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT t FROM TareaUsuario t ");
        sql.append("WHERE fechaInicioTarea + t.timeout/1440 < sysdate ");
        sql.append("AND estadoTarea.idEstadoTarea in (:estadosTareaUsuario) ");
        sql.append("AND t.timeout is not null ");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
        return query.getResultList();
    }

    /**
     * Meotdo para obtener la ultima tarea de usuario completada
     */
    @SuppressWarnings("unchecked")
    public TareaUsuario obtenerUltimaTareaCompletada(final Long bpId) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT t FROM TareaUsuario t ");
        sql.append("WHERE t.estadoTarea.idEstadoTarea =:estadoTareaUsuario ");
        sql.append("AND t.instancia.bpId =:bpId ");
        sql.append("order by t.fechaFinTarea desc ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("estadoTareaUsuario", EstadoTareaUsuarioEnum.COMPLETADA.getClave());
        query.setParameter("bpId", bpId);
        List<TareaUsuario> lstTareaUsuarios = query.getResultList();
        if (lstTareaUsuarios != null && !lstTareaUsuarios.isEmpty()) {
            return lstTareaUsuarios.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public TareaUsuario obtenerUltimaTareaActiva(final Long bpId) {
        List<Long> estadosTareaUsuario = new ArrayList<Long>();
        estadosTareaUsuario.add(EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
        estadosTareaUsuario.add(EstadoTareaUsuarioEnum.ACTIVA.getClave());
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT t FROM TareaUsuario t ");
        sql.append("WHERE t.estadoTarea.idEstadoTarea  in (:estadosTareaUsuario) ");
        sql.append("AND t.instancia.bpId =:bpId ");
        sql.append("order by t.fechaFinTarea desc ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
        query.setParameter("bpId", bpId);
        List<TareaUsuario> lstTareaUsuarios = query.getResultList();
        if (lstTareaUsuarios != null && !lstTareaUsuarios.isEmpty()) {
            return lstTareaUsuarios.get(0);
        } else {
            return null;
        }
    }

    /**
     * Metodo para buscar tareas de usuario de un proceso
     */
    @SuppressWarnings("unchecked")
    public List<Tarea> obtenerTareasUsuarioByProceso(final Long bp) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT t FROM Tarea t ");
        sql.append("WHERE t.proceso.bp =:bp ");
        sql.append("AND t.participante.idParticipante != :sistema ");
        sql.append("AND t.subProceso = :subProceso ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("bp", bp);
        query.setParameter("sistema", ParticipantesEnum.SISTEMA.getClave());
        query.setParameter("subProceso", false);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Tarea> buscarTareaPorNombre(final Long bp, final String nombre) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT t FROM Tarea t ");
        sql.append("WHERE t.proceso.bp =:bp ");
        sql.append("AND t.participante.nombre = :nombre ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("bp", bp);
        query.setParameter("nombre", nombre);
        return query.getResultList();
    }

    /**
     * Metodo para obtener una lista de instancias historicas por usuario
     */
    @SuppressWarnings("unchecked")
    public DataPage obtenerInstanciasHistoricasPorUsuario(DataPage dataPage, final String usuario, List<Long> idsProcesos) {
        List<HashMap<String, String>> listFilter = (List<HashMap<String, String>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        long maxResults = obtenerTotalInstanciasHistoricasPorUsuario(usuario, dataPage, idsProcesos);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }

        if (maxResults > 0) {
            sql.append("SELECT distinct t.instancia FROM TareaUsuario t ");
            sql.append("WHERE t.instancia.estadoInstancia.idEstadoInstancia = :idEstadoInstancia ");
            sql.append("AND t.usuario = :usuario ");

            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
                sql.append("AND t.instancia.bDoc like :folio ");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
                sql.append("AND t.instancia.bDoc like :nss ");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
                sql.append("AND t.instancia.bDoc like :estado ");
            }
            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                sql.append("AND t.instancia.proceso.bp in (:idsProceso) ");
            }
            //sql.append("order by t.instancia.bpId desc "); 
            sql.append("order by t.instancia.datosAuditoria.fecRegistroActualizado desc ");
            Query query = entityManager.createQuery(sql.toString());
            query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.TERMINADA.getClave());
            query.setParameter("usuario", usuario.trim());
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
                query.setParameter("folio", "%folio>" + listFilter.get(0).get("filtroFolio") + "%");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
                query.setParameter("nss", "%data>{%nssInvolucrados%" + listFilter.get(0).get("filtroNss") + "%");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
                query.setParameter("estado", "%<estatus>" + listFilter.get(0).get("filtroEstado") + "</estatus>%");
            }
            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                query.setParameter("idsProceso", idsProcesos);
            }
            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    @SuppressWarnings("unchecked")
    private long obtenerTotalInstanciasHistoricasPorUsuario(final String usuario, DataPage dataPage, List<Long> idsProcesos) {
        StringBuffer sql = new StringBuffer(50);
        List<HashMap<String, String>> listFilter = (List<HashMap<String, String>>) dataPage.getData();
        sql.append("SELECT count(distinct t.instancia ) FROM TareaUsuario t ");
        sql.append("WHERE t.instancia.estadoInstancia.idEstadoInstancia = :idEstadoInstancia ");

        sql.append("AND t.usuario = :usuario ");
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
            sql.append("AND t.instancia.bDoc like :folio ");
        }
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
            sql.append("AND t.instancia.bDoc like :nss ");
        }
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
            sql.append("AND t.instancia.bDoc like :estado ");
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            sql.append("AND t.instancia.proceso.bp in (:idsProceso) ");
        }
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.TERMINADA.getClave());
        query.setParameter("usuario", usuario.trim());
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
            query.setParameter("folio", "%folio>" + listFilter.get(0).get("filtroFolio") + "%");
        }
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
            query.setParameter("nss", "%data>{%nssInvolucrados%" + listFilter.get(0).get("filtroNss") + "%");
        }
        if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
            query.setParameter("estado", "%<estatus>" + listFilter.get(0).get("filtroEstado") + "</estatus>%");
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            query.setParameter("idsProceso", idsProcesos);
        }
        return Long.parseLong(query.getSingleResult().toString());
    }

	/*
	Llenado de la bandeja de Históricos CDA
	*/

    /**
     * Metodo para obtener una lista de instancias historicas por usuario
     */
    @SuppressWarnings("unchecked")
    public DataPage obtenerInstanciasHistoricasCDA(DataPage dataPage, String usuario, String subdelegacion, Long idsProceso) {
        List<HashMap<String, String>> listFilter = (List<HashMap<String, String>>) dataPage.getData();

        String folio = listFilter.get(0).get("filtroFolio");
        String nss = listFilter.get(0).get("filtroNss");
        String fecha = listFilter.get(0).get("filtroFecha");
        String estado = listFilter.get(0).get("filtroEstado");
        //Filtros adicionales CDA
        String origen = listFilter.get(0).get("filtroOrigen");
        String responsable = listFilter.get(0).get("filtroResponsable");
        String autorizador = listFilter.get(0).get("filtroAutorizo");
        String curp = listFilter.get(0).get("filtroCurp");
        String tipoTramite = listFilter.get(0).get("filtroTramite");
        String fechaActualizacion = listFilter.get(0).get("filtroFechaActualizacion");
        String fechaSolicitud = listFilter.get(0).get("filtroFechaSolicitud");
        String vencido = listFilter.get(0).get("filtroVencido");
        String asignadas = listFilter.get(0).get("filtroUsuario");
        StringBuffer sql = new StringBuffer(50);

        if (StringUtils.isBlank(asignadas)) {
            usuario = null;
        } else {
            subdelegacion = null;
        }

        long maxResults = obtenerTotalInstanciasHistoricasCDA(usuario, subdelegacion, dataPage, idsProceso);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }

        if (maxResults > 0) {

            //SI CONSULTA POR NSS, OBTIENE EL FOLIO PARA OBTENER TODOS LOS TRAMITES
            if (StringUtils.isNotBlank(nss)) {
                folio = obtenerFolioParaBusquedaPorNss(usuario, subdelegacion, nss, true, idsProceso);
                nss = StringUtils.EMPTY;
            }

            sql.append(" select distinct sol.* from DIT_SOLICITUD sol");
            sql.append(" inner join DIT_TRAMITE tra on sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD");
            sql.append(" inner join DIT_INSTANCIA inst on tra.CVE_ID_TRAMITE = inst.CVE_ID_TRAMITE");
            sql.append(" inner join DIT_TAREA_USUARIO tarusu on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA");
            if (StringUtils.isNotBlank(responsable) || StringUtils.isNotBlank(autorizador)) {
                sql.append(" inner join DIT_INST_PARTICIP part on part.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA");
            }
            sql.append(" inner join DIT_CORRECCION_DATOS_ASEG corr on corr.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE");

            sql.append(" where inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia");
            sql.append(" AND (sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitudA or sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitudB) ");
            sql.append(" AND inst.cve_id_proceso = :idProceso ");

            if (StringUtils.isNotBlank(folio)) {
                sql.append(" AND sol.REF_FOLIO = :folio ");
            }
            if (StringUtils.isNotBlank(origen)) {
                sql.append(" AND sol.CVE_ID_ORIGEN_SOLICITUD = :idOrigen");
            }
            if (StringUtils.isNotBlank(usuario)) {
                sql.append(" and tarusu.CVE_USUARIO= :usuario ");
            } else {
                //FILTRA SUBDELEGACION
                if (StringUtils.isNotBlank(subdelegacion)) {
                    sql.append(" AND inst.DES_BDOC_INSTANCIA like :subdelegacion ");
                }
            }
            if (StringUtils.isNotBlank(estado)) {
                sql.append(generarQueryEstadoUsuario(estado));
            }

            if (StringUtils.isNotBlank(fechaActualizacion)) {
                sql.append(" AND( sol.FEC_REGISTRO_ACTUALIZADO BETWEEN TO_DATE(:fechaActualizacionInicial,'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaActualizacionFinal,'dd/MM/YYYY HH24:mi:ss') )");
            }
            if (StringUtils.isNotBlank(fechaSolicitud)) {
                sql.append(" AND( sol.FEC_SOLICITUD BETWEEN TO_DATE(:fechaSolicitudInicial,'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaSolicitudFinal,'dd/MM/YYYY HH24:mi:ss') )");
            }
            if (StringUtils.isNotBlank(responsable)) {
                sql.append(" AND part.CVE_USUARIO = :responsable");
            }
            if (StringUtils.isNotBlank(autorizador)) {
                sql.append(" AND part.CVE_USUARIO = :autorizador");
            }
            if (StringUtils.isNotBlank(vencido)) {
                sql.append(" AND tra.CVE_ID_ESTADO_TRAMITE = :vencido");
            }

            if (StringUtils.isNotBlank(curp)) {
                sql.append(" AND corr.REF_CURP = :curp");
            }
            if (StringUtils.isNotBlank(tipoTramite)) {
                sql.append(" AND tra.CVE_ID_TIPO_TRAMITE = :tipoTramite");
            }
            sql.append(" order by sol.FEC_SOLICITUD desc ");
            Query query = entityManager.createNativeQuery(sql.toString(), DitSolicitud.class);
            if (StringUtils.isNotBlank(usuario)) {
                query.setParameter("usuario", usuario.trim());
            }
            query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.TERMINADA.getClave());
            query.setParameter("estadoSolicitudA", EstadoSolicitudEnum.ATENDIDA.getCodigo());
            query.setParameter("estadoSolicitudB", EstadoSolicitudEnum.CANCELADA.getCodigo());
            query.setParameter("idProceso", idsProceso);

            if (StringUtils.isNotBlank(folio)) {
                query.setParameter("folio", folio);
            }

            if (StringUtils.isNotBlank(estado)) {
                asignarParametroUsuario(query, estado);
            }

            if (StringUtils.isNotBlank(origen)) {
                if (origen.equals("INTERNET")) {
                    query.setParameter("idOrigen", OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
                } else {
                    query.setParameter("idOrigen", OrigenSolicitudEnum.getByDesc(origen).getId());
                }
            }
            if (StringUtils.isNotBlank(responsable)) {
                query.setParameter("responsable", responsable);
            }
            if (StringUtils.isNotBlank(autorizador)) {
                query.setParameter("autorizador", autorizador);
            }
            if (StringUtils.isNotBlank(curp)) {
                query.setParameter("curp", curp);
            }
            if (StringUtils.isNotBlank(tipoTramite)) {
                query.setParameter("tipoTramite", TipoRegularizacionSolicitudCDAEnum.fromDesc(tipoTramite).getIdTipoTramite());
            }
            if (StringUtils.isNotBlank(fechaActualizacion)) {
                query.setParameter("fechaActualizacionInicial", fechaActualizacion + " 00:00:00");
                query.setParameter("fechaActualizacionFinal", fechaActualizacion + " 23:59:59");
            }
            if (StringUtils.isNotBlank(fechaSolicitud)) {
                query.setParameter("fechaSolicitudInicial", fechaSolicitud + " 00:00:00");
                query.setParameter("fechaSolicitudFinal", fechaSolicitud + " 23:59:59");
            }
            if (StringUtils.isNotBlank(vencido)) {
                query.setParameter("vencido", EstadoTramiteEnum.VENCIDA.getCodigo());
            }
            if (StringUtils.isNotBlank(subdelegacion)) {
                query.setParameter("subdelegacion", "%data>{%subdelegacion%" + subdelegacion + "%");
            }

            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            List<DitSolicitud> ditSolicitudes = query.getResultList();
            List<String> solicitudes = new ArrayList<String>();
            for (DitSolicitud solicitud : ditSolicitudes) {
                solicitudes.add(Long.toString(solicitud.getCveIdSolicitud()));
            }

            System.out.println("Solicitudes Hist" + solicitudes.toString());
            sql.delete(0, sql.length());
            sql.append("SELECT distinct inst.* FROM DIT_INSTANCIA inst ");
            sql.append("inner join DIT_TRAMITE tra on tra.CVE_ID_TRAMITE = inst.CVE_ID_TRAMITE ");
            sql.append("inner join DIT_SOLICITUD sol on sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD ");
            sql.append("WHERE inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia ");
            sql.append(" AND (sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitudA or sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitudB)");
            sql.append(" AND inst.cve_id_proceso = :idProceso ");
            sql.append(agregarQueryList("sol.CVE_ID_SOLICITUD", "solicitud", solicitudes.size()));
            sql.append("order by inst.FEC_REGISTRO_ACTUALIZADO desc ");
            System.out.println("Query hist " + sql.toString());
            query = entityManager.createNativeQuery(sql.toString(), Instancia.class);
            query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.TERMINADA.getClave());
            query.setParameter("estadoSolicitudA", EstadoSolicitudEnum.ATENDIDA.getCodigo());
            query.setParameter("estadoSolicitudB", EstadoSolicitudEnum.CANCELADA.getCodigo());
            query.setParameter("idProceso", idsProceso);
            agregarParametrosList(query, "solicitud", solicitudes);
            dataPage.setData(query.getResultList());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    @SuppressWarnings("unchecked")
    private long obtenerTotalInstanciasHistoricasCDA(final String usuario, final String subdelegacion, DataPage dataPage, Long idsProceso) {
        List<HashMap<String, String>> listFilter = (List<HashMap<String, String>>) dataPage.getData();

        String folio = listFilter.get(0).get("filtroFolio");
        String nss = listFilter.get(0).get("filtroNss");
        String fecha = listFilter.get(0).get("filtroFecha");
        String estado = listFilter.get(0).get("filtroEstado");
        //Filtros adicionales CDA
        String origen = listFilter.get(0).get("filtroOrigen");
        String responsable = listFilter.get(0).get("filtroResponsable");
        String autorizador = listFilter.get(0).get("filtroAutorizo");
        String curp = listFilter.get(0).get("filtroCurp");
        String tipoTramite = listFilter.get(0).get("filtroTramite");
        String fechaActualizacion = listFilter.get(0).get("filtroFechaActualizacion");
        String fechaSolicitud = listFilter.get(0).get("filtroFechaSolicitud");
        String vencido = listFilter.get(0).get("filtroVencido");
        String asignadas = listFilter.get(0).get("filtroUsuario");
        StringBuffer sql = new StringBuffer(50);
        sql.append(" select count(1) from ( ");
        sql.append(" select distinct sol.REF_FOLIO from DIT_SOLICITUD sol");
        sql.append(" inner join DIT_TRAMITE tra on sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD");
        sql.append(" inner join DIT_INSTANCIA inst on tra.CVE_ID_TRAMITE = inst.CVE_ID_TRAMITE");
        sql.append(" inner join DIT_TAREA_USUARIO tarusu on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA");
        if (StringUtils.isNotBlank(responsable) || StringUtils.isNotBlank(autorizador)) {
            sql.append(" inner join DIT_INST_PARTICIP part on part.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA");
        }
        sql.append(" inner join DIT_CORRECCION_DATOS_ASEG corr on corr.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE");
        if (StringUtils.isNotBlank(nss)) {
            sql.append(" inner join DIT_DETALLE_NSS_CDA det on det.CVE_ID_CORRECCION_DATOS_ASEG = corr.CVE_ID_CORRECCION_DATOS_ASEG");
        }
        sql.append(" where inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia");
        sql.append(" AND (sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitudA or sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitudB)");
        sql.append(" AND inst.cve_id_proceso = :idProceso ");

        if (StringUtils.isNotBlank(folio)) {
            sql.append(" AND sol.REF_FOLIO = :folio ");
        }
        if (StringUtils.isNotBlank(origen)) {
            sql.append(" AND sol.CVE_ID_ORIGEN_SOLICITUD = :idOrigen");
        }
        if (StringUtils.isNotBlank(usuario)) {
            sql.append(" and tarusu.CVE_USUARIO= :usuario ");
        } else {
            //FILTRA SUBDELEGACION
            if (StringUtils.isNotBlank(subdelegacion)) {
                sql.append(" AND inst.DES_BDOC_INSTANCIA like :subdelegacion ");
            }
        }
        if (StringUtils.isNotBlank(estado)) {
            sql.append(generarQueryEstadoUsuario(estado));
        }

        if (StringUtils.isNotBlank(fechaActualizacion)) {
            sql.append(" AND( sol.FEC_REGISTRO_ACTUALIZADO BETWEEN TO_DATE(:fechaActualizacionInicial,'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaActualizacionFinal,'dd/MM/YYYY HH24:mi:ss') )");
        }
        if (StringUtils.isNotBlank(fechaSolicitud)) {
            sql.append(" AND( sol.FEC_SOLICITUD BETWEEN TO_DATE(:fechaSolicitudInicial,'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaSolicitudFinal,'dd/MM/YYYY HH24:mi:ss') )");
        }
        if (StringUtils.isNotBlank(responsable)) {
            sql.append(" AND part.CVE_USUARIO = :responsable");
        }
        if (StringUtils.isNotBlank(autorizador)) {
            sql.append(" AND part.CVE_USUARIO = :autorizador");
        }
        if (StringUtils.isNotBlank(vencido)) {
            sql.append(" AND tra.CVE_ID_ESTADO_TRAMITE = :vencido");
        }
        if (StringUtils.isNotBlank(nss)) {
            sql.append(" AND det.CVE_NSS = :nss ");
        }
        if (StringUtils.isNotBlank(curp)) {
            sql.append(" AND corr.REF_CURP = :curp");
        }
        if (StringUtils.isNotBlank(tipoTramite)) {
            sql.append(" AND tra.CVE_ID_TIPO_TRAMITE = :tipoTramite");
        }
        sql.append(" ) ");
        System.out.println("Query : " + sql.toString());
        Query query = entityManager.createNativeQuery(sql.toString());
        if (StringUtils.isNotBlank(usuario)) {
            query.setParameter("usuario", usuario.trim());
        }
        query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.TERMINADA.getClave());
        query.setParameter("estadoSolicitudA", EstadoSolicitudEnum.ATENDIDA.getCodigo());
        query.setParameter("estadoSolicitudB", EstadoSolicitudEnum.CANCELADA.getCodigo());
        query.setParameter("idProceso", idsProceso);

        if (StringUtils.isNotBlank(folio)) {
            query.setParameter("folio", folio);
        }
        if (StringUtils.isNotBlank(nss)) {
            query.setParameter("nss", nss);
        }
        if (StringUtils.isNotBlank(estado)) {
            asignarParametroUsuario(query, estado);
        }

        if (StringUtils.isNotBlank(origen)) {
            if (origen.equals("INTERNET")) {
                query.setParameter("idOrigen", OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
            } else {
                query.setParameter("idOrigen", OrigenSolicitudEnum.getByDesc(origen).getId());
            }
        }
        if (StringUtils.isNotBlank(responsable)) {
            query.setParameter("responsable", responsable);
        }
        if (StringUtils.isNotBlank(autorizador)) {
            query.setParameter("autorizador", autorizador);
        }
        if (StringUtils.isNotBlank(curp)) {
            query.setParameter("curp", curp);
        }
        if (StringUtils.isNotBlank(tipoTramite)) {
            query.setParameter("tipoTramite", TipoRegularizacionSolicitudCDAEnum.fromDesc(tipoTramite).getIdTipoTramite());
        }
        if (StringUtils.isNotBlank(fechaActualizacion)) {
            query.setParameter("fechaActualizacionInicial", fechaActualizacion + " 00:00:00");
            query.setParameter("fechaActualizacionFinal", fechaActualizacion + " 23:59:59");
        }
        if (StringUtils.isNotBlank(fechaSolicitud)) {
            query.setParameter("fechaSolicitudInicial", fechaSolicitud + " 00:00:00");
            query.setParameter("fechaSolicitudFinal", fechaSolicitud + " 23:59:59");
        }
        if (StringUtils.isNotBlank(vencido)) {
            query.setParameter("vencido", EstadoTramiteEnum.VENCIDA.getCodigo());
        }
        if (StringUtils.isNotBlank(subdelegacion)) {
            query.setParameter("subdelegacion", "%data>{%subdelegacion%" + subdelegacion + "%");
        }

        return Long.parseLong(query.getSingleResult().toString());
    }


    /**
     * Metodo para obtener una lista de tareas por usuario
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerInstanciasHistoricasPorSubdelegacion(DataPage dataPage, final String subdelegacion, List<Long> idsProcesos) {
        List<HashMap<String, String>> listFilter = (List<HashMap<String, String>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        long maxResults = obtenerTotalInstanciasHistoricasPorSubdelegacion(subdelegacion, dataPage, idsProcesos);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }
        dataPage.setData(null);
        if (maxResults > 0) {

            sql.append("SELECT distinct i FROM Instancia i ");
            sql.append("WHERE i.estadoInstancia.idEstadoInstancia = :idEstadoInstancia ");
            if (StringUtils.isNotBlank(subdelegacion)) {
                sql.append("AND i.bDoc like :subdelegacion ");
            }
            if (listFilter != null && !listFilter.isEmpty()) {
                if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
                    sql.append("AND i.bDoc like :folio ");
                }
                if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
                    sql.append("AND i.bDoc like :nss ");
                }
                if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
                    sql.append("AND i.bDoc like :estado ");
                }
            }
            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                sql.append("AND i.proceso.bp in (:idsProceso) ");
            }
            sql.append("order by i.datosAuditoria.fecRegistroActualizado desc  ");
            Query query = entityManager.createQuery(sql.toString());
            query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.TERMINADA.getClave());
            if (StringUtils.isNotBlank(subdelegacion)) {
                query.setParameter("subdelegacion", "%data>{%subdelegacion&quot;:&quot;" + subdelegacion + "&quot;%");
            }
            if (listFilter != null && !listFilter.isEmpty()) {
                if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
                    query.setParameter("folio", "%folio>" + listFilter.get(0).get("filtroFolio") + "%");
                }
                if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
                    query.setParameter("nss", "%data>{%nssInvolucrados%" + listFilter.get(0).get("filtroNss") + "%");
                }
                if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
                    query.setParameter("estado", "%<estatus>" + listFilter.get(0).get("filtroEstado") + "</estatus>%");
                }
            }

            if (idsProcesos != null && !idsProcesos.isEmpty()) {
                query.setParameter("idsProceso", idsProcesos);
            }

            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    @SuppressWarnings("unchecked")
    private long obtenerTotalInstanciasHistoricasPorSubdelegacion(final String subdelegacion, DataPage dataPage, List<Long> idsProcesos) {
        List<HashMap<String, String>> listFilter = (List<HashMap<String, String>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT count(distinct i) FROM Instancia i ");
        sql.append("WHERE i.estadoInstancia.idEstadoInstancia = :idEstadoInstancia ");
        if (listFilter != null && !listFilter.isEmpty()) {
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
                sql.append("AND i.bDoc like :folio ");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
                sql.append("AND i.bDoc like :nss ");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
                sql.append("AND i.bDoc like :estado ");
            }
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            sql.append("AND i.proceso.bp in (:idsProceso) ");
        }
        if (StringUtils.isNotBlank(subdelegacion)) {
            sql.append("AND i.bDoc like :subdelegacion ");
        }
        Query query = entityManager.createQuery(sql.toString());
        if (StringUtils.isNotBlank(subdelegacion)) {
            query.setParameter("subdelegacion", "%data>{%subdelegacion&quot;:&quot;" + subdelegacion + "&quot;%");
        }
        query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.TERMINADA.getClave());
        if (listFilter != null && !listFilter.isEmpty()) {
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroFolio"))) {
                query.setParameter("folio", "%folio>" + listFilter.get(0).get("filtroFolio") + "%");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroNss"))) {
                query.setParameter("nss", "%data>{%nssInvolucrados%" + listFilter.get(0).get("filtroNss") + "%");
            }
            if (StringUtils.isNotBlank(listFilter.get(0).get("filtroEstado"))) {
                query.setParameter("estado", "%<estatus>" + listFilter.get(0).get("filtroEstado") + "</estatus>%");
            }
        }

        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            query.setParameter("idsProceso", idsProcesos);
        }

        return Long.parseLong(query.getSingleResult().toString());
    }

    @Override
    public Instancia buscarInstanciaByTramite(Long idTramite) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("select i from Instancia i ");
        sql.append("WHERE i.idTramite = :idTramite");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idTramite", idTramite);

        return (query.getResultList() != null && query.getResultList().size() > 0)
                ? (Instancia) query.getSingleResult() : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DataPage obtenerTareasPorUsuarioTramite(DataPage dataPage, final String usuario, final List<Long> estadosTareaUsuario, final Long idTramite) {
        StringBuffer sql = new StringBuffer(50);
        String vacio = "";
        long maxResults = obtenerTotalTareasPorUsuario(usuario, estadosTareaUsuario, vacio, vacio, vacio, vacio, idTramite);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }

        if (maxResults > 0) {
            sql.append("SELECT t FROM TareaUsuario t ");

            sql.append("WHERE t.estadoTarea.idEstadoTarea in (:estadosTareaUsuario) ");
            sql.append("AND t.instancia.estadoInstancia.idEstadoInstancia = :idEstadoInstancia ");
            if (StringUtils.isNotBlank(usuario)) {
                sql.append("AND t.usuario = :usuario ");
            }
            sql.append("AND t.instancia.idTramite like :idTramite ");
            sql.append("order by t.instancia.datosAuditoria.fecRegistroActualizado desc  ");

            Query query = entityManager.createQuery(sql.toString());
            query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
            if (StringUtils.isNotBlank(usuario)) {
                query.setParameter("usuario", usuario.trim());
            }
            query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.ACTIVA.getClave());
            query.setParameter("idTramite", idTramite.intValue());

            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    private String agregarQueryList(String campo, int tamanio) {
        StringBuilder sql = new StringBuilder(50);
        sql.append(" AND ");
        sql.append(campo);
        sql.append(" in ( ");
        for (int i = 0; i < tamanio; i++) {
            if (i != 0) {
                sql.append(",");
            }
            sql.append(":param" + i);
        }
        sql.append(" ) ");

        return sql.toString();
    }

    private String agregarQueryList(String campo, String id, int tamanio) {
        StringBuilder sql = new StringBuilder(50);
        sql.append(" AND ");
        sql.append(campo);
        sql.append(" in ( ");
        for (int i = 0; i < tamanio; i++) {
            if (i != 0) {
                sql.append(",");
            }
            sql.append(":" + id + i);
        }
        sql.append(" ) ");

        return sql.toString();
    }


    private <T> void agregarParametrosList(Query query, List<T> idsProcesos) {
        for (int i = 0; i < idsProcesos.size(); i++) {
            query.setParameter("param" + i, idsProcesos.get(i));
        }

    }

    private <T> void agregarParametrosList(Query query, String id, List<T> idsProcesos) {
        for (int i = 0; i < idsProcesos.size(); i++) {
            query.setParameter(id + i, idsProcesos.get(i));
        }

    }

    private long obtenerTotalTareasPorUsuarioNativo(final String usuario, final List<Long> estadosTareaUsuario,
                                                    final String folio, final String nss, final String fecha, final String estado, final Long idTramite, List<Long> idsProcesos) {
        StringBuffer sql = new StringBuffer(50);
        sql.append(" select count(*) from ( ");

        sql.append(" select tarUsu.* from DIT_TAREA_USUARIO tarusu ");
        sql.append(" inner join DIT_INSTANCIA inst on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA ");
        sql.append(" where (tarusu.CVE_ID_EDO_TAREA = :estadoA or tarusu.CVE_ID_EDO_TAREA = :estadoB ) ");
        sql.append(" and inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia and tarusu.CVE_USUARIO= :usuario ");
        if (idTramite != null && idTramite > 0) {
            sql.append(" AND inst.CVE_ID_TRAMITE like :idTramite ");
        }
        if (StringUtils.isNotBlank(folio)) {
            sql.append(" AND inst.DES_BDOC_INSTANCIA like :folio ");
        }
        if (StringUtils.isNotBlank(nss)) {
            sql.append(" AND inst.DES_BDOC_INSTANCIA like :nss ");
        }
        if (StringUtils.isNotBlank(estado)) {
            sql.append(generarQueryEstadoUsuario(estado));
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            sql.append(agregarQueryList("inst.cve_id_proceso", idsProcesos.size()));
        }

        sql.append(" union ");

        sql.append(" select tarUsu.* from DIT_TAREA_USUARIO tarusu ");
        sql.append(" inner join DIT_INSTANCIA inst on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA ");
        sql.append(" where tarusu.CVE_ID_EDO_TAREA  = :estadoC ");
        sql.append(" and inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia and tarusu.CVE_USUARIO= :usuario ");
        if (idTramite != null && idTramite > 0) {
            sql.append("AND inst.CVE_ID_TRAMITE like :idTramite ");
        }
        if (StringUtils.isNotBlank(folio)) {
            sql.append(" AND inst.DES_BDOC_INSTANCIA like :folio ");
        }
        if (StringUtils.isNotBlank(nss)) {
            sql.append(" AND inst.DES_BDOC_INSTANCIA like :nss ");
        }
        if (StringUtils.isNotBlank(estado)) {
            sql.append(generarQueryEstadoUsuario(estado));
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            sql.append(agregarQueryList("inst.cve_id_proceso", idsProcesos.size()));
        }
        sql.append(" and tarusu.CVE_ID_INSTANCIA not in (select tarusu.CVE_ID_INSTANCIA from DIT_TAREA_USUARIO tarusu ");
        sql.append(" inner join DIT_INSTANCIA inst on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA ");
        sql.append(" where (tarusu.CVE_ID_EDO_TAREA = :estadoA or tarusu.CVE_ID_EDO_TAREA = :estadoB )");
        sql.append(" and inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia and tarusu.CVE_USUARIO= :usuario ");
        if (idTramite != null && idTramite > 0) {
            sql.append(" AND inst.CVE_ID_TRAMITE like :idTramite ");
        }
        if (StringUtils.isNotBlank(folio)) {
            sql.append(" AND inst.DES_BDOC_INSTANCIA like :folio ");
        }
        if (StringUtils.isNotBlank(nss)) {
            sql.append(" AND inst.DES_BDOC_INSTANCIA like :nss ");
        }
        if (StringUtils.isNotBlank(estado)) {
            sql.append(generarQueryEstadoUsuario(estado));
        }
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            sql.append(agregarQueryList("inst.cve_id_proceso", idsProcesos.size()));
        }

        sql.append(" )) ");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("estadoA", EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
        query.setParameter("estadoB", EstadoTareaUsuarioEnum.ACTIVA.getClave());
        query.setParameter("estadoC", EstadoTareaUsuarioEnum.COMPLETADA.getClave());
        query.setParameter("usuario", usuario.trim());
        query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.ACTIVA.getClave());
        if (StringUtils.isNotBlank(folio)) {
            query.setParameter("folio", "%folio>" + folio + "%");
        }
        if (StringUtils.isNotBlank(nss)) {
            query.setParameter("nss", "%data>{%nssInvolucrados%" + nss + "%");
        }
        if (StringUtils.isNotBlank(estado)) {
            asignarParametroUsuario(query, estado);
        }

        if (idTramite != null && idTramite > 0) {
            query.setParameter("idTramite", idTramite.intValue());
        }

        agregarParametrosList(query, idsProcesos);

        return Long.parseLong(query.getSingleResult().toString());
    }

    //Se agrega nuevo método para buscar con los filtros adicionales de CDA
    private long obtenerTotalTareasNativoCDA(final String usuario, final String subdelegacion,
                                             final String folio, final String nss, final String estado, final String origen, final String responsable, final String autorizador, final String curp, final String tipoTramite, final String fechaActualizacion, final String fechaSolicitud, final String vencido, final Long idTramite, Long idsProceso) {
        StringBuffer sql = new StringBuffer(50);
        sql.append(" select count(1) from ( ");
        sql.append(" select distinct sol.REF_FOLIO from DIT_SOLICITUD sol");
        sql.append(" inner join DIT_TRAMITE tra on sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD");
        sql.append(" inner join DIT_INSTANCIA inst on tra.CVE_ID_TRAMITE = inst.CVE_ID_TRAMITE");
        sql.append(" inner join DIT_TAREA_USUARIO tarusu on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA");
        if (StringUtils.isNotBlank(responsable) || StringUtils.isNotBlank(autorizador)) {
            sql.append(" inner join DIT_INST_PARTICIP part on part.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA");
        }
        sql.append(" inner join DIT_CORRECCION_DATOS_ASEG corr on corr.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE");
        if (StringUtils.isNotBlank(nss)) {
            sql.append(" inner join DIT_DETALLE_NSS_CDA det on det.CVE_ID_CORRECCION_DATOS_ASEG = corr.CVE_ID_CORRECCION_DATOS_ASEG");
        }
        sql.append(" where (tarusu.CVE_ID_EDO_TAREA = :estadoA or tarusu.CVE_ID_EDO_TAREA = :estadoB or tarusu.CVE_ID_EDO_TAREA = :estadoC) ");
        sql.append(" and inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia");
        sql.append(" AND sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitud");
        sql.append(" AND inst.cve_id_proceso = :idProceso ");

        if (idTramite != null && idTramite > 0) {
            sql.append(" AND inst.CVE_ID_TRAMITE = :idTramite ");
        }
        if (StringUtils.isNotBlank(folio)) {
            sql.append(" AND sol.REF_FOLIO = :folio ");
        }

        if (StringUtils.isNotBlank(origen)) {
            sql.append(" AND sol.CVE_ID_ORIGEN_SOLICITUD = :idOrigen");
        }
        if (StringUtils.isNotBlank(usuario)) {
            sql.append(" and tarusu.CVE_USUARIO= :usuario ");
        } else {
            //FILTRA SUBDELEGACION
            if (StringUtils.isNotBlank(subdelegacion)) {
                sql.append(" AND inst.DES_BDOC_INSTANCIA like :subdelegacion ");
            }
        }
        if (StringUtils.isNotBlank(estado)) {
            sql.append(generarQueryEstadoUsuario(estado));
        }

        if (StringUtils.isNotBlank(fechaActualizacion)) {
            sql.append(" AND( sol.FEC_REGISTRO_ACTUALIZADO BETWEEN TO_DATE(:fechaActualizacionInicial,'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaActualizacionFinal,'dd/MM/YYYY HH24:mi:ss') )");
        }
        if (StringUtils.isNotBlank(fechaSolicitud)) {
            sql.append(" AND( sol.FEC_SOLICITUD BETWEEN TO_DATE(:fechaSolicitudInicial,'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaSolicitudFinal,'dd/MM/YYYY HH24:mi:ss') )");
        }
        if (StringUtils.isNotBlank(responsable)) {
            sql.append(" AND part.CVE_USUARIO = :responsable");
        }
        if (StringUtils.isNotBlank(autorizador)) {
            sql.append(" AND part.CVE_USUARIO = :autorizador");
        }
        if (StringUtils.isNotBlank(vencido)) {
            sql.append(" AND tra.CVE_ID_ESTADO_TRAMITE = :vencido");
        }
        if (StringUtils.isNotBlank(nss)) {
            sql.append(" AND det.CVE_NSS = :nss ");
        }
        if (StringUtils.isNotBlank(curp)) {
            sql.append(" AND corr.REF_CURP = :curp");
        }
        if (StringUtils.isNotBlank(tipoTramite)) {
            sql.append(" AND tra.CVE_ID_TIPO_TRAMITE = :tipoTramite");
        }
        sql.append(" ) ");

        System.out.println("Query : " + sql.toString());
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("estadoA", EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
        query.setParameter("estadoB", EstadoTareaUsuarioEnum.ACTIVA.getClave());
        query.setParameter("estadoC", EstadoTareaUsuarioEnum.COMPLETADA.getClave());
        query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.ACTIVA.getClave());
        query.setParameter("estadoSolicitud", EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
        query.setParameter("idProceso", idsProceso);

        if (StringUtils.isNotBlank(usuario)) {
            query.setParameter("usuario", usuario.trim());
        }
        if (StringUtils.isNotBlank(folio)) {
            query.setParameter("folio", folio);
        }
        if (StringUtils.isNotBlank(nss)) {
            query.setParameter("nss", nss);
        }
        if (StringUtils.isNotBlank(estado)) {
            asignarParametroUsuario(query, estado);
        }

        if (idTramite != null && idTramite > 0) {
            query.setParameter("idTramite", idTramite.intValue());
        }

        if (StringUtils.isNotBlank(origen)) {
            if (origen.equals("INTERNET")) {
                query.setParameter("idOrigen", OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
            } else {
                query.setParameter("idOrigen", OrigenSolicitudEnum.getByDesc(origen).getId());
            }
        }
        if (StringUtils.isNotBlank(responsable)) {
            query.setParameter("responsable", responsable);
        }
        if (StringUtils.isNotBlank(autorizador)) {
            query.setParameter("autorizador", autorizador);
        }
        if (StringUtils.isNotBlank(curp)) {
            query.setParameter("curp", curp);
        }
        if (StringUtils.isNotBlank(tipoTramite)) {
            query.setParameter("tipoTramite", TipoRegularizacionSolicitudCDAEnum.fromDesc(tipoTramite).getIdTipoTramite());
        }
        if (StringUtils.isNotBlank(fechaActualizacion)) {
            query.setParameter("fechaActualizacionInicial", fechaActualizacion + " 00:00:00");
            query.setParameter("fechaActualizacionFinal", fechaActualizacion + " 23:59:59");
        }
        if (StringUtils.isNotBlank(fechaSolicitud)) {
            query.setParameter("fechaSolicitudInicial", fechaSolicitud + " 00:00:00");
            query.setParameter("fechaSolicitudFinal", fechaSolicitud + " 23:59:59");
        }
        if (StringUtils.isNotBlank(vencido)) {
            query.setParameter("vencido", EstadoTramiteEnum.VENCIDA.getCodigo());
        }
        if (StringUtils.isNotBlank(subdelegacion)) {
            query.setParameter("subdelegacion", "%subdelegacion&quot;:&quot;" + subdelegacion + "&quot;%");
        }

        return Long.parseLong(query.getSingleResult().toString());
    }

    /**
     * Metodo para el folio de la solicitud basada en el Nss enviado
     */
    private String obtenerFolioParaBusquedaPorNss(final String usuario, final String subdelegacion, final String nss, boolean isHistorico, Long idProceso) {
        StringBuffer sql = new StringBuffer(50);
        sql.append(" SELECT distinct sol.REF_FOLIO FROM DIT_SOLICITUD sol");
        sql.append(" INNER JOIN DIT_TRAMITE tra on sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD");
        sql.append(" INNER JOIN DIT_INSTANCIA inst on tra.CVE_ID_TRAMITE = inst.CVE_ID_TRAMITE");
        sql.append(" INNER JOIN DIT_TAREA_USUARIO tarusu on tarusu.CVE_ID_INSTANCIA = inst.CVE_ID_INSTANCIA");
        sql.append(" INNER JOIN DIT_CORRECCION_DATOS_ASEG corr on corr.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE");
        sql.append(" INNER JOIN DIT_DETALLE_NSS_CDA det on det.CVE_ID_CORRECCION_DATOS_ASEG = corr.CVE_ID_CORRECCION_DATOS_ASEG");
        sql.append(" WHERE inst.CVE_ID_EDO_INSTANCIA = :idEstadoInstancia ");

        if (isHistorico) {
            sql.append(" AND sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitudA or sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitudB");
        } else {
            sql.append(" AND (tarusu.CVE_ID_EDO_TAREA = :estadoA or tarusu.CVE_ID_EDO_TAREA = :estadoB  or tarusu.CVE_ID_EDO_TAREA = :estadoC) ");
            sql.append(" AND sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitud");
        }

        sql.append(" AND inst.cve_id_proceso = :procesoCda");
        sql.append(" AND det.CVE_NSS = :nss ");

        if (StringUtils.isNotBlank(usuario)) {
            sql.append(" AND tarusu.CVE_USUARIO= :usuario ");
        } else {
            //FILTRA SUBDELEGACION
            if (StringUtils.isNotBlank(subdelegacion)) {
                sql.append(" AND inst.DES_BDOC_INSTANCIA like :subdelegacion ");
            }
        }

        System.out.println("Query obtenerFolioParaBusquedaPorNss:" + sql.toString());

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("nss", nss);
        query.setParameter("procesoCda", idProceso);

        if (isHistorico) {
            query.setParameter("estadoSolicitudA", EstadoSolicitudEnum.ATENDIDA.getCodigo());
            query.setParameter("estadoSolicitudB", EstadoSolicitudEnum.CANCELADA.getCodigo());
            query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.TERMINADA.getClave());
        } else {
            query.setParameter("estadoA", EstadoTareaUsuarioEnum.ACTIVA.getClave());
            query.setParameter("estadoB", EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
            query.setParameter("estadoC", EstadoTareaUsuarioEnum.COMPLETADA.getClave());
            query.setParameter("estadoSolicitud", EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
            query.setParameter("idEstadoInstancia", EstadoInstanciaEnum.ACTIVA.getClave());
        }

        if (StringUtils.isNotBlank(usuario)) {
            query.setParameter("usuario", usuario);
        }

        if (StringUtils.isNotBlank(subdelegacion)) {
            query.setParameter("subdelegacion", "%data>{%subdelegacion%" + subdelegacion + "%");
        }

        return query.getSingleResult().toString();
    }

    /**
     * Metodo para obtener la ultima instancia activa por subdelegacion para CDA
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerUltimaInstanciaPorSubdelegacion(DataPage dataPage, final String subdelegacion) {
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT i FROM Instancia i where i.bpId = (");
        sql.append("SELECT max(i.bpId) FROM Instancia i ");
        sql.append("WHERE i.proceso.bp = :idProceso ");
        sql.append("AND i.estadoInstancia.idEstadoInstancia = :idEstado ");
        sql.append("AND i.bDoc like :subdelegacion ");
        sql.append("AND i.bDoc not like :responsable )");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idProceso", 1L);
        query.setParameter("idEstado", 1L);
        query.setParameter("subdelegacion", "%data>{%subdelegacion&quot;:&quot;" + subdelegacion + "&quot;%");
        query.setParameter("responsable", "%<string>SIN_RESPONSABLE</string>%");
        dataPage.setData(query.getResultList());
        return dataPage;
    }

    private String generarQueryEstadoUsuario(String estado) {
        String cadena = "";
        if (!EstadoNegocioEnum.VENCIDA.getDescripcion().equals(estado)) {

            cadena = " AND inst.DES_BDOC_INSTANCIA LIKE :estado ";
            cadena = cadena + " AND not exists ( select null from DIT_SOLICITUD solb ";
            cadena = cadena + " INNER JOIN DIT_TRAMITE trab on solb.CVE_ID_SOLICITUD = trab.CVE_ID_SOLICITUD ";
            cadena = cadena + " INNER JOIN DIT_INSTANCIA instb on trab.CVE_ID_TRAMITE = instb.CVE_ID_TRAMITE ";
            cadena = cadena + " INNER JOIN DIT_TAREA_USUARIO tarusub on tarusub.CVE_ID_INSTANCIA = instb.CVE_ID_INSTANCIA ";
            cadena = cadena + " WHERE ";
            cadena = cadena + " instb.DES_BDOC_INSTANCIA NOT LIKE :estado ";
            cadena = cadena + " and solb.CVE_ID_SOLICITUD= sol.CVE_ID_SOLICITUD ) ";

        } else {
            cadena = " AND inst.FEC_REGISTRO_ACTUALIZADO < :fechaLimite ";
        }
        return cadena;
    }

    private void asignarParametroUsuario(Query query, String estado) {
        if (!EstadoNegocioEnum.VENCIDA.getDescripcion().equals(estado)) {
            query.setParameter("estado", "%<estatus>" + estado + "</estatus>%");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, -DIAS_MAXIMOS_ESPERA);
            query.setParameter("fechaLimite", c.getTime());
        }
    }

    private String generarQueryEstadoSubdelegacion(String estado) {
        String cadena = "";
        if (!EstadoNegocioEnum.VENCIDA.getDescripcion().equals(estado)) {
            cadena = " AND t.instancia.bDoc like :estado ";
        } else {
            cadena = " AND t.instancia.datosAuditoria.fecRegistroActualizado < :fechaLimite ";
        }
        return cadena;
    }

    private void asignarParametroSubdelegacion(Query query, String estado) {
        if (!EstadoNegocioEnum.VENCIDA.getDescripcion().equals(estado)) {
            query.setParameter("estado", "%<estatus>" + estado + "</estatus>%");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, -DIAS_MAXIMOS_ESPERA);
            query.setParameter("fechaLimite", c.getTime());
        }
    }

    private void agregarFiltrosBDoc(StringBuffer sql, HashMap<String, Object> listFilterBDoc) {
        if (Utilerias.isNotEmpty((List<String>) listFilterBDoc.get("filtroEstados"))) {
            List<String> estados = (List<String>) listFilterBDoc.get("filtroEstados");
            sql.append("AND (");
            for (int i = 0; i < estados.size(); i++) {
                if (i == 0) {
                    sql.append("t.instancia.bDoc like :estado_").append(i).append(" ");
                } else {
                    sql.append("OR t.instancia.bDoc like :estado_").append(i).append(" ");
                }
            }
            if (estados.size() == 1) {
                sql.append("OR t.instancia.bDoc like :tarea");
            }
            sql.append(") ");
        }
        if (StringUtils.isNotBlank((String) listFilterBDoc.get("filtroSubdelegacionControl"))) {

            sql.append("AND t.instancia.bDoc like :subdelegacionControl ");

        }

        if (StringUtils.isNotBlank((String) listFilterBDoc.get("filtroPortabilidad"))) {
            sql.append("AND t.tarea.idTarea in (8,9) ");
        } else {
            sql.append("AND t.tarea.idTarea not in (8,9) ");
        }
    }

    private void asignarParametrosBDoc(Query query, HashMap<String, Object> listFilterBDoc) {

        if (Utilerias.isNotEmpty((List<String>) listFilterBDoc.get("filtroEstados"))) {
            List<String> estados = (List<String>) listFilterBDoc.get("filtroEstados");
            for (int i = 0; i < estados.size(); i++) {
                query.setParameter("estado_" + i, "%<estatus>" + estados.get(i) + "</estatus>%");
            }
            if (estados.size() == 1) {
                query.setParameter("tarea", "%tarea&quot;:&quot;" + estados.get(0) + "&quot;%");
            }
        }
        if (StringUtils.isNotBlank((String) listFilterBDoc.get("filtroSubdelegacionControl"))) {
            query.setParameter("subdelegacionControl", "%subdelegacionControl&quot;:&quot;" + listFilterBDoc.get("filtroSubdelegacionControl") + "&quot;%");


        }
    }


    /**
     * Metodo para obtener una lista de tareas por usuario normativo
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerTareasPorUsuarioNormativo(DataPage dataPage, List<Long> estadosTareaUsuario, List<Long> idsProcesos) {
        LOGGERBPM.trace("obtenerTareasPorUsuarioNormativo Principal");

        LOGGERBPM.trace("TotalOfRecords :: " + dataPage.getTotalOfRecords());

        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>)dataPage.getData();

        LOGGERBPM.trace("listFilter :: " + listFilter.size());
        for (HashMap<String, Object> filters : listFilter) {
            LOGGERBPM.trace("\n");
            for (String key : filters.keySet()) {
                if (filters.get(key) != null)
                    LOGGERBPM.trace("            " + key + " :: " + filters.get(key));
            }
        }

        List filtrosTipoTramite = (List) listFilter.get(0).get("filtrosTipoTramite");
        List filtrosTipoSolicitud = (List) listFilter.get(0).get("filtrosTipoSolicitud");
        if (Utilerias.isNotEmpty(filtrosTipoTramite) && Utilerias.isNotEmpty(filtrosTipoTramite)
        && filtrosTipoTramite .size() == 3 &&  filtrosTipoSolicitud.size() == 3) {
            LOGGERBPM.trace("Debe aplicar CONSULTA NACIONAL");
            return obtenerTareasPorUsuarioNormativo_V1(dataPage, estadosTareaUsuario, idsProcesos);
        } else {
            LOGGERBPM.trace("Ejecutar Version 2 de la query ");
            return obtenerTareasPorUsuarioNormativo_V2(dataPage, estadosTareaUsuario, idsProcesos)  ;
        }
    }


    /**
     * Metodo para obtener una lista de tareas por usuario normativo V2
     */
    private DataPage obtenerTareasPorUsuarioNormativo_V2(DataPage dataPage, List<Long> estadosTareaUsuario, List<Long> idsProcesos) {
        LOGGERBPM.trace("obtenerTareasPorUsuarioNormativoV2");

        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();

        long maxResults = obtenerTotalTareasPorUsuarioNormativo_V2(dataPage, estadosTareaUsuario, idsProcesos);
        long totalOfPages = (long)Math.ceil((maxResults * 10L / dataPage.getPageSize()) / 10.0D);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages)
            dataPage.setCurrentPage(totalOfPages);
        if (maxResults > 0L) {
            StringBuffer sql = new StringBuffer(3500);
            sql.append(armarConsultaBaseV2(listFilter, idsProcesos, false));
            Query query = this.entityManager.createNativeQuery(sql.toString());
            LOGGERBPM.trace("Actualizar timeout a 600000");
            query.setHint("javax.persistence.query.timeout", 600000);

            if (StringUtils.isNotBlank((String) listFilter.get(0).get("sinPaginacion"))) {
                dataPage.setCurrentPage(1L); }

            int firstResult = (int)(dataPage.getCurrentPage() - 1L) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            LOGGERBPM.trace(query.toString());
            List resultados = query.getResultList();
            LOGGERBPM.trace("Se obtuvieron resultados de la página " +
                    "[" + dataPage.getCurrentPage() + "] :: " +( resultados != null ? resultados.size() : "NULL"));
            dataPage.setData(resultados);
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    /**
     * Metodo para obtener cantidad todal de tareas por usuario normativo V2
     */
    private long obtenerTotalTareasPorUsuarioNormativo_V2(DataPage dataPage, List<Long> estadosTareaUsuario, List<Long> idsProcesos) {
        LOGGERBPM.trace("obtenerTotalTareasPorUsuarioNormativo V2");

        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();

        LOGGERBPM.trace("estadosTareaUsuario :: " + estadosTareaUsuario);

        StringBuffer sql = new StringBuffer(3500);
        sql.append("SELECT count(*) FROM (");
        sql.append(armarConsultaBaseV2(listFilter, idsProcesos, true));
        sql.append(")");
        Query query = this.entityManager.createNativeQuery(sql.toString());
        LOGGERBPM.trace("Actualizar timeout a 600000");
        query.setHint("javax.persistence.query.timeout", 600000);

        LOGGERBPM.trace("Query :: \n" + query);

        Object result = query.getSingleResult();

        LOGGERBPM.trace(result + "  " +( result != null ? result.getClass().getName() : "NULL"));

        long totalTareasPorUsuarioNormativo  = ((BigDecimal) result!= null) ? ((BigDecimal) result).longValue() : 0L;

        LOGGERBPM.trace("TotalTareasPorUsuarioNormativo :: " + totalTareasPorUsuarioNormativo);

        return totalTareasPorUsuarioNormativo;
    }

    /**
     * Metodo para armar consulta base para obtener resultados de Tareas por Usuario Normativo V2
     */
    private StringBuffer armarConsultaBaseV2(List<HashMap<String, Object>> listFilter, List<Long> idsProcesos, boolean contador) {
        LOGGERBPM.trace("Armado de query BASE V2 [Tareas por Usuario Normativo ]>>>> TareasPorUsuarioNormativo");

        StringBuffer sql = new StringBuffer(3500);
        sql.append("SELECT REF_FOLIO");
        if (!contador) {
            sql.append(", CVE_ID_SOLICITUD, CVE_ID_TAREA_USUARIO, CVE_ID_INSTANCIA, ");
            sql.append("tarea_instancia.CVE_ID_TRAMITE, CVE_ID_ESTADO_TRAMITE, CVE_ID_EDO_TAREA, CVE_ID_TAREA, ");
            sql.append("CVE_USUARIO, DES_BDOC_TAREA, DES_BDOC_INSTANCIA, FEC_REGISTRO_ACTUALIZADO, FEC_REGISTRO_ALTA, ");
            sql.append("FEC_INI_TAREA, FEC_FIN_TAREA, FEC_SOLICITUD, CVE_ID_SUBDELEGACION, DES_DELEG, DES_SUBDELEGACION");
        }
        sql.append("\n");
        sql.append("FROM (   \n");
        sql.append("        SELECT CVE_ID_TRAMITE, CVE_ID_ESTADO_TRAMITE, tramite.CVE_ID_SOLICITUD, FEC_SOLICITUD, REF_FOLIO \n");
        sql.append("        FROM    (   SELECT CVE_ID_TRAMITE, CVE_ID_ESTADO_TRAMITE, CVE_ID_SOLICITUD\n");
        sql.append("                    FROM    (   SELECT CVE_ID_TRAMITE, CVE_ID_SOLICITUD, CVE_ID_ESTADO_TRAMITE \n");
        sql.append("                                FROM " + DB_USER + "DIT_TRAMITE\n");
        sql.append("                                WHERE CVE_ID_ESTADO_TRAMITE  ");
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstadoTramite"))) {
            sql.append("= ").append((String) listFilter.get(0).get("filtroEstadoTramite"));
        } else {
            sql.append("IN (1, 2)");
        }
        sql.append("\n");
        if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoTramite")).booleanValue()) {
            List<Long> tiposTramite = (List<Long>)((HashMap)listFilter.get(0)).get("filtrosTipoTramite");
            sql.append("                                  AND CVE_ID_TIPO_TRAMITE IN (");
            sql.append(tiposTramite.get(0)).append(" ");
            if (tiposTramite.size() > 1)
                for (int i = 1; i < tiposTramite.size(); i++) {
                    sql.append(", ").append(tiposTramite.get(i));
                }
            sql.append(") \n");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial")) &&
                StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("                                  AND TRUNC(FEC_REGISTRO_ALTA) ");
            sql.append("BETWEEN TRUNC(TO_DATE('").append((String) listFilter.get(0).get("fechaInicial")).append(" 00:00:00', 'dd/mm/yyyy hh24:mi:ss') ) ");
            sql.append("AND TRUNC(TO_DATE('").append((String) listFilter.get(0).get("fechaFinal")).append(" 23:59:59', 'dd/mm/yyyy hh24:mi:ss') )\n");
        }
        sql.append("                )   )tramite\n");
        sql.append("                INNER JOIN (    SELECT CVE_ID_SOLICITUD, CVE_ID_SUBDELEGACION, FEC_SOLICITUD, REF_FOLIO");
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCapturaSolicitud"))) {
            sql.append(" , CVE_ID_ORIGEN_SOLICITUD");
        }
        sql.append("\n");
        sql.append("                                FROM " + DB_USER + "DIT_SOLICITUD \n");
        sql.append("                                WHERE CVE_ID_ESTADO_SOLICITUD <> 3 \n");
        if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud")).booleanValue()) {
            List<Long> tiposSolicitud = (List<Long>)((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud");
            LOGGERBPM.trace("filtrosTipoSolicitud :: " + tiposSolicitud);
            sql.append("                                    AND CVE_ID_TIPO_SOLICITUD IN (");
            sql.append(tiposSolicitud.get(0));
            if (tiposSolicitud.size() > 1)
                for (int i = 1; i < tiposSolicitud.size(); i++)
                    sql.append(", ").append(tiposSolicitud.get(i));
            sql.append(") \n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial")) &&
                StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("                                    AND TRUNC(FEC_SOLICITUD)  ");
            sql.append("BETWEEN TRUNC(TO_DATE('").append((String) listFilter.get(0).get("fechaInicial")).append(" 00:00:00', 'dd/mm/yyyy hh24:mi:ss') ) ");
            sql.append("AND TRUNC(TO_DATE('").append((String) listFilter.get(0).get("fechaFinal")).append(" 23:59:59', 'dd/mm/yyyy hh24:mi:ss') )\n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCapturaSolicitud"))) {
            sql.append("                                    AND CVE_ID_ORIGEN_SOLICITUD <> 2 \n");
        }

        sql.append("                            ) solicitud ON tramite.CVE_ID_SOLICITUD = solicitud.CVE_ID_SOLICITUD\n");
        sql.append("    )tramite_solicitud\n");
        sql.append("    INNER JOIN (    SELECT  CVE_ID_TAREA_USUARIO, CVE_ID_INSTANCIA, CVE_ID_TRAMITE, CVE_ID_EDO_TAREA, ");
        sql.append("CVE_ID_TAREA, CVE_USUARIO, DES_BDOC_TAREA, DES_BDOC_INSTANCIA,  FEC_REGISTRO_ACTUALIZADO, FEC_REGISTRO_ALTA,  ");
        sql.append("FEC_INI_TAREA, FEC_FIN_TAREA, CVE_ID_SUBDELEGACION,  DES_DELEG, DES_SUBDELEGACION\n");
        sql.append("                    FROM (  SELECT instancia1.CVE_ID_TRAMITE, instancia1.CVE_ID_SUBDELEGACION,  instancia1.CVE_ID_INSTANCIA, instancia1.DES_BDOC_INSTANCIA, dicdelega1.DES_DELEG, dicsubdele1.DES_SUBDELEGACION,\n");
        sql.append("                                    MAX(tareausuario1.CVE_ID_TAREA_USUARIO) KEEP (DENSE_RANK FIRST ORDER BY tareausuario1.FEC_FIN_TAREA DESC) CVE_ID_TAREA_USUARIO,\n");
        sql.append("                                    MAX(tareausuario1.CVE_ID_EDO_TAREA) KEEP (DENSE_RANK FIRST ORDER BY tareausuario1.FEC_FIN_TAREA DESC) CVE_ID_EDO_TAREA,\n");
        sql.append("                                    MAX(tareausuario1.CVE_ID_TAREA) KEEP (DENSE_RANK FIRST ORDER BY tareausuario1.FEC_FIN_TAREA DESC) CVE_ID_TAREA,\n");
        sql.append("                                    MAX(tareausuario1.CVE_USUARIO) KEEP (DENSE_RANK FIRST ORDER BY tareausuario1.FEC_FIN_TAREA DESC) CVE_USUARIO,\n");
        sql.append("                                    MAX(tareausuario1.DES_BDOC_TAREA) KEEP (DENSE_RANK FIRST ORDER BY tareausuario1.FEC_FIN_TAREA DESC) DES_BDOC_TAREA,\n");
        sql.append("                                    MAX(tareausuario1.FEC_REGISTRO_ACTUALIZADO) KEEP (DENSE_RANK FIRST ORDER BY tareausuario1.FEC_FIN_TAREA DESC) FEC_REGISTRO_ACTUALIZADO,\n");
        sql.append("                                    MAX(tareausuario1.FEC_REGISTRO_ALTA) KEEP (DENSE_RANK FIRST ORDER BY tareausuario1.FEC_FIN_TAREA DESC) FEC_REGISTRO_ALTA,\n");
        sql.append("                                    MAX(tareausuario1.FEC_INI_TAREA) KEEP (DENSE_RANK FIRST ORDER BY tareausuario1.FEC_FIN_TAREA DESC) FEC_INI_TAREA,\n");
        sql.append("                                    MAX(tareausuario1.FEC_FIN_TAREA) KEEP (DENSE_RANK FIRST ORDER BY tareausuario1.FEC_FIN_TAREA DESC) FEC_FIN_TAREA\n");
        sql.append("                            FROM ( SELECT *  FROM " + DB_USER + "DIT_TAREA_USUARIO " );
        if(!isStage)        sql.append("WHERE cve_id_tarea_usuario > 462000 " );
        sql.append(    ")   tareausuario1 \n");
        sql.append("                                INNER JOIN (    SELECT CVE_ID_INSTANCIA, CVE_ID_PROCESO, CVE_ID_EDO_INSTANCIA, DES_BDOC_INSTANCIA, CVE_ID_TRAMITE,\n");
        sql.append("                                                        REGEXP_SUBSTR(DES_BDOC_INSTANCIA,'subdelegacion");

        boolean isCapPreventiva = false;
        if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoTramite")).booleanValue()) {
            List<Long> tiposTramite = (List<Long>)((HashMap)listFilter.get(0)).get("filtrosTipoTramite");
            if(tiposTramite.contains(153L)){
                if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud")).booleanValue()) {
                    List<Long> tiposSolicitud = (List<Long>)((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud");
                    if(tiposSolicitud.contains(57L)){
                        isCapPreventiva = true;
                    }
                }
            }
        }

        if(!isCapPreventiva){
            sql.append("Control");
        }

        sql.append("&quot;:&quot;([0-9]+)&quot;', 1, 1, NULL, 1) CVE_ID_SUBDELEGACION\n");

        sql.append("                                                FROM " + DB_USER + "DIT_INSTANCIA\n");
        sql.append("                                                WHERE " );
        if(!isStage) sql.append("CVE_ID_INSTANCIA > 202000 AND " );
        sql.append("CVE_ID_EDO_INSTANCIA IN (1, 2) \n");
        sql.append("                                                    AND CVE_ID_PROCESO IN (");
        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            sql.append(idsProcesos.get(0)).append(" ");
            if (idsProcesos.size() > 1)
                for (int i = 1; i < idsProcesos.size(); i++)
                    sql.append(", ").append(idsProcesos.get(i));
        } else {
            sql.append(" 2, 3 ");
        }
        sql.append(") \n");
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial")) &&
                StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("                                                    AND TRUNC(FEC_REGISTRO_ALTA) ");
            sql.append("BETWEEN TRUNC(TO_DATE('").append((String) listFilter.get(0).get("fechaInicial")).append(" 00:00:00', 'dd/mm/yyyy hh24:mi:ss') ) ");
            sql.append("AND TRUNC(TO_DATE('").append((String) listFilter.get(0).get("fechaFinal")).append(" 23:59:59', 'dd/mm/yyyy hh24:mi:ss') )\n");
        }

        sql.append("                                            ) instancia1  ON tareausuario1.CVE_ID_INSTANCIA = instancia1.CVE_ID_INSTANCIA\n");
        sql.append("                                LEFT JOIN  " + DB_USER + "DIC_SUBDELEGACION dicsubdele1 ON instancia1.CVE_ID_SUBDELEGACION = dicsubdele1.CVE_ID_SUBDELEGACION\n");
        sql.append("                                LEFT JOIN  " + DB_USER + "DIC_DELEGACION dicdelega1  ON dicsubdele1.CVE_ID_DELEGACION = dicdelega1.CVE_ID_DELEGACION \n");
        sql.append("                            WHERE tareausuario1.CVE_ID_EDO_TAREA in (1, 2, 3) \n");

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
            sql.append("                                AND (DES_BDOC_TAREA LIKE '%<estatus>");
            sql.append((String) listFilter.get(0).get("filtroEstado")).append("</estatus>%' OR DES_BDOC_INSTANCIA LIKE '%<estatus>");
            sql.append((String) listFilter.get(0).get("filtroEstado")).append("</estatus>%') \n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroDelegacionControl")) ) {
            sql.append("                                AND DES_BDOC_INSTANCIA LIKE '%;delegacionControl&quot;:&quot;");
            sql.append((String) listFilter.get(0).get("filtroDelegacionControl"));
            sql.append("&quot;%' \n");
            sql.append("                                AND dicsubdele1.CVE_ID_DELEGACION = ");
            sql.append((String) listFilter.get(0).get("filtroDelegacionControl"));
            sql.append(" \n");
        }

        if(StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacionControl"))){
            sql.append("                                AND DES_BDOC_INSTANCIA LIKE '%;subdelegacionControl&quot;:&quot;");
            sql.append((String) listFilter.get(0).get("filtroSubdelegacionControl")).append("\n");
            sql.append("&quot;%' \n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroDelegacion")) ) {
            sql.append("                                AND DES_BDOC_INSTANCIA LIKE '%;delegacion&quot;:&quot;");
            sql.append((String) listFilter.get(0).get("filtroDelegacion")).append("&quot;%' \n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacion"))) {
            sql.append("                                AND DES_BDOC_INSTANCIA LIKE '%;subdelegacion&quot;:&quot;");
            sql.append((String) listFilter.get(0).get("filtroSubdelegacion")).append("&quot;%' \n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroDelegacionOrigen")) ) {
            sql.append("                                AND DES_BDOC_INSTANCIA LIKE '%;delegacionOrigen&quot;:&quot;");
            sql.append((String) listFilter.get(0).get("filtroDelegacionOrigen")).append("&quot;%' \n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacionOrigen"))) {
            sql.append("                                AND DES_BDOC_INSTANCIA LIKE '%;subdelegacionOrigen&quot;:&quot;");
            sql.append((String) listFilter.get(0).get("filtroSubdelegacionOrigen")).append("&quot;%' \n");
        }


        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
            sql.append("                                AND DES_BDOC_INSTANCIA LIKE '%nss&quot;:&quot;");
            sql.append((String) listFilter.get(0).get("filtroNss")).append("%' \n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
            sql.append("                                AND DES_BDOC_INSTANCIA LIKE '%curp&quot;:&quot;");
            sql.append((String) listFilter.get(0).get("filtroCurp")).append("%' \n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            sql.append("                                AND DES_BDOC_INSTANCIA LIKE '%folio>");
            sql.append((String) listFilter.get(0).get("filtroFolio")).append("%' \n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroTipoAclaracion"))) {
            sql.append("                                AND DES_BDOC_INSTANCIA LIKE '%tipoAclaracion&quot;:&quot;");
            sql.append((String) listFilter.get(0).get("filtroTipoAclaracion")).append("&quot;%' \n");
        }

        sql.append("                            GROUP BY  instancia1.CVE_ID_TRAMITE,instancia1.CVE_ID_SUBDELEGACION, instancia1.CVE_ID_INSTANCIA, instancia1.DES_BDOC_INSTANCIA, \n");
        sql.append("                                      dicdelega1.DES_DELEG, dicsubdele1.DES_SUBDELEGACION )\n");
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroUsuario"))) {
            sql.append("                    WHERE CVE_USUARIO = '");
            sql.append((String) listFilter.get(0).get("filtroUsuario")).append("' \n");
        }
        sql.append("                ) tarea_instancia ON tarea_instancia.CVE_ID_TRAMITE = tramite_solicitud.CVE_ID_TRAMITE\n");

        if (!contador)
            sql.append("ORDER BY CVE_ID_INSTANCIA DESC, FEC_REGISTRO_ACTUALIZADO DESC ");
        LOGGERBPM.trace("Consulta armada :: \n" + sql.toString());
        return sql;
    }






    /**
     * Metodo para obtener una lista de tareas por usuario normativo Version 1: Consulta Nacional
     */

    private DataPage obtenerTareasPorUsuarioNormativo_V1(
            DataPage dataPage, List<Long> estadosTareaUsuario, List<Long> idsProcesos) {
        LOGGERBPM.trace("obtenerTareasPorUsuarioNormativo V1");
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>)dataPage.getData();

        StringBuffer sql = new StringBuffer(1500);

        long maxResults = obtenerTotalTareasPorUsuarioNormativo_V1(dataPage, estadosTareaUsuario, idsProcesos);
        long totalOfPages = (long)Math.ceil((maxResults * 10L / dataPage.getPageSize()) / 10.0D);

        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages)
            dataPage.setCurrentPage(totalOfPages);
        if (maxResults > 0L) {
            sql.append(armarConsultaBaseV1(listFilter, idsProcesos, false));

            Query query = this.entityManager.createNativeQuery(sql.toString());

            LOGGERBPM.trace("Actualizar timeout a 600000");
            query.setHint("javax.persistence.query.timeout", 600000);


            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("sinPaginacion")))
                dataPage.setCurrentPage(1L);
            int firstResult = (int)(dataPage.getCurrentPage() - 1L) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    /**
     * Metodo para obtener cantidad todal de tareas por usuario normativo V1: Consulta Nacional
     */
    private long obtenerTotalTareasPorUsuarioNormativo_V1(
            DataPage dataPage, List<Long> estadosTareaUsuario, List<Long> idsProcesos) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>)dataPage.getData();
        LOGGERBPM.trace("obtenerTotalTareasPorUsuarioNormativo V1");

        StringBuffer sql = new StringBuffer(1500);
        sql.append("SELECT count(*) FROM (");
        sql.append(armarConsultaBaseV1(listFilter, idsProcesos, true));
        sql.append(")");

        Query query = this.entityManager.createNativeQuery(sql.toString());

        LOGGERBPM.trace("Actualizar timeout a 600000");
        query.setHint("javax.persistence.query.timeout", 600000);

        LOGGERBPM.trace("Query :: \n" + query.toString());
        Object result = query.getSingleResult();

        LOGGERBPM.trace(result + "  " + result.getClass().getName());
        Long totalTareasPorUsuarioNormativo = Long.valueOf(((BigDecimal)result).longValue());

        LOGGERBPM.trace("TotalTareasPorUsuarioNormativo :: " + totalTareasPorUsuarioNormativo);

        return totalTareasPorUsuarioNormativo.longValue();
    }


    /**
     * Metodo para armar consulta base para obtener resultados de Tareas por Usuario Normativo V1: Consulta Nacional
     */
    private StringBuffer armarConsultaBaseV1(List<HashMap<String, Object>> listFilter, List<Long> idsProcesos, boolean contador) {
        LOGGERBPM.trace("Armado de query BASE V1 [Tareas por Usuario Normativo :: Consulta Nacional ]>>>> TareasPorUsuarioNormativo");

        StringBuffer sql = new StringBuffer(4500);


        sql.append("SELECT");
        if(contador) {
            sql.append(" * \n");
        } else {
            sql.append(" solicitud0.REF_FOLIO, solicitud0.CVE_ID_SOLICITUD, tareausuario0.CVE_ID_TAREA_USUARIO, \n");
            sql.append("       instancia0.CVE_ID_INSTANCIA, tramite0.CVE_ID_TRAMITE, tramite0.CVE_ID_ESTADO_TRAMITE, \n");
            sql.append("       tareausuario0.CVE_ID_EDO_TAREA, tareausuario0.CVE_ID_TAREA, tareausuario0.CVE_USUARIO, \n");
            sql.append("       tareausuario0.DES_BDOC_TAREA, instancia0.DES_BDOC_INSTANCIA, tareausuario0.FEC_REGISTRO_ACTUALIZADO, \n");
            sql.append("       tareausuario0.FEC_REGISTRO_ALTA, tareausuario0.FEC_INI_TAREA, tareausuario0.FEC_FIN_TAREA, \n");
            sql.append("       solicitud0.FEC_SOLICITUD, solicitud0.CVE_ID_SUBDELEGACION,dicdelega0.DES_DELEG,dicsubdele0.DES_SUBDELEGACION \n");
        }
        sql.append("FROM  " + DB_USER + "DIT_TAREA_USUARIO tareausuario0\n");
        sql.append("        INNER JOIN  " + DB_USER + "DIT_INSTANCIA instancia0 ON tareausuario0.CVE_ID_INSTANCIA = instancia0.CVE_ID_INSTANCIA\n");
        sql.append("        INNER JOIN  " + DB_USER + "DIT_TRAMITE  tramite0 ON instancia0.CVE_ID_TRAMITE = tramite0.CVE_ID_TRAMITE\n");
        sql.append("        INNER JOIN  " + DB_USER + "DIT_SOLICITUD solicitud0 ON tramite0.CVE_ID_SOLICITUD = solicitud0.CVE_ID_SOLICITUD\n");
        sql.append("         LEFT JOIN  " + DB_USER + "DIC_SUBDELEGACION dicsubdele0 ON solicitud0.CVE_ID_SUBDELEGACION = dicsubdele0.CVE_ID_SUBDELEGACION\n");
        sql.append("         LEFT JOIN  " + DB_USER + "DIC_DELEGACION dicdelega0 ON dicsubdele0.CVE_ID_DELEGACION = dicdelega0.CVE_ID_DELEGACION\n");
        sql.append("WHERE tareausuario0.CVE_ID_TAREA_USUARIO IN (\n");
        sql.append("        SELECT MAX(tareausuario1.CVE_ID_TAREA_USUARIO) KEEP(dense_rank FIRST ORDER BY tareausuario1.FEC_REGISTRO_ALTA desc)\n");
        sql.append("        FROM (SELECT * FROM  " + DB_USER + "DIT_TAREA_USUARIO " );
        if(!isStage) sql.append("WHERE cve_id_tarea_usuario > 462000 " );
        sql.append(")tareausuario1\n");
        sql.append("                INNER JOIN (SELECT * FROM  " + DB_USER + "DIT_INSTANCIA " );
        if(!isStage)sql.append("WHERE CVE_ID_INSTANCIA > 202000" );
        sql.append(") instancia1\n");
        sql.append("                            ON tareausuario1.CVE_ID_INSTANCIA = instancia1.CVE_ID_INSTANCIA\n");
        sql.append("                INNER JOIN   " + DB_USER + "DIT_TRAMITE  tramite1 ON instancia1.CVE_ID_TRAMITE = tramite1.CVE_ID_TRAMITE\n");
        sql.append("                INNER JOIN (SELECT * FROM  " + DB_USER + "DIT_SOLICITUD WHERE CVE_ID_ESTADO_SOLICITUD <> 3) solicitud1\n");
        sql.append("                            ON tramite1.CVE_ID_SOLICITUD = solicitud1.CVE_ID_SOLICITUD\n");
        sql.append("                LEFT JOIN  " + DB_USER + "DIC_SUBDELEGACION dicsubdele1 ON solicitud1.CVE_ID_SUBDELEGACION = dicsubdele1.CVE_ID_SUBDELEGACION\n");
        sql.append("                LEFT JOIN  " + DB_USER + "DIC_DELEGACION dicdelega1 ON dicsubdele1.CVE_ID_DELEGACION = dicdelega1.CVE_ID_DELEGACION\n");
        sql.append("        WHERE tareausuario1.CVE_ID_EDO_TAREA in (1, 2, 3)\n");
        sql.append("                AND instancia1.CVE_ID_EDO_INSTANCIA in (1, 2)\n");
        sql.append("                AND tramite1.CVE_ID_ESTADO_TRAMITE <> 7\n");


        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
            sql.append("                AND instancia1.DES_BDOC_INSTANCIA LIKE '%nss&quot;:&quot;");
            sql.append((String) listFilter.get(0).get("filtroNss")).append("%' \n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
            sql.append("                AND instancia1.DES_BDOC_INSTANCIA LIKE '%curp&quot;:&quot;");
            sql.append((String) listFilter.get(0).get("filtroCurp")).append("%' \n");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            sql.append("                AND instancia1.DES_BDOC_INSTANCIA LIKE '%<folio>");
            sql.append((String) listFilter.get(0).get("filtroFolio")).append("%' \n");
        }

        if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoTramite")).booleanValue()) {
            List<Long> tiposTramite = (List<Long>)((HashMap)listFilter.get(0)).get("filtrosTipoTramite");
            sql.append("                AND (   instancia1.DES_BDOC_INSTANCIA like '%idTipoTramite&quot;:&quot;");
            sql.append(tiposTramite.get(0)).append("&quot;%'\n");
            if (tiposTramite.size() > 1) {
                for (int i = 1; i < tiposTramite.size(); i++) {
                    sql.append("                        OR instancia1.DES_BDOC_INSTANCIA like '%idTipoTramite&quot;:&quot;");
                    sql.append(tiposTramite.get(i)).append("&quot;%'\n");
                }
            }
            sql.append(") \n");
        }

        if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud")).booleanValue()) {
            List<Long> tiposSolicitud = (List<Long>)((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud");
            LOGGERBPM.trace("filtrosTipoSolicitud :: " + tiposSolicitud);
            sql.append("                AND solicitud1.CVE_ID_TIPO_SOLICITUD IN (");
            sql.append(tiposSolicitud.get(0));
            if (tiposSolicitud.size() > 1) {
                for (int i = 1; i < tiposSolicitud.size(); i++) {
                    sql.append(", ").append(tiposSolicitud.get(i));
                }
            }
            sql.append(") \n");
        }

        sql.append("                AND instancia1.CVE_ID_PROCESO IN (\n" );

        if (idsProcesos != null && !idsProcesos.isEmpty()) {
            sql.append(idsProcesos.get(0)).append(" ");
            if (idsProcesos.size() > 1)
                for (int i = 1; i < idsProcesos.size(); i++)
                    sql.append(", ").append(idsProcesos.get(i));
        } else {
            sql.append(" 2, 3 ");
        }
        sql.append(") \n");

        sql.append("        GROUP BY tareausuario1.CVE_ID_INSTANCIA )\n" );
        if(!contador) {
            sql.append("ORDER BY ");
            sql.append("instancia0.CVE_ID_INSTANCIA DESC,  instancia0.FEC_REGISTRO_ACTUALIZADO DESC ");
        }

        LOGGERBPM.trace("Consulta armada :: \n" + sql.toString());
        return sql;
    }



























    /**
     * Metodo para obtener una lista de tareas por usuario normativo Version 0
     */

    public DataPage obtenerTareasPorUsuarioNormativo_V0(
            DataPage dataPage, List<Long> estadosTareaUsuario, List<Long> idsProcesos) {
        LOGGERBPM.trace("obtenerTareasPorUsuarioNormativo V0");
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>)dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        long maxResults = obtenerTotalTareasPorUsuarioNormativo_V0(dataPage, estadosTareaUsuario, idsProcesos);
        long totalOfPages = (long)Math.ceil((maxResults * 10L / dataPage.getPageSize()) / 10.0D);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages)
            dataPage.setCurrentPage(totalOfPages);
        if (maxResults > 0L) {
            sql.append("SELECT solicitud0.REF_FOLIO, solicitud0.CVE_ID_SOLICITUD, tareausuario0.CVE_ID_TAREA_USUARIO, instancia0.CVE_ID_INSTANCIA, tramite0.CVE_ID_TRAMITE, tramite0.CVE_ID_ESTADO_TRAMITE, tareausuario0.CVE_ID_EDO_TAREA, tareausuario0.CVE_ID_TAREA, tareausuario0.CVE_USUARIO, tareausuario0.DES_BDOC_TAREA, instancia0.DES_BDOC_INSTANCIA, tareausuario0.FEC_REGISTRO_ACTUALIZADO, tareausuario0.FEC_REGISTRO_ALTA, tareausuario0.FEC_INI_TAREA, tareausuario0.FEC_FIN_TAREA, solicitud0.FEC_SOLICITUD, solicitud0.CVE_ID_SUBDELEGACION,dicdelega0.DES_DELEG,dicsubdele0.DES_SUBDELEGACION ");
            sql.append("FROM " + DB_USER + "DIT_TAREA_USUARIO tareausuario0 ");
            sql.append("INNER JOIN " + DB_USER + "DIT_INSTANCIA instancia0 ON tareausuario0.CVE_ID_INSTANCIA = instancia0.CVE_ID_INSTANCIA ");
            sql.append("INNER JOIN " + DB_USER + "DIT_TRAMITE  tramite0 ON instancia0.CVE_ID_TRAMITE = tramite0.CVE_ID_TRAMITE ");
            sql.append("INNER JOIN " + DB_USER + "DIT_SOLICITUD solicitud0 ON tramite0.CVE_ID_SOLICITUD = solicitud0.CVE_ID_SOLICITUD ");
            sql.append("LEFT JOIN " + DB_USER + "DIC_SUBDELEGACION dicsubdele0 ON solicitud0.CVE_ID_SUBDELEGACION = dicsubdele0.CVE_ID_SUBDELEGACION ");
            sql.append("LEFT JOIN " + DB_USER + "DIC_DELEGACION dicdelega0 ON dicsubdele0.CVE_ID_DELEGACION = dicdelega0.CVE_ID_DELEGACION ");
            sql.append("WHERE tareausuario0.CVE_ID_TAREA_USUARIO IN ( ");
            sql.append("SELECT max(tareausuario1.CVE_ID_TAREA_USUARIO) keep(dense_rank FIRST order by tareausuario1.FEC_REGISTRO_ALTA desc) ");
            sql.append("FROM " + DB_USER + "DIT_TAREA_USUARIO tareausuario1 ");
            sql.append("INNER JOIN " + DB_USER + "DIT_INSTANCIA instancia1 ON tareausuario1.CVE_ID_INSTANCIA = instancia1.CVE_ID_INSTANCIA ");
            sql.append("INNER JOIN " + DB_USER + "DIT_TRAMITE  tramite1 ON instancia1.CVE_ID_TRAMITE = tramite1.CVE_ID_TRAMITE ");
            sql.append("INNER JOIN " + DB_USER + "DIT_SOLICITUD solicitud1 ON tramite1.CVE_ID_SOLICITUD = solicitud1.CVE_ID_SOLICITUD ");
            sql.append("LEFT JOIN " + DB_USER + "DIC_SUBDELEGACION dicsubdele1 ON solicitud1.CVE_ID_SUBDELEGACION = dicsubdele1.CVE_ID_SUBDELEGACION ");
            sql.append("LEFT JOIN " + DB_USER + "DIC_DELEGACION dicdelega1 ON dicsubdele1.CVE_ID_DELEGACION = dicdelega1.CVE_ID_DELEGACION ");
            sql.append("WHERE tareausuario1.CVE_ID_EDO_TAREA in (:estadosTareaUsuario) ");
            sql.append("AND instancia1.CVE_ID_EDO_INSTANCIA in (:idEstadoInstancia) ");
            sql.append("AND tramite1.CVE_ID_ESTADO_TRAMITE <> 7 ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroNss")))
                sql.append("AND instancia1.DES_BDOC_INSTANCIA like :nss ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroCurp")))
                sql.append("AND instancia1.DES_BDOC_INSTANCIA like :curp ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroFolio")))
                sql.append("AND instancia1.DES_BDOC_INSTANCIA like :folio ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacion")))
                sql.append("AND instancia1.DES_BDOC_INSTANCIA like :delegacion ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacion")))
                sql.append("AND instancia1.DES_BDOC_INSTANCIA like :subdelegacion ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacionControl")))
                sql.append("AND dicdelega1.CVE_ID_DELEGACION = :delegacionControl ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacionControl")))
                sql.append("AND dicsubdele1.CVE_ID_SUBDELEGACION = :subdelegacionControl ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacionOrigen")))
                sql.append("AND instancia1.DES_BDOC_INSTANCIA like :delegacionOrigen ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacionOrigen")))
                sql.append("AND instancia1.DES_BDOC_INSTANCIA like :subdelegacionOrigen ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroCapturaSolicitud"))) {
                sql.append("AND instancia1.CVE_ID_INSTANCIA NOT IN (");
                sql.append("SELECT CVE_ID_INSTANCIA FROM " + DB_USER + "DIT_INSTANCIA ");
                sql.append("WHERE DES_BDOC_INSTANCIA like '%delegacionOrigen&quot;:&quot;Portal&quot;%')");
            }
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroTipoAclaracion")))
                sql.append("AND instancia1.DES_BDOC_INSTANCIA like :tipoAclaracion ");
            if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoTramite")).booleanValue())
                agregarFiltrosTipoTramite(sql, (List<Long>)((HashMap)listFilter.get(0)).get("filtrosTipoTramite"));
            if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud")).booleanValue())
                sql.append("AND solicitud1.CVE_ID_TIPO_SOLICITUD in (:tiposSolicitud) ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroEstado"))) {
                sql.append("AND ( instancia1.DES_BDOC_INSTANCIA like :estado ");
                sql.append("OR instancia1.DES_BDOC_INSTANCIA like :tarea ) ");
            }
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroEstadoTramite")))
                sql.append("AND tramite1.CVE_ID_ESTADO_TRAMITE = :estadoTramite ");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("fechaInicial")) &&
                    StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("fechaFinal"))) {
                sql.append("AND TRUNC(solicitud1.FEC_SOLICITUD) BETWEEN TRUNC(:fechaInicial) ");
                sql.append("AND TRUNC(:fechaFinal) ");
            }
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroUsuario")))
                sql.append("AND tareausuario1.CVE_USUARIO = :usuario ");
            if (idsProcesos != null && !idsProcesos.isEmpty())
                sql.append("AND instancia1.CVE_ID_PROCESO in (:idsProceso) ");
            sql.append("GROUP BY tareausuario1.CVE_ID_INSTANCIA ");
            sql.append(")");
            sql.append(" order by ");
            sql.append("instancia0.CVE_ID_INSTANCIA DESC, ");
            sql.append("instancia0.FEC_REGISTRO_ACTUALIZADO DESC ");
            Query query = this.entityManager.createNativeQuery(sql.toString());

            LOGGERBPM.trace("Actualizar timeout a 600000");
            query.setHint("javax.persistence.query.timeout", 600000);

            query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
            List<Long> estadosInstancia = new ArrayList<Long>();
            estadosInstancia.add(Long.valueOf(EstadoInstanciaEnum.ACTIVA.getClave()));
            estadosInstancia.add(Long.valueOf(EstadoInstanciaEnum.TERMINADA.getClave()));
            query.setParameter("idEstadoInstancia", estadosInstancia);
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroNss")))
                query.setParameter("nss", "%nss&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroNss") + "%");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroCurp")))
                query.setParameter("curp", "%curp&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroCurp") + "%");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroFolio")))
                query.setParameter("folio", "%folio>" + ((HashMap)listFilter.get(0)).get("filtroFolio") + "%");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacion")))
                query.setParameter("delegacion", "%;delegacion&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroDelegacion") + "&quot;%");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacion")))
                query.setParameter("subdelegacion", "%data>{%subdelegacion&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroSubdelegacion") + "&quot;%");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacionControl")))
                query.setParameter("delegacionControl", ((HashMap)listFilter.get(0)).get("filtroDelegacionControl"));
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacionControl")))
                query.setParameter("subdelegacionControl", ((HashMap)listFilter.get(0)).get("filtroSubdelegacionControl"));
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacionOrigen")))
                query.setParameter("delegacionOrigen", "%delegacionOrigen&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroDelegacionOrigen") + "&quot;%");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacionOrigen")))
                query.setParameter("subdelegacionOrigen", "%data>{%subdelegacionOrigen&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroSubdelegacionOrigen") + "&quot;%");
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroTipoAclaracion")))
                query.setParameter("tipoAclaracion", "%tipoAclaracion&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroTipoAclaracion") + "&quot;%");
            if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoTramite")).booleanValue())
                asignarParametrosTipoTramite(query, (List<Long>)((HashMap)listFilter.get(0)).get("filtrosTipoTramite"));
            if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud")).booleanValue())
                query.setParameter("tiposSolicitud", ((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud"));
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroEstado"))) {
                query.setParameter("estado", "%<estatus>" + ((HashMap)listFilter.get(0)).get("filtroEstado") + "</estatus>%");
                query.setParameter("tarea", "%tarea&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroEstado") + "&quot;%");
            }
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroEstadoTramite")))
                query.setParameter("estadoTramite", ((HashMap)listFilter.get(0)).get("filtroEstadoTramite"));
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("fechaInicial")) &&
                    StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("fechaFinal")))
                try {
                    query.setParameter("fechaInicial", FORMATTER.parse((String)((HashMap)listFilter.get(0)).get("fechaInicial")));
                    query.setParameter("fechaFinal", FORMATTER.parse((String)((HashMap)listFilter.get(0)).get("fechaFinal")));
                } catch (ParseException ex) {
                    LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
                }
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroUsuario")))
                query.setParameter("usuario", String.valueOf(((HashMap)listFilter.get(0)).get("filtroUsuario")).trim());
            if (idsProcesos != null && !idsProcesos.isEmpty())
                query.setParameter("idsProceso", idsProcesos);
            if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("sinPaginacion")))
                dataPage.setCurrentPage(1L);
            int firstResult = (int)(dataPage.getCurrentPage() - 1L) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    /**
     * Metodo para obtener cantidad todal de tareas por usuario normativo V0
     */
    private long obtenerTotalTareasPorUsuarioNormativo_V0(
            DataPage dataPage, List<Long> estadosTareaUsuario, List<Long> idsProcesos) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>)dataPage.getData();
        LOGGERBPM.trace("obtenerTotalTareasPorUsuarioNormativo V0");

        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT count(*) FROM (");
        sql.append("SELECT * ");
        sql.append("FROM " + DB_USER + "DIT_TAREA_USUARIO tareausuario0 ");
        sql.append("INNER JOIN " + DB_USER + "DIT_INSTANCIA instancia0 ON tareausuario0.CVE_ID_INSTANCIA = instancia0.CVE_ID_INSTANCIA ");
        sql.append("INNER JOIN " + DB_USER + "DIT_TRAMITE  tramite0 ON instancia0.CVE_ID_TRAMITE = tramite0.CVE_ID_TRAMITE ");
        sql.append("INNER JOIN " + DB_USER + "DIT_SOLICITUD solicitud0 ON tramite0.CVE_ID_SOLICITUD = solicitud0.CVE_ID_SOLICITUD ");
        sql.append("LEFT JOIN " + DB_USER + "DIC_SUBDELEGACION dicsubdele0 ON solicitud0.CVE_ID_SUBDELEGACION = dicsubdele0.CVE_ID_SUBDELEGACION ");
        sql.append("LEFT JOIN " + DB_USER + "DIC_DELEGACION dicdelega0 ON dicsubdele0.CVE_ID_DELEGACION = dicdelega0.CVE_ID_DELEGACION ");
        sql.append("WHERE tareausuario0.CVE_ID_TAREA_USUARIO IN ( ");
        sql.append("SELECT max(tareausuario1.CVE_ID_TAREA_USUARIO) keep(dense_rank FIRST order by tareausuario1.FEC_REGISTRO_ALTA desc) ");
        sql.append("FROM " + DB_USER + "DIT_TAREA_USUARIO tareausuario1 ");
        sql.append("INNER JOIN " + DB_USER + "DIT_INSTANCIA instancia1 ON tareausuario1.CVE_ID_INSTANCIA = instancia1.CVE_ID_INSTANCIA ");
        sql.append("INNER JOIN " + DB_USER + "DIT_TRAMITE  tramite1 ON instancia1.CVE_ID_TRAMITE = tramite1.CVE_ID_TRAMITE ");
        sql.append("INNER JOIN " + DB_USER + "DIT_SOLICITUD solicitud1 ON tramite1.CVE_ID_SOLICITUD = solicitud1.CVE_ID_SOLICITUD ");
        sql.append("LEFT JOIN " + DB_USER + "DIC_SUBDELEGACION dicsubdele1 ON solicitud1.CVE_ID_SUBDELEGACION = dicsubdele1.CVE_ID_SUBDELEGACION ");
        sql.append("LEFT JOIN " + DB_USER + "DIC_DELEGACION dicdelega1 ON dicsubdele1.CVE_ID_DELEGACION = dicdelega1.CVE_ID_DELEGACION ");
        sql.append("WHERE tareausuario1.CVE_ID_EDO_TAREA in (:estadosTareaUsuario) ");
        sql.append("AND instancia1.CVE_ID_EDO_INSTANCIA in (:idEstadoInstancia) ");
        sql.append("AND tramite1.CVE_ID_ESTADO_TRAMITE <> 7 ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroNss")))
            sql.append("AND instancia1.DES_BDOC_INSTANCIA like :nss ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroCurp")))
            sql.append("AND instancia1.DES_BDOC_INSTANCIA like :curp ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroFolio")))
            sql.append("AND instancia1.DES_BDOC_INSTANCIA like :folio ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacion")))
            sql.append("AND instancia1.DES_BDOC_INSTANCIA like :delegacion ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacion")))
            sql.append("AND instancia1.DES_BDOC_INSTANCIA like :subdelegacion ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacionControl")))
            sql.append("AND dicdelega1.CVE_ID_DELEGACION = :delegacionControl ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacionControl")))
            sql.append("AND dicsubdele1.CVE_ID_SUBDELEGACION = :subdelegacionControl ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacionOrigen")))
            sql.append("AND instancia1.DES_BDOC_INSTANCIA like :delegacionOrigen ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacionOrigen")))
            sql.append("AND instancia1.DES_BDOC_INSTANCIA like :subdelegacionOrigen ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroCapturaSolicitud"))) {
            sql.append("AND instancia1.CVE_ID_INSTANCIA NOT IN (");
            sql.append("SELECT CVE_ID_INSTANCIA FROM " + DB_USER + "DIT_INSTANCIA ");
            sql.append("WHERE DES_BDOC_INSTANCIA like '%delegacionOrigen&quot;:&quot;Portal&quot;%')");
        }
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroTipoAclaracion")))
            sql.append("AND instancia1.DES_BDOC_INSTANCIA like :tipoAclaracion ");
        if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoTramite")).booleanValue())
            agregarFiltrosTipoTramite(sql, (List<Long>)((HashMap)listFilter.get(0)).get("filtrosTipoTramite"));
        if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud")).booleanValue())
            sql.append("AND solicitud1.CVE_ID_TIPO_SOLICITUD in (:tiposSolicitud) ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroEstado"))) {
            sql.append("AND ( instancia1.DES_BDOC_INSTANCIA like :estado ");
            sql.append("OR instancia1.DES_BDOC_INSTANCIA like :tarea ) ");
        }
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroEstadoTramite")))
            sql.append("AND tramite1.CVE_ID_ESTADO_TRAMITE = :estadoTramite ");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("fechaInicial")) &&
                StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("fechaFinal"))) {
            sql.append("AND TRUNC(solicitud1.FEC_SOLICITUD) BETWEEN TRUNC(:fechaInicial) ");
            sql.append("AND TRUNC(:fechaFinal) ");
        }
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroUsuario")))
            sql.append("AND tareausuario1.CVE_USUARIO = :usuario ");
        if (idsProcesos != null && !idsProcesos.isEmpty())
            sql.append("AND instancia1.CVE_ID_PROCESO in (:idsProceso) ");
        sql.append("GROUP BY tareausuario1.CVE_ID_INSTANCIA ");
        sql.append(") )");
        Query query = this.entityManager.createNativeQuery(sql.toString());

        LOGGERBPM.trace("Actualizar timeout a 600000");
        query.setHint("javax.persistence.query.timeout", 600000);

        query.setParameter("estadosTareaUsuario", estadosTareaUsuario);
        List<Long> estadosInstancia = new ArrayList<Long>();
        estadosInstancia.add(Long.valueOf(EstadoInstanciaEnum.ACTIVA.getClave()));
        estadosInstancia.add(Long.valueOf(EstadoInstanciaEnum.TERMINADA.getClave()));
        query.setParameter("idEstadoInstancia", estadosInstancia);
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroNss")))
            query.setParameter("nss", "%nss&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroNss") + "%");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroCurp")))
            query.setParameter("curp", "%curp&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroCurp") + "%");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroFolio")))
            query.setParameter("folio", "%folio>" + ((HashMap)listFilter.get(0)).get("filtroFolio") + "%");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacion")))
            query.setParameter("delegacion", "%;delegacion&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroDelegacion") + "&quot;%");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacion")))
            query.setParameter("subdelegacion", "%data>{%subdelegacion&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroSubdelegacion") + "&quot;%");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacionControl")))
            query.setParameter("delegacionControl", ((HashMap)listFilter.get(0)).get("filtroDelegacionControl"));
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacionControl")))
            query.setParameter("subdelegacionControl", ((HashMap)listFilter.get(0)).get("filtroSubdelegacionControl"));
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroDelegacionOrigen")))
            query.setParameter("delegacionOrigen", "%delegacionOrigen&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroDelegacionOrigen") + "&quot;%");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroSubdelegacionOrigen")))
            query.setParameter("subdelegacionOrigen", "%data>{%subdelegacionOrigen&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroSubdelegacionOrigen") + "&quot;%");
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroTipoAclaracion")))
            query.setParameter("tipoAclaracion", "%tipoAclaracion&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroTipoAclaracion") + "&quot;%");
        if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoTramite")).booleanValue())
            asignarParametrosTipoTramite(query, (List<Long>)((HashMap)listFilter.get(0)).get("filtrosTipoTramite"));
        if (Utilerias.isNotEmpty((List)((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud")).booleanValue())
            query.setParameter("tiposSolicitud", ((HashMap)listFilter.get(0)).get("filtrosTipoSolicitud"));
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroEstado"))) {
            query.setParameter("estado", "%<estatus>" + ((HashMap)listFilter.get(0)).get("filtroEstado") + "</estatus>%");
            query.setParameter("tarea", "%tarea&quot;:&quot;" + ((HashMap)listFilter.get(0)).get("filtroEstado") + "&quot;%");
        }
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroEstadoTramite")))
            query.setParameter("estadoTramite", ((HashMap)listFilter.get(0)).get("filtroEstadoTramite"));
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("fechaInicial")) &&
                StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("fechaFinal")))
            try {
                query.setParameter("fechaInicial", FORMATTER.parse((String)((HashMap)listFilter.get(0)).get("fechaInicial")));
                query.setParameter("fechaFinal", FORMATTER.parse((String)((HashMap)listFilter.get(0)).get("fechaFinal")));
            } catch (ParseException ex) {
                LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
            }
        if (StringUtils.isNotBlank((String)((HashMap)listFilter.get(0)).get("filtroUsuario")))
            query.setParameter("usuario", String.valueOf(((HashMap)listFilter.get(0)).get("filtroUsuario")).trim());
        if (idsProcesos != null && !idsProcesos.isEmpty())
            query.setParameter("idsProceso", idsProcesos);

        LOGGERBPM.trace("Actualizar timeout a 600000");
        query.setHint("javax.persistence.query.timeout", 600000);

        LOGGERBPM.trace("Query :: \n" + query.toString());
        Object result = query.getSingleResult();

        LOGGERBPM.trace(result + "  " + result.getClass().getName());
        Long totalTareasPorUsuarioNormativo = Long.valueOf(((BigDecimal)result).longValue());

        LOGGERBPM.trace("TotalTareasPorUsuarioNormativo :: " + totalTareasPorUsuarioNormativo);

        return totalTareasPorUsuarioNormativo.longValue();
    }

    /**
     * Metodo para agregar campos de Tipos de Tramite
     */
    private void agregarFiltrosTipoTramite(StringBuffer sql, List<Long> tipos) {
        if (Utilerias.isNotEmpty(tipos).booleanValue()) {
            sql.append("AND (");
            for (int i = 0; i < tipos.size(); i++) {
                if (i == 0) {
                    sql.append("instancia1.DES_BDOC_INSTANCIA like :tipo_").append(i).append(" ");
                } else {
                    sql.append("OR instancia1.DES_BDOC_INSTANCIA like :tipo_").append(i).append(" ");
                }
            }
            sql.append(") ");
        }
    }

    /**
     * Metodo para mapear Tipos de Tramite
     */
    private void asignarParametrosTipoTramite(Query query, List<Long> tipos) {
        if (Utilerias.isNotEmpty(tipos).booleanValue())
            for (int i = 0; i < tipos.size(); i++)
                query.setParameter("tipo_" + i, "%idTipoTramite&quot;:&quot;" + tipos.get(i) + "&quot;%");
    }


    /**
     * Metodo para obtener una lista de tareas por usuario normativo
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerConstanciasPorUsuarioNormativo(DataPage dataPage) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        long maxResults = obtenerTotalConstanciasPorUsuarioNormativo(dataPage);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }

        if (maxResults > 0) {


            sql.append("SELECT ");
            sql.append("dicsubdele6_.DES_SUBDELEGACION, dicdelegac7_.DES_DELEG, ditasignac5_.NUM_NSS, ");
            sql.append("ditpersona3_.CURP, dictiposol4_.DES_TIPO_SOLICITUD, ");
            sql.append("ditsolicit0_.FEC_SOLICITUD ");
            sql.append("from DIT_SOLICITUD ditsolicit0_ ");
            sql.append("LEFT OUTER join DIC_ESTADO_SOLICITUD dicestados1_ on ditsolicit0_.CVE_ID_ESTADO_SOLICITUD=dicestados1_.CVE_ID_ESTADO_SOLICITUD ");
            sql.append("LEFT OUTER join DIT_PERSONA_INTERESADA_SOL ditpersona2_ on ditsolicit0_.CVE_ID_SOLICITUD=ditpersona2_.CVE_ID_SOLICITUD ");
            sql.append("LEFT OUTER join DIT_PERSONA ditpersona3_ on ditpersona2_.CVE_ID_PERSONA=ditpersona3_.CVE_ID_PERSONA ");
            sql.append("LEFT OUTER join DIC_TIPO_SOLICITUD dictiposol4_ on ditsolicit0_.CVE_ID_TIPO_SOLICITUD=dictiposol4_.CVE_ID_TIPO_SOLICITUD ");
            sql.append("LEFT OUTER join DIT_ASIGNACION_NSS ditasignac5_ on ditpersona3_.CVE_ID_PERSONA=ditasignac5_.CVE_ID_PERSONA and ditasignac5_.FEC_REGISTRO_BAJA is null ");
            sql.append("LEFT OUTER JOIN DIC_SUBDELEGACION dicsubdele6_ on ditsolicit0_.CVE_ID_SUBDELEGACION=dicsubdele6_.CVE_ID_SUBDELEGACION ");
            sql.append("LEFT OUTER join DIC_DELEGACION dicdelegac7_ on dicsubdele6_.CVE_ID_DELEGACION=dicdelegac7_.CVE_ID_DELEGACION ");
            sql.append("LEFT OUTER join DIT_TRAMITE dittramite8_ on ditsolicit0_.CVE_ID_SOLICITUD=dittramite8_.CVE_ID_SOLICITUD ");
            sql.append("WHERE ditsolicit0_.cve_id_origen_solicitud=1 ");
            sql.append("AND dictiposol4_.CVE_ID_TIPO_SOLICITUD =54  ");


            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroDelegacion"))) {
                sql.append("AND dicsubdele6_.cve_id_delegacion= :delegacion ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacion"))) {
                sql.append("AND dicsubdele6_.cve_id_subdelegacion = :subdelegacion ");
            }

            if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                sql.append("AND ditsolicit0_.FEC_SOLICITUD >= :fechaInicial ");
                sql.append("AND ditsolicit0_.FEC_SOLICITUD <= :fechaFinal ");
            }

            sql.append("order by ");
            sql.append("ditsolicit0_.FEC_SOLICITUD DESC ");

            Query query = entityManager.createNativeQuery(sql.toString());


            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroDelegacion"))) {
                query.setParameter("delegacion", "" + listFilter.get(0).get("filtroDelegacion") + "");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacion"))) {
                query.setParameter("subdelegacion", "" + listFilter.get(0).get("filtroSubdelegacion") + "");
            }

            if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                try {
                    query.setParameter("fechaInicial", FORMATTER.parse((String) listFilter.get(0).get("fechaInicial")));
                    query.setParameter("fechaFinal", FORMATTER_HOUR.parse((String) listFilter.get(0).get("fechaFinal")));
                } catch (ParseException ex) {
                    LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
                }
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("sinPaginacion"))) {
                dataPage.setCurrentPage(1);
            }
            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }


    @SuppressWarnings("unchecked")
    private long obtenerTotalConstanciasPorUsuarioNormativo(DataPage dataPage) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);


        //Consulta constancias
        sql.append("SELECT COUNT (*) FROM ( ");
        sql.append("SELECT ");
        sql.append("dicsubdele6_.DES_SUBDELEGACION, dicdelegac7_.DES_DELEG, ditasignac5_.NUM_NSS, ");
        sql.append("ditpersona3_.CURP, dictiposol4_.DES_TIPO_SOLICITUD, ");
        sql.append("ditsolicit0_.FEC_SOLICITUD ");
        sql.append("from DIT_SOLICITUD ditsolicit0_ ");
        sql.append("LEFT OUTER join DIC_ESTADO_SOLICITUD dicestados1_ on ditsolicit0_.CVE_ID_ESTADO_SOLICITUD=dicestados1_.CVE_ID_ESTADO_SOLICITUD ");
        sql.append("LEFT OUTER join DIT_PERSONA_INTERESADA_SOL ditpersona2_ on ditsolicit0_.CVE_ID_SOLICITUD=ditpersona2_.CVE_ID_SOLICITUD ");
        sql.append("LEFT OUTER join DIT_PERSONA ditpersona3_ on ditpersona2_.CVE_ID_PERSONA=ditpersona3_.CVE_ID_PERSONA ");
        sql.append("LEFT OUTER join DIC_TIPO_SOLICITUD dictiposol4_ on ditsolicit0_.CVE_ID_TIPO_SOLICITUD=dictiposol4_.CVE_ID_TIPO_SOLICITUD ");
        sql.append("LEFT OUTER join DIT_ASIGNACION_NSS ditasignac5_ on ditpersona3_.CVE_ID_PERSONA=ditasignac5_.CVE_ID_PERSONA and ditasignac5_.FEC_REGISTRO_BAJA is null ");
        sql.append("LEFT OUTER JOIN DIC_SUBDELEGACION dicsubdele6_ on ditsolicit0_.CVE_ID_SUBDELEGACION=dicsubdele6_.CVE_ID_SUBDELEGACION ");
        sql.append("LEFT OUTER join DIC_DELEGACION dicdelegac7_ on dicsubdele6_.CVE_ID_DELEGACION=dicdelegac7_.CVE_ID_DELEGACION ");
        sql.append("LEFT OUTER join DIT_TRAMITE dittramite8_ on ditsolicit0_.CVE_ID_SOLICITUD=dittramite8_.CVE_ID_SOLICITUD ");
        sql.append("WHERE ditsolicit0_.cve_id_origen_solicitud=1 ");
        sql.append("AND dictiposol4_.CVE_ID_TIPO_SOLICITUD =54  ");

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroDelegacion"))) {
            sql.append("AND dicsubdele6_.cve_id_delegacion= :delegacion ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacion"))) {
            sql.append("AND dicsubdele6_.cve_id_subdelegacion = :subdelegacion ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("AND ditsolicit0_.FEC_SOLICITUD >= :fechaInicial ");
            sql.append("AND ditsolicit0_.FEC_SOLICITUD <= :fechaFinal ");
        }

        sql.append("order by ");
        sql.append("ditsolicit0_.FEC_SOLICITUD DESC) ");

        Query query = entityManager.createNativeQuery(sql.toString());


        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroDelegacion"))) {
            query.setParameter("delegacion", "" + listFilter.get(0).get("filtroDelegacion") + "");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacion"))) {
            query.setParameter("subdelegacion", "" + listFilter.get(0).get("filtroSubdelegacion") + "");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            try {
                query.setParameter("fechaInicial", FORMATTER.parse((String) listFilter.get(0).get("fechaInicial")));
                query.setParameter("fechaFinal", FORMATTER_HOUR.parse((String) listFilter.get(0).get("fechaFinal")));
            } catch (ParseException ex) {
                LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
            }
        }
        return Long.parseLong(query.getSingleResult().toString());
    }

    /**
     * Metodo para obtener un historico de tareas por el id de la instancia
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerHistoricoTareasPorIdInstancia(DataPage dataPage) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        dataPage.setData(null);

        sql.append("SELECT ");
        sql.append("dt.CVE_ID_TAREA,");
        sql.append("SUBSTR( SUBSTR( dtu.DES_BDOC_TAREA, INSTR( dtu.DES_BDOC_TAREA,'tarea&quot;:&quot;' )+ 18 ), 0, ");
        sql.append("INSTR( SUBSTR( dtu.DES_BDOC_TAREA, INSTR( dtu.DES_BDOC_TAREA,'tarea&quot;:&quot;' )+ 18 ),'&quot;' )- 1 ) AS ESTADO, ");
        sql.append("SUBSTR( SUBSTR( dtu.DES_BDOC_TAREA, INSTR( dtu.DES_BDOC_TAREA,'usuario>' )+ 8 ), 0, ");
        sql.append("INSTR( SUBSTR( dtu.DES_BDOC_TAREA, INSTR( dtu.DES_BDOC_TAREA,'usuario>' )+ 8 ),'</usuario>' )- 1 ) AS USUARIO, ");
        sql.append("to_char(dtu.fec_registro_alta,'dd/MM/yyyy HH24:mi:ss') FECHA,");
        sql.append("din.DES_BDOC_INSTANCIA,dtu.DES_BDOC_TAREA,dtu.CVE_ID_TAREA_USUARIO, to_char(dtu.fec_fin_tarea,'dd/MM/yyyy HH24:mi:ss') FECHA_FIN ");
        sql.append("FROM DIT_INSTANCIA din ");
        sql.append("JOIN DIT_TAREA_USUARIO dtu ON din.CVE_ID_INSTANCIA = dtu.CVE_ID_INSTANCIA ");
        sql.append("JOIN DIC_TAREA dt ON dt.CVE_ID_TAREA = dtu.CVE_ID_TAREA ");
        sql.append("WHERE din.CVE_ID_INSTANCIA = :idInstancia ");
        //sql.append("AND dtu.FEC_FIN_TAREA IS NOT NULL ");
        sql.append("ORDER BY dtu.FEC_REGISTRO_ALTA desc");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("idInstancia", listFilter.get(0).get("idInstancia"));

        dataPage.setData(query.getResultList());

        return dataPage;
    }

    /***
     * Metodo que permite obtener el reporte de CDA
     *
     * @param dataPage
     * 				  Objeto DataPage que contiene la lista de parametros a consultar
     * @return
     *       Lista de Objects dentro del objeto DataPage de salida
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerReportePrincipalCDA(DataPage dataPage, Boolean isCount) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuilder sqlTabla = new StringBuilder();
        StringBuilder sqlMaxCount = new StringBuilder();
        StringBuilder sqlDistinct = new StringBuilder();
        StringBuilder sqlStructure = new StringBuilder();
        LOGGERBPM.info("---FljoTrabajoEntuty--- " + listFilter.toString());

        sqlDistinct.append(" DISTINCT ");
        sqlDistinct.append("VISTA.CVE_ID_TRAMITE, VISTA.CVE_ID_SOLICITUD, VISTA.FOLIO, VISTA.CURP, VISTA.CVE_NSS, VISTA.VENCIDO, VISTA.DELEGACION, ");
        sqlDistinct.append("VISTA.SUBDELEGACION, VISTA.AUTORIZADOR, VISTA.RESPONSABLE, ");
        sqlDistinct.append("(CASE WHEN VISTA.CVE_ID_ORIGEN_SOLICITUD = 6 THEN 'INTERNET' WHEN VISTA.CVE_ID_ORIGEN_SOLICITUD = 1 THEN 'VENTANILLA' ELSE 'OTROS' END) ORIGEN, ");
        sqlDistinct.append("(select ");


        sqlDistinct.append(" CAST(WM_CONCAT( DISTINCT VISTA1.TIPO_TRAMITE)AS VARCHAR2(100)) AS TIPO_TRAMITE ");
        sqlDistinct.append(" from DIV_CONS_REPORTES_SOL_CDA VISTA1 ");
        sqlDistinct.append(" WHERE VISTA1.CVE_NSS = VISTA.CVE_NSS ");

        sqlDistinct.append(" ) as TIPO_TRAMITE, VISTA.FECHASOL, ");
        sqlDistinct.append("VISTA.FECHAFIN, VISTA.ULTIMACT, VISTA.CVE_ID_ESTADO_TRAMITE, vista.reasignada ");

        sqlTabla.append("SELECT ");
        sqlTabla.append(sqlDistinct);

        sqlStructure.append("FROM DIV_CONS_REPORTES_SOL_CDA VISTA ");
        sqlStructure.append("WHERE 1 = 1 ");
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("delegacion"))) {
            sqlStructure.append("AND VISTA.CLAVE_DELEGACION = :delegacion ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("subdelegacion"))) {
            sqlStructure.append("AND VISTA.CVE_ID_SUBDELEGACION = :subdelegacion ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("autorizo"))) {
            sqlStructure.append("AND VISTA.AUTORIZADOR = :autorizador ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("responsable"))) {
            sqlStructure.append("AND VISTA.RESPONSABLE = :responsable ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("origen"))) {
            sqlStructure.append("AND VISTA.CVE_ID_ORIGEN_SOLICITUD = :origen ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("folio"))) {
            sqlStructure.append("AND VISTA.FOLIO = :folio ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("curp"))) {
            sqlStructure.append("AND VISTA.CURP = :curp ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("nssInvolucrado"))) {
            sqlStructure.append("AND VISTA.CVE_NSS = :nss ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("tipoTramite"))) {
            sqlStructure.append("AND VISTA.ID_TIPO_TRAMIETE = :tipoRegulacion ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("estado"))) {
            sqlStructure.append("AND VISTA.CVE_ID_ESTADO_TRAMITE = :edoTramite ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("vencida"))) {
            sqlStructure.append("AND VISTA.CVE_ID_ESTADO_TRAMITE = :vencida ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudHasta"))) {
            sqlStructure.append("AND (VISTA.FECHASOL BETWEEN TO_DATE(:fechaSolicitudDesde, 'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaSolicitudHasta,'dd/MM/YYYY HH24:mi:ss')) ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionHasta"))) {
            sqlStructure.append("AND (VISTA.FECHAFIN  BETWEEN TO_DATE(:fechaFinalizacionDesde, 'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaFinalizacionHasta,'dd/MM/YYYY HH24:mi:ss')) ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionHasta"))) {
            sqlStructure.append("AND (VISTA.ULTIMACT BETWEEN TO_DATE(:fechaActualizacionDesde, 'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaActualizacionHasta,'dd/MM/YYYY HH24:mi:ss')) ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("curpBeneficiario"))) {
            sqlStructure.append("AND VISTA.TRAMITE_XML LIKE :curpBeneficiario ");
        }
        sqlStructure.append("ORDER BY VISTA.CVE_ID_SOLICITUD DESC ");


        sqlTabla.append(sqlStructure.toString());

        sqlMaxCount.append("SELECT COUNT (1) from ( ");
        sqlMaxCount.append(sqlTabla.toString());
        sqlMaxCount.append(") t");

        Query query = null;

        if (isCount) {
            query = entityManager.createNativeQuery(sqlMaxCount.toString());
        } else {
            query = entityManager.createNativeQuery(sqlTabla.toString());
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("delegacion"))) {
            query.setParameter("delegacion", listFilter.get(0).get("delegacion"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("subdelegacion"))) {
            query.setParameter("subdelegacion", listFilter.get(0).get("subdelegacion"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("autorizo"))) {
            query.setParameter("autorizador", listFilter.get(0).get("autorizo"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("responsable"))) {
            query.setParameter("responsable", listFilter.get(0).get("responsable"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("origen"))) {
            query.setParameter("origen", listFilter.get(0).get("origen"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("folio"))) {
            query.setParameter("folio", listFilter.get(0).get("folio"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("curp"))) {
            query.setParameter("curp", listFilter.get(0).get("curp"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("nssInvolucrado"))) {
            query.setParameter("nss", listFilter.get(0).get("nssInvolucrado"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("tipoTramite"))) {
            query.setParameter("tipoRegulacion", listFilter.get(0).get("tipoTramite"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("estado"))) {
            query.setParameter("edoTramite", listFilter.get(0).get("estado"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("vencida"))) {
            query.setParameter("vencida", "86");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudHasta"))) {
            query.setParameter("fechaSolicitudDesde", listFilter.get(0).get("fechaSolicitudDesde") + " 00:00:00");
            query.setParameter("fechaSolicitudHasta", listFilter.get(0).get("fechaSolicitudHasta") + " 23:59:59");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionHasta"))) {
            query.setParameter("fechaFinalizacionDesde", listFilter.get(0).get("fechaFinalizacionDesde") + " 00:00:00");
            query.setParameter("fechaFinalizacionHasta", listFilter.get(0).get("fechaFinalizacionHasta") + " 23:59:59");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionHasta"))) {
            query.setParameter("fechaActualizacionDesde", listFilter.get(0).get("fechaActualizacionDesde") + " 00:00:00");
            query.setParameter("fechaActualizacionHasta", listFilter.get(0).get("fechaActualizacionHasta") + " 23:59:59");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("curpBeneficiario"))) {
            query.setParameter("curpBeneficiario", "%curp=%" + listFilter.get(0).get("curpBeneficiario") + "%");
        }

        if (isCount) {
            Long maxCount = Long.parseLong(query.getSingleResult().toString());

            long totalOfPages = (long) Math.ceil((maxCount * 30 / dataPage.getPageSize()) / 30d);
            if (dataPage.getCurrentPage() > totalOfPages) {
                dataPage.setCurrentPage(totalOfPages);
            }
            dataPage.setTotalOfRecords(maxCount);
            dataPage.setTotalOfPages(totalOfPages);
        } else {
            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            LOGGERBPM.info("---CDA--Reportes: {}", firstResult);
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
        }

        return dataPage;

    }

    /**
     * Metodo que obtiene el detalle de conteo del reporte de CDA
     *
     * @param dataPage Objeto DataPage que contiene la lista de parametros a consultar
     * @return Lista de Objects dentro del objeto DataPage de salida
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerConteoReportePrincipalCDA(DataPage dataPage) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        String seleccionCombo = (String) listFilter.get(0).get("variable");

        if (seleccionCombo.equals("delegacion")) {
            sql.append("DELEGACION, COUNT(DELEGACION) NUMERO ");
        }
        if (seleccionCombo.equals("subdelegacion")) {
            sql.append("SUBDELEGACION, COUNT(SUBDELEGACION) NUMERO ");
        }
        if (seleccionCombo.equals("responsable")) {
            sql.append("RESPONSABLE, COUNT(RESPONSABLE) NUMERO ");
        }
        if (seleccionCombo.equals("autorizador")) {
            sql.append("AUTORIZADOR, COUNT(AUTORIZADOR) NUMERO ");
        }
        if (seleccionCombo.equals("estado")) {
            sql.append("ESTADO_SOLICITUD, COUNT(ESTADO_SOLICITUD) NUMERO ");
        }
        if (seleccionCombo.equals("origen")) {
            sql.append("(CASE WHEN CVE_ID_ORIGEN_SOLICITUD = 6 THEN 'INTERNET' WHEN CVE_ID_ORIGEN_SOLICITUD = 1 THEN 'VENTANILLA' ELSE 'OTROS' END) ORIGEN, COUNT(CVE_ID_ORIGEN_SOLICITUD) NUMERO ");
        }
        if (seleccionCombo.equals("tipoTramite")) {
            sql.append("TIPO_TRAMITE, COUNT(TIPO_TRAMITE) NUMERO ");
        }

        sql.append("FROM DIV_CONS_REPORTES_SOL_CDA ");
        sql.append("WHERE 1 = 1 ");

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("delegacion"))) {
            sql.append("AND CLAVE_DELEGACION = :delegacion ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("subdelegacion"))) {
            sql.append("AND CVE_ID_SUBDELEGACION = :subdelegacion ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("autorizo"))) {
            sql.append("AND AUTORIZADOR = :autorizador ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("responsable"))) {
            sql.append("AND RESPONSABLE = :responsable ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("origen"))) {
            sql.append("AND CVE_ID_ORIGEN_SOLICITUD = :origen ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("folio"))) {
            sql.append("AND FOLIO = :folio ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("curp"))) {
            sql.append("AND CURP = :curp ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("nssInvolucrado"))) {
            sql.append("AND CVE_NSS = :nss ");
        }
        ///MODIFICACION TABALA---------------------------------------------------------------------

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("tipoTramite"))) {
            sql.append("AND ID_TIPO_TRAMIETE = :tipoRegulacion ");
        }
        ///MODIFICACION TABALA---------------------------------------------------------------------
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("estado"))) {
            sql.append("AND CVE_ID_ESTADO_TRAMITE = :edoTramite ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("vencida"))) {
            sql.append("AND CVE_ID_ESTADO_TRAMITE = :vencida ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudHasta"))) {
            sql.append("AND (FECHASOL BETWEEN TO_DATE(:fechaSolicitudDesde, 'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaSolicitudHasta,'dd/MM/YYYY HH24:mi:ss')) ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionHasta"))) {
            sql.append("AND (FECHAFIN  BETWEEN TO_DATE(:fechaFinalizacionDesde, 'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaFinalizacionHasta,'dd/MM/YYYY HH24:mi:ss')) ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionHasta"))) {
            sql.append("AND (ULTIMACT BETWEEN TO_DATE(:fechaActualizacionDesde, 'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaActualizacionHasta,'dd/MM/YYYY HH24:mi:ss')) ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("curpBeneficiario"))) {
            sql.append("AND TRAMITE_XML LIKE :curpBeneficiario ");
        }

        if (seleccionCombo.equals("delegacion")) {
            sql.append("GROUP BY DELEGACION ");
        }
        if (seleccionCombo.equals("subdelegacion")) {
            sql.append("GROUP BY SUBDELEGACION ");
        }
        if (seleccionCombo.equals("responsable")) {
            sql.append("GROUP BY RESPONSABLE ");
        }
        if (seleccionCombo.equals("autorizador")) {
            sql.append("GROUP BY AUTORIZADOR ");
        }
        if (seleccionCombo.equals("estado")) {
            sql.append("GROUP BY ESTADO_SOLICITUD ");
        }
        if (seleccionCombo.equals("origen")) {
            sql.append("GROUP BY CVE_ID_ORIGEN_SOLICITUD ");
        }
        if (seleccionCombo.equals("tipoTramite")) {
            sql.append("GROUP BY TIPO_TRAMITE ");
        }

        Query query = entityManager.createNativeQuery(sql.toString());

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("delegacion"))) {
            query.setParameter("delegacion", listFilter.get(0).get("delegacion"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("subdelegacion"))) {
            query.setParameter("subdelegacion", listFilter.get(0).get("subdelegacion"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("autorizo"))) {
            query.setParameter("autorizador", listFilter.get(0).get("autorizo"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("responsable"))) {
            query.setParameter("responsable", listFilter.get(0).get("responsable"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("origen"))) {
            query.setParameter("origen", listFilter.get(0).get("origen"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("folio"))) {
            query.setParameter("folio", listFilter.get(0).get("folio"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("curp"))) {
            query.setParameter("curp", listFilter.get(0).get("curp"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("nssInvolucrado"))) {
            query.setParameter("nss", listFilter.get(0).get("nssInvolucrado"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("tipoTramite"))) {
            query.setParameter("tipoRegulacion", listFilter.get(0).get("tipoTramite"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("estado"))) {
            query.setParameter("edoTramite", listFilter.get(0).get("estado"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("vencida"))) {
            query.setParameter("vencida", "86");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudHasta"))) {
            query.setParameter("fechaSolicitudDesde", listFilter.get(0).get("fechaSolicitudDesde") + " 00:00:00");
            query.setParameter("fechaSolicitudHasta", listFilter.get(0).get("fechaSolicitudHasta") + " 23:59:59");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionHasta"))) {
            query.setParameter("fechaFinalizacionDesde", listFilter.get(0).get("fechaFinalizacionDesde") + " 00:00:00");
            query.setParameter("fechaFinalizacionHasta", listFilter.get(0).get("fechaFinalizacionHasta") + " 23:59:59");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionHasta"))) {
            query.setParameter("fechaActualizacionDesde", listFilter.get(0).get("fechaActualizacionDesde") + " 00:00:00");
            query.setParameter("fechaActualizacionHasta", listFilter.get(0).get("fechaActualizacionHasta") + " 23:59:59");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("curpBeneficiario"))) {
            query.setParameter("curpBeneficiario", "%curp=%" + listFilter.get(0).get("curpBeneficiario") + "%");
        }

        dataPage.setData(query.getResultList());
        return dataPage;
    }


    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerConteoOrigenesReportePrincipalCDA(DataPage dataPage) {
        StringBuilder sql = new StringBuilder();
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();

        sql.append("SELECT ");
        sql.append("(CASE WHEN CVE_ID_ORIGEN_SOLICITUD = 6 THEN 'INTERNET' WHEN CVE_ID_ORIGEN_SOLICITUD = 1 THEN 'VENTANILLA' ELSE 'OTROS' END) ORIGEN, ");
        sql.append("COUNT(CVE_ID_ORIGEN_SOLICITUD) NUMERO ");
        sql.append("FROM DIV_CONS_REPORTES_SOL_CDA ");
        sql.append("WHERE 1 = 1 ");

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("delegacion"))) {
            sql.append("AND CLAVE_DELEGACION = :delegacion ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("subdelegacion"))) {
            sql.append("AND CVE_ID_SUBDELEGACION = :subdelegacion ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("autorizo"))) {
            sql.append("AND AUTORIZADOR = :autorizador ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("responsable"))) {
            sql.append("AND RESPONSABLE = :responsable ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("origen"))) {
            sql.append("AND CVE_ID_ORIGEN_SOLICITUD = :origen ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("folio"))) {
            sql.append("AND FOLIO = :folio ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("curp"))) {
            sql.append("AND CURP = :curp ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("nssInvolucrado"))) {
            sql.append("AND CVE_NSS = :nss ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("tipoTramite"))) {
            sql.append("AND CVE_ID_TIPO_REGULACION = :tipoRegulacion ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("estado"))) {
            sql.append("AND CVE_ID_ESTADO_TRAMITE = :edoTramite ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("vencida"))) {
            sql.append("AND CVE_ID_ESTADO_TRAMITE = :vencida ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudHasta"))) {
            sql.append("AND (FECHASOL BETWEEN TO_DATE(:fechaSolicitudDesde, 'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaSolicitudHasta,'dd/MM/YYYY HH24:mi:ss')) ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionHasta"))) {
            sql.append("AND (FECHAFIN  BETWEEN TO_DATE(:fechaFinalizacionDesde, 'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaFinalizacionHasta,'dd/MM/YYYY HH24:mi:ss')) ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionHasta"))) {
            sql.append("AND (ULTIMACT BETWEEN TO_DATE(:fechaActualizacionDesde, 'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaActualizacionHasta,'dd/MM/YYYY HH24:mi:ss')) ");
        }

        sql.append("GROUP BY CVE_ID_ORIGEN_SOLICITUD ");

        Query query = entityManager.createNativeQuery(sql.toString());

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("delegacion"))) {
            query.setParameter("delegacion", listFilter.get(0).get("delegacion"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("subdelegacion"))) {
            query.setParameter("subdelegacion", listFilter.get(0).get("subdelegacion"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("autorizo"))) {
            query.setParameter("autorizador", listFilter.get(0).get("autorizo"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("responsable"))) {
            query.setParameter("responsable", listFilter.get(0).get("responsable"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("origen"))) {
            query.setParameter("origen", listFilter.get(0).get("origen"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("folio"))) {
            query.setParameter("folio", listFilter.get(0).get("folio"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("curp"))) {
            query.setParameter("curp", listFilter.get(0).get("curp"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("nssInvolucrado"))) {
            query.setParameter("nss", listFilter.get(0).get("nssInvolucrado"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("tipoTramite"))) {
            query.setParameter("tipoRegulacion", listFilter.get(0).get("tipoTramite"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("estado"))) {
            query.setParameter("edoTramite", listFilter.get(0).get("estado"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("vencida"))) {
            query.setParameter("vencida", "86");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaSolicitudHasta"))) {
            query.setParameter("fechaSolicitudDesde", listFilter.get(0).get("fechaSolicitudDesde") + " 00:00:00");
            query.setParameter("fechaSolicitudHasta", listFilter.get(0).get("fechaSolicitudHasta") + " 23:59:59");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinalizacionHasta"))) {
            query.setParameter("fechaFinalizacionDesde", listFilter.get(0).get("fechaFinalizacionDesde") + " 00:00:00");
            query.setParameter("fechaFinalizacionHasta", listFilter.get(0).get("fechaFinalizacionHasta") + " 23:59:59");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionDesde")) && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaActualizacionHasta"))) {
            query.setParameter("fechaActualizacionDesde", listFilter.get(0).get("fechaActualizacionDesde") + " 00:00:00");
            query.setParameter("fechaActualizacionHasta", listFilter.get(0).get("fechaActualizacionHasta") + " 23:59:59");
        }

        dataPage.setData(query.getResultList());
        return dataPage;
    }

    @SuppressWarnings("unchecked")
    private long obtenerTotalImssIsssteUsNormativo(DataPage dataPage, List<Long> idsEdoSolicitud) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        sql.append("SELECT count(*) ");
        sql.append("FROM DIT_SOLICITUD solicitud1 ");
        sql.append("LEFT JOIN DIC_SUBDELEGACION dicsubdele1 ON solicitud1.CVE_ID_SUBDELEGACION = dicsubdele1.CVE_ID_SUBDELEGACION ");
        sql.append("LEFT JOIN DIC_DELEGACION dicdelega1 ON dicsubdele1.CVE_ID_DELEGACION = dicdelega1.CVE_ID_DELEGACION ");
        sql.append("INNER JOIN DIT_PERSONA_INTERESADA_SOL personaint ON personaint.CVE_ID_SOLICITUD = solicitud1.CVE_ID_SOLICITUD ");
        sql.append("INNER JOIN DIT_PERSONA persona1 ON persona1.CVE_ID_PERSONA = personaint.CVE_ID_PERSONA ");
        sql.append("INNER JOIN DIT_ASIGNACION_NSS asignacion1 ON asignacion1.CVE_ID_PERSONA = personaint.CVE_ID_PERSONA ");
        sql.append("INNER JOIN DIC_ESTADO_SOLICITUD des ON des.CVE_ID_ESTADO_SOLICITUD = solicitud1.CVE_ID_ESTADO_SOLICITUD ");
        sql.append("INNER JOIN (SELECT tramite1.CVE_ID_TIPO_TRAMITE, tramite1.CVE_ID_SOLICITUD ");
        sql.append("FROM DIT_TRAMITE tramite1 where tramite1.CVE_ID_TIPO_TRAMITE in (:tipoTramites) AND tramite1.CVE_ID_ESTADO_TRAMITE IN (2, 7) ");

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("AND TRUNC(tramite1.FEC_TRAMITE) BETWEEN TRUNC(:fechaInicial) ");
            sql.append("AND TRUNC(:fechaFinal) ");
        } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("AND TRUNC(tramite1.FEC_TRAMITE) >= TRUNC(:fechaInicial) ");
        } else if (StringUtils.isBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("AND TRUNC(tramite1.FEC_TRAMITE) BETWEEN TRUNC(:fechaInicial) ");
            sql.append("AND TRUNC(:fechaFinal) ");
        } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            sql.append("AND TRUNC(tramite1.FEC_TRAMITE) >= TRUNC(:fechaInicial) ");
        }

        sql.append(") tramitesub ON tramitesub.CVE_ID_SOLICITUD = solicitud1.CVE_ID_SOLICITUD ");
        sql.append("INNER JOIN DIC_TIPO_TRAMITE dtt ON dtt.CVE_ID_TIPO_TRAMITE = tramitesub.CVE_ID_TIPO_TRAMITE ");
        sql.append("WHERE solicitud1.CVE_ID_ESTADO_SOLICITUD in (:idsEdoSolicitud) ");

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
            sql.append("AND asignacion1.NUM_NSS = :nss ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
            sql.append("AND persona1.CURP = :curp ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            sql.append("AND solicitud1.REF_FOLIO = :folio ");
        }
        if (StringUtils.isBlank((String) listFilter.get(0).get("filtroNss")) && StringUtils.isBlank((String) listFilter.get(0).get("filtroCurp")) && StringUtils.isBlank((String) listFilter.get(0).get("filtroFolio"))) {
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroDelegacionControl"))) {
                sql.append("AND dicdelega1.CVE_ID_DELEGACION = :delegacion ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacionControl"))) {
                sql.append("AND dicsubdele1.CVE_ID_SUBDELEGACION = :subdelegacion ");
            }
        }
        if (Utilerias.isNotEmpty((List<String>) listFilter.get(0).get("filtrosTipoSolicitud"))) {
            sql.append("AND solicitud1.CVE_ID_TIPO_SOLICITUD in (:tiposSolicitud) ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstadoTramite"))) {
            sql.append("AND tramite1.CVE_ID_ESTADO_TRAMITE IN (:estadoTramite) ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
            sql.append("AND (solicitud1.REF_OBSERVACION like :estadoSolicitud OR solicitud1.REF_OBSERVACION like :pipeEdoSolicitud) ");
        } else {
            sql.append("AND solicitud1.REF_OBSERVACION is not null ");
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("AND TRUNC(solicitud1.FEC_SOLICITUD) BETWEEN TRUNC(:fechaInicial) ");
            sql.append("AND TRUNC(:fechaFinal) ");
        } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("AND TRUNC(solicitud1.FEC_SOLICITUD) >= TRUNC(:fechaInicial) ");
        } else if (StringUtils.isBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("AND TRUNC(solicitud1.FEC_SOLICITUD) BETWEEN TRUNC(:fechaInicial) ");
            sql.append("AND TRUNC(:fechaFinal) ");
        } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            sql.append("AND TRUNC(solicitud1.FEC_SOLICITUD) >= TRUNC(:fechaInicial) ");
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("tipoTramites", listFilter.get(0).get("filtrosTipoTramite"));

        if (idsEdoSolicitud != null && !idsEdoSolicitud.isEmpty()) {
            query.setParameter("idsEdoSolicitud", idsEdoSolicitud);
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
            query.setParameter("nss", listFilter.get(0).get("filtroNss"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
            query.setParameter("curp", listFilter.get(0).get("filtroCurp"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            query.setParameter("folio", listFilter.get(0).get("filtroFolio"));
        }
        if (StringUtils.isBlank((String) listFilter.get(0).get("filtroNss")) && StringUtils.isBlank((String) listFilter.get(0).get("filtroCurp")) && StringUtils.isBlank((String) listFilter.get(0).get("filtroFolio"))) {
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroDelegacionControl"))) {
                query.setParameter("delegacion", listFilter.get(0).get("filtroDelegacionControl"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacionControl"))) {
                query.setParameter("subdelegacion", listFilter.get(0).get("filtroSubdelegacionControl"));
            }
        }
        if (Utilerias.isNotEmpty((List<String>) listFilter.get(0).get("filtrosTipoSolicitud"))) {
            query.setParameter("tiposSolicitud", listFilter.get(0).get("filtrosTipoSolicitud"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstadoTramite"))) {
            query.setParameter("estadoTramite", listFilter.get(0).get("filtroEstadoTramite"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
            query.setParameter("estadoSolicitud", (listFilter.get(0).get("filtroEstado") + "%"));
            query.setParameter("pipeEdoSolicitud", ("%|" + listFilter.get(0).get("filtroEstado") + "%"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            try {
                query.setParameter("fechaInicial", FORMATTER.parse((String) listFilter.get(0).get("fechaInicial")));
                query.setParameter("fechaFinal", FORMATTER.parse((String) listFilter.get(0).get("fechaFinal")));
            } catch (ParseException ex) {
                LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
            }
        } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isBlank((String) listFilter.get(0).get("fechaFinal"))) {
            try {
                query.setParameter("fechaInicial", FORMATTER.parse((String) listFilter.get(0).get("fechaInicial")));
            } catch (ParseException ex) {
                LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
            }
        } else if (StringUtils.isBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            try {
                query.setParameter("fechaInicial", FORMATTER.parse(FECHA_INICIO_SPIC));
                query.setParameter("fechaFinal", FORMATTER.parse((String) listFilter.get(0).get("fechaFinal")));
            } catch (ParseException ex) {
                LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
            }
        } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            try {
                query.setParameter("fechaInicial", FORMATTER.parse(FECHA_INICIO_SPIC));
            } catch (ParseException ex) {
                LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
            }
        }

        return Long.parseLong(query.getSingleResult().toString());
    }


    /**
     * Metodo para obtener una lista de tramites portabilidad imss issste por usuario normativo
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerPortabilidadImssIsssteUsNormativo(DataPage dataPage, List<Long> idsEdoSolicitud) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        long maxResults = obtenerTotalImssIsssteUsNormativo(dataPage, idsEdoSolicitud);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }

        if (maxResults > 0) {
            sql.append("SELECT solicitud1.REF_FOLIO, solicitud1.CVE_ID_SOLICITUD, tramitesub.CVE_ID_TRAMITE, tramitesub.CVE_ID_ESTADO_TRAMITE, ");
            sql.append("solicitud1.CVE_ID_SUBDELEGACION, dicdelega1.DES_DELEG, dicsubdele1.DES_SUBDELEGACION, solicitud1.FEC_SOLICITUD, ");
            sql.append("solicitud1.FEC_REGISTRO_ACTUALIZADO, solicitud1.CVE_ID_ESTADO_SOLICITUD, des.DES_ESTADO_SOLICITUD, ");
            sql.append("asignacion1.NUM_NSS, persona1.CURP, tramitesub.CVE_ID_TIPO_TRAMITE, dtt.DES_TIPO_TRAMITE, solicitud1.REF_OBSERVACION, ");
            sql.append("tramitesub.REF_OBSERVACION as OBSERVTRA, relat.CVE_ID_REL_LABORAL_TRAM ");
            sql.append("FROM DIT_SOLICITUD solicitud1 ");
            sql.append("LEFT JOIN DIC_SUBDELEGACION dicsubdele1 ON solicitud1.CVE_ID_SUBDELEGACION = dicsubdele1.CVE_ID_SUBDELEGACION ");
            sql.append("LEFT JOIN DIC_DELEGACION dicdelega1 ON dicsubdele1.CVE_ID_DELEGACION = dicdelega1.CVE_ID_DELEGACION ");
            sql.append("INNER JOIN DIT_PERSONA_INTERESADA_SOL personaint ON personaint.CVE_ID_SOLICITUD = solicitud1.CVE_ID_SOLICITUD ");
            sql.append("INNER JOIN DIT_PERSONA persona1 ON persona1.CVE_ID_PERSONA = personaint.CVE_ID_PERSONA ");
            sql.append("INNER JOIN DIT_ASIGNACION_NSS asignacion1 ON asignacion1.CVE_ID_PERSONA = personaint.CVE_ID_PERSONA ");
            sql.append("INNER JOIN DIC_ESTADO_SOLICITUD des ON des.CVE_ID_ESTADO_SOLICITUD = solicitud1.CVE_ID_ESTADO_SOLICITUD ");
            sql.append("INNER JOIN (SELECT tramite1.CVE_ID_TRAMITE, tramite1.CVE_ID_SOLICITUD, tramite1.CVE_ID_ESTADO_TRAMITE, tramite1.CVE_ID_TIPO_TRAMITE, tramite1.REF_OBSERVACION ");
            sql.append("FROM DIT_TRAMITE tramite1 where tramite1.CVE_ID_TIPO_TRAMITE in (:tipoTramites) AND tramite1.CVE_ID_ESTADO_TRAMITE IN (2, 7) ");

            if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                sql.append("AND TRUNC(tramite1.FEC_TRAMITE) BETWEEN TRUNC(:fechaInicial) ");
                sql.append("AND TRUNC(:fechaFinal) ");
            } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isBlank((String) listFilter.get(0).get("fechaFinal"))) {
                sql.append("AND TRUNC(tramite1.FEC_TRAMITE) >= TRUNC(:fechaInicial) ");
            } else if (StringUtils.isBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                sql.append("AND TRUNC(tramite1.FEC_TRAMITE) BETWEEN TRUNC(:fechaInicial) ");
                sql.append("AND TRUNC(:fechaFinal) ");
            } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
                sql.append("AND TRUNC(tramite1.FEC_TRAMITE) >= TRUNC(:fechaInicial) ");
            }

            sql.append(") tramitesub ON tramitesub.CVE_ID_SOLICITUD = solicitud1.CVE_ID_SOLICITUD ");
            sql.append("INNER JOIN DIC_TIPO_TRAMITE dtt ON dtt.CVE_ID_TIPO_TRAMITE = tramitesub.CVE_ID_TIPO_TRAMITE ");
            sql.append("LEFT JOIN SCT_REL_LABORAL_TRAM relat ON relat.CVE_ID_TRAMITE = tramitesub.CVE_ID_TRAMITE ");
            sql.append("WHERE solicitud1.CVE_ID_ESTADO_SOLICITUD in (:idsEdoSolicitud) ");

            if (Utilerias.isNotEmpty((List<String>) listFilter.get(0).get("filtrosTipoSolicitud"))) {
                sql.append("AND solicitud1.CVE_ID_TIPO_SOLICITUD in (:tiposSolicitud) ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
                sql.append("AND asignacion1.NUM_NSS = :nss ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
                sql.append("AND persona1.CURP = :curp ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
                sql.append("AND solicitud1.REF_FOLIO = :folio ");
            }
            if (StringUtils.isBlank((String) listFilter.get(0).get("filtroNss")) && StringUtils.isBlank((String) listFilter.get(0).get("filtroCurp")) && StringUtils.isBlank((String) listFilter.get(0).get("filtroFolio"))) {
                if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroDelegacionControl"))) {
                    sql.append("AND dicdelega1.CVE_ID_DELEGACION = :delegacion ");
                }
                if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacionControl"))) {
                    sql.append("AND dicsubdele1.CVE_ID_SUBDELEGACION = :subdelegacion ");
                }
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstadoTramite"))) {
                sql.append("AND tramite1.CVE_ID_ESTADO_TRAMITE IN (:estadoTramite) ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
                sql.append("AND (solicitud1.REF_OBSERVACION like :estadoSolicitud OR solicitud1.REF_OBSERVACION like :pipeEdoSolicitud) ");
            } else {
                sql.append("AND solicitud1.REF_OBSERVACION is not null ");
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                sql.append("AND TRUNC(solicitud1.FEC_SOLICITUD) BETWEEN TRUNC(:fechaInicial) ");
                sql.append("AND TRUNC(:fechaFinal) ");
            } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isBlank((String) listFilter.get(0).get("fechaFinal"))) {
                sql.append("AND TRUNC(solicitud1.FEC_SOLICITUD) >= TRUNC(:fechaInicial) ");
            } else if (StringUtils.isBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                sql.append("AND TRUNC(solicitud1.FEC_SOLICITUD) BETWEEN TRUNC(:fechaInicial) ");
                sql.append("AND TRUNC(:fechaFinal) ");
            } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
                sql.append("AND TRUNC(solicitud1.FEC_SOLICITUD) >= TRUNC(:fechaInicial) ");
            }

            sql.append("AND relat.CVE_ID_REL_LABORAL_TRAM_R is null ");

            Query query = entityManager.createNativeQuery(sql.toString());
            query.setParameter("tipoTramites", listFilter.get(0).get("filtrosTipoTramite"));

            if (idsEdoSolicitud != null && !idsEdoSolicitud.isEmpty()) {
                query.setParameter("idsEdoSolicitud", idsEdoSolicitud);
            }

            if (Utilerias.isNotEmpty((List<String>) listFilter.get(0).get("filtrosTipoSolicitud"))) {
                query.setParameter("tiposSolicitud", listFilter.get(0).get("filtrosTipoSolicitud"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
                query.setParameter("nss", listFilter.get(0).get("filtroNss"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
                query.setParameter("curp", listFilter.get(0).get("filtroCurp"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
                query.setParameter("folio", listFilter.get(0).get("filtroFolio"));
            }
            if (StringUtils.isBlank((String) listFilter.get(0).get("filtroNss")) && StringUtils.isBlank((String) listFilter.get(0).get("filtroCurp")) && StringUtils.isBlank((String) listFilter.get(0).get("filtroFolio"))) {
                if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroDelegacionControl"))) {
                    query.setParameter("delegacion", listFilter.get(0).get("filtroDelegacionControl"));
                }
                if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroSubdelegacionControl"))) {
                    query.setParameter("subdelegacion", listFilter.get(0).get("filtroSubdelegacionControl"));
                }
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstadoTramite"))) {
                query.setParameter("estadoTramite", listFilter.get(0).get("filtroEstadoTramite"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroEstado"))) {
                query.setParameter("estadoSolicitud", (listFilter.get(0).get("filtroEstado") + "%"));
                query.setParameter("pipeEdoSolicitud", ("%|" + listFilter.get(0).get("filtroEstado") + "%"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                try {
                    query.setParameter("fechaInicial", FORMATTER.parse((String) listFilter.get(0).get("fechaInicial")));
                    query.setParameter("fechaFinal", FORMATTER.parse((String) listFilter.get(0).get("fechaFinal")));
                } catch (ParseException ex) {
                    LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
                }
            } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isBlank((String) listFilter.get(0).get("fechaFinal"))) {
                try {
                    query.setParameter("fechaInicial", FORMATTER.parse((String) listFilter.get(0).get("fechaInicial")));
                } catch (ParseException ex) {
                    LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
                }
            } else if (StringUtils.isBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                try {
                    query.setParameter("fechaInicial", FORMATTER.parse(FECHA_INICIO_SPIC));
                    query.setParameter("fechaFinal", FORMATTER.parse((String) listFilter.get(0).get("fechaFinal")));
                } catch (ParseException ex) {
                    LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
                }
            } else if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp")) || StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
                try {
                    query.setParameter("fechaInicial", FORMATTER.parse(FECHA_INICIO_SPIC));
                } catch (ParseException ex) {
                    LOGGERBPM.info("Error al convertir las fechas, formato incorrecto ", ex);
                }
            }

            if (StringUtils.isNotBlank((String) listFilter.get(0).get("sinPaginacion"))) {
                dataPage.setCurrentPage(1);
            }
            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
            LOGGERBPM.info("Total de registros ", query.getResultList().size());
        }
        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataPage obtenerPortabilidadIsssteImssUsNormativo(DataPage dataPage) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);
        long maxResults = obtenerTotalIsssteImssUsNormativo(dataPage);
        long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
        dataPage.setData(null);
        if (dataPage.getCurrentPage() > totalOfPages) {
            dataPage.setCurrentPage(totalOfPages);
        }

        if (maxResults > 0) {
            sql.append("SELECT " +
                            "sol.REF_FOLIO, " + // 0 >>> folio <<< Folio trámite
                            "sol.CVE_ID_SOLICITUD, " + // 1 >>> idSolicitud
                            "dt.CVE_ID_TRAMITE, " + // 2 >>> idTramite
                            "dt.CVE_ID_ESTADO_TRAMITE, " + // 3 >>> idEstadoTramite
                            "sol.CVE_ID_ESTADO_SOLICITUD, " + // 4 >>> idEstadoTarea
                            "des.DES_ESTADO_SOLICITUD, " + // 5 >>> estadoTarea <<< Estado
                            "sol.FEC_SOLICITUD, " + // 6 >>> fecSolicitud <<< Fecha solicitud
                            "dan.NUM_NSS, " + // 7 >>> nss <<< NSS
                            "dp.CURP, " + // 8 >>> curp <<< CURP
                            "dtt.DES_TIPO_TRAMITE " // 9 >>> descTipoSolicitud <<< Tipo certificación
                    // "(SELECT smr2.DES_MOTIVO FROM MGPSSEC02.SCT_MOTIVO_RECHAZO smr " +
                    // "INNER JOIN MGPSSEC02.SCC_MOTIVO_RECHAZO smr2 ON smr2.CVE_ID_MOTIVO = smr.CVE_ID_MOTIVO " +
                    // "INNER JOIN MGPSSEC02.SCT_RESPUESTA_PORTABILIDAD srp ON srp.CVE_ID_RESPUESTA_PORTABILIDAD = smr.CVE_ID_RESPUESTA_PORTABILIDAD " +
                    // "WHERE srp.CVE_ID_TRAMITE = dt.CVE_ID_TRAMITE) AS MOTIVO_RECHAZO "
            );

            sql.append("FROM DIT_SOLICITUD sol ");
            sql.append("INNER JOIN DIC_ESTADO_SOLICITUD des ON des.CVE_ID_ESTADO_SOLICITUD = sol.CVE_ID_ESTADO_SOLICITUD ");
            sql.append("INNER JOIN DIT_PERSONA_INTERESADA_SOL dpis ON dpis.CVE_ID_SOLICITUD = sol.CVE_ID_SOLICITUD ");
            sql.append("INNER JOIN DIT_PERSONA dp ON dp.CVE_ID_PERSONA = dpis.CVE_ID_PERSONA ");
            sql.append("INNER JOIN DIT_ASIGNACION_NSS dan ON dan.CVE_ID_PERSONA = dp.CVE_ID_PERSONA ");
            sql.append("INNER JOIN DIT_TRAMITE dt ON dt.CVE_ID_SOLICITUD = sol.CVE_ID_SOLICITUD ");
            sql.append("INNER JOIN DIC_TIPO_TRAMITE dtt ON dtt.CVE_ID_TIPO_TRAMITE = dt.CVE_ID_TIPO_TRAMITE ");

            sql.append("WHERE sol.CVE_ID_TIPO_SOLICITUD IN (:tipoTramites) ");
            sql.append("AND sol.CVE_ID_ESTADO_SOLICITUD IN (:tiposSolicitud) ");


            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
                sql.append("AND dan.NUM_NSS = :nss ");
            }

            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
                sql.append("AND dp.CURP = :curp ");
            }

            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
                sql.append("AND sol.REF_FOLIO = :folio ");
            }

            if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                sql.append("AND TRUNC(sol.FEC_SOLICITUD) BETWEEN TRUNC(:fechaInicial) ");
                sql.append("AND TRUNC(:fechaFinal) ");
            }

            sql.append("ORDER BY sol.FEC_SOLICITUD DESC");

            Query query = entityManager.createNativeQuery(sql.toString());

            query.setParameter("tipoTramites", listFilter.get(0).get("filtrosTipoTramite"));
            query.setParameter("tiposSolicitud", listFilter.get(0).get("filtrosTipoSolicitud"));


            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
                query.setParameter("folio", listFilter.get(0).get("filtroFolio"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
                query.setParameter("curp", listFilter.get(0).get("filtroCurp"));
            }
            if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
                query.setParameter("nss", listFilter.get(0).get("filtroNss"));
            }


            if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                    && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
                try {
                    query.setParameter("fechaInicial", FORMATTER.parse((String) listFilter.get(0).get("fechaInicial")));
                    query.setParameter("fechaFinal", FORMATTER.parse((String) listFilter.get(0).get("fechaFinal")));
                } catch (ParseException ex) {
                    LOGGERBPM.info("Error al convertir las fechas, formato incorrecto " + ex);
                }
            }

            if (StringUtils.isNotBlank((String) listFilter.get(0).get("sinPaginacion"))) {
                dataPage.setCurrentPage(1);
            }
            int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.getPageSize();
            query.setFirstResult(firstResult);
            query.setMaxResults(dataPage.getPageSize());
            dataPage.setData(query.getResultList());
            LOGGERBPM.info("Total de registros " + query.getResultList().size());
        }

        dataPage.setTotalOfRecords(maxResults);
        dataPage.setTotalOfPages(totalOfPages);
        return dataPage;
    }

    @SuppressWarnings("unchecked")
    private long obtenerTotalIsssteImssUsNormativo(DataPage dataPage) {
        List<HashMap<String, Object>> listFilter = (List<HashMap<String, Object>>) dataPage.getData();
        StringBuffer sql = new StringBuffer(50);

        sql.append("SELECT COUNT(*) ");
        sql.append("FROM DIT_SOLICITUD sol ");
        sql.append("INNER JOIN DIC_ESTADO_SOLICITUD des ON des.CVE_ID_ESTADO_SOLICITUD = sol.CVE_ID_ESTADO_SOLICITUD ");
        sql.append("INNER JOIN DIT_PERSONA_INTERESADA_SOL dpis ON dpis.CVE_ID_SOLICITUD = sol.CVE_ID_SOLICITUD ");
        sql.append("INNER JOIN DIT_PERSONA dp ON dp.CVE_ID_PERSONA = dpis.CVE_ID_PERSONA ");
        sql.append("INNER JOIN DIT_ASIGNACION_NSS dan ON dan.CVE_ID_PERSONA = dp.CVE_ID_PERSONA ");
        sql.append("INNER JOIN DIT_TRAMITE dt ON dt.CVE_ID_SOLICITUD = sol.CVE_ID_SOLICITUD ");
        sql.append("INNER JOIN DIC_TIPO_TRAMITE dtt ON dtt.CVE_ID_TIPO_TRAMITE = dt.CVE_ID_TIPO_TRAMITE ");

        sql.append("WHERE sol.CVE_ID_TIPO_SOLICITUD IN (:tipoTramites) ");
        sql.append("AND sol.CVE_ID_ESTADO_SOLICITUD IN (:tiposSolicitud) ");


        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
            sql.append("AND dan.NUM_NSS = :nss ");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
            sql.append("AND dp.CURP = :curp ");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            sql.append("AND sol.REF_FOLIO = :folio ");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("AND TRUNC(sol.FEC_SOLICITUD) BETWEEN TRUNC(:fechaInicial) ");
            sql.append("AND TRUNC(:fechaFinal) ");
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            sql.append("AND TRUNC(sol.FEC_SOLICITUD) BETWEEN TRUNC(:fechaInicial) ");
            sql.append("AND TRUNC(:fechaFinal) ");
        }

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("tipoTramites", listFilter.get(0).get("filtrosTipoTramite"));
        query.setParameter("tiposSolicitud", listFilter.get(0).get("filtrosTipoSolicitud"));

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroFolio"))) {
            query.setParameter("folio", listFilter.get(0).get("filtroFolio"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroCurp"))) {
            query.setParameter("curp", listFilter.get(0).get("filtroCurp"));
        }
        if (StringUtils.isNotBlank((String) listFilter.get(0).get("filtroNss"))) {
            query.setParameter("nss", listFilter.get(0).get("filtroNss"));
        }

        if (StringUtils.isNotBlank((String) listFilter.get(0).get("fechaInicial"))
                && StringUtils.isNotBlank((String) listFilter.get(0).get("fechaFinal"))) {
            try {
                query.setParameter("fechaInicial", FORMATTER.parse((String) listFilter.get(0).get("fechaInicial")));
                query.setParameter("fechaFinal", FORMATTER.parse((String) listFilter.get(0).get("fechaFinal")));
            } catch (ParseException ex) {
                LOGGERBPM.info("Error al convertir las fechas, formato incorrecto " + ex);
            }
        }

        return Long.parseLong(query.getSingleResult().toString());
    }

}
