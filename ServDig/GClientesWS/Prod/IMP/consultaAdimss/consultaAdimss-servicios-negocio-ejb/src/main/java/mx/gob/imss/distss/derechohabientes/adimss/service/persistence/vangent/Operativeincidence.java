package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the OPERATIVEINCIDENCES database table.
 * 
 */
@Entity
@Table(name="OPERATIVEINCIDENCES")
@NamedQuery(name="Operativeincidence.findAll", query="SELECT o FROM Operativeincidence o")
public class Operativeincidence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idoperativeincidence;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String incidencecover;

	@Temporal(TemporalType.DATE)
	private Date operativeincidencesdate;

	private String operativeincidencesdesc;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private String whocover;

	//bi-directional many-to-one association to Attendance
	@ManyToOne
	@JoinColumn(name="IDATTENDANCE")
	private Attendance attendance;

	//bi-directional many-to-one association to Incidence
	@ManyToOne
	@JoinColumn(name="IDINCIDENCE")
	private Incidence incidence;

	public Operativeincidence() {
	}

	public long getIdoperativeincidence() {
		return this.idoperativeincidence;
	}

	public void setIdoperativeincidence(long idoperativeincidence) {
		this.idoperativeincidence = idoperativeincidence;
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

	public String getIncidencecover() {
		return this.incidencecover;
	}

	public void setIncidencecover(String incidencecover) {
		this.incidencecover = incidencecover;
	}

	public Date getOperativeincidencesdate() {
		return this.operativeincidencesdate;
	}

	public void setOperativeincidencesdate(Date operativeincidencesdate) {
		this.operativeincidencesdate = operativeincidencesdate;
	}

	public String getOperativeincidencesdesc() {
		return this.operativeincidencesdesc;
	}

	public void setOperativeincidencesdesc(String operativeincidencesdesc) {
		this.operativeincidencesdesc = operativeincidencesdesc;
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

	public String getWhocover() {
		return this.whocover;
	}

	public void setWhocover(String whocover) {
		this.whocover = whocover;
	}

	public Attendance getAttendance() {
		return this.attendance;
	}

	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}

	public Incidence getIncidence() {
		return this.incidence;
	}

	public void setIncidence(Incidence incidence) {
		this.incidence = incidence;
	}

}