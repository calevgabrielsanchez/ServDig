package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.correccion.model.CrtCorrPromInvita;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtPresentacorr;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;

@Stateless
public class PresentacionCorreccionHibernateDAO 
		extends GenericHibernateDAO<CrtPresentacorr, Integer>
		implements PresentacionCorreccionDAO{

	@Override
	public void setPersistentClass(Class<CrtPresentacorr> p) {
		super.setPersistentClass(p);
	}
	
	public PresentacionCorreccionHibernateDAO() {
		setPersistentClass(CrtPresentacorr.class);
	}

	@Override
	public Boolean isFolioCorreccionPresentado(Integer solicitudCorrecionID) {
		List<CrtPresentacorr> list = findByCriteria(Restrictions.eq("cveSolicitudcorr", solicitudCorrecionID));
		if(list.size() > 0){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}

	public CrtInvitacion buscarInvitacionDeSolicitudCorreccion(Integer solicitudCorreccionID) {
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		solicitud.setCveSolicitudCorr(solicitudCorreccionID);
		
		Criteria criteria = getSession().createCriteria(CrtCorrPromInvita.class);
		criteria.add(Restrictions.eq("crtSolicitudcorr.cveSolicitudCorr", solicitudCorreccionID));
		
		CrtCorrPromInvita promocionInvitacion = (CrtCorrPromInvita) criteria.uniqueResult();
		
		CrtInvitacion invitacion = promocionInvitacion.getCrtInvitacion();
		
		return invitacion;
	}

	@Override
	public CrtPresentacorr getByClaveSolCorr(Integer claveSolCorr) {
		// TODO Auto-generated method stub
		List<CrtPresentacorr> list = findByCriteria(Restrictions.eq("cveSolicitudcorr", claveSolCorr));
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
