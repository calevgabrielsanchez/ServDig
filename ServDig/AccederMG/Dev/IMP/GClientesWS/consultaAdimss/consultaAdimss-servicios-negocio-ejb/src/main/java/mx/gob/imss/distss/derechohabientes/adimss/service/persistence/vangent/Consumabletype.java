package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CONSUMABLETYPES database table.
 * 
 */
@Entity
@Table(name="CONSUMABLETYPES")
@NamedQuery(name="Consumabletype.findAll", query="SELECT c FROM Consumabletype c")
public class Consumabletype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idconsumabletype;

	private String consumabletypedesc;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal gennumcards;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Purchas
	@ManyToOne
	@JoinColumn(name="IDPURCHASE")
	private Purchas purchas;

	//bi-directional many-to-one association to Inventory
	@OneToMany(mappedBy="consumabletype")
	private List<Inventory> inventories;

	public Consumabletype() {
	}

	public long getIdconsumabletype() {
		return this.idconsumabletype;
	}

	public void setIdconsumabletype(long idconsumabletype) {
		this.idconsumabletype = idconsumabletype;
	}

	public String getConsumabletypedesc() {
		return this.consumabletypedesc;
	}

	public void setConsumabletypedesc(String consumabletypedesc) {
		this.consumabletypedesc = consumabletypedesc;
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

	public BigDecimal getGennumcards() {
		return this.gennumcards;
	}

	public void setGennumcards(BigDecimal gennumcards) {
		this.gennumcards = gennumcards;
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

	public Purchas getPurchas() {
		return this.purchas;
	}

	public void setPurchas(Purchas purchas) {
		this.purchas = purchas;
	}

	public List<Inventory> getInventories() {
		return this.inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}

	public Inventory addInventory(Inventory inventory) {
		getInventories().add(inventory);
		inventory.setConsumabletype(this);

		return inventory;
	}

	public Inventory removeInventory(Inventory inventory) {
		getInventories().remove(inventory);
		inventory.setConsumabletype(null);

		return inventory;
	}

}