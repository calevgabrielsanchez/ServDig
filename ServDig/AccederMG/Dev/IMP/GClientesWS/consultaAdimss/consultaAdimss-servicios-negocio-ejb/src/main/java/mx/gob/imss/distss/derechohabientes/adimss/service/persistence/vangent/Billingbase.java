package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BILLINGBASE database table.
 * 
 */
@Entity
@NamedQuery(name="Billingbase.findAll", query="SELECT b FROM Billingbase b")
public class Billingbase implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BillingbasePK id;

	private BigDecimal calidad;

	@Column(name="CVE_DELEGACION")
	private BigDecimal cveDelegacion;

	private String descripcionce;

	private String enrolcurp;

	@Temporal(TemporalType.DATE)
	private Date fecha;

//	@Column(name="HORA_FIN")
//	private Object horaFin;

//	@Column(name="HORA_INICIO")
//	private Object horaInicio;

	private BigDecimal idenrolstate;

	private String nombre;

	private String nss;

	@Column(name="NUM_ECONOMICO")
	private BigDecimal numEconomico;

	@Column(name="NUM_NIVEL_ATENCION")
	private BigDecimal numNivelAtencion;

	public Billingbase() {
	}

	public BillingbasePK getId() {
		return this.id;
	}

	public void setId(BillingbasePK id) {
		this.id = id;
	}

	public BigDecimal getCalidad() {
		return this.calidad;
	}

	public void setCalidad(BigDecimal calidad) {
		this.calidad = calidad;
	}

	public BigDecimal getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(BigDecimal cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getDescripcionce() {
		return this.descripcionce;
	}

	public void setDescripcionce(String descripcionce) {
		this.descripcionce = descripcionce;
	}

	public String getEnrolcurp() {
		return this.enrolcurp;
	}

	public void setEnrolcurp(String enrolcurp) {
		this.enrolcurp = enrolcurp;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

//	public Object getHoraFin() {
//		return this.horaFin;
//	}
//
//	public void setHoraFin(Object horaFin) {
//		this.horaFin = horaFin;
//	}
//
//	public Object getHoraInicio() {
//		return this.horaInicio;
//	}
//
//	public void setHoraInicio(Object horaInicio) {
//		this.horaInicio = horaInicio;
//	}

	public BigDecimal getIdenrolstate() {
		return this.idenrolstate;
	}

	public void setIdenrolstate(BigDecimal idenrolstate) {
		this.idenrolstate = idenrolstate;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNss() {
		return this.nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
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

}