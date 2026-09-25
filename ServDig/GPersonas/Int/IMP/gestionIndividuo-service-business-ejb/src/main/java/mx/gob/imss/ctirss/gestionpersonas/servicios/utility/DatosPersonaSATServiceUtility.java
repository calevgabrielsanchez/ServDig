package mx.gob.imss.ctirss.gestionpersonas.servicios.utility;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.DatosPersonaSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.persistence.DitDatosPersonaSat;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;

@Stateless(mappedName = "datosPersonaSATServiceUtility")
public class DatosPersonaSATServiceUtility extends AbstractServiceUtility
		implements DatosPersonaSATServiceUtilityLocal {

	@Override
	public DatosPersonaSAT transformarDatosPersonaSAT(DitDatosPersonaSat entity)
			throws TransformacionException {

		DatosPersonaSAT model = null;
		
		if(entity == null){
			throw new TransformacionException();
		}
		
		model = new DatosPersonaSAT();
		
		model.setCveDatosSAT(entity.getCveIdDatosSat());
		model.setFechaConstitucion(entity.getFecConstitucion());
		model.setFechaInicioOperaciones(entity.getFecInicioOperaciones());
		
		if (entity.getDitPersonaFisica() != null
				&& entity.getDitPersonaFisica().getCveIdPersonaFisica() > 0) {
			Fisica fisica = new Fisica();
			fisica.setCveFisica(entity.getDitPersonaFisica().getCveIdPersonaFisica());
			model.setPersona(fisica);
		} else if (entity.getDitPersonaMoral() != null
				&& entity.getDitPersonaMoral().getCveIdPersonaMoral() > 0) {
			Moral moral = new Moral();
			moral.setCveMoral(entity.getDitPersonaMoral().getCveIdPersonaMoral());
			model.setPersona(moral);
		}
		
		return model;
	}

	@Override
	public DitDatosPersonaSat transformarDatosPersonaSAT(DatosPersonaSAT model)
			throws TransformacionException {
		
		DitDatosPersonaSat entity = null;
		
		if(model == null){
			throw new TransformacionException();
		}
		
		entity = new DitDatosPersonaSat();
		
		if (model.getCveDatosSAT() != null){
			entity.setCveIdDatosSat(model.getCveDatosSAT());
		}
		
		entity.setFecConstitucion(model.getFechaConstitucion());
		entity.setFecInicioOperaciones(model.getFechaInicioOperaciones());
		
		if(model.getPersona() != null){
			if (model.getPersona() instanceof Fisica
					|| (model.getPersona().getTipoPersona() != null && model
							.getPersona().getTipoPersona().getIdTipoPersona()
							.longValue() == TipoPersonaEnum.FISICA.getId())) {
				
				DitPersonaFisica ditPersonaFisica = new DitPersonaFisica();
				ditPersonaFisica.setCveIdPersonaFisica(((Fisica)model.getPersona()).getCveFisica());
				entity.setDitPersonaFisica(ditPersonaFisica);
				
			} else if (model.getPersona() instanceof Moral
					|| (model.getPersona().getTipoPersona() != null && model
							.getPersona().getTipoPersona().getIdTipoPersona()
							.longValue() == TipoPersonaEnum.MORAL.getId())) {
				
				DitPersonaMoral ditPersonaMoral = new DitPersonaMoral();
				ditPersonaMoral.setCveIdPersonaMoral(((Moral)model.getPersona()).getCveMoral());
				entity.setDitPersonaMoral(ditPersonaMoral);
			}
		}
		
		return entity;
	}

}
