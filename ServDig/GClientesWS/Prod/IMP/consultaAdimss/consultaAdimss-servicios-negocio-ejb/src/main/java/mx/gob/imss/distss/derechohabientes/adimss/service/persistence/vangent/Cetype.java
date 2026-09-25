package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CETYPE database table.
 * 
 */
@Entity
@NamedQuery(name="Cetype.findAll", query="SELECT c FROM Cetype c")
public class Cetype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idcetype;

	private String cetypedescription;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentcenter
	@OneToMany(mappedBy="cetype")
	private List<Enrollmentcenter> enrollmentcenters;

	//bi-directional many-to-one association to Hoursoperationsla6sum
	@OneToMany(mappedBy="cetype")
	private List<Hoursoperationsla6sum> hoursoperationsla6sums;

	public Cetype() {
	}

	public long getIdcetype() {
		return this.idcetype;
	}

	public void setIdcetype(long idcetype) {
		this.idcetype = idcetype;
	}

	public String getCetypedescription() {
		return this.cetypedescription;
	}

	public void setCetypedescription(String cetypedescription) {
		this.cetypedescription = cetypedescription;
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
		enrollmentcenter.setCetype(this);

		return enrollmentcenter;
	}

	public Enrollmentcenter removeEnrollmentcenter(Enrollmentcenter enrollmentcenter) {
		getEnrollmentcenters().remove(enrollmentcenter);
		enrollmentcenter.setCetype(null);

		return enrollmentcenter;
	}

	public List<Hoursoperationsla6sum> getHoursoperationsla6sums() {
		return this.hoursoperationsla6sums;
	}

	public void setHoursoperationsla6sums(List<Hoursoperationsla6sum> hoursoperationsla6sums) {
		this.hoursoperationsla6sums = hoursoperationsla6sums;
	}

	public Hoursoperationsla6sum addHoursoperationsla6sum(Hoursoperationsla6sum hoursoperationsla6sum) {
		getHoursoperationsla6sums().add(hoursoperationsla6sum);
		hoursoperationsla6sum.setCetype(this);

		return hoursoperationsla6sum;
	}

	public Hoursoperationsla6sum removeHoursoperationsla6sum(Hoursoperationsla6sum hoursoperationsla6sum) {
		getHoursoperationsla6sums().remove(hoursoperationsla6sum);
		hoursoperationsla6sum.setCetype(null);

		return hoursoperationsla6sum;
	}

}