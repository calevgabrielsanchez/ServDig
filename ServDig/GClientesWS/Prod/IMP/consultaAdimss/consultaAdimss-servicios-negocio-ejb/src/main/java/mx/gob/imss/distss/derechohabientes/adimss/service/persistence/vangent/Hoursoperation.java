package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the HOURSOPERATION database table.
 * 
 */
@Entity
@NamedQuery(name="Hoursoperation.findAll", query="SELECT h FROM Hoursoperation h")
public class Hoursoperation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idhoursoperation;

	@Temporal(TemporalType.DATE)
	private Date beginopertime;

	private BigDecimal begintimedelay;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date endopertime;

	private BigDecimal endtimeforward;

	@Temporal(TemporalType.DATE)
	private Date hoursoperationdate;

	@Column(name="\"MONTH\"")
	private BigDecimal month;

	private BigDecimal specialschedule;

	private String timezone;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	@Column(name="\"YEAR\"")
	private BigDecimal year;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	public Hoursoperation() {
	}

	public long getIdhoursoperation() {
		return this.idhoursoperation;
	}

	public void setIdhoursoperation(long idhoursoperation) {
		this.idhoursoperation = idhoursoperation;
	}

	public Date getBeginopertime() {
		return this.beginopertime;
	}

	public void setBeginopertime(Date beginopertime) {
		this.beginopertime = beginopertime;
	}

	public BigDecimal getBegintimedelay() {
		return this.begintimedelay;
	}

	public void setBegintimedelay(BigDecimal begintimedelay) {
		this.begintimedelay = begintimedelay;
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

	public Date getEndopertime() {
		return this.endopertime;
	}

	public void setEndopertime(Date endopertime) {
		this.endopertime = endopertime;
	}

	public BigDecimal getEndtimeforward() {
		return this.endtimeforward;
	}

	public void setEndtimeforward(BigDecimal endtimeforward) {
		this.endtimeforward = endtimeforward;
	}

	public Date getHoursoperationdate() {
		return this.hoursoperationdate;
	}

	public void setHoursoperationdate(Date hoursoperationdate) {
		this.hoursoperationdate = hoursoperationdate;
	}

	public BigDecimal getMonth() {
		return this.month;
	}

	public void setMonth(BigDecimal month) {
		this.month = month;
	}

	public BigDecimal getSpecialschedule() {
		return this.specialschedule;
	}

	public void setSpecialschedule(BigDecimal specialschedule) {
		this.specialschedule = specialschedule;
	}

	public String getTimezone() {
		return this.timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
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