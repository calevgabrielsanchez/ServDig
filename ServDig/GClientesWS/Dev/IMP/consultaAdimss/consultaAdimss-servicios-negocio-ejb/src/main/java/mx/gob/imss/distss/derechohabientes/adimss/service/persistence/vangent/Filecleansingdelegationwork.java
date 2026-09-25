package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FILECLEANSINGDELEGATIONWORKS database table.
 * 
 */
@Entity
@Table(name="FILECLEANSINGDELEGATIONWORKS")
@NamedQuery(name="Filecleansingdelegationwork.findAll", query="SELECT f FROM Filecleansingdelegationwork f")
public class Filecleansingdelegationwork implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long recordid;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private BigDecimal workid;

	//bi-directional many-to-one association to Delegation
	@ManyToOne
	@JoinColumn(name="IDDELEGATION")
	private Delegation delegation;

	public Filecleansingdelegationwork() {
	}

	public long getRecordid() {
		return this.recordid;
	}

	public void setRecordid(long recordid) {
		this.recordid = recordid;
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

	public BigDecimal getWorkid() {
		return this.workid;
	}

	public void setWorkid(BigDecimal workid) {
		this.workid = workid;
	}

	public Delegation getDelegation() {
		return this.delegation;
	}

	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}

}