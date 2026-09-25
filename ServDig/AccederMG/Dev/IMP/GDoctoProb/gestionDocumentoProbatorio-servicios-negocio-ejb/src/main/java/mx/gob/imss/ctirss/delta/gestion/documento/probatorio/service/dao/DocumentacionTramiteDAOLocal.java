package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.dao;



import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitDoctosPersona;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentacionTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;


@Local
public interface DocumentacionTramiteDAOLocal {

	
/**
 * Trae la lista de documentos probatorios para el tramite persona y solicitud
 * @param ditDocumentacionTramite
 * @return
 */
	Long getIdPersona(Long idTramite);
	List<DocumentoProbatorio> findDocumentosProbatorios(Long cveIdTramite);
	void saveDocumentacionTramite(DitDocumentacionTramite ditDocumentacionTramite);
	void saveDocumentacionPersona(DitDoctosPersona ditDoctosPersona);
	void saveDocumentacionTramiteProrroga(DitDocumentacionTramite ditDocumentacionTramite);
	List<DitDocumentoProbatorio> findDitDocumentosProbatorios(Long cveIdTramite) throws Exception; 
	
	List<DocumentoProbatorio> findDocumentosProbatoriosActivos (Long cveIdTramite);
}
