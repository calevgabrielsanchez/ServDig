package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.PersonaSinDocumentosException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.dto.DatosBoveda;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DetalleNivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramiteOrigenSol;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoNivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface DocumentoProbatorioServiceBusinessRemote {
	
	Boolean requiereDocumentos(Long idTipoTramite);
	List<MedicoFamiliar> getAllMedicos() throws Exception ;
	List<TipoNivelEducativo> getAllTipoNivelEducativos() throws Exception ;
	DetalleNivelEducativo getDetalleNivelEducativo(Long idTipoNivel,Long idNivel)throws Exception;
	List<MedicoEnTurno> medicoEnTurnos(Long idUmf) throws Exception;
	/**
	 * Servicio para registrar una lista de documentos probatorios
	 * 
	 * @param lista
	 *            de documentos a guardar
	 * @return lista de docuemntos recibidos
	 * @throws RegistrarDocumentoProbatorioException
	 */
	List<DocumentoProbatorio> registrarDocumentos(
			List<DocumentoProbatorio> documentos)
			throws RegistrarDocumentoProbatorioException;

	void asociaDocumentosPersona(Long idPersona, Long idTramite);

	/**
	 * Servicio para consultar los documentos probatorios de una persona
	 * 
	 * @param persona
	 *            de la que se desean obtener sus documentos probatorios
	 * @return lista de documentos probatorios de la persona
	 * @throws PersonaSinDocumentosException
	 */
	List<DocumentoProbatorio> consultarDocumentosDePersona (
			Persona persona) throws PersonaSinDocumentosException;

	/**
	 * Guarda el documento probeatorio relacionado a su tramite
	 * 
	 * @param idTramite
	 * @param documentoProbatorio
	 */
	void procesaDocumentos(Long idTramite,
			List<DocumentoProbatorio> documentoProbatorio)
			throws DocumentoProbatorioException;

	/**
	 * Guarda el documento probeatorio relacionado a su tramite y persona
	 * 
	 * @param idTramite
	 * @param idPersona
	 * @param documentoProbatorio
	 */
	void procesaDocumentosGD(Long idTramite, Long idPersona,
			List<DocumentoProbatorio> documentoProbatorio)
			throws DocumentoProbatorioException;

	/**
	 * Lista los documentos probatorios del Tramite Encapsulando su Documento
	 * correspondiente de tenerlo Valida que el documento digitalizado sea el
	 * original de lo contrario lo regresa en null junto con su cifrado
	 * 
	 * @param cveIdTramite
	 * @return
	 */
	List<DocumentoProbatorio> listaDocumentosProbatoriosTramite(
			Long cveIdTramite);
	
	/**
	 * Lista los documentos probatorios para un tramite, seleccionado los documentos 
	 * que no tienen una fecha de baja
	 * @param cveIdTramite
	 * @return
	 */
	List<DocumentoProbatorio> listaDocumentosProbatoriosActivosTramite(Long cveIdTramite);

	/**
	 * Trae el documento probatorio Encapsulando su Documento correspondiente de
	 * tenerlo Valida que el documento digitalizado sea el original de lo
	 * contrario lo regresa en null junto con su cifrado
	 * 
	 * @param idDocProvatorio
	 * @return
	 */
	DocumentoProbatorio getDocumentoProbatorio(Long idDocProvatorio)
			throws DocumentoProbatorioException;

	/**
	 * Trae solo la Digitalizacion del documento Probatorio Valida que el
	 * documento digitalizado sea el original de lo contrario lo regresa en null
	 * junto con su cifrado
	 * 
	 * @param idDocProvatorio
	 * @return
	 */
	DocumentoProbatorio getDocumentoProbatorioBytes(Long idDocProvatorio);

	DocumentoProbatorio registraDocumento(DocumentoProbatorio dP)
			throws DocumentoProbatorioException;

	/**
	 * Servicio que guarda y asocia un documento probatorio a una persona
	 * 
	 * @param documento
	 * @param cvePersona
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	DocumentoProbatorio registrarAsociarDocumentoProbatorioPersona(
			DocumentoProbatorio documento, Long cvePersona)
			throws DocumentoProbatorioException;

	/**
	 * Servicio que actualiza un documento probatorio
	 * 
	 * @param documentoProbatorio
	 * @return
	 * @throws DerechohabientesBusinessException 
	 */
	void modificarDocumentoProbatorio(
			DocumentoProbatorio documentoProbatorio) throws DocumentoProbatorioException;

	/**
	 * Servicio que elimina un documento probatorio y lo desasocia de la persona
	 * 
	 * @param documentoProbatorio
	 * @param cvePersona
	 */
	void eliminarDesasociarDocumentoProbatorioPersona(
			DocumentoProbatorio documentoProbatorio, Long cvePersona);
	
	List<DoctoReqTramite> getDocumentosRequeridosPorTipoTramite(Long cveIdTipoTramite);
	List<Documento> getDocumentosPorTramites(List<Long> idTiposTramite);
	List<Map<String,Object>> getDocumentosClasificadosPorTipoTramite(List<Long> idTiposTramite);
	void guardarDocumentosCapturados(Long idSolicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, DocumentoProbatorioException;
	void guardarDocumentosCapturados(Solicitud solicitud) throws TramiteNoEncontradoException, DocumentoProbatorioException;
	
	Long getIdPersonaTramite(Long idTramite);
	
	/**
	 * Realiza un borrado lógico de los documentos asociados a un trámite (El borrado lógico consiste en actualizar la fecha de baja de un documento para que la aplicación lo omita 
	 * cada que se leen los documentos asociados a un trámite)
	 * @param cveIdTramite
	 */
	void eliminarDocumentosTramite(Long cveIdTramite) throws TramiteNoEncontradoException, DocumentoProbatorioException;
	
	/**
	 * Realiza un borrado lógico de un documento
	 * @param documentoProbatorio
	 */
	void eliminarDocumentoProbatorio(DocumentoProbatorio documentoProbatorio);
	
	/**
	 * Realiza un borrado lógico de un documento EN bOVEDA Y bd
	 * @param documentoProbatorio
	 */
	void eliminarDocumentoProbatorioBoveda(DocumentoProbatorio documentoProbatorio)  throws DocumentoProbatorioException;
	
	DocumentoProbatorio guardarDocumentoProbatorioBoveda(DatosBoveda datosBoveda, DocumentoProbatorio documentoProbatorio) throws DocumentoProbatorioException;
	
	DocumentoProbatorio getDocumentoBoveda(String idDocumento, DatosBoveda datosBoveda) throws DocumentoProbatorioException;
	
	/**
	 * Metodo que consulta los documetos requerisos para un tramite basado en su origen
	 * @param cveIdTipoTramite
	 * @param cveIdOrigenSolicituid
	 * @return
	 * @throws DocumentoProbatorioException
	 */
	List<DoctoReqTramiteOrigenSol> getDocumentosRequeridosPorTipoTramiteOrigenSol(
			Long cveIdTipoTramite, Long cveIdOrigenSolicituid)throws DocumentoProbatorioException;

}
