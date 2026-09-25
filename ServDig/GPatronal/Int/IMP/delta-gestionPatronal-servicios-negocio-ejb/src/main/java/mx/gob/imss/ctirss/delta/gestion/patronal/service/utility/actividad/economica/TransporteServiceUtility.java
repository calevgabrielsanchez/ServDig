package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;
import mx.gob.imss.ctirss.delta.persistence.DicTipoCombustible;
import mx.gob.imss.ctirss.delta.persistence.DitEquipoTransporte;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

@Stateless
public class TransporteServiceUtility extends AbstractServiceUtility implements
		TransporteServiceUtilityLocal {

	@Override
	public DitEquipoTransporte convertirModelToEntity(EquipoTransporte model)
			{
		DitEquipoTransporte transporte = new DitEquipoTransporte();
		System.out.println("Model transporte: "+model.getId());
		System.out.println("Nuevo Tranporte: "+transporte);
		if(model.getId()!=null){
			transporte.setCveIdEquipoTransporte(model.getId());
		}
		
		
		transporte.setDesCapacidadPotencia(model.getDesCapacidadPotencia());
		transporte.setDesNombre(model.getDesNombre());
		transporte.setDesUso(model.getDesUso());
		transporte.setNumUnidades(model.getNumUnidades());
		transporte.setDicTipoCombustible(convertirModelToEntityTipoCombustible(model.getTipoCombustible()));
		DitPatronSujetoObligado so = new DitPatronSujetoObligado();
		so.setCveIdPatronSujetoObligado(model.getSujetoObligado().getCveIdSujetoObligado());
		transporte.setDitPatronSujetoObligado(so);
		return transporte;
	}
 
	@Override
	public EquipoTransporte convertirEntityToModel(DitEquipoTransporte entity)
			{
		EquipoTransporte model = new EquipoTransporte();
		model.setDesCapacidadPotencia(entity.getDesCapacidadPotencia());
		model.setDesNombre(entity.getDesNombre());
		model.setDesUso(entity.getDesUso());
		model.setId(entity.getCveIdEquipoTransporte());
		model.setNumUnidades(entity.getNumUnidades());
		model.setTipoCombustible(convertirEntityToModelTipoCombustible(entity.getDicTipoCombustible()));
		return model;
	}
	
	@Override
	public List<EquipoTransporte> convertListOfEntitiesToListOfModel(
			List<DitEquipoTransporte> origen) throws Exception {
		
		List<EquipoTransporte> transportes = new ArrayList<EquipoTransporte>(); 
		for(DitEquipoTransporte entity : origen){
			EquipoTransporte model = convertirEntityToModel(entity);
			transportes.add(model);
		}
		return transportes;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelTipoCombustible(mx.gob.imss.ctirss.delta.persistence.DicTipoCombustible)
	 */
	@Override
	public TipoCombustible convertirEntityToModelTipoCombustible(
			DicTipoCombustible entity) {
		TipoCombustible model = new TipoCombustible();
		model.setClave(entity.getCveIdTipoCombustible());
		model.setDesTipoCombustible(entity.getDesTipoCombustible());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.TransporteServiceUtilityLocal#convertirModelToEntityTipoCombustible(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible)
	 */
	@Override
	public DicTipoCombustible convertirModelToEntityTipoCombustible(
			TipoCombustible model) {
		DicTipoCombustible tipoCombustible = new DicTipoCombustible();
		tipoCombustible.setCveIdTipoCombustible(model.getClave());
		tipoCombustible.setDesTipoCombustible(model.getDesTipoCombustible());
		return tipoCombustible;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.TransporteServiceUtilityLocal#convertListOfEntitiesToListOfModelTipoCombustible(java.util.List)
	 */
	@Override
	public List<TipoCombustible> convertListOfEntitiesToListOfModelTipoCombustible(
			List<DicTipoCombustible> origen){
		
		List<TipoCombustible> tipos = new ArrayList<TipoCombustible>();
		for(DicTipoCombustible tipoCombustible : origen){
			tipos.add(convertirEntityToModelTipoCombustible(tipoCombustible));
		}
		return tipos;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.TransporteServiceUtilityLocal#mergeEntities(mx.gob.imss.ctirss.delta.persistence.DitEquipoTransporte, mx.gob.imss.ctirss.delta.persistence.DitEquipoTransporte)
	 */
	@Override
	public DitEquipoTransporte mergeEntities(DitEquipoTransporte origen,
			DitEquipoTransporte destino) {
		destino.setCveIdEquipoTransporte(origen.getCveIdEquipoTransporte());
		destino.setDesCapacidadPotencia(origen.getDesCapacidadPotencia());
		destino.setDesNombre(origen.getDesNombre());
		destino.setDesUso(origen.getDesUso());
		destino.setDicTipoCombustible(origen.getDicTipoCombustible());
		destino.setDitPatronSujetoObligado(origen.getDitPatronSujetoObligado());
		destino.setFecRegistroActualizado(origen.getFecRegistroActualizado());
		destino.setFecRegistroAlta(origen.getFecRegistroAlta());
		destino.setFecRegistroBaja(origen.getFecRegistroBaja());
		destino.setNumUnidades(origen.getNumUnidades());
		return destino;
	}

	@Override
	public List<DitEquipoTransporte> convertListOfModelToListOfEntities(
			List<EquipoTransporte> origen) {
		List<DitEquipoTransporte> entities = Collections.emptyList();
		
		if(origen!=null){
			entities=new ArrayList<DitEquipoTransporte>();
		}
		for(EquipoTransporte model : origen){
			DitEquipoTransporte entity = convertirModelToEntity(model);
			entities.add(entity);
		}
		return entities;
	}
	
	
}
