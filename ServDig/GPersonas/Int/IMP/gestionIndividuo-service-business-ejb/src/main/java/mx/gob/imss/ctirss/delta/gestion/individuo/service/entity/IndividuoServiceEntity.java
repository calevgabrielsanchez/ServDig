package mx.gob.imss.ctirss.delta.gestion.individuo.service.entity;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.IndividuoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;

/**
 * 
 * Project: gestionIndividuo-service-business-ejb
 * IndividuoServiceEntity.java
 * @author Hugo Armando Martinez Chamonica
 * 19/07/2012 11:09:06
 */
@Stateless
public class IndividuoServiceEntity extends AbstractServiceEntity  implements IndividuoServiceEntityLocal{

	@EJB
	IndividuoServiceUtilityLocal utility;
	
	@Override
	public Fisica buscarPersonaFisicaPorIdentificador(Fisica persona) {
		DitPersonaFisica ditFisica = this.em.find(DitPersonaFisica.class, persona.getIdPersona());
		return utility.convertirEntityToModelFisica(ditFisica);
	}

	@Override
	public Moral buscarPersonaMoralPorIdentificador(Moral persona) {
		DitPersonaMoral ditMoral = this.em.find(DitPersonaMoral.class, persona.getCveMoral());
		return utility.convertirEntityToModelMoral(ditMoral);
	}

	@Override
	public Persona consultarPersonaCalificadaSATPorRFC(Persona persona) {
		
		StringBuffer query = new StringBuffer();
		
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			query.append("select persona from DitHistPersonaCalificacion personaCalificacion ");
			query.append("join personaCalificacion.ditPersona persona ");
			query.append("join personaCalificacion.dicPersonaCalificacion calificacion ");
			query.append("where calificacion.cveIdCalificacion = ").append(CalificacionEnum.VALIDADO_SAT.getCodigo().longValue());
			query.append(" and persona.rfc = ").append(persona.getRfc());
			query.append(" order by persona.cveIdPersona asc ");
			
			Query consulta=this.em.createQuery(query.toString());
			@SuppressWarnings("unchecked")
			List<DitPersona> personasEncontradas = consulta.getResultList();
			DitPersonaFisica fisicaCandidata = null;
			if( personasEncontradas != null && personasEncontradas.size()>0)
				fisicaCandidata = personasEncontradas.get(0).getDitPersonaFisicas().get(0);
			
			if(fisicaCandidata!=null)
				return utility.convertirEntityToModelFisica(fisicaCandidata);
			
		}else{
			query.append("select persona from DitHistPersonaMoralCalific personaCalificacion ");
			query.append("join personaCalificacion.ditPersonaMoral persona ");
			query.append("join personaCalificacion.dicPersonaCalificacion calificacion ");
			query.append("where calificacion.cveIdCalificacion = ").append(CalificacionEnum.VALIDADO_SAT.getCodigo().longValue());
			query.append(" and persona.rfc = ").append(persona.getRfc());
			query.append(" order by persona.cveIdPersonaMoral asc ");
			
			Query consulta=this.em.createQuery(query.toString());
			@SuppressWarnings("unchecked")
			List<DitPersonaMoral> personasEncontradas = consulta.getResultList();
			DitPersonaMoral moralCandidata = null;
			if( personasEncontradas != null && personasEncontradas.size()>0)
				moralCandidata = personasEncontradas.get(0);
			
			if(moralCandidata!=null)
				return utility.convertirEntityToModelMoral(moralCandidata);
			
		}
		return null;
	}
	
	
	

}
