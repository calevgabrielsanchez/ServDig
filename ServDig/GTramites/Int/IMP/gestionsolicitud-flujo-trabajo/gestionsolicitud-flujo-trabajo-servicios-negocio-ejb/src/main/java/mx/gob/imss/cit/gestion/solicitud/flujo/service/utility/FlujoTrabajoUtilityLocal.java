package mx.gob.imss.cit.gestion.solicitud.flujo.service.utility;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

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
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Asignacion;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Instancia;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Proceso;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.Tarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaTransicion;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TareaUsuario;
import mx.gob.imss.cit.gestion.solicitud.flujo.persistence.TransicionTarea;

@Local
public interface FlujoTrabajoUtilityLocal {

	/**
	 * Metodo para la validacion del la tarea inicial
	 * 
	 * @param tareas
	 * @return
	 * @throws TareaInicialException
	 */
	Tarea validarTareaInicial(List<Tarea> tareas) throws TareaInicialException;

	/**
	 * Metodo para la creacion de una instancia
	 * 
	 * @param proceso
	 * @param bDoc
	 * @return
	 */
	Instancia crearInstancia(Proceso proceso, InicioTramite inicioTramite);

	/**
	 * Metodo para la creacion de asignaciones
	 * 
	 * @param bpId
	 * @param inicioTramite
	 * @return
	 */
	List<Asignacion> crearAsignaciones(final Long bp, final Long bpId, Map<String, String> participantes);

	/**
	 * Metodo para la creacion de una tarea de usuario
	 * 
	 * @param instancia
	 * @param tarea
	 * @return
	 * @throws TereaSinUsuarioAsignadoException
	 */
	TareaUsuario crearTareaUsuario(final Instancia instancia, final Tarea tarea)
			throws TereaSinUsuarioAsignadoException;

	/**
	 * Meotodo para la reasignacion de tarea de usuario
	 * 
	 * @param instancia
	 * @param tarea
	 * @param usuario
	 * @return
	 */
	TareaUsuario crearTareaReasignarUsuario(final Instancia instancia, final Tarea tarea, final String usuario, MensajeTarea mensaje);

	/**
	 * Metodo para la creacion de tareas de usuario
	 * 
	 * @param lstTransicionTarea
	 * @param instancia
	 * @return
	 * @throws TereaSinUsuarioAsignadoException
	 */
	List<TareaUsuario> crearTareasUsuario(final List<TransicionTarea> lstTransicionTarea, final Instancia instancia)
			throws TereaSinUsuarioAsignadoException;

	/**
	 * Metodo para crear lista de tareas de usuario
	 * 
	 * @param lstTransicionTarea
	 * @param instancia
	 * @param bDoc
	 * @return
	 * @throws TereaSinUsuarioAsignadoException
	 */
	List<TareaUsuario> crearTareasUsuario(final List<TransicionTarea> lstTransicionTarea, final Instancia instancia,
			final String bDoc) throws TereaSinUsuarioAsignadoException;

	/**
	 * Metodo para crear lista de tareas de sistema
	 * 
	 * @param lstTransicionTarea
	 * @return
	 */
	List<Tarea> crearTareasSistema(final List<TransicionTarea> lstTransicionTarea);

	/**
	 * Metodo que valida una taarea de usuario
	 * 
	 * @param tareaUsuario
	 * @throws NoExisteTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 */
	void validarTareaUsuario(TareaUsuario tareaUsuario)
			throws NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException;

	/**
	 * metodo que valida la reasignacion de una tarea a un usuario
	 * 
	 * @param tareaUsuario
	 * @param usuario
	 * @throws NoExisteTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 * @throws TareaYaAsignadaAlUsuarioException
	 */
	void validarTareaUsuarioReasignar(TareaUsuario tareaUsuario, String usuario) throws NoExisteTareaUsuarioException,
			EstadoTareaUsuarioNoValidoException, TareaYaAsignadaAlUsuarioException;

	/**
	 * Metodo para preparar la tarea de usuario
	 * 
	 * @param tareaUsuario
	 * @param bDoc
	 */
	void prepararTareaUsuario(String usuario,TareaUsuario tareaUsuario, MensajeTarea mensajeTarea);

	/**
	 * Metodo que valida la tarea de transicion
	 * 
	 * @param lstTareaTransicion
	 * @throws NoExisteTransicionParaTareaUsuarioException
	 */
	void validarTareaTransicion(List<TareaTransicion> lstTareaTransicion)
			throws NoExisteTransicionParaTareaUsuarioException;

	/**
	 * Metodo para verificar la existencia de tareas activas
	 * 
	 * @param bpId
	 * @param subProceso
	 * @return
	 */
	boolean existenTaresActivas(final Long bpId, boolean subProceso);

