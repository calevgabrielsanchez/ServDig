package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the OPERATORTRACKING database table.
 * 
 */
@Entity
@NamedQuery(name="Operatortracking.findAll", query="SELECT o FROM Operatortracking o")
public class Operatortracking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idoptrack;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idenrollmentstation;

	private BigDecimal iduser;

//	private Object optracktimestamp;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Operatortracking() {
	}

	public long getIdoptrack() {
		return this.idoptrack;
	}

	public void setIdoptrack(long idoptrack) {
		this.idoptrack = idoptrack;
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

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public BigDecimal getIduser() {
		return this.iduser;
	}

	public void setIduser(BigDecimal iduser) {
		this.iduser = iduser;
	}

//	public Object getOptracktimestamp() {
//		return this.optracktimestamp;
//	}
//
//	public void setOptracktimestamp(Object optracktimestamp) {
//		this.optracktimestamp = optracktimestamp;
//	}

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