package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the AREASUPERVISORS database table.
 * 
 */
@Entity
@Table(name="AREASUPERVISORS")
@NamedQuery(name="Areasupervisor.findAll", query="SELECT a FROM Areasupervisor a")
public class Areasupervisor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idareasupervisor;

	private String areasupervisorname;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Areasupervisor() {
	}

	public long getIdareasupervisor() {
		return this.idareasupervisor;
	}

	public void setIdareasupervisor(long idareasupervisor) {
		this.idareasupervisor = idareasupervisor;
	}

	public String getAreasupervisorname() {
		return this.areasupervisorname;
	}

	public void setAreasupervisorname(String areasupervisorname) {
		this.areasupervisorname = areasupervisorname;
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

}