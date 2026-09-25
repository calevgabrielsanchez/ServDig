package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CESSPECIALTIMES database table.
 * 
 */
@Entity
@Table(name="CESSPECIALTIMES")
@NamedQuery(name="Cesspecialtime.findAll", query="SELECT c FROM Cesspecialtime c")
public class Cesspecialtime implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idcesspecialtimes;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal cvedelegation;

	private String endtime;

	private BigDecimal idenrollmentstation;

	@Column(name="MM_RR")
	private String mmRr;

	private String starttime;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Cesspecialtime() {
	}

	public long getIdcesspecialtimes() {
		return this.idcesspecialtimes;
	}

	public void setIdcesspecialtimes(long idcesspecialtimes) {
		this.idcesspecialtimes = idcesspecialtimes;
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

	public BigDecimal getCvedelegation() {
		return this.cvedelegation;
	}

	public void setCvedelegation(BigDecimal cvedelegation) {
		this.cvedelegation = cvedelegation;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public String getMmRr() {
		return this.mmRr;
	}

	public void setMmRr(String mmRr) {
		this.mmRr = mmRr;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
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