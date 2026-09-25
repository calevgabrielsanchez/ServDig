package mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Asignacion;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Instancia;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Tarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaTransicion;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaUsuario;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TransicionTarea;

@Local
public interface FlujoTrabajoLocal {
	
	Integer DIAS_MAXIMOS_ESPERA= 40;
	
	
	/**
	 * Metodo que obtiene la tarea por id
	 * 
	 * @param id
	 * @return Tarea
	 */
	Tarea obtenerTareaPorId(Long id);

	/**
	 * Metodo que obtiene la tarea inicial
	 * 
	 * @param bp
	 * @return List<Tarea>
	 */
	List<Tarea> obtenerTareaInicial(Long bp);

	/**
	 * Metodo para guardar las instancias
	 * 
	 * @param instancia
	 * @return Instancia
	 */
	Instancia guardarInstancia(Instancia instancia);

	/**
	 * Metodo para actualizar instancia
	 * 
	 * @param instancia
	 * @return Instancia
	 */
	Instancia actualizarInstancia(Instancia instancia);

	/**
	 * Metodo para guardar una lista de asignaciones
	 * 
	 * @param asignaciones
	 */
	void guardarAsignaciones(List<Asignacion> asignaciones);

	/**
	 * Metodo para guardar una asignacion
	 * 
	 * @param asignacion
	 */
	void guardarAsignacion(Asignacion asignacion);

	/**
	 * Metodo para gurdar una tarea de usuario
	 * 
	 * @param tareaUsuario
	 * @return TareaUsuario
	 */
	TareaUsuario guardarTareaUsuario(TareaUsuario tareaUsuario);

	/**
	 * Metodo para guardar una lista de tareas de usuario
	 * 
	 * @param tareasUsuario
	 */
	void guardarTareasUsuario(List<TareaUsuario> tareasUsuario);

	/**
	 * Metodo para buscar asignacion por la llave primaria
	 * 
	 * @param asignacion
	 * @return Asignacion
	 */
	Asignacion buscarAsignacionById(final Long idAsignacion);

	/**
	 * Metodo para buscar asignacions por Instancia y Participante
	 * 
	 * @param asignacion
	 * @return Asignacion
	 */
	Asignacion buscarAsignacionByInsPart(final Asignacion asignacion);
        
        Asignacion buscarAsignacion(final Asignacion asignacion);

	Asignacion buscarAsignacionByInsRol(final Asignacion asignacion);

	/**
	 * Metodo para buscar una tarea de usuario por su Id
	 * 
	 * @param idTareaUsuario
	 * @return TareaUsuario
	 */
	TareaUsuario buscarTareaUsuarioById(final Long idTareaUsuario);

	/**
	 * Metodo para buscar una instancia por su Id
	 * 
	 * @param bpId
	 * @return Instancia
	 */
	Instancia buscarInstanciaById(final Long bpId);

	/**
	 * Metodo para buscar tareas de transicion
	 * 
	 * @param idTarea
	 * @param tipoTransicion
	 * @return List<TareaTransicion>
	 */
	List<TareaTransicion> buscarTareaTransicion(final Long idTarea, final String tipoTransicion);

	/**
	 * Metodo para buscar las tareas activas por el identificador del BP
	 * 
	 * @param bpId
	 * @param estadosTareaUsuario
	 * @param subProceso
	 * @return List<TareaUsuario>
	 */
	List<TareaUsuario> buscarTareasActivasByBpId(final Long bpId, List<Long> estadosTareaUsuario, boolean subProceso);

	/**
	 * Metodo para buscar una transicion de tarea por el Id de transicion
	 * 
	 * @param idTransicion
	 * @return List<TransicionTarea>
	 */
	List<TransicionTarea> buscarTransicionTarea(final Long idTransicion);

	/**
	 * Metodo para obtener las tareas por usuario
	 * 
	 * @param usuario
	 * @param estadosTareaUsuario
	 * @return List<TareaUsuario>
	 */
	DataPage obtenerTareasPorUsuario(DataPage dataPage, final String usuario, final List<Long> estadosTareaUsuario,List<Long> idsProcesos);
	DataPage consultaTareasCDA(DataPage dataPage, String usuario, String subdelegacion,Long idsProceso);

	/**
	 * Sobrecarga del metodo para obtener las tareas por Subdelegacion
	 * 
	 * @param dataPage
	 * @param subdelegacion
	 * @param estadosTareaUsuario
	 * @param idsProcesos
	 * @return List<TareaUsuario>
	 */
	DataPage obtenerTareasPorSubdelegacion(DataPage dataPage, final String subdelegacion, final List<Long> estadosTareaUsuario,
                List<Long> idsProcesos);

	/**
	 * Metodo para reasignar tareas
	 * 
	 * @param rfcPromovente
	 * @param folioTramite
	 * @param estadosProcesal
	 * @param unidadesAdmin
	 * @param estadosTareaUsuario
	 * @return List<TareaUsuario>
	 */
	List<TareaUsuario> obtenerTareasReasignar(final String rfcPromovente, final String folioTramite,
			final List<String> estadosProcesal, final List<String> unidadesAdmin,
			final List<String> estadosTareaUsuario);

