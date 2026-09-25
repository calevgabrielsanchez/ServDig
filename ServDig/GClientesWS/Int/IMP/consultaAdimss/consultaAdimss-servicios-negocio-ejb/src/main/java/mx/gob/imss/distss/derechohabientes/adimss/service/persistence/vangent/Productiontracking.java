package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRODUCTIONTRACKING database table.
 * 
 */
@Entity
@NamedQuery(name="Productiontracking.findAll", query="SELECT p FROM Productiontracking p")
public class Productiontracking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idprodtrack;

	private BigDecimal cardamount;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date dateprtrac;

//	private Object firstenrollment;

	private BigDecimal greater10;

	private BigDecimal idcompanymanager;

	private BigDecimal iddelegation;

	private BigDecimal idenrollmentstation;

	private BigDecimal less10;

	@Temporal(TemporalType.DATE)
	private Date operatorenter;

	@Temporal(TemporalType.DATE)
	private Date operatorout;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Productiontracking() {
	}

	public long getIdprodtrack() {
		return this.idprodtrack;
	}

	public void setIdprodtrack(long idprodtrack) {
		this.idprodtrack = idprodtrack;
	}

	public BigDecimal getCardamount() {
		return this.cardamount;
	}

	public void setCardamount(BigDecimal cardamount) {
		this.cardamount = cardamount;
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

	public Date getDateprtrac() {
		return this.dateprtrac;
	}

	public void setDateprtrac(Date dateprtrac) {
		this.dateprtrac = dateprtrac;
	}

//	public Object getFirstenrollment() {
//		return this.firstenrollment;
//	}
//
//	public void setFirstenrollment(Object firstenrollment) {
//		this.firstenrollment = firstenrollment;
//	}

	public BigDecimal getGreater10() {
		return this.greater10;
	}

	public void setGreater10(BigDecimal greater10) {
		this.greater10 = greater10;
	}

	public BigDecimal getIdcompanymanager() {
		return this.idcompanymanager;
	}

	public void setIdcompanymanager(BigDecimal idcompanymanager) {
		this.idcompanymanager = idcompanymanager;
	}

	public BigDecimal getIddelegation() {
		return this.iddelegation;
	}

	public void setIddelegation(BigDecimal iddelegation) {
		this.iddelegation = iddelegation;
	}

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public BigDecimal getLess10() {
		return this.less10;
	}

	public void setLess10(BigDecimal less10) {
		this.less10 = less10;
	}

	public Date getOperatorenter() {
		return this.operatorenter;
	}

	public void setOperatorenter(Date operatorenter) {
		this.operatorenter = operatorenter;
	}

	public Date getOperatorout() {
		return this.operatorout;
	}

	public void setOperatorout(Date operatorout) {
		this.operatorout = operatorout;
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

}