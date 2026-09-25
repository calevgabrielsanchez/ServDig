package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TIMESUMMARY database table.
 * 
 */
@Entity
@NamedQuery(name="Timesummary.findAll", query="SELECT t FROM Timesummary t")
public class Timesummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idtimesummary;

	private BigDecimal between10and15;

	private BigDecimal between16and20;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal lessthan10;

	@Column(name="\"MONTH\"")
	private BigDecimal month;

	private BigDecimal morethan20;

	@Temporal(TemporalType.DATE)
	private Date timesummarydate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	@Column(name="\"YEAR\"")
	private BigDecimal year;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	public Timesummary() {
	}

	public long getIdtimesummary() {
		return this.idtimesummary;
	}

	public void setIdtimesummary(long idtimesummary) {
		this.idtimesummary = idtimesummary;
	}

	public BigDecimal getBetween10and15() {
		return this.between10and15;
	}

	public void setBetween10and15(BigDecimal between10and15) {
		this.between10and15 = between10and15;
	}

	public BigDecimal getBetween16and20() {
		return this.between16and20;
	}

	public void setBetween16and20(BigDecimal between16and20) {
		this.between16and20 = between16and20;
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

	public BigDecimal getLessthan10() {
		return this.lessthan10;
	}

	public void setLessthan10(BigDecimal lessthan10) {
		this.lessthan10 = lessthan10;
	}

	public BigDecimal getMonth() {
		return this.month;
	}

	public void setMonth(BigDecimal month) {
		this.month = month;
	}

	public BigDecimal getMorethan20() {
		return this.morethan20;
	}

	public void setMorethan20(BigDecimal morethan20) {
		this.morethan20 = morethan20;
	}

	public Date getTimesummarydate() {
		return this.timesummarydate;
	}

	public void setTimesummarydate(Date timesummarydate) {
		this.timesummarydate = timesummarydate;
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