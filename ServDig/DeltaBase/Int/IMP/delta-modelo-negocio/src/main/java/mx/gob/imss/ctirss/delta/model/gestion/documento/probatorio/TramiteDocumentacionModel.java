package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;




public class TramiteDocumentacionModel implements Serializable{ 

	/**
	 *  
	 */
	private static final long serialVersionUID = 3597653881648842343L;

	private Long cveDoctoReqTramite;
	private Long cveIdTipoTramite;
	private TipoDocumentoProbatorio tipoDocumentoProbatorio;

	

	public TipoDocumentoProbatorio getTipoDocumentoProbatorio() {
		return tipoDocumentoProbatorio;
	}
	public void setTipoDocumentoProbatorio(
			TipoDocumentoProbatorio tipoDocumentoProbatorio) {
		this.tipoDocumentoProbatorio = tipoDocumentoProbatorio;
	}
	

	public Long getCveDoctoReqTramite() {
		return cveDoctoReqTramite;
	}
	public void setCveDoctoReqTramite(Long cveDoctoReqTramite) {
		this.cveDoctoReqTramite = cveDoctoReqTramite;
	}
	public Long getCveIdTipoTramite() {
		return cveIdTipoTramite;
	}
	public void setCveIdTipoTramite(Long cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
	}
	


}

