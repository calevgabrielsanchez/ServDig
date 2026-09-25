/**
 * 
 */
package mx.gob.imss.ctirss.delta.service.ejb.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.framework.exceptions.TechnicalPersistenceException;
import mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean;
import mx.gob.imss.ctirss.delta.service.ejb.SelectServiceRemote;
import mx.gob.imss.ctirss.delta.service.ejb.dao.SelectDAOLocal;

/**
 * @author Juan Manuel Lopez Lozano
 * @since 08/10/2011
 *
 */ 
@Stateless(name="componentComboService", mappedName = "componentComboService") 
public class SelectServiceBean implements SelectServiceRemote{
	
	@EJB SelectDAOLocal daoSelect;
	
	public List<SelectBean> getOptions(String clazEntityName, String entityParentName, String valueParent) throws TechnicalPersistenceException{
		//Validaciones
		if(clazEntityName==null){
			throw new IllegalArgumentException("");
		}
		try{
			Class claz = null;
			try {
				claz = Class.forName(clazEntityName);
			} catch (ClassNotFoundException e) {
				throw new TechnicalPersistenceException();
			}
			return this.daoSelect.getOptions(claz, entityParentName, valueParent, false,null);			
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe);
		}
	}
	
	public List<SelectBean> getOptions(String clazEntityName) throws TechnicalPersistenceException{
		//Validaciones

			
			
			try{
				Class claz = null;
				try {
					claz = Class.forName(clazEntityName);
				} catch (ClassNotFoundException e) {
					throw new TechnicalPersistenceException();
				}
			
			
			return this.daoSelect.getOptions(claz,false, null);
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe);
		}		

}
	
	

	@Override
	public List<SelectBean> getActiveOptions(String clazEntityName,
			String campoVigencia) throws TechnicalPersistenceException {
		//Validaciones
		if(clazEntityName==null){
			throw new IllegalArgumentException("");
		}
		try{
			Class claz = null;
			try {
				claz = Class.forName(clazEntityName);
			} catch (ClassNotFoundException e) {
				throw new TechnicalPersistenceException();
			}
			return this.daoSelect.getOptions(claz,true, campoVigencia);			
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe);
		}
	}

	@Override
	public List<SelectBean> getActiveEntitiesOptions(String clazEntityName,
			boolean campoEntidades) throws TechnicalPersistenceException {
		//Validaciones
		if(clazEntityName==null){
			throw new IllegalArgumentException("");
		}
		try{
			Class claz = null;
			try {
				claz = Class.forName(clazEntityName);
			} catch (ClassNotFoundException e) {
				throw new TechnicalPersistenceException();
			}
			return this.daoSelect.getOptionsEntities(claz,true, campoEntidades);			
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe);
		}
	}

	@Override
	public List<SelectBean> getActiveOptions(String clazEntityName,
			String entityParentName, String valueParent, String campoVigencia)
			throws TechnicalPersistenceException {
		//Validaciones
		if(clazEntityName==null){
			throw new IllegalArgumentException("");
		}
		try{
			Class claz = null;
			try {
				claz = Class.forName(clazEntityName);
			} catch (ClassNotFoundException e) {
				throw new TechnicalPersistenceException();
			}
			return this.daoSelect.getOptions(claz, entityParentName, valueParent, true, campoVigencia);			
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe);
		}
	}

	@Override
	public List<SelectBean> getOptionsEstado(String clazEntityName)
			throws TechnicalPersistenceException {
		//Validaciones

			try{
				Class claz = null;
				try {
					claz = Class.forName(clazEntityName);
				} catch (ClassNotFoundException e) {
					throw new TechnicalPersistenceException();
				}
			
			
			return this.daoSelect.getOptionsEstado(claz);
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe);
		}		

	}

	@Override
	public List<SelectBean> getActiveOptions(String clazEntityName,
			String entityParentName, String valueParent)
			throws TechnicalPersistenceException {
		//Validaciones
		if(clazEntityName==null){
			throw new IllegalArgumentException("");
		}
		try{
			Class claz = null;
			try {
				claz = Class.forName(clazEntityName);
			} catch (ClassNotFoundException e) {
				throw new TechnicalPersistenceException();
			}
			return this.daoSelect.getOptions(claz, entityParentName, valueParent, true,null);			
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe);
		}
	}
	

	@Override
	public List<SelectBean> getActiveOptions(String clazEntityName) throws TechnicalPersistenceException {
		//Validaciones

			
			
		try{
			Class claz = null;
			try {
				claz = Class.forName(clazEntityName);
			} catch (ClassNotFoundException e) {
				throw new TechnicalPersistenceException();
			}
		
		
			return this.daoSelect.getOptions(claz,true,null);
		}catch(PersistenceException pe){
			throw new TechnicalPersistenceException(pe);
		}
	}
}