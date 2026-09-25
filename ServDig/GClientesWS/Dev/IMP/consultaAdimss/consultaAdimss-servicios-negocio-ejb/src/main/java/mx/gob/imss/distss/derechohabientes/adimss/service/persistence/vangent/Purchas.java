package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PURCHASES database table.
 * 
 */
@Entity
@Table(name="PURCHASES")
@NamedQuery(name="Purchas.findAll", query="SELECT p FROM Purchas p")
public class Purchas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idpurchase;

	private String comments;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date purchasedate;

	private BigDecimal purchasefolio;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Consumabletype
	@OneToMany(mappedBy="purchas")
	private List<Consumabletype> consumabletypes;

	//bi-directional many-to-one association to Movement
	@OneToMany(mappedBy="purchas")
	private List<Movement> movements;

	//bi-directional many-to-one association to Packagedetail
	@ManyToOne
	@JoinColumn(name="IDPACKAGEDETAIL")
	private Packagedetail packagedetail;

	public Purchas() {
	}

	public long getIdpurchase() {
		return this.idpurchase;
	}

	public void setIdpurchase(long idpurchase) {
		this.idpurchase = idpurchase;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Date getPurchasedate() {
		return this.purchasedate;
	}

	public void setPurchasedate(Date purchasedate) {
		this.purchasedate = purchasedate;
	}

	public BigDecimal getPurchasefolio() {
		return this.purchasefolio;
	}

	public void setPurchasefolio(BigDecimal purchasefolio) {
		this.purchasefolio = purchasefolio;
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

	public List<Consumabletype> getConsumabletypes() {
		return this.consumabletypes;
	}

	public void setConsumabletypes(List<Consumabletype> consumabletypes) {
		this.consumabletypes = consumabletypes;
	}

	public Consumabletype addConsumabletype(Consumabletype consumabletype) {
		getConsumabletypes().add(consumabletype);
		consumabletype.setPurchas(this);

		return consumabletype;
	}

	public Consumabletype removeConsumabletype(Consumabletype consumabletype) {
		getConsumabletypes().remove(consumabletype);
		consumabletype.setPurchas(null);

		return consumabletype;
	}

	public List<Movement> getMovements() {
		return this.movements;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}

	public Movement addMovement(Movement movement) {
		getMovements().add(movement);
		movement.setPurchas(this);

		return movement;
	}

	public Movement removeMovement(Movement movement) {
		getMovements().remove(movement);
		movement.setPurchas(null);

		return movement;
	}

	public Packagedetail getPackagedetail() {
		return this.packagedetail;
	}

	public void setPackagedetail(Packagedetail packagedetail) {
		this.packagedetail = packagedetail;
	}

}