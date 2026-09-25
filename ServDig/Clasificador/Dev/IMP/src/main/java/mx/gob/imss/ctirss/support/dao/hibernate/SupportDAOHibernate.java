/**
 * 
 */
package mx.gob.imss.ctirss.support.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author lucio
 *
 */
public abstract class SupportDAOHibernate extends HibernateDaoSupport{

	
	@Autowired
    public void MyHibernateDaoSupport(@Qualifier("sessionFactory")SessionFactory factory)
    {
        setSessionFactory(factory);
    }

	
	

	
}
