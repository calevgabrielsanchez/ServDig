/**
 * 
 */
package mx.gob.imss.ctirss.correccion.service.ejb.dao.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.SelectDAOLocal;

import org.apache.log4j.Logger;
import org.hibernate.Query;

/**
 * @author Juan Manuel Lopez Lozano
 * @since 08/10/2011
 * 
 */ 
@Stateless
public class SelectDAOBean extends AbstractRespository implements SelectDAOLocal{
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(SelectDAOBean.class);
	
	public List<SelectBean> getOptions(Class entityName, String entityParentName, String valueParent) {
		logger.debug(".-..--. Ingresa en SelectDAOBean - getOptions(Class claz, String entityParentName, String valueParent)");
		StringBuffer hql = new StringBuffer();
		//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
		List<String> lsLlavePrimaria = SelectDAOBean.getLlavePrimaria(entityName);
		
		//Obtiene la descripcion
		String sLlavePrimaria = lsLlavePrimaria.get(0);
		String sDesc	= SelectDAOBean.getDescripcionComponenteCombo(entityName);		
		
		//Modificacion 11/11/11 obtener opciones por mas de una llave PK o FK
		
		String whereClause="";
		hql.append( "select new  mx.gob.imss.ctirss.correccion.bean.SelectBean( c.").append(sLlavePrimaria).append(", c.").
		append(sDesc).append(") from  " + entityName.getName() + " c  where ");
		
		int parameterIndex=0;
		int parameterAuxIndex=0;
		String parameterName;
		Query query = null;
		
		if(entityParentName.contains(",") && valueParent.contains(",") ){
			StringTokenizer stEntity = new StringTokenizer(entityParentName,",");
			String tokenEntity ="";
			
			while(stEntity.hasMoreTokens()){
				parameterAuxIndex++;
				if(whereClause.length()!=0)
					whereClause+=" AND ";
				
				tokenEntity = stEntity.nextToken();
				
				
				parameterIndex++;
				parameterName="numIndex"+parameterIndex;
				//La arroba nos permite intercambiar variables
				if(tokenEntity.contains("@")) 
					tokenEntity = tokenEntity.substring(tokenEntity.lastIndexOf("@")+1,tokenEntity.length());
				
				whereClause+=" c." + tokenEntity +" = :" + parameterName + " ";
			}
			hql.append(whereClause);
			
			getOrderClause(entityName,hql);
			
			logger.debug("********************* Query:"+hql.toString());
			query = this.getSession().createQuery(hql.toString());
			
			String tokenValue ="";
			StringTokenizer stValues = null;
			
			if(valueParent.contains(",")){
				stValues = new StringTokenizer(valueParent,",");
				if(!stValues.hasMoreTokens()){
					valueParent="";
					for(int i=0;i<parameterAuxIndex;i++)
						valueParent += "-1,";
				}
				
				stValues = new StringTokenizer(valueParent,",");
			}
				
			
			
			parameterIndex = 0;
			
			while(stValues.hasMoreTokens()){
				
				tokenValue = stValues.nextToken();
				
				parameterIndex++;
				parameterName="numIndex"+parameterIndex;
				
				query.setParameter(parameterName,tokenValue);
				
			}
			
		}else{
			parameterName="numIndex1";
			if(entityParentName.contains("@")) 
				entityParentName = entityParentName.substring(entityParentName.lastIndexOf("@")+1,entityParentName.length());
			hql.append("c." + entityParentName +" = :"+parameterName);
			
			getOrderClause(entityName,hql);
			
			logger.debug("********************* Query:"+hql.toString());
			query = this.getSession().createQuery(hql.toString());
			
			query.setParameter(parameterName,valueParent);
			
		}
		
		logger.debug(".-..--. retornara "+query.list().size()+"; registros");
		return query.list();
	}
	
