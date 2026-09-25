package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the BILLING database table.
 * 
 */
@Entity
@NamedQuery(name="Billing.findAll", query="SELECT b FROM Billing b")
public class Billing implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idbilling;

	private BigDecimal amount;

	@Temporal(TemporalType.DATE)
	private Date billingdate;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String folio;

	private BigDecimal iva;

	private BigDecimal subtotal;

	private BigDecimal total;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Prebilling
	@ManyToOne
	@JoinColumn(name="IDPREBILLING")
	private Prebilling prebilling;

	//bi-directional many-to-one association to Payrequest
	@OneToMany(mappedBy="billing")
	private List<Payrequest> payrequests;

	public Billing() {
	}

	public long getIdbilling() {
		return this.idbilling;
	}

	public void setIdbilling(long idbilling) {
		this.idbilling = idbilling;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getBillingdate() {
		return this.billingdate;
	}

	public void setBillingdate(Date billingdate) {
		this.billingdate = billingdate;
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

	public String getFolio() {
		return this.folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
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

	public Prebilling getPrebilling() {
		return this.prebilling;
	}

	public void setPrebilling(Prebilling prebilling) {
		this.prebilling = prebilling;
	}

	public List<Payrequest> getPayrequests() {
		return this.payrequests;
	}

	public void setPayrequests(List<Payrequest> payrequests) {
		this.payrequests = payrequests;
	}

	public Payrequest addPayrequest(Payrequest payrequest) {
		getPayrequests().add(payrequest);
		payrequest.setBilling(this);

		return payrequest;
	}

	public Payrequest removePayrequest(Payrequest payrequest) {
		getPayrequests().remove(payrequest);
		payrequest.setBilling(null);

		return payrequest;
	}

}