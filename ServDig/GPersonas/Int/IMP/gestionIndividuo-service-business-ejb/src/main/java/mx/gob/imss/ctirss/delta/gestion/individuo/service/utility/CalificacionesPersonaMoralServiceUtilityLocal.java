package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalific;

/**
 * @author Marco Sánchez
 * @date 15/03/2013
 * 
 */
@Local
public interface CalificacionesPersonaMoralServiceUtilityLocal {

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de entidad a
	 * uno de modelo
	 * 
	 * @param ditHistPersonaCalificacion
	 * @return
	 */
	PersonaCalificacion transformarAModelo(
			DitHistPersonaMoralCalific ditHistPersonaMoralCalific);

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

}
