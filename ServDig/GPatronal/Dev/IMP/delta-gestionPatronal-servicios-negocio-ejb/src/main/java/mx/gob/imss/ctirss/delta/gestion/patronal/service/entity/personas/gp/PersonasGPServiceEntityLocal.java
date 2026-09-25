package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.personas.gp;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;

@Local
public interface PersonasGPServiceEntityLocal {
	
	DitPersonaFisica registrarPersonaFisica(Fisica fisica);
	
	boolean existePersonaFisica(Fisica fisica);
	
	/**
	 * Busca a una persona fisica en base a un RFC proporcionado en DIT_PERSONA_FISICA
	 * @param fisica
	 * @return
	 */
	DitPersonaFisica buscarPersonaFisicaPorRFC(Fisica fisica);
	
	/**
	 * Busca a una persona fisica en base a un id de Persona proporcionado en DIT_PERSONA_FISICA
	 * @param fisica
	 * @return
	 */
	DitPersonaFisica buscarPersonaFisicaPorIdPersona(Fisica fisica);

}
