package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTWORKFLOWS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTWORKFLOWS")
@NamedQuery(name="Enrollmentworkflow.findAll", query="SELECT e FROM Enrollmentworkflow e")
public class Enrollmentworkflow implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentworkflowPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String enrolwfdesc;

	private BigDecimal enrolwfid;

	private String enrolwfname;

	private BigDecimal idenrolstep;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentmode
	@ManyToOne
	@JoinColumn(name="IDENROLMODE")
	private Enrollmentmode enrollmentmode;

	//bi-directional many-to-one association to Enrollmentstep
	@ManyToOne
	@JoinColumn(name="NEXTENROLSTEP")
	private Enrollmentstep enrollmentstep1;

	//bi-directional many-to-one association to Enrollmentstep
	@ManyToOne
	@JoinColumn(name="PREVENROLSTEP")
	private Enrollmentstep enrollmentstep2;

	//bi-directional many-to-one association to Enrollmenttype
	@ManyToOne
	@JoinColumn(name="IDENROLTYPE")
	private Enrollmenttype enrollmenttype;

	public Enrollmentworkflow() {
	}

	public EnrollmentworkflowPK getId() {
		return this.id;
	}

	public void setId(EnrollmentworkflowPK id) {
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

	public String getEnrolwfdesc() {
		return this.enrolwfdesc;
	}

	public void setEnrolwfdesc(String enrolwfdesc) {
		this.enrolwfdesc = enrolwfdesc;
	}

	public BigDecimal getEnrolwfid() {
		return this.enrolwfid;
	}

	public void setEnrolwfid(BigDecimal enrolwfid) {
		this.enrolwfid = enrolwfid;
	}

	public String getEnrolwfname() {
		return this.enrolwfname;
	}

	public void setEnrolwfname(String enrolwfname) {
		this.enrolwfname = enrolwfname;
	}

	public BigDecimal getIdenrolstep() {
		return this.idenrolstep;
	}

	public void setIdenrolstep(BigDecimal idenrolstep) {
		this.idenrolstep = idenrolstep;
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

	public Enrollmentmode getEnrollmentmode() {
		return this.enrollmentmode;
	}

	public void setEnrollmentmode(Enrollmentmode enrollmentmode) {
		this.enrollmentmode = enrollmentmode;
	}

	public Enrollmentstep getEnrollmentstep1() {
		return this.enrollmentstep1;
	}

	public void setEnrollmentstep1(Enrollmentstep enrollmentstep1) {
		this.enrollmentstep1 = enrollmentstep1;
	}

	public Enrollmentstep getEnrollmentstep2() {
		return this.enrollmentstep2;
	}

	public void setEnrollmentstep2(Enrollmentstep enrollmentstep2) {
		this.enrollmentstep2 = enrollmentstep2;
	}

	public Enrollmenttype getEnrollmenttype() {
		return this.enrollmenttype;
	}

	public void setEnrollmenttype(Enrollmenttype enrollmenttype) {
		this.enrollmenttype = enrollmenttype;
	}

}