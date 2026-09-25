package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTTURNS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTTURNS")
@NamedQuery(name="Enrollmentturn.findAll", query="SELECT e FROM Enrollmentturn e")
public class Enrollmentturn implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentturnPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal currentstatus;

	@Column(name="CVE_CALIDAD_ASEG")
	private BigDecimal cveCalidadAseg;

	private String ensuretname;

//	private Object finaltime;

	private String foliosolicitud;

	private BigDecimal idtipoenrolamiento;

//	private Object initialtime;

	private String nss;

	private BigDecimal secondstowait;

//	private Object timewait;

	private BigDecimal turn;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollment
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROL", referencedColumnName="IDENROL"),
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION")
		})
	private Enrollment enrollment;

	public Enrollmentturn() {
	}

	public EnrollmentturnPK getId() {
		return this.id;
	}

	public void setId(EnrollmentturnPK id) {
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

	public BigDecimal getCurrentstatus() {
		return this.currentstatus;
	}

	public void setCurrentstatus(BigDecimal currentstatus) {
		this.currentstatus = currentstatus;
	}

	public BigDecimal getCveCalidadAseg() {
		return this.cveCalidadAseg;
	}

	public void setCveCalidadAseg(BigDecimal cveCalidadAseg) {
		this.cveCalidadAseg = cveCalidadAseg;
	}

	public String getEnsuretname() {
		return this.ensuretname;
	}

	public void setEnsuretname(String ensuretname) {
		this.ensuretname = ensuretname;
	}

//	public Object getFinaltime() {
//		return this.finaltime;
//	}
//
//	public void setFinaltime(Object finaltime) {
//		this.finaltime = finaltime;
//	}

	public String getFoliosolicitud() {
		return this.foliosolicitud;
	}

	public void setFoliosolicitud(String foliosolicitud) {
		this.foliosolicitud = foliosolicitud;
	}

	public BigDecimal getIdtipoenrolamiento() {
		return this.idtipoenrolamiento;
	}

	public void setIdtipoenrolamiento(BigDecimal idtipoenrolamiento) {
		this.idtipoenrolamiento = idtipoenrolamiento;
	}

//	public Object getInitialtime() {
//		return this.initialtime;
//	}
//
//	public void setInitialtime(Object initialtime) {
//		this.initialtime = initialtime;
//	}

	public String getNss() {
		return this.nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public BigDecimal getSecondstowait() {
		return this.secondstowait;
	}

	public void setSecondstowait(BigDecimal secondstowait) {
		this.secondstowait = secondstowait;
	}

//	public Object getTimewait() {
//		return this.timewait;
//	}
//
//	public void setTimewait(Object timewait) {
//		this.timewait = timewait;
//	}

	public BigDecimal getTurn() {
		return this.turn;
	}

	public void setTurn(BigDecimal turn) {
		this.turn = turn;
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

	public Enrollment getEnrollment() {
		return this.enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

}