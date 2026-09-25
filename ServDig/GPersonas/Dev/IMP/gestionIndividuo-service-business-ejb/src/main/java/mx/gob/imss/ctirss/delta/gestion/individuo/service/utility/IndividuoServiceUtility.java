package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
/**
 * 
 * Project: gestionIndividuo-service-business-ejb
 * IndividuoServiceUtility.java
 * @author Hugo Armando Martinez Chamonica
 * 19/07/2012 11:29:13
 */
@Stateless
public class IndividuoServiceUtility implements IndividuoServiceUtilityLocal{

	@Override
	public Fisica convertirEntityToModelFisica(DitPersonaFisica entity) {
		Fisica fisica = new Fisica();
		fisica.setIdPersona(entity.getCveIdPersonaFisica());
		fisica.setNombre(entity.getDitPersona().getNomNombre());
		fisica.setPrimerApellido(entity.getDitPersona().getNomPrimerApellido());
		fisica.setSegundoApellido(entity.getDitPersona().getNomSegundoApellido());
		fisica.setRfc(entity.getRfc());
		fisica.setCurp(entity.getDitPersona().getCurp());
//		fisica.setNombreComercial(entity.getNombreComercial());
		fisica.setSexo(convertirEntityToModelSexo(entity.getDitPersona().getDicSexo()));
		fisica.setFechaNacimiento(entity.getDitPersona().getFecNacimiento());
		
		return fisica;
	}

	@Override
	public Moral convertirEntityToModelMoral(DitPersonaMoral entity) {
		Moral moral = new Moral();
		moral.setCveMoral(entity.getCveIdPersonaMoral());
//		moral.setNombreComercial(entity.getNombreComercial());
		moral.setRazonSocial(entity.getDenominacionRazonSocial());
		moral.setTipoSociedad(convertirEntityToModelTipoSociedad(entity.getDicTipoSociedad()));
		
		return moral;
	}

	@Override
	public Sexo convertirEntityToModelSexo(DicSexo entity) {
		
		if(entity== null)
			return new Sexo();
		Sexo model = new Sexo();
		model.setIdSexo(entity.getCveIdSexo().intValue());
		model.setDescripcion(entity.getDesSexo());
	
		return model;
	}

	@Override
	public TipoSociedad convertirEntityToModelTipoSociedad(
			DicTipoSociedad entity) {
		if(entity== null)
			return new TipoSociedad();
		TipoSociedad model = new TipoSociedad();
		model.setIdTipoSociedad(entity.getCveIdTipoSociedad().longValue());
		model.setDescripcion(entity.getDesTipoSociedad());
		
		return model;
	}
	
	

}
