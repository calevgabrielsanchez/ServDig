package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.NotificacionNoValidaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Notificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Marco Sánchez
 * @Proyecto: delta
 * @Archivo: NotificacionServiceEntityLocal.java
 * @Paquete: mx.gob.imss.ctirss.gestionpersonas.servicios.entity
 * @Fecha: 17/01/2013
 */
@Local
public interface NotificacionServiceEntityLocal {

	/**
	 * Guarda una notificación
	 * 
	 * @param notificacion
	 * @return
	 * @throws NotificacionNoValidaException
	 */
	Notificacion crearNotificacion(Notificacion notificacion)
			throws NotificacionNoValidaException;

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
	 * Expira (settea la fecha de baja) una notificación
	 * 
	 * @param notificacion
	 * @throws NotificacionNoValidaException
	 */
	void expirarNotificacion(Notificacion notificacion)
			throws NotificacionNoValidaException;

	/**
	 * Obtiene la lista de notificaciones de una persona especificada
	 * 
	 * @param persona
	 * @param modulo
	 * @return
	 */
	List<Notificacion> obtenerNotificacionesPersona(Persona persona);

	/**
	 * Obtiene la cantidad de notificaciones asociadas a una persona
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
