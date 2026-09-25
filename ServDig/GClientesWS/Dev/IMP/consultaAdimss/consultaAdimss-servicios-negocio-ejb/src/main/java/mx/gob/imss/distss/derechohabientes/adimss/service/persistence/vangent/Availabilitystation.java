package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the AVAILABILITYSTATIONS database table.
 * 
 */
@Entity
@Table(name="AVAILABILITYSTATIONS")
@NamedQuery(name="Availabilitystation.findAll", query="SELECT a FROM Availabilitystation a")
public class Availabilitystation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrollmentstation;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idavailabilitystatus;

	@Temporal(TemporalType.DATE)
	private Date idavailabilitystatusupdate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Availabilitystation() {
	}

	public long getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(long idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
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

	public BigDecimal getIdavailabilitystatus() {
		return this.idavailabilitystatus;
	}

	public void setIdavailabilitystatus(BigDecimal idavailabilitystatus) {
		this.idavailabilitystatus = idavailabilitystatus;
	}

	public Date getIdavailabilitystatusupdate() {
		return this.idavailabilitystatusupdate;
	}

	public void setIdavailabilitystatusupdate(Date idavailabilitystatusupdate) {
		this.idavailabilitystatusupdate = idavailabilitystatusupdate;
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