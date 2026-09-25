package mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaInicialException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaYaAsignadaAlUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;

@Remote
public interface FlujoTrabajoRemote {

	/**
	 * Metodo para iniciar el flujo de trabajo
	 * 
	 * @param bp
	 * @param inicioTramite
	 * @return bpId
	 * @throws TareaInicialException
	 * @throws TereaSinUsuarioAsignadoException
	 */
	Long iniciarWorkFlow(Long bp, InicioTramite inicioTramite)
			throws TareaInicialException, TereaSinUsuarioAsignadoException;
			
	Long iniciarWorkFlow(Long bp, InicioTramite inicioTramite, Long idTarea)
			throws TareaInicialException, TereaSinUsuarioAsignadoException;
				
	/**
	 * Meotodo para completar tarea
	 * 
	 * @param idTareaUsuario
	 * @param mensajeTarea
	 * @throws NoExisteTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 * @throws NoExisteTransicionParaTareaUsuarioException
	 * @throws TereaSinUsuarioAsignadoException
	 */
	void completarTarea(Long idTareaUsuario, MensajeTarea mensajeTarea)
			throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException, TereaSinUsuarioAsignadoException;

	/**
	 * Meotodo para completar tarea y actualizar el usuario
	 * 
	 * @param idTareaUsuario
	 * @param mensajeTarea
	 * @param usuario 
	 * @throws NoExisteTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 * @throws NoExisteTransicionParaTareaUsuarioException
	 * @throws TereaSinUsuarioAsignadoException
	 */
	void completarTarea(Long idTareaUsuario,String usuario, MensajeTarea mensajeTarea)
			throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException, TereaSinUsuarioAsignadoException;

	/**
	 * Metodo para obtener las tareas por usuario
	 * 
	 * @param usuario
	 * @param idsProcesos
	 * @return DataPage
	 */
	DataPage obtenerTareasPorUsuario(DataPage dataPage, String usuario,List<Long> idsProcesos);
	
	/**
	 * Metodo para obtener las tareas por subdelegacion
	 * 
	 * @param subdelegacion
	 * @param idsProcesos
	 * @return DataPage
	 */ 
	DataPage obtenerTareasPorSubdelegacion(DataPage dataPage, String subdelegacion, List<Long> idsProcesos );
	
	DataPage obtenerInstanciasHistoricasPorUsuario(DataPage dataPage, String usuario,List<Long> idsProcesos);
	
	DataPage obtenerInstanciasHistoricasCDA(DataPage dataPage,final String usuario,final String subdelegacion, Long idProceso);
	
	DataPage obtenerInstanciasHistoricasPorSubdelegacion(DataPage dataPage, String subdelegacion,List<Long> idsProcesos);
	
	void reasignarTarea(final String usuario, final Long idTarea,MensajeTarea mensajeTarea) throws NoExisteTareaUsuarioException,
	
	EstadoTareaUsuarioNoValidoException, TareaYaAsignadaAlUsuarioException;
	
	DataPage obtenerBitacoraEstatus(DataPage dataPage, final Integer idTramite);
	/**
	 * Metodo para obtener los participantes de la ultima tarea por idTarea
	 * 
	 * @param IdTarea
	 * @return Map<String,String>
	 */
	Map<String,String> obtenerParticipantesUltimaTarea(final Long idTarea)throws NoExisteTareaUsuarioException;
	
	/**
	 * Metodo para obtener los participantes de la ultima tarea por idTramite
	 * 
	 * @param IdTarea
	 * @return Map<String,String>
	 */
	Map<String,String> obtenerParticipantesUltimaTareaByTramite(final Long idTramite)throws NoExisteTareaUsuarioException;
	
	/**
	 * Meotodo para actualizar el bdoc de la instancia
	 * 
	 * @param idTareaUsuario
	 * @param mensajeTarea
	 * @throws NoExisteTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 */
	void actualizarBDocInstancia(Long idTareaUsuario,MensajeTarea mensajeTarea) throws NoExisteTareaUsuarioException, 
	EstadoTareaUsuarioNoValidoException;
  
  void actualizarBDocInstancia(Long idTareaUsuario,MensajeTarea mensajeTarea,String usuario) throws NoExisteTareaUsuarioException, 
	EstadoTareaUsuarioNoValidoException;
	
	String nombrePersona(String curp);
	
	DataPage obtenerTareasPorUsuarioResponsable(DataPage dataPage, String usuario,List<Long> idsProcesos);
	DataPage obtenerTareasCDA(DataPage dataPage, String usuario, String subdelegacion, Long idProceso);
	/**
	 * Metodo para solicitarInformacion
	 * actualiz bdoc instancia y mensaje Tarea
	 * @param idTareaUsuario
	 * @param mensajeTarea
	 * @throws NoExisteTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 */
	void solicitarInformacion(Long idTareaUsuario,MensajeTarea mensajeTarea) throws NoExisteTareaUsuarioException, 
	EstadoTareaUsuarioNoValidoException;
	
	/**
	 * Obtiene la tarea asociada al 
	 * @param idTramite
	 * @return
	 */
	TareaBandeja getTareaPorIdTramite(Long idTramite, String usuario);
	
	/**
	 * Obtiene la tarea activa asociada al 
	 * @param idTramite
	 * @return
	 */
	TareaBandeja getTareaActivaPorIdTramite(Long idTramite);
	
	/**
	 * Obtiene las tareas activas asociada al 
	 * @param idTramite
	 * @return
	 */
	List<TareaBandeja> getTareasActivasPorIdTramite(Long idTramite);
	
	/**
	 * Obtiene ultima instancia de la subdelegacion
	 * @return
	 */
	//DataPage obtenerUltimaInstanciaPorSubdelegacion(DataPage dataPage, String subdelegacion);
	/**
	 * A partir de la instancia obtiene CURP
	 * */
	
	String getCurpResponsable(String subdelegacion);
	
	TareaBandeja obtenerTareaPorIdTramite(Long idTramite);

    DataPage obtenerTramitesPorSubdelegacion(DataPage dataPage, String subdelegacion, List<Long> idsProcesos);

    DataPage obtenerTareasPorSubdelegacionIncluyendoCompletadas(DataPage dataPage, String subdelegacion, List<Long> idsProcesos);
	
    DataPage obtenerTareasPorUsuarioNormativo(DataPage dataPage, List<Long> idsProcesos);
	
	DataPage obtenerConstanciasPorUsuarioNormativo(DataPage dataPage);

        DataPage obtenerHistoricoTareasPorIdInstancia(DataPage dataPage);

    void cancelarInstanciaDeTramite(Long idTramite);
    DataPage obtenerReportePrincipalCDA(DataPage dataPage);
    
    DataPage obtenerConteoReportePrincipalCDA(DataPage dataPage);
    
    DataPage obtenerConteoOrigenesReportePrincipalCDA (DataPage dataPage);

	DataPage obtenerPortabilidadImssIsssteUsNormativo(DataPage dataPage, List<Long> idsEdoSolicitud);

	DataPage obtenerPortabilidadIsssteImssUsNormativo(DataPage dataPage);
}
