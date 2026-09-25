package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class CalculoPrimaMovPat extends AbstractModel {

	
	private static final long serialVersionUID = 1L;

	//valores para guardar los datos capturados para el calculo de la prima en el tramite de fusión WO436058
	private BigDecimal diasSubsidiados;
	private BigDecimal porcentajeIncapacidades;
	private BigDecimal numeroDefunciones;
	private BigDecimal numeroTrabajadores;
	private BigDecimal primaAnterior;
	private BigDecimal primaCalculada;
	private BigDecimal primaResultado;
		

	public BigDecimal getDiasSubsidiados() {
		return diasSubsidiados;
	}

	public void setDiasSubsidiados(BigDecimal diasSubsidiados) {
		this.diasSubsidiados = diasSubsidiados;
	}

	public BigDecimal getPorcentajeIncapacidades() {
		return porcentajeIncapacidades;
	}

	public void setPorcentajeIncapacidades(BigDecimal porcentajeIncapacidades) {
		this.porcentajeIncapacidades = porcentajeIncapacidades;
	}

	public BigDecimal getNumeroDefunciones() {
		return numeroDefunciones;
	}

	public void setNumeroDefunciones(BigDecimal numeroDefunciones) {
		this.numeroDefunciones = numeroDefunciones;
	}

	public BigDecimal getNumeroTrabajadores() {
		return numeroTrabajadores;
	}

	public void setNumeroTrabajadores(BigDecimal numeroTrabajadores) {
		this.numeroTrabajadores = numeroTrabajadores;
	}

	public BigDecimal getPrimaAnterior() {
		return primaAnterior;
	}

	public void setPrimaAnterior(BigDecimal primaAnterior) {
		this.primaAnterior = primaAnterior;
	}

	public BigDecimal getPrimaCalculada() {
		return primaCalculada;
	}

	public void setPrimaCalculada(BigDecimal primaCalculada) {
		this.primaCalculada = primaCalculada;
	}

	public BigDecimal getPrimaResultado() {
		return primaResultado;
	}

	public void setPrimaResultado(BigDecimal primaResultado) {
		this.primaResultado = primaResultado;
	}

	
	@Override
	public String toString() {
		return "CalculoPrimaMovPat [diasSubsidiados=" + diasSubsidiados + ", porcentajeIncapacidades="
				+ porcentajeIncapacidades + ", numeroDefunciones=" + numeroDefunciones + ", numeroTrabajadores="
				+ numeroTrabajadores + ", primaAnterior=" + primaAnterior + ", primaCalculada=" + primaCalculada
				+ ", primaResultado=" + primaResultado + "]";
	}
	
}
