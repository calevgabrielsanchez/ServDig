package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.personas.gp;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;

@Stateless
public class PersonasGPServiceUtility extends AbstractServiceUtility implements PersonasGPServiceUtilityLocal{

	@Override
	public DitPersonaFisica convertirModelToEntity(Fisica fisica) {
		
		DitPersonaFisica ditPersonaFisica = new DitPersonaFisica();
		DitPersona ditPersona = new DitPersona();
		
		ditPersona.setCveIdPersona(fisica.getIdPersona());
		
		ditPersonaFisica.setDitPersona(ditPersona);
		ditPersonaFisica.setRfc(fisica.getRfc());
		ditPersonaFisica.setFecRegistroAlta(fisica.getFechaRegistro());
		
		return ditPersonaFisica;
	}

	@Override
	public Fisica convertirEntityToModel(DitPersonaFisica entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
