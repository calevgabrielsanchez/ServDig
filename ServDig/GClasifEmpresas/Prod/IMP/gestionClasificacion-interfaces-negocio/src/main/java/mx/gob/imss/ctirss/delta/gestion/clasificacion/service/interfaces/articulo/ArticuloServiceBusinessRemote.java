/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:ArticuloServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.articulo
 *  @Fecha:12/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.articulo;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ArticuloNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.clasificacion.ArticuloModel;

@Remote
public interface ArticuloServiceBusinessRemote {
	
	/**
	 * Consulta un Articulo del CLEM  
	 * @param articulo
	 * @return
	 * @throws ArticuloNoEncontradoException
	 */
	ArticuloModel buscarPorDelegacionSubdelegacion(ArticuloModel articulo) throws ArticuloNoEncontradoException;
	
	/**
	 * Consulta los Articulos utilizados en un CLEM 
	 * @param idClem
	 * @return
	 * @throws ArticuloNoEncontradoException
	 */
	List<ArticuloModel> buscarPorIdClem(Long idClem) throws ArticuloNoEncontradoException;
}
