package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the CARDSCONTROL database table.
 * 
 */
@Entity
@NamedQuery(name="Cardscontrol.findAll", query="SELECT c FROM Cardscontrol c")
public class Cardscontrol implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CardscontrolPK id;

	private BigDecimal blocked;

	private BigDecimal delivered;

	@Column(name="IN_COMMING")
	private BigDecimal inComming;

	@Column(name="IN_ENROLMENT_CENTER")
	private BigDecimal inEnrolmentCenter;

	@Column(name="IN_TEST")
	private BigDecimal inTest;

	@Column(name="MAX_IDCARDINVENTORY")
	private BigDecimal maxIdcardinventory;

	@Column(name="MIN_IDCARDINVENTORY")
	private BigDecimal minIdcardinventory;

	private BigDecimal personalized;

	private BigDecimal received;

	private BigDecimal status;

	private BigDecimal tarjetasrecibidas;

	private BigDecimal validated;

	public Cardscontrol() {
	}

	public CardscontrolPK getId() {
		return this.id;
	}

	public void setId(CardscontrolPK id) {
		this.id = id;
	}

	public BigDecimal getBlocked() {
		return this.blocked;
	}

	public void setBlocked(BigDecimal blocked) {
		this.blocked = blocked;
	}

	public BigDecimal getDelivered() {
		return this.delivered;
	}

	public void setDelivered(BigDecimal delivered) {
		this.delivered = delivered;
	}

	public BigDecimal getInComming() {
		return this.inComming;
	}

	public void setInComming(BigDecimal inComming) {
		this.inComming = inComming;
	}

	public BigDecimal getInEnrolmentCenter() {
		return this.inEnrolmentCenter;
	}

	public void setInEnrolmentCenter(BigDecimal inEnrolmentCenter) {
		this.inEnrolmentCenter = inEnrolmentCenter;
	}

	public BigDecimal getInTest() {
		return this.inTest;
	}

	public void setInTest(BigDecimal inTest) {
		this.inTest = inTest;
	}

	public BigDecimal getMaxIdcardinventory() {
		return this.maxIdcardinventory;
	}

	public void setMaxIdcardinventory(BigDecimal maxIdcardinventory) {
		this.maxIdcardinventory = maxIdcardinventory;
	}

	public BigDecimal getMinIdcardinventory() {
		return this.minIdcardinventory;
	}

	public void setMinIdcardinventory(BigDecimal minIdcardinventory) {
		this.minIdcardinventory = minIdcardinventory;
	}

	public BigDecimal getPersonalized() {
		return this.personalized;
	}

	public void setPersonalized(BigDecimal personalized) {
		this.personalized = personalized;
	}

	public BigDecimal getReceived() {
		return this.received;
	}

	public void setReceived(BigDecimal received) {
		this.received = received;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public BigDecimal getTarjetasrecibidas() {
		return this.tarjetasrecibidas;
	}

	public void setTarjetasrecibidas(BigDecimal tarjetasrecibidas) {
		this.tarjetasrecibidas = tarjetasrecibidas;
	}

	public BigDecimal getValidated() {
		return this.validated;
	}

	public void setValidated(BigDecimal validated) {
		this.validated = validated;
	}

}