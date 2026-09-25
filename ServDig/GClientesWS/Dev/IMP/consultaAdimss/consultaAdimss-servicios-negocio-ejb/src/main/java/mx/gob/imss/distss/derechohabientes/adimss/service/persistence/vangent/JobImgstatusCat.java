package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the JOB_IMGSTATUS_CAT database table.
 * 
 */
@Entity
@Table(name="JOB_IMGSTATUS_CAT")
@NamedQuery(name="JobImgstatusCat.findAll", query="SELECT j FROM JobImgstatusCat j")
public class JobImgstatusCat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idjobimgstatus;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String jobimgstatusdescription;

	private String jobimgstatustype;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to ProcessJobsImageData
	@OneToMany(mappedBy="jobImgstatusCat")
	private List<ProcessJobsImageData> processJobsImageData;

	public JobImgstatusCat() {
	}

	public long getIdjobimgstatus() {
		return this.idjobimgstatus;
	}

	public void setIdjobimgstatus(long idjobimgstatus) {
		this.idjobimgstatus = idjobimgstatus;
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

	public String getJobimgstatusdescription() {
		return this.jobimgstatusdescription;
	}

	public void setJobimgstatusdescription(String jobimgstatusdescription) {
		this.jobimgstatusdescription = jobimgstatusdescription;
	}

	public String getJobimgstatustype() {
		return this.jobimgstatustype;
	}

	public void setJobimgstatustype(String jobimgstatustype) {
		this.jobimgstatustype = jobimgstatustype;
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

	public List<ProcessJobsImageData> getProcessJobsImageData() {
		return this.processJobsImageData;
	}

	public void setProcessJobsImageData(List<ProcessJobsImageData> processJobsImageData) {
		this.processJobsImageData = processJobsImageData;
	}

	public ProcessJobsImageData addProcessJobsImageData(ProcessJobsImageData processJobsImageData) {
		getProcessJobsImageData().add(processJobsImageData);
		processJobsImageData.setJobImgstatusCat(this);

		return processJobsImageData;
	}

	public ProcessJobsImageData removeProcessJobsImageData(ProcessJobsImageData processJobsImageData) {
		getProcessJobsImageData().remove(processJobsImageData);
		processJobsImageData.setJobImgstatusCat(null);

		return processJobsImageData;
	}

}