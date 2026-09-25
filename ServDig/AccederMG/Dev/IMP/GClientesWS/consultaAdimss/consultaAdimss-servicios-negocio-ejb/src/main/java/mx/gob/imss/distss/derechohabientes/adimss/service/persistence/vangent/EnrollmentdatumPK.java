package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLMENTDATUM database table.
 * 
 */
@Embeddable
public class EnrollmentdatumPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TAXPYRINFO_ID")
	private long taxpyrinfoId;

	@Column(insertable=false, updatable=false)
	private long idenrol;

	@Column(insertable=false, updatable=false)
	private long idenrollmentstation;

	public EnrollmentdatumPK() {
	}
	public long getTaxpyrinfoId() {
		return this.taxpyrinfoId;
	}
	public void setTaxpyrinfoId(long taxpyrinfoId) {
		this.taxpyrinfoId = taxpyrinfoId;
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
		if (!(other instanceof EnrollmentdatumPK)) {
			return false;
		}
		EnrollmentdatumPK castOther = (EnrollmentdatumPK)other;
		return 
			(this.taxpyrinfoId == castOther.taxpyrinfoId)
			&& (this.idenrol == castOther.idenrol)
			&& (this.idenrollmentstation == castOther.idenrollmentstation);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.taxpyrinfoId ^ (this.taxpyrinfoId >>> 32)));
		hash = hash * prime + ((int) (this.idenrol ^ (this.idenrol >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		
		return hash;
	}
}