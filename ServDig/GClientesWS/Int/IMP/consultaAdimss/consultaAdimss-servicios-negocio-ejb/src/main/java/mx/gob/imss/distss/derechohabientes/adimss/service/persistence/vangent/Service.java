package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SERVICES database table.
 * 
 */
@Entity
@Table(name="SERVICES")
@NamedQuery(name="Service.findAll", query="SELECT s FROM Service s")
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long serviceid;

	@Temporal(TemporalType.DATE)
	private Date availstatusupdatedate;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal serviceavailstatus;

	private String serviceipaddress;

	private String servicename;

	private String serviceurl;

	@Temporal(TemporalType.DATE)
	private Date statusupdatedate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private BigDecimal workload;

	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="SERVICESTATUS")
	private Status status;

	public Service() {
	}

	public long getServiceid() {
		return this.serviceid;
	}

	public void setServiceid(long serviceid) {
		this.serviceid = serviceid;
	}

	public Date getAvailstatusupdatedate() {
		return this.availstatusupdatedate;
	}

	public void setAvailstatusupdatedate(Date availstatusupdatedate) {
		this.availstatusupdatedate = availstatusupdatedate;
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

	public BigDecimal getServiceavailstatus() {
		return this.serviceavailstatus;
	}

	public void setServiceavailstatus(BigDecimal serviceavailstatus) {
		this.serviceavailstatus = serviceavailstatus;
	}

	public String getServiceipaddress() {
		return this.serviceipaddress;
	}

	public void setServiceipaddress(String serviceipaddress) {
		this.serviceipaddress = serviceipaddress;
	}

	public String getServicename() {
		return this.servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getServiceurl() {
		return this.serviceurl;
	}

	public void setServiceurl(String serviceurl) {
		this.serviceurl = serviceurl;
	}

	public Date getStatusupdatedate() {
		return this.statusupdatedate;
	}

	public void setStatusupdatedate(Date statusupdatedate) {
		this.statusupdatedate = statusupdatedate;
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

	public BigDecimal getWorkload() {
		return this.workload;
	}

	public void setWorkload(BigDecimal workload) {
		this.workload = workload;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}