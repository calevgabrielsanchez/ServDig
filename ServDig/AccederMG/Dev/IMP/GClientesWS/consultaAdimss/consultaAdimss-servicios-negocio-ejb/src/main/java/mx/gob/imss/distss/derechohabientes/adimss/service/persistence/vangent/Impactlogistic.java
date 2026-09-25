package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the IMPACTLOGISTICS database table.
 * 
 */
@Entity
@Table(name="IMPACTLOGISTICS")
@NamedQuery(name="Impactlogistic.findAll", query="SELECT i FROM Impactlogistic i")
public class Impactlogistic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idimpactlogistic;

	private String concept;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date impactlogisticdate;

	private String impactlogisticsdesc;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Incidence
	@ManyToOne
	@JoinColumn(name="IDINCIDENCE")
	private Incidence incidence;

	//bi-directional many-to-one association to Movement
	@OneToMany(mappedBy="impactlogistic")
	private List<Movement> movements;

	public Impactlogistic() {
	}

	public long getIdimpactlogistic() {
		return this.idimpactlogistic;
	}

	public void setIdimpactlogistic(long idimpactlogistic) {
		this.idimpactlogistic = idimpactlogistic;
	}

	public String getConcept() {
		return this.concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
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

	public Date getImpactlogisticdate() {
		return this.impactlogisticdate;
	}

	public void setImpactlogisticdate(Date impactlogisticdate) {
		this.impactlogisticdate = impactlogisticdate;
	}

	public String getImpactlogisticsdesc() {
		return this.impactlogisticsdesc;
	}

	public void setImpactlogisticsdesc(String impactlogisticsdesc) {
		this.impactlogisticsdesc = impactlogisticsdesc;
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

	public Incidence getIncidence() {
		return this.incidence;
	}

	public void setIncidence(Incidence incidence) {
		this.incidence = incidence;
	}

	public List<Movement> getMovements() {
		return this.movements;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}

	public Movement addMovement(Movement movement) {
		getMovements().add(movement);
		movement.setImpactlogistic(this);

		return movement;
	}

	public Movement removeMovement(Movement movement) {
		getMovements().remove(movement);
		movement.setImpactlogistic(null);

		return movement;
	}

}