package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CONTROLADIMSS database table.
 * 
 */
@Embeddable
public class ControladimssPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idenrol;

	private long idenrollmentstation;

	public ControladimssPK() {
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
		if (!(other instanceof ControladimssPK)) {
			return false;
		}
		ControladimssPK castOther = (ControladimssPK)other;
		return 
			(this.idenrol == castOther.idenrol)
			&& (this.idenrollmentstation == castOther.idenrollmentstation);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idenrol ^ (this.idenrol >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		
		return hash;
	}
}