	/**
	 * Metodo para terminar la instancia
	 * 
	 * @param instancia
	 */
	void prepararTerminarInstancia(Instancia instancia);

	/**
	 * Metodo que devuelve una lista de estados validos de las bandejas
	 * 
	 * @return
	 */
	List<Long> prepararEstadosValidosBandejas();

	/**
	 * Metodo que devuelve una lista de tareas de bandeja
	 * 
	 * @param lstTareasUsuario
	 * @return
	 */
	DataPage prepararRespuestaBandejas(DataPage dataPage);

	/**
	 * Metodo reasigna una tarea de usuario
	 * 
	 * @param tareaUsuario
	 */
	void prepararTareaReasignada(TareaUsuario tareaUsuario);

	/**
	 * Metodo para la cambiar la asignacion a otro usuario
	 * 
	 * @param bpId
	 * @param idParticipante
	 * @param usuario
	 * @return
	 */
	Asignacion prepararCambioUsuarioAsignacion(Long bpId, Long idParticipante, final String usuario);

	/**
	 * Metodo para verificar si se actualiza la asignacion
	 * 
	 * @param bpId
	 * @param mensajeTarea
	 */
	void validarSiActualizaAsignacion(Instancia instancia, Map<String, String> participantes);

	/**
	 * Metodo que devuelveun lista de estados validos del Timmer
	 * 
	 * @return
	 */
	List<Long> prepararEstadosValidosTimmer();

	/**
	 * Metodo que asigna un mensaje de tarea
	 * 
	 * @return
	 */
	MensajeTarea prepararBDocTimmer();

	/**
	 * Metodo que actualiza los datos BDoc de la instancia
	 * 
	 * @param tareaUsuario
	 * @param mensajeTarea
	 */
	void actualizarDatosBDocInstancia(TareaUsuario tareaUsuario, MensajeTarea mensajeTarea);

	/**
	 * Metodo que obtiene la ultima tarea del bdoc
	 * 
	 * @param bpId
	 * @return
	 */
	String obtenerBDocUltimaTarea(final Long bpId);
	
	DataPage prepararRespuestaBandejasHistorico(DataPage dataPage);
	
	void prepararListaEstatus(DataPage dataPage);
	
	/**
	 * Metodo que obtiene los participantes de la ultima Tarea
	 * 
	 * @param bpId
	 * @return
	 */
	Map<String, String> obtenerParticipantesUltimaTarea(final Long bpId);
	
	String nombrePersona(String curp);
	
	void prepararbDocAnterior(TareaUsuario tareaAnterior, String usuario);
	
	/**
	 * Metodo para preparar la tarea de usuario
	 * 
	 * @param tareaUsuario
	 * @param mensajeTarea
	 */
	void prepararTareaUsuario(TareaUsuario tareaUsuario, MensajeTarea mensajeTarea);
	
	/**
	 * Metodo que devuelve una lista de estados validos para la bitacora
	 * 
	 * @return
	 */
	List<Long> prepararEstadosValidosBitacora();
	
	/**
	 * M&eacute;todo que proporciona la transformaci&oacute;n de una TareaUsuario a una TareaBandeja para su uso
	 * en la atenci&ocute;n de solicitudes.
	 * @param tareaUsuario
	 * @param tareaBandeja
	 */
	void preparaTareaSeguimiento(TareaUsuario tareaUsuario, TareaBandeja tareaBandeja);
	
	void preparaTareasSeguimiento(List<TareaUsuario> tareasUsuario, List<TareaBandeja> tareasBandeja);
	
	
	public String obtenerResponsableSubdelegacion(DataPage dataPage);
	
	public TareaBandeja obtenerTareaByInstancia(Instancia instancia);
        
    List<Long> prepararEstadosValidosBandejasIncluyendoCompletadas();
        
    DataPage prepararRespuestaTramites(DataPage dataPage);
    
    DataPage prepararRespuestaBandejaNormativos(DataPage dataPage);
	DataPage prepararRespuestaBandejaNormativosConstancias(DataPage dataPage);
	
    DataPage prepararRespuestaHistoricoTareas(DataPage dataPage);
    
    DataPage transformObjectToReporteCDA(DataPage dataPage);
    
    DataPage transformObjectToConteoReporteCDA(DataPage dataPage);
    
    DataPage transformObjectToOrigenesConteoReporteCDA(DataPage dataPage);

	DataPage prepararPortabilidadBandejaNormativos(DataPage dataPage);

	DataPage prepararPortabilidadIsssteImssBandejaNormativos(DataPage dataPage);
}
