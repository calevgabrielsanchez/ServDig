package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTMODES database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTMODES")
@NamedQuery(name="Enrollmentmode.findAll", query="SELECT e FROM Enrollmentmode e")
public class Enrollmentmode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrolmode;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String enrolmodedesc;

	private String enrolmodename;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollment
	@OneToMany(mappedBy="enrollmentmode")
	private List<Enrollment> enrollments;

	//bi-directional many-to-one association to Enrollmentworkflow
	@OneToMany(mappedBy="enrollmentmode")
	private List<Enrollmentworkflow> enrollmentworkflows;

	public Enrollmentmode() {
	}

	public long getIdenrolmode() {
		return this.idenrolmode;
	}

	public void setIdenrolmode(long idenrolmode) {
		this.idenrolmode = idenrolmode;
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

	public String getEnrolmodedesc() {
		return this.enrolmodedesc;
	}

	public void setEnrolmodedesc(String enrolmodedesc) {
		this.enrolmodedesc = enrolmodedesc;
	}

	public String getEnrolmodename() {
		return this.enrolmodename;
	}

	public void setEnrolmodename(String enrolmodename) {
		this.enrolmodename = enrolmodename;
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

	public List<Enrollment> getEnrollments() {
		return this.enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	public Enrollment addEnrollment(Enrollment enrollment) {
		getEnrollments().add(enrollment);
		enrollment.setEnrollmentmode(this);

		return enrollment;
	}

	public Enrollment removeEnrollment(Enrollment enrollment) {
		getEnrollments().remove(enrollment);
		enrollment.setEnrollmentmode(null);

		return enrollment;
	}

	public List<Enrollmentworkflow> getEnrollmentworkflows() {
		return this.enrollmentworkflows;
	}

	public void setEnrollmentworkflows(List<Enrollmentworkflow> enrollmentworkflows) {
		this.enrollmentworkflows = enrollmentworkflows;
	}

	public Enrollmentworkflow addEnrollmentworkflow(Enrollmentworkflow enrollmentworkflow) {
		getEnrollmentworkflows().add(enrollmentworkflow);
		enrollmentworkflow.setEnrollmentmode(this);

		return enrollmentworkflow;
	}

	public Enrollmentworkflow removeEnrollmentworkflow(Enrollmentworkflow enrollmentworkflow) {
		getEnrollmentworkflows().remove(enrollmentworkflow);
		enrollmentworkflow.setEnrollmentmode(null);

		return enrollmentworkflow;
	}

}