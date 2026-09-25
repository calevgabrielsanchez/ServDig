package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the COMPANYMANAGER database table.
 * 
 */
@Entity
@NamedQuery(name="Companymanager.findAll", query="SELECT c FROM Companymanager c")
public class Companymanager implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idcompanymanager;

	private String address;

	private String companymanagerdescription;

	private String companypass;

	private String companyuser;

	private String contact;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String email;

	private String phonenumber;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Companycelife
	@OneToMany(mappedBy="companymanager")
	private List<Companycelife> companycelifes;

	//bi-directional many-to-one association to State
	@ManyToOne
	@JoinColumn(name="IDSTATE")
	private State state;

	//bi-directional many-to-one association to Enrollmentcenter
	@OneToMany(mappedBy="companymanager")
	private List<Enrollmentcenter> enrollmentcenters;

	//bi-directional many-to-one association to Enrollperformancecom
	@OneToMany(mappedBy="companymanager")
	private List<Enrollperformancecom> enrollperformancecoms;

	public Companymanager() {
	}

	public long getIdcompanymanager() {
		return this.idcompanymanager;
	}

	public void setIdcompanymanager(long idcompanymanager) {
		this.idcompanymanager = idcompanymanager;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanymanagerdescription() {
		return this.companymanagerdescription;
	}

	public void setCompanymanagerdescription(String companymanagerdescription) {
		this.companymanagerdescription = companymanagerdescription;
	}

	public String getCompanypass() {
		return this.companypass;
	}

	public void setCompanypass(String companypass) {
		this.companypass = companypass;
	}

	public String getCompanyuser() {
		return this.companyuser;
	}

	public void setCompanyuser(String companyuser) {
		this.companyuser = companyuser;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
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

	public List<Companycelife> getCompanycelifes() {
		return this.companycelifes;
	}

	public void setCompanycelifes(List<Companycelife> companycelifes) {
		this.companycelifes = companycelifes;
	}

	public Companycelife addCompanycelife(Companycelife companycelife) {
		getCompanycelifes().add(companycelife);
		companycelife.setCompanymanager(this);

		return companycelife;
	}

	public Companycelife removeCompanycelife(Companycelife companycelife) {
		getCompanycelifes().remove(companycelife);
		companycelife.setCompanymanager(null);

		return companycelife;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Enrollmentcenter> getEnrollmentcenters() {
		return this.enrollmentcenters;
	}

	public void setEnrollmentcenters(List<Enrollmentcenter> enrollmentcenters) {
		this.enrollmentcenters = enrollmentcenters;
	}

	public Enrollmentcenter addEnrollmentcenter(Enrollmentcenter enrollmentcenter) {
		getEnrollmentcenters().add(enrollmentcenter);
		enrollmentcenter.setCompanymanager(this);

		return enrollmentcenter;
	}

	public Enrollmentcenter removeEnrollmentcenter(Enrollmentcenter enrollmentcenter) {
		getEnrollmentcenters().remove(enrollmentcenter);
		enrollmentcenter.setCompanymanager(null);

		return enrollmentcenter;
	}

	public List<Enrollperformancecom> getEnrollperformancecoms() {
		return this.enrollperformancecoms;
	}

	public void setEnrollperformancecoms(List<Enrollperformancecom> enrollperformancecoms) {
		this.enrollperformancecoms = enrollperformancecoms;
	}

	public Enrollperformancecom addEnrollperformancecom(Enrollperformancecom enrollperformancecom) {
		getEnrollperformancecoms().add(enrollperformancecom);
		enrollperformancecom.setCompanymanager(this);

		return enrollperformancecom;
	}

	public Enrollperformancecom removeEnrollperformancecom(Enrollperformancecom enrollperformancecom) {
		getEnrollperformancecoms().remove(enrollperformancecom);
		enrollperformancecom.setCompanymanager(null);

		return enrollperformancecom;
	}

}