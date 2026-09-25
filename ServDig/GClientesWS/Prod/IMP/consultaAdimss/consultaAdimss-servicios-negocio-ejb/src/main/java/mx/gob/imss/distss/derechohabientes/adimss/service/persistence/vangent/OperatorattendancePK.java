package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the OPERATORATTENDANCES database table.
 * 
 */
@Embeddable
public class OperatorattendancePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idopattendances;

	@Column(insertable=false, updatable=false)
	private long idusers;

	@Column(insertable=false, updatable=false)
	private long idenrollmentstation;

	public OperatorattendancePK() {
	}
	public long getIdopattendances() {
		return this.idopattendances;
	}
	public void setIdopattendances(long idopattendances) {
		this.idopattendances = idopattendances;
	}
	public long getIdusers() {
		return this.idusers;
	}
	public void setIdusers(long idusers) {
		this.idusers = idusers;
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
		if (!(other instanceof OperatorattendancePK)) {
			return false;
		}
		OperatorattendancePK castOther = (OperatorattendancePK)other;
		return 
			(this.idopattendances == castOther.idopattendances)
			&& (this.idusers == castOther.idusers)
			&& (this.idenrollmentstation == castOther.idenrollmentstation);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idopattendances ^ (this.idopattendances >>> 32)));
		hash = hash * prime + ((int) (this.idusers ^ (this.idusers >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		
		return hash;
	}
}