package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.dao;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.DocumentoProbatorioParser;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitDoctosPersona;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentacionTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


@Stateless(name = "DocumentacionTramiteDAO", mappedName = "DocumentacionTramiteDAO")
public class DocumentacionTramiteDAO extends AbstractServiceEntity implements DocumentacionTramiteDAOLocal {
	
	
	@Override
	public Long getIdPersona(Long idTramite) {
		
		Long res = null;
		
		Criteria queryTF = this.getSession().createCriteria(DitTramitePersonaFisica.class);
		queryTF.createAlias("ditPersona", "persona");
		queryTF.setProjection(Projections.property("persona.cveIdPersona"));
		Criteria queryTramite = queryTF.createAlias("ditTramite", "tramite");
		queryTramite.add(Restrictions.eq("tramite.cveIdTramite", idTramite));
		
		try {
			res = (Long)queryTF.uniqueResult();
			return res;
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public void saveDocumentacionTramite(DitDocumentacionTramite ditDocumentacionTramite){
		em.persist(ditDocumentacionTramite);
		em.flush();
	}
	
	
	@Override
	public void saveDocumentacionPersona(DitDoctosPersona ditDoctosPersona) {
		em.persist(ditDoctosPersona);
		em.flush();
	}

	@Override
	public void saveDocumentacionTramiteProrroga(DitDocumentacionTramite ditDocumentacionTramite){
		DitDocumentoProbatorio documentoProbatorio=ditDocumentacionTramite.getDitDocumentoProbatorio();
		ditDocumentacionTramite.getId().setCveIdDocumentoProbatorio(documentoProbatorio.getCveIdDocumentoProbatorio());
		em.persist(ditDocumentacionTramite);
		
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoProbatorio> findDocumentosProbatorios (
			Long cveIdTramite) {
		List<DitDocumentoProbatorio> ditDocumentoProbatorios=null;
		List<DocumentoProbatorio> documentoProbatorios=null;
		
		Criteria query = this.getSession().createCriteria(DitDocumentacionTramite.class);
		query.setProjection(Projections.property("ditDocumentoProbatorio"));
		
		query.createAlias("ditTramite", "tramite").add(Restrictions.eq("tramite.cveIdTramite", cveIdTramite));
		query.createAlias("ditDocumentoProbatorio", "documento").add(Restrictions.isNull("documento.fecRegistroBaja"));
		
		ditDocumentoProbatorios = query.list();
		documentoProbatorios = DocumentoProbatorioParser.PersistToModelDocumentoProbatorioOnlyList(ditDocumentoProbatorios);
				
		return documentoProbatorios;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoProbatorio> findDocumentosProbatoriosActivos (Long cveIdTramite) {		
		Criteria query = this.getSession().createCriteria(DitDocumentacionTramite.class);
		query.setProjection(Projections.property("ditDocumentoProbatorio"));
		
		query.createAlias("ditTramite", "tramite")
			.add(Restrictions.eq("tramite.cveIdTramite", cveIdTramite));
		
		query.createAlias("ditDocumentoProbatorio", "documento")
			.add(Restrictions.isNull("documento.fecRegistroBaja"));
		
		return DocumentoProbatorioParser.PersistToModelDocumentoProbatorioOnlyList(query.list());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DitDocumentoProbatorio> findDitDocumentosProbatorios(
			Long cveIdTramite) throws Exception {
		List<DitDocumentoProbatorio> ditDocumentoProbatorios=null;
		
		try {
			
			Criteria queryDocs = this.getSession().createCriteria(DitDocumentacionTramite.class);
			queryDocs.setProjection(Projections.property("ditDocumentoProbatorio"));
			queryDocs.createAlias("ditTramite", "tramite");
			queryDocs.add(Restrictions.eq("tramite.cveIdTramite", cveIdTramite));
			
			ditDocumentoProbatorios = queryDocs.list();
		} catch (Exception e) {		
			throw e;
		}
		
		
		return ditDocumentoProbatorios;
	}

}
