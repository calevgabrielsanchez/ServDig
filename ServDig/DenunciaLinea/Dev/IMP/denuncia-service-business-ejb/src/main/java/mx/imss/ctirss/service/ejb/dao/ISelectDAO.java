package mx.imss.ctirss.service.ejb.dao;

import java.util.List;

import mx.imss.ctirss.bean.SelectBean;


public interface ISelectDAO {	
	
	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo dependiente
	 * @param clazEntityName Objeto Clase de la tabla Hijo
	 * @param entityParentName Cadena que contiene el nombre del objeto clase de la tabla Padre
	 * @param valueParent Cadena que contiene el valor del padre por el cual se filtrara la informacion
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	public List<SelectBean> getOptions (Class clazEntityName , String entityParentName , String valueParent);
	
	
	public List<SelectBean> getOptions (Class clazEntityName , String param , String paramValue, String x);
	/**
	 * Obtiene la lista de valores (clave, descripcion) para un combo simple
	 * @param clazEntityName Objeto Clase de la tabla Hijo
	 * @return Lista de valores (clave, descripcion) para un combo dependiente
	 */
	public List<SelectBean> getOptions (Class clazEntityName);	
	
	
	public List<SelectBean> getOptions(Class entityName, String entityParentName,String  valueParent, String entityParentName2, String valueParent2) ;
	
	public List<SelectBean> getOptions(Class entityName, String entityParentName, String valueParent, String entityRelationName, String idRelation, String fieldRelation, String  valueRelation  ) ;

}
