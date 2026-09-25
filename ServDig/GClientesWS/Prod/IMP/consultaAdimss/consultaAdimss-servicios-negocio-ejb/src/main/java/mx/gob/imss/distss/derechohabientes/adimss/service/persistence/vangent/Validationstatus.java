package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the VALIDATIONSTATUS database table.
 * 
 */
@Entity
@NamedQuery(name="Validationstatus.findAll", query="SELECT v FROM Validationstatus v")
public class Validationstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idvalstatus;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private String valstatename;

	public Validationstatus() {
	}

	public long getIdvalstatus() {
		return this.idvalstatus;
	}

	public void setIdvalstatus(long idvalstatus) {
		this.idvalstatus = idvalstatus;
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

	public String getValstatename() {
		return this.valstatename;
	}

	public void setValstatename(String valstatename) {
		this.valstatename = valstatename;
	}

}