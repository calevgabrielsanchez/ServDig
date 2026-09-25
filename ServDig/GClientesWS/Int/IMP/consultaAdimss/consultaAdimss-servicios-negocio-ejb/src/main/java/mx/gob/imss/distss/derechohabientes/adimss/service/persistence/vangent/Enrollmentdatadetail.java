package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTDATADETAILS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTDATADETAILS")
@NamedQuery(name="Enrollmentdatadetail.findAll", query="SELECT e FROM Enrollmentdatadetail e")
public class Enrollmentdatadetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentdatadetailPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal enroldatalength;

	private BigDecimal enroldataquality;

	private String enroldatavalue;

	private String enrolkeydata;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentdatatype
	@ManyToOne
	@JoinColumn(name="IDENROLDATATYPE")
	private Enrollmentdatatype enrollmentdatatype;

	//bi-directional many-to-one association to Enrollment
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROL", referencedColumnName="IDENROL"),
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION")
		})
	private Enrollment enrollment;

	public Enrollmentdatadetail() {
	}

	public EnrollmentdatadetailPK getId() {
		return this.id;
	}

	public void setId(EnrollmentdatadetailPK id) {
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

	public BigDecimal getEnroldatalength() {
		return this.enroldatalength;
	}

	public void setEnroldatalength(BigDecimal enroldatalength) {
		this.enroldatalength = enroldatalength;
	}

	public BigDecimal getEnroldataquality() {
		return this.enroldataquality;
	}

	public void setEnroldataquality(BigDecimal enroldataquality) {
		this.enroldataquality = enroldataquality;
	}

	public String getEnroldatavalue() {
		return this.enroldatavalue;
	}

	public void setEnroldatavalue(String enroldatavalue) {
		this.enroldatavalue = enroldatavalue;
	}

	public String getEnrolkeydata() {
		return this.enrolkeydata;
	}

	public void setEnrolkeydata(String enrolkeydata) {
		this.enrolkeydata = enrolkeydata;
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

	public Enrollmentdatatype getEnrollmentdatatype() {
		return this.enrollmentdatatype;
	}

	public void setEnrollmentdatatype(Enrollmentdatatype enrollmentdatatype) {
		this.enrollmentdatatype = enrollmentdatatype;
	}

	public Enrollment getEnrollment() {
		return this.enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

}