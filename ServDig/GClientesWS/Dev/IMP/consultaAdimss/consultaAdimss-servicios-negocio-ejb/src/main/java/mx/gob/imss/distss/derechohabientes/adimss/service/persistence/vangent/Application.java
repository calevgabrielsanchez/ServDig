package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the APPLICATIONS database table.
 * 
 */
@Entity
@Table(name="APPLICATIONS")
@NamedQuery(name="Application.findAll", query="SELECT a FROM Application a")
public class Application implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idapplications;

	private String applicationcategory;

	private String applicationdescription;

	private String applicationname;

	@Temporal(TemporalType.DATE)
	private Date createdate;

	private String createuser;

	@Temporal(TemporalType.DATE)
	private Date updatedate;

	private String updateuser;

	//bi-directional many-to-one association to Applicationcomponent
	@OneToMany(mappedBy="application")
	private List<Applicationcomponent> applicationcomponents;

	//bi-directional many-to-one association to Auditlogdetail
	@OneToMany(mappedBy="application")
	private List<Auditlogdetail> auditlogdetails;

	//bi-directional many-to-one association to Enrollmentdevice
	@OneToMany(mappedBy="application")
	private List<Enrollmentdevice> enrollmentdevices;

	//bi-directional many-to-one association to Parameter
	@OneToMany(mappedBy="application")
	private List<Parameter> parameters;

	public Application() {
	}

	public long getIdapplications() {
		return this.idapplications;
	}

	public void setIdapplications(long idapplications) {
		this.idapplications = idapplications;
	}

	public String getApplicationcategory() {
		return this.applicationcategory;
	}

	public void setApplicationcategory(String applicationcategory) {
		this.applicationcategory = applicationcategory;
	}

	public String getApplicationdescription() {
		return this.applicationdescription;
	}

	public void setApplicationdescription(String applicationdescription) {
		this.applicationdescription = applicationdescription;
	}

	public String getApplicationname() {
		return this.applicationname;
	}

	public void setApplicationname(String applicationname) {
		this.applicationname = applicationname;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getCreateuser() {
		return this.createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdateuser() {
		return this.updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public List<Applicationcomponent> getApplicationcomponents() {
		return this.applicationcomponents;
	}

	public void setApplicationcomponents(List<Applicationcomponent> applicationcomponents) {
		this.applicationcomponents = applicationcomponents;
	}

	public Applicationcomponent addApplicationcomponent(Applicationcomponent applicationcomponent) {
		getApplicationcomponents().add(applicationcomponent);
		applicationcomponent.setApplication(this);

		return applicationcomponent;
	}

	public Applicationcomponent removeApplicationcomponent(Applicationcomponent applicationcomponent) {
		getApplicationcomponents().remove(applicationcomponent);
		applicationcomponent.setApplication(null);

		return applicationcomponent;
	}

	public List<Auditlogdetail> getAuditlogdetails() {
		return this.auditlogdetails;
	}

	public void setAuditlogdetails(List<Auditlogdetail> auditlogdetails) {
		this.auditlogdetails = auditlogdetails;
	}

	public Auditlogdetail addAuditlogdetail(Auditlogdetail auditlogdetail) {
		getAuditlogdetails().add(auditlogdetail);
		auditlogdetail.setApplication(this);

		return auditlogdetail;
	}

	public Auditlogdetail removeAuditlogdetail(Auditlogdetail auditlogdetail) {
		getAuditlogdetails().remove(auditlogdetail);
		auditlogdetail.setApplication(null);

		return auditlogdetail;
	}

	public List<Enrollmentdevice> getEnrollmentdevices() {
		return this.enrollmentdevices;
	}

	public void setEnrollmentdevices(List<Enrollmentdevice> enrollmentdevices) {
		this.enrollmentdevices = enrollmentdevices;
	}

	public Enrollmentdevice addEnrollmentdevice(Enrollmentdevice enrollmentdevice) {
		getEnrollmentdevices().add(enrollmentdevice);
		enrollmentdevice.setApplication(this);

		return enrollmentdevice;
	}

	public Enrollmentdevice removeEnrollmentdevice(Enrollmentdevice enrollmentdevice) {
		getEnrollmentdevices().remove(enrollmentdevice);
		enrollmentdevice.setApplication(null);

		return enrollmentdevice;
	}

	public List<Parameter> getParameters() {
		return this.parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public Parameter addParameter(Parameter parameter) {
		getParameters().add(parameter);
		parameter.setApplication(this);

		return parameter;
	}

	public Parameter removeParameter(Parameter parameter) {
		getParameters().remove(parameter);
		parameter.setApplication(null);

		return parameter;
	}

}