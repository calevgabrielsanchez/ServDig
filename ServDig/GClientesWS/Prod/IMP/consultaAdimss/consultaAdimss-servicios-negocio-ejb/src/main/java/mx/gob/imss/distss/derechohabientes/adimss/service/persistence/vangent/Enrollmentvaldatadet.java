package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTVALDATADET database table.
 * 
 */
@Entity
@NamedQuery(name="Enrollmentvaldatadet.findAll", query="SELECT e FROM Enrollmentvaldatadet e")
public class Enrollmentvaldatadet implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentvaldatadetPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idvalstatus;

	private BigDecimal incident;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentvalidation
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROL", referencedColumnName="IDENROL"),
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION")
		})
	private Enrollmentvalidation enrollmentvalidation;

	public Enrollmentvaldatadet() {
	}

	public EnrollmentvaldatadetPK getId() {
		return this.id;
	}

	public void setId(EnrollmentvaldatadetPK id) {
		this.id = id;
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

	public BigDecimal getIdvalstatus() {
		return this.idvalstatus;
	}

	public void setIdvalstatus(BigDecimal idvalstatus) {
		this.idvalstatus = idvalstatus;
	}

	public BigDecimal getIncident() {
		return this.incident;
	}

	public void setIncident(BigDecimal incident) {
		this.incident = incident;
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

	public Enrollmentvalidation getEnrollmentvalidation() {
		return this.enrollmentvalidation;
	}

	public void setEnrollmentvalidation(Enrollmentvalidation enrollmentvalidation) {
		this.enrollmentvalidation = enrollmentvalidation;
	}

}