package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio;

public class CorteGeneralAsignacion {
	private Exception excepcionGeneral;
	private Long numAsignacionInternet = 0L;
	private Long numLocalizacionInternet = 0L;
	private Long numAsignacionVentanilla = 0L;
	private Long numLocalizacionVentanilla = 0L;

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
