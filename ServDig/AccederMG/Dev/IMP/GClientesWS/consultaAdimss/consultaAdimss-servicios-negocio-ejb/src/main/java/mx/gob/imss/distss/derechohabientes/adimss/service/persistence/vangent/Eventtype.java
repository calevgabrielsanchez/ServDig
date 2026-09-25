package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the EVENTTYPES database table.
 * 
 */
@Entity
@Table(name="EVENTTYPES")
@NamedQuery(name="Eventtype.findAll", query="SELECT e FROM Eventtype e")
public class Eventtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long ideventtype;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String eventtypedesc;

	private BigDecimal idgrouptype;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Adimssevent
	@OneToMany(mappedBy="eventtype")
	private List<Adimssevent> adimssevents;

	public Eventtype() {
	}

	public long getIdeventtype() {
		return this.ideventtype;
	}

	public void setIdeventtype(long ideventtype) {
		this.ideventtype = ideventtype;
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

	public String getEventtypedesc() {
		return this.eventtypedesc;
	}

	public void setEventtypedesc(String eventtypedesc) {
		this.eventtypedesc = eventtypedesc;
	}

	public BigDecimal getIdgrouptype() {
		return this.idgrouptype;
	}

	public void setIdgrouptype(BigDecimal idgrouptype) {
		this.idgrouptype = idgrouptype;
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

	public List<Adimssevent> getAdimssevents() {
		return this.adimssevents;
	}

	public void setAdimssevents(List<Adimssevent> adimssevents) {
		this.adimssevents = adimssevents;
	}

	public Adimssevent addAdimssevent(Adimssevent adimssevent) {
		getAdimssevents().add(adimssevent);
		adimssevent.setEventtype(this);

		return adimssevent;
	}

	public Adimssevent removeAdimssevent(Adimssevent adimssevent) {
		getAdimssevents().remove(adimssevent);
		adimssevent.setEventtype(null);

		return adimssevent;
	}

}