package mx.gob.imss.ctirss.correccion.service.ejb.dao.impl;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.SacSubdelegacionDAOLocal;

@Stateless
public class SacSubdelegacionHibernateDAO 
	   extends GenericHibernateDAO<SacSubdelegacion, Long>
	   implements SacSubdelegacionDAOLocal{
	
	public void setPersistentClass(Class<SacSubdelegacion> p) {
		super.setPersistentClass(p);
	}
	
	public SacSubdelegacionHibernateDAO() {
		setPersistentClass(SacSubdelegacion.class);
	}
	
}
