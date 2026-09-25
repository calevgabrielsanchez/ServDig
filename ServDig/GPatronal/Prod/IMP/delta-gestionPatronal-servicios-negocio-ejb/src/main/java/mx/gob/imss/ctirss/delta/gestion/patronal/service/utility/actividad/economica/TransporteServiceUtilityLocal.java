package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;
import mx.gob.imss.ctirss.delta.persistence.DicTipoCombustible;
import mx.gob.imss.ctirss.delta.persistence.DitEquipoTransporte;

@Local
public interface TransporteServiceUtilityLocal {
	

	DitEquipoTransporte convertirModelToEntity(EquipoTransporte model) ;
	

	EquipoTransporte convertirEntityToModel(DitEquipoTransporte entity);
	
	

	List <EquipoTransporte> convertListOfEntitiesToListOfModel(List <DitEquipoTransporte> origen) throws Exception;
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * TipoCombustible
	 */
	TipoCombustible convertirEntityToModelTipoCombustible(DicTipoCombustible entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return
	 * DicTipoCombustible
	 */
	DicTipoCombustible convertirModelToEntityTipoCombustible(TipoCombustible model); 
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param origen
	 * @return List<TipoCombustible>
	 */
	List<TipoCombustible> convertListOfEntitiesToListOfModelTipoCombustible(List <DicTipoCombustible> origen);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param origen
	 * @param destino
	 * @return DitEquipoTransporte
	 */
	DitEquipoTransporte mergeEntities(DitEquipoTransporte origen, DitEquipoTransporte destino);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param origen
	 * @return
	 */
	List <DitEquipoTransporte> convertListOfModelToListOfEntities(List <EquipoTransporte> origen);
}
