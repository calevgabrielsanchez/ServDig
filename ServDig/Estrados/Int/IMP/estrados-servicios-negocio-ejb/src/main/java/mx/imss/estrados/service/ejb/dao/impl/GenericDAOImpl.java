package mx.imss.estrados.service.ejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;


import mx.imss.estrados.repository.AbstractRespository;
import mx.imss.estrados.service.ejb.dao.GenericDAO;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Transaction;

@Stateless
public class GenericDAOImpl<T> extends AbstractRespository implements GenericDAO<T> {

	@Override
	public List<T> getAll(Class<T> persistentClass) {
        Criteria criteria = getSession().createCriteria(persistentClass);       
        return criteria.list();
	}

	@Override
	public List<T> getByQuery(String query) {
		// TODO Auto-generated method stub
		Query consulta=getSession().createQuery(query);
		
		List<T> lista=consulta.list();
		return lista;
	}

	
	public T saveOrUpdate(T model) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		try{
			tx=this.getSession().beginTransaction();
			this.getSession().saveOrUpdate(model);			
			tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
	
		return model;
	}

	@Override
	public T eliminar(T model) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		try{
			tx=this.getSession().beginTransaction();
			this.getSession().delete(model);	
			tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
	
		return model;
	}

}
