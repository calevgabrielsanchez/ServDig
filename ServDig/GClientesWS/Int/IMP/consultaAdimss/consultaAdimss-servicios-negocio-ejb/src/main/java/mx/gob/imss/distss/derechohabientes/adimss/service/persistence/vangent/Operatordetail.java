package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the OPERATORDETAILS database table.
 * 
 */
@Entity
@Table(name="OPERATORDETAILS")
@NamedQuery(name="Operatordetail.findAll", query="SELECT o FROM Operatordetail o")
public class Operatordetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idoperatordetail;

	private BigDecimal active;

	private String comments;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String email;

	private String fire;

	@Temporal(TemporalType.DATE)
	private Date firedate;

//	private Object forwardtime;

	@Temporal(TemporalType.DATE)
	private Date hiredate;

	private BigDecimal idscysuser;

	private String iduserpayroll;

	private String movilnumber;

	private String operatorname;

	private String phonenumber;

	private String rfc;

//	private Object tolerancetime;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private String whohire;

	//bi-directional many-to-one association to Additionaltime
	@OneToMany(mappedBy="operatordetail")
	private List<Additionaltime> additionaltimes;

	//bi-directional many-to-one association to Attendance
	@OneToMany(mappedBy="operatordetail")
	private List<Attendance> attendances;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDUMF")
	private Enrollmentstation enrollmentstation;

	//bi-directional many-to-one association to Imssrole
	@ManyToOne
	@JoinColumn(name="IDIMSSROLE")
	private Imssrole imssrole;

	//bi-directional many-to-one association to Shift
	@ManyToOne
	@JoinColumn(name="SHIFT")
	private Shift shiftBean;

	public Operatordetail() {
	}

	public String getIdoperatordetail() {
		return this.idoperatordetail;
	}

	public void setIdoperatordetail(String idoperatordetail) {
		this.idoperatordetail = idoperatordetail;
	}

	public BigDecimal getActive() {
		return this.active;
	}

	public void setActive(BigDecimal active) {
		this.active = active;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getFire() {
		return this.fire;
	}

	public void setFire(String fire) {
		this.fire = fire;
	}

	public Date getFiredate() {
		return this.firedate;
	}

	public void setFiredate(Date firedate) {
		this.firedate = firedate;
	}

//	public Object getForwardtime() {
//		return this.forwardtime;
//	}
//
//	public void setForwardtime(Object forwardtime) {
//		this.forwardtime = forwardtime;
//	}

	public Date getHiredate() {
		return this.hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public BigDecimal getIdscysuser() {
		return this.idscysuser;
	}

	public void setIdscysuser(BigDecimal idscysuser) {
		this.idscysuser = idscysuser;
	}

	public String getIduserpayroll() {
		return this.iduserpayroll;
	}

	public void setIduserpayroll(String iduserpayroll) {
		this.iduserpayroll = iduserpayroll;
	}

	public String getMovilnumber() {
		return this.movilnumber;
	}

	public void setMovilnumber(String movilnumber) {
		this.movilnumber = movilnumber;
	}

	public String getOperatorname() {
		return this.operatorname;
	}

	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
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

//	public Object getTolerancetime() {
//		return this.tolerancetime;
//	}
//
//	public void setTolerancetime(Object tolerancetime) {
//		this.tolerancetime = tolerancetime;
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

	public String getWhohire() {
		return this.whohire;
	}

	public void setWhohire(String whohire) {
		this.whohire = whohire;
	}

	public List<Additionaltime> getAdditionaltimes() {
		return this.additionaltimes;
	}

	public void setAdditionaltimes(List<Additionaltime> additionaltimes) {
		this.additionaltimes = additionaltimes;
	}

	public Additionaltime addAdditionaltime(Additionaltime additionaltime) {
		getAdditionaltimes().add(additionaltime);
		additionaltime.setOperatordetail(this);

		return additionaltime;
	}

	public Additionaltime removeAdditionaltime(Additionaltime additionaltime) {
		getAdditionaltimes().remove(additionaltime);
		additionaltime.setOperatordetail(null);

		return additionaltime;
	}

	public List<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public Attendance addAttendance(Attendance attendance) {
		getAttendances().add(attendance);
		attendance.setOperatordetail(this);

		return attendance;
	}

	public Attendance removeAttendance(Attendance attendance) {
		getAttendances().remove(attendance);
		attendance.setOperatordetail(null);

		return attendance;
	}

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

	public Imssrole getImssrole() {
		return this.imssrole;
	}

	public void setImssrole(Imssrole imssrole) {
		this.imssrole = imssrole;
	}

	public Shift getShiftBean() {
		return this.shiftBean;
	}

	public void setShiftBean(Shift shiftBean) {
		this.shiftBean = shiftBean;
	}

}