	private StringBuffer getOrderClause(Class entityName,StringBuffer hql){
		
		List<String> lsOrdenCombos = SelectDAOBean.getOrdenCombo(entityName);
		
		if(lsOrdenCombos!=null && !lsOrdenCombos.isEmpty()){
			
			hql.append(" Order By ");
			
			Iterator<String> iter = lsOrdenCombos.iterator();
			String currentValue ="";
			
			while(iter.hasNext()){
				currentValue = iter.next();
				
				if(iter.hasNext())
					hql.append(" c."+currentValue+",");
				else
					hql.append(" c."+currentValue);
				
			}
			
		}
		
		return hql;
		
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ISelectDAO#getOptions(java.lang.Class)
	 */
	public List<SelectBean> getOptions(Class entityName) {
		logger.debug(".-..--. Ingresa en SelectDAOBean - getOptions(Class claz) :: " + entityName.getName());		
		final StringBuffer hql = new StringBuffer();
		//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
		final List<String> lsLlavePrimaria = SelectDAOBean.getLlavePrimaria(entityName);
		if (lsLlavePrimaria==null && lsLlavePrimaria.isEmpty()){
			return Collections.EMPTY_LIST;
		}
		//Obtiene la descripcion
		String sLlavePrimaria = lsLlavePrimaria.get(0);
		String sDesc	= SelectDAOBean.getDescripcionComponenteCombo(entityName);
		hql.append( "select new mx.gob.imss.ctirss.correccion.bean.SelectBean( c.").append(sLlavePrimaria).append(", c.").append(sDesc).append(") from  " + entityName.getName() + " c ");
		
		getOrderClause(entityName, hql);
		
		logger.debug("************ Query:"+hql.toString());
		Query query = this.getSession().createQuery(hql.toString());
		logger.debug(".-..--. retornara "+query.list().size()+"; registros");	
		return query.list();
	}	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ISelectDAO#getOptions(java.lang.Class)
	 */
	public List<SelectBean> getOptions(Class entityName, String param, String paramValue, String x) {
		logger.debug("asdkfasjkfakslfkljasfklasklfalksdf");
		
		StringBuffer hql = new StringBuffer();
		//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
		List<String> lsLlavePrimaria = SelectDAOBean.getLlavePrimaria(entityName);
		//Obtiene la descripcion
		String sLlavePrimaria = lsLlavePrimaria.get(0);
		String sDesc	= SelectDAOBean.getDescripcionComponenteCombo(entityName);
		hql.append( "select   new  mx.gob.imss.ctirss.correccion.bean.SelectBean( c.").append(sLlavePrimaria).append(", c.").append(sDesc).append(") from  " + entityName.getName() + " c where c." +param + " in( "+ paramValue + " )");
		Query query = this.getSession().createQuery(hql.toString());
		return query.list();
	}	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ISelectDAO#getOptions(java.lang.Class, java.lang.String, java.lang.String)
	 */
	public List<SelectBean> getOptions(Class entityName, String entityParentName,String  valueParent, String entityParentName2, String valueParent2) {
		StringBuffer hql = new StringBuffer();
		//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
		List<String> lsLlavePrimaria = SelectDAOBean.getLlavePrimaria(entityName);
		//Obtiene la descripcion
		String sLlavePrimaria = lsLlavePrimaria.get(0);
		String sDesc	= SelectDAOBean.getDescripcionComponenteCombo(entityName);		
		hql.append( "select   new mx.gob.imss.ctirss.correccion.bean.SelectBean( c.").append(sLlavePrimaria).append(", c.").append(sDesc).append(") from  " + entityName.getName() + " c  where c." + entityParentName +" = :valueParent and c."+entityParentName2 + " = :valueParent2"  );
		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter("valueParent",valueParent);
		query.setParameter("valueParetn2", valueParent2);
		return query.list();
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ISelectDAO#getOptions(java.lang.Class, java.lang.String, java.lang.String)
	 */
	public List<SelectBean> getOptions(Class entityName, String entityParentName, String valueParent, String entityRelationName, String idRelation, String fieldRelation, String  valueRelation  ) {
		StringBuffer hql = new StringBuffer();
		//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
		List<String> lsLlavePrimaria = SelectDAOBean.getLlavePrimaria(entityName);
		//Obtiene la descripcion
		String sLlavePrimaria = lsLlavePrimaria.get(0);
		logger.debug("llave primaria " + sLlavePrimaria);
		String sDesc	= SelectDAOBean.getDescripcionComponenteCombo(entityName);		
		hql.append( "select   new mx.gob.imss.ctirss.correccion.bean.SelectBean( c.").append(sLlavePrimaria).append(", c.").append(sDesc).append(") from  " + entityName.getName() + " c  where c." + sLlavePrimaria +" in (select d.cgcCatOrigen.idOrigen  from "+ entityRelationName + " d where d."+fieldRelation +"= :fieldValue )"   );
		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter("fieldValue",valueRelation);
		
		return query.list();
	}

}
