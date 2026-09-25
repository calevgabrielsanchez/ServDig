package mx.gob.imss.cit.cda.web.app.common.model;

import java.util.Date;

public class InformacionSesion {

	private String usuario;
	private Date fechaSistema;
	private Date fechaFinSession;
	private Date fechaAvisoSession;
	private boolean validaAvisoSession;
	private String refreshCtx;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

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

	public String getRefreshCtx() {
		return refreshCtx;
	}

	public void setRefreshCtx(String refreshCtx) {
		this.refreshCtx = refreshCtx;
	}

}
