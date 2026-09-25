package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTDEVICES database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTDEVICES")
@NamedQuery(name="Enrollmentdevice.findAll", query="SELECT e FROM Enrollmentdevice e")
public class Enrollmentdevice implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentdevicePK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String enroldevicedescription;

	private BigDecimal enroldevicelifetime;

	private String enroldevicename;

	private BigDecimal updateby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Application
	@ManyToOne
	@JoinColumn(name="IDAPPLICATIONS")
	private Application application;

	public Enrollmentdevice() {
	}

	public EnrollmentdevicePK getId() {
		return this.id;
	}

	public void setId(EnrollmentdevicePK id) {
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

	public String getEnroldevicedescription() {
		return this.enroldevicedescription;
	}

	public void setEnroldevicedescription(String enroldevicedescription) {
		this.enroldevicedescription = enroldevicedescription;
	}

	public BigDecimal getEnroldevicelifetime() {
		return this.enroldevicelifetime;
	}

	public void setEnroldevicelifetime(BigDecimal enroldevicelifetime) {
		this.enroldevicelifetime = enroldevicelifetime;
	}

	public String getEnroldevicename() {
		return this.enroldevicename;
	}

	public void setEnroldevicename(String enroldevicename) {
		this.enroldevicename = enroldevicename;
	}

	public BigDecimal getUpdateby() {
		return this.updateby;
	}

	public void setUpdateby(BigDecimal updateby) {
		this.updateby = updateby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public Application getApplication() {
		return this.application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}