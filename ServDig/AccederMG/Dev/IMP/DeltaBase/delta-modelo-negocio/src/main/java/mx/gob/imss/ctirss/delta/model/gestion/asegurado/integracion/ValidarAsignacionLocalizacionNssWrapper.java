package mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

public class ValidarAsignacionLocalizacionNssWrapper extends AbstractModel {
	private static final long serialVersionUID = 5083387642833845308L;

	private Fisica fisica;

	/*
	 * Banderas para indicar que datos se desean validar en los servicios de
	 * validacion para asignacion/localizacion de NSS
	 */
	private boolean validarSolicitudesActivas;
	private boolean validarCalificaciones;
	private boolean validarDifSoloFecNac;
	private boolean validarFormatoCurp;
	private boolean validarDomicilioUmf;
	private boolean validaAseguradoCL3;

	public boolean isValidaAseguradoCL3() {
		return validaAseguradoCL3;
	}

	public void setValidaAseguradoCL3(boolean validaAseguradoCL3) {
		this.validaAseguradoCL3 = validaAseguradoCL3;
	}

	public Fisica getFisica() {
		return fisica;
	}

	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}

	public boolean isValidarSolicitudesActivas() {
		return validarSolicitudesActivas;
	}

	public void setValidarSolicitudesActivas(boolean validarSolicitudesActivas) {
		this.validarSolicitudesActivas = validarSolicitudesActivas;
	}

	public boolean isValidarCalificaciones() {
		return validarCalificaciones;
	}

	public void setValidarCalificaciones(boolean validarCalificaciones) {
		this.validarCalificaciones = validarCalificaciones;
	}

	public boolean isValidarDifSoloFecNac() {
		return validarDifSoloFecNac;
	}

	public void setValidarDifSoloFecNac(boolean validarDifSoloFecNac) {
		this.validarDifSoloFecNac = validarDifSoloFecNac;
	}

	public boolean isValidarFormatoCurp() {
		return validarFormatoCurp;
	}

	public void setValidarFormatoCurp(boolean validarFormatoCurp) {
		this.validarFormatoCurp = validarFormatoCurp;
	}

	public boolean isValidarDomicilioUmf() {
		return validarDomicilioUmf;
	}

	public void setValidarDomicilioUmf(boolean validarDomicilioUmf) {
		this.validarDomicilioUmf = validarDomicilioUmf;
	}
}
