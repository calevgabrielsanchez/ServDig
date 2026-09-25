package mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CPT_CREINC14_IMSS_RCV database table.
 * 
 */
@Entity
@Table(name="CPT_CREINC14_IMSS_RCV")
public class CptCreinc14ImssRcv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEGACION")
	private BigDecimal cveDelegacion;

	@Column(name="CVE_SUBDELEGACION")
	private BigDecimal cveSubdelegacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_MOVTO")
	private Date fecMovto;

	@Column(name="IMP_SALDO_TOTAL")
	private BigDecimal impSaldoTotal;

	@Column(name="MODALIDAD")
	private String modalidad;

	@Column(name="NUM_ANIO_PERIODO_CORTE_OPE")
	private BigDecimal numAnioPeriodoCorteOpe;
	
	@Id
	@Column(name="NUM_CREDITO")
	private BigDecimal numCredito;

	@Column(name="NUM_INCIDENCIA_ACTUAL")
	private BigDecimal numIncidenciaActual;

	@Column(name="NUM_INCIDENCIA_ANTERIOR")
	private BigDecimal numIncidenciaAnterior;

	@Column(name="NUM_MES_PERIODO_CORTE_OPE")
	private BigDecimal numMesPeriodoCorteOpe;

	@Column(name="NUM_PERIODO_CREDITO")
	private BigDecimal numPeriodoCredito;

	@Column(name="REG_PATRONAL")
	private String regPatronal;

	@Column(name="TIP_RAMO_IMSS")
	private BigDecimal tipRamoImss;

	public CptCreinc14ImssRcv() {
	}

	public BigDecimal getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(BigDecimal cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public BigDecimal getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}

	public void setCveSubdelegacion(BigDecimal cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	

	public Date getFecMovto() {
		return fecMovto;
	}

	public void setFecMovto(Date fecMovto) {
		this.fecMovto = fecMovto;
	}

	public BigDecimal getImpSaldoTotal() {
		return this.impSaldoTotal;
	}

	public void setImpSaldoTotal(BigDecimal impSaldoTotal) {
		this.impSaldoTotal = impSaldoTotal;
	}

	public String getModalidad() {
		return this.modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public BigDecimal getNumAnioPeriodoCorteOpe() {
		return this.numAnioPeriodoCorteOpe;
	}

	public void setNumAnioPeriodoCorteOpe(BigDecimal numAnioPeriodoCorteOpe) {
		this.numAnioPeriodoCorteOpe = numAnioPeriodoCorteOpe;
	}

	public BigDecimal getNumCredito() {
		return this.numCredito;
	}

	public void setNumCredito(BigDecimal numCredito) {
		this.numCredito = numCredito;
	}

	public BigDecimal getNumIncidenciaActual() {
		return this.numIncidenciaActual;
	}

	public void setNumIncidenciaActual(BigDecimal numIncidenciaActual) {
		this.numIncidenciaActual = numIncidenciaActual;
	}

	public BigDecimal getNumIncidenciaAnterior() {
		return this.numIncidenciaAnterior;
	}

	public void setNumIncidenciaAnterior(BigDecimal numIncidenciaAnterior) {
		this.numIncidenciaAnterior = numIncidenciaAnterior;
	}

	public BigDecimal getNumMesPeriodoCorteOpe() {
		return this.numMesPeriodoCorteOpe;
	}

	public void setNumMesPeriodoCorteOpe(BigDecimal numMesPeriodoCorteOpe) {
		this.numMesPeriodoCorteOpe = numMesPeriodoCorteOpe;
	}

	public BigDecimal getNumPeriodoCredito() {
		return this.numPeriodoCredito;
	}

	public void setNumPeriodoCredito(BigDecimal numPeriodoCredito) {
		this.numPeriodoCredito = numPeriodoCredito;
	}

	public String getRegPatronal() {
		return this.regPatronal;
	}

	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}

	public BigDecimal getTipRamoImss() {
		return this.tipRamoImss;
	}

	public void setTipRamoImss(BigDecimal tipRamoImss) {
		this.tipRamoImss = tipRamoImss;
	}

}