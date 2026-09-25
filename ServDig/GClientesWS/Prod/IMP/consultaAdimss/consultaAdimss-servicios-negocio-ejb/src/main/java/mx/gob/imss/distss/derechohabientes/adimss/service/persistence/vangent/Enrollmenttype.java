package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTTYPES database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTTYPES")
@NamedQuery(name="Enrollmenttype.findAll", query="SELECT e FROM Enrollmenttype e")
public class Enrollmenttype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenroltype;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String enroltypedesc;

	private String enroltypename;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollment
	@OneToMany(mappedBy="enrollmenttype")
	private List<Enrollment> enrollments;

	//bi-directional many-to-one association to Enrollmentworkflow
	@OneToMany(mappedBy="enrollmenttype")
	private List<Enrollmentworkflow> enrollmentworkflows;

	//bi-directional many-to-one association to Userattention
	@OneToMany(mappedBy="enrollmenttype")
	private List<Userattention> userattentions;

	public Enrollmenttype() {
	}

	public long getIdenroltype() {
		return this.idenroltype;
	}

	public void setIdenroltype(long idenroltype) {
		this.idenroltype = idenroltype;
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

	public String getEnroltypedesc() {
		return this.enroltypedesc;
	}

	public void setEnroltypedesc(String enroltypedesc) {
		this.enroltypedesc = enroltypedesc;
	}

	public String getEnroltypename() {
		return this.enroltypename;
	}

	public void setEnroltypename(String enroltypename) {
		this.enroltypename = enroltypename;
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
		enrollment.setEnrollmenttype(this);

		return enrollment;
	}

	public Enrollment removeEnrollment(Enrollment enrollment) {
		getEnrollments().remove(enrollment);
		enrollment.setEnrollmenttype(null);

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
		enrollmentworkflow.setEnrollmenttype(this);

		return enrollmentworkflow;
	}

	public Enrollmentworkflow removeEnrollmentworkflow(Enrollmentworkflow enrollmentworkflow) {
		getEnrollmentworkflows().remove(enrollmentworkflow);
		enrollmentworkflow.setEnrollmenttype(null);

		return enrollmentworkflow;
	}

	public List<Userattention> getUserattentions() {
		return this.userattentions;
	}

	public void setUserattentions(List<Userattention> userattentions) {
		this.userattentions = userattentions;
	}

	public Userattention addUserattention(Userattention userattention) {
		getUserattentions().add(userattention);
		userattention.setEnrollmenttype(this);

		return userattention;
	}

	public Userattention removeUserattention(Userattention userattention) {
		getUserattentions().remove(userattention);
		userattention.setEnrollmenttype(null);

		return userattention;
	}

}