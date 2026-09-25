package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PROCESS_JOBS database table.
 * 
 */
@Entity
@Table(name="PROCESS_JOBS")
@NamedQuery(name="ProcessJob.findAll", query="SELECT p FROM ProcessJob p")
public class ProcessJob implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idprocessjob;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idjobstatus;

	private BigDecimal processjobclass;

	private String processjobdescription;

	@Temporal(TemporalType.DATE)
	private Date processjobenddate;

	private BigDecimal processjobenrollmentsnumber;

	private BigDecimal processjobfinalsequence;

	private BigDecimal processjobinitialsequence;

	private BigDecimal processjobinterval;

	@Temporal(TemporalType.DATE)
	private Date processjobstartdate;

	private String processjobsummarydata;

	private BigDecimal processjobtype;

	private BigDecimal processjobvalue;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to JobStatusCat
	@ManyToOne
	@JoinColumn(name="CREATEDBY")
	private JobStatusCat jobStatusCat;

	//bi-directional many-to-one association to ProcessJobsDetail
	@OneToMany(mappedBy="processJob")
	private List<ProcessJobsDetail> processJobsDetails;

	public ProcessJob() {
	}

	public long getIdprocessjob() {
		return this.idprocessjob;
	}

	public void setIdprocessjob(long idprocessjob) {
		this.idprocessjob = idprocessjob;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getIdjobstatus() {
		return this.idjobstatus;
	}

	public void setIdjobstatus(BigDecimal idjobstatus) {
		this.idjobstatus = idjobstatus;
	}

	public BigDecimal getProcessjobclass() {
		return this.processjobclass;
	}

	public void setProcessjobclass(BigDecimal processjobclass) {
		this.processjobclass = processjobclass;
	}

	public String getProcessjobdescription() {
		return this.processjobdescription;
	}

	public void setProcessjobdescription(String processjobdescription) {
		this.processjobdescription = processjobdescription;
	}

	public Date getProcessjobenddate() {
		return this.processjobenddate;
	}

	public void setProcessjobenddate(Date processjobenddate) {
		this.processjobenddate = processjobenddate;
	}

	public BigDecimal getProcessjobenrollmentsnumber() {
		return this.processjobenrollmentsnumber;
	}

	public void setProcessjobenrollmentsnumber(BigDecimal processjobenrollmentsnumber) {
		this.processjobenrollmentsnumber = processjobenrollmentsnumber;
	}

	public BigDecimal getProcessjobfinalsequence() {
		return this.processjobfinalsequence;
	}

	public void setProcessjobfinalsequence(BigDecimal processjobfinalsequence) {
		this.processjobfinalsequence = processjobfinalsequence;
	}

	public BigDecimal getProcessjobinitialsequence() {
		return this.processjobinitialsequence;
	}

	public void setProcessjobinitialsequence(BigDecimal processjobinitialsequence) {
		this.processjobinitialsequence = processjobinitialsequence;
	}

	public BigDecimal getProcessjobinterval() {
		return this.processjobinterval;
	}

	public void setProcessjobinterval(BigDecimal processjobinterval) {
		this.processjobinterval = processjobinterval;
	}

	public Date getProcessjobstartdate() {
		return this.processjobstartdate;
	}

	public void setProcessjobstartdate(Date processjobstartdate) {
		this.processjobstartdate = processjobstartdate;
	}

	public String getProcessjobsummarydata() {
		return this.processjobsummarydata;
	}

	public void setProcessjobsummarydata(String processjobsummarydata) {
		this.processjobsummarydata = processjobsummarydata;
	}

	public BigDecimal getProcessjobtype() {
		return this.processjobtype;
	}

	public void setProcessjobtype(BigDecimal processjobtype) {
		this.processjobtype = processjobtype;
	}

	public BigDecimal getProcessjobvalue() {
		return this.processjobvalue;
	}

	public void setProcessjobvalue(BigDecimal processjobvalue) {
		this.processjobvalue = processjobvalue;
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

	public JobStatusCat getJobStatusCat() {
		return this.jobStatusCat;
	}

	public void setJobStatusCat(JobStatusCat jobStatusCat) {
		this.jobStatusCat = jobStatusCat;
	}

	public List<ProcessJobsDetail> getProcessJobsDetails() {
		return this.processJobsDetails;
	}

	public void setProcessJobsDetails(List<ProcessJobsDetail> processJobsDetails) {
		this.processJobsDetails = processJobsDetails;
	}

	public ProcessJobsDetail addProcessJobsDetail(ProcessJobsDetail processJobsDetail) {
		getProcessJobsDetails().add(processJobsDetail);
		processJobsDetail.setProcessJob(this);

		return processJobsDetail;
	}

	public ProcessJobsDetail removeProcessJobsDetail(ProcessJobsDetail processJobsDetail) {
		getProcessJobsDetails().remove(processJobsDetail);
		processJobsDetail.setProcessJob(null);

		return processJobsDetail;
	}

}