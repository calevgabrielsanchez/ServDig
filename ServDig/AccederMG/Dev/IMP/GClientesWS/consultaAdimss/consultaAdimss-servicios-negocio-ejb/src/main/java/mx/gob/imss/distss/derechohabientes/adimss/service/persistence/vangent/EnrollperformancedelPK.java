package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLPERFORMANCEDEL database table.
 * 
 */
@Embeddable
public class EnrollperformancedelPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idenrollperfdel;

	@Column(insertable=false, updatable=false)
	private long iddelegation;

	public EnrollperformancedelPK() {
	}
	public long getIdenrollperfdel() {
		return this.idenrollperfdel;
	}
	public void setIdenrollperfdel(long idenrollperfdel) {
		this.idenrollperfdel = idenrollperfdel;
	}
	public long getIddelegation() {
		return this.iddelegation;
	}
	public void setIddelegation(long iddelegation) {
		this.iddelegation = iddelegation;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnrollperformancedelPK)) {
			return false;
		}
		EnrollperformancedelPK castOther = (EnrollperformancedelPK)other;
		return 
			(this.idenrollperfdel == castOther.idenrollperfdel)
			&& (this.iddelegation == castOther.iddelegation);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idenrollperfdel ^ (this.idenrollperfdel >>> 32)));
		hash = hash * prime + ((int) (this.iddelegation ^ (this.iddelegation >>> 32)));
		
		return hash;
	}
}