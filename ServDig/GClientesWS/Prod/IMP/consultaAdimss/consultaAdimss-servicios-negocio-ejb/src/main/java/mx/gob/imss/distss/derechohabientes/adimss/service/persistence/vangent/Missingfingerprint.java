package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MISSINGFINGERPRINTS database table.
 * 
 */
@Embeddable
@Table(name="MISSINGFINGERPRINTS")
@NamedQuery(name="Missingfingerprint.findAll", query="SELECT m FROM Missingfingerprint m")
public class Missingfingerprint implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idcalidad;

	private BigDecimal idenrol;

	private BigDecimal idenrollmentstation;

	private BigDecimal idoperator;

	private String nss;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Missingfingerprint() {
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

	public BigDecimal getIdcalidad() {
		return this.idcalidad;
	}

	public void setIdcalidad(BigDecimal idcalidad) {
		this.idcalidad = idcalidad;
	}

	public BigDecimal getIdenrol() {
		return this.idenrol;
	}

	public void setIdenrol(BigDecimal idenrol) {
		this.idenrol = idenrol;
	}

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public BigDecimal getIdoperator() {
		return this.idoperator;
	}

	public void setIdoperator(BigDecimal idoperator) {
		this.idoperator = idoperator;
	}

	public String getNss() {
		return this.nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
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