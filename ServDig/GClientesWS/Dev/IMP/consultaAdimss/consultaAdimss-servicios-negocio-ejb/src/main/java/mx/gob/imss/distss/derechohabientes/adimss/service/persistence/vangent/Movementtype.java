package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the MOVEMENTTYPES database table.
 * 
 */
@Entity
@Table(name="MOVEMENTTYPES")
@NamedQuery(name="Movementtype.findAll", query="SELECT m FROM Movementtype m")
public class Movementtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idmovementtype;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String movementtypedesc;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Movement
	@OneToMany(mappedBy="movementtype")
	private List<Movement> movements;

	public Movementtype() {
	}

	public long getIdmovementtype() {
		return this.idmovementtype;
	}

	public void setIdmovementtype(long idmovementtype) {
		this.idmovementtype = idmovementtype;
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

	public String getMovementtypedesc() {
		return this.movementtypedesc;
	}

	public void setMovementtypedesc(String movementtypedesc) {
		this.movementtypedesc = movementtypedesc;
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

	public List<Movement> getMovements() {
		return this.movements;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}

	public Movement addMovement(Movement movement) {
		getMovements().add(movement);
		movement.setMovementtype(this);

		return movement;
	}

	public Movement removeMovement(Movement movement) {
		getMovements().remove(movement);
		movement.setMovementtype(null);

		return movement;
	}

}