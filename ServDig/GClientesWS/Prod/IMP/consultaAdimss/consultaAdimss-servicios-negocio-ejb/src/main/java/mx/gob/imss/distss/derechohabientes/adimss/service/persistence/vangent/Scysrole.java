package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SCYSROLES database table.
 * 
 */
@Entity
@Table(name="SCYSROLES")
@NamedQuery(name="Scysrole.findAll", query="SELECT s FROM Scysrole s")
public class Scysrole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idscysrole;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String scysrolecomment;

	private String scysrolename;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Scysuser
	@OneToMany(mappedBy="scysrole")
	private List<Scysuser> scysusers;

	public Scysrole() {
	}

	public long getIdscysrole() {
		return this.idscysrole;
	}

	public void setIdscysrole(long idscysrole) {
		this.idscysrole = idscysrole;
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

	public String getScysrolecomment() {
		return this.scysrolecomment;
	}

	public void setScysrolecomment(String scysrolecomment) {
		this.scysrolecomment = scysrolecomment;
	}

	public String getScysrolename() {
		return this.scysrolename;
	}

	public void setScysrolename(String scysrolename) {
		this.scysrolename = scysrolename;
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

	public List<Scysuser> getScysusers() {
		return this.scysusers;
	}

	public void setScysusers(List<Scysuser> scysusers) {
		this.scysusers = scysusers;
	}

	public Scysuser addScysuser(Scysuser scysuser) {
		getScysusers().add(scysuser);
		scysuser.setScysrole(this);

		return scysuser;
	}

	public Scysuser removeScysuser(Scysuser scysuser) {
		getScysusers().remove(scysuser);
		scysuser.setScysrole(null);

		return scysuser;
	}

}