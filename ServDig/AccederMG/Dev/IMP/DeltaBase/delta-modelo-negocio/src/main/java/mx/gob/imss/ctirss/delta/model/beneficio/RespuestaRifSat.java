package mx.gob.imss.ctirss.delta.model.beneficio;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class RespuestaRifSat extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String rfcVigente;
	private boolean indicadorDerecho;
	private boolean indicadorC;
	private String curp;
	private Date fechaAltaRif;
	private String tipoApartadoBeneficio;
	private String motivoDeRechazo;

	private int exito;
	private int claveError;
	private String descripcion;

	public String getRfcVigente() {
		return rfcVigente;
	}

	public void setRfcVigente(String rfcVigente) {
		this.rfcVigente = rfcVigente;
	}

	public boolean getIndicadorDerecho() {
		return indicadorDerecho;
	}

	public void setIndicadorDerecho(boolean indicadorDerecho) {
		this.indicadorDerecho = indicadorDerecho;
	}

	public boolean getIndicadorC() {
		return indicadorC;
	}

	public void setIndicadorC(boolean indicadorC) {
		this.indicadorC = indicadorC;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public Date getFechaAltaRif() {
		return fechaAltaRif;
	}

	public void setFechaAltaRif(Date fechaAltaRif) {
		this.fechaAltaRif = fechaAltaRif;
	}

	public String getTipoApartadoBeneficio() {
		return tipoApartadoBeneficio;
	}

	public void setTipoApartadoBeneficio(String tipoApartadoBeneficio) {
		this.tipoApartadoBeneficio = tipoApartadoBeneficio;
	}

	public String getMotivoDeRechazo() {
		return motivoDeRechazo;
	}

	public void setMotivoDeRechazo(String motivoDeRechazo) {
		this.motivoDeRechazo = motivoDeRechazo;
	}

	public int getExito() {
		return exito;
	}

	public void setExito(int exito) {
		this.exito = exito;
	}

	public int getClaveError() {
		return claveError;
	}

	public void setClaveError(int claveError) {
		this.claveError = claveError;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
