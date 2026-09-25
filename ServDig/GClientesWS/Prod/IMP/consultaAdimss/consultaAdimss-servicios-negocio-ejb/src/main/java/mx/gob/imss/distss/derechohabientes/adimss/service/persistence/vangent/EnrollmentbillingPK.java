package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLMENTBILLING database table.
 * 
 */
@Embeddable
public class EnrollmentbillingPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String idenrolflows;

	@Column(insertable=false, updatable=false)
	private long idenrol;

	@Column(insertable=false, updatable=false)
	private long idenrollmentstation;

	@Column(insertable=false, updatable=false)
	private long idenrolstate;

	public EnrollmentbillingPK() {
	}
	public String getIdenrolflows() {
		return this.idenrolflows;
	}
	public void setIdenrolflows(String idenrolflows) {
		this.idenrolflows = idenrolflows;
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
	public long getIdenrolstate() {
		return this.idenrolstate;
	}
	public void setIdenrolstate(long idenrolstate) {
		this.idenrolstate = idenrolstate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnrollmentbillingPK)) {
			return false;
		}
		EnrollmentbillingPK castOther = (EnrollmentbillingPK)other;
		return 
			this.idenrolflows.equals(castOther.idenrolflows)
			&& (this.idenrol == castOther.idenrol)
			&& (this.idenrollmentstation == castOther.idenrollmentstation)
			&& (this.idenrolstate == castOther.idenrolstate);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idenrolflows.hashCode();
		hash = hash * prime + ((int) (this.idenrol ^ (this.idenrol >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		hash = hash * prime + ((int) (this.idenrolstate ^ (this.idenrolstate >>> 32)));
		
		return hash;
	}
}