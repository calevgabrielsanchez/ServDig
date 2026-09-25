package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SCYSENROLLMENTS database table.
 * 
 */
@Entity
@Table(name="SCYSENROLLMENTS")
@NamedQuery(name="Scysenrollment.findAll", query="SELECT s FROM Scysenrollment s")
public class Scysenrollment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idscysenrollments;

	private String comments;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date enrolmentdate;

	private BigDecimal enroltotal;

	@Temporal(TemporalType.DATE)
	private Date updatedate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private BigDecimal wastedcards;

	private BigDecimal wastedpaper;

	private BigDecimal withoutenrol;

	//bi-directional many-to-one association to Umf
	@ManyToOne
	@JoinColumn(name="IDUMF")
	private Umf umf;

	public Scysenrollment() {
	}

	public long getIdscysenrollments() {
		return this.idscysenrollments;
	}

	public void setIdscysenrollments(long idscysenrollments) {
		this.idscysenrollments = idscysenrollments;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Date getEnrolmentdate() {
		return this.enrolmentdate;
	}

	public void setEnrolmentdate(Date enrolmentdate) {
		this.enrolmentdate = enrolmentdate;
	}

	public BigDecimal getEnroltotal() {
		return this.enroltotal;
	}

	public void setEnroltotal(BigDecimal enroltotal) {
		this.enroltotal = enroltotal;
	}

	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
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

	public BigDecimal getWastedcards() {
		return this.wastedcards;
	}

	public void setWastedcards(BigDecimal wastedcards) {
		this.wastedcards = wastedcards;
	}

	public BigDecimal getWastedpaper() {
		return this.wastedpaper;
	}

	public void setWastedpaper(BigDecimal wastedpaper) {
		this.wastedpaper = wastedpaper;
	}

	public BigDecimal getWithoutenrol() {
		return this.withoutenrol;
	}

	public void setWithoutenrol(BigDecimal withoutenrol) {
		this.withoutenrol = withoutenrol;
	}

	public Umf getUmf() {
		return this.umf;
	}

	public void setUmf(Umf umf) {
		this.umf = umf;
	}

}