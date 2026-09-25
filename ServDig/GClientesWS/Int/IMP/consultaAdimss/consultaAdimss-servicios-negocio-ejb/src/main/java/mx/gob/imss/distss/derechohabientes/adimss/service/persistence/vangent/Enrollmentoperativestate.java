package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTOPERATIVESTATES database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTOPERATIVESTATES")
@NamedQuery(name="Enrollmentoperativestate.findAll", query="SELECT e FROM Enrollmentoperativestate e")
public class Enrollmentoperativestate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrolopstate;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String enrolopstatedescription;

	private String enrolopstatename;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Enrollmentoperativestate() {
	}

	public long getIdenrolopstate() {
		return this.idenrolopstate;
	}

	public void setIdenrolopstate(long idenrolopstate) {
		this.idenrolopstate = idenrolopstate;
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

	public String getEnrolopstatedescription() {
		return this.enrolopstatedescription;
	}

	public void setEnrolopstatedescription(String enrolopstatedescription) {
		this.enrolopstatedescription = enrolopstatedescription;
	}

	public String getEnrolopstatename() {
		return this.enrolopstatename;
	}

	public void setEnrolopstatename(String enrolopstatename) {
		this.enrolopstatename = enrolopstatename;
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