package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio;

import java.util.Date;

public class RegistroMovRissSindo {
	private String numeroRegistroPatronal;
	private String rfc;
	private String numNss;
	private String curp;
	private String tipPatPerFis;
	private int numConsecutivo;
	private int porcentaje;
	private Date fechaMovimiento;

	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public String getNumeroRegistroPatronal() {
		return numeroRegistroPatronal;
	}

	public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
		this.numeroRegistroPatronal = numeroRegistroPatronal;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNumNss() {
		return numNss;
	}

	public void setNumNss(String numNss) {
		this.numNss = numNss;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getTipPatPerFis() {
		return tipPatPerFis;
	}

	public void setTipPatPerFis(String tipPatPerFis) {
		this.tipPatPerFis = tipPatPerFis;
	}

	public int getNumConsecutivo() {
		return numConsecutivo;
	}

	public void setNumConsecutivo(int numConsecutivo) {
		this.numConsecutivo = numConsecutivo;
	}

	public int getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}
}
