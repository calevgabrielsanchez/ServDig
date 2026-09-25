package mx.gob.imss.csdiss.sdroc.orm.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.csdiss.sdroc.orm.dao.AbstractDao;


public abstract class AbstractDaoImpl<Entity, ID extends Serializable>  implements AbstractDao<Entity, ID> {

	@Autowired
	private SessionFactory sessionFactory;

	private Class<Entity> entityClasss;

	@SuppressWarnings("unchecked")
	public AbstractDaoImpl(){
		 this.entityClasss = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];  
	} 

	@SuppressWarnings("unchecked")
	public List<Entity> findAll() {
		List<Entity> resultList = null;
		resultList = getSession().createQuery("from " + getEntityClass().getName()).list();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public Entity findByID(Long id) {
		return (Entity) getSession().get(getEntityClass(),id);
	}

	@SuppressWarnings("unchecked")
   public ID save(Entity entity) {
		ID a = null;
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SDROC-PROD-TEST DAO");
			a = (ID) getSession().save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		
		return a;
	  
	}

   public void merge(Entity entity) {
	   getSession().merge(entity);
	}

   public void delete(Entity entity) {
	   getSession().delete(entity);
	}
   
	public Class<Entity> getEntityClass(){
		return this.entityClasss;
	}

	@SuppressWarnings("unchecked")
	public List<Entity> findMany(Query query) {		
		List<Entity> list = (List<Entity>) query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findManyObject(Query query) {		
		List<Object[]> list = (List<Object[]>) query.list();
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public Entity findOne(Query query) {
		Entity uniqueResult = (Entity) query.uniqueResult();
		return uniqueResult; 
	}
	
	
	public Object findOneObjectBySqlQuery(Query query) {		
		Object uniqueResult = query.uniqueResult();
		return uniqueResult;
	}
		
	
	protected Session getSession() { 
		Session hibernateSession = sessionFactory.getCurrentSession(); 
		return hibernateSession; 
	} 

	
}
