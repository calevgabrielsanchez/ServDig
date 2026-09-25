package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the INVENTORIES database table.
 * 
 */
@Entity
@Table(name="INVENTORIES")
@NamedQuery(name="Inventory.findAll", query="SELECT i FROM Inventory i")
public class Inventory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idinventory;

	private BigDecimal amount;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal durationtime;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Consumabletype
	@ManyToOne
	@JoinColumn(name="IDCONSUMABLETYPE")
	private Consumabletype consumabletype;

	//bi-directional many-to-one association to Umf
	@ManyToOne
	@JoinColumn(name="IDUMF")
	private Umf umf;

	public Inventory() {
	}

	public long getIdinventory() {
		return this.idinventory;
	}

	public void setIdinventory(long idinventory) {
		this.idinventory = idinventory;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public BigDecimal getDurationtime() {
		return this.durationtime;
	}

	public void setDurationtime(BigDecimal durationtime) {
		this.durationtime = durationtime;
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

	public Consumabletype getConsumabletype() {
		return this.consumabletype;
	}

	public void setConsumabletype(Consumabletype consumabletype) {
		this.consumabletype = consumabletype;
	}

	public Umf getUmf() {
		return this.umf;
	}

	public void setUmf(Umf umf) {
		this.umf = umf;
	}

}