package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTSTATES database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTSTATES")
@NamedQuery(name="Enrollmentstate.findAll", query="SELECT e FROM Enrollmentstate e")
public class Enrollmentstate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrolstate;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String enrolstatedescription;

	private String enrolstatename;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Controladimss
	@OneToMany(mappedBy="enrollmentstate")
	private List<Controladimss> controladimsses;

	//bi-directional many-to-one association to Enrollmentbilling
	@OneToMany(mappedBy="enrollmentstate")
	private List<Enrollmentbilling> enrollmentbillings;

	//bi-directional many-to-one association to Enrollmentflow
	@OneToMany(mappedBy="enrollmentstate")
	private List<Enrollmentflow> enrollmentflows;

	//bi-directional many-to-one association to Enrollment
	@OneToMany(mappedBy="enrollmentstate")
	private List<Enrollment> enrollments;

	//bi-directional many-to-one association to Enrollmentsworld
	@OneToMany(mappedBy="enrollmentstate")
	private List<Enrollmentsworld> enrollmentsworlds;

	public Enrollmentstate() {
	}

	public long getIdenrolstate() {
		return this.idenrolstate;
	}

	public void setIdenrolstate(long idenrolstate) {
		this.idenrolstate = idenrolstate;
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

	public String getEnrolstatedescription() {
		return this.enrolstatedescription;
	}

	public void setEnrolstatedescription(String enrolstatedescription) {
		this.enrolstatedescription = enrolstatedescription;
	}

	public String getEnrolstatename() {
		return this.enrolstatename;
	}

	public void setEnrolstatename(String enrolstatename) {
		this.enrolstatename = enrolstatename;
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

	public List<Controladimss> getControladimsses() {
		return this.controladimsses;
	}

	public void setControladimsses(List<Controladimss> controladimsses) {
		this.controladimsses = controladimsses;
	}

	public Controladimss addControladimss(Controladimss controladimss) {
		getControladimsses().add(controladimss);
		controladimss.setEnrollmentstate(this);

		return controladimss;
	}

	public Controladimss removeControladimss(Controladimss controladimss) {
		getControladimsses().remove(controladimss);
		controladimss.setEnrollmentstate(null);

		return controladimss;
	}

	public List<Enrollmentbilling> getEnrollmentbillings() {
		return this.enrollmentbillings;
	}

	public void setEnrollmentbillings(List<Enrollmentbilling> enrollmentbillings) {
		this.enrollmentbillings = enrollmentbillings;
	}

	public Enrollmentbilling addEnrollmentbilling(Enrollmentbilling enrollmentbilling) {
		getEnrollmentbillings().add(enrollmentbilling);
		enrollmentbilling.setEnrollmentstate(this);

		return enrollmentbilling;
	}

	public Enrollmentbilling removeEnrollmentbilling(Enrollmentbilling enrollmentbilling) {
		getEnrollmentbillings().remove(enrollmentbilling);
		enrollmentbilling.setEnrollmentstate(null);

		return enrollmentbilling;
	}

	public List<Enrollmentflow> getEnrollmentflows() {
		return this.enrollmentflows;
	}

	public void setEnrollmentflows(List<Enrollmentflow> enrollmentflows) {
		this.enrollmentflows = enrollmentflows;
	}

	public Enrollmentflow addEnrollmentflow(Enrollmentflow enrollmentflow) {
		getEnrollmentflows().add(enrollmentflow);
		enrollmentflow.setEnrollmentstate(this);

		return enrollmentflow;
	}

	public Enrollmentflow removeEnrollmentflow(Enrollmentflow enrollmentflow) {
		getEnrollmentflows().remove(enrollmentflow);
		enrollmentflow.setEnrollmentstate(null);

		return enrollmentflow;
	}

	public List<Enrollment> getEnrollments() {
		return this.enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	public Enrollment addEnrollment(Enrollment enrollment) {
		getEnrollments().add(enrollment);
		enrollment.setEnrollmentstate(this);

		return enrollment;
	}

	public Enrollment removeEnrollment(Enrollment enrollment) {
		getEnrollments().remove(enrollment);
		enrollment.setEnrollmentstate(null);

		return enrollment;
	}

	public List<Enrollmentsworld> getEnrollmentsworlds() {
		return this.enrollmentsworlds;
	}

	public void setEnrollmentsworlds(List<Enrollmentsworld> enrollmentsworlds) {
		this.enrollmentsworlds = enrollmentsworlds;
	}

	public Enrollmentsworld addEnrollmentsworld(Enrollmentsworld enrollmentsworld) {
		getEnrollmentsworlds().add(enrollmentsworld);
		enrollmentsworld.setEnrollmentstate(this);

		return enrollmentsworld;
	}

	public Enrollmentsworld removeEnrollmentsworld(Enrollmentsworld enrollmentsworld) {
		getEnrollmentsworlds().remove(enrollmentsworld);
		enrollmentsworld.setEnrollmentstate(null);

		return enrollmentsworld;
	}

}