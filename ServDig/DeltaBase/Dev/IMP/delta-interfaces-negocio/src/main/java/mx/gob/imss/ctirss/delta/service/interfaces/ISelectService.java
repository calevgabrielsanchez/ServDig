/**
 * 
 */
package mx.gob.imss.ctirss.delta.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.exceptions.TechnicalPersistenceException;
import mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean;


/**
 * @author Juan Manuel Lopez Lozano
 * @since 08/10/2011
 */
public interface ISelectService {
	
	/**
	 * 
	 * Obtiene la lista de valores (clave, descripcion) para un combo dependiente
	 * @param String clazEntityName Nombre del Objeto Clase de la tabla Hijo
	 * @param entityParentName Cadena que contiene el nombre del objeto clase de la tabla Padre
	 * @param valueParent Cadena que contiene el valor del padre por el cual se filtrara la informacion
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getOptions(String clazEntityName, String entityParentName, String valueParent) throws TechnicalPersistenceException;
	
	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo simple
	 * @param String clazEntityName Nombre del Objeto Clase de la tabla Hijo
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getOptions (String clazEntityName) throws TechnicalPersistenceException;		

	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo de entidad federativa con estados de mexico
	 * @param String clazEntityName Nombre del Objeto Clase de la tabla Hijo	
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getOptionsEstado (String clazEntityName) throws TechnicalPersistenceException;
	
	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo de entidad federativa con estados de mexico
	 * @param String clazEntityName Nombre del Objeto Clase de la tabla Hijo	
	 * @param campoVigencia Cadena que indica cual es el nombre del campo donde se comparara la vigencia
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getActiveOptions (String clazEntityName, String campoVigencia) throws TechnicalPersistenceException;
	
	
	/**
	 * 
	 * Obtiene la lista de valores (clave, descripcion) para un combo dependiente
	 * @param String clazEntityName Nombre del Objeto Clase de la tabla Hijo
	 * @param entityParentName Cadena que contiene el nombre del objeto clase de la tabla Padre
	 * @param valueParent Cadena que contiene el valor del padre por el cual se filtrara la informacion
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getActiveOptions(String clazEntityName, String entityParentName, String valueParent) throws TechnicalPersistenceException;
	
	/**
	 * 
	 * Obtiene la lista de valores (clave, descripcion) para un combo dependiente
	 * @param String clazEntityName Nombre del Objeto Clase de la tabla Hijo
	 * @param entityParentName Cadena que contiene el nombre del objeto clase de la tabla Padre
	 * @param valueParent Cadena que contiene el valor del padre por el cual se filtrara la informacion
	 * @param campoVigencia Cadena que indica cual es el nombre del campo donde se comparara la vigencia
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getActiveOptions(String clazEntityName, String entityParentName, String valueParent, String campoVigencia) throws TechnicalPersistenceException;
	
	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo simple
	 * @param String clazEntityName Nombre del Objeto Clase de la tabla Hijo
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getActiveOptions (String clazEntityName) throws TechnicalPersistenceException;		

	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo de entidad federativa con estados de mexico
	 * @param String clazEntityName Nombre del Objeto Clase de la tabla Hijo	
	 * @param campoEntidades boolean que indica si trae solo entidades validas
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	List<SelectBean> getActiveEntitiesOptions (String clazEntityName, boolean campoEntidades) throws TechnicalPersistenceException;

}
