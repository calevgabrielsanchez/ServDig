package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siscob;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CptCreinc14ImssRcv implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 9036706144016105498L;

	private BigDecimal cveDelegacion;

	private BigDecimal cveSubdelegacion;

	private Date fecMovto;
	private BigDecimal impSaldoTotal;

	private String modalidad;

	private BigDecimal numAnioPeriodoCorteOpe;
	
	private BigDecimal numCredito;

	private BigDecimal numIncidenciaActual;

	private BigDecimal numIncidenciaAnterior;

	private BigDecimal numMesPeriodoCorteOpe;

	private BigDecimal numPeriodoCredito;
	private String regPatronal;

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