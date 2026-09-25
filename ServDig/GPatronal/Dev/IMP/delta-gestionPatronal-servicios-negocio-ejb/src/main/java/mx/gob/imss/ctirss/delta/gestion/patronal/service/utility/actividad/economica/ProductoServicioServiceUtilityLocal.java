package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.persistence.DitProducto;

@Local
public interface ProductoServicioServiceUtilityLocal {
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	DitProducto convertirModelToEntity(Producto model);
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	Producto convertirEntityToModel(DitProducto entity) throws Exception;
	
	

	List <Producto> convertListOfEntitiesToListOfModel(List <DitProducto> origen) throws Exception;
	

	List <DitProducto> convertListOfModelToListOfEntity(List <Producto> origen);


}
