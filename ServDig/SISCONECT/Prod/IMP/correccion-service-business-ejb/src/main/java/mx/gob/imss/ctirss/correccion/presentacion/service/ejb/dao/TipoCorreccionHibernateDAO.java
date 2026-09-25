package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;

@Stateless
public class TipoCorreccionHibernateDAO 
		extends GenericHibernateDAO<CrcTipoCorr, Long> 
		implements TipoCorreccionDAO {
	
	public void setPersistentClass(Class<CrcTipoCorr> p) {
		// TODO Auto-generated method stub
		super.setPersistentClass(p);
	}
	
	public TipoCorreccionHibernateDAO() {
		setPersistentClass(CrcTipoCorr.class);
	}
}
