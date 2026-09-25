package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CARDSTATUS database table.
 * 
 */
@Entity
@NamedQuery(name="Cardstatus.findAll", query="SELECT c FROM Cardstatus c")
public class Cardstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idcardstatus;

	private String cardstatusdesc;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Cardinventory
	@OneToMany(mappedBy="cardstatus")
	private List<Cardinventory> cardinventories;

	//bi-directional many-to-one association to Cardlife
	@OneToMany(mappedBy="cardstatus")
	private List<Cardlife> cardlifes;

	public Cardstatus() {
	}

	public long getIdcardstatus() {
		return this.idcardstatus;
	}

	public void setIdcardstatus(long idcardstatus) {
		this.idcardstatus = idcardstatus;
	}

	public String getCardstatusdesc() {
		return this.cardstatusdesc;
	}

	public void setCardstatusdesc(String cardstatusdesc) {
		this.cardstatusdesc = cardstatusdesc;
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

	public List<Cardinventory> getCardinventories() {
		return this.cardinventories;
	}

	public void setCardinventories(List<Cardinventory> cardinventories) {
		this.cardinventories = cardinventories;
	}

	public Cardinventory addCardinventory(Cardinventory cardinventory) {
		getCardinventories().add(cardinventory);
		cardinventory.setCardstatus(this);

		return cardinventory;
	}

	public Cardinventory removeCardinventory(Cardinventory cardinventory) {
		getCardinventories().remove(cardinventory);
		cardinventory.setCardstatus(null);

		return cardinventory;
	}

	public List<Cardlife> getCardlifes() {
		return this.cardlifes;
	}

	public void setCardlifes(List<Cardlife> cardlifes) {
		this.cardlifes = cardlifes;
	}

	public Cardlife addCardlife(Cardlife cardlife) {
		getCardlifes().add(cardlife);
		cardlife.setCardstatus(this);

		return cardlife;
	}

	public Cardlife removeCardlife(Cardlife cardlife) {
		getCardlifes().remove(cardlife);
		cardlife.setCardstatus(null);

		return cardlife;
	}

}