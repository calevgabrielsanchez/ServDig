package mx.gob.imss.cit.dacvass.servicios.externos.model;

import java.io.Serializable;

public class TokenConveniosRequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4235266489659033485L;
	
	private String rfcSolicitante;
	private String registroPatronal;
	private String razonSocial;
	private String curpRepresentante;
	private String nombreRepresentante;
	private String idPersonaUsuario;
	
	
	
	
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
	public String getCurpRepresentante() {
		return curpRepresentante;
	}
	public void setCurpRepresentante(String curpRepresentante) {
		this.curpRepresentante = curpRepresentante;
	}
	public String getNombreRepresentante() {
		return nombreRepresentante;
	}
	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}
	
	

}
