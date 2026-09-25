package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the AVAILABILITYSTATUS database table.
 * 
 */
@Entity
@NamedQuery(name="Availabilitystatus.findAll", query="SELECT a FROM Availabilitystatus a")
public class Availabilitystatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idavailstatus;

	private String availstatusname;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Availabilitystatus() {
	}

	public long getIdavailstatus() {
		return this.idavailstatus;
	}

	public void setIdavailstatus(long idavailstatus) {
		this.idavailstatus = idavailstatus;
	}

	public String getAvailstatusname() {
		return this.availstatusname;
	}

	public void setAvailstatusname(String availstatusname) {
		this.availstatusname = availstatusname;
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