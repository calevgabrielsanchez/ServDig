package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRODUCTIONBEHAVIOUR database table.
 * 
 */
@Entity
@NamedQuery(name="Productionbehaviour.findAll", query="SELECT p FROM Productionbehaviour p")
public class Productionbehaviour implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idproductionbehaviour;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="ENROLLMENTS_DOCB")
	private BigDecimal enrollmentsDocb;

	@Column(name="ENROLLMENTS_EDAT")
	private BigDecimal enrollmentsEdat;

	@Column(name="ENROLLMENTS_ENPR")
	private BigDecimal enrollmentsEnpr;

	@Column(name="ENROLLMENTS_EXPE")
	private BigDecimal enrollmentsExpe;

	@Column(name="ENROLLMENTS_PEND")
	private BigDecimal enrollmentsPend;

	@Column(name="ENROLLMENTS_PROD")
	private BigDecimal enrollmentsProd;

	@Column(name="ENROLLMENTS_REPR")
	private BigDecimal enrollmentsRepr;

	@Column(name="ENROLLMENTS_SYNC")
	private BigDecimal enrollmentsSync;

	@Temporal(TemporalType.DATE)
	private Date prodbehaviourdate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	public Productionbehaviour() {
	}

	public long getIdproductionbehaviour() {
		return this.idproductionbehaviour;
	}

	public void setIdproductionbehaviour(long idproductionbehaviour) {
		this.idproductionbehaviour = idproductionbehaviour;
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

	public BigDecimal getEnrollmentsDocb() {
		return this.enrollmentsDocb;
	}

	public void setEnrollmentsDocb(BigDecimal enrollmentsDocb) {
		this.enrollmentsDocb = enrollmentsDocb;
	}

	public BigDecimal getEnrollmentsEdat() {
		return this.enrollmentsEdat;
	}

	public void setEnrollmentsEdat(BigDecimal enrollmentsEdat) {
		this.enrollmentsEdat = enrollmentsEdat;
	}

	public BigDecimal getEnrollmentsEnpr() {
		return this.enrollmentsEnpr;
	}

	public void setEnrollmentsEnpr(BigDecimal enrollmentsEnpr) {
		this.enrollmentsEnpr = enrollmentsEnpr;
	}

	public BigDecimal getEnrollmentsExpe() {
		return this.enrollmentsExpe;
	}

	public void setEnrollmentsExpe(BigDecimal enrollmentsExpe) {
		this.enrollmentsExpe = enrollmentsExpe;
	}

	public BigDecimal getEnrollmentsPend() {
		return this.enrollmentsPend;
	}

	public void setEnrollmentsPend(BigDecimal enrollmentsPend) {
		this.enrollmentsPend = enrollmentsPend;
	}

	public BigDecimal getEnrollmentsProd() {
		return this.enrollmentsProd;
	}

	public void setEnrollmentsProd(BigDecimal enrollmentsProd) {
		this.enrollmentsProd = enrollmentsProd;
	}

	public BigDecimal getEnrollmentsRepr() {
		return this.enrollmentsRepr;
	}

	public void setEnrollmentsRepr(BigDecimal enrollmentsRepr) {
		this.enrollmentsRepr = enrollmentsRepr;
	}

	public BigDecimal getEnrollmentsSync() {
		return this.enrollmentsSync;
	}

	public void setEnrollmentsSync(BigDecimal enrollmentsSync) {
		this.enrollmentsSync = enrollmentsSync;
	}

	public Date getProdbehaviourdate() {
		return this.prodbehaviourdate;
	}

	public void setProdbehaviourdate(Date prodbehaviourdate) {
		this.prodbehaviourdate = prodbehaviourdate;
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

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

}