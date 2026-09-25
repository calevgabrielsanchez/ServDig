package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PACKAGEDETAILS database table.
 * 
 */
@Entity
@Table(name="PACKAGEDETAILS")
@NamedQuery(name="Packagedetail.findAll", query="SELECT p FROM Packagedetail p")
public class Packagedetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idpackagedetail;

	private BigDecimal amount;

	private String cardconsecutive;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idconsumabletype;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Purchas
	@OneToMany(mappedBy="packagedetail")
	private List<Purchas> purchases;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="packagedetail")
	private List<Shipment> shipments;

	public Packagedetail() {
	}

	public long getIdpackagedetail() {
		return this.idpackagedetail;
	}

	public void setIdpackagedetail(long idpackagedetail) {
		this.idpackagedetail = idpackagedetail;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCardconsecutive() {
		return this.cardconsecutive;
	}

	public void setCardconsecutive(String cardconsecutive) {
		this.cardconsecutive = cardconsecutive;
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

	public BigDecimal getIdconsumabletype() {
		return this.idconsumabletype;
	}

	public void setIdconsumabletype(BigDecimal idconsumabletype) {
		this.idconsumabletype = idconsumabletype;
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

	public List<Purchas> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(List<Purchas> purchases) {
		this.purchases = purchases;
	}

	public Purchas addPurchas(Purchas purchas) {
		getPurchases().add(purchas);
		purchas.setPackagedetail(this);

		return purchas;
	}

	public Purchas removePurchas(Purchas purchas) {
		getPurchases().remove(purchas);
		purchas.setPackagedetail(null);

		return purchas;
	}

	public List<Shipment> getShipments() {
		return this.shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public Shipment addShipment(Shipment shipment) {
		getShipments().add(shipment);
		shipment.setPackagedetail(this);

		return shipment;
	}

	public Shipment removeShipment(Shipment shipment) {
		getShipments().remove(shipment);
		shipment.setPackagedetail(null);

		return shipment;
	}

}