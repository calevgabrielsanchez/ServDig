package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;

/**
 * 
 * @date 15/03/2013
 * @author Marco Sánchez
 * 
 */
@Local
public interface CalificacionesPersonaMoralServiceEntityLocal {

	/**
	 * Metodo encargado de realizar el registro de las calificaciones asociadas
	 * a una persona moral en BDU
	 * 
	 * @param personaCalificacion
	 * @param moral
	 */
	void registrar(PersonaCalificacion personaCalificacion, Moral moral);

}
