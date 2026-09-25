package mx.gob.imss.ctirss.gestionpersonas.servicios.utility;

import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitSituacionSat;

@Stateless(mappedName = "situacionSatServiceUtility")
public class SituacionSatServiceUtility extends AbstractServiceUtility
		implements SituacionSatServiceUtilityLocal {

	@Override
	public SituacionSAT transformarSituacionSat(DitSituacionSat entity)
			throws TransformacionException {

		SituacionSAT model = null;

		if (entity == null) {
			throw new TransformacionException();
		}

		model = new SituacionSAT();
		model.setIdSituacionSAT(entity.getCveSituacionSat());
		
		String descripcionAux[] = entity.getRefDescripcion().split("\\|");
		model.setCveSituacionSAT(descripcionAux[0]);
		model.setDescripcion(descripcionAux[1]);
		
		model.setFechaSituacion(entity.getFecSituacion());

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
	public DitSituacionSat transformarSituacionSat(SituacionSAT model)
			throws TransformacionException {

		DitSituacionSat entity = null;
		
		if(model ==  null){
			throw new TransformacionException();
		}
		
		entity = new DitSituacionSat();
		
		if(model.getIdSituacionSAT() != null){
			entity.setCveSituacionSat(model.getIdSituacionSAT());
		}
		
		// Se concatena la clave y la descripcion para guardarlo en la base de datos
		StringBuffer descripcion = new StringBuffer();
		descripcion.append(StringUtils.isNotBlank(model.getCveSituacionSAT()) ? model.getCveSituacionSAT() : " ");
		descripcion.append("|");
		descripcion.append(StringUtils.isNotBlank(model.getDescripcion()) ? model.getDescripcion() : " ");
		
		entity.setRefDescripcion(descripcion.toString());
		entity.setFecSituacion(model.getFechaSituacion());
		
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
