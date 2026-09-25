package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the VALIDATIONSTATES database table.
 * 
 */
@Entity
@Table(name="VALIDATIONSTATES")
@NamedQuery(name="Validationstate.findAll", query="SELECT v FROM Validationstate v")
public class Validationstate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idvalstate;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private String valstatename;

	//bi-directional many-to-one association to Enrollmentvalidation
	@OneToMany(mappedBy="validationstate")
	private List<Enrollmentvalidation> enrollmentvalidations;

	public Validationstate() {
	}

	public long getIdvalstate() {
		return this.idvalstate;
	}

	public void setIdvalstate(long idvalstate) {
		this.idvalstate = idvalstate;
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

	public String getValstatename() {
		return this.valstatename;
	}

	public void setValstatename(String valstatename) {
		this.valstatename = valstatename;
	}

	public List<Enrollmentvalidation> getEnrollmentvalidations() {
		return this.enrollmentvalidations;
	}

	public void setEnrollmentvalidations(List<Enrollmentvalidation> enrollmentvalidations) {
		this.enrollmentvalidations = enrollmentvalidations;
	}

	public Enrollmentvalidation addEnrollmentvalidation(Enrollmentvalidation enrollmentvalidation) {
		getEnrollmentvalidations().add(enrollmentvalidation);
		enrollmentvalidation.setValidationstate(this);

		return enrollmentvalidation;
	}

	public Enrollmentvalidation removeEnrollmentvalidation(Enrollmentvalidation enrollmentvalidation) {
		getEnrollmentvalidations().remove(enrollmentvalidation);
		enrollmentvalidation.setValidationstate(null);

		return enrollmentvalidation;
	}

}