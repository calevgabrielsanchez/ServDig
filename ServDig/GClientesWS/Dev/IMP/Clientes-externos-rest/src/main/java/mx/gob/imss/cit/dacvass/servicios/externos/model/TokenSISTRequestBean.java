package mx.gob.imss.cit.dacvass.servicios.externos.model;

import java.io.Serializable;

public class TokenSISTRequestBean implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6892557313860009009L;
	private String rfcSolicitante;
	private String registroPatronal;
	private String razonSocial;
	private String curpSolicitante;
	private String idPersonaUsuario;
	private boolean isPatronPlataforma;
	private String nombrePersona;
	private boolean isListaBlanca;
	private String fechaDespliegue;
	
	public String getFechaDespliegue() {
		return fechaDespliegue;
	}
	public void setFechaDespliegue(String fechaDespliegue) {
		this.fechaDespliegue = fechaDespliegue;
	}
	public boolean isListaBlanca() {
		return isListaBlanca;
	}
	public void setListaBlanca(boolean isListaBlanca) {
		this.isListaBlanca = isListaBlanca;
	}
	
	public String getNombrePersona() {
		return nombrePersona;
	}
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}
	public boolean isPatronPlataforma() {
		return isPatronPlataforma;
	}
	public void setPatronPlataforma(boolean isPatronPlataforma) {
		this.isPatronPlataforma = isPatronPlataforma;
	}
	public String getIdPersonaUsuario() {
		return idPersonaUsuario;
	}
	public void setIdPersonaUsuario(String idPersonaUsuario) {
		this.idPersonaUsuario = idPersonaUsuario;
	}
	public String getRfcSolicitante() {
		return rfcSolicitante;
	}
	public void setRfcSolicitante(String rfcSolicitante) {
		this.rfcSolicitante = rfcSolicitante;
	}
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getCurpSolicitante() {
		return curpSolicitante;
	}
	public void setCurpSolicitante(String curpSolicitante) {
		this.curpSolicitante = curpSolicitante;
	}
	
	

}
