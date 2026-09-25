package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DitMaquinariaEquipo;

@Local
public interface MaquinariaEquipoServiceUtilityLocal {
	
	DitMaquinariaEquipo convertirModelToEntity(MaquinariaEquipo model);
	

	MaquinariaEquipo convertirEntityToModel(DitMaquinariaEquipo entity);
	

	List <MaquinariaEquipo> convertListOfEntitiesToListOfModel(List <DitMaquinariaEquipo> origen) throws Exception;


	List <DitMaquinariaEquipo> convertListOfModelToListOfEntity(List <MaquinariaEquipo> origen);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @return
	 * TipoMaquinariaEquipo
	 */
	TipoMaquinariaEquipo convertirEntityToModelTipoMaquinariaEquipo(DicTipoMaquinariaEquipo entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return
	 * DicTipoMaquinariaEquipo
	 */
	DicTipoMaquinariaEquipo convertirModelToEntityTipoMaquinaria(TipoMaquinariaEquipo model);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @return
	 * DitMaquinariaEquipo
	 */
	DitMaquinariaEquipo mergeEntities(DitMaquinariaEquipo source, DitMaquinariaEquipo target);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param origen
	 * @return List<TipoMaquinariaEquipo>
	 */
	List <TipoMaquinariaEquipo> convertListOfEntitiesToListOfModelTipoMaquinaria(List <DicTipoMaquinariaEquipo> origen);

}
