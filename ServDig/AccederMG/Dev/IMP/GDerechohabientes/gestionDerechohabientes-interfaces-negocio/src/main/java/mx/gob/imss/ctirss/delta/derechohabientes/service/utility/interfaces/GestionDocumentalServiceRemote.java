package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DetalleNivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoNivelEducativo;



@Remote
public interface GestionDocumentalServiceRemote {
	
	List<DoctoReqTramite> listaDocumentosTramite(Long cveIdTramite) throws DerechohabientesBusinessException, Exception ;
	void procesaDocumentos(Long idTramite, List<DocumentoProbatorio> documentoProbatorio) throws DerechohabientesBusinessException, Exception;
	List<DocumentoProbatorio> listaDocumentosProbatoriosTramite(Long cveIdTramite);
	/**
	 * Obtiene todos los medicos
	 * @return
	 */
	List<MedicoFamiliar> getAllMedicos() throws DerechohabientesBusinessException, Exception ;
	List<TipoNivelEducativo> getAllTipoNivelEducativos() throws DerechohabientesBusinessException, Exception ;
	DetalleNivelEducativo getDetalleNivelEducativo(Long idTipoNivel,
			Long idNivel)throws DerechohabientesBusinessException, Exception ;
	/**
	 * Obtiene el documentoProbatorio
	 * @param idDocumentoProbatorio
	 * @return
	 * @throws DerechohabientesBusinessException 
	 * @throws Exception 
	 */
	DocumentoProbatorio getDocumentoProbatorio(Long idDocumentoProbatorio) throws DerechohabientesBusinessException, Exception;
	/**
	 * Obtiene los bytes del documentoProbatorio
	 * @param idDocProvatorio
	 * @return
	 */
	DocumentoProbatorio getDocumentoProbatorioBytes(Long idDocProvatorio);


		
	

}