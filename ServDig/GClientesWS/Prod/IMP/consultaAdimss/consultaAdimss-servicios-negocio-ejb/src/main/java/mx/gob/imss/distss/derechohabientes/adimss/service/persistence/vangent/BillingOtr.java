package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the BILLING_OTRS database table.
 * 
 */
@Embeddable
@Table(name="BILLING_OTRS")
@NamedQuery(name="BillingOtr.findAll", query="SELECT b FROM BillingOtr b")
public class BillingOtr implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal ce;

	private String descripcion;

	@Column(name="DURACION_HORAS")
	private BigDecimal duracionHoras;

	@Column(name="FECHA_FIN")
	private String fechaFin;

	@Column(name="FECHA_INICIO")
	private String fechaInicio;

	private BigDecimal id;

	private BigDecimal idenrollment;

	private String incidencia;

	@Column(name="\"MONTH\"")
	private BigDecimal month;

	private String penalizable;

	private BigDecimal sla;

	private String sladescription;

	private String status;

	private BigDecimal ticket;

	public BillingOtr() {
	}

	public BigDecimal getCe() {
		return this.ce;
	}

	public void setCe(BigDecimal ce) {
		this.ce = ce;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getDuracionHoras() {
		return this.duracionHoras;
	}

	public void setDuracionHoras(BigDecimal duracionHoras) {
		this.duracionHoras = duracionHoras;
	}

	public String getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getIdenrollment() {
		return this.idenrollment;
	}

	public void setIdenrollment(BigDecimal idenrollment) {
		this.idenrollment = idenrollment;
	}

	public String getIncidencia() {
		return this.incidencia;
	}

	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}

	public BigDecimal getMonth() {
		return this.month;
	}

	public void setMonth(BigDecimal month) {
		this.month = month;
	}

	public String getPenalizable() {
		return this.penalizable;
	}

	public void setPenalizable(String penalizable) {
		this.penalizable = penalizable;
	}

	public BigDecimal getSla() {
		return this.sla;
	}

	public void setSla(BigDecimal sla) {
		this.sla = sla;
	}

	public String getSladescription() {
		return this.sladescription;
	}

	public void setSladescription(String sladescription) {
		this.sladescription = sladescription;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTicket() {
		return this.ticket;
	}

	public void setTicket(BigDecimal ticket) {
		this.ticket = ticket;
	}

}