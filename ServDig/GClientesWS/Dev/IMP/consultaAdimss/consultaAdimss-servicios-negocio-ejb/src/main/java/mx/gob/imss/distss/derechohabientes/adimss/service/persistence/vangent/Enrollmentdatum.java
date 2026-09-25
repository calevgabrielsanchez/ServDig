package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTDATUM database table.
 * 
 */
@Entity
@NamedQuery(name="Enrollmentdatum.findAll", query="SELECT e FROM Enrollmentdatum e")
public class Enrollmentdatum implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentdatumPK id;

	private String createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="ENROLBIRTHCERT_NUMBER")
	private BigDecimal enrolbirthcertNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="ENROLBIRTHCERT_REGDATE")
	private Date enrolbirthcertRegdate;

	@Column(name="ENROLBIRTHCERT_SHEET")
	private BigDecimal enrolbirthcertSheet;

	private String enrolcurp;

	@Column(name="ENROLCUSTDOC_NUMBER")
	private BigDecimal enrolcustdocNumber;

	private String enrolkey;

	private String enrolname;

	@Column(name="ENROLNATCERTIFICATE_NUMBER")
	private BigDecimal enrolnatcertificateNumber;

	@Column(name="ENROLNATCERTIFICATE_YEAR")
	private BigDecimal enrolnatcertificateYear;

	@Column(name="ENROLNATLETTER_NUMBER")
	private BigDecimal enrolnatletterNumber;

	@Column(name="ENROLNATLETTER_YEAR")
	private BigDecimal enrolnatletterYear;

	private String enrolstatus;

	private BigDecimal updateby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollment
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROL", referencedColumnName="IDENROL"),
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION")
		})
	private Enrollment enrollment;

	public Enrollmentdatum() {
	}

	public EnrollmentdatumPK getId() {
		return this.id;
	}

	public void setId(EnrollmentdatumPK id) {
		this.id = id;
	}

	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getEnrolbirthcertNumber() {
		return this.enrolbirthcertNumber;
	}

	public void setEnrolbirthcertNumber(BigDecimal enrolbirthcertNumber) {
		this.enrolbirthcertNumber = enrolbirthcertNumber;
	}

	public Date getEnrolbirthcertRegdate() {
		return this.enrolbirthcertRegdate;
	}

	public void setEnrolbirthcertRegdate(Date enrolbirthcertRegdate) {
		this.enrolbirthcertRegdate = enrolbirthcertRegdate;
	}

	public BigDecimal getEnrolbirthcertSheet() {
		return this.enrolbirthcertSheet;
	}

	public void setEnrolbirthcertSheet(BigDecimal enrolbirthcertSheet) {
		this.enrolbirthcertSheet = enrolbirthcertSheet;
	}

	public String getEnrolcurp() {
		return this.enrolcurp;
	}

	public void setEnrolcurp(String enrolcurp) {
		this.enrolcurp = enrolcurp;
	}

	public BigDecimal getEnrolcustdocNumber() {
		return this.enrolcustdocNumber;
	}

	public void setEnrolcustdocNumber(BigDecimal enrolcustdocNumber) {
		this.enrolcustdocNumber = enrolcustdocNumber;
	}

	public String getEnrolkey() {
		return this.enrolkey;
	}

	public void setEnrolkey(String enrolkey) {
		this.enrolkey = enrolkey;
	}

	public String getEnrolname() {
		return this.enrolname;
	}

	public void setEnrolname(String enrolname) {
		this.enrolname = enrolname;
	}

	public BigDecimal getEnrolnatcertificateNumber() {
		return this.enrolnatcertificateNumber;
	}

	public void setEnrolnatcertificateNumber(BigDecimal enrolnatcertificateNumber) {
		this.enrolnatcertificateNumber = enrolnatcertificateNumber;
	}

	public BigDecimal getEnrolnatcertificateYear() {
		return this.enrolnatcertificateYear;
	}

	public void setEnrolnatcertificateYear(BigDecimal enrolnatcertificateYear) {
		this.enrolnatcertificateYear = enrolnatcertificateYear;
	}

	public BigDecimal getEnrolnatletterNumber() {
		return this.enrolnatletterNumber;
	}

	public void setEnrolnatletterNumber(BigDecimal enrolnatletterNumber) {
		this.enrolnatletterNumber = enrolnatletterNumber;
	}

	public BigDecimal getEnrolnatletterYear() {
		return this.enrolnatletterYear;
	}

	public void setEnrolnatletterYear(BigDecimal enrolnatletterYear) {
		this.enrolnatletterYear = enrolnatletterYear;
	}

	public String getEnrolstatus() {
		return this.enrolstatus;
	}

	public void setEnrolstatus(String enrolstatus) {
		this.enrolstatus = enrolstatus;
	}

	public BigDecimal getUpdateby() {
		return this.updateby;
	}

	public void setUpdateby(BigDecimal updateby) {
		this.updateby = updateby;
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

}