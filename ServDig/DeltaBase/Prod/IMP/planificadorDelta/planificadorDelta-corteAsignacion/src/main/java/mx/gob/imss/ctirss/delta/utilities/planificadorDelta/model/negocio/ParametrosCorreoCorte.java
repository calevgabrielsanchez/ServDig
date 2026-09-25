package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio;

import java.util.Date;

import mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.enums.TipoCorteEnum;

public class ParametrosCorreoCorte {
	private Date fechaOperacion;
	private TipoCorteEnum tipoCorte;
	private Date fechaCorte;
	private Exception excepcionGeneral;
	private Long numAsignacionInternet = 0L;
	private Long numLocalizacionInternet = 0L;
	private Long numAsignacionVentanilla = 0L;
	private Long numLocalizacionVentanilla = 0L;

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public TipoCorteEnum getTipoCorte() {
		return tipoCorte;
	}

	public void setTipoCorte(TipoCorteEnum tipoCorte) {
		this.tipoCorte = tipoCorte;
	}

	public Date getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public Exception getExcepcionGeneral() {
		return excepcionGeneral;
	}

	public void setExcepcionGeneral(Exception excepcionGeneral) {
		this.excepcionGeneral = excepcionGeneral;
	}

	public Long getNumAsignacionInternet() {
		return numAsignacionInternet;
	}

	public void setNumAsignacionInternet(Long numAsignacionInternet) {
		this.numAsignacionInternet = numAsignacionInternet;
	}

	public Long getNumLocalizacionInternet() {
		return numLocalizacionInternet;
	}

	public void setNumLocalizacionInternet(Long numLocalizacionInternet) {
		this.numLocalizacionInternet = numLocalizacionInternet;
	}

	public Long getNumAsignacionVentanilla() {
		return numAsignacionVentanilla;
	}

	public void setNumAsignacionVentanilla(Long numAsignacionVentanilla) {
		this.numAsignacionVentanilla = numAsignacionVentanilla;
	}

	public Long getNumLocalizacionVentanilla() {
		return numLocalizacionVentanilla;
	}

	public void setNumLocalizacionVentanilla(Long numLocalizacionVentanilla) {
		this.numLocalizacionVentanilla = numLocalizacionVentanilla;
	}
}
