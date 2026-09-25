package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CESTATUS database table.
 * 
 */
@Entity
@NamedQuery(name="Cestatus.findAll", query="SELECT c FROM Cestatus c")
public class Cestatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idcestatus;

	private String cestatusdescription;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentcenter
	@OneToMany(mappedBy="cestatus")
	private List<Enrollmentcenter> enrollmentcenters;

	public Cestatus() {
	}

	public long getIdcestatus() {
		return this.idcestatus;
	}

	public void setIdcestatus(long idcestatus) {
		this.idcestatus = idcestatus;
	}

	public String getCestatusdescription() {
		return this.cestatusdescription;
	}

	public void setCestatusdescription(String cestatusdescription) {
		this.cestatusdescription = cestatusdescription;
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

	public List<Enrollmentcenter> getEnrollmentcenters() {
		return this.enrollmentcenters;
	}

	public void setEnrollmentcenters(List<Enrollmentcenter> enrollmentcenters) {
		this.enrollmentcenters = enrollmentcenters;
	}

	public Enrollmentcenter addEnrollmentcenter(Enrollmentcenter enrollmentcenter) {
		getEnrollmentcenters().add(enrollmentcenter);
		enrollmentcenter.setCestatus(this);

		return enrollmentcenter;
	}

	public Enrollmentcenter removeEnrollmentcenter(Enrollmentcenter enrollmentcenter) {
		getEnrollmentcenters().remove(enrollmentcenter);
		enrollmentcenter.setCestatus(null);

		return enrollmentcenter;
	}

}