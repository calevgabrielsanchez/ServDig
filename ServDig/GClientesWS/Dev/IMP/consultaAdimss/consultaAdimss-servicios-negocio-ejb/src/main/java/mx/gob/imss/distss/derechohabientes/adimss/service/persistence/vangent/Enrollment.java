package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTS")
@NamedQuery(name="Enrollment.findAll", query="SELECT e FROM Enrollment e")
public class Enrollment implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date enroldate;

	private BigDecimal idenrolsequence;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Documentuminterface
	@OneToMany(mappedBy="enrollment")
	private List<Documentuminterface> documentuminterfaces;

	//bi-directional many-to-one association to Documentuminterfacesclon
	@OneToMany(mappedBy="enrollment")
	private List<Documentuminterfacesclon> documentuminterfacesclons;

	//bi-directional many-to-one association to Enrollmentbilling
	@OneToMany(mappedBy="enrollment")
	private List<Enrollmentbilling> enrollmentbillings;

	//bi-directional many-to-one association to Enrollmentdatadetail
	@OneToMany(mappedBy="enrollment")
	private List<Enrollmentdatadetail> enrollmentdatadetails;

	//bi-directional many-to-one association to Enrollmentdatum
	@OneToMany(mappedBy="enrollment")
	private List<Enrollmentdatum> enrollmentdatums;

	//bi-directional many-to-one association to Enrollmentdetail
	@OneToMany(mappedBy="enrollment")
	private List<Enrollmentdetail> enrollmentdetails;

	//bi-directional many-to-one association to Enrollmentflow
	@OneToMany(mappedBy="enrollment")
	private List<Enrollmentflow> enrollmentflows;

	//bi-directional many-to-one association to Enrollmentimagedetail
	@OneToMany(mappedBy="enrollment")
	private List<Enrollmentimagedetail> enrollmentimagedetails;

	//bi-directional many-to-one association to Enrollmentmode
	@ManyToOne
	@JoinColumn(name="IDENROLMODE")
	private Enrollmentmode enrollmentmode;

	//bi-directional many-to-one association to Enrollmentstate
	@ManyToOne
	@JoinColumn(name="IDENROLSTATE")
	private Enrollmentstate enrollmentstate;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	//bi-directional many-to-one association to Enrollmentsubtype
	@ManyToOne
	@JoinColumn(name="IDENROLSUBTYPE")
	private Enrollmentsubtype enrollmentsubtype;

	//bi-directional many-to-one association to Enrollmenttype
	@ManyToOne
	@JoinColumn(name="IDENROLTYPE")
	private Enrollmenttype enrollmenttype;

	//bi-directional many-to-one association to Enrollmentturn
	@OneToMany(mappedBy="enrollment")
	private List<Enrollmentturn> enrollmentturns;

	public Enrollment() {
	}

	public EnrollmentPK getId() {
		return this.id;
	}

	public void setId(EnrollmentPK id) {
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

	public BigDecimal getIdenrolsequence() {
		return this.idenrolsequence;
	}

	public void setIdenrolsequence(BigDecimal idenrolsequence) {
		this.idenrolsequence = idenrolsequence;
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

	public List<Documentuminterface> getDocumentuminterfaces() {
		return this.documentuminterfaces;
	}

	public void setDocumentuminterfaces(List<Documentuminterface> documentuminterfaces) {
		this.documentuminterfaces = documentuminterfaces;
	}

	public Documentuminterface addDocumentuminterface(Documentuminterface documentuminterface) {
		getDocumentuminterfaces().add(documentuminterface);
		documentuminterface.setEnrollment(this);

		return documentuminterface;
	}

	public Documentuminterface removeDocumentuminterface(Documentuminterface documentuminterface) {
		getDocumentuminterfaces().remove(documentuminterface);
		documentuminterface.setEnrollment(null);

		return documentuminterface;
	}

	public List<Documentuminterfacesclon> getDocumentuminterfacesclons() {
		return this.documentuminterfacesclons;
	}

	public void setDocumentuminterfacesclons(List<Documentuminterfacesclon> documentuminterfacesclons) {
		this.documentuminterfacesclons = documentuminterfacesclons;
	}

	public Documentuminterfacesclon addDocumentuminterfacesclon(Documentuminterfacesclon documentuminterfacesclon) {
		getDocumentuminterfacesclons().add(documentuminterfacesclon);
		documentuminterfacesclon.setEnrollment(this);

		return documentuminterfacesclon;
	}

	public Documentuminterfacesclon removeDocumentuminterfacesclon(Documentuminterfacesclon documentuminterfacesclon) {
		getDocumentuminterfacesclons().remove(documentuminterfacesclon);
		documentuminterfacesclon.setEnrollment(null);

		return documentuminterfacesclon;
	}

	public List<Enrollmentbilling> getEnrollmentbillings() {
		return this.enrollmentbillings;
	}

	public void setEnrollmentbillings(List<Enrollmentbilling> enrollmentbillings) {
		this.enrollmentbillings = enrollmentbillings;
	}

	public Enrollmentbilling addEnrollmentbilling(Enrollmentbilling enrollmentbilling) {
		getEnrollmentbillings().add(enrollmentbilling);
		enrollmentbilling.setEnrollment(this);

		return enrollmentbilling;
	}

	public Enrollmentbilling removeEnrollmentbilling(Enrollmentbilling enrollmentbilling) {
		getEnrollmentbillings().remove(enrollmentbilling);
		enrollmentbilling.setEnrollment(null);

		return enrollmentbilling;
	}

	public List<Enrollmentdatadetail> getEnrollmentdatadetails() {
		return this.enrollmentdatadetails;
	}

	public void setEnrollmentdatadetails(List<Enrollmentdatadetail> enrollmentdatadetails) {
		this.enrollmentdatadetails = enrollmentdatadetails;
	}

	public Enrollmentdatadetail addEnrollmentdatadetail(Enrollmentdatadetail enrollmentdatadetail) {
		getEnrollmentdatadetails().add(enrollmentdatadetail);
		enrollmentdatadetail.setEnrollment(this);

		return enrollmentdatadetail;
	}

	public Enrollmentdatadetail removeEnrollmentdatadetail(Enrollmentdatadetail enrollmentdatadetail) {
		getEnrollmentdatadetails().remove(enrollmentdatadetail);
		enrollmentdatadetail.setEnrollment(null);

		return enrollmentdatadetail;
	}

	public List<Enrollmentdatum> getEnrollmentdatums() {
		return this.enrollmentdatums;
	}

	public void setEnrollmentdatums(List<Enrollmentdatum> enrollmentdatums) {
		this.enrollmentdatums = enrollmentdatums;
	}

	public Enrollmentdatum addEnrollmentdatum(Enrollmentdatum enrollmentdatum) {
		getEnrollmentdatums().add(enrollmentdatum);
		enrollmentdatum.setEnrollment(this);

		return enrollmentdatum;
	}

	public Enrollmentdatum removeEnrollmentdatum(Enrollmentdatum enrollmentdatum) {
		getEnrollmentdatums().remove(enrollmentdatum);
		enrollmentdatum.setEnrollment(null);

		return enrollmentdatum;
	}

	public List<Enrollmentdetail> getEnrollmentdetails() {
		return this.enrollmentdetails;
	}

	public void setEnrollmentdetails(List<Enrollmentdetail> enrollmentdetails) {
		this.enrollmentdetails = enrollmentdetails;
	}

	public Enrollmentdetail addEnrollmentdetail(Enrollmentdetail enrollmentdetail) {
		getEnrollmentdetails().add(enrollmentdetail);
		enrollmentdetail.setEnrollment(this);

		return enrollmentdetail;
	}

	public Enrollmentdetail removeEnrollmentdetail(Enrollmentdetail enrollmentdetail) {
		getEnrollmentdetails().remove(enrollmentdetail);
		enrollmentdetail.setEnrollment(null);

		return enrollmentdetail;
	}

	public List<Enrollmentflow> getEnrollmentflows() {
		return this.enrollmentflows;
	}

	public void setEnrollmentflows(List<Enrollmentflow> enrollmentflows) {
		this.enrollmentflows = enrollmentflows;
	}

	public Enrollmentflow addEnrollmentflow(Enrollmentflow enrollmentflow) {
		getEnrollmentflows().add(enrollmentflow);
		enrollmentflow.setEnrollment(this);

		return enrollmentflow;
	}

	public Enrollmentflow removeEnrollmentflow(Enrollmentflow enrollmentflow) {
		getEnrollmentflows().remove(enrollmentflow);
		enrollmentflow.setEnrollment(null);

		return enrollmentflow;
	}

	public List<Enrollmentimagedetail> getEnrollmentimagedetails() {
		return this.enrollmentimagedetails;
	}

	public void setEnrollmentimagedetails(List<Enrollmentimagedetail> enrollmentimagedetails) {
		this.enrollmentimagedetails = enrollmentimagedetails;
	}

	public Enrollmentimagedetail addEnrollmentimagedetail(Enrollmentimagedetail enrollmentimagedetail) {
		getEnrollmentimagedetails().add(enrollmentimagedetail);
		enrollmentimagedetail.setEnrollment(this);

		return enrollmentimagedetail;
	}

	public Enrollmentimagedetail removeEnrollmentimagedetail(Enrollmentimagedetail enrollmentimagedetail) {
		getEnrollmentimagedetails().remove(enrollmentimagedetail);
		enrollmentimagedetail.setEnrollment(null);

		return enrollmentimagedetail;
	}

	public Enrollmentmode getEnrollmentmode() {
		return this.enrollmentmode;
	}

	public void setEnrollmentmode(Enrollmentmode enrollmentmode) {
		this.enrollmentmode = enrollmentmode;
	}

	public Enrollmentstate getEnrollmentstate() {
		return this.enrollmentstate;
	}

	public void setEnrollmentstate(Enrollmentstate enrollmentstate) {
		this.enrollmentstate = enrollmentstate;
	}

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

	public Enrollmentsubtype getEnrollmentsubtype() {
		return this.enrollmentsubtype;
	}

	public void setEnrollmentsubtype(Enrollmentsubtype enrollmentsubtype) {
		this.enrollmentsubtype = enrollmentsubtype;
	}

	public Enrollmenttype getEnrollmenttype() {
		return this.enrollmenttype;
	}

	public void setEnrollmenttype(Enrollmenttype enrollmenttype) {
		this.enrollmenttype = enrollmenttype;
	}

	public List<Enrollmentturn> getEnrollmentturns() {
		return this.enrollmentturns;
	}

	public void setEnrollmentturns(List<Enrollmentturn> enrollmentturns) {
		this.enrollmentturns = enrollmentturns;
	}

	public Enrollmentturn addEnrollmentturn(Enrollmentturn enrollmentturn) {
		getEnrollmentturns().add(enrollmentturn);
		enrollmentturn.setEnrollment(this);

		return enrollmentturn;
	}

	public Enrollmentturn removeEnrollmentturn(Enrollmentturn enrollmentturn) {
		getEnrollmentturns().remove(enrollmentturn);
		enrollmentturn.setEnrollment(null);

		return enrollmentturn;
	}

}