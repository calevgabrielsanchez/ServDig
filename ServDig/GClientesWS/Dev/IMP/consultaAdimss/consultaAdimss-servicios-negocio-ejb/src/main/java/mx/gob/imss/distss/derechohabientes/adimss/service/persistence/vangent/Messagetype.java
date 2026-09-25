package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MESSAGETYPES database table.
 * 
 */
@Entity
@Table(name="MESSAGETYPES")
@NamedQuery(name="Messagetype.findAll", query="SELECT m FROM Messagetype m")
public class Messagetype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long messagetype;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String messagetypedescription;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Messagetype() {
	}

	public long getMessagetype() {
		return this.messagetype;
	}

	public void setMessagetype(long messagetype) {
		this.messagetype = messagetype;
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

	public String getMessagetypedescription() {
		return this.messagetypedescription;
	}

	public void setMessagetypedescription(String messagetypedescription) {
		this.messagetypedescription = messagetypedescription;
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