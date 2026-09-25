package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BILLINGMARK database table.
 * 
 */
@Embeddable
public class BillingmarkPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idenrollmentstation;

	private long idenrol;

	public BillingmarkPK() {
	}
	public long getIdenrollmentstation() {
		return this.idenrollmentstation;
	}
	public void setIdenrollmentstation(long idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}
	public long getIdenrol() {
		return this.idenrol;
	}
	public void setIdenrol(long idenrol) {
		this.idenrol = idenrol;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BillingmarkPK)) {
			return false;
		}
		BillingmarkPK castOther = (BillingmarkPK)other;
		return 
			(this.idenrollmentstation == castOther.idenrollmentstation)
			&& (this.idenrol == castOther.idenrol);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		hash = hash * prime + ((int) (this.idenrol ^ (this.idenrol >>> 32)));
		
		return hash;
	}
}