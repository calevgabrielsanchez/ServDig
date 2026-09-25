package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTCTRACKING database table.
 * 
 */
@Entity
@NamedQuery(name="Enrollmentctracking.findAll", query="SELECT e FROM Enrollmentctracking e")
public class Enrollmentctracking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrollmentctrack;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String endtime;

	private String enrollmentcentername;

	private String locality;

	private String starttime;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Delegation
	@ManyToOne
	@JoinColumn(name="IDDELEGATION")
	private Delegation delegation;

	//bi-directional many-to-one association to Enrollmentcenter
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTCENTER")
	private Enrollmentcenter enrollmentcenter;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	public Enrollmentctracking() {
	}

	public long getIdenrollmentctrack() {
		return this.idenrollmentctrack;
	}

	public void setIdenrollmentctrack(long idenrollmentctrack) {
		this.idenrollmentctrack = idenrollmentctrack;
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

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getEnrollmentcentername() {
		return this.enrollmentcentername;
	}

	public void setEnrollmentcentername(String enrollmentcentername) {
		this.enrollmentcentername = enrollmentcentername;
	}

	public String getLocality() {
		return this.locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
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

	public Delegation getDelegation() {
		return this.delegation;
	}

	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}

	public Enrollmentcenter getEnrollmentcenter() {
		return this.enrollmentcenter;
	}

	public void setEnrollmentcenter(Enrollmentcenter enrollmentcenter) {
		this.enrollmentcenter = enrollmentcenter;
	}

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

}