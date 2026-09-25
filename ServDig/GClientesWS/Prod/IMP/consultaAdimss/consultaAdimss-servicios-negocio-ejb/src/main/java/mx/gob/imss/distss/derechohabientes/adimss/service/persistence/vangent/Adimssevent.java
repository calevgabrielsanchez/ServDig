package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ADIMSSEVENTS database table.
 * 
 */
@Entity
@Table(name="ADIMSSEVENTS")
@NamedQuery(name="Adimssevent.findAll", query="SELECT a FROM Adimssevent a")
public class Adimssevent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idadimssevents;

	private String comments;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="CVE_DELEGACION")
	private BigDecimal cveDelegacion;

//	private Object enddate;

	@Temporal(TemporalType.DATE)
	private Date eventdate;

	private BigDecimal idenrollmentlocation;

	@Column(name="NUM_ECONOMICO")
	private BigDecimal numEconomico;

	@Column(name="NUM_NIVEL_ATENCION")
	private BigDecimal numNivelAtencion;

	private String referenceurl;

//	private Object startdate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Adimssuser
	@ManyToOne
	@JoinColumn(name="IDUSERRESPONSIBLE")
	private Adimssuser adimssuser1;

	//bi-directional many-to-one association to Adimssuser
	@ManyToOne
	@JoinColumn(name="IDUSERAPPROVAL1")
	private Adimssuser adimssuser2;

	//bi-directional many-to-one association to Adimssuser
	@ManyToOne
	@JoinColumn(name="IDUSERAPPROVAL2")
	private Adimssuser adimssuser3;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	//bi-directional many-to-one association to Eventtype
	@ManyToOne
	@JoinColumn(name="IDEVENTTYPE")
	private Eventtype eventtype;

	public Adimssevent() {
	}

	public long getIdadimssevents() {
		return this.idadimssevents;
	}

	public void setIdadimssevents(long idadimssevents) {
		this.idadimssevents = idadimssevents;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public BigDecimal getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(BigDecimal cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

//	public Object getEnddate() {
//		return this.enddate;
//	}
//
//	public void setEnddate(Object enddate) {
//		this.enddate = enddate;
//	}

	public Date getEventdate() {
		return this.eventdate;
	}

	public void setEventdate(Date eventdate) {
		this.eventdate = eventdate;
	}

	public BigDecimal getIdenrollmentlocation() {
		return this.idenrollmentlocation;
	}

	public void setIdenrollmentlocation(BigDecimal idenrollmentlocation) {
		this.idenrollmentlocation = idenrollmentlocation;
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

	public String getReferenceurl() {
		return this.referenceurl;
	}

	public void setReferenceurl(String referenceurl) {
		this.referenceurl = referenceurl;
	}

//	public Object getStartdate() {
//		return this.startdate;
//	}
//
//	public void setStartdate(Object startdate) {
//		this.startdate = startdate;
//	}

	public BigDecimal getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public Adimssuser getAdimssuser1() {
		return this.adimssuser1;
	}

	public void setAdimssuser1(Adimssuser adimssuser1) {
		this.adimssuser1 = adimssuser1;
	}

	public Adimssuser getAdimssuser2() {
		return this.adimssuser2;
	}

	public void setAdimssuser2(Adimssuser adimssuser2) {
		this.adimssuser2 = adimssuser2;
	}

	public Adimssuser getAdimssuser3() {
		return this.adimssuser3;
	}

	public void setAdimssuser3(Adimssuser adimssuser3) {
		this.adimssuser3 = adimssuser3;
	}

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

	public Eventtype getEventtype() {
		return this.eventtype;
	}

	public void setEventtype(Eventtype eventtype) {
		this.eventtype = eventtype;
	}

}