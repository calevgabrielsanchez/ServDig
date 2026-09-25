package mx.imss.ctirss.service.ejb.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface GenericDAO<T, ID extends Serializable> {

	public T findById(ID id, boolean lock);
	public List<T> findAll();
	public List<T> findByExample(T exampleInstance, String... excludeProperty);
	public T makePersistent(T model);
	public void makeTransient(T model);
	public List<T> findByCriteria(Criterion... criterion);
	public T findConcreteByCriteria(Criterion... criterion);

}
