package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the USERSTATUSES database table.
 * 
 */
@Entity
@Table(name="USERSTATUSES")
@NamedQuery(name="Userstatus.findAll", query="SELECT u FROM Userstatus u")
public class Userstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long iduserstatus;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private String userstatusdescription;

	private String userstatusname;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="userstatus")
	private List<User> users;

	public Userstatus() {
	}

	public long getIduserstatus() {
		return this.iduserstatus;
	}

	public void setIduserstatus(long iduserstatus) {
		this.iduserstatus = iduserstatus;
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

	public String getUserstatusdescription() {
		return this.userstatusdescription;
	}

	public void setUserstatusdescription(String userstatusdescription) {
		this.userstatusdescription = userstatusdescription;
	}

	public String getUserstatusname() {
		return this.userstatusname;
	}

	public void setUserstatusname(String userstatusname) {
		this.userstatusname = userstatusname;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUserstatus(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserstatus(null);

		return user;
	}

}