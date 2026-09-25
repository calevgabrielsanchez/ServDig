package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.personas.autorizadas;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.exception.ParametrosInvalidosException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Local
public interface PersonasAutorizadasEntityLocal {

	List<PersonaAutorizada> getPersonasAutorizadasByPersonaMF(Persona persona);
	List<PersonaAutorizada> getPersonaAutorizadasPorPersonaMFYAutorizada(Persona persona, Fisica Autorizada);
	void updatePersonaAutorizada(PersonaAutorizada personaAutorizada) throws ParametrosInvalidosException;
	void savePersonaAutorizada(PersonaAutorizada personaAutorizada);
	List<SujetoObligado> getSujetosObligadosByIdPersonaAutorizada(Long idPersona);
	List<PersonaAutorizada> getPersonasAutorizadasByIdFisica(Long idPersonaFisica);
}
