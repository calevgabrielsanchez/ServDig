package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.dto.DatosBoveda;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;

@Local
public interface BovedaServiceUtilLocal {
	
	DocumentoProbatorio guardarDocumentoBoveda(DocumentoProbatorio documento, DatosBoveda datosBoveda)  throws DocumentoProbatorioException;
	DocumentoProbatorio getDocumento(String idDocumento, DatosBoveda datosBoveda) throws DocumentoProbatorioException;
	void eliminarDocumentoBoveda(DocumentoProbatorio documento) throws DocumentoProbatorioException;
}
