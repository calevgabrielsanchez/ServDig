package mx.gob.imss.ctirss.delta.derechohabientes.web.bean;

import java.io.Serializable;
import java.util.Date;

public class Busqueda implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2619470970613103175L;
	public static final String SES_NAME="busquedaSes"; 
	public static final String REQ_NAME="busqueda";
	private String valor;
	private String usuario;
	private Date fechaSistema;
	private Date fechaFinSession;
	private Date fechaAvisoSession;
	private boolean validaAvisoSession;
	
	private String busca;
	private String nss;
	private String folio;
	private String error;
	private String delegacion;
	
	private String umf;
	private long perfil;
	
	private String subDelegacion;
	
	
	
	
	public String getSubDelegacion() {
		return subDelegacion;
	}
	public void setSubDelegacion(String subDelegacion) {
		this.subDelegacion = subDelegacion;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getBusca() {
		return busca;
	}
	public void setBusca(String busca) {
		this.busca = busca;
	}
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}	
	public Date getFechaSistema() {
		return fechaSistema;
	}
	public void setFechaSistema(Date fechaSistema) {
		this.fechaSistema = fechaSistema;
	}
	public String getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	public String getUmf() {
		return umf;
	}
	public void setUmf(String umf) {
		this.umf = umf;
	}
	public long getPerfil() {
		return perfil;
	}
	public void setPerfil(long perfil) {
		this.perfil = perfil;
	}
	
	
	public Date getFechaFinSession() {
		return fechaFinSession;
	}
	public void setFechaFinSession(Date fechaFinSession) {
		this.fechaFinSession = fechaFinSession;
	}
	
	public Date getFechaAvisoSession() {
		return fechaAvisoSession;
	}
	public void setFechaAvisoSession(Date fechaAvisoSession) {
		this.fechaAvisoSession = fechaAvisoSession;
	}
	
	public boolean getValidaAvisoSession() {
		return validaAvisoSession;
	}
	public void setValidaAvisoSession(boolean validaAvisoSession) {
		this.validaAvisoSession = validaAvisoSession;
	}

}
