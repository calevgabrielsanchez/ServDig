package mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the H_COP_ESTADO_CUENTA database table.
 * 
 */
@Embeddable
public class HCopEstadoCuentaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CR_PAT")
	private String crPat;

	@Column(name="CR_MOD")
	private String crMod;

	@Column(name="CR_PER")
	private long crPer;

	@Column(name="CR_CRED")
	private String crCred;

	public HCopEstadoCuentaPK() {
	}
	public String getCrPat() {
		return this.crPat;
	}
	public void setCrPat(String crPat) {
		this.crPat = crPat;
	}
	public String getCrMod() {
		return this.crMod;
	}
	public void setCrMod(String crMod) {
		this.crMod = crMod;
	}
	public long getCrPer() {
		return this.crPer;
	}
	public void setCrPer(long crPer) {
		this.crPer = crPer;
	}
	public String getCrCred() {
		return this.crCred;
	}
	public void setCrCred(String crCred) {
		this.crCred = crCred;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HCopEstadoCuentaPK)) {
			return false;
		}
		HCopEstadoCuentaPK castOther = (HCopEstadoCuentaPK)other;
		return 
			this.crPat.equals(castOther.crPat)
			&& this.crMod.equals(castOther.crMod)
			&& (this.crPer == castOther.crPer)
			&& this.crCred.equals(castOther.crCred);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.crPat.hashCode();
		hash = hash * prime + this.crMod.hashCode();
		hash = hash * prime + ((int) (this.crPer ^ (this.crPer >>> 32)));
		hash = hash * prime + this.crCred.hashCode();
		
		return hash;
	}
}