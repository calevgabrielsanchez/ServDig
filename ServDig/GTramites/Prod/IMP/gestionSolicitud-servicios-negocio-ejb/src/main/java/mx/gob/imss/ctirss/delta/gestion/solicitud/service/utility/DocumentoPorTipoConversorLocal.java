package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoPorTipo;

@Local
public interface DocumentoPorTipoConversorLocal {
	
	DocumentoPorTipo convertirEntityToModel(DitDocumentoPorTipo documento);
	List<DocumentoPorTipo> convertirEntityToModelList(List<DitDocumentoPorTipo> lista);
	
}
