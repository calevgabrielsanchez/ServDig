package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;


import mx.gob.imss.ctirss.correccion.bean.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

public class ParamSolicitudTramite implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String registroPatronal;
	private String tramiteNotaria;
	private String cadenaOriginal;
	private String selloDigital;
	private SujetoObligado sujetoObligado;
	private int usuarioInternet;
	private String numeroFolio;
	private RespuestaFirmadoSimple respuestaFirmadoSimple;
	private FirmaElectronica firmaElectronicaResultado;
	private Long claveSubDelegacion;
	private String cveUsuario;
	
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public String getTramiteNotaria() {
		return tramiteNotaria;
	}
	public void setTramiteNotaria(String tramiteNotaria) {
		this.tramiteNotaria = tramiteNotaria;
	}
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}
	public String getSelloDigital() {
		return selloDigital;
	}
	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}
	public SujetoObligado getSujetoObligado() {
		return sujetoObligado;
	}
	public void setSujetoObligado(SujetoObligado sujetoObligado) {
		this.sujetoObligado = sujetoObligado;
	}
	public int getUsuarioInternet() {
		return usuarioInternet;
	}
	public void setUsuarioInternet(int usuarioInternet) {
		this.usuarioInternet = usuarioInternet;
	}
	public String getNumeroFolio() {
		return numeroFolio;
	}
	public void setNumeroFolio(String numeroFolio) {
		this.numeroFolio = numeroFolio;
	}
	public RespuestaFirmadoSimple getRespuestaFirmadoSimple() {
		return respuestaFirmadoSimple;
	}
	public void setRespuestaFirmadoSimple(
			RespuestaFirmadoSimple respuestaFirmadoSimple) {
		this.respuestaFirmadoSimple = respuestaFirmadoSimple;
	}
	public FirmaElectronica getFirmaElectronicaResultado() {
		return firmaElectronicaResultado;
	}
	public void setFirmaElectronicaResultado(
			FirmaElectronica firmaElectronicaResultado) {
		this.firmaElectronicaResultado = firmaElectronicaResultado;
	}
	public Long getClaveSubDelegacion() {
		return claveSubDelegacion;
	}
	public void setClaveSubDelegacion(Long claveSubDelegacion) {
		this.claveSubDelegacion = claveSubDelegacion;
	}
	public String getCveUsuario() {
		return cveUsuario;
	}
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	
}
