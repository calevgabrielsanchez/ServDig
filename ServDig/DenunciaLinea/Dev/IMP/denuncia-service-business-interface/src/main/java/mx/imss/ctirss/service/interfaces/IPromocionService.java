/**
 * 
 */
package mx.imss.ctirss.service.interfaces;

import java.util.List;

import mx.imss.ctirss.bean.SelectBean;
import mx.imss.ctirss.framework.base.model.AbstractModel;



/**
 * @author Juan Manuel Lopez Lozano
 * @since 08/10/2011
 */
public interface IPromocionService<T extends AbstractModel> {
	
	/**
	 * 
	 * Obtiene la lista de valores (clave, descripcion) para un combo dependiente
	 * @param clazEntityName Objeto Clase de la tabla Hijo
	 * @param entityParentName Cadena que contiene el nombre del objeto clase de la tabla Padre
	 * @param valueParent Cadena que contiene el valor del padre por el cual se filtrara la informaci�n
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	public List<SelectBean> getOptions(Class clazEntityName, String entityParentName, String valueParent) throws IllegalArgumentException;
	
	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo simple
	 * @param clazEntityName Objeto Clase de la tabla Hijo
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	public List<SelectBean> getOptions (Class clazEntityName) throws IllegalArgumentException;		

}
