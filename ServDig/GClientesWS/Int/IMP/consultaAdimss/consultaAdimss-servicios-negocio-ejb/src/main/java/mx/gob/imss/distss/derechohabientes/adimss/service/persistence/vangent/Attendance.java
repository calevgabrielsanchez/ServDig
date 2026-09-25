package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ATTENDANCES database table.
 * 
 */
@Entity
@Table(name="ATTENDANCES")
@NamedQuery(name="Attendance.findAll", query="SELECT a FROM Attendance a")
public class Attendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idattendance;

	@Temporal(TemporalType.DATE)
	private Date attendancedate;

//	private Object checkin;

//	private Object checkout;

	private String comments;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idattendance2nd;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Operatordetail
	@ManyToOne
	@JoinColumn(name="IDOPERATORDETAIL")
	private Operatordetail operatordetail;

	//bi-directional many-to-one association to Operativeincidence
	@OneToMany(mappedBy="attendance")
	private List<Operativeincidence> operativeincidences;

	public Attendance() {
	}

	public long getIdattendance() {
		return this.idattendance;
	}

	public void setIdattendance(long idattendance) {
		this.idattendance = idattendance;
	}

	public Date getAttendancedate() {
		return this.attendancedate;
	}

	public void setAttendancedate(Date attendancedate) {
		this.attendancedate = attendancedate;
	}

//	public Object getCheckin() {
//		return this.checkin;
//	}
//
//	public void setCheckin(Object checkin) {
//		this.checkin = checkin;
//	}
//
//	public Object getCheckout() {
//		return this.checkout;
//	}
//
//	public void setCheckout(Object checkout) {
//		this.checkout = checkout;
//	}

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

	public BigDecimal getIdattendance2nd() {
		return this.idattendance2nd;
	}

	public void setIdattendance2nd(BigDecimal idattendance2nd) {
		this.idattendance2nd = idattendance2nd;
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

	public Operatordetail getOperatordetail() {
		return this.operatordetail;
	}

	public void setOperatordetail(Operatordetail operatordetail) {
		this.operatordetail = operatordetail;
	}

	public List<Operativeincidence> getOperativeincidences() {
		return this.operativeincidences;
	}

	public void setOperativeincidences(List<Operativeincidence> operativeincidences) {
		this.operativeincidences = operativeincidences;
	}

	public Operativeincidence addOperativeincidence(Operativeincidence operativeincidence) {
		getOperativeincidences().add(operativeincidence);
		operativeincidence.setAttendance(this);

		return operativeincidence;
	}

	public Operativeincidence removeOperativeincidence(Operativeincidence operativeincidence) {
		getOperativeincidences().remove(operativeincidence);
		operativeincidence.setAttendance(null);

		return operativeincidence;
	}

}