package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the JUSTIFICATIONTYPES database table.
 * 
 */
@Entity
@Table(name="JUSTIFICATIONTYPES")
@NamedQuery(name="Justificationtype.findAll", query="SELECT j FROM Justificationtype j")
public class Justificationtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idjustificationtype;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String justificationtdesc;

	private String justificationtname;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Hoursoperationsla6
	@OneToMany(mappedBy="justificationtype")
	private List<Hoursoperationsla6> hoursoperationsla6s;

	public Justificationtype() {
	}

	public long getIdjustificationtype() {
		return this.idjustificationtype;
	}

	public void setIdjustificationtype(long idjustificationtype) {
		this.idjustificationtype = idjustificationtype;
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

	public String getJustificationtdesc() {
		return this.justificationtdesc;
	}

	public void setJustificationtdesc(String justificationtdesc) {
		this.justificationtdesc = justificationtdesc;
	}

	public String getJustificationtname() {
		return this.justificationtname;
	}

	public void setJustificationtname(String justificationtname) {
		this.justificationtname = justificationtname;
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

	public List<Hoursoperationsla6> getHoursoperationsla6s() {
		return this.hoursoperationsla6s;
	}

	public void setHoursoperationsla6s(List<Hoursoperationsla6> hoursoperationsla6s) {
		this.hoursoperationsla6s = hoursoperationsla6s;
	}

	public Hoursoperationsla6 addHoursoperationsla6(Hoursoperationsla6 hoursoperationsla6) {
		getHoursoperationsla6s().add(hoursoperationsla6);
		hoursoperationsla6.setJustificationtype(this);

		return hoursoperationsla6;
	}

	public Hoursoperationsla6 removeHoursoperationsla6(Hoursoperationsla6 hoursoperationsla6) {
		getHoursoperationsla6s().remove(hoursoperationsla6);
		hoursoperationsla6.setJustificationtype(null);

		return hoursoperationsla6;
	}

}