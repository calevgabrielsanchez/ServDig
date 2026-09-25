package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.model.CgcCatMotivoRechazo;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;

@Stateless
public class MotivoRechazoHibernateDAO 
	 	extends GenericHibernateDAO<CgcCatMotivoRechazo, Long> 
		implements MotivoRechazoDAO {
	
	public void setPersistentClass(Class<CgcCatMotivoRechazo> p) {
		super.setPersistentClass(p);
	}
	
	public MotivoRechazoHibernateDAO() {
		setPersistentClass(CgcCatMotivoRechazo.class);
	}

}
