package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLPERFORMANCECOM database table.
 * 
 */
@Entity
@NamedQuery(name="Enrollperformancecom.findAll", query="SELECT e FROM Enrollperformancecom e")
public class Enrollperformancecom implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollperformancecomPK id;

//	private Object begtime;

	private BigDecimal coalcard;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="\"DAYS\"")
	private BigDecimal days;

//	private Object endtime;

	private BigDecimal goalavg;

	private BigDecimal goalmonth;

	private String period;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Companymanager
	@ManyToOne
	@JoinColumn(name="IDCOMPANYMANAGER")
	private Companymanager companymanager;

	public Enrollperformancecom() {
	}

	public EnrollperformancecomPK getId() {
		return this.id;
	}

	public void setId(EnrollperformancecomPK id) {
		this.id = id;
	}

//	public Object getBegtime() {
//		return this.begtime;
//	}
//
//	public void setBegtime(Object begtime) {
//		this.begtime = begtime;
//	}

	public BigDecimal getCoalcard() {
		return this.coalcard;
	}

	public void setCoalcard(BigDecimal coalcard) {
		this.coalcard = coalcard;
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

	public BigDecimal getDays() {
		return this.days;
	}

	public void setDays(BigDecimal days) {
		this.days = days;
	}

//	public Object getEndtime() {
//		return this.endtime;
//	}
//
//	public void setEndtime(Object endtime) {
//		this.endtime = endtime;
//	}

	public BigDecimal getGoalavg() {
		return this.goalavg;
	}

	public void setGoalavg(BigDecimal goalavg) {
		this.goalavg = goalavg;
	}

	public BigDecimal getGoalmonth() {
		return this.goalmonth;
	}

	public void setGoalmonth(BigDecimal goalmonth) {
		this.goalmonth = goalmonth;
	}

	public String getPeriod() {
		return this.period;
	}

	public void setPeriod(String period) {
		this.period = period;
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

	public Companymanager getCompanymanager() {
		return this.companymanager;
	}

	public void setCompanymanager(Companymanager companymanager) {
		this.companymanager = companymanager;
	}

}