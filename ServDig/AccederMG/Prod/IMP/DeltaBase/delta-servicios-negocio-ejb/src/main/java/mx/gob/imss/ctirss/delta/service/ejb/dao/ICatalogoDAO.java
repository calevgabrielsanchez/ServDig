package mx.gob.imss.ctirss.delta.service.ejb.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;

/**
 * @author Juan Manuel Lopez Lozano
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
public interface ICatalogoDAO<T extends AbstractModel> {

	/**
	 * Metodo para agregar un elemento al catalogo.
	 * @param model
	 * @return
	 */
	T agrega(T model) throws PersistenceException;
	
	/**
	 * Metodo para modificar un elemento del catalogo
	 * @param model
	 * @return
	 */
	T actualiza(T model) throws PersistenceException;
	
	
	/**
	 * Metodo para eliminar un elemento del catalogo
	 * @param model
	 */
	void elimina(T model) throws PersistenceException;
	
	
	/**
	 * Metodo para realizar consultas de los elementos del catalgo
	 * @param filtro
	 * @return
	 */
	List<T> consulta(T filtro);	
	
	/**
	 * Metodo para realizar consulta por clave
	 * @param filtro
	 * @return
	 */
	T consultaPorClave(T filtro);		
	

	/**
	 * Metodo para realizar la consulta de los datos 
	 * @param params
	 * @return
	 */
	DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params);	
	
	
}
