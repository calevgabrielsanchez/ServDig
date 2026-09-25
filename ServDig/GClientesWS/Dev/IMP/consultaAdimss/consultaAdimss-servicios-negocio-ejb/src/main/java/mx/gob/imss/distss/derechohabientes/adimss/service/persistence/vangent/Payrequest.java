package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PAYREQUESTS database table.
 * 
 */
@Entity
@Table(name="PAYREQUESTS")
@NamedQuery(name="Payrequest.findAll", query="SELECT p FROM Payrequest p")
public class Payrequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idpayrequest;

	private BigDecimal accountnum;

	private String bank;

	private BigDecimal clabeaccount;

	private String comments;

	private String companyname;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String payconcept;

	@Temporal(TemporalType.DATE)
	private Date payreqdate;

	private BigDecimal total;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Billing
	@ManyToOne
	@JoinColumn(name="IDBILLING")
	private Billing billing;

	public Payrequest() {
	}

	public long getIdpayrequest() {
		return this.idpayrequest;
	}

	public void setIdpayrequest(long idpayrequest) {
		this.idpayrequest = idpayrequest;
	}

	public BigDecimal getAccountnum() {
		return this.accountnum;
	}

	public void setAccountnum(BigDecimal accountnum) {
		this.accountnum = accountnum;
	}

	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public BigDecimal getClabeaccount() {
		return this.clabeaccount;
	}

	public void setClabeaccount(BigDecimal clabeaccount) {
		this.clabeaccount = clabeaccount;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
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

	public String getPayconcept() {
		return this.payconcept;
	}

	public void setPayconcept(String payconcept) {
		this.payconcept = payconcept;
	}

	public Date getPayreqdate() {
		return this.payreqdate;
	}

	public void setPayreqdate(Date payreqdate) {
		this.payreqdate = payreqdate;
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

	public Billing getBilling() {
		return this.billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

}