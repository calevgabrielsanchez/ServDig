package mx.gob.imss.distss.portal.vigencia.grupo.bean;

import java.io.Serializable;
import java.util.Date;

public class InfoSession implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 261947097061310666L;
	
	private Date fechaSistema;
	private Date fechaFinSession;
	private Date fechaAvisoSession;
	private boolean validaAvisoSession;
	
	
	public Date getFechaSistema() {
		return fechaSistema;
	}
	public void setFechaSistema(Date fechaSistema) {
		this.fechaSistema = fechaSistema;
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
	public boolean isValidaAvisoSession() {
		return validaAvisoSession;
	}
	public void setValidaAvisoSession(boolean validaAvisoSession) {
		this.validaAvisoSession = validaAvisoSession;
	}
	
	
}
