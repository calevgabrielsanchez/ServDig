package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the USERS database table.
 * 
 */
@Entity
@Table(name="USERS")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String hashcode;

	@Lob
	private byte[] privkey;

	private String status;

	@Lob
	private byte[] tplfingerprint1;

	@Lob
	private byte[] tplfingerprint2;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private String userdescription;

	private String username;

	private String userrfc;

	@Lob
	private byte[] wsqfingerprint1;

	@Lob
	private byte[] wsqfingerprint2;

	//bi-directional many-to-one association to Auditlogdetail
	@OneToMany(mappedBy="user")
	private List<Auditlogdetail> auditlogdetails;

	//bi-directional many-to-one association to Enrollmentdetail
	@OneToMany(mappedBy="user")
	private List<Enrollmentdetail> enrollmentdetails;

	//bi-directional many-to-one association to Operatorattendance
	@OneToMany(mappedBy="user")
	private List<Operatorattendance> operatorattendances;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="IDROLES")
	private Role role;

	//bi-directional many-to-one association to Userstatus
	@ManyToOne
	@JoinColumn(name="IDUSERSTATUS")
	private Userstatus userstatus;

	//bi-directional many-to-many association to Operatorsemployed
	@ManyToMany
	@JoinTable(
		name="USERSVSOPERATORS"
		, joinColumns={
			@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION"),
			@JoinColumn(name="IDUSERS", referencedColumnName="IDUSERS")
			}
		, inverseJoinColumns={
			@JoinColumn(name="IDOPERATORSEMPLOYED")
			}
		)
	private List<Operatorsemployed> operatorsemployeds;

	public User() {
	}

	public UserPK getId() {
		return this.id;
	}

	public void setId(UserPK id) {
		this.id = id;
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

	public String getHashcode() {
		return this.hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public byte[] getPrivkey() {
		return this.privkey;
	}

	public void setPrivkey(byte[] privkey) {
		this.privkey = privkey;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public byte[] getTplfingerprint1() {
		return this.tplfingerprint1;
	}

	public void setTplfingerprint1(byte[] tplfingerprint1) {
		this.tplfingerprint1 = tplfingerprint1;
	}

	public byte[] getTplfingerprint2() {
		return this.tplfingerprint2;
	}

	public void setTplfingerprint2(byte[] tplfingerprint2) {
		this.tplfingerprint2 = tplfingerprint2;
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

	public String getUserdescription() {
		return this.userdescription;
	}

	public void setUserdescription(String userdescription) {
		this.userdescription = userdescription;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserrfc() {
		return this.userrfc;
	}

	public void setUserrfc(String userrfc) {
		this.userrfc = userrfc;
	}

	public byte[] getWsqfingerprint1() {
		return this.wsqfingerprint1;
	}

	public void setWsqfingerprint1(byte[] wsqfingerprint1) {
		this.wsqfingerprint1 = wsqfingerprint1;
	}

	public byte[] getWsqfingerprint2() {
		return this.wsqfingerprint2;
	}

	public void setWsqfingerprint2(byte[] wsqfingerprint2) {
		this.wsqfingerprint2 = wsqfingerprint2;
	}

	public List<Auditlogdetail> getAuditlogdetails() {
		return this.auditlogdetails;
	}

	public void setAuditlogdetails(List<Auditlogdetail> auditlogdetails) {
		this.auditlogdetails = auditlogdetails;
	}

	public Auditlogdetail addAuditlogdetail(Auditlogdetail auditlogdetail) {
		getAuditlogdetails().add(auditlogdetail);
		auditlogdetail.setUser(this);

		return auditlogdetail;
	}

	public Auditlogdetail removeAuditlogdetail(Auditlogdetail auditlogdetail) {
		getAuditlogdetails().remove(auditlogdetail);
		auditlogdetail.setUser(null);

		return auditlogdetail;
	}

	public List<Enrollmentdetail> getEnrollmentdetails() {
		return this.enrollmentdetails;
	}

	public void setEnrollmentdetails(List<Enrollmentdetail> enrollmentdetails) {
		this.enrollmentdetails = enrollmentdetails;
	}

	public Enrollmentdetail addEnrollmentdetail(Enrollmentdetail enrollmentdetail) {
		getEnrollmentdetails().add(enrollmentdetail);
		enrollmentdetail.setUser(this);

		return enrollmentdetail;
	}

	public Enrollmentdetail removeEnrollmentdetail(Enrollmentdetail enrollmentdetail) {
		getEnrollmentdetails().remove(enrollmentdetail);
		enrollmentdetail.setUser(null);

		return enrollmentdetail;
	}

	public List<Operatorattendance> getOperatorattendances() {
		return this.operatorattendances;
	}

	public void setOperatorattendances(List<Operatorattendance> operatorattendances) {
		this.operatorattendances = operatorattendances;
	}

	public Operatorattendance addOperatorattendance(Operatorattendance operatorattendance) {
		getOperatorattendances().add(operatorattendance);
		operatorattendance.setUser(this);

		return operatorattendance;
	}

	public Operatorattendance removeOperatorattendance(Operatorattendance operatorattendance) {
		getOperatorattendances().remove(operatorattendance);
		operatorattendance.setUser(null);

		return operatorattendance;
	}

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Userstatus getUserstatus() {
		return this.userstatus;
	}

	public void setUserstatus(Userstatus userstatus) {
		this.userstatus = userstatus;
	}

	public List<Operatorsemployed> getOperatorsemployeds() {
		return this.operatorsemployeds;
	}

	public void setOperatorsemployeds(List<Operatorsemployed> operatorsemployeds) {
		this.operatorsemployeds = operatorsemployeds;
	}

}