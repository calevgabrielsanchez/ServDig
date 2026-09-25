package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLPERFORMANCECES database table.
 * 
 */
@Entity
@Table(name="ENROLLPERFORMANCECES")
@NamedQuery(name="Enrollperformancece.findAll", query="SELECT e FROM Enrollperformancece e")
public class Enrollperformancece implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollperformancecePK id;

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

	public Enrollperformancece() {
	}

	public EnrollperformancecePK getId() {
		return this.id;
	}

	public void setId(EnrollperformancecePK id) {
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

}