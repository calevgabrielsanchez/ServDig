/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:ArticuloServiceEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.articulo
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.articulo;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.model.clasificacion.ArticuloModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;

@Local
public interface ArticuloServiceEntityLocal {
	
	ArticuloModel buscarPorDelegacionSubdelegacion(ArticuloModel articulo) throws PersistenceException;
	
	List<ArticuloModel> crearArticulos(DatosClem datosClem)  throws PersistenceException;
	
	List<ArticuloModel> buscarPorIdClem(Long idClem) throws PersistenceException;
	
	Boolean actualizarArticulos(List<ArticuloModel> articulos, DatosClem datosClem) throws PersistenceException;
}
