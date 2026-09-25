package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the REPLICATIONCONTROL database table.
 * 
 */
@Entity
@NamedQuery(name="Replicationcontrol.findAll", query="SELECT r FROM Replicationcontrol r")
public class Replicationcontrol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrollmentstation;

	@Column(name="ADDITIONAL_DAYS")
	private BigDecimal additionalDays;

	private String comments;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="DAILY_CAPACITY")
	private BigDecimal dailyCapacity;

	private BigDecimal generated;

	@Column(name="NIGHT_MODE")
	private BigDecimal nightMode;

	private BigDecimal pending;

	private BigDecimal replicated;

	private BigDecimal unrecoverable;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	@Temporal(TemporalType.DATE)
	private Date updgenerated;

	@Temporal(TemporalType.DATE)
	private Date updreplicated;

	@Temporal(TemporalType.DATE)
	private Date updunrecoverable;

	public Replicationcontrol() {
	}

	public long getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(long idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public BigDecimal getAdditionalDays() {
		return this.additionalDays;
	}

	public void setAdditionalDays(BigDecimal additionalDays) {
		this.additionalDays = additionalDays;
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

	public BigDecimal getDailyCapacity() {
		return this.dailyCapacity;
	}

	public void setDailyCapacity(BigDecimal dailyCapacity) {
		this.dailyCapacity = dailyCapacity;
	}

	public BigDecimal getGenerated() {
		return this.generated;
	}

	public void setGenerated(BigDecimal generated) {
		this.generated = generated;
	}

	public BigDecimal getNightMode() {
		return this.nightMode;
	}

	public void setNightMode(BigDecimal nightMode) {
		this.nightMode = nightMode;
	}

	public BigDecimal getPending() {
		return this.pending;
	}

	public void setPending(BigDecimal pending) {
		this.pending = pending;
	}

	public BigDecimal getReplicated() {
		return this.replicated;
	}

	public void setReplicated(BigDecimal replicated) {
		this.replicated = replicated;
	}

	public BigDecimal getUnrecoverable() {
		return this.unrecoverable;
	}

	public void setUnrecoverable(BigDecimal unrecoverable) {
		this.unrecoverable = unrecoverable;
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

	public Date getUpdgenerated() {
		return this.updgenerated;
	}

	public void setUpdgenerated(Date updgenerated) {
		this.updgenerated = updgenerated;
	}

	public Date getUpdreplicated() {
		return this.updreplicated;
	}

	public void setUpdreplicated(Date updreplicated) {
		this.updreplicated = updreplicated;
	}

	public Date getUpdunrecoverable() {
		return this.updunrecoverable;
	}

	public void setUpdunrecoverable(Date updunrecoverable) {
		this.updunrecoverable = updunrecoverable;
	}

}