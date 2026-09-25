package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.personas.gp;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;

public interface PersonasGPServiceUtilityLocal {
	
	DitPersonaFisica convertirModelToEntity(Fisica fisica);
	
	Fisica convertirEntityToModel(DitPersonaFisica entity);

}
