package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the REPLICATIONCONTROLMONTHDETAIL database table.
 * 
 */
@Embeddable
public class ReplicationcontrolmonthdetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idenrollmentstation;

	@Column(name="\"YEAR\"")
	private long year;

	@Column(name="\"MONTH\"")
	private long month;

	public ReplicationcontrolmonthdetailPK() {
	}
	public long getIdenrollmentstation() {
		return this.idenrollmentstation;
	}
	public void setIdenrollmentstation(long idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}
	public long getYear() {
		return this.year;
	}
	public void setYear(long year) {
		this.year = year;
	}
	public long getMonth() {
		return this.month;
	}
	public void setMonth(long month) {
		this.month = month;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ReplicationcontrolmonthdetailPK)) {
			return false;
		}
		ReplicationcontrolmonthdetailPK castOther = (ReplicationcontrolmonthdetailPK)other;
		return 
			(this.idenrollmentstation == castOther.idenrollmentstation)
			&& (this.year == castOther.year)
			&& (this.month == castOther.month);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		hash = hash * prime + ((int) (this.year ^ (this.year >>> 32)));
		hash = hash * prime + ((int) (this.month ^ (this.month >>> 32)));
		
		return hash;
	}
}