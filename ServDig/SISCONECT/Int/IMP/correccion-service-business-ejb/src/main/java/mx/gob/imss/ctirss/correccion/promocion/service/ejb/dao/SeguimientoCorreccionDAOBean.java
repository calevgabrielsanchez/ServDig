package mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtPresentacorr;
import mx.gob.imss.ctirss.correccion.model.CrtRevCedRevValAclara;
import mx.gob.imss.ctirss.correccion.model.CrtRevCedRevision;
import mx.gob.imss.ctirss.correccion.model.CrtRevDerivASubd;
import mx.gob.imss.ctirss.correccion.model.CrtRevOficios;
import mx.gob.imss.ctirss.correccion.model.CrtRevRecepcion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

@Stateless
public class SeguimientoCorreccionDAOBean <T extends AbstractModel> extends AbstractRespository implements SeguimientoCorreccionDAOLocal<T> {

	
	
	public CrtRevRecepcion guardaRecepcion(CrtRevRecepcion crtRevRecepcion){
		try{
			this.getSession().saveOrUpdate(crtRevRecepcion);
			this.getSession().flush();			
		}catch(Exception re){
			logger.debug(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}
		return crtRevRecepcion;
	}
	
	
	@Override
	public CrtRevOficios consultaRevOficiosPorClave(CrtRevOficios crtRevOficios) {
		Criteria criteria = this.getSession().createCriteria(crtRevOficios.getClass());
		criteria.add(Restrictions.eq("cvePresentaCorr", crtRevOficios.getCvePresentaCorr()));
		if(crtRevOficios.getId_TipoOficio() != null){
			criteria.add(Restrictions.eq("id_TipoOficio", crtRevOficios.getId_TipoOficio()));
		}
		return (CrtRevOficios) criteria.uniqueResult();
	}
	
	public CrtRevRecepcion consultaRecepcion(CrtRevRecepcion crtRevRecepcion){
		Criteria criteria = this.getSession().createCriteria(crtRevRecepcion.getClass());
		criteria.add(Restrictions.eq("cvePresentacorr", crtRevRecepcion.getCvePresentacorr()));
		criteria.add(Restrictions.eq("indTipoPago", crtRevRecepcion.getIndTipoPago()));
		return (CrtRevRecepcion) criteria.uniqueResult();
	}

	@Override
	public CrtRevOficios guardaReqDoc(CrtRevOficios crtRevOficios) {
		try{
			this.getSession().saveOrUpdate(crtRevOficios);
			this.getSession().flush();
			return crtRevOficios;
		}catch(Exception re){
			logger.debug(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}
	}

	@Override
	public CrtPresentacorr consultaCrtPresentacorrPorClave(CrtPresentacorr crtPresentacorr) {
		Criteria criteria = this.getSession().createCriteria(crtPresentacorr.getClass());
		criteria.add(Restrictions.eq("cveSolicitudcorr", crtPresentacorr.getCveSolicitudcorr()));
		return (CrtPresentacorr) criteria.uniqueResult();
	}

	public CrtPresentacorr consultaCrtPresentacorrByPk(CrtPresentacorr crtPresentacorr){
		Criteria criteria = this.getSession().createCriteria(crtPresentacorr.getClass());
		criteria.add(Restrictions.eq("cvePresentacorr", crtPresentacorr.getCvePresentacorr()));
		return (CrtPresentacorr) criteria.uniqueResult();
	}
	
	public CrtSolicitudcorr getSolicitudCorr(CrtSolicitudcorr crtSolicitudcorr){
		Criteria criteria = this.getSession().createCriteria(crtSolicitudcorr.getClass());
		criteria.add(Restrictions.eq("cveSolicitudCorr", crtSolicitudcorr.getCveSolicitudCorr()));
		return (CrtSolicitudcorr) criteria.uniqueResult();	
	}
	
	@Override
	public CrtRevOficios consultaRevOficiosPorCveSolCorr(CrtRevOficios crtRevOficios) {
	
		CrtPresentacorr crtPresentacorr = new CrtPresentacorr();
		try{
			if(crtRevOficios != null && crtRevOficios.getCveSolCorr() != null){
				crtPresentacorr.setCveSolicitudcorr(crtRevOficios.getCveSolCorr().intValue());
				crtPresentacorr = this.consultaCrtPresentacorrPorClave(crtPresentacorr);
				if(crtPresentacorr != null && crtPresentacorr.getCvePresentacorr() != null){
					crtRevOficios.setCvePresentaCorr(crtPresentacorr.getCvePresentacorr());
					crtRevOficios = this.consultaRevOficiosPorClave(crtRevOficios);
				}				
			}			
			return crtRevOficios;
		}catch(Exception re){
			logger.debug(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}
	}


	@Override
	public CrtRevCedRevision guardaCedulaRevision(
			CrtRevCedRevision crtRevCedRevision) {
		try{
			
			this.getSession().saveOrUpdate(crtRevCedRevision);
			this.getSession().flush();
			return crtRevCedRevision;
		}catch(Exception re){
			logger.debug(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}
	}


	@Override
	public CrtRevCedRevValAclara guardaCedulaRevisionAclarado(
			CrtRevCedRevValAclara crtRevCedRevValAclara) {
		// TODO Auto-generated method stub
		try{
			this.getSession().saveOrUpdate(crtRevCedRevValAclara);
			this.getSession().flush();
			return crtRevCedRevValAclara;
		}catch(Exception re){
			logger.debug(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}
	}


	@Override
	public CrtRevCedRevision consultaCedulaRevisionPorParams(
			CrtRevCedRevision crtRevCedRevision) {
		StringBuilder query=new StringBuilder();
		query.append("SELECT rev FROM CrtRevCedRevision rev,CrtAnexosolcorrpat anexo,SatPatron patron where rev.cveAnexoSolicitudCorrPat=anexo.cveAnexoSolicitudCorrPat");
		query.append(" and patron.cvePK=anexo.cvePatron");
		query.append(" and rev.cveEjercicio=:ejercicio");
		query.append(" and rev.cvePresentaCorr=:cvePresentaCorr");
		query.append(" and patron.registroPatronal=:registroPatronal");
		query.append(" and anexo.cveAnexoSolicitudCorrPat=:cveAnexoSolicitudCorrPat ");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("ejercicio", crtRevCedRevision.getCveEjercicio());
		consulta.setParameter("cvePresentaCorr", crtRevCedRevision.getCvePresentaCorr());
		consulta.setParameter("registroPatronal", crtRevCedRevision.getRegistroPatronal());
		consulta.setParameter("cveAnexoSolicitudCorrPat",crtRevCedRevision.getCveAnexoSolicitudCorrPat());
		
		
		logger.info("Cve presentacion "+crtRevCedRevision.getCvePresentaCorr());
		logger.info("Ejercicio "+crtRevCedRevision.getCveEjercicio());
		logger.info("RegPAtron "+crtRevCedRevision.getRegistroPatronal());
		logger.info("CveAnexoSol "+crtRevCedRevision.getCveAnexoSolicitudCorrPat());
		
		System.out.println("El query de consulta es "+query.toString());
		return (CrtRevCedRevision) consulta.uniqueResult();
	}


	@Override
	public CrtAnexosolcorrpat consultaAnexoPorRegPatSolCorrEjer(String regPatr,
			Long cvSolCorr, Integer ejercicio) {
		// TODO Auto-generated method stub
		
		StringBuilder query=new StringBuilder();
		query.append("SELECT anexo FROM CrtAnexosolcorrpat anexo,CrtSolicitudcorr solCorr,SatPatron patron,CrcEjercicio ejercicio where anexo.cveSolicitudCorr=solCorr.cveSolicitudCorr ");
		query.append(" and patron.cvePK=anexo.cvePatron ");
		query.append(" and anexo.cveAnexoSolicitudCorrPat=ejercicio.cveAcexoCorrPat");
		query.append(" and patron.registroPatronal=:registroPatronal");
		query.append(" and solCorr.cveSolicitudCorr=:cveSolicitudCorr");
		query.append(" and ejercicio.cveEjercicio=:cveEjercicio	");
		Query consulta=getSession().createQuery(query.toString());		
		consulta.setParameter("registroPatronal", regPatr);
		consulta.setParameter("cveSolicitudCorr", cvSolCorr);
		consulta.setParameter("cveEjercicio", ejercicio);
		List<CrtAnexosolcorrpat> list=consulta.list();
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}


	@Override
	public List<CrtRevCedRevValAclara> consultaRubrosRevCeduAclarado(
			CrtRevCedRevValAclara crtRevCedRevValAclara) {
		StringBuilder query=new StringBuilder();
		query.append("FROM CrtRevCedRevValAclara aclara");
		query.append(" where aclara.cveAnexoSolicitudCorrPat=:cveAnexoSolicitudCorrPat ");
		query.append(" and aclara.cveEjercicio=:cveEjercicio ");
		query.append(" and aclara.cvePresentaCorr=:cvePresentaCorr ");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("cveAnexoSolicitudCorrPat", crtRevCedRevValAclara.getCveAnexoSolicitudCorrPat());
		consulta.setParameter("cveEjercicio", crtRevCedRevValAclara.getCveEjercicio());
		consulta.setParameter("cvePresentaCorr", crtRevCedRevValAclara.getCvePresentaCorr());
		List<CrtRevCedRevValAclara> lista=consulta.list();
		return lista;
	}


	@Override
	public List<CrtRevCedRevValAclara> consultaRubrosRevCeduAclaradoByCvePresentacion(
			CrtRevCedRevValAclara crtRevCedRevValAclara) {
		// TODO Auto-generated method stub
		StringBuilder query=new StringBuilder();
		query.append("FROM CrtRevCedRevValAclara aclara");
		query.append(" where  ");
		query.append(" aclara.cvePresentaCorr=:cvePresentaCorr ");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("cvePresentaCorr",  crtRevCedRevValAclara.getCvePresentaCorr());
		List<CrtRevCedRevValAclara> lista=consulta.list();
		return lista;
	}
	
	
	
	@Override
	public CrtRevCedRevValAclara eliminaCedulaRevisionAclarado(
			CrtRevCedRevValAclara crtRevCedRevValAclara) {
		// TODO Auto-generated method stub
		try{
			this.getSession().delete(crtRevCedRevValAclara);
			this.getSession().flush();
			return crtRevCedRevValAclara;
		}catch(Exception re){
			logger.debug(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}
	}


	@Override
	public List<CrtRevCedRevision> consultaCedulaRevisionPorCvePresenta(
			CrtRevCedRevision crtRevCedRevision) {
		// TODO Auto-generated method stub
		StringBuilder query=new StringBuilder();
		query.append("SELECT rev FROM CrtRevCedRevision rev where ");
		query.append(" rev.cvePresentaCorr=:cvePresentaCorr");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("cvePresentaCorr", crtRevCedRevision.getCvePresentaCorr());		
		return consulta.list();
	}


	@Override
	public BigDecimal sumaTotalPorAclarar(CrtRevCedRevision crtRevCedRevision) {
		// TODO Auto-generated method stub
		StringBuilder query=new StringBuilder();
		query.append("SELECT SUM(aclara.impRevPorAclarar) FROM CrtRevCedRevValAclara aclara where ");
		query.append(" aclara.cveAnexoSolicitudCorrPat=:cveAnexoSolicitudCorrPat and");
		query.append(" aclara.cveEjercicio=:cveEjercicio and ");
		query.append(" aclara.cvePresentaCorr=:cvePresentaCorr 	");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("cveAnexoSolicitudCorrPat", crtRevCedRevision.getCveAnexoSolicitudCorrPat());		
		consulta.setParameter("cveEjercicio", crtRevCedRevision.getCveEjercicio());
		consulta.setParameter("cvePresentaCorr", crtRevCedRevision.getCvePresentaCorr());
		return (BigDecimal) consulta.uniqueResult();
	}


	@Override
	public List<Object> consultaRegistroCedulaRevByCvePresenta(
			Integer cvPresentacion) {
		// TODO Auto-generated method stub
		
		StringBuilder query=new StringBuilder();
		query.append("SELECT anexo.cveAnexoSolicitudCorrPat,ejercicio.cveEjercicio,presenta.cvePresentacorr FROM CrtPresentacorr presenta,CrtSolicitudcorr solcorr,CrtAnexosolcorrpat anexo,CrcEjercicio ejercicio,SatPatron patron ");
		query.append(" where presenta.cveSolicitudcorr=solcorr.cveSolicitudCorr and ");
		query.append(" solcorr.cveSolicitudCorr=anexo.cveSolicitudCorr and");
		query.append(" anexo.cveAnexoSolicitudCorrPat=ejercicio.cveAcexoCorrPat and");
		query.append(" anexo.cvePatron=patron.cvePK and ");
		query.append(" anexo.tipoPatron=:tipoPatron and");
		query.append(" presenta.cvePresentacorr=:cvePresentacorr");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("cvePresentacorr", cvPresentacion);
		consulta.setParameter("tipoPatron", CrtAnexosolcorrpat.TIPO_REGISTRO_RP_FISCAL);
		return consulta.list();
	}


	/*@Override
	public CrtRevRecepcion consultaRecepcion(CrtRevRecepcion crtRevRecepcion) {
		// TODO Auto-generated method stub
		StringBuilder query=new StringBuilder();
		query.append("FROM CrtRevRecepcion rec where rec.cvePresentacorr=:cvePresentacorr ");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("cvePresentacorr", crtRevRecepcion.getCvePresentacorr());
		return (CrtRevRecepcion) consulta.uniqueResult();
	}*/

	@Override
	public List<Object> consultaCedulaValidacionConsolidado(Integer cvePresentacion) {
		
		StringBuilder query=new StringBuilder();
		query.append("select p.TX_REMUNERACION, impxAclarar, aclarado, aclaradoOfR, totPagado from ");
		query.append("(select ac.CVE_PRESENTACORR, ac.CVE_PERCEPCION, ");
		query.append("NVL(sum(IMP_REV_PORACLARAR),0) impxAclarar, NVL(sum(IMP_VAL_ACLARADO),0) aclarado, ");
		query.append("NVL(sum(IMP_VAL_ACLARADOOFRESUL),0) aclaradoOfR, NVL(sum(IMP_VAL_TOTPAGADO),0) totPagado ");
		query.append("from CRT_REVCEDREVVAL_ACLARA ac ");
		query.append("where ac.CVE_PRESENTACORR=" + cvePresentacion+" ");		
		query.append("group by ac.CVE_PRESENTACORR, ac.CVE_PERCEPCION) con, CRC_PERCEPCIONES p ");
		query.append("where con.CVE_PERCEPCION = p.CVE_PERCEPCION ");
		query.append("order by p.TX_REMUNERACION");

		SQLQuery consulta=getSession().createSQLQuery(query.toString());
		//consulta.setParameter("cvePresentacorr", cvePresentacion);
		return consulta.list();
	}


	@Override
	public CrtRevDerivASubd getDerivASubdByClaveSolCorr(Integer cveSolCorr) {
		// TODO Auto-generated method stub
//		
		StringBuilder query=new StringBuilder();
		query.append("FROM CrtRevDerivASubd der where der.solicitudCorr.cveSolicitudCorr=:cveSolicitudCorr");
		Query consulta=getSession().createQuery(query.toString());
		consulta.setParameter("cveSolicitudCorr", cveSolCorr);
		return (CrtRevDerivASubd) consulta.uniqueResult();
	}


	
}
