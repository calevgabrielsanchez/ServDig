package mx.gob.imss.ctirss.delta.service.ejb.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean;



public interface ISelectDAO {
	
	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo dependiente
	 * @param clazEntityName Objeto Clase de la tabla Hijo
	 * @param entityParentName Cadena que contiene el nombre del objeto clase de la tabla Padre
	 * @param valueParent Cadena que contiene el valor del padre por el cual se filtrara la informacion
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getOptions (Class clazEntityName , String entityParentName , String valueParent, boolean soloActivos, String campoVigencia) throws PersistenceException;
	
	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo simple
	 * @param clazEntityName Objeto Clase de la tabla Hijo
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getOptions (Class clazEntityName, boolean soloActivos, String campoVigencia) throws PersistenceException;	
	
	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo de entidad federativa filtrando los estados de mexico
	 * @param clazEntityName Objeto Clase de la tabla Hijo	 
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getOptionsEstado (Class clazEntityName) throws PersistenceException;


	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo simple
	 * @param clazEntityName Objeto Clase de la tabla Hijo
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getOptionsEntities(Class entityName, boolean soloActivos, boolean campoEntidades)  throws PersistenceException;

}
