package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.personas.autorizadas;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaAutorizada;

@Local
public interface PersonaAutorizadaServiceUtilityLocal {
	
	PersonaAutorizada convertirEntityToModel(DitPersonaAutorizada ditPersonaAutorizada);
	DitPersonaAutorizada convertirModelToEntity(PersonaAutorizada personaAutorizada);
	List<PersonaAutorizada> convertirEntityToModelList(List<DitPersonaAutorizada> ditPersonasAutorizadas);
}
