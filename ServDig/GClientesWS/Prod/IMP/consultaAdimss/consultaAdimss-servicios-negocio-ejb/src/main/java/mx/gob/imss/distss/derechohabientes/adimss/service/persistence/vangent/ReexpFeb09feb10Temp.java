package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the REEXP_FEB09FEB10_TEMP database table.
 * 
 */
@Embeddable
@Table(name="REEXP_FEB09FEB10_TEMP")
@NamedQuery(name="ReexpFeb09feb10Temp.findAll", query="SELECT r FROM ReexpFeb09feb10Temp r")
public class ReexpFeb09feb10Temp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEGACION")
	private BigDecimal cveDelegacion;

	@Column(name="CVE_PERSONA")
	private BigDecimal cvePersona;

	@Column(name="NUM_ECONOMICO")
	private BigDecimal numEconomico;

	@Column(name="NUM_NIVEL_ATENCION")
	private BigDecimal numNivelAtencion;

	private String status;

	public ReexpFeb09feb10Temp() {
	}

	public BigDecimal getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(BigDecimal cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public BigDecimal getCvePersona() {
		return this.cvePersona;
	}

	public void setCvePersona(BigDecimal cvePersona) {
		this.cvePersona = cvePersona;
	}

	public BigDecimal getNumEconomico() {
		return this.numEconomico;
	}

	public void setNumEconomico(BigDecimal numEconomico) {
		this.numEconomico = numEconomico;
	}

	public BigDecimal getNumNivelAtencion() {
		return this.numNivelAtencion;
	}

	public void setNumNivelAtencion(BigDecimal numNivelAtencion) {
		this.numNivelAtencion = numNivelAtencion;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}