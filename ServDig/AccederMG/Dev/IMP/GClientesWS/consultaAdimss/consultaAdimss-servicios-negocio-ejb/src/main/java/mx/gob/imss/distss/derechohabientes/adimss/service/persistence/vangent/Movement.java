package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MOVEMENTS database table.
 * 
 */
@Entity
@Table(name="MOVEMENTS")
@NamedQuery(name="Movement.findAll", query="SELECT m FROM Movement m")
public class Movement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idmovement;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date movementdate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Impactlogistic
	@ManyToOne
	@JoinColumn(name="IDIMPACTLOGISTIC")
	private Impactlogistic impactlogistic;

	//bi-directional many-to-one association to Movementtype
	@ManyToOne
	@JoinColumn(name="IDMOVEMENTTYPE")
	private Movementtype movementtype;

	//bi-directional many-to-one association to Purchas
	@ManyToOne
	@JoinColumn(name="IDPURCHASE")
	private Purchas purchas;

	//bi-directional many-to-one association to Shipment
	@ManyToOne
	@JoinColumn(name="IDSHIPMENTS")
	private Shipment shipment;

	//bi-directional many-to-one association to Umf
	@ManyToOne
	@JoinColumn(name="IDUMF")
	private Umf umf;

	public Movement() {
	}

	public long getIdmovement() {
		return this.idmovement;
	}

	public void setIdmovement(long idmovement) {
		this.idmovement = idmovement;
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

	public Date getMovementdate() {
		return this.movementdate;
	}

	public void setMovementdate(Date movementdate) {
		this.movementdate = movementdate;
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

	public Impactlogistic getImpactlogistic() {
		return this.impactlogistic;
	}

	public void setImpactlogistic(Impactlogistic impactlogistic) {
		this.impactlogistic = impactlogistic;
	}

	public Movementtype getMovementtype() {
		return this.movementtype;
	}

	public void setMovementtype(Movementtype movementtype) {
		this.movementtype = movementtype;
	}

	public Purchas getPurchas() {
		return this.purchas;
	}

	public void setPurchas(Purchas purchas) {
		this.purchas = purchas;
	}

	public Shipment getShipment() {
		return this.shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	public Umf getUmf() {
		return this.umf;
	}

	public void setUmf(Umf umf) {
		this.umf = umf;
	}

}