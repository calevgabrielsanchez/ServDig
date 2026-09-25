/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

/**
 * @author vanderluk
 *
 */
@Stateless
public class CalculoPorcentajeProbabilidadUtility extends
		AbstractServiceUtility implements CalculoPorcentajeProbabilidadUtilityLocal {

	
	
	public static final Integer PESO_CURP = new Integer(1);
	
	public static final Integer PESO_RFC = new Integer(1);
	
	public static final Integer PESO_IMSS = new Integer(1);
	
	public static final Integer PESO_BASE = new Integer(33);
	
	
	
	
	/**
	 * 
	 * @param persona
	 * @param peso
	 * @return
	 */
	public Long calcularProbabilidadDeCandidato(Persona persona , Integer peso){
		Long probabilidad = this.calcularProbabilidad(peso);
		return probabilidad;
	}
	
	
	/**
	 * 
	 * @param peso
	 * @return
	 */
	private Long calcularProbabilidad(Integer peso){
		Long probabilidad = new Long(PESO_BASE);
		probabilidad = probabilidad * peso;
		return probabilidad;
		
	}


	
	
}
