package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;

@Stateless
public class SatPatronHibernateDAO 
			extends GenericHibernateDAO<SatPatron, Long> 
			implements SatPatronDAO{
	
	public void setPersistentClass(Class<SatPatron> p) {
		super.setPersistentClass(p);
	}
	
	public SatPatronHibernateDAO() {
		setPersistentClass(SatPatron.class);
	}
}