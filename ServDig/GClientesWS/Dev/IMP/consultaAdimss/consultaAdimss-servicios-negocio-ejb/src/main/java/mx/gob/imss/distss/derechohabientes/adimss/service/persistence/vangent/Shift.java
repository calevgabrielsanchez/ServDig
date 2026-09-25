package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SHIFT database table.
 * 
 */
@Entity
@NamedQuery(name="Shift.findAll", query="SELECT s FROM Shift s")
public class Shift implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idshift;

	private String nameshift;

	//bi-directional many-to-one association to Operatordetail
	@OneToMany(mappedBy="shiftBean")
	private List<Operatordetail> operatordetails;

	public Shift() {
	}

	public long getIdshift() {
		return this.idshift;
	}

	public void setIdshift(long idshift) {
		this.idshift = idshift;
	}

	public String getNameshift() {
		return this.nameshift;
	}

	public void setNameshift(String nameshift) {
		this.nameshift = nameshift;
	}

	public List<Operatordetail> getOperatordetails() {
		return this.operatordetails;
	}

	public void setOperatordetails(List<Operatordetail> operatordetails) {
		this.operatordetails = operatordetails;
	}

	public Operatordetail addOperatordetail(Operatordetail operatordetail) {
		getOperatordetails().add(operatordetail);
		operatordetail.setShiftBean(this);

		return operatordetail;
	}

	public Operatordetail removeOperatordetail(Operatordetail operatordetail) {
		getOperatordetails().remove(operatordetail);
		operatordetail.setShiftBean(null);

		return operatordetail;
	}

}