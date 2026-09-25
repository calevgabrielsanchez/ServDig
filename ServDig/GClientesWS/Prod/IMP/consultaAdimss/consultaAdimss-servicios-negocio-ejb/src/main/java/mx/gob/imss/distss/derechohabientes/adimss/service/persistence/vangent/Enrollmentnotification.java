package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTNOTIFICATIONS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTNOTIFICATIONS")
@NamedQuery(name="Enrollmentnotification.findAll", query="SELECT e FROM Enrollmentnotification e")
public class Enrollmentnotification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrolnotification;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String idenrol;

	@Temporal(TemporalType.DATE)
	private Date notificationdate;

	private String notificationdescription;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	//bi-directional many-to-one association to Notificationtype
	@ManyToOne
	@JoinColumn(name="IDNOTIFICATIONTYPE")
	private Notificationtype notificationtype;

	public Enrollmentnotification() {
	}

	public long getIdenrolnotification() {
		return this.idenrolnotification;
	}

	public void setIdenrolnotification(long idenrolnotification) {
		this.idenrolnotification = idenrolnotification;
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

	public String getIdenrol() {
		return this.idenrol;
	}

	public void setIdenrol(String idenrol) {
		this.idenrol = idenrol;
	}

	public Date getNotificationdate() {
		return this.notificationdate;
	}

	public void setNotificationdate(Date notificationdate) {
		this.notificationdate = notificationdate;
	}

	public String getNotificationdescription() {
		return this.notificationdescription;
	}

	public void setNotificationdescription(String notificationdescription) {
		this.notificationdescription = notificationdescription;
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

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

	public Notificationtype getNotificationtype() {
		return this.notificationtype;
	}

	public void setNotificationtype(Notificationtype notificationtype) {
		this.notificationtype = notificationtype;
	}

}