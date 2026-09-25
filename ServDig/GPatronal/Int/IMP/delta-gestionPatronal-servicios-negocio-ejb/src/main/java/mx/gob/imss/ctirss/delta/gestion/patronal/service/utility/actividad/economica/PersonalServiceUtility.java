package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersonal;

@Stateless
public class PersonalServiceUtility extends AbstractServiceUtility implements
		PersonalServiceUtilityLocal {

	@Override
	public DitPersonal convertirModelToEntity(Personal model){
		DitPersonal entity = new DitPersonal();
		DitPatronSujetoObligado ditPatronSujetoObligado = new DitPatronSujetoObligado();
		if(model.getClave()!= null){
			entity.setCveIdPersonal(model.getClave());
		}
		entity.setNumeroTrabajadores(model.getNumTrabajadores());
		entity.setOficioOcupacion(model.getOficioOcupacion());
		ditPatronSujetoObligado.setCveIdPatronSujetoObligado(model.getSujetoObligado().getCveIdSujetoObligado());
		entity.setDitPatronSujetoObligado(ditPatronSujetoObligado);
		
		return entity;
	}

	@Override
	public Personal convertirEntityToModel(DitPersonal entity) throws Exception {
		Personal model = new Personal();
		SujetoObligado sujetoObligado = new SujetoObligado();
		model.setClave(entity.getCveIdPersonal());
		model.setNumTrabajadores(entity.getNumeroTrabajadores());
		model.setOficioOcupacion(entity.getOficioOcupacion());
		
		sujetoObligado.setCveIdSujetoObligado(entity.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());
		model.setSujetoObligado(sujetoObligado);
		
		return model;
	}

	@Override
	public List<Personal> convertListOfEntitiesToListOfModel(
			List<DitPersonal> origen) throws Exception {
		
		List<Personal> models = new ArrayList<Personal>();
		
		for(DitPersonal entity : origen){
			Personal personal = convertirEntityToModel(entity);
			models.add(personal);
		}
		
		return models;
	}

	@Override
	public List<DitPersonal> convertListOfModelToListOfEntity(
			List<Personal> origen){
		List<DitPersonal> entities = new ArrayList<DitPersonal>();
		for(Personal model:origen){
			DitPersonal entity = convertirModelToEntity(model);
			entities.add(entity);
		}
		return entities;
	}

}
