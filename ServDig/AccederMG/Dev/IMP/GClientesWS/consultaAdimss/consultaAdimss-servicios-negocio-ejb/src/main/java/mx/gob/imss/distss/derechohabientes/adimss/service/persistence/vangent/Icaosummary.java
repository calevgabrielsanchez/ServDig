package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ICAOSUMMARIES database table.
 * 
 */
@Entity
@Table(name="ICAOSUMMARIES")
@NamedQuery(name="Icaosummary.findAll", query="SELECT i FROM Icaosummary i")
public class Icaosummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idicaosummary;

	private BigDecimal brightfund;

	private BigDecimal caraabridged;

	private BigDecimal confidencefacedetection;

	private BigDecimal contrast;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal exposure;

	private BigDecimal eyescreening;

	private BigDecimal eyesopen;

	private BigDecimal facecentered;

	private BigDecimal facefocused;

	private BigDecimal facewithoutmovement;

	private BigDecimal facewithoutshadows;

	private BigDecimal freeglow;

	private BigDecimal frontpose;

	private BigDecimal fundwithoutshadows;

	private BigDecimal generalaverage;

	private BigDecimal headrightsize;

	@Temporal(TemporalType.DATE)
	private Date icaosummarydate;

	private BigDecimal imagewithoutartifactcomp;

	private BigDecimal imagewithoutnoise;

	@Column(name="\"MONTH\"")
	private BigDecimal month;

	private BigDecimal naturalnocolor;

	private BigDecimal overexposure;

	private BigDecimal picturequality;

	private BigDecimal picturetries;

	private BigDecimal rayoflightintheface;

	private BigDecimal shadowsincheek;

	private BigDecimal sunglasses;

	private BigDecimal unentangledimage;

	private BigDecimal uniformillumination;

	private BigDecimal uniformityfund;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	@Column(name="\"YEAR\"")
	private BigDecimal year;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	public Icaosummary() {
	}

	public long getIdicaosummary() {
		return this.idicaosummary;
	}

	public void setIdicaosummary(long idicaosummary) {
		this.idicaosummary = idicaosummary;
	}

	public BigDecimal getBrightfund() {
		return this.brightfund;
	}

	public void setBrightfund(BigDecimal brightfund) {
		this.brightfund = brightfund;
	}

	public BigDecimal getCaraabridged() {
		return this.caraabridged;
	}

	public void setCaraabridged(BigDecimal caraabridged) {
		this.caraabridged = caraabridged;
	}

	public BigDecimal getConfidencefacedetection() {
		return this.confidencefacedetection;
	}

	public void setConfidencefacedetection(BigDecimal confidencefacedetection) {
		this.confidencefacedetection = confidencefacedetection;
	}

	public BigDecimal getContrast() {
		return this.contrast;
	}

	public void setContrast(BigDecimal contrast) {
		this.contrast = contrast;
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

	public BigDecimal getExposure() {
		return this.exposure;
	}

	public void setExposure(BigDecimal exposure) {
		this.exposure = exposure;
	}

	public BigDecimal getEyescreening() {
		return this.eyescreening;
	}

	public void setEyescreening(BigDecimal eyescreening) {
		this.eyescreening = eyescreening;
	}

	public BigDecimal getEyesopen() {
		return this.eyesopen;
	}

	public void setEyesopen(BigDecimal eyesopen) {
		this.eyesopen = eyesopen;
	}

	public BigDecimal getFacecentered() {
		return this.facecentered;
	}

	public void setFacecentered(BigDecimal facecentered) {
		this.facecentered = facecentered;
	}

	public BigDecimal getFacefocused() {
		return this.facefocused;
	}

	public void setFacefocused(BigDecimal facefocused) {
		this.facefocused = facefocused;
	}

	public BigDecimal getFacewithoutmovement() {
		return this.facewithoutmovement;
	}

	public void setFacewithoutmovement(BigDecimal facewithoutmovement) {
		this.facewithoutmovement = facewithoutmovement;
	}

	public BigDecimal getFacewithoutshadows() {
		return this.facewithoutshadows;
	}

	public void setFacewithoutshadows(BigDecimal facewithoutshadows) {
		this.facewithoutshadows = facewithoutshadows;
	}

	public BigDecimal getFreeglow() {
		return this.freeglow;
	}

	public void setFreeglow(BigDecimal freeglow) {
		this.freeglow = freeglow;
	}

	public BigDecimal getFrontpose() {
		return this.frontpose;
	}

	public void setFrontpose(BigDecimal frontpose) {
		this.frontpose = frontpose;
	}

	public BigDecimal getFundwithoutshadows() {
		return this.fundwithoutshadows;
	}

	public void setFundwithoutshadows(BigDecimal fundwithoutshadows) {
		this.fundwithoutshadows = fundwithoutshadows;
	}

	public BigDecimal getGeneralaverage() {
		return this.generalaverage;
	}

	public void setGeneralaverage(BigDecimal generalaverage) {
		this.generalaverage = generalaverage;
	}

	public BigDecimal getHeadrightsize() {
		return this.headrightsize;
	}

	public void setHeadrightsize(BigDecimal headrightsize) {
		this.headrightsize = headrightsize;
	}

	public Date getIcaosummarydate() {
		return this.icaosummarydate;
	}

	public void setIcaosummarydate(Date icaosummarydate) {
		this.icaosummarydate = icaosummarydate;
	}

	public BigDecimal getImagewithoutartifactcomp() {
		return this.imagewithoutartifactcomp;
	}

	public void setImagewithoutartifactcomp(BigDecimal imagewithoutartifactcomp) {
		this.imagewithoutartifactcomp = imagewithoutartifactcomp;
	}

	public BigDecimal getImagewithoutnoise() {
		return this.imagewithoutnoise;
	}

	public void setImagewithoutnoise(BigDecimal imagewithoutnoise) {
		this.imagewithoutnoise = imagewithoutnoise;
	}

	public BigDecimal getMonth() {
		return this.month;
	}

	public void setMonth(BigDecimal month) {
		this.month = month;
	}

	public BigDecimal getNaturalnocolor() {
		return this.naturalnocolor;
	}

	public void setNaturalnocolor(BigDecimal naturalnocolor) {
		this.naturalnocolor = naturalnocolor;
	}

	public BigDecimal getOverexposure() {
		return this.overexposure;
	}

	public void setOverexposure(BigDecimal overexposure) {
		this.overexposure = overexposure;
	}

	public BigDecimal getPicturequality() {
		return this.picturequality;
	}

	public void setPicturequality(BigDecimal picturequality) {
		this.picturequality = picturequality;
	}

	public BigDecimal getPicturetries() {
		return this.picturetries;
	}

	public void setPicturetries(BigDecimal picturetries) {
		this.picturetries = picturetries;
	}

	public BigDecimal getRayoflightintheface() {
		return this.rayoflightintheface;
	}

	public void setRayoflightintheface(BigDecimal rayoflightintheface) {
		this.rayoflightintheface = rayoflightintheface;
	}

	public BigDecimal getShadowsincheek() {
		return this.shadowsincheek;
	}

	public void setShadowsincheek(BigDecimal shadowsincheek) {
		this.shadowsincheek = shadowsincheek;
	}

	public BigDecimal getSunglasses() {
		return this.sunglasses;
	}

	public void setSunglasses(BigDecimal sunglasses) {
		this.sunglasses = sunglasses;
	}

	public BigDecimal getUnentangledimage() {
		return this.unentangledimage;
	}

	public void setUnentangledimage(BigDecimal unentangledimage) {
		this.unentangledimage = unentangledimage;
	}

	public BigDecimal getUniformillumination() {
		return this.uniformillumination;
	}

	public void setUniformillumination(BigDecimal uniformillumination) {
		this.uniformillumination = uniformillumination;
	}

	public BigDecimal getUniformityfund() {
		return this.uniformityfund;
	}

	public void setUniformityfund(BigDecimal uniformityfund) {
		this.uniformityfund = uniformityfund;
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

	public BigDecimal getYear() {
		return this.year;
	}

	public void setYear(BigDecimal year) {
		this.year = year;
	}

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

}