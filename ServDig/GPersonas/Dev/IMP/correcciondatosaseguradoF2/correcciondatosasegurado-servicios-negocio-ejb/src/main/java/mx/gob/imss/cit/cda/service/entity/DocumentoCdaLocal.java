package mx.gob.imss.cit.cda.service.entity;


import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoCdaNssBenRep;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;


@Local
public interface DocumentoCdaLocal {
    
     void guardarDoctoAseg(Long cveIdTramite, DocumentoProbatorio documentoProbatorio,
            Long cveIdCorreccionDatosAseg, Long cveIdOrigenDocto,boolean informacionAdicional);
     
     void guardarDoctoNss(Long cveIdTramite, DocumentoProbatorio documentoProbatorio,
            Long cveIdCorreccionDatosAseg, Long cveIdDetalleNssCda,boolean informacionAdicional);
     
     List<DocumentoProbatorio> obtenerDocumentosProbatoriosNss(Long cveIdDetalleNssCda);
     
     void eliminarDoctoNss(String idBoveda);
     void eliminarNss(String nss);
     
     List<DitDocumentoProbatorio> obtenerDocProbatorioByIdBoveda(String idBoveda);
     
     DitDoctoCdaNssBenRep obtenerDocProbatorioCda(Long  cveIdDoctoCdaAseBenRep);
     
     void guardarDocumentacionTramite(Long cveIdTramite, DocumentoProbatorio documentoProbatorio);
}
