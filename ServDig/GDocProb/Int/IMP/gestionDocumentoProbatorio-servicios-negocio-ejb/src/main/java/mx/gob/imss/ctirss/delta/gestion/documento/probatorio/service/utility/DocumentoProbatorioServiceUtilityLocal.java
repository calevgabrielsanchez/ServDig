package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;

@Local
public interface DocumentoProbatorioServiceUtilityLocal {

	DitDocumentoProbatorio transformarDocumentoProbatorio(
			DocumentoProbatorio modelo) throws TransformacionException;

	DocumentoProbatorio transformarDocumentoProbatorio(
			DitDocumentoProbatorio entity) throws TransformacionException;
}
