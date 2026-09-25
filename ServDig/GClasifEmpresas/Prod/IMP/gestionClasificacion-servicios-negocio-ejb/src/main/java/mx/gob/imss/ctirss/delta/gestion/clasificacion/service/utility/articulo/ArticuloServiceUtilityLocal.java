/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ArticuloServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.articulo
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.articulo;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.base.exception.ModelAccessException;
import mx.gob.imss.ctirss.delta.model.clasificacion.ArticuloModel;
import mx.gob.imss.ctirss.delta.persistence.DitArticulo;

@Local
public interface ArticuloServiceUtilityLocal {

	ArticuloModel convertirEntityToModel(DitArticulo articulo) throws ModelAccessException;
	
	DitArticulo convertirModelToEntity(ArticuloModel articulo) throws ModelAccessException;
	
	List<ArticuloModel> convertirEntityToModel(List<DitArticulo> articulo) throws ModelAccessException;
}
