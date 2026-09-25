package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ADT_ARC_MIN_HUELLAS_FINGERS database table.
 * 
 */
@Embeddable
@Table(name="ADT_ARC_MIN_HUELLAS_FINGERS")
@NamedQuery(name="AdtArcMinHuellasFinger.findAll", query="SELECT a FROM AdtArcMinHuellasFinger a")
public class AdtArcMinHuellasFinger implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal fingernumber;

	private BigDecimal minexstatus;

	@Column(name="NUM_FOLIO_PERSONA")
	private BigDecimal numFolioPersona;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private BigDecimal withoutreference;

	public AdtArcMinHuellasFinger() {
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

	public BigDecimal getFingernumber() {
		return this.fingernumber;
	}

	public void setFingernumber(BigDecimal fingernumber) {
		this.fingernumber = fingernumber;
	}

	public BigDecimal getMinexstatus() {
		return this.minexstatus;
	}

	public void setMinexstatus(BigDecimal minexstatus) {
		this.minexstatus = minexstatus;
	}

	public BigDecimal getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(BigDecimal numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
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

	public BigDecimal getWithoutreference() {
		return this.withoutreference;
	}

	public void setWithoutreference(BigDecimal withoutreference) {
		this.withoutreference = withoutreference;
	}

}