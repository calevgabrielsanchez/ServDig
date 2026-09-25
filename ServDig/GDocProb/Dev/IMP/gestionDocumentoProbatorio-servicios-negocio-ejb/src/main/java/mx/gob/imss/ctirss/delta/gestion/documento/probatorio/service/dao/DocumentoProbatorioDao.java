package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.dao;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.DocumentoProbatorioParser;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
@Stateless(name = "documentoProbatorioDao", mappedName = "documentoProbatorioDao")
public class DocumentoProbatorioDao extends AbstractServiceEntity implements DocumentoProbatorioDaoLocal {
	
	private static final Long WATERMARK=new Long(0);
	
	
	@Override
	public DitDocumentoProbatorio findWaterMark() {
		
		Criteria query = this.getSession().createCriteria(DitDocumentoProbatorio.class);
		query.add(Restrictions.eq("cveIdDocumentoProbatorio", WATERMARK));
		query.add(Restrictions.isNull("fecRegistroBaja"));
		
		DitDocumentoProbatorio ditDocumentoProbatorio = (DitDocumentoProbatorio) query.uniqueResult();
		return ditDocumentoProbatorio;
	}
	
	@Override 
	public DocumentoProbatorio saveDocumentoProbatorio(DocumentoProbatorio documento){
		DitDocumentoProbatorio ditDocumento = DocumentoProbatorioParser.modelToPersist(documento);
		em.persist(ditDocumento);
		em.flush();
		documento.setIdDocumentoProbatorio(new Integer(""+ditDocumento.getCveIdDocumentoProbatorio()));
		return documento;
	}
	
	@Override 
	public void updateDocumentoProbatorio(Long idTramite, byte [] arreglo){
		
		DitTramite tramite=	em.find(DitTramite.class, idTramite);
		DitDocumentoProbatorio documentoProbatorio = tramite.getDitDocumentoProbatorios().get(0);
		documentoProbatorio.setRefDocumentoDigitalizado(arreglo);
		em.merge(documentoProbatorio);
		/*DitDocumentoProbatorio ditDocumento = em.find(DitDocumentoProbatorio.class,idDocumento);
		
		ditDocumento.setRefDocumentoDigitalizado(arreglo);
		em.merge(ditDocumento);
		*/
	}
	
	@Override 
	public void updateDocumentoProbatorio(DitDocumentoProbatorio documento) {
		em.merge(documento);
	}
	
	@Override
	public Object saveDocumentoCapturado(Object obj){
		em.persist(obj);
		return obj;
	}
	
	@Override
	public DocumentoProbatorio getDocumentoProvatorio(Long id) throws DocumentoProbatorioException{
		DocumentoProbatorio documentoProbatorio=null;
		documentoProbatorio=DocumentoProbatorioParser.PersistToModel(this.getDocumentoProvatorioPersistencia(id));
		return documentoProbatorio;
	}
	@Override
	public DocumentoProbatorio getDocumentoProvatorioBytes(Long id){
		DocumentoProbatorio documentoProbatorio=null;
		documentoProbatorio=DocumentoProbatorioParser.PersistToModelBytesOnly(this.getDocumentoProvatorioPersistencia(id));
		return documentoProbatorio;
	}
	
	@Override
	public void eliminarDocumentoProbatorio(DitDocumentoProbatorio documento) {
		Session session = this.getSession();

		session.delete(documento);
		session.flush();
	}
	
	private DitDocumentoProbatorio getDocumentoProvatorioPersistencia(Long id){
		return this.em.find(DitDocumentoProbatorio.class, id);
	}
	

}
