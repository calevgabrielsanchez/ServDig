package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;



public class DoctoReqTramite implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7001593329074925967L;
	private Long cveIdDoctoReqTramite;
	private TipoTramite tipoTramite;
	private DocumentoPorTipo documentoPorTipo;
	private Integer refCapturaDocumentoObligatorio;
	private Integer refDocumentoOpcional;
	private Integer refCargaDocumentoObigatorio;

	
/**
	 * @return the cveIdDoctoReqTramite
	 */
	public Long getCveIdDoctoReqTramite() {
		return cveIdDoctoReqTramite;
	}

	/**
	 * @param cveIdDoctoReqTramite the cveIdDoctoReqTramite to set
	 */
	public void setCveIdDoctoReqTramite(Long cveIdDoctoReqTramite) {
		this.cveIdDoctoReqTramite = cveIdDoctoReqTramite;
	}

	/**
	 * @return the tipoTramite
	 */
	public TipoTramite getTipoTramite() {
		return tipoTramite;
	}

	/**
	 * @param tipoTramite the tipoTramite to set
	 */
	public void setTipoTramite(TipoTramite tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	/**
	 * @return the documentoPorTipo
	 */
	public DocumentoPorTipo getDocumentoPorTipo() {
		return documentoPorTipo;
	}

	/**
	 * @param documentoPorTipo the documentoPorTipo to set
	 */
	public void setDocumentoPorTipo(DocumentoPorTipo documentoPorTipo) {
		this.documentoPorTipo = documentoPorTipo;
	}

	/**
	 * @return the refCapturaDocumentoObligatorio
	 */
	public Integer getRefCapturaDocumentoObligatorio() {
		return refCapturaDocumentoObligatorio;
	}

	/**
	 * @param refCapturaDocumentoObligatorio the refCapturaDocumentoObligatorio to set
	 */
	public void setRefCapturaDocumentoObligatorio(
			Integer refCapturaDocumentoObligatorio) {
		this.refCapturaDocumentoObligatorio = refCapturaDocumentoObligatorio;
	}

	/**
	 * @return the refDocumentoOpcional
	 */
	public Integer getRefDocumentoOpcional() {
		return refDocumentoOpcional;
	}

	/**
	 * @param refDocumentoOpcional the refDocumentoOpcional to set
	 */
	public void setRefDocumentoOpcional(Integer refDocumentoOpcional) {
		this.refDocumentoOpcional = refDocumentoOpcional;
	}

	/**
	 * @return the refCargaDocumentoObigatorio
	 */
	public Integer getRefCargaDocumentoObigatorio() {
		return refCargaDocumentoObigatorio;
	}

	/**
	 * @param refCargaDocumentoObigatorio the refCargaDocumentoObigatorio to set
	 */
	public void setRefCargaDocumentoObigatorio(Integer refCargaDocumentoObigatorio) {
		this.refCargaDocumentoObigatorio = refCargaDocumentoObigatorio;
	}

	

}
