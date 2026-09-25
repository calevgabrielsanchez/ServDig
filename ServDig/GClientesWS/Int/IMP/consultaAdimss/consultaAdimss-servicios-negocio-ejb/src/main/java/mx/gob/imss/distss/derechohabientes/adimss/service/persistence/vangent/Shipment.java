package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SHIPMENTS database table.
 * 
 */
@Entity
@Table(name="SHIPMENTS")
@NamedQuery(name="Shipment.findAll", query="SELECT s FROM Shipment s")
public class Shipment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idshipments;

	private String comments;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date deliverdate;

	private String guide;

	private BigDecimal idmessage;

	private String instructions;

	@Column(name="\"MODULE\"")
	private String module;

	@Temporal(TemporalType.DATE)
	private Date shipmentdate;

	private BigDecimal shipmentno;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private String whoreceive;

	//bi-directional many-to-one association to Movement
	@OneToMany(mappedBy="shipment")
	private List<Movement> movements;

	//bi-directional many-to-one association to Packagedetail
	@ManyToOne
	@JoinColumn(name="IDPACKAGEDETAIL")
	private Packagedetail packagedetail;

	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="IDSTATUS")
	private Status status;

	public Shipment() {
	}

	public long getIdshipments() {
		return this.idshipments;
	}

	public void setIdshipments(long idshipments) {
		this.idshipments = idshipments;
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

	public Date getDeliverdate() {
		return this.deliverdate;
	}

	public void setDeliverdate(Date deliverdate) {
		this.deliverdate = deliverdate;
	}

	public String getGuide() {
		return this.guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public BigDecimal getIdmessage() {
		return this.idmessage;
	}

	public void setIdmessage(BigDecimal idmessage) {
		this.idmessage = idmessage;
	}

	public String getInstructions() {
		return this.instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Date getShipmentdate() {
		return this.shipmentdate;
	}

	public void setShipmentdate(Date shipmentdate) {
		this.shipmentdate = shipmentdate;
	}

	public BigDecimal getShipmentno() {
		return this.shipmentno;
	}

	public void setShipmentno(BigDecimal shipmentno) {
		this.shipmentno = shipmentno;
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

	public String getWhoreceive() {
		return this.whoreceive;
	}

	public void setWhoreceive(String whoreceive) {
		this.whoreceive = whoreceive;
	}

	public List<Movement> getMovements() {
		return this.movements;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}

	public Movement addMovement(Movement movement) {
		getMovements().add(movement);
		movement.setShipment(this);

		return movement;
	}

	public Movement removeMovement(Movement movement) {
		getMovements().remove(movement);
		movement.setShipment(null);

		return movement;
	}

	public Packagedetail getPackagedetail() {
		return this.packagedetail;
	}

	public void setPackagedetail(Packagedetail packagedetail) {
		this.packagedetail = packagedetail;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}