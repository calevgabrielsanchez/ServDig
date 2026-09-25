package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTSWORLD database table.
 * 
 */
@Entity
@NamedQuery(name="Enrollmentsworld.findAll", query="SELECT e FROM Enrollmentsworld e")
public class Enrollmentsworld implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentsworldPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date enroldate;

	private BigDecimal idenrolbaexcepstate;

	private BigDecimal idenrolbastate;

	private BigDecimal idenrolbillstate;

	private BigDecimal idenroletlstate;

	private BigDecimal idenrolmode;

	private BigDecimal idenrolsequence;

	private BigDecimal idenrolsubtype;

	private BigDecimal idenroltype;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentstate
	@ManyToOne
	@JoinColumn(name="IDENROLSTATE")
	private Enrollmentstate enrollmentstate;

	public Enrollmentsworld() {
	}

	public EnrollmentsworldPK getId() {
		return this.id;
	}

	public void setId(EnrollmentsworldPK id) {
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

	public Date getEnroldate() {
		return this.enroldate;
	}

	public void setEnroldate(Date enroldate) {
		this.enroldate = enroldate;
	}

	public BigDecimal getIdenrolbaexcepstate() {
		return this.idenrolbaexcepstate;
	}

	public void setIdenrolbaexcepstate(BigDecimal idenrolbaexcepstate) {
		this.idenrolbaexcepstate = idenrolbaexcepstate;
	}

	public BigDecimal getIdenrolbastate() {
		return this.idenrolbastate;
	}

	public void setIdenrolbastate(BigDecimal idenrolbastate) {
		this.idenrolbastate = idenrolbastate;
	}

	public BigDecimal getIdenrolbillstate() {
		return this.idenrolbillstate;
	}

	public void setIdenrolbillstate(BigDecimal idenrolbillstate) {
		this.idenrolbillstate = idenrolbillstate;
	}

	public BigDecimal getIdenroletlstate() {
		return this.idenroletlstate;
	}

	public void setIdenroletlstate(BigDecimal idenroletlstate) {
		this.idenroletlstate = idenroletlstate;
	}

	public BigDecimal getIdenrolmode() {
		return this.idenrolmode;
	}

	public void setIdenrolmode(BigDecimal idenrolmode) {
		this.idenrolmode = idenrolmode;
	}

	public BigDecimal getIdenrolsequence() {
		return this.idenrolsequence;
	}

	public void setIdenrolsequence(BigDecimal idenrolsequence) {
		this.idenrolsequence = idenrolsequence;
	}

	public BigDecimal getIdenrolsubtype() {
		return this.idenrolsubtype;
	}

	public void setIdenrolsubtype(BigDecimal idenrolsubtype) {
		this.idenrolsubtype = idenrolsubtype;
	}

	public BigDecimal getIdenroltype() {
		return this.idenroltype;
	}

	public void setIdenroltype(BigDecimal idenroltype) {
		this.idenroltype = idenroltype;
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

	public Enrollmentstate getEnrollmentstate() {
		return this.enrollmentstate;
	}

	public void setEnrollmentstate(Enrollmentstate enrollmentstate) {
		this.enrollmentstate = enrollmentstate;
	}

}