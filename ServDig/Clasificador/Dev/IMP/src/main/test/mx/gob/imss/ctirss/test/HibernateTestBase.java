/**
 * 
 */
package mx.gob.imss.ctirss.test;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * @author lucio
 *
 */
public class HibernateTestBase extends TestCase { 

	
	
	protected Session session; 
	protected SessionFactory sessionFactory;

	protected void setUp() throws Exception { 
	   super.setUp();
	 
	  session = null;
	   sessionFactory = new AnnotationConfiguration() .configure("/hibernate-test.cfg.xml").buildSessionFactory();
	   System.out.println("Configurando ...");
	   session = sessionFactory.openSession();
	}

	protected void tearDown() throws Exception {
	   super.tearDown();
	   session.close(); 
	   sessionFactory.close();
	}
	
}
