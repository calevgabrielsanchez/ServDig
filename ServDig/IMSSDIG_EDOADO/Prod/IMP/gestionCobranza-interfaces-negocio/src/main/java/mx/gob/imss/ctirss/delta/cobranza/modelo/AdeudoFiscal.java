package mx.gob.imss.ctirss.delta.cobranza.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AdeudoFiscal implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nrp;
	private String modalidad;
	private String numeroCredito;
	private String periodo;
	private Date periodoTmp;
	private BigDecimal saldoTotal;
	private String situacion;
	private String origen;
	private String desSubdelegacion;
	private String desDelegacion;

	public AdeudoFiscal() {
		super();
	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getNumeroCredito() {
		return numeroCredito;
	}

	public void setNumeroCredito(String numeroCredito) {
		this.numeroCredito = numeroCredito;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Date getPeriodoTmp() {
		return periodoTmp;
	}

	public void setPeriodoTmp(Date periodoTmp) {
		this.periodoTmp = periodoTmp;
	}

	public BigDecimal getSaldoTotal() {
		return saldoTotal;
	}

	public void setSaldoTotal(BigDecimal saldoTotal) {
		this.saldoTotal = saldoTotal;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDesSubdelegacion() {
		return desSubdelegacion;
	}

	public void setDesSubdelegacion(String desSubdelegacion) {
		this.desSubdelegacion = desSubdelegacion;
	}

	public String getDesDelegacion() {
		return desDelegacion;
	}

	public void setDesDelegacion(String desDelegacion) {
		this.desDelegacion = desDelegacion;
	}

}