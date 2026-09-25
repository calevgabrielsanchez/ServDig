package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DitMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

@Stateless
public class MaquinariaEquipoServiceUtility extends AbstractServiceUtility
		implements MaquinariaEquipoServiceUtilityLocal {

	@Override
	public DitMaquinariaEquipo convertirModelToEntity(MaquinariaEquipo model)
			{
		DitMaquinariaEquipo maq = new DitMaquinariaEquipo();
		System.out.println("Modelo: " +model);
		System.out.println("Modelo Id: "+model.getId());
		System.out.println("Maquinaria Obj: "+maq);
		if(model.getId() !=null ){
			maq.setCveIdMaquinariaEquipo(model.getId());
		}
		
		maq.setDesCapacidadPotencia(model.getDesCapacidadPotencia());
		System.out.println("Numero de unidades: "+model.getNumUnidades());
		maq.setNumUnidades(model.getNumUnidades());
		maq.setDesNombre(model.getDesNombre());
		maq.setDesUso(model.getDesUso());
		maq.setDicTipoMaquinariaEquipo(convertirModelToEntityTipoMaquinaria(model.getTipo()));
		maq.setDitPatronSujetoObligado(generaDitPatronSujetoObligadoConIdentificador(model.getSujetoObligado().getCveIdSujetoObligado()));
		return maq;
	}

	@Override
	public MaquinariaEquipo convertirEntityToModel(DitMaquinariaEquipo entity)
		{
		MaquinariaEquipo model = new MaquinariaEquipo();
		model.setId(entity.getCveIdMaquinariaEquipo());
		model.setDesCapacidadPotencia(entity.getDesCapacidadPotencia());
		model.setDesUso(entity.getDesUso());
		model.setDesNombre(entity.getDesNombre());
		model.setTipo(convertirEntityToModelTipoMaquinariaEquipo(entity.getDicTipoMaquinariaEquipo()));
		model.setNumUnidades(entity.getNumUnidades());
		return model;
	}

	@Override
	public List<MaquinariaEquipo> convertListOfEntitiesToListOfModel(
			List<DitMaquinariaEquipo> origen) throws Exception {
		List<MaquinariaEquipo> equipos = new ArrayList<MaquinariaEquipo>();
		for(DitMaquinariaEquipo entity:origen){
			MaquinariaEquipo equipo=convertirEntityToModel(entity);
			equipos.add(equipo);
		}
		return equipos;
	}

	@Override
	public List<DitMaquinariaEquipo> convertListOfModelToListOfEntity(
			List<MaquinariaEquipo> origen){
		
		List<DitMaquinariaEquipo> equipos=Collections.emptyList();
		if(origen!=null){
			equipos = new ArrayList<DitMaquinariaEquipo>();
		}
		for(MaquinariaEquipo equipo : origen){
			DitMaquinariaEquipo entity=convertirModelToEntity(equipo);
			equipos.add(entity);
		}
		return equipos;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal#convertirEntityToModelTipoMaquinariaEquipo(mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.DicTipoMaquinariaEquipo)
	 */
	@Override
	public TipoMaquinariaEquipo convertirEntityToModelTipoMaquinariaEquipo(
			DicTipoMaquinariaEquipo entity) {
		TipoMaquinariaEquipo model = new TipoMaquinariaEquipo();
		model.setId(entity.getCveIdTipoMaquinariaEquipo());
		model.setDescripcion(entity.getDesTipoMaquinariaEquipo());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.MaquinariaEquipoServiceUtilityLocal#convertirModelToEntityTipoMaquinaria(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo)
	 */
	@Override
	public DicTipoMaquinariaEquipo convertirModelToEntityTipoMaquinaria(
			TipoMaquinariaEquipo model) {
		DicTipoMaquinariaEquipo entity = new DicTipoMaquinariaEquipo();
		entity.setCveIdTipoMaquinariaEquipo(model.getId());
		entity.setDesTipoMaquinariaEquipo(model.getDescripcion());
		return entity;
	}
	
	private DitPatronSujetoObligado generaDitPatronSujetoObligadoConIdentificador(Long idPatron){
		DitPatronSujetoObligado so = new DitPatronSujetoObligado();
		so.setCveIdPatronSujetoObligado(idPatron);
		return 	so;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.MaquinariaEquipoServiceUtilityLocal#mergeEntities(mx.gob.imss.ctirss.delta.persistence.DitMaquinariaEquipo, mx.gob.imss.ctirss.delta.persistence.DitMaquinariaEquipo)
	 */
	@Override
	public DitMaquinariaEquipo mergeEntities(DitMaquinariaEquipo source,
			DitMaquinariaEquipo target) {
		target.setCveIdMaquinariaEquipo(source.getCveIdMaquinariaEquipo());
		target.setDesCapacidadPotencia(source.getDesCapacidadPotencia());
		target.setDesNombre(source.getDesNombre());
		target.setDesUso(source.getDesUso());
		target.setDicTipoMaquinariaEquipo(source.getDicTipoMaquinariaEquipo());
		target.setDitPatronSujetoObligado(source.getDitPatronSujetoObligado());
		target.setFecRegistroActualizado(source.getFecRegistroActualizado());
		target.setFecRegistroAlta(source.getFecRegistroAlta());
		target.setFecRegistroBaja(source.getFecRegistroBaja());
		target.setNumUnidades(source.getNumUnidades());
		return target;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica.MaquinariaEquipoServiceUtilityLocal#convertListOfEntitiesToListOfModelTipoMaquinaria(java.util.List)
	 */
	@Override
	public List<TipoMaquinariaEquipo> convertListOfEntitiesToListOfModelTipoMaquinaria(
			List<DicTipoMaquinariaEquipo> origen) {
		List<TipoMaquinariaEquipo> tiposMaquinaria = new ArrayList<TipoMaquinariaEquipo>();
		for(DicTipoMaquinariaEquipo entity : origen){
			tiposMaquinaria.add(convertirEntityToModelTipoMaquinariaEquipo(entity));
		}
		return tiposMaquinaria;
	}
	
	
}
