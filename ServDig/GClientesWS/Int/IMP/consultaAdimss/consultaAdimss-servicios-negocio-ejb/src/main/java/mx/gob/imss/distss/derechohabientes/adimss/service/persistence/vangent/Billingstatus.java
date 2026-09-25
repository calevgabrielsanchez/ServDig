package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the BILLINGSTATUS database table.
 * 
 */
@Entity
@NamedQuery(name="Billingstatus.findAll", query="SELECT b FROM Billingstatus b")
public class Billingstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idbillstatus;

	private String billstatusname;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Prebilling
	@OneToMany(mappedBy="billingstatus")
	private List<Prebilling> prebillings;

	public Billingstatus() {
	}

	public long getIdbillstatus() {
		return this.idbillstatus;
	}

	public void setIdbillstatus(long idbillstatus) {
		this.idbillstatus = idbillstatus;
	}

	public String getBillstatusname() {
		return this.billstatusname;
	}

	public void setBillstatusname(String billstatusname) {
		this.billstatusname = billstatusname;
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

	public List<Prebilling> getPrebillings() {
		return this.prebillings;
	}

	public void setPrebillings(List<Prebilling> prebillings) {
		this.prebillings = prebillings;
	}

	public Prebilling addPrebilling(Prebilling prebilling) {
		getPrebillings().add(prebilling);
		prebilling.setBillingstatus(this);

		return prebilling;
	}

	public Prebilling removePrebilling(Prebilling prebilling) {
		getPrebillings().remove(prebilling);
		prebilling.setBillingstatus(null);

		return prebilling;
	}

}