	/**
	 * Meotdo para obtener la ultima tarea completada
	 * 
	 * @param bpId
	 * @return TareaUsuario
	 */
	TareaUsuario obtenerUltimaTareaCompletada(final Long bpId);
	
	/**
	 * Metodo para obtener la ultima tarea Activa
	 * 
	 * @param bpId
	 * @return TareaUsuario
	 */
	TareaUsuario obtenerUltimaTareaActiva(final Long bpId);

	/**
	 * Metodo para obtener las tareas vencidas por usuario
	 * 
	 * @param estadosTareaUsuario
	 * @return List<TareaUsuario>
	 */
	List<TareaUsuario> obtenerTareasUsuarioVencidas(final List<Long> estadosTareaUsuario);

	/**
	 * Metodo para buscar tareas de usuario de un proceso
	 * 
	 * @param bp
	 * @return List<Tarea>
	 */
	List<Tarea> obtenerTareasUsuarioByProceso(final Long bp);

	List<Tarea> buscarTareaPorNombre(final Long bp, final String nombre);
	
	DataPage obtenerInstanciasHistoricasPorUsuario(DataPage dataPage, final String usuario,List<Long> idsProcesos);
	
	DataPage obtenerInstanciasHistoricasCDA(DataPage dataPage,final String usuario,final String subdelegacion, Long idsProceso);
	
	DataPage obtenerInstanciasHistoricasPorSubdelegacion(DataPage dataPage, final String subdelegacion,List<Long> idsProcesos);
	
	DataPage obtenerUltimaInstanciaPorSubdelegacion(DataPage dataPage, final String subdelegacion);
	
	DataPage obtenerBitacoraEstatus (DataPage dataPage, final Integer idTramite,final List<Long> estadosTareaUsuario);
	
	/**
	 * Metodo para buscar una instancia por tramite
	 * 
	 * @param bpId
	 * @return Instancia
	 */
	Instancia buscarInstanciaByTramite(final Long idTramite);
	
	/**
	 * M&eacute;todo para obtener las tareas por usuario e identificador de tr&aacute;mite
	 * 
	 * @param usuario
	 * @param estadosTareaUsuario
	 * @return List<TareaUsuario>
	 */
	DataPage obtenerTareasPorUsuarioTramite(DataPage dataPage, final String usuario, final List<Long> estadosTareaUsuario, final Long idTramite);

    DataPage obtenerTareasPorSubdelegacionIncluyendoCompletadas(DataPage dataPage,final String subdelegacion,final List<Long> estadosTareaUsuario,
                        List<Long> idsProcesos);
        
        /**
	 * M&eacute;todo para obtener los tramites por subdelegacion
	 * 
	 * @param dataPage
	 * @param subdelegacion
         * @param estadosTareaUsuario
         * @param 
	 * @return DataPage
	 */
     DataPage obtenerTramitesPorSubdelegacion(DataPage dataPage, final String subdelegacion, final List<Long> estadosTareaUsuario,
            List<Long> idsProcesos);
     
     /**
 	 * Sobrecarga del metodo para obtener las tareas por Subdelegacion
 	 * 
 	 * @param dataPage
 	 * @param subdelegacion
 	 * @param estadosTareaUsuario
 	 * @param idsProcesos
 	 * @return List<TareaUsuario>
 	 */
 	DataPage obtenerTareasPorUsuarioNormativo(DataPage dataPage, final List<Long> estadosTareaUsuario,
                 List<Long> idsProcesos);
     
    DataPage obtenerConstanciasPorUsuarioNormativo(DataPage dataPage);

    DataPage obtenerHistoricoTareasPorIdInstancia(DataPage dataPage);
    
    
    /***
     * Metodo que permite obtener el reporte de CDA
     * 
     * @param dataPage
     * 				  Objeto DataPage que contiene la lista de parametros a consultar
     * @return
     *       Lista de Objects dentro del objeto DataPage de salida
     */
    DataPage obtenerReportePrincipalCDA(DataPage dataPage, Boolean isCount);
    
    /**
     * Metodo que obtiene el detalle de conteo del reporte de CDA
     * 
     * @param dataPage
     * 				  Objeto DataPage que contiene la lista de parametros a consultar
     * @return
     * 		  Lista de Objects dentro del objeto DataPage de salida
     */
    DataPage obtenerConteoReportePrincipalCDA(DataPage dataPage);
    
    /**
     * Metodo que obtiene el conteo de los origenes de ventanilla e internet de CDA
     * 
     * @return
     * 		 Lista de Objects con los campos correspondientes
     */
    DataPage obtenerConteoOrigenesReportePrincipalCDA(DataPage dataPage);

	/**
	 * Metodo para obtener los tr?mites de portabilidad SPIC
	 *
	 * @param dataPage
	 * @return Cantidad de tramites portabilidad SPIC
	 */
	DataPage obtenerPortabilidadImssIsssteUsNormativo(DataPage dataPage, List<Long> idsEdoSolicitud);

	DataPage obtenerPortabilidadIsssteImssUsNormativo(DataPage dataPage);
    
}
