package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.CalificacionesNoExistentesException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;

/**
 * 111012
 * 
 * @author ICCSRG
 * 
 */
@Remote
public interface CalificacionesPersonaBusinessServiceRemote {

	/**
	 * Servicio encargado de realizar el registro de las calificaciones asociadas
	 * a una persona fisica
	 * 
	 * @param fisica
	 * @throws CalificacionesNoExistentesException
	 */
	void registrar(Fisica fisica)
			throws CalificacionesNoExistentesException;
	
	/**
	 * Servicio encargado de realizar el registro de las calificaciones asociadas
	 * a una persona moral
	 * 
	 * @param moral
	 * @throws CalificacionesNoExistentesException
	 */
	void registrar(Moral moral)
			throws CalificacionesNoExistentesException;

	/**
	 * Servicio encargado de expirar la calificación de la persona física recibida
	 * 
	 * @param fisica
	 * @param personaCalificacion
	 */
	void expirar(Fisica fisica, PersonaCalificacion personaCalificacion);
	
	/**
	 * Servicio encargado de expirar la calificación de la persona moral recibida
	 * 
	 * @param moral
	 * @param personaCalificacion
	 */
	void expirar(Moral moral, PersonaCalificacion personaCalificacion);

	/**
	 * Servicio que obtiene las calificaciones vigentes de una persona física, en caso
	 * de no obtener resultados, se lanza una excepción
	 * 
	 * @param fisica
	 * @return
	 * @throws PersonaSinCalificacionesException
	 */
	List<PersonaCalificacion> obtenerCalificacionesVigentes(Fisica fisica)
			throws PersonaSinCalificacionesException;
	
	/**
	 * Servicio que obtiene las calificaciones vigentes de una persona moral, en caso
	 * de no obtener resultados, se lanza una excepción
	 * 
	 * @param moral
	 * @return 
	 * @throws PersonaSinCalificacionesException
	 */
	List<PersonaCalificacion> obtenerCalificacionesVigentes(Moral moral)
			throws PersonaSinCalificacionesException;

	/**
	 * Servicio que obtiene todas las calificaciones de una persona física, en caso de
	 * no obtener resultados, se lanza una excepción
	 * 
	 * @param fisica
	 * @return
	 * @throws PersonaSinCalificacionesException
	 */
	List<PersonaCalificacion> obtenerCalificaciones(Fisica fisica)
			throws PersonaSinCalificacionesException;
	
	/**
	 * Servicio que obtiene todas las calificaciones de una persona moral, en caso de
	 * no obtener resultados, se lanza una excepción
	 * 
	 * @param moral
	 * @return
	 * @throws PersonaSinCalificacionesException
	 */
	List<PersonaCalificacion> obtenerCalificaciones(Moral moral)
			throws PersonaSinCalificacionesException;

	/**
	 * Servicio que pone la calificación IMSS a la persona física. Valida si tiene la
	 * calificación vigente, de ser así, actualiza la fecha en caso contrario la
	 * da de alta y expira cualquier otra calificación.
	 * 
	 * @param fisica
	 * @throws PersonaSinCalificacionesException
	 */
	void calificarIMSS(Fisica fisica) throws PersonaSinCalificacionesException;
	
	/**
	 * Servicio que pone la calificación IMSS a la persona moral. Valida si tiene la
	 * calificación vigente, de ser así, actualiza la fecha en caso contrario la
	 * da de alta y expira cualquier otra calificación.
	 * 
	 * @param moral
	 * @throws PersonaSinCalificacionesException
	 */
	void calificarIMSS(Moral moral) throws PersonaSinCalificacionesException;

	/**
	 * Servicio que pone la calificación RENAPO a la persona física. Valida si tiene la
	 * calificación vigente, de ser así, actualiza la fecha en caso contrario la
	 * da de alta y expira la calificación IMSS en caso de contar con ella.
	 * 
	 * @param fisica
	 * @throws PersonaSinCalificacionesException
	 */
	void calificarRENAPO(Fisica fisica)
			throws PersonaSinCalificacionesException;

	/**
	 * Servicio que pone la calificación SAT a la persona física. Valida si tiene la
	 * calificación vigente, de ser así, actualiza la fecha en caso contrario la
	 * da de alta y expira la calificación IMSS en caso de contar con ella.
	 * 
	 * @param fisica
	 * @throws PersonaSinCalificacionesException
	 */
	void calificarSAT(Fisica fisica) throws PersonaSinCalificacionesException;
	
	/**
	 * Servicio que pone la calificación SAT a la persona moral. Valida si tiene la
	 * calificación vigente, de ser así, actualiza la fecha en caso contrario la
	 * da de alta y expira la calificación IMSS en caso de contar con ella.
	 * 
	 * @param moral
	 * @throws PersonaSinCalificacionesException
	 */
	void calificarSAT(Moral moral) throws PersonaSinCalificacionesException;

	/**
	 * Servicio que pone las calificaciones RENAPO y SAT a la persona física recibida.
	 * Si ya tiene la calificación le actualiza la fecha, de no tenerla la da de
	 * alta, para cualquier otra calificación la expira
	 * 
	 * @param fisica
	 * @throws PersonaSinCalificacionesException
	 */
	void calificarRENAPOySAT(Fisica fisica)
			throws PersonaSinCalificacionesException;

	/**
	 * Servicio que regresa la calificación vigente del tipo solicitado
	 * relacionada a la persona solicitada; en caso de no tener el tipo de
	 * calificación especificado se regresa nulo
	 * 
	 * @param fisica
	 * @param tipoCalificacion
	 * @return
	 */
	PersonaCalificacion obtenerCalificacionEspecifica(Fisica fisica,
			CalificacionEnum tipoCalificacion);

	/**
	 * Servicio que indica si tiene o no una calificación vigente en particular
	 * 
	 * @param fisica
	 * @param tipoCalificacion
	 * @return
	 */
	boolean tieneCalificacionEspecifica(Fisica fisica,
			CalificacionEnum tipoCalificacion);

}
