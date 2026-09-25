package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the JOB_STATUS_CAT database table.
 * 
 */
@Entity
@Table(name="JOB_STATUS_CAT")
@NamedQuery(name="JobStatusCat.findAll", query="SELECT j FROM JobStatusCat j")
public class JobStatusCat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idjobstatus;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String jobstatusdescription;

	private String jobstatustype;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to ProcessJob
	@OneToMany(mappedBy="jobStatusCat")
	private List<ProcessJob> processJobs;

	//bi-directional many-to-one association to ProcessJobsDetail
	@OneToMany(mappedBy="jobStatusCat")
	private List<ProcessJobsDetail> processJobsDetails;

	public JobStatusCat() {
	}

	public long getIdjobstatus() {
		return this.idjobstatus;
	}

	public void setIdjobstatus(long idjobstatus) {
		this.idjobstatus = idjobstatus;
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

	public String getJobstatusdescription() {
		return this.jobstatusdescription;
	}

	public void setJobstatusdescription(String jobstatusdescription) {
		this.jobstatusdescription = jobstatusdescription;
	}

	public String getJobstatustype() {
		return this.jobstatustype;
	}

	public void setJobstatustype(String jobstatustype) {
		this.jobstatustype = jobstatustype;
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

	public List<ProcessJob> getProcessJobs() {
		return this.processJobs;
	}

	public void setProcessJobs(List<ProcessJob> processJobs) {
		this.processJobs = processJobs;
	}

	public ProcessJob addProcessJob(ProcessJob processJob) {
		getProcessJobs().add(processJob);
		processJob.setJobStatusCat(this);

		return processJob;
	}

	public ProcessJob removeProcessJob(ProcessJob processJob) {
		getProcessJobs().remove(processJob);
		processJob.setJobStatusCat(null);

		return processJob;
	}

	public List<ProcessJobsDetail> getProcessJobsDetails() {
		return this.processJobsDetails;
	}

	public void setProcessJobsDetails(List<ProcessJobsDetail> processJobsDetails) {
		this.processJobsDetails = processJobsDetails;
	}

	public ProcessJobsDetail addProcessJobsDetail(ProcessJobsDetail processJobsDetail) {
		getProcessJobsDetails().add(processJobsDetail);
		processJobsDetail.setJobStatusCat(this);

		return processJobsDetail;
	}

	public ProcessJobsDetail removeProcessJobsDetail(ProcessJobsDetail processJobsDetail) {
		getProcessJobsDetails().remove(processJobsDetail);
		processJobsDetail.setJobStatusCat(null);

		return processJobsDetail;
	}

}