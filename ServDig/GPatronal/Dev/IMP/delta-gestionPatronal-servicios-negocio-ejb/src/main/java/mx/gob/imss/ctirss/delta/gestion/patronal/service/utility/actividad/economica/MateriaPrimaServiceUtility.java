package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.actividad.economica.MateriaPrimaServiceEntityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitMateriaPrimaMaterial;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

@Stateless
public class MateriaPrimaServiceUtility extends AbstractServiceUtility
		implements MateriaPrimaServiceUtilityLocal {
	
	@EJB
	private MateriaPrimaServiceEntityLocal materiaPrimaServiceEntity;

	@Override
	public DitMateriaPrimaMaterial convertirModelToEntity(
			MateriaPrima model){
		DitMateriaPrimaMaterial entity = new DitMateriaPrimaMaterial();
		DitPatronSujetoObligado ditPatronSujetoObligado = new DitPatronSujetoObligado();
		
		if (model.getId() != null){
			entity.setCveIdMateriaPrimaMaterial(model.getId());				
		}

		ditPatronSujetoObligado.setCveIdPatronSujetoObligado(model.getSujetoObligado().getCveIdSujetoObligado());
		entity.setDitPatronSujetoObligado(ditPatronSujetoObligado);
		
		entity.setDesMateriaPrimaMaterial(model.getDescripcion());
		
		return entity;
	}

	@Override
	public MateriaPrima convertirEntityToModel(DitMateriaPrimaMaterial entity)
			throws Exception {
		
		MateriaPrima model = new MateriaPrima();
		SujetoObligado sujetoObligado = new SujetoObligado();
		
		this.copyBeans(entity, model);
		
		sujetoObligado.setCveIdSujetoObligado(entity.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());
		
		model.setId(entity.getCveIdMateriaPrimaMaterial());
		model.setDescripcion(entity.getDesMateriaPrimaMaterial());
		model.setSujetoObligado(sujetoObligado);
		
		return model;
	}

	@Override
	public List<MateriaPrima> convertListOfEntitiesToListOfModel(
			List<DitMateriaPrimaMaterial> origen) throws Exception {
		List<MateriaPrima> models = new ArrayList<MateriaPrima>();

		for (DitMateriaPrimaMaterial entity : origen) {
			models.add(convertirEntityToModel(entity));
		}
		
		return models;
	}

	@Override
	public void validaLimMaxRegMateriaPrima(MateriaPrima materiaPrima)
			throws Exception {
		// verificar el limite maximo de registros (10 para este caso)
        int numRegistros = this.materiaPrimaServiceEntity.consultarNumRegistros(materiaPrima);

        log.debug("validaLimMaxRegMateriaPrimaMaterial.numRegistros: " + numRegistros);

        if (numRegistros >= 10) {
            String msg = "Se ha excedido del numero maximo de registros para Materia Prima, registros en BD: " + numRegistros;
			log.debug(msg);
            throw new Exception(msg);
        }
		
	}

	@Override
	public MateriaPrima validaAgregarMateriaPrima(MateriaPrima materiaPrima)
			throws Exception {
		return this.materiaPrimaServiceEntity.validaExisteMateriaPrimaMaterial(materiaPrima);
	}

	@Override
	public void validaBorrarMateriaPrima(MateriaPrima materiaPrima)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DitMateriaPrimaMaterial> convertirListOfModelToListOfEntities(
			List<MateriaPrima> models) {
		List<DitMateriaPrimaMaterial> entities = Collections.emptyList();
		if(models!= null){
			entities = new ArrayList<DitMateriaPrimaMaterial>();
		}
		for(MateriaPrima model:models){
			DitMateriaPrimaMaterial entity = convertirModelToEntity(model);
			entities.add(entity);
		}
		return entities;
	}

}
