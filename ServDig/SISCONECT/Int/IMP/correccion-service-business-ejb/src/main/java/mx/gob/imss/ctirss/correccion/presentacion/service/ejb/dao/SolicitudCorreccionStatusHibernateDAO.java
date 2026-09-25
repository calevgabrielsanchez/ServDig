package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.model.CrcStatus;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;

@Stateless
public class SolicitudCorreccionStatusHibernateDAO 
		extends GenericHibernateDAO<CrcStatus, Long> implements SolicitudCorreccionStatusDAO{

	public void setPersistentClass(Class<CrcStatus> p) {
		// TODO Auto-generated method stub
		super.setPersistentClass(p);
	}
	
	public SolicitudCorreccionStatusHibernateDAO() {
		setPersistentClass(CrcStatus.class);
	}
}
