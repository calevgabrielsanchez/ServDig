package mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.ErrorValidation;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;

@Stateless
public class AnexoSolicitudCorreccionHibernateDAO 
	   extends GenericHibernateDAO<CrtAnexosolcorrpat, Integer>
	   implements AnexoSolicitudCorreccionDAO{
	
	public void setPersistentClass(Class<CrtAnexosolcorrpat> p) {
		super.setPersistentClass(p);
	}
	
	public AnexoSolicitudCorreccionHibernateDAO() {
		setPersistentClass(CrtAnexosolcorrpat.class);
	}

	public List<ErrorValidation> isFolioCorrecionConAnexoPatronal(Integer solicitudCorreccion) {
		List<ErrorValidation> errores = new ArrayList<ErrorValidation>(); 
		/** Patron principal */
		List<CrtAnexosolcorrpat> anexoPatPrincipal = findByCriteria(Restrictions.eq("cveSolicitudCorr", solicitudCorreccion), Restrictions.isNull("cvePatronPr"));
		if(anexoPatPrincipal.size() <= 0){
			errores.add(new ErrorValidation("El anexo patronal para esta solicitud de corrección no se encuentra en el sistema", true));
		}
		
		return errores;
	}

	@Override
	public List<Object> getByClaveSolicitudCorrEjercicio(Integer claveSolCorr, Long ejercicio) {

		
		List<Object> obje=null;
		StringBuffer query=new StringBuffer();
		query.append(" SELECT patron.registroPatronal,anexo.cveAnexoSolicitudCorrPat from CrtAnexosolcorrpat anexo,SatPatron patron, CrcEjercicio ejercicio where");
		query.append(" anexo.cveAnexoSolicitudCorrPat=ejercicio.cveAcexoCorrPat and");
		query.append(" anexo.cvePatron=patron.cvePK  and ");
		query.append(" anexo.cveSolicitudCorr=:cveSolicitudCorr and");
		query.append(" ejercicio.cveEjercicio=:cveEjercicio and");
		query.append(" anexo.tipoPatron=:tipoPatron");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("cveSolicitudCorr", claveSolCorr);
		consulta.setParameter("cveEjercicio", ejercicio);
		consulta.setParameter("tipoPatron", CrtAnexosolcorrpat.TIPO_REGISTRO_RP_FISCAL);
		obje=consulta.list();
		return obje;
	}

	@Override
	public CrtAnexosolcorrpat getByClaveSolicitudCorrRegistroPatronal(
			Integer claveSolCorr,String registroPatronal) {
		
		StringBuffer query=new StringBuffer();
		query.append("select anexo from CrtAnexosolcorrpat anexo,SatPatron patron where patron.cvePK=anexo.cvePatron and anexo.cveSolicitudCorr=:cveSolicitudCorr and patron.registroPatronal=:registroPatronal");
		query.append(" and anexo.tipoPatron=:tipoPatron");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("cveSolicitudCorr", claveSolCorr);
		consulta.setParameter("registroPatronal", registroPatronal);	
		consulta.setParameter("tipoPatron", CrtAnexosolcorrpat.TIPO_REGISTRO_RP_FISCAL);
		return (CrtAnexosolcorrpat) consulta.uniqueResult();
	}

	@Override
	public String getRegistroPatronalByAnexoSolCorrPat(Integer anexoSolCorrPatt) {

		StringBuffer query=new StringBuffer();
		query.append("select patron.registroPatronal from CrtAnexosolcorrpat anexo,SatPatron patron where patron.cvePK=anexo.cvePatron  ");
		query.append(" and anexo.cveAnexoSolicitudCorrPat=:cveAnexoSolicitudCorrPat ");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("cveAnexoSolicitudCorrPat", anexoSolCorrPatt);
		return (String) consulta.uniqueResult();
	}

	@Override
	public List<Long> getEjerciciosByCveSolCorr(
			Integer cveSolCorrPat) {
		
		StringBuffer query=new StringBuffer();
		query.append("SELECT distinct ejercicio.cveEjercicio FROM CrtAnexosolcorrpat anexo,CrcEjercicio ejercicio where anexo.cveAnexoSolicitudCorrPat=ejercicio.cveAcexoCorrPat ");
		query.append(" and anexo.cveSolicitudCorr=:cveSolicitudCorr");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("cveSolicitudCorr", cveSolCorrPat);
		List<Long> ejercicios=consulta.list();
		return ejercicios;
	}
	
}
