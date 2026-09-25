package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TIMEZONES database table.
 * 
 */
@Entity
@Table(name="TIMEZONES")
@NamedQuery(name="Timezone.findAll", query="SELECT t FROM Timezone t")
public class Timezone implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idtimezone;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal cvedelegation;

	@Temporal(TemporalType.DATE)
	private Date enddate;

	private BigDecimal springdifference;

	@Temporal(TemporalType.DATE)
	private Date startdate;

	private BigDecimal summerdifference;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Timezone() {
	}

	public long getIdtimezone() {
		return this.idtimezone;
	}

	public void setIdtimezone(long idtimezone) {
		this.idtimezone = idtimezone;
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

	public BigDecimal getCvedelegation() {
		return this.cvedelegation;
	}

	public void setCvedelegation(BigDecimal cvedelegation) {
		this.cvedelegation = cvedelegation;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public BigDecimal getSpringdifference() {
		return this.springdifference;
	}

	public void setSpringdifference(BigDecimal springdifference) {
		this.springdifference = springdifference;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public BigDecimal getSummerdifference() {
		return this.summerdifference;
	}

	public void setSummerdifference(BigDecimal summerdifference) {
		this.summerdifference = summerdifference;
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

}