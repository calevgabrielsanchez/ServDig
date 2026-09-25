package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLMENTTURNS database table.
 * 
 */
@Embeddable
public class EnrollmentturnPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idturn;

	@Column(insertable=false, updatable=false)
	private long idenrollmentstation;

	public EnrollmentturnPK() {
	}
	public long getIdturn() {
		return this.idturn;
	}
	public void setIdturn(long idturn) {
		this.idturn = idturn;
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
		if (!(other instanceof EnrollmentturnPK)) {
			return false;
		}
		EnrollmentturnPK castOther = (EnrollmentturnPK)other;
		return 
			(this.idturn == castOther.idturn)
			&& (this.idenrollmentstation == castOther.idenrollmentstation);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idturn ^ (this.idturn >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		
		return hash;
	}
}