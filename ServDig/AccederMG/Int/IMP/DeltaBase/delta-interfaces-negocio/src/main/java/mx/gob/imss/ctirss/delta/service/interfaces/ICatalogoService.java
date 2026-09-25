/**
 * ICatalogoService.java
 * mx.gob.imss.delta.service.interfaces
 * service-business-interface
 */
package mx.gob.imss.ctirss.delta.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.framework.exceptions.CatalogoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TechnicalPersistenceException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;



/**
 * @author Lucio Duran Silva
 * 
 *
 */

public interface ICatalogoService<T extends AbstractModel>  {

	/**
	 * Mtodo para agregar un elemento al catalogo.
	 * @param model
	 * @return
	 */
	 
	T agregar(T model) throws CatalogoException, TechnicalPersistenceException;
	
	/**
	 * Mtodo para modificar un elemento del catlogo
	 * @param model
	 * @return
	 */
	 
	T actualizar(T model) throws CatalogoException, TechnicalPersistenceException;
	
	
	/**
	 * Mtodo para eliminar un elemento del catlogo
	 * @param model
	 */
	 
	void eliminar(T model) throws CatalogoException, TechnicalPersistenceException;
	
	
	/**
	 * Mtodo para realizar consultas de los elementos del catlgo
	 * @param filtro
	 * @return
	 */
	 
	List<T> consultar(T filtro) throws CatalogoException, TechnicalPersistenceException;
	
	/**
	 * Mtodo para realizar consulta por clave de un elemento del catlogo
	 * @param filtro
	 * @return
	 */
	 
	T consultaPorClave(T filtro) throws CatalogoException, TechnicalPersistenceException;			
		
	/**
	 * Metodo para realizar la consulta de los datos 
	 * @param params
	 * @return
	 */
	 
	DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);
		
	
}
