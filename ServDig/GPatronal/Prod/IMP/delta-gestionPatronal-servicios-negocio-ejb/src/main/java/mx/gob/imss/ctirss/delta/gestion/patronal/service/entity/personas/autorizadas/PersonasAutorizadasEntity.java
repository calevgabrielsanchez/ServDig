package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.personas.autorizadas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.exception.ParametrosInvalidosException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.personas.autorizadas.PersonaAutorizadaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaAutorizada;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "personasAutorizadasEntity", mappedName = "personasAutorizadasEntity")
public class PersonasAutorizadasEntity extends AbstractServiceEntity implements
		PersonasAutorizadasEntityLocal {

	@EJB PersonaAutorizadaServiceUtilityLocal personaAutorizadaServiceUtilityLocal;
	@EJB SujetoObligadoUtilityLocal sujetoObligadoUtilityLocal;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaAutorizada> getPersonasAutorizadasByPersonaMF(
			Persona persona) {
		
		List<DitPersonaAutorizada> listaDitPersonasAut = null;
		List<PersonaAutorizada> listaPersonasAut = null;
//		StringBuffer sqlQuery = new StringBuffer();
//		sqlQuery.append("select pa from DitPersonaAutorizada pa where ");
//		
//		
//		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaFiscal.FISICA.getCodigo().longValue())) {
//			if(persona.getIdPersona() != null) {
//				sqlQuery.append("select pa from DitPersonaAutorizada pa where pa.ditPersonaFisica.ditPersona.cveIdPersona = ").append(persona.getIdPersona());
//			}else{
//				sqlQuery.append("select pa from DitPersonaAutorizada pa where pa.ditPersonaFisica.rfc = ").append(persona.getRfc());
//			}
//		} else {
//			if(persona.getIdPersona() != null) {
//				sqlQuery.append("select pa from DitPersonaAutorizada pa where pa.ditPatronSujetoObligado.ditPersonaMoral.cveIdPersonaMoral = ").append(persona.getIdPersona());
//			}else{
//				sqlQuery.append("select pa from DitPersonaAutorizada pa where pa.ditPatronSujetoObligado.ditPersonaMoral.rfc = ").append(persona.getRfc());
//			}
//		}
		
//		Query query = em.createQuery(sqlQuery.toString());
//		listaDitPersonasAut = query.getResultList();

		Criteria consultaPersonasA = this.getSession().createCriteria(DitPersonaAutorizada.class);
		consultaPersonasA.add(Restrictions.isNull("fecRegistroBaja"));
		Criteria conSujetoObligado = consultaPersonasA.createCriteria("ditPatronSujetoObligado");
		
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaFiscal.FISICA.getCodigo().longValue())) {
			Criteria conPersonaFisica = conSujetoObligado.createCriteria("ditPersonaFisica");
			if(persona.getIdPersona() != null) {
				conPersonaFisica.createAlias("ditPersona", "persona");
				conPersonaFisica.add(Restrictions.eq("persona.cveIdPersona", persona.getIdPersona()));
			} else {
				conPersonaFisica.add(Restrictions.eq("rfc", persona.getRfc()));
			}
		} else {
			Criteria conPersonaMoral = conSujetoObligado.createCriteria("ditPersonaMoral");
			if(persona.getIdPersona() != null) {
				conPersonaMoral.add(Restrictions.eq("cveIdPersonaMoral", persona.getIdPersona()));
			} else {
				conPersonaMoral.add(Restrictions.eq("rfc", persona.getRfc()));
			}
		}
		
		listaDitPersonasAut = consultaPersonasA.list();
		
		if(!listaDitPersonasAut.isEmpty()) {
			listaPersonasAut = personaAutorizadaServiceUtilityLocal.convertirEntityToModelList(listaDitPersonasAut);
		}
		
		return listaPersonasAut;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<SujetoObligado> getSujetosObligadosByIdPersonaAutorizada(
			Long idPersona) {
		List<DitPatronSujetoObligado> listaDitPatrones = null;
		List<SujetoObligado> listaSujetos = null;
		Criteria consultaPersonasA = this.getSession().createCriteria(DitPersonaAutorizada.class);
		consultaPersonasA.setProjection(Projections.property("ditPatronSujetoObligado"));
		
		consultaPersonasA.add(Restrictions.isNull("fecRegistroBaja"));
		Criteria consultaPersona = consultaPersonasA.createCriteria("ditPersonaFisica");
		consultaPersona.createAlias("ditPersona", "persona");
		consultaPersona.add(Restrictions.eq("persona.cveIdPersona", idPersona));

		
		listaDitPatrones = consultaPersonasA.list();
		
		if(!listaDitPatrones.isEmpty()) {
			listaSujetos = new ArrayList<SujetoObligado>();
			for(DitPatronSujetoObligado dpso: listaDitPatrones) {
				SujetoObligado pso = sujetoObligadoUtilityLocal.convertirEntityToModel(dpso, null);
				listaSujetos.add(pso);
			}
		}
		
		return listaSujetos;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaAutorizada> getPersonaAutorizadasPorPersonaMFYAutorizada(
			Persona persona, Fisica autorizada) {
		List<DitPersonaAutorizada> listaDitPersonasAut = null;
		List<PersonaAutorizada> listaPersonasAut = null;
		Criteria consultaPersonasA = this.getSession().createCriteria(DitPersonaAutorizada.class);
		Criteria conSujetoObligado = consultaPersonasA.createCriteria("ditPatronSujetoObligado");
		
		consultaPersonasA.add(Restrictions.isNull("fecRegistroBaja"));
		consultaPersonasA.createAlias("ditPersonaFisica", "fisica");
		consultaPersonasA.add(Restrictions.eq("fisica.cveIdPersonaFisica", autorizada.getCveFisica()));
		
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaFiscal.FISICA.getCodigo().longValue())) {
			Criteria conPersonaFisica = conSujetoObligado.createCriteria("ditPersonaFisica");
			if(persona.getIdPersona() != null) {
				conPersonaFisica.createAlias("ditPersona", "persona");
				conPersonaFisica.add(Restrictions.eq("persona.cveIdPersona", persona.getIdPersona()));
			} else {
				conPersonaFisica.add(Restrictions.eq("rfc", persona.getRfc()));
			}
		} else {
			Criteria conPersonaMoral = conSujetoObligado.createCriteria("ditPersonaMoral");
			if(persona.getIdPersona() != null) {
				conPersonaMoral.add(Restrictions.eq("cveIdPersonaMoral", persona.getIdPersona()));
			} else {
				conPersonaMoral.add(Restrictions.eq("rfc", persona.getRfc()));
			}
		}
		
		listaDitPersonasAut = consultaPersonasA.list();
		
		if(!listaDitPersonasAut.isEmpty()) {
			listaPersonasAut = personaAutorizadaServiceUtilityLocal.convertirEntityToModelList(listaDitPersonasAut);
		}
		
		return listaPersonasAut;
	}



	@Override
	public void savePersonaAutorizada(PersonaAutorizada personaAutorizada) {
		personaAutorizada.setFechaAlta(new Date());
		DitPersonaAutorizada ditPersonaAutorizada = personaAutorizadaServiceUtilityLocal.convertirModelToEntity(personaAutorizada);
		
		this.em.persist(ditPersonaAutorizada);
		
	}

	@Override
	public void updatePersonaAutorizada(PersonaAutorizada personaAutorizada)
			throws ParametrosInvalidosException {
		if(personaAutorizada.getFisica() == null || personaAutorizada.getFisica().getCveFisica() == null) {
			throw new ParametrosInvalidosException("Parametros invalidos");
		}
		if(personaAutorizada.getSujetoObligado() == null || personaAutorizada.getSujetoObligado().getCveIdSujetoObligado() == null){
			throw new ParametrosInvalidosException("Parametros invalidos");
		}
		
		DitPersonaAutorizada ditPersonaAutorizada = personaAutorizadaServiceUtilityLocal.convertirModelToEntity(personaAutorizada);
		this.em.merge(ditPersonaAutorizada);
	}



	@Override
	public List<PersonaAutorizada> getPersonasAutorizadasByIdFisica(
			Long idPersonaFisica) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
