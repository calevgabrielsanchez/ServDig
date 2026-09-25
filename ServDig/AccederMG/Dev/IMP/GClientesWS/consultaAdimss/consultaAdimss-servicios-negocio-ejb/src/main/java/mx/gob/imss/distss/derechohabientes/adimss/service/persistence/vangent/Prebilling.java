package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PREBILLING database table.
 * 
 */
@Entity
@NamedQuery(name="Prebilling.findAll", query="SELECT p FROM Prebilling p")
public class Prebilling implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idprebilling;

	@Temporal(TemporalType.DATE)
	private Date closingdate;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idcompanymanager;

	private BigDecimal iddiscount;

	private BigDecimal idweek;

	private BigDecimal idyear;

	private BigDecimal iva;

	private BigDecimal subtotal;

	private BigDecimal summarizing;

	private BigDecimal total;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Billing
	@OneToMany(mappedBy="prebilling")
	private List<Billing> billings;

	//bi-directional many-to-one association to Billingstatus
	@ManyToOne
	@JoinColumn(name="IDBILLSTATUS")
	private Billingstatus billingstatus;

	public Prebilling() {
	}

	public long getIdprebilling() {
		return this.idprebilling;
	}

	public void setIdprebilling(long idprebilling) {
		this.idprebilling = idprebilling;
	}

	public Date getClosingdate() {
		return this.closingdate;
	}

	public void setClosingdate(Date closingdate) {
		this.closingdate = closingdate;
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

	public BigDecimal getIdcompanymanager() {
		return this.idcompanymanager;
	}

	public void setIdcompanymanager(BigDecimal idcompanymanager) {
		this.idcompanymanager = idcompanymanager;
	}

	public BigDecimal getIddiscount() {
		return this.iddiscount;
	}

	public void setIddiscount(BigDecimal iddiscount) {
		this.iddiscount = iddiscount;
	}

	public BigDecimal getIdweek() {
		return this.idweek;
	}

	public void setIdweek(BigDecimal idweek) {
		this.idweek = idweek;
	}

	public BigDecimal getIdyear() {
		return this.idyear;
	}

	public void setIdyear(BigDecimal idyear) {
		this.idyear = idyear;
	}

	public BigDecimal getIva() {
		return this.iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getSummarizing() {
		return this.summarizing;
	}

	public void setSummarizing(BigDecimal summarizing) {
		this.summarizing = summarizing;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
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

	public List<Billing> getBillings() {
		return this.billings;
	}

	public void setBillings(List<Billing> billings) {
		this.billings = billings;
	}

	public Billing addBilling(Billing billing) {
		getBillings().add(billing);
		billing.setPrebilling(this);

		return billing;
	}

	public Billing removeBilling(Billing billing) {
		getBillings().remove(billing);
		billing.setPrebilling(null);

		return billing;
	}

	public Billingstatus getBillingstatus() {
		return this.billingstatus;
	}

	public void setBillingstatus(Billingstatus billingstatus) {
		this.billingstatus = billingstatus;
	}

}