package mx.gob.imss.ctirss.cliente.clasificacion.dto;

import java.io.Serializable;

public class RequestMacII implements Serializable {

	private static final long serialVersionUID = 7858531363074626258L;

	private String registroPatronal;
	private String fecIniRegistro;
	private String fecFinRegistro;
	private String cveIdTipoPersona;
	private String claseRectificada;
	private String tipoRegistro;
	private String cveIdEstatus;
	private String cveIdDelegacion;
	private String cveIdSubdelegacion;
	private String tipoMovimiento;
	private String cveIdTipoTramite;

	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public String getFecIniRegistro() {
		return fecIniRegistro;
	}

	public void setFecIniRegistro(String fecIniRegistro) {
		this.fecIniRegistro = fecIniRegistro;
	}

	public String getFecFinRegistro() {
		return fecFinRegistro;
	}

	public void setFecFinRegistro(String fecFinRegistro) {
		this.fecFinRegistro = fecFinRegistro;
	}

	public String getCveIdTipoPersona() {
		return cveIdTipoPersona;
	}

	public void setCveIdTipoPersona(String cveIdTipoPersona) {
		this.cveIdTipoPersona = cveIdTipoPersona;
	}

	public String getClaseRectificada() {
		return claseRectificada;
	}

	public void setClaseRectificada(String claseRectificada) {
		this.claseRectificada = claseRectificada;
	}

	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public String getCveIdEstatus() {
		return cveIdEstatus;
	}

	public void setCveIdEstatus(String cveIdEstatus) {
		this.cveIdEstatus = cveIdEstatus;
	}

	public String getCveIdDelegacion() {
		return cveIdDelegacion;
	}

	public void setCveIdDelegacion(String cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}

	public String getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}

	public void setCveIdSubdelegacion(String cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getCveIdTipoTramite() {
		return cveIdTipoTramite;
	}

	public void setCveIdTipoTramite(String cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
	}

}
