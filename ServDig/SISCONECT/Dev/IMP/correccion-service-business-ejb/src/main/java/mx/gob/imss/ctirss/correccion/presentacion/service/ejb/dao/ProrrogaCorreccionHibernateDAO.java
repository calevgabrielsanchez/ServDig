package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import javax.ejb.Stateless;
import javax.mail.internet.NewsAddress;

import org.hibernate.Query;

import mx.gob.imss.ctirss.correccion.model.CrtProrroga;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;

@Stateless
public class ProrrogaCorreccionHibernateDAO
		extends GenericHibernateDAO<CrtProrroga, Long>
		implements ProrrogaCorreccionDAO{
	
	@Override
	public void setPersistentClass(Class<CrtProrroga> p) {
		super.setPersistentClass(p);
	}
	
	public ProrrogaCorreccionHibernateDAO() {
		setPersistentClass(CrtProrroga.class);
	}

	@Override
	public CrtProrroga getByClaveSolCorr(Integer claveSolCorr) {
		// TODO Auto-generated method stub
		StringBuffer qu=new StringBuffer();
		qu.append("FROM CrtProrroga po where po.cveSolicitudcorr=:claveSolCorr");
		Query query=getSession().createQuery(qu.toString());
		query.setParameter("claveSolCorr", claveSolCorr);
		return (CrtProrroga) query.uniqueResult();
	}

}
