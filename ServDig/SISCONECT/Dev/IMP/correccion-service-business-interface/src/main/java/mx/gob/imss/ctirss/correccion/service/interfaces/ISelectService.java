/**
 * 
 */
package mx.gob.imss.ctirss.correccion.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;



/**
 * @author Juan Manuel Lopez Lozano
 * @since 08/10/2011
 */
public interface ISelectService<T extends AbstractModel> {
	
	/**
	 * 
	 * Obtiene la lista de valores (clave, descripcion) para un combo dependiente
	 * @param clazEntityName Objeto Clase de la tabla Hijo
	 * @param entityParentName Cadena que contiene el nombre del objeto clase de la tabla Padre
	 * @param valueParent Cadena que contiene el valor del padre por el cual se filtrara la informacian
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	public List<SelectBean> getOptions(Class clazEntityName, String entityParentName, String valueParent) throws IllegalArgumentException;
	
	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo simple
	 * @param clazEntityName Objeto Clase de la tabla Hijo
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	public List<SelectBean> getOptions (Class clazEntityName) throws IllegalArgumentException;		
	
	
	public List<SelectBean> getOptions (Class clazEntityName, String param, String paramValue, String x) throws IllegalArgumentException;		

	public List<SelectBean> getOptions(Class entityName,	String entityParentName, String valueParent,
			String entityRelationName, String idRelation, String fieldRelation,
			String valueRelation) ;
}
