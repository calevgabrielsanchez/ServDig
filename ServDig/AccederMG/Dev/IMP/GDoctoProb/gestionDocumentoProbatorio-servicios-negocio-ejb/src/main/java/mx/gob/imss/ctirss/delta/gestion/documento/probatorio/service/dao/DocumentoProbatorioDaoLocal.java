package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.dao;




import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;



@Local
public interface DocumentoProbatorioDaoLocal {

	
/**
 * Trae la marca de agua
 * @return
 */
	DitDocumentoProbatorio findWaterMark();
	DocumentoProbatorio saveDocumentoProbatorio(DocumentoProbatorio documento);
	void updateDocumentoProbatorio(Long idTramite,byte [] arreglo);
	Object saveDocumentoCapturado(Object obj);
	DocumentoProbatorio getDocumentoProvatorio(Long id) throws DocumentoProbatorioException;
	DocumentoProbatorio getDocumentoProvatorioBytes(Long idDocProvatorio);
	
	void eliminarDocumentoProbatorio(DitDocumentoProbatorio documento);
	
	/**
	 * Actualiza los datos de un documento probatorio
	 * @param documento
	 */
	void updateDocumentoProbatorio(DitDocumentoProbatorio documento);
}
