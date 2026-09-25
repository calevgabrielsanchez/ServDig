package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLPERFORMANCECES database table.
 * 
 */
@Embeddable
public class EnrollperformancecePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idenrollperfce;

	private long idenrollmentstation;

	public EnrollperformancecePK() {
	}
	public long getIdenrollperfce() {
		return this.idenrollperfce;
	}
	public void setIdenrollperfce(long idenrollperfce) {
		this.idenrollperfce = idenrollperfce;
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
		if (!(other instanceof EnrollperformancecePK)) {
			return false;
		}
		EnrollperformancecePK castOther = (EnrollperformancecePK)other;
		return 
			(this.idenrollperfce == castOther.idenrollperfce)
			&& (this.idenrollmentstation == castOther.idenrollmentstation);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idenrollperfce ^ (this.idenrollperfce >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		
		return hash;
	}
}