package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;

public class DoctoReqTramiteOrigenSol  extends DoctoReqTramite implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1132791220588645156L;
	
	private Long cveIdDoctoReqTramOrgSol;
	private OrigenSolicitud origenSolicitud;
	private String refDetalleDoctoRequerido;
	private Long ordenListaDoc;
	private Long cveIdTipoPersona;
	private Long cveTipoArea;
	
	public Long getCveIdTipoPersona() {
		return cveIdTipoPersona;
	}
	public void setCveIdTipoPersona(Long cveIdTipoPersona) {
		this.cveIdTipoPersona = cveIdTipoPersona;
	}
	public Long getCveIdDoctoReqTramOrgSol() {
		return cveIdDoctoReqTramOrgSol;
	}
	public void setCveIdDoctoReqTramOrgSol(Long cveIdDoctoReqTramOrgSol) {
		this.cveIdDoctoReqTramOrgSol = cveIdDoctoReqTramOrgSol;
	}
	public OrigenSolicitud getOrigenSolicitud() {
		return origenSolicitud;
	}
	public void setOrigenSolicitud(OrigenSolicitud origenSolicitud) {
		this.origenSolicitud = origenSolicitud;
	}
	public String getRefDetalleDoctoRequerido() {
		return refDetalleDoctoRequerido;
	}
	public void setRefDetalleDoctoRequerido(String refDetalleDoctoRequerido) {
		this.refDetalleDoctoRequerido = refDetalleDoctoRequerido;
	}
	public Long getOrdenListaDoc() {
		return ordenListaDoc;
	}
	public void setOrdenListaDoc(Long ordenListaDoc) {
		this.ordenListaDoc = ordenListaDoc;
	}
	public Long getCveTipoArea() {
		return cveTipoArea;
	}
	public void setCveTipoArea(Long cveTipoArea) {
		this.cveTipoArea = cveTipoArea;
	}
	

}
