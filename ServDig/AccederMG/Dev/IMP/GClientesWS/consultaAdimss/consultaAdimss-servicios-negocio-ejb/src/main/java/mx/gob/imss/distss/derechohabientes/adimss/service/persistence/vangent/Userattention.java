package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the USERATTENTIONS database table.
 * 
 */
@Entity
@Table(name="USERATTENTIONS")
@NamedQuery(name="Userattention.findAll", query="SELECT u FROM Userattention u")
public class Userattention implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long iduserattention;

	private String attentioncomments;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="CVE_CALIDAD_ASEG")
	private BigDecimal cveCalidadAseg;

	private BigDecimal idturn;

	private String nss;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	//bi-directional many-to-one association to Enrollmenttype
	@ManyToOne
	@JoinColumn(name="IDENROLTYPE")
	private Enrollmenttype enrollmenttype;

	//bi-directional many-to-one association to Tipoenrolamiento
	@ManyToOne
	@JoinColumn(name="IDTIPOENROLAMIENTO")
	private Tipoenrolamiento tipoenrolamiento;

	public Userattention() {
	}

	public long getIduserattention() {
		return this.iduserattention;
	}

	public void setIduserattention(long iduserattention) {
		this.iduserattention = iduserattention;
	}

	public String getAttentioncomments() {
		return this.attentioncomments;
	}

	public void setAttentioncomments(String attentioncomments) {
		this.attentioncomments = attentioncomments;
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

	public BigDecimal getCveCalidadAseg() {
		return this.cveCalidadAseg;
	}

	public void setCveCalidadAseg(BigDecimal cveCalidadAseg) {
		this.cveCalidadAseg = cveCalidadAseg;
	}

	public BigDecimal getIdturn() {
		return this.idturn;
	}

	public void setIdturn(BigDecimal idturn) {
		this.idturn = idturn;
	}

	public String getNss() {
		return this.nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
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

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

	public Enrollmenttype getEnrollmenttype() {
		return this.enrollmenttype;
	}

	public void setEnrollmenttype(Enrollmenttype enrollmenttype) {
		this.enrollmenttype = enrollmenttype;
	}

	public Tipoenrolamiento getTipoenrolamiento() {
		return this.tipoenrolamiento;
	}

	public void setTipoenrolamiento(Tipoenrolamiento tipoenrolamiento) {
		this.tipoenrolamiento = tipoenrolamiento;
	}

}