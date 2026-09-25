package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the IMSSROLES database table.
 * 
 */
@Entity
@Table(name="IMSSROLES")
@NamedQuery(name="Imssrole.findAll", query="SELECT i FROM Imssrole i")
public class Imssrole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idroles;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String rolecomment;

	private String rolename;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Operatordetail
	@OneToMany(mappedBy="imssrole")
	private List<Operatordetail> operatordetails;

	public Imssrole() {
	}

	public long getIdroles() {
		return this.idroles;
	}

	public void setIdroles(long idroles) {
		this.idroles = idroles;
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

	public String getRolecomment() {
		return this.rolecomment;
	}

	public void setRolecomment(String rolecomment) {
		this.rolecomment = rolecomment;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
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

	public List<Operatordetail> getOperatordetails() {
		return this.operatordetails;
	}

	public void setOperatordetails(List<Operatordetail> operatordetails) {
		this.operatordetails = operatordetails;
	}

	public Operatordetail addOperatordetail(Operatordetail operatordetail) {
		getOperatordetails().add(operatordetail);
		operatordetail.setImssrole(this);

		return operatordetail;
	}

	public Operatordetail removeOperatordetail(Operatordetail operatordetail) {
		getOperatordetails().remove(operatordetail);
		operatordetail.setImssrole(null);

		return operatordetail;
	}

}