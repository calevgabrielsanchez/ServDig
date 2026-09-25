package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;


import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoPorTipo;
@Local
public interface DocumentoPorTipoDaoLocal {

	List<DitDocumentoPorTipo> finddocumentosPorTipoTramite(
			Long cvIdTipoDocumentoProbatorio) throws Exception;
	DocumentoPorTipo getDocumentosPorTipo(Long idDocumento,Long idTipoDocumentoProbatorio) throws DocumentoProbatorioException,Exception;


}