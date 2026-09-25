package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SERVICESLOGS database table.
 * 
 */
@Entity
@Table(name="SERVICESLOGS")
@NamedQuery(name="Serviceslog.findAll", query="SELECT s FROM Serviceslog s")
public class Serviceslog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idservicelog;

	@Temporal(TemporalType.DATE)
	private Date createdate;

	private String createserver;

	@Column(name="\"MESSAGE\"")
	private String message;

	private BigDecimal operationstatusid;

	private BigDecimal serviceid;

	private BigDecimal serviceoperatonid;

	public Serviceslog() {
	}

	public long getIdservicelog() {
		return this.idservicelog;
	}

	public void setIdservicelog(long idservicelog) {
		this.idservicelog = idservicelog;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getCreateserver() {
		return this.createserver;
	}

	public void setCreateserver(String createserver) {
		this.createserver = createserver;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BigDecimal getOperationstatusid() {
		return this.operationstatusid;
	}

	public void setOperationstatusid(BigDecimal operationstatusid) {
		this.operationstatusid = operationstatusid;
	}

	public BigDecimal getServiceid() {
		return this.serviceid;
	}

	public void setServiceid(BigDecimal serviceid) {
		this.serviceid = serviceid;
	}

	public BigDecimal getServiceoperatonid() {
		return this.serviceoperatonid;
	}

	public void setServiceoperatonid(BigDecimal serviceoperatonid) {
		this.serviceoperatonid = serviceoperatonid;
	}

}