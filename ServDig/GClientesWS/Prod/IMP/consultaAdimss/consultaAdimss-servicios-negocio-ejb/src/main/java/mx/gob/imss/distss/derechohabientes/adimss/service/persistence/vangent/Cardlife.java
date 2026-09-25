package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CARDLIFE database table.
 * 
 */
@Entity
@NamedQuery(name="Cardlife.findAll", query="SELECT c FROM Cardlife c")
public class Cardlife implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idcardlife;

	private String cardlifedesc;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date statusdate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Cardinventory
	@ManyToOne
	@JoinColumn(name="IDCARDINVENTORY")
	private Cardinventory cardinventory;

	//bi-directional many-to-one association to Cardstatus
	@ManyToOne
	@JoinColumn(name="IDCARDSTATUS")
	private Cardstatus cardstatus;

	public Cardlife() {
	}

	public long getIdcardlife() {
		return this.idcardlife;
	}

	public void setIdcardlife(long idcardlife) {
		this.idcardlife = idcardlife;
	}

	public String getCardlifedesc() {
		return this.cardlifedesc;
	}

	public void setCardlifedesc(String cardlifedesc) {
		this.cardlifedesc = cardlifedesc;
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

	public Date getStatusdate() {
		return this.statusdate;
	}

	public void setStatusdate(Date statusdate) {
		this.statusdate = statusdate;
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

	public Cardinventory getCardinventory() {
		return this.cardinventory;
	}

	public void setCardinventory(Cardinventory cardinventory) {
		this.cardinventory = cardinventory;
	}

	public Cardstatus getCardstatus() {
		return this.cardstatus;
	}

	public void setCardstatus(Cardstatus cardstatus) {
		this.cardstatus = cardstatus;
	}

}