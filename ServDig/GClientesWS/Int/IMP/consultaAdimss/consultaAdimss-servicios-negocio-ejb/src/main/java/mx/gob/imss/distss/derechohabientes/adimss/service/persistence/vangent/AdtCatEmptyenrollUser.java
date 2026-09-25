package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ADT_CAT_EMPTYENROLL_USERS database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_EMPTYENROLL_USERS")
@NamedQuery(name="AdtCatEmptyenrollUser.findAll", query="SELECT a FROM AdtCatEmptyenrollUser a")
public class AdtCatEmptyenrollUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idemptyenrolluser;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String cveuser;

	private String descenroluser;

	private BigDecimal statususer;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public AdtCatEmptyenrollUser() {
	}

	public long getIdemptyenrolluser() {
		return this.idemptyenrolluser;
	}

	public void setIdemptyenrolluser(long idemptyenrolluser) {
		this.idemptyenrolluser = idemptyenrolluser;
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

	public String getCveuser() {
		return this.cveuser;
	}

	public void setCveuser(String cveuser) {
		this.cveuser = cveuser;
	}

	public String getDescenroluser() {
		return this.descenroluser;
	}

	public void setDescenroluser(String descenroluser) {
		this.descenroluser = descenroluser;
	}

	public BigDecimal getStatususer() {
		return this.statususer;
	}

	public void setStatususer(BigDecimal statususer) {
		this.statususer = statususer;
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