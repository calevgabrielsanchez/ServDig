package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.personas.gp;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.personas.gp.PersonasGPServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

@Stateless
public class PersonasGPServiceEntity extends AbstractServiceEntity implements PersonasGPServiceEntityLocal{
	
	@EJB
	private PersonasGPServiceUtilityLocal personasGPServiceUtility;

	@Override
	public DitPersonaFisica registrarPersonaFisica(Fisica fisica) {
		DitPersonaFisica ditPersonaFisica = this.personasGPServiceUtility.convertirModelToEntity(fisica);
		this.getSession().persist(ditPersonaFisica);
		return ditPersonaFisica;
	}

	@Override
	public boolean existePersonaFisica(Fisica fisica) {
		boolean result = false;
		if (this.buscarPersonaFisicaPorIdPersona(fisica) != null){
			log.debug("Se localizó al menos una persona fisica con el id de persona: " + fisica.getIdPersona());
			result = true;
		} else if (this.buscarPersonaFisicaPorRFC(fisica) != null){
			log.debug("Se localizó al menos una persona fisica con el RFC: " + fisica.getRfc() + " e id de persona: " + fisica.getIdPersona());
			result = true;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DitPersonaFisica buscarPersonaFisicaPorRFC(Fisica fisica) {

		DitPersonaFisica personaFisica;

		final Criteria criteria = this.getSession().createCriteria(
				DitPersonaFisica.class);
		criteria.add(Restrictions.eq("rfc", fisica.getRfc()));

		List<DitPersonaFisica> listaDitPersonasFisicas = criteria.list();

		if (!CollectionUtils.isEmpty(listaDitPersonasFisicas)) {
			personaFisica = listaDitPersonasFisicas.get(0);
		} else {
			personaFisica = null;
		}

		return personaFisica;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DitPersonaFisica buscarPersonaFisicaPorIdPersona(Fisica fisica) {

		DitPersonaFisica personaFisica;

		final Criteria criteria = this.getSession().createCriteria(
				DitPersonaFisica.class);
		criteria.createAlias("ditPersona", "ditPersona").add(
				Restrictions.eq("ditPersona.cveIdPersona",
						fisica.getIdPersona()));

		List<DitPersonaFisica> listaDitPersonasFisicas = criteria.list();

		if (!CollectionUtils.isEmpty(listaDitPersonasFisicas)) {
			personaFisica = listaDitPersonasFisicas.get(0);
		} else {
			personaFisica = null;
		}

		return personaFisica;
	}

}
