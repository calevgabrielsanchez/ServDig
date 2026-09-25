package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTFLOWS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTFLOWS")
@NamedQuery(name="Enrollmentflow.findAll", query="SELECT e FROM Enrollmentflow e")
public class Enrollmentflow implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentflowPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

//	private Object enrolstatedate;

	private BigDecimal enrolstatestatus;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollment
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROL", referencedColumnName="IDENROL"),
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION")
		})
	private Enrollment enrollment;

	//bi-directional many-to-one association to Enrollmentstate
	@ManyToOne
	@JoinColumn(name="IDENROLSTATE")
	private Enrollmentstate enrollmentstate;

	public Enrollmentflow() {
	}

	public EnrollmentflowPK getId() {
		return this.id;
	}

	public void setId(EnrollmentflowPK id) {
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

//	public Object getEnrolstatedate() {
//		return this.enrolstatedate;
//	}
//
//	public void setEnrolstatedate(Object enrolstatedate) {
//		this.enrolstatedate = enrolstatedate;
//	}

	public BigDecimal getEnrolstatestatus() {
		return this.enrolstatestatus;
	}

	public void setEnrolstatestatus(BigDecimal enrolstatestatus) {
		this.enrolstatestatus = enrolstatestatus;
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

	public Enrollment getEnrollment() {
		return this.enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public Enrollmentstate getEnrollmentstate() {
		return this.enrollmentstate;
	}

	public void setEnrollmentstate(Enrollmentstate enrollmentstate) {
		this.enrollmentstate = enrollmentstate;
	}

}