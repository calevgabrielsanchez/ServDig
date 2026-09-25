package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the HOURSOPERATIONSLA6 database table.
 * 
 */
@Entity
@NamedQuery(name="Hoursoperationsla6.findAll", query="SELECT h FROM Hoursoperationsla6 h")
public class Hoursoperationsla6 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idhoursoperationsla6;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date hoursoperationdate;

	private BigDecimal idenrollmentstation;

	@Temporal(TemporalType.DATE)
	private Date indate;

	@Temporal(TemporalType.DATE)
	private Date outdate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Justificationtype
	@ManyToOne
	@JoinColumn(name="IDJUSTIFICATIONTYPE")
	private Justificationtype justificationtype;

	public Hoursoperationsla6() {
	}

	public long getIdhoursoperationsla6() {
		return this.idhoursoperationsla6;
	}

	public void setIdhoursoperationsla6(long idhoursoperationsla6) {
		this.idhoursoperationsla6 = idhoursoperationsla6;
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

	public Date getHoursoperationdate() {
		return this.hoursoperationdate;
	}

	public void setHoursoperationdate(Date hoursoperationdate) {
		this.hoursoperationdate = hoursoperationdate;
	}

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public Date getIndate() {
		return this.indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public Date getOutdate() {
		return this.outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
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

	public Justificationtype getJustificationtype() {
		return this.justificationtype;
	}

	public void setJustificationtype(Justificationtype justificationtype) {
		this.justificationtype = justificationtype;
	}

}