package mx.gob.imss.cit.cda.service.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.enums.TipoDoctoOrigSolicitanteCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoCdaNssBenRep;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentacionTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentacionTramitePK;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class DocumentoCdaEntity extends AbstractServiceEntity implements DocumentoCdaLocal {
    
    private static Logger logger = LoggerFactory.getLogger(DocumentoCdaEntity.class);

    /**
     * Guarda los documentos de CDA de Asegurado, Beneficiario o Rep Legal
     * @param cveIdTramite
     * @param documentos
     * @param cveIdCorreccionDatosAseg
     * @param cveIdOrigenDocto 
     */
    @Override
    public void guardarDoctoAseg(Long cveIdTramite, DocumentoProbatorio documentoProbatorio,
            Long cveIdCorreccionDatosAseg, Long cveIdOrigenDocto,boolean informacionAdicional) {
        logger.debug("---CDA--- insertando  documentos de Aseg/Beneficiario/Representante [{}]", cveIdOrigenDocto);
        if(documentoProbatorio != null ) {
                      
             
             logger.debug("---CDA--- insertando  IdDocumentoProbatorio [{}]", documentoProbatorio.getIdDocumentoProbatorio());
             logger.debug("---CDA--- insertando  cveIdTramite [{}]", cveIdTramite);
             
             DitDoctoCdaNssBenRep ditDoctoCdaNssBenRep = new DitDoctoCdaNssBenRep();
             ditDoctoCdaNssBenRep.setFecRegistroAlta(new Date());

             ditDoctoCdaNssBenRep.setCveIdDocumentoProbatorio(new Long( documentoProbatorio.getIdDocumentoProbatorio()));
             ditDoctoCdaNssBenRep.setCveIdTramite(cveIdTramite);

             ditDoctoCdaNssBenRep.setCveIDCorreccionDatosAseg(cveIdCorreccionDatosAseg);
             ditDoctoCdaNssBenRep.setCveIdOrigenDocto(cveIdOrigenDocto);
             if(informacionAdicional){
            	 ditDoctoCdaNssBenRep.setIndInformacionAdicional(1l);
             }
             else{
            	 ditDoctoCdaNssBenRep.setIndInformacionAdicional(0l);
             }
             try { 
            	 em.persist(ditDoctoCdaNssBenRep);
             }
             catch(Exception e) {
            	 logger.error(e.getMessage(), e);
             }
        }
    }
    
    /**
     * Guarda los documentos de CDA asociados a un NSS
     * @param cveIdTramite
     * @param documentos
     * @param cveIdCorreccionDatosAseg
     * @param cveIdOrigenDocto
     * @param cveIdDetalleNssCda 
     */
     @Override
    public void guardarDoctoNss(Long cveIdTramite, DocumentoProbatorio documentoProbatorio,
            Long cveIdCorreccionDatosAseg, Long cveIdDetalleNssCda,boolean informacionAdicional) {
        
        logger.debug("---CDA--- insertando  documentos de NSS [{}]", cveIdDetalleNssCda);
        if(documentoProbatorio != null ) {
         
         
             
             logger.debug("---CDA--- insertando  IdDocumentoProbatorio [{}]", documentoProbatorio.getIdDocumentoProbatorio());
             logger.debug("---CDA--- insertando  cveIdTramite [{}]", cveIdTramite);
                          
             DitDoctoCdaNssBenRep ditDoctoCdaNssBenRep = new DitDoctoCdaNssBenRep();
             ditDoctoCdaNssBenRep.setFecRegistroAlta(new Date());

             ditDoctoCdaNssBenRep.setCveIdDocumentoProbatorio(new Long( documentoProbatorio.getIdDocumentoProbatorio()));
             ditDoctoCdaNssBenRep.setCveIdTramite(cveIdTramite);

             ditDoctoCdaNssBenRep.setCveIDCorreccionDatosAseg(cveIdCorreccionDatosAseg);
             ditDoctoCdaNssBenRep.setCveIdOrigenDocto(TipoDoctoOrigSolicitanteCDAEnum.NSS.getClave());
             ditDoctoCdaNssBenRep.setCveIdDetalleNssCda(cveIdDetalleNssCda);
             if(informacionAdicional){
            	 ditDoctoCdaNssBenRep.setIndInformacionAdicional(1l);
             }
             else{
            	 ditDoctoCdaNssBenRep.setIndInformacionAdicional(0l);
             }
             
             

             em.persist(ditDoctoCdaNssBenRep);
         
        }
        
    }
    
    @Override
    public List<DocumentoProbatorio> obtenerDocumentosProbatoriosNss(Long cveIdDetalleNssCda){
        StringBuilder sql = new StringBuilder();

	sql.append(" select dp.CVE_ID_DOCUMENTO_PROBATORIO,  dp.FEC_EXPEDICION, ");
        sql.append(" dp.REF_COD_ENCRIPTADO, dp.CVE_ID_DOCTO_PROB_POR_TIPO, dp.NOM_NOMBRE_DOCUMENTO,");
        sql.append(" dp.REF_BOVEDA_DOC_ID");
        sql.append(" from DIT_DOCTO_CDA_NSS_BEN_REP br ");
        sql.append(" join DIT_DOCUMENTO_PROBATORIO dp ");
        sql.append(" on dp.CVE_ID_DOCUMENTO_PROBATORIO = br.CVE_ID_DOCUMENTO_PROBATORIO    ");
        sql.append(" where br.CVE_ID_DETALLE_NSS_CDA = :cveIdDetalleNssCda ");

        Query query = em.createQuery(sql.toString());
        query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
        
        List<Object[]> rows = query.getResultList();
        List<DocumentoProbatorio> result = new ArrayList<DocumentoProbatorio>(rows.size());
        for (Object[] row : rows) {
            DocumentoProbatorio docto = new DocumentoProbatorio();
            docto.setIdDocumentoProbatorio((Integer)row[0]);
            docto.setFechaExpedicion((Date)row[1]);
            docto.setCifrado((String)row[2]);
            
            DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
            TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
            tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio((Integer)row[3]);
            documentoPorTipo.setTipoDocumentoProbatorio(tipoDocumentoProbatorio);
            docto.setDocumentoPorTipo(documentoPorTipo);
            
            docto.setNomNombreDocumento((String)row[4]);
            docto.setBovedaDocId((String)row[5]);
            result.add(docto);
        }
        
        return result;
       }
    
    public  List<DitDocumentoProbatorio> obtenerDocProbatorioByIdBoveda(String idBoveda){
        StringBuilder sql = new StringBuilder();
        sql.append(" select dp ");
        sql.append(" from DitDocumentoProbatorio dp ");
        sql.append(" where dp.refBovedaDocId = :idBoveda ");

        logger.debug("---CDA--- se busc el idBoveda:" + idBoveda);
        Query query = em.createQuery(sql.toString());
        query.setParameter("idBoveda", idBoveda);
        
        return  (List<DitDocumentoProbatorio>) query.getResultList();
    }
    
    public  DitDoctoCdaNssBenRep obtenerDocProbatorioCda(Long  cveIdDocumentoProbatorio){
        DitDoctoCdaNssBenRep ditDoctoCdaNssBenRep = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" select dp ");
        sql.append(" from DitDoctoCdaNssBenRep dp ");
        sql.append(" where dp.cveIdDocumentoProbatorio = :cveIdDocumentoProbatorio ");
        sql.append(" and dp.fecRegistroBaja is null ");

        logger.debug("---CDA--- se busc el docto cda:" + cveIdDocumentoProbatorio);
        Query query = em.createQuery(sql.toString());
        query.setParameter("cveIdDocumentoProbatorio", cveIdDocumentoProbatorio);
        
        List<DitDoctoCdaNssBenRep> listaDoctos =  query.getResultList();
        if(listaDoctos != null && !listaDoctos.isEmpty()){
            ditDoctoCdaNssBenRep = listaDoctos.get(0);
        }
        return  ditDoctoCdaNssBenRep;
    }
    
    //Pone fecha de baja al documento
     public void eliminarDoctoNss(String idBoveda) {
        
        List<DitDocumentoProbatorio> list = this.obtenerDocProbatorioByIdBoveda(idBoveda);
        
        if(!list.isEmpty()){
            logger.debug("---CDA--- se obtuvieron documentos con ese id");
            DitDocumentoProbatorio docto = list.get(0);
             StringBuilder strUpdate = new StringBuilder(
				"UPDATE DIT_DOCTO_CDA_NSS_BEN_REP br");
            strUpdate.append(" SET br.FEC_REGISTRO_BAJA = sysdate ");
            strUpdate.append(" where br.CVE_ID_DOCUMENTO_PROBATORIO = :idDoctoProbatorio");

            SQLQuery queryUpdate = this.getSession().createSQLQuery(strUpdate.toString());
            queryUpdate.setParameter("idDoctoProbatorio", docto.getCveIdDocumentoProbatorio());
            queryUpdate.executeUpdate();
        }
    }
     
     //Pone fecha de baja al NSS
     public void eliminarNss(String nss) {
         
       logger.debug("---CDA--- se da de baja el nss" +  nss);
       
        
         StringBuilder strUpdate = new StringBuilder(
                            "UPDATE DIT_DETALLE_NSS_CDA cda");
        strUpdate.append(" SET cda.FEC_REGISTRO_BAJA = sysdate ");
        strUpdate.append(" where cda.NUM_NSS= :nss");
        strUpdate.append(" and cda.FEC_REGISTRO_BAJA is null");

        SQLQuery queryUpdate = this.getSession().createSQLQuery(strUpdate.toString());
        queryUpdate.setParameter("nss", nss);
        queryUpdate.executeUpdate();

    }
     
     
	/**
	 * Guarda los documentos de CDA asociados a un NSS
	 * 
	 * @param cveIdTramite
	 * @param documentoProbatorio
	 */
	@Override
	public void guardarDocumentacionTramite(Long cveIdTramite,
			DocumentoProbatorio documentoProbatorio) {

		logger.debug(
				"---CDA--- insertando DocumentacionTramite documentos de NSS [{}]",
				cveIdTramite);
		try {
			if (documentoProbatorio != null) {
	
				logger.debug("---CDA--- insertando  IdDocumentoProbatorio [{}]",
						documentoProbatorio.getIdDocumentoProbatorio());
				logger.debug("---CDA--- insertando  cveIdTramite [{}]",
						cveIdTramite);
				DitDocumentoProbatorio ditDocumentoProbatorio = new DitDocumentoProbatorio();
				DitTramite ditTramite = new DitTramite();
				ditTramite.setCveIdTramite(cveIdTramite);
				DitDocumentacionTramite ditDocumentacionTramite = new DitDocumentacionTramite();
				ditDocumentacionTramite.setDitTramite(ditTramite);
				ditDocumentoProbatorio
						.setCveIdDocumentoProbatorio(documentoProbatorio
								.getIdDocumentoProbatorio().longValue());
				ditDocumentacionTramite
						.setDitDocumentoProbatorio(ditDocumentoProbatorio);
				DitDocumentacionTramitePK ditDocumentacionTramitePK = new DitDocumentacionTramitePK();
				ditDocumentacionTramitePK.setCveIdDocumentoProbatorio(documentoProbatorio.getIdDocumentoProbatorio().longValue());
				ditDocumentacionTramitePK.setCveIdTramite(cveIdTramite);
				ditDocumentacionTramite.setId(ditDocumentacionTramitePK);				
				em.persist(ditDocumentacionTramite);
	
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage(), e);
		}

	}
}
