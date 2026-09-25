package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTIMAGEDETAILS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTIMAGEDETAILS")
@NamedQuery(name="Enrollmentimagedetail.findAll", query="SELECT e FROM Enrollmentimagedetail e")
public class Enrollmentimagedetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentimagedetailPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal imgattempts;

	private String imgdepth;

	private String imgformat;

	private String imgobject;

	private BigDecimal imgquality;

	private String imgresolution;

	private BigDecimal imgscore;

	private String imgsize;

	private String imgstatus;

	private BigDecimal objsize;

	private BigDecimal scoredby;

	@Temporal(TemporalType.DATE)
	private Date scoredon;

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

	//bi-directional many-to-one association to Imagetype
	@ManyToOne
	@JoinColumn(name="IDIMGTYPE")
	private Imagetype imagetype;

	//bi-directional many-to-one association to Enrollmentimageicaovalue
	@OneToMany(mappedBy="enrollmentimagedetail")
	private List<Enrollmentimageicaovalue> enrollmentimageicaovalues;

	public Enrollmentimagedetail() {
	}

	public EnrollmentimagedetailPK getId() {
		return this.id;
	}

	public void setId(EnrollmentimagedetailPK id) {
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

	public BigDecimal getImgattempts() {
		return this.imgattempts;
	}

	public void setImgattempts(BigDecimal imgattempts) {
		this.imgattempts = imgattempts;
	}

	public String getImgdepth() {
		return this.imgdepth;
	}

	public void setImgdepth(String imgdepth) {
		this.imgdepth = imgdepth;
	}

	public String getImgformat() {
		return this.imgformat;
	}

	public void setImgformat(String imgformat) {
		this.imgformat = imgformat;
	}

	public String getImgobject() {
		return this.imgobject;
	}

	public void setImgobject(String imgobject) {
		this.imgobject = imgobject;
	}

	public BigDecimal getImgquality() {
		return this.imgquality;
	}

	public void setImgquality(BigDecimal imgquality) {
		this.imgquality = imgquality;
	}

	public String getImgresolution() {
		return this.imgresolution;
	}

	public void setImgresolution(String imgresolution) {
		this.imgresolution = imgresolution;
	}

	public BigDecimal getImgscore() {
		return this.imgscore;
	}

	public void setImgscore(BigDecimal imgscore) {
		this.imgscore = imgscore;
	}

	public String getImgsize() {
		return this.imgsize;
	}

	public void setImgsize(String imgsize) {
		this.imgsize = imgsize;
	}

	public String getImgstatus() {
		return this.imgstatus;
	}

	public void setImgstatus(String imgstatus) {
		this.imgstatus = imgstatus;
	}

	public BigDecimal getObjsize() {
		return this.objsize;
	}

	public void setObjsize(BigDecimal objsize) {
		this.objsize = objsize;
	}

	public BigDecimal getScoredby() {
		return this.scoredby;
	}

	public void setScoredby(BigDecimal scoredby) {
		this.scoredby = scoredby;
	}

	public Date getScoredon() {
		return this.scoredon;
	}

	public void setScoredon(Date scoredon) {
		this.scoredon = scoredon;
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

	public Imagetype getImagetype() {
		return this.imagetype;
	}

	public void setImagetype(Imagetype imagetype) {
		this.imagetype = imagetype;
	}

	public List<Enrollmentimageicaovalue> getEnrollmentimageicaovalues() {
		return this.enrollmentimageicaovalues;
	}

	public void setEnrollmentimageicaovalues(List<Enrollmentimageicaovalue> enrollmentimageicaovalues) {
		this.enrollmentimageicaovalues = enrollmentimageicaovalues;
	}

	public Enrollmentimageicaovalue addEnrollmentimageicaovalue(Enrollmentimageicaovalue enrollmentimageicaovalue) {
		getEnrollmentimageicaovalues().add(enrollmentimageicaovalue);
		enrollmentimageicaovalue.setEnrollmentimagedetail(this);

		return enrollmentimageicaovalue;
	}

	public Enrollmentimageicaovalue removeEnrollmentimageicaovalue(Enrollmentimageicaovalue enrollmentimageicaovalue) {
		getEnrollmentimageicaovalues().remove(enrollmentimageicaovalue);
		enrollmentimageicaovalue.setEnrollmentimagedetail(null);

		return enrollmentimageicaovalue;
	}

}