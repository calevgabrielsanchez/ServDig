package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PROCESS_JOBS_IMAGE_DATA database table.
 * 
 */
@Entity
@Table(name="PROCESS_JOBS_IMAGE_DATA")
@NamedQuery(name="ProcessJobsImageData.findAll", query="SELECT p FROM ProcessJobsImageData p")
public class ProcessJobsImageData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idprocessjobimagedata;

	private String adimssdctmfilename;

	private String adimssdctmimgref;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idenrolimagedetails;

	private String processjobimagedata;

	private BigDecimal processjobimagesequence;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private String vangentdctmimgefilename;

	private String vangentdctmimgeref;

	//bi-directional many-to-one association to JobImgstatusCat
	@ManyToOne
	@JoinColumn(name="IDJOBIMGSTATUS")
	private JobImgstatusCat jobImgstatusCat;

	//bi-directional many-to-one association to ProcessJobsDetail
	@ManyToOne
	@JoinColumn(name="IDPROCESSJOBDETAIL")
	private ProcessJobsDetail processJobsDetail;

	public ProcessJobsImageData() {
	}

	public long getIdprocessjobimagedata() {
		return this.idprocessjobimagedata;
	}

	public void setIdprocessjobimagedata(long idprocessjobimagedata) {
		this.idprocessjobimagedata = idprocessjobimagedata;
	}

	public String getAdimssdctmfilename() {
		return this.adimssdctmfilename;
	}

	public void setAdimssdctmfilename(String adimssdctmfilename) {
		this.adimssdctmfilename = adimssdctmfilename;
	}

	public String getAdimssdctmimgref() {
		return this.adimssdctmimgref;
	}

	public void setAdimssdctmimgref(String adimssdctmimgref) {
		this.adimssdctmimgref = adimssdctmimgref;
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

	public BigDecimal getIdenrolimagedetails() {
		return this.idenrolimagedetails;
	}

	public void setIdenrolimagedetails(BigDecimal idenrolimagedetails) {
		this.idenrolimagedetails = idenrolimagedetails;
	}

	public String getProcessjobimagedata() {
		return this.processjobimagedata;
	}

	public void setProcessjobimagedata(String processjobimagedata) {
		this.processjobimagedata = processjobimagedata;
	}

	public BigDecimal getProcessjobimagesequence() {
		return this.processjobimagesequence;
	}

	public void setProcessjobimagesequence(BigDecimal processjobimagesequence) {
		this.processjobimagesequence = processjobimagesequence;
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

	public String getVangentdctmimgefilename() {
		return this.vangentdctmimgefilename;
	}

	public void setVangentdctmimgefilename(String vangentdctmimgefilename) {
		this.vangentdctmimgefilename = vangentdctmimgefilename;
	}

	public String getVangentdctmimgeref() {
		return this.vangentdctmimgeref;
	}

	public void setVangentdctmimgeref(String vangentdctmimgeref) {
		this.vangentdctmimgeref = vangentdctmimgeref;
	}

	public JobImgstatusCat getJobImgstatusCat() {
		return this.jobImgstatusCat;
	}

	public void setJobImgstatusCat(JobImgstatusCat jobImgstatusCat) {
		this.jobImgstatusCat = jobImgstatusCat;
	}

	public ProcessJobsDetail getProcessJobsDetail() {
		return this.processJobsDetail;
	}

	public void setProcessJobsDetail(ProcessJobsDetail processJobsDetail) {
		this.processJobsDetail = processJobsDetail;
	}

}