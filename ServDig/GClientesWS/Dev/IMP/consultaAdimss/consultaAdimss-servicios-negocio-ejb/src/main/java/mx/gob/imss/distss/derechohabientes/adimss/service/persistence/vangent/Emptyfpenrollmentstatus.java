package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the EMPTYFPENROLLMENTSTATUS database table.
 * 
 */
@Entity
@NamedQuery(name="Emptyfpenrollmentstatus.findAll", query="SELECT e FROM Emptyfpenrollmentstatus e")
public class Emptyfpenrollmentstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idstatus;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String statusdescription;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Emptyfpenrollmentevent
	@OneToMany(mappedBy="emptyfpenrollmentstatus")
	private List<Emptyfpenrollmentevent> emptyfpenrollmentevents;

	public Emptyfpenrollmentstatus() {
	}

	public long getIdstatus() {
		return this.idstatus;
	}

	public void setIdstatus(long idstatus) {
		this.idstatus = idstatus;
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

	public String getStatusdescription() {
		return this.statusdescription;
	}

	public void setStatusdescription(String statusdescription) {
		this.statusdescription = statusdescription;
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

	public List<Emptyfpenrollmentevent> getEmptyfpenrollmentevents() {
		return this.emptyfpenrollmentevents;
	}

	public void setEmptyfpenrollmentevents(List<Emptyfpenrollmentevent> emptyfpenrollmentevents) {
		this.emptyfpenrollmentevents = emptyfpenrollmentevents;
	}

	public Emptyfpenrollmentevent addEmptyfpenrollmentevent(Emptyfpenrollmentevent emptyfpenrollmentevent) {
		getEmptyfpenrollmentevents().add(emptyfpenrollmentevent);
		emptyfpenrollmentevent.setEmptyfpenrollmentstatus(this);

		return emptyfpenrollmentevent;
	}

	public Emptyfpenrollmentevent removeEmptyfpenrollmentevent(Emptyfpenrollmentevent emptyfpenrollmentevent) {
		getEmptyfpenrollmentevents().remove(emptyfpenrollmentevent);
		emptyfpenrollmentevent.setEmptyfpenrollmentstatus(null);

		return emptyfpenrollmentevent;
	}

}