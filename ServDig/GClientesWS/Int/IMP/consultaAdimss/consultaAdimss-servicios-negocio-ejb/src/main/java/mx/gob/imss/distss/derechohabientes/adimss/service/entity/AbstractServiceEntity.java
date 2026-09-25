package mx.gob.imss.distss.derechohabientes.adimss.service.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

public class AbstractServiceEntity {

	@PersistenceContext(unitName = "ad.imssPersistenceUnit")
	protected EntityManager em;

	protected Session getSession(){
		
		//LA FORMA DE RECUPERAR LA SESION DE HIBERNATE CAMBIA ENTRE APLICATION SERVERS, NO EXISTE UNA FORMA UNIFICADA
		Session session = null;
	    if (em.getDelegate() instanceof org.hibernate.ejb.HibernateEntityManager) {
	    	//ESTA FORMA DE RECUPERAR LA SESION LA UTILIZA GLASSFISH
	    	session = ((org.hibernate.ejb.HibernateEntityManager) em.getDelegate()).getSession();
	    }
	    else {
	    	//LA SIGUIENTE FORMA DE RECUPERAR LA SESION LA UTILIZA WEBLOGIC
	    	session = (Session) em.getDelegate();
	    }
	    return session;
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
	
}
