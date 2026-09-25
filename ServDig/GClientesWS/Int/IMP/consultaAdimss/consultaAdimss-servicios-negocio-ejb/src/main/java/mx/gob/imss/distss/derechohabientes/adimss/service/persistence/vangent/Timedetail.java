package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TIMEDETAILS database table.
 * 
 */
@Entity
@Table(name="TIMEDETAILS")
@NamedQuery(name="Timedetail.findAll", query="SELECT t FROM Timedetail t")
public class Timedetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idtimedetail;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="\"MONTH\"")
	private BigDecimal month;

	private BigDecimal timebasiccapture;

	private BigDecimal timecomplementarycapture;

	private BigDecimal timeconfirmation;

	@Temporal(TemporalType.DATE)
	private Date timedetaildate;

	private BigDecimal timedocuments;

	private BigDecimal timefingerprints;

	private BigDecimal timephoto;

	private BigDecimal timeprinterandverification;

	private BigDecimal timesign;

	private BigDecimal timestep1;

	private BigDecimal timestep2;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	@Column(name="\"YEAR\"")
	private BigDecimal year;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	public Timedetail() {
	}

	public long getIdtimedetail() {
		return this.idtimedetail;
	}

	public void setIdtimedetail(long idtimedetail) {
		this.idtimedetail = idtimedetail;
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

	public BigDecimal getMonth() {
		return this.month;
	}

	public void setMonth(BigDecimal month) {
		this.month = month;
	}

	public BigDecimal getTimebasiccapture() {
		return this.timebasiccapture;
	}

	public void setTimebasiccapture(BigDecimal timebasiccapture) {
		this.timebasiccapture = timebasiccapture;
	}

	public BigDecimal getTimecomplementarycapture() {
		return this.timecomplementarycapture;
	}

	public void setTimecomplementarycapture(BigDecimal timecomplementarycapture) {
		this.timecomplementarycapture = timecomplementarycapture;
	}

	public BigDecimal getTimeconfirmation() {
		return this.timeconfirmation;
	}

	public void setTimeconfirmation(BigDecimal timeconfirmation) {
		this.timeconfirmation = timeconfirmation;
	}

	public Date getTimedetaildate() {
		return this.timedetaildate;
	}

	public void setTimedetaildate(Date timedetaildate) {
		this.timedetaildate = timedetaildate;
	}

	public BigDecimal getTimedocuments() {
		return this.timedocuments;
	}

	public void setTimedocuments(BigDecimal timedocuments) {
		this.timedocuments = timedocuments;
	}

	public BigDecimal getTimefingerprints() {
		return this.timefingerprints;
	}

	public void setTimefingerprints(BigDecimal timefingerprints) {
		this.timefingerprints = timefingerprints;
	}

	public BigDecimal getTimephoto() {
		return this.timephoto;
	}

	public void setTimephoto(BigDecimal timephoto) {
		this.timephoto = timephoto;
	}

	public BigDecimal getTimeprinterandverification() {
		return this.timeprinterandverification;
	}

	public void setTimeprinterandverification(BigDecimal timeprinterandverification) {
		this.timeprinterandverification = timeprinterandverification;
	}

	public BigDecimal getTimesign() {
		return this.timesign;
	}

	public void setTimesign(BigDecimal timesign) {
		this.timesign = timesign;
	}

	public BigDecimal getTimestep1() {
		return this.timestep1;
	}

	public void setTimestep1(BigDecimal timestep1) {
		this.timestep1 = timestep1;
	}

	public BigDecimal getTimestep2() {
		return this.timestep2;
	}

	public void setTimestep2(BigDecimal timestep2) {
		this.timestep2 = timestep2;
	}

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

	public BigDecimal getYear() {
		return this.year;
	}

	public void setYear(BigDecimal year) {
		this.year = year;
	}

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

}