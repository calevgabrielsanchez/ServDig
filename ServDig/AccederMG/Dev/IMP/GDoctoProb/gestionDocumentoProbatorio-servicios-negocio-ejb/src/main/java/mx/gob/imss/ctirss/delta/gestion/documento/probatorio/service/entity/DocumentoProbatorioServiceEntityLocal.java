package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramiteOrigenSol;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

@Local
public interface DocumentoProbatorioServiceEntityLocal {

	Long getNumeroDocumentosProbatoriosActivos(Long idTipoTramite, Boolean obligatorio);
	/**
	 * Servicio para registrar una lista de documentos probatorios
	 * 
	 * @param lista
	 *            de documentos a guardar
	 * @return lista de docuemntos recibidos
	 */
	List<DocumentoProbatorio> registrarDocumentos(
			List<DocumentoProbatorio> documentos);

	/**
	 * Servicio para consultar los documentos probatorios de una persona
	 * 
	 * @param persona
	 *            de la que se desean obtener sus documentos probatorios
	 * @return lista de documentos probatorios de la persona
	 */
	List<DocumentoProbatorio> consultarDocumentosDePersona(
			Persona persona);

	/**
	 * M�todo que asocia el documento probatorio a la persona
	 * 
	 * @param documento
	 * @param cvePersona
	 * @throws DocumentoProbatorioException
	 */
	void asociarDocumentoAPersona(DocumentoProbatorio documento, Long cvePersona)
			throws DocumentoProbatorioException;
	
	/**
	 * M�todo que actualiza un documento probatorio
	 * 
	 * @param documentoProbatorio
	 * @return
	 * @throws DerechohabientesBusinessException 
	 */
	void modificarDocumentoProbatorio(
			DocumentoProbatorio documentoProbatorio) throws DocumentoProbatorioException;

	/**
	 * M�todo que elimina un documento probatorio y lo desasocia de la persona
	 * 
	 * @param documentoProbatorio
	 * @param cvePersona
	 */
	void eliminarDesasociarDocumentoProbatorioPersona(
			DocumentoProbatorio documentoProbatorio, Long cvePersona);
	
	List<DoctoReqTramite> obtenerListaDeDocumentos(Long cveIdTipoTramite, Boolean resultantes);
	List<Documento> obtenerListaDeDocumentos(List<Long> tiposTramite, Boolean resultantes);
	List<DocumentoPorTipo> obtenerDocumentosPorTipoByTipoTramite(List<Long> tiposTramite);
	List<TipoDocumentoProbatorio> getTiposDocumentoProbatorios();
	
	void eliminarDocumentoProbatorio(DocumentoProbatorio documentoProbatorio);
	
	/**
	 * Consulta los documentos por tipo tramite y origen filtrando o no los resultantes
	 * @param cveIdTipoTramite
	 * @param resultantes
	 * @param cveIdOrigenSolicitud
	 * @return
	 * @throws DocumentoProbatorioException
	 */
	List<DoctoReqTramiteOrigenSol> getDocumentosReqTramiteOrigenSol(
			Long cveIdTipoTramite, Boolean resultantes, Long cveIdOrigenSolicitud) throws DocumentoProbatorioException;
}
