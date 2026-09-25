package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CARELEVELS database table.
 * 
 */
@Entity
@Table(name="CARELEVELS")
@NamedQuery(name="Carelevel.findAll", query="SELECT c FROM Carelevel c")
public class Carelevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idcarelevel;

	private String careleveldesc;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Carelevel() {
	}

	public long getIdcarelevel() {
		return this.idcarelevel;
	}

	public void setIdcarelevel(long idcarelevel) {
		this.idcarelevel = idcarelevel;
	}

	public String getCareleveldesc() {
		return this.careleveldesc;
	}

	public void setCareleveldesc(String careleveldesc) {
		this.careleveldesc = careleveldesc;
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