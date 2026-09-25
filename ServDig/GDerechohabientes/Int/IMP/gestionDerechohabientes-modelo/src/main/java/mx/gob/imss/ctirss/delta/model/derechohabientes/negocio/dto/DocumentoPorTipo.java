package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;


public class DocumentoPorTipo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7679617085570835897L;
	private TipoDocumentoProbatorio tipoDocumentoProbatorio;
	private Documento documento;
	private Long idDocumentoPorTipo;
	public TipoDocumentoProbatorio getTipoDocumentoProbatorio() {
		return tipoDocumentoProbatorio;
	}
	public void setTipoDocumentoProbatorio(
			TipoDocumentoProbatorio tipoDocumentoProbatorio) {
		this.tipoDocumentoProbatorio = tipoDocumentoProbatorio;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public Long getIdDocumentoPorTipo() {
		return idDocumentoPorTipo;
	}
	public void setIdDocumentoPorTipo(Long idDocumentoPorTipo) {
		this.idDocumentoPorTipo = idDocumentoPorTipo;
	}

	
}
