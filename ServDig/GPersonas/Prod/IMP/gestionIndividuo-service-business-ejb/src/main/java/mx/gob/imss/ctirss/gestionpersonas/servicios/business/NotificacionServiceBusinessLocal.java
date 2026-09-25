package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.NotificacionNoValidaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Notificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @author Marco Sánchez
 * @Proyecto: delta
 * @Fecha: 17/01/2013
 */
@Local
public interface NotificacionServiceBusinessLocal {

	/**
	 * Crea y guarda una notificacion, busca a qué gestiones se debe notificar.
	 * 
	 * @param tramite
	 * @param moduloOrigen
	 * @throws NotificacionNoValidaException
	 */
	void crearNotificacion(TramiteCambioInformacionPersona tramite,
			Modulo moduloOrigen) throws NotificacionNoValidaException;

	/**
	 * Obtiene la lista de notificaciones asociadas al módulo y persona
	 * especificados
	 * 
	 * @param persona
	 * @param modulo
	 * @return
	 */
	List<Notificacion> obtenerNotificacionesPersonaModulo(Persona persona,
			Modulo modulo);

	/**
	 * Obtiene la cantidad de notificaciones asociadas al módulo y persona
	 * especificados
	 * 
	 * @param persona
	 * @param modulo
	 * @return
	 */
	int obtenerNumeroNotificacionesPersonaModulo(Persona persona, Modulo modulo);

	/**
	 * Expira una lista de notificaciones
	 * 
	 * @param notificaciones
	 */
	void expirarNotificaciones(List<Notificacion> notificaciones);
	
	/**
	 * Obtiene la lista de notificaciones asociadas a la persona especificada
	 * 
	 * @param persona
	 * @param modulo
	 * @return
	 */
	List<Notificacion> obtenerNotificacionesPersona(Persona persona);
	
	/**
	 * Obtiene la cantidad de notificaciones asociadas a la persona
	 * especificada
	 * 
	 * @param persona
	 * @param modulo
	 * @return
	 */
	int obtenerNumeroNotificacionesPersona(Persona persona);
	
	/**
	 * Obtiene una notificación en específico a través de su id
	 * 
	 * @param idNotificacion
	 * @return
	 * @throws NotificacionNoValidaException
	 */
	Notificacion obtenerNotificacion(Long idNotificacion)
			throws NotificacionNoValidaException;
}
