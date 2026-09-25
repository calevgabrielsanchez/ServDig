package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CARDINVENTORY database table.
 * 
 */
@Entity
@NamedQuery(name="Cardinventory.findAll", query="SELECT c FROM Cardinventory c")
public class Cardinventory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idcardinventory;

	private String carddescription;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date entrydate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Cardstatus
	@ManyToOne
	@JoinColumn(name="IDCARDSTATUS")
	private Cardstatus cardstatus;

	//bi-directional many-to-one association to Cardlife
	@OneToMany(mappedBy="cardinventory")
	private List<Cardlife> cardlifes;

	public Cardinventory() {
	}

	public long getIdcardinventory() {
		return this.idcardinventory;
	}

	public void setIdcardinventory(long idcardinventory) {
		this.idcardinventory = idcardinventory;
	}

	public String getCarddescription() {
		return this.carddescription;
	}

	public void setCarddescription(String carddescription) {
		this.carddescription = carddescription;
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

	public Date getEntrydate() {
		return this.entrydate;
	}

	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
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

	public Cardstatus getCardstatus() {
		return this.cardstatus;
	}

	public void setCardstatus(Cardstatus cardstatus) {
		this.cardstatus = cardstatus;
	}

	public List<Cardlife> getCardlifes() {
		return this.cardlifes;
	}

	public void setCardlifes(List<Cardlife> cardlifes) {
		this.cardlifes = cardlifes;
	}

	public Cardlife addCardlife(Cardlife cardlife) {
		getCardlifes().add(cardlife);
		cardlife.setCardinventory(this);

		return cardlife;
	}

	public Cardlife removeCardlife(Cardlife cardlife) {
		getCardlifes().remove(cardlife);
		cardlife.setCardinventory(null);

		return cardlife;
	}

}