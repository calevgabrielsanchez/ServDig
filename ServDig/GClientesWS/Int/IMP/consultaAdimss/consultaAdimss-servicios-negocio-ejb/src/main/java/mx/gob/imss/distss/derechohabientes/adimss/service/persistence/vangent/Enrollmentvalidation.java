package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTVALIDATIONS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTVALIDATIONS")
@NamedQuery(name="Enrollmentvalidation.findAll", query="SELECT e FROM Enrollmentvalidation e")
public class Enrollmentvalidation implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentvalidationPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentvaldatadet
	@OneToMany(mappedBy="enrollmentvalidation")
	private List<Enrollmentvaldatadet> enrollmentvaldatadets;

	//bi-directional many-to-one association to Validationstate
	@ManyToOne
	@JoinColumn(name="IDVALSTATE")
	private Validationstate validationstate;

	//bi-directional many-to-one association to Enrollmentvalimagdet
	@OneToMany(mappedBy="enrollmentvalidation")
	private List<Enrollmentvalimagdet> enrollmentvalimagdets;

	public Enrollmentvalidation() {
	}

	public EnrollmentvalidationPK getId() {
		return this.id;
	}

	public void setId(EnrollmentvalidationPK id) {
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

	public List<Enrollmentvaldatadet> getEnrollmentvaldatadets() {
		return this.enrollmentvaldatadets;
	}

	public void setEnrollmentvaldatadets(List<Enrollmentvaldatadet> enrollmentvaldatadets) {
		this.enrollmentvaldatadets = enrollmentvaldatadets;
	}

	public Enrollmentvaldatadet addEnrollmentvaldatadet(Enrollmentvaldatadet enrollmentvaldatadet) {
		getEnrollmentvaldatadets().add(enrollmentvaldatadet);
		enrollmentvaldatadet.setEnrollmentvalidation(this);

		return enrollmentvaldatadet;
	}

	public Enrollmentvaldatadet removeEnrollmentvaldatadet(Enrollmentvaldatadet enrollmentvaldatadet) {
		getEnrollmentvaldatadets().remove(enrollmentvaldatadet);
		enrollmentvaldatadet.setEnrollmentvalidation(null);

		return enrollmentvaldatadet;
	}

	public Validationstate getValidationstate() {
		return this.validationstate;
	}

	public void setValidationstate(Validationstate validationstate) {
		this.validationstate = validationstate;
	}

	public List<Enrollmentvalimagdet> getEnrollmentvalimagdets() {
		return this.enrollmentvalimagdets;
	}

	public void setEnrollmentvalimagdets(List<Enrollmentvalimagdet> enrollmentvalimagdets) {
		this.enrollmentvalimagdets = enrollmentvalimagdets;
	}

	public Enrollmentvalimagdet addEnrollmentvalimagdet(Enrollmentvalimagdet enrollmentvalimagdet) {
		getEnrollmentvalimagdets().add(enrollmentvalimagdet);
		enrollmentvalimagdet.setEnrollmentvalidation(this);

		return enrollmentvalimagdet;
	}

	public Enrollmentvalimagdet removeEnrollmentvalimagdet(Enrollmentvalimagdet enrollmentvalimagdet) {
		getEnrollmentvalimagdets().remove(enrollmentvalimagdet);
		enrollmentvalimagdet.setEnrollmentvalidation(null);

		return enrollmentvalimagdet;
	}

}