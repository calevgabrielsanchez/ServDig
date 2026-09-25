package mx.gob.imss.ctirss.delta.service.ejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean;
import mx.gob.imss.ctirss.delta.service.ejb.dao.SelectDAOLocal;

import org.hibernate.Query;

/**
 * @author Juan Manuel Lopez Lozano
 * @since 08/10/2011
 *
 */
@Stateless
public class SelectDAOBean extends AbstractServiceEntity implements SelectDAOLocal{
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ISelectDAO#getOptions(java.lang.Class, java.lang.String, java.lang.String)
	 */
	public List<SelectBean> getOptions(Class entityName, String entityParentName, String valueParent, boolean soloActivos, String campoVigencia) throws PersistenceException{
		try{
			StringBuffer hql = new StringBuffer();
			//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
			List<String> lsLlavePrimaria = SelectDAOBean.getLlavePrimaria(entityName);
			//Obtiene la descripcion
			String sLlavePrimaria = lsLlavePrimaria.get(0);
			String sDesc	= SelectDAOBean.getDescripcionComponenteCombo(entityName);		
			hql.append( "select  new mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean( c.").
						append(sLlavePrimaria).append(", c.").append(sDesc).append(") " +
						"from  " + entityName.getName() + " c  where c." + entityParentName +" = :valueParent ");
			if(soloActivos){
				if(campoVigencia == null) {
					hql.append(" and c.fecRegistroBaja is null");
				} else {
					hql.append(" and c."+campoVigencia+" is null");
				}
			}			
			hql.append(" order by c."+sDesc);
			Query query = this.getSession().createQuery(hql.toString());
			query.setParameter("valueParent",valueParent);
			return query.list();			
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException(re);
		}
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ISelectDAO#getOptions(java.lang.Class)
	 */
	public List<SelectBean> getOptions(Class entityName, boolean soloActivos, String campoVigencia)  throws PersistenceException{
		try{
			StringBuffer hql = new StringBuffer();
			//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
			List<String> lsLlavePrimaria = SelectDAOBean.getLlavePrimaria(entityName);
			//Obtiene la descripcion
			String sLlavePrimaria = lsLlavePrimaria.get(0);
			String sDesc	= SelectDAOBean.getDescripcionComponenteCombo(entityName);
			hql.append( "select  new mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean( c.").append(sLlavePrimaria).append(", c.").
			append(sDesc).append(") from  " + entityName.getName() +" c "); 
			
			if(soloActivos){
				if(campoVigencia == null) {
					hql.append(" where c.fecRegistroBaja is null");
				} else {
					hql.append(" where c."+campoVigencia+" is null");
				}
			}	
			hql.append(" order by c."+sDesc);
			Query query = this.getSession().createQuery(hql.toString());
			return query.list();
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException(re);
		}				
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ISelectDAO#getOptions(java.lang.Class)
	 */
	public List<SelectBean> getOptionsEntities(Class entityName, boolean soloActivos, boolean campoEntidades)  throws PersistenceException{
		try{
			StringBuffer hql = new StringBuffer();
			//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
			List<String> lsLlavePrimaria = SelectDAOBean.getLlavePrimaria(entityName);
			//Obtiene la descripcion
			String sLlavePrimaria = lsLlavePrimaria.get(0);
			String sDesc	= SelectDAOBean.getDescripcionComponenteCombo(entityName);
			hql.append( "select  new mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean( c.").append(sLlavePrimaria).append(", c.").
			append(sDesc).append(") from  " + entityName.getName() +" c "); 
			
			if(soloActivos){
					hql.append(" where c.fecRegistroBaja is null");
			}		
				
			hql.append(" and c.indEdoGeografico =:indEdoGeografico");
			
			hql.append(" order by c."+sDesc);

			Query query = this.getSession().createQuery(hql.toString());
			query.setParameter("indEdoGeografico",campoEntidades);
			return query.list();
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException(re);
		}				
	}


	@Override
	public List<SelectBean> getOptionsEstado(Class entityName)
			throws PersistenceException {
		try{
			StringBuffer hql = new StringBuffer();
			//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
			List<String> lsLlavePrimaria = SelectDAOBean.getLlavePrimaria(entityName);
			//Obtiene la descripcion
			String sLlavePrimaria = lsLlavePrimaria.get(0);
			String sDesc	= SelectDAOBean.getDescripcionComponenteCombo(entityName);
			hql.append( "select  new mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean( c.").append(sLlavePrimaria).append(", c.").append(sDesc).append(") from  " + entityName.getName() + " c where c.dicPai.cveIdPais = 1 order by c."+sDesc);
			Query query = this.getSession().createQuery(hql.toString());
			return query.list();			
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException(re);
		}	
	}	

}
