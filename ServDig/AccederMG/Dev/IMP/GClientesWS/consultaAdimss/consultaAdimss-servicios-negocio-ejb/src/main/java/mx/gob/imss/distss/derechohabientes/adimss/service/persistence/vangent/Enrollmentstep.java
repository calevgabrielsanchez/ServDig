package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTSTEPS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTSTEPS")
@NamedQuery(name="Enrollmentstep.findAll", query="SELECT e FROM Enrollmentstep e")
public class Enrollmentstep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrolstep;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String enrolstepdesc;

	private String enrolstepname;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Applicationcomponent
	@OneToMany(mappedBy="enrollmentstep")
	private List<Applicationcomponent> applicationcomponents;

	//bi-directional many-to-one association to Enrollmentworkflow
	@OneToMany(mappedBy="enrollmentstep1")
	private List<Enrollmentworkflow> enrollmentworkflows1;

	//bi-directional many-to-one association to Enrollmentworkflow
	@OneToMany(mappedBy="enrollmentstep2")
	private List<Enrollmentworkflow> enrollmentworkflows2;

	public Enrollmentstep() {
	}

	public long getIdenrolstep() {
		return this.idenrolstep;
	}

	public void setIdenrolstep(long idenrolstep) {
		this.idenrolstep = idenrolstep;
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

	public String getEnrolstepdesc() {
		return this.enrolstepdesc;
	}

	public void setEnrolstepdesc(String enrolstepdesc) {
		this.enrolstepdesc = enrolstepdesc;
	}

	public String getEnrolstepname() {
		return this.enrolstepname;
	}

	public void setEnrolstepname(String enrolstepname) {
		this.enrolstepname = enrolstepname;
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

	public List<Applicationcomponent> getApplicationcomponents() {
		return this.applicationcomponents;
	}

	public void setApplicationcomponents(List<Applicationcomponent> applicationcomponents) {
		this.applicationcomponents = applicationcomponents;
	}

	public Applicationcomponent addApplicationcomponent(Applicationcomponent applicationcomponent) {
		getApplicationcomponents().add(applicationcomponent);
		applicationcomponent.setEnrollmentstep(this);

		return applicationcomponent;
	}

	public Applicationcomponent removeApplicationcomponent(Applicationcomponent applicationcomponent) {
		getApplicationcomponents().remove(applicationcomponent);
		applicationcomponent.setEnrollmentstep(null);

		return applicationcomponent;
	}

	public List<Enrollmentworkflow> getEnrollmentworkflows1() {
		return this.enrollmentworkflows1;
	}

	public void setEnrollmentworkflows1(List<Enrollmentworkflow> enrollmentworkflows1) {
		this.enrollmentworkflows1 = enrollmentworkflows1;
	}

	public Enrollmentworkflow addEnrollmentworkflows1(Enrollmentworkflow enrollmentworkflows1) {
		getEnrollmentworkflows1().add(enrollmentworkflows1);
		enrollmentworkflows1.setEnrollmentstep1(this);

		return enrollmentworkflows1;
	}

	public Enrollmentworkflow removeEnrollmentworkflows1(Enrollmentworkflow enrollmentworkflows1) {
		getEnrollmentworkflows1().remove(enrollmentworkflows1);
		enrollmentworkflows1.setEnrollmentstep1(null);

		return enrollmentworkflows1;
	}

	public List<Enrollmentworkflow> getEnrollmentworkflows2() {
		return this.enrollmentworkflows2;
	}

	public void setEnrollmentworkflows2(List<Enrollmentworkflow> enrollmentworkflows2) {
		this.enrollmentworkflows2 = enrollmentworkflows2;
	}

	public Enrollmentworkflow addEnrollmentworkflows2(Enrollmentworkflow enrollmentworkflows2) {
		getEnrollmentworkflows2().add(enrollmentworkflows2);
		enrollmentworkflows2.setEnrollmentstep2(this);

		return enrollmentworkflows2;
	}

	public Enrollmentworkflow removeEnrollmentworkflows2(Enrollmentworkflow enrollmentworkflows2) {
		getEnrollmentworkflows2().remove(enrollmentworkflows2);
		enrollmentworkflows2.setEnrollmentstep2(null);

		return enrollmentworkflows2;
	}

}