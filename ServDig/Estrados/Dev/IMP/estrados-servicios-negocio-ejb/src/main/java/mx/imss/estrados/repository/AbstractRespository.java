package mx.imss.estrados.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

public abstract class AbstractRespository {
	
	@PersistenceContext(unitName="estradosPersistenceUnit")
	private EntityManager entityManager;
	
	protected Session getSession() {
		return (Session)entityManager.getDelegate();
	}

}
