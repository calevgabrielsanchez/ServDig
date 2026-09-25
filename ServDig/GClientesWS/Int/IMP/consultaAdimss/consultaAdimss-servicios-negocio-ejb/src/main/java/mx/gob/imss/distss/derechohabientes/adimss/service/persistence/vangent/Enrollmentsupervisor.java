package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTSUPERVISORS database table.
 * 
 */
@Embeddable
@Table(name="ENROLLMENTSUPERVISORS")
@NamedQuery(name="Enrollmentsupervisor.findAll", query="SELECT e FROM Enrollmentsupervisor e")
public class Enrollmentsupervisor implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String description;

	private BigDecimal idsupervisor;

	private String lastnamem;

	private String lastnamep;

	private String name;

	private BigDecimal status;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updateon;

	public Enrollmentsupervisor() {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getIdsupervisor() {
		return this.idsupervisor;
	}

	public void setIdsupervisor(BigDecimal idsupervisor) {
		this.idsupervisor = idsupervisor;
	}

	public String getLastnamem() {
		return this.lastnamem;
	}

	public void setLastnamem(String lastnamem) {
		this.lastnamem = lastnamem;
	}

	public String getLastnamep() {
		return this.lastnamep;
	}

	public void setLastnamep(String lastnamep) {
		this.lastnamep = lastnamep;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public BigDecimal getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdateon() {
		return this.updateon;
	}

	public void setUpdateon(Date updateon) {
		this.updateon = updateon;
	}

}