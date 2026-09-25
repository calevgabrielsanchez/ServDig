package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRODUCTION_REEX database table.
 * 
 */
@Embeddable
@Table(name="PRODUCTION_REEX")
@NamedQuery(name="ProductionReex.findAll", query="SELECT p FROM ProductionReex p")
public class ProductionReex implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal baseobjective;

	private BigDecimal between15and50;

	private BigDecimal between50and60;

	private BigDecimal between61and72;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idenrollmentstation;

	private BigDecimal idproduction;

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

	public ProductionReex() {
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

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public BigDecimal getIdproduction() {
		return this.idproduction;
	}

	public void setIdproduction(BigDecimal idproduction) {
		this.idproduction = idproduction;
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

}