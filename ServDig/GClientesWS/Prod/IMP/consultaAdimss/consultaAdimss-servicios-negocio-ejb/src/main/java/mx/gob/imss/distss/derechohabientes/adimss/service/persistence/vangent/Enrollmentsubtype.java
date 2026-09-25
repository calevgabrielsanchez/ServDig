package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTSUBTYPES database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTSUBTYPES")
@NamedQuery(name="Enrollmentsubtype.findAll", query="SELECT e FROM Enrollmentsubtype e")
public class Enrollmentsubtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrolsubtype;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String enrolsubtypedesc;

	private String enrolsubtypename;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollment
	@OneToMany(mappedBy="enrollmentsubtype")
	private List<Enrollment> enrollments;

	public Enrollmentsubtype() {
	}

	public long getIdenrolsubtype() {
		return this.idenrolsubtype;
	}

	public void setIdenrolsubtype(long idenrolsubtype) {
		this.idenrolsubtype = idenrolsubtype;
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

	public String getEnrolsubtypedesc() {
		return this.enrolsubtypedesc;
	}

	public void setEnrolsubtypedesc(String enrolsubtypedesc) {
		this.enrolsubtypedesc = enrolsubtypedesc;
	}

	public String getEnrolsubtypename() {
		return this.enrolsubtypename;
	}

	public void setEnrolsubtypename(String enrolsubtypename) {
		this.enrolsubtypename = enrolsubtypename;
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
		enrollment.setEnrollmentsubtype(this);

		return enrollment;
	}

	public Enrollment removeEnrollment(Enrollment enrollment) {
		getEnrollments().remove(enrollment);
		enrollment.setEnrollmentsubtype(null);

		return enrollment;
	}

}