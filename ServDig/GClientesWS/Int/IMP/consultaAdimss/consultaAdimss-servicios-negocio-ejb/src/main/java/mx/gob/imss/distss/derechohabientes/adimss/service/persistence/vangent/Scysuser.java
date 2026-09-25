package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SCYSUSERS database table.
 * 
 */
@Entity
@Table(name="SCYSUSERS")
@NamedQuery(name="Scysuser.findAll", query="SELECT s FROM Scysuser s")
public class Scysuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idscysuser;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String scysusername;

	private String scysuserpassword;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Scysrole
	@ManyToOne
	@JoinColumn(name="IDSCYSROLE")
	private Scysrole scysrole;

	public Scysuser() {
	}

	public long getIdscysuser() {
		return this.idscysuser;
	}

	public void setIdscysuser(long idscysuser) {
		this.idscysuser = idscysuser;
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

	public String getScysusername() {
		return this.scysusername;
	}

	public void setScysusername(String scysusername) {
		this.scysusername = scysusername;
	}

	public String getScysuserpassword() {
		return this.scysuserpassword;
	}

	public void setScysuserpassword(String scysuserpassword) {
		this.scysuserpassword = scysuserpassword;
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

	public Scysrole getScysrole() {
		return this.scysrole;
	}

	public void setScysrole(Scysrole scysrole) {
		this.scysrole = scysrole;
	}

}