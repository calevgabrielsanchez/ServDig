package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RANGES database table.
 * 
 */
@Entity
@Table(name="RANGES")
@NamedQuery(name="Range.findAll", query="SELECT r FROM Range r")
public class Range implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idranges;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal finalrange;

	private BigDecimal initialrange;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Range() {
	}

	public long getIdranges() {
		return this.idranges;
	}

	public void setIdranges(long idranges) {
		this.idranges = idranges;
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

	public BigDecimal getFinalrange() {
		return this.finalrange;
	}

	public void setFinalrange(BigDecimal finalrange) {
		this.finalrange = finalrange;
	}

	public BigDecimal getInitialrange() {
		return this.initialrange;
	}

	public void setInitialrange(BigDecimal initialrange) {
		this.initialrange = initialrange;
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