package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the EMPTYFPENROLLMENTREASONSCAT database table.
 * 
 */
@Entity
@NamedQuery(name="Emptyfpenrollmentreasonscat.findAll", query="SELECT e FROM Emptyfpenrollmentreasonscat e")
public class Emptyfpenrollmentreasonscat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idreason;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String reasondescription;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Emptyfpenrollmentevent
	@OneToMany(mappedBy="emptyfpenrollmentreasonscat")
	private List<Emptyfpenrollmentevent> emptyfpenrollmentevents;

	public Emptyfpenrollmentreasonscat() {
	}

	public long getIdreason() {
		return this.idreason;
	}

	public void setIdreason(long idreason) {
		this.idreason = idreason;
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

	public String getReasondescription() {
		return this.reasondescription;
	}

	public void setReasondescription(String reasondescription) {
		this.reasondescription = reasondescription;
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
		emptyfpenrollmentevent.setEmptyfpenrollmentreasonscat(this);

		return emptyfpenrollmentevent;
	}

	public Emptyfpenrollmentevent removeEmptyfpenrollmentevent(Emptyfpenrollmentevent emptyfpenrollmentevent) {
		getEmptyfpenrollmentevents().remove(emptyfpenrollmentevent);
		emptyfpenrollmentevent.setEmptyfpenrollmentreasonscat(null);

		return emptyfpenrollmentevent;
	}

}