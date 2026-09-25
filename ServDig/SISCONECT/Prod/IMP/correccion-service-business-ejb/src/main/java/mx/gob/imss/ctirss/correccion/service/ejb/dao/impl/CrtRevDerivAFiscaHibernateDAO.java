package mx.gob.imss.ctirss.correccion.service.ejb.dao.impl;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.model.CrtRevDerivAFisca;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.CrtRevDerivAFiscaDAOLocal;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;

import org.hibernate.Query;

@Stateless
public class CrtRevDerivAFiscaHibernateDAO 
	   extends GenericHibernateDAO<CrtRevDerivAFisca, Long>
	   implements CrtRevDerivAFiscaDAOLocal{
	
	public void setPersistentClass(Class<CrtRevDerivAFisca> p) {
		super.setPersistentClass(p);
	}
	
	public CrtRevDerivAFiscaHibernateDAO() {
		setPersistentClass(CrtRevDerivAFisca.class);
	}

	public CrtRevDerivAFisca buscaPorSolicitudCorr(Integer cveSolicitudCorr) {
		CrtRevDerivAFisca derivacion = null;
		String jpql = "from CrtRevDerivAFisca model where model.cveSolicitudCorr=:claveSolicitud";
		Query query = this.getSession().createQuery(jpql);
		query.setParameter("claveSolicitud",cveSolicitudCorr);
		derivacion = (CrtRevDerivAFisca) query.uniqueResult();
		return derivacion;
	}
}
