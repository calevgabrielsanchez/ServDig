package mx.gob.imss.cit.dacvass.servicios.externos.persistence.util;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;

public class PersistenceUnitSISCOBServiceEntity {

	
	@PersistenceContext(unitName = "SISCOBPersistenceUnit")
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

	
	
	
	/**
	 * 
	 * @return
	 */
	public EntityManager getEntityManager() {
		return em;
	}
	
}
