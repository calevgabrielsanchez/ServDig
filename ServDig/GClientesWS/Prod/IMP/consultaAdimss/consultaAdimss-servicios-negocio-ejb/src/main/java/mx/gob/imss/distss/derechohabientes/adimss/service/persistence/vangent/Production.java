package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRODUCTION database table.
 * 
 */
@Entity
@NamedQuery(name="Production.findAll", query="SELECT p FROM Production p")
public class Production implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idproduction;

	private BigDecimal baseobjective;

	private BigDecimal between15and50;

	private BigDecimal between50and60;

	private BigDecimal between61and72;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal lessthan15;

	@Column(name="\"MONTH\"")
	private BigDecimal month;

	private BigDecimal morethan72;

	@Temporal(TemporalType.DATE)
	private Date productiondate;

	private BigDecimal total;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	@Column(name="\"YEAR\"")
	private BigDecimal year;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	public Production() {
	}

	public long getIdproduction() {
		return this.idproduction;
	}

	public void setIdproduction(long idproduction) {
		this.idproduction = idproduction;
	}

	public BigDecimal getBaseobjective() {
		return this.baseobjective;
	}

	public void setBaseobjective(BigDecimal baseobjective) {
		this.baseobjective = baseobjective;
	}

	public BigDecimal getBetween15and50() {
		return this.between15and50;
	}

	public void setBetween15and50(BigDecimal between15and50) {
		this.between15and50 = between15and50;
	}

	public BigDecimal getBetween50and60() {
		return this.between50and60;
	}

	public void setBetween50and60(BigDecimal between50and60) {
		this.between50and60 = between50and60;
	}

	public BigDecimal getBetween61and72() {
		return this.between61and72;
	}

	public void setBetween61and72(BigDecimal between61and72) {
		this.between61and72 = between61and72;
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

	public BigDecimal getLessthan15() {
		return this.lessthan15;
	}

	public void setLessthan15(BigDecimal lessthan15) {
		this.lessthan15 = lessthan15;
	}

	public BigDecimal getMonth() {
		return this.month;
	}

	public void setMonth(BigDecimal month) {
		this.month = month;
	}

	public BigDecimal getMorethan72() {
		return this.morethan72;
	}

	public void setMorethan72(BigDecimal morethan72) {
		this.morethan72 = morethan72;
	}

	public Date getProductiondate() {
		return this.productiondate;
	}

	public void setProductiondate(Date productiondate) {
		this.productiondate = productiondate;
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

	public BigDecimal getYear() {
		return this.year;
	}

	public void setYear(BigDecimal year) {
		this.year = year;
	}

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

}