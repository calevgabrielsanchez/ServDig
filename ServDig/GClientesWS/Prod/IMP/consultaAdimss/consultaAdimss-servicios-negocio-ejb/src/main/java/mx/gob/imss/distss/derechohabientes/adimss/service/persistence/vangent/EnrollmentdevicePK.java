package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLMENTDEVICES database table.
 * 
 */
@Embeddable
public class EnrollmentdevicePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idenroldevices;

	@Column(insertable=false, updatable=false)
	private long idapplications;

	public EnrollmentdevicePK() {
	}
	public long getIdenroldevices() {
		return this.idenroldevices;
	}
	public void setIdenroldevices(long idenroldevices) {
		this.idenroldevices = idenroldevices;
	}
	public long getIdapplications() {
		return this.idapplications;
	}
	public void setIdapplications(long idapplications) {
		this.idapplications = idapplications;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnrollmentdevicePK)) {
			return false;
		}
		EnrollmentdevicePK castOther = (EnrollmentdevicePK)other;
		return 
			(this.idenroldevices == castOther.idenroldevices)
			&& (this.idapplications == castOther.idapplications);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idenroldevices ^ (this.idenroldevices >>> 32)));
		hash = hash * prime + ((int) (this.idapplications ^ (this.idapplications >>> 32)));
		
		return hash;
	}
}