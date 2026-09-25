/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

/**
 * @author Lucio Duran Silva
 *
 */
@Local
public interface CalculoPorcentajeProbabilidadUtilityLocal {

	/**
	 * 
	 * @param persona
	 * @param peso
	 * @return
	 */
	Long calcularProbabilidadDeCandidato(Persona persona , Integer peso);
	
	
	
}
