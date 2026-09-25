package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitBiene;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

@Stateless
public class BienesServiceUtility extends AbstractServiceUtility implements
		BienesServiceUtilityLocal {

	@Override
	public DitBiene convertirModelToEntity(Bien model) {
		DitBiene entity = new DitBiene();
		DitPatronSujetoObligado ditPatronSujetoObligado = new DitPatronSujetoObligado();
		
		if(model.getId()!= null){
			entity.setCveIdBienes(model.getId());
		}
//		entity.setDesAfectacion(model.getDesAfectacion());
		entity.setDesBienes(model.getDesBienes());
//		entity.setDesUsosBienes(model.getDesUsosBienes());
		entity.setNumCantidad(model.getNumCantidad());
		
		ditPatronSujetoObligado.setCveIdPatronSujetoObligado(model.getSujetoObligado().getCveIdSujetoObligado());
		entity.setDitPatronSujetoObligado(ditPatronSujetoObligado);
		
		return entity;
	}

	@Override
	public Bien convertirEntityToModel(DitBiene entity) {
		Bien model = new Bien();
		SujetoObligado sujetoObligado = new SujetoObligado();
		
//		model.setDesAfectacion(entity.getDesAfectacion());
		model.setDesBienes(entity.getDesBienes());
//		model.setDesUsosBienes(entity.getDesUsosBienes());
		model.setId(entity.getCveIdBienes());
		model.setNumCantidad(entity.getNumCantidad());
		
		sujetoObligado.setCveIdSujetoObligado(entity.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());
		model.setSujetoObligado(sujetoObligado);
		
		return model;
	}

	@Override
	public List<Bien> convertListOfEntitiesToListOfModel(List<DitBiene> origen)
			{
		List<Bien> models = new ArrayList<Bien>();
		
		for(DitBiene entity : origen){
			models.add(convertirEntityToModel(entity));
		}
		
		return models;
	}

	@Override
	public List<DitBiene> convertListOfModelToListOfEntity(List<Bien> origen)
			{
		List<DitBiene> entities = new ArrayList<DitBiene>();
		for(Bien model : origen){
			DitBiene entity = convertirModelToEntity(model);
			entities.add(entity);
		}
		return entities;
	}

}
