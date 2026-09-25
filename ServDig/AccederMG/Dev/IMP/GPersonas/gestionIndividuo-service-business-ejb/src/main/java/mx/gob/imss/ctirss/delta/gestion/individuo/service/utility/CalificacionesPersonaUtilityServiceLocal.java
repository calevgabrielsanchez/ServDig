package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalific;

/**
 * 111012
 * 
 * @author ICCSRG
 * 
 */
@Local
public interface CalificacionesPersonaUtilityServiceLocal {

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de entidad a
	 * uno de modelo
	 * 
	 * @param ditHistPersonaCalificacion
	 * @return
	 * @throws TransformacionException
	 */
	PersonaCalificacion transformarAModelo(
			DitHistPersonaCalificacion ditHistPersonaCalificacion)
			throws TransformacionException;
	
	/**
	 * Metodo encargado de realizar la transformacion de un objeto de entidad a
	 * uno de modelo
	 * 
	 * @param ditHistPersonaMoralCalific
	 * @return
	 * @throws TransformacionException
	 */
	PersonaCalificacion transformarAModelo(
			DitHistPersonaMoralCalific ditHistPersonaMoralCalific)
			throws TransformacionException;

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de modelo a
	 * uno de entidad
	 * 
	 * @param personaCalificacion
	 * @param fisica
	 * @return
	 */
	DitHistPersonaCalificacion transformarAEntidad(
			PersonaCalificacion personaCalificacion, Fisica fisica);
	
	/**
	 * Metodo encargado de realizar la transformacion de un objeto de modelo a
	 * uno de entidad
	 * 
	 * @param personaCalificacion
	 * @param fisica
	 * @return
	 */
	DitHistPersonaMoralCalific transformarAEntidad(
			PersonaCalificacion personaCalificacion, Moral moral);

	/**
	 * Metodo encargado de filtar Calificaciones Vigentes a una persona física
	 * 
	 * @param listCalificaciones
	 * @return
	 * @throws TransformacionException
	 */
	List<PersonaCalificacion> filtrarCalificacionesVigentesPersonaFisica(
			List<DitHistPersonaCalificacion> listCalificaciones)
			throws TransformacionException;

	/**
	 * Metodo encargado de filtar Calificaciones Vigentes a una persona moral
	 * 
	 * @param listCalificaciones
	 * @return
	 * @throws TransformacionException
	 */
	List<PersonaCalificacion> filtrarCalificacionesVigentesPersonaMoral(
			List<DitHistPersonaMoralCalific> listCalificaciones)
			throws TransformacionException;

	/**
	 * Metodo que regreso las calificaciones de una persona como cadena
	 * 
	 * @param listCalificaciones
	 * @return
	 */
	String getCalificacionesVigentesAsString(
			List<PersonaCalificacion> listCalificaciones);
}
