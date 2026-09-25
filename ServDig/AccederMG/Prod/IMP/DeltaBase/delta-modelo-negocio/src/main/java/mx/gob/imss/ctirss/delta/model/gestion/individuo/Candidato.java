/**
 * Clase de placeholder para el calculo del porcentaje de probabilidad
 * de que un registro de la consulta de personas fisicas y morales.
 */
package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class Candidato extends AbstractModel {
	
	
	
	private Long probabilidad;
	
	
	private Persona persona;


	/**
	 * @return the probabilidad
	 */
	public Long getProbabilidad() {
		return probabilidad;
	}


	/**
	 * @param probabilidad the probabilidad to set
	 */
	public void setProbabilidad(Long probabilidad) {
		this.probabilidad = probabilidad;
	}


	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}


	/**
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	
	
}
