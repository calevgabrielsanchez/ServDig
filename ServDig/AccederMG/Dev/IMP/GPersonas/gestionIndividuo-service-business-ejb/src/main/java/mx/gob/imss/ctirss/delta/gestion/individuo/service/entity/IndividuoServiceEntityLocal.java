package mx.gob.imss.ctirss.delta.gestion.individuo.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

/**
 * 
 * Project: gestionIndividuo-service-business-ejb
 * IndividuoServiceEntityLocal.java
 * @author Hugo Armando Martinez Chamonica
 * 19/07/2012 10:54:35
 */
@Local
public interface IndividuoServiceEntityLocal {
	
	/**
	 * Busca los datos de una persona en la tabla
	 * DIT_PERSONA_FISICA
	 * @author Hugo Armando Martinez Chamonica
	 * 19/07/2012 11:06:31
	 * @param persona (Requerido: idPersona)
	 * @return Fisica
	 *
	 */
	Fisica buscarPersonaFisicaPorIdentificador(Fisica persona);
	
	/**
	 * Busca los datos de una persona en la tabla
	 * DIT_PERSONA_FISICA
	 * @author Hugo Armando Martinez Chamonica
	 * 19/07/2012 11:06:55
	 * @param persona (Requerido: cveMoral)
	 * @return Moral
	 *
	 */
	Moral buscarPersonaMoralPorIdentificador(Moral persona);
	
	/**
	 * Consulta una persona, ya sea fisica o moral por RFC 
	 * asegurando que este calificada por el SAT. Si existen
	 * dos o mas persona calificadas retorna la primera insertada en BDTU
	 * 
	 * 
	 * @param persona (RFC, tipo de persona)
	 * @return Persona instanceof FISICA/MORAL
	 */
	Persona consultarPersonaCalificadaSATPorRFC(Persona persona);
	
}
