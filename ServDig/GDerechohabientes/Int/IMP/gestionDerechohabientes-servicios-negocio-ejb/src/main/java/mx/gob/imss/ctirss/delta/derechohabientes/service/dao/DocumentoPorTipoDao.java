package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.DocumentoPorTipoParser;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoPorTipo;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


@Stateless(name = "DocumentoPorTipoDao", mappedName = "DocumentoPorTipoDao")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DocumentoPorTipoDao extends AbstractServiceEntity implements DocumentoPorTipoDaoLocal {
	
	private static final Logger logger = Logger.getLogger(DocumentoPorTipoDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DitDocumentoPorTipo> finddocumentosPorTipoTramite(Long cvIdTipoDocumentoProbatorio) throws Exception{
		List<DitDocumentoPorTipo> documentosPorTipo=null;
		try {
			Criteria queryDocumento = this.getSession().createCriteria(DitDocumentoPorTipo.class);
			queryDocumento.createAlias("dicTipoDocumentoProbatorio", "tipo");
			queryDocumento.add(Restrictions.eq("tipo.cveIdTipoDocumentoProbator", cvIdTipoDocumentoProbatorio));
			queryDocumento.createAlias("dicDocumento", "docto");
			queryDocumento.add(Restrictions.isNull("docto.fecRegistroBaja"));
			
			documentosPorTipo=queryDocumento.list();
			/*CriteriaBuilder cb = em.getCriteriaBuilder(); //Step 1 
			CriteriaQuery<DitDocumentoPorTipo> cqry= cb.createQuery(DitDocumentoPorTipo.class);  //Aqui se pone que tipo esperamos recivir
			Root<DitDocumentoPorTipo> root = cqry.from(DitDocumentoPorTipo.class); //Step 2 //se crea el from
			
			cqry.select(root);
			Predicate conjunction = cb.conjunction();//se crea unoa conjuntion para poder hacer and
			conjunction.getExpressions().add(cb.equal(root.get("dicTipoDocumentoProbatorio").get("cveIdTipoDocumentoProbator").as(Integer.class),cvIdTipoDocumentoProbatorio));
			//fecha baja
			conjunction.getExpressions().add(cb.isNull(root.get("dicDocumento").get("fecRegistroBaja")));	
			cqry.where(conjunction); //Step 5 se agrega el predicado
			 
			 documentosPorTipo=em.createQuery(cqry).getResultList();*/
		} catch (Exception e) {
			logger.error("Error - finddocumentosPorTipoTramite", e);
			throw e;
		}
		
		 return documentosPorTipo;
	}
	
	@Override
	public DocumentoPorTipo getDocumentosPorTipo(Long idDocumento,Long idTipoDocumentoProbatorio) throws DocumentoProbatorioException,Exception {
		DitDocumentoPorTipo documento=null;
		DocumentoPorTipo documentoPorTipo=null;
		try {
			Criteria queryDocumento = this.getSession().createCriteria(DitDocumentoPorTipo.class);
			queryDocumento.createAlias("dicTipoDocumentoProbatorio", "tipo");
			queryDocumento.createAlias("dicDocumento", "docto");
			queryDocumento.add(Restrictions.eq("tipo.cveIdTipoDocumentoProbator", idTipoDocumentoProbatorio));
			queryDocumento.add(Restrictions.eq("docto.cveIdDocumento", idDocumento));
			queryDocumento.add(Restrictions.isNull("docto.fecRegistroBaja"));
			
			documento = (DitDocumentoPorTipo) queryDocumento.uniqueResult();
			
			
			/*CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitDocumentoPorTipo> cqry= cb.createQuery(DitDocumentoPorTipo.class);
			Root<DitDocumentoPorTipo> root = cqry.from(DitDocumentoPorTipo.class);		
			cqry.select(root);
		       
			Predicate conjunction = cb.conjunction();

			conjunction.getExpressions().add(cb.equal(
					root.get("dicTipoDocumentoProbatorio").get("cveIdTipoDocumentoProbator").as(Integer.class),idTipoDocumentoProbatorio));
			conjunction.getExpressions().add(cb.equal(
					root.get("dicDocumento").get("cveIdDocumento").as(Integer.class),idDocumento));
			
			conjunction.getExpressions().add(cb.isNull(root.get("dicDocumento").get("fecRegistroBaja")));	
			cqry.where(conjunction); 
			 
			documento=em.createQuery(cqry).getSingleResult();*/
		} catch (Exception e) {
			logger.error("Error - getDocumentosPorTipo", e);
			throw e;
		}
		
		
		documentoPorTipo= DocumentoPorTipoParser.persisToModel(documento);
		return documentoPorTipo;
	}

	
	
	

}
