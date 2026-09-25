package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTDETAILS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTDETAILS")
@NamedQuery(name="Enrollmentdetail.findAll", query="SELECT e FROM Enrollmentdetail e")
public class Enrollmentdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentdetailPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

//	private Object enrolendtime;
//
//	private Object enrolstarttime;

	private String opcomment;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollment
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROL", referencedColumnName="IDENROL", insertable=false, updatable=false),
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION", insertable=false, updatable=false)
		})
	private Enrollment enrollment;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION", insertable=false, updatable=false),
		@JoinColumn(name="IDUSERS", referencedColumnName="IDUSERS", insertable=false, updatable=false)
		})
	private User user;

	public Enrollmentdetail() {
	}

	public EnrollmentdetailPK getId() {
		return this.id;
	}

	public void setId(EnrollmentdetailPK id) {
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

//	public Object getEnrolendtime() {
//		return this.enrolendtime;
//	}
//
//	public void setEnrolendtime(Object enrolendtime) {
//		this.enrolendtime = enrolendtime;
//	}
//
//	public Object getEnrolstarttime() {
//		return this.enrolstarttime;
//	}
//
//	public void setEnrolstarttime(Object enrolstarttime) {
//		this.enrolstarttime = enrolstarttime;
//	}

	public String getOpcomment() {
		return this.opcomment;
	}

	public void setOpcomment(String opcomment) {
		this.opcomment = opcomment;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}