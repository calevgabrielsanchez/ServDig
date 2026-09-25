package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.asegurado.TipoMovtoAsegurado;

public class PeriodoMovimientoAfiliatorio implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date fechaInicioMovimiento;
	private Date fechaFinalMovimiento;
	private TipoMovtoAsegurado tipoMovimientoInicial;
	private TipoMovtoAsegurado tipoMovimientoFinal;
	private String nrp;
	private String nss;
	private Modalidad cveModalidad;

	public Date getFechaInicioMovimiento() {
		return fechaInicioMovimiento;
	}

	public void setFechaInicioMovimiento(Date fechaInicioMovimiento) {
		this.fechaInicioMovimiento = fechaInicioMovimiento;
	}

	public Date getFechaFinalMovimiento() {
		return fechaFinalMovimiento;
	}

	public void setFechaFinalMovimiento(Date fechaFinalMovimiento) {
		this.fechaFinalMovimiento = fechaFinalMovimiento;
	}

	public TipoMovtoAsegurado getTipoMovimientoInicial() {
		return tipoMovimientoInicial;
	}

	public void setTipoMovimientoInicial(
			TipoMovtoAsegurado tipoMovimientoInicial) {
		this.tipoMovimientoInicial = tipoMovimientoInicial;
	}

	public TipoMovtoAsegurado getTipoMovimientoFinal() {
		return tipoMovimientoFinal;
	}

	public void setTipoMovimientoFinal(TipoMovtoAsegurado tipoMovimientoFinal) {
		this.tipoMovimientoFinal = tipoMovimientoFinal;
	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public Modalidad getCveModalidad() {
		return cveModalidad;
	}

	public void setCveModalidad(Modalidad cveModalidad) {
		this.cveModalidad = cveModalidad;
	}

}
