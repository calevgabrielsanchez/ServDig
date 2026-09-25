package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the INCIDENCES database table.
 * 
 */
@Entity
@Table(name="INCIDENCES")
@NamedQuery(name="Incidence.findAll", query="SELECT i FROM Incidence i")
public class Incidence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idincidence;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String incidencename;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Impactlogistic
	@OneToMany(mappedBy="incidence")
	private List<Impactlogistic> impactlogistics;

	//bi-directional many-to-one association to Operativeincidence
	@OneToMany(mappedBy="incidence")
	private List<Operativeincidence> operativeincidences;

	public Incidence() {
	}

	public String getIdincidence() {
		return this.idincidence;
	}

	public void setIdincidence(String idincidence) {
		this.idincidence = idincidence;
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

	public String getIncidencename() {
		return this.incidencename;
	}

	public void setIncidencename(String incidencename) {
		this.incidencename = incidencename;
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

	public List<Impactlogistic> getImpactlogistics() {
		return this.impactlogistics;
	}

	public void setImpactlogistics(List<Impactlogistic> impactlogistics) {
		this.impactlogistics = impactlogistics;
	}

	public Impactlogistic addImpactlogistic(Impactlogistic impactlogistic) {
		getImpactlogistics().add(impactlogistic);
		impactlogistic.setIncidence(this);

		return impactlogistic;
	}

	public Impactlogistic removeImpactlogistic(Impactlogistic impactlogistic) {
		getImpactlogistics().remove(impactlogistic);
		impactlogistic.setIncidence(null);

		return impactlogistic;
	}

	public List<Operativeincidence> getOperativeincidences() {
		return this.operativeincidences;
	}

	public void setOperativeincidences(List<Operativeincidence> operativeincidences) {
		this.operativeincidences = operativeincidences;
	}

	public Operativeincidence addOperativeincidence(Operativeincidence operativeincidence) {
		getOperativeincidences().add(operativeincidence);
		operativeincidence.setIncidence(this);

		return operativeincidence;
	}

	public Operativeincidence removeOperativeincidence(Operativeincidence operativeincidence) {
		getOperativeincidences().remove(operativeincidence);
		operativeincidence.setIncidence(null);

		return operativeincidence;
	}

}