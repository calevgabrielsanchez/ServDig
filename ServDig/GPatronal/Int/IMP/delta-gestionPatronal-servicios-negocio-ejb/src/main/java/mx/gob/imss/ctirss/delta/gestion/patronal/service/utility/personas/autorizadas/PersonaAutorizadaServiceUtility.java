package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.personas.autorizadas;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaAutorizada;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;

@Stateless(name = "personaAutorizadaServiceUtility", mappedName = "personaAutorizadaServiceUtility")
public class PersonaAutorizadaServiceUtility implements
		PersonaAutorizadaServiceUtilityLocal {
	
	@EJB SujetoObligadoUtilityLocal sujetoObligadoUtilityLocal;

	@Override
	public PersonaAutorizada convertirEntityToModel(
			DitPersonaAutorizada ditPersonaAutorizada) {
		PersonaAutorizada personaAutorizada = new PersonaAutorizada();
		
		personaAutorizada.setCvePersonaAutorizada(ditPersonaAutorizada.getCveIdPersonaAutorizada());
		SujetoObligado obligado =sujetoObligadoUtilityLocal.convertirEntityToModelSinPersona(ditPersonaAutorizada.getDitPatronSujetoObligado(), null);
		
		Fisica fisica = this.convertirFisicaEntityToModel(ditPersonaAutorizada.getDitPersonaFisica());
		
		personaAutorizada.setSujetoObligado(obligado);
		personaAutorizada.setFisica(fisica);
		personaAutorizada.setFechaAlta(ditPersonaAutorizada.getFecRegistroAlta());
		personaAutorizada.setFechaActualizacion(ditPersonaAutorizada.getFecRegistroActualizado());
		personaAutorizada.setFechaBaja(ditPersonaAutorizada.getFecRegistroBaja());
		
		return personaAutorizada;
	}

	@Override
	public DitPersonaAutorizada convertirModelToEntity(
			PersonaAutorizada personaAutorizada) {
		DitPersonaAutorizada ditPersonaAutorizada = new DitPersonaAutorizada();
		
		System.out.println("Clave de persona autorizada: " + personaAutorizada.getCvePersonaAutorizada());
		System.out.println("Clave de persona fisica: " + personaAutorizada.getFisica().getCveFisica());
		if(personaAutorizada.getCvePersonaAutorizada() != null)
			ditPersonaAutorizada.setCveIdPersonaAutorizada(personaAutorizada.getCvePersonaAutorizada());
		ditPersonaAutorizada.setDitPersonaFisica(new DitPersonaFisica());
		ditPersonaAutorizada.getDitPersonaFisica().setCveIdPersonaFisica(personaAutorizada.getFisica().getCveFisica());
		ditPersonaAutorizada.setDitPatronSujetoObligado(new DitPatronSujetoObligado());
		ditPersonaAutorizada.getDitPatronSujetoObligado().setCveIdPatronSujetoObligado(personaAutorizada.getSujetoObligado().getCveIdSujetoObligado());
		ditPersonaAutorizada.setFecRegistroAlta(personaAutorizada.getFechaAlta());
		ditPersonaAutorizada.setFecRegistroActualizado(personaAutorizada.getFechaActualizacion());
		ditPersonaAutorizada.setFecRegistroBaja(personaAutorizada.getFechaBaja());
		
		return ditPersonaAutorizada;
	}
	
	@Override
	public List<PersonaAutorizada> convertirEntityToModelList(
			List<DitPersonaAutorizada> ditPersonasAutorizadas) {

		List<PersonaAutorizada> personaAutorizadas = new ArrayList<PersonaAutorizada>();
		
		for(DitPersonaAutorizada perAut: ditPersonasAutorizadas) {
			personaAutorizadas.add(this.convertirEntityToModel(perAut));
		}
		
		return personaAutorizadas;
	}

	private Fisica convertirFisicaEntityToModel(DitPersonaFisica ditPersonaFisica) {
		Fisica fisica = new Fisica();
		fisica.setCveFisica(ditPersonaFisica.getCveIdPersonaFisica());
		fisica.setIdPersona(ditPersonaFisica.getDitPersona().getCveIdPersona());
		fisica.setNombre(ditPersonaFisica.getDitPersona().getNomNombre());
		fisica.setCurp(ditPersonaFisica.getDitPersona().getCurp());
		fisica.setPrimerApellido(ditPersonaFisica.getDitPersona().getNomPrimerApellido());
		fisica.setSegundoApellido(ditPersonaFisica.getDitPersona().getNomSegundoApellido());
		fisica.setRfc(ditPersonaFisica.getRfc());

		return fisica;
	}
	
	
}
