package mx.gob.imss.ctirss.delta.model.clasificacion;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class DocumentosAnalisis  extends AbstractModel {

	private static final long serialVersionUID = 1L;
	private Boolean existeClem;
	private Boolean existeAviso;
	private Boolean existeTip;
	private Boolean existeArp;
	private Long cveIdClem;
	private Long cveIdSolicitud;
	private Long cveIdDocumentoProbatorioTip;
	private Long cveIdDocumentoProbatorioArp;
	
	private boolean boIndFirma = Boolean.FALSE;
	private String urlClemFirma = null;

	
	public DocumentosAnalisis() {
		this.existeClem = false;
		this.existeAviso = false;
		this.existeTip = false;
		this.existeArp = false;
	}
	
	public Boolean getExisteClem() {
		return existeClem;
	}
	public void setExisteClem(Boolean existeClem) {
		this.existeClem = existeClem;
	}
	public Boolean getExisteAviso() {
		return existeAviso;
	}
	public void setExisteAviso(Boolean existeAviso) {
		this.existeAviso = existeAviso;
	}
	public Boolean getExisteTip() {
		return existeTip;
	}
	public void setExisteTip(Boolean existeTip) {
		this.existeTip = existeTip;
	}
	public Boolean getExisteArp() {
		return existeArp;
	}
	public void setExisteArp(Boolean existeArp) {
		this.existeArp = existeArp;
	}
	public Long getCveIdClem() {
		return cveIdClem;
	}
	public void setCveIdClem(Long cveIdClem) {
		this.cveIdClem = cveIdClem;
	}
	public Long getCveIdSolicitud() {
		return cveIdSolicitud;
	}
	public void setCveIdSolicitud(Long cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}
	public Long getCveIdDocumentoProbatorioTip() {
		return cveIdDocumentoProbatorioTip;
	}
	public void setCveIdDocumentoProbatorioTip(Long cveIdDocumentoProbatorioTip) {
		this.cveIdDocumentoProbatorioTip = cveIdDocumentoProbatorioTip;
	}
	public Long getCveIdDocumentoProbatorioArp() {
		return cveIdDocumentoProbatorioArp;
	}
	public void setCveIdDocumentoProbatorioArp(Long cveIdDocumentoProbatorioArp) {
		this.cveIdDocumentoProbatorioArp = cveIdDocumentoProbatorioArp;
	}
	public boolean isBoIndFirma() {
		return boIndFirma;
	}

	public void setBoIndFirma(boolean boIndFirma) {
		this.boIndFirma = boIndFirma;
	}

	public String getUrlClemFirma() {
		return urlClemFirma;
	}

	public void setUrlClemFirma(String urlClemFirma) {
		this.urlClemFirma = urlClemFirma;
	}

	@Override
	public String toString() {
		return "DocumentosAnalisis [existeClem=" + existeClem
				+ ", existeAviso=" + existeAviso + ", existeTip=" + existeTip
				+ ", existeArp=" + existeArp + ", cveIdClem=" + cveIdClem
				+ ", cveIdSolicitud=" + cveIdSolicitud
				+ ", cveIdDocumentoProbatorioTip="
				+ cveIdDocumentoProbatorioTip
				+ ", cveIdDocumentoProbatorioArp="
				+ cveIdDocumentoProbatorioArp + ", boIndFirma=" + boIndFirma
				+ ", urlClemFirma=" + urlClemFirma + "]";
	}

}
