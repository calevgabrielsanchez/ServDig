package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the OPERATORSEMPLOYED database table.
 * 
 */
@Entity
@NamedQuery(name="Operatorsemployed.findAll", query="SELECT o FROM Operatorsemployed o")
public class Operatorsemployed implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idoperatorsemployed;

	private String address;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String email;

	@Temporal(TemporalType.DATE)
	private Date firedate;

	private String firstname;

	@Temporal(TemporalType.DATE)
	private Date hiredate;

	private BigDecimal idcompany;

	private BigDecimal idoperatorposition;

	private String lastname;

	private String maidenname;

	private String phonenumber;

	private String rfc;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private BigDecimal zipcode;

	//bi-directional many-to-one association to State
	@ManyToOne
	@JoinColumn(name="IDSTATE")
	private State state;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="operatorsemployeds")
	private List<User> users;

	public Operatorsemployed() {
	}

	public String getIdoperatorsemployed() {
		return this.idoperatorsemployed;
	}

	public void setIdoperatorsemployed(String idoperatorsemployed) {
		this.idoperatorsemployed = idoperatorsemployed;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFiredate() {
		return this.firedate;
	}

	public void setFiredate(Date firedate) {
		this.firedate = firedate;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Date getHiredate() {
		return this.hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public BigDecimal getIdcompany() {
		return this.idcompany;
	}

	public void setIdcompany(BigDecimal idcompany) {
		this.idcompany = idcompany;
	}

	public BigDecimal getIdoperatorposition() {
		return this.idoperatorposition;
	}

	public void setIdoperatorposition(BigDecimal idoperatorposition) {
		this.idoperatorposition = idoperatorposition;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMaidenname() {
		return this.maidenname;
	}

	public void setMaidenname(String maidenname) {
		this.maidenname = maidenname;
	}

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
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

	public BigDecimal getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(BigDecimal zipcode) {
		this.zipcode = zipcode;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}