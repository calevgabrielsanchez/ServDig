package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the CES_HORARIOS_ESPECIALES database table.
 * 
 */
@Embeddable
@Table(name="CES_HORARIOS_ESPECIALES")
@NamedQuery(name="CesHorariosEspeciale.findAll", query="SELECT c FROM CesHorariosEspeciale c")
public class CesHorariosEspeciale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEGACION")
	private BigDecimal cveDelegacion;

	@Column(name="HORA_FIN")
	private String horaFin;

	@Column(name="HORA_INICIO")
	private String horaInicio;

	private BigDecimal idenrollmentstation;

	@Column(name="MM_RR")
	private String mmRr;

	public CesHorariosEspeciale() {
	}

	public BigDecimal getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(BigDecimal cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public String getMmRr() {
		return this.mmRr;
	}

	public void setMmRr(String mmRr) {
		this.mmRr = mmRr;
	}

}