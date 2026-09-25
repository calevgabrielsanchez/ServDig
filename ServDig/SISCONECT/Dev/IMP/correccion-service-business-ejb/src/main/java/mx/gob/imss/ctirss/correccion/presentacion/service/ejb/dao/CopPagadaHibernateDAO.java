package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.model.CrtCoppagada;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;

import org.hibernate.Query;

@Stateless
public class CopPagadaHibernateDAO 
		extends GenericHibernateDAO<CrtCoppagada, Long>
		implements CopPagadaDAO{

	public CopPagadaHibernateDAO() {
		setPersistentClass(CrtCoppagada.class);
	}
	
	public void setPersistentClass(Class<CrtCoppagada> p) {
		super.setPersistentClass(p);
	}

	public Long isFolioCorreccionConCopPagada(Integer solicitudCorreccion) {				  
		Query qry = getSession().createQuery("select count(*) from CrtCoppagada cop where cop.cveAnexoSolCorrPat in (select anexo.cveAnexoSolicitudCorrPat from CrtAnexosolcorrpat anexo where cve_solicitudcorr = :solicitud)");
		qry.setParameter("solicitud", solicitudCorreccion);
		Long rows = ((Long)qry.uniqueResult());
		return rows;
	}

	@Override
	public Object[] sumaCOPbyClaveAnexoSolCorr(Integer claveAnexoSolCorr) {

		System.out.println("anexo busqueda "+claveAnexoSolCorr);
		Query que=getSession().createQuery("select sum(cop.impCop),sum(cop.impCopact),sum(cop.impCoprec),sum(cop.impCoptot),sum(cop.nuTrabregu) from CrtCoppagada cop where cop.cveAnexoSolCorrPat=:anexo ");
		que.setParameter("anexo", claveAnexoSolCorr);
		
		System.out.println("antes");
		Object ob=que.uniqueResult();
		Object[] res= (Object[]) ob;
		return res;
	
		
	}

	@Override
	public Object[] sumaRCVbyClaveAnexoSolCorr(Integer claveAnexoSolCorr) {

		Query que=getSession().createQuery("select sum(cop.impRcv),sum(cop.impRcvact),sum(cop.impRcvrec),sum(cop.impRcvtot),sum(cop.nuTrabregu) from CrtCoppagada cop where cop.cveAnexoSolCorrPat=:anexo ");
		que.setParameter("anexo", claveAnexoSolCorr);
		
		System.out.println("antesRCV");
		Object ob=que.uniqueResult();
		Object[] res= (Object[]) ob;
		return res;
	}
}
