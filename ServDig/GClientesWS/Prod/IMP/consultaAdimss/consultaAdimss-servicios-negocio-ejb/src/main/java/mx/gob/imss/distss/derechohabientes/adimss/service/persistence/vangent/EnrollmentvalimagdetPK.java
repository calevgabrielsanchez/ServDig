package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLMENTVALIMAGDET database table.
 * 
 */
@Embeddable
public class EnrollmentvalimagdetPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idimgtype;

	@Column(insertable=false, updatable=false)
	private long idenrol;

	@Column(insertable=false, updatable=false)
	private long idenrollmentstation;

	public EnrollmentvalimagdetPK() {
	}
	public long getIdimgtype() {
		return this.idimgtype;
	}
	public void setIdimgtype(long idimgtype) {
		this.idimgtype = idimgtype;
	}
	public long getIdenrol() {
		return this.idenrol;
	}
	public void setIdenrol(long idenrol) {
		this.idenrol = idenrol;
	}
	public long getIdenrollmentstation() {
		return this.idenrollmentstation;
	}
	public void setIdenrollmentstation(long idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnrollmentvalimagdetPK)) {
			return false;
		}
		EnrollmentvalimagdetPK castOther = (EnrollmentvalimagdetPK)other;
		return 
			(this.idimgtype == castOther.idimgtype)
			&& (this.idenrol == castOther.idenrol)
			&& (this.idenrollmentstation == castOther.idenrollmentstation);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idimgtype ^ (this.idimgtype >>> 32)));
		hash = hash * prime + ((int) (this.idenrol ^ (this.idenrol >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		
		return hash;
	}
}