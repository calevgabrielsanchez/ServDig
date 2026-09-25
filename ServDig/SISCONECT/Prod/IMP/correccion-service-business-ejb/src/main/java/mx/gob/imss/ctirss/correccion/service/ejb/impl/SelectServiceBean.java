/**
 * 
 */
package mx.gob.imss.ctirss.correccion.service.ejb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.service.ejb.SelectServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.SelectDAOLocal;

/**
 * @author Juan Manuel Lopez Lozano
 * @since 08/10/2011
 *
 */ 
@Stateless(name="componentComboService", mappedName = "componentComboService") 
public class SelectServiceBean implements SelectServiceRemote{
	
	@EJB SelectDAOLocal daoSelect;		
	
	public List<SelectBean> getOptions(Class clazEntityName, String entityParentName, String valueParent) {
		//Validaciones
		if(clazEntityName==null){
			throw new IllegalArgumentException("");
		}
		return this.daoSelect.getOptions(clazEntityName, entityParentName, valueParent);
	}
	public List<SelectBean> getOptions(Class clazEntityName, String param, String paramValues, String x) {
		//Validaciones
		if(clazEntityName==null){
			throw new IllegalArgumentException("");
		}
		return this.daoSelect.getOptions(clazEntityName, param, paramValues, "");
	}
	public List<SelectBean> getOptions(Class clazEntityName) {
		//Validaciones
		if(clazEntityName==null){
			
		}
		return this.daoSelect.getOptions(clazEntityName);
	}	
	
	public List<SelectBean> getOptions(Class clazEntityName, String entityParentName, String valueParent, String entityParentName2, String valueParent2) {
		//Validaciones
		if(clazEntityName==null){
			throw new IllegalArgumentException("");
		}
		return this.daoSelect.getOptions(clazEntityName, entityParentName, valueParent, entityParentName2, valueParent2);
	}

		public List<SelectBean> getOptions(Class entityName,
			String entityParentName, String valueParent,
			String entityRelationName, String idRelation, String fieldRelation,
			String valueRelation) {
		// TODO Auto-generated method stub
		return this.daoSelect.getOptions(entityName, entityParentName, valueParent, entityRelationName, idRelation, fieldRelation, valueRelation);
	}
	
	
}
