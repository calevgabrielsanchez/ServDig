package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLMENTDETAILS database table.
 * 
 */
@Embeddable
public class EnrollmentdetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idenroldetails;

	@Column(insertable=false, updatable=false)
	private long idenrol;

	@Column(insertable=false, updatable=false)
	private long idenrollmentstation;

	@Column(insertable=false, updatable=false)
	private long idusers;

	public EnrollmentdetailPK() {
	}
	public long getIdenroldetails() {
		return this.idenroldetails;
	}
	public void setIdenroldetails(long idenroldetails) {
		this.idenroldetails = idenroldetails;
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
	public long getIdusers() {
		return this.idusers;
	}
	public void setIdusers(long idusers) {
		this.idusers = idusers;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnrollmentdetailPK)) {
			return false;
		}
		EnrollmentdetailPK castOther = (EnrollmentdetailPK)other;
		return 
			(this.idenroldetails == castOther.idenroldetails)
			&& (this.idenrol == castOther.idenrol)
			&& (this.idenrollmentstation == castOther.idenrollmentstation)
			&& (this.idusers == castOther.idusers);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idenroldetails ^ (this.idenroldetails >>> 32)));
		hash = hash * prime + ((int) (this.idenrol ^ (this.idenrol >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		hash = hash * prime + ((int) (this.idusers ^ (this.idusers >>> 32)));
		
		return hash;
	}
}