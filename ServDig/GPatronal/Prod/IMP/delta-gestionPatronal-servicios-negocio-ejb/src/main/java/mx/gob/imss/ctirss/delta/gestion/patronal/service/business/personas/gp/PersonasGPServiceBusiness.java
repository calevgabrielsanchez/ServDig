package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.personas.gp;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.personas.gp.PersonasGPServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.gp.PersonasGPServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Stateless(name="personasGPServiceBusiness" ,mappedName="personasGPServiceBusiness")
public class PersonasGPServiceBusiness extends AbstractServiceBusiness implements PersonasGPServiceBusinessRemote, PersonasGPServiceBusinessLocal{
	
	@EJB
	private PersonasGPServiceEntityLocal personasGPServiceEntity;

	@Override
	public long registrarPersonaFisica(Fisica fisica) {
		//return this.personasGPServiceEntity.registrarPersonaFisica(fisica);
		return 0L;
	}

	@Override
	public boolean existePersonaFisica(Fisica fisica) {
		return this.personasGPServiceEntity.existePersonaFisica(fisica);
	}
	
}
