package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio;

public class RegistroMovSindo {
	private String tipoMovimiento;
	private String numeroRegistroPatronal;
	private String curp;
	private String rfc;
	private int tipoClasificacion;

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getNumeroRegistroPatronal() {
		return numeroRegistroPatronal;
	}

	public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
		this.numeroRegistroPatronal = numeroRegistroPatronal;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public int getTipoClasificacion() {
		return tipoClasificacion;
	}

	public void setTipoClasificacion(int tipoClasificacion) {
		this.tipoClasificacion = tipoClasificacion;
	}
}
