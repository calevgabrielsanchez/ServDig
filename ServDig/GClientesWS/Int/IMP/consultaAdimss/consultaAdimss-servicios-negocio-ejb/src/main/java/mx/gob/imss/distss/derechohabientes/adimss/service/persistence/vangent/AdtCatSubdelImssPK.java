package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ADT_CAT_SUBDEL_IMSS database table.
 * 
 */
@Embeddable
public class AdtCatSubdelImssPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_SUBDEL_IMSS")
	private long cveSubdelImss;

	@Column(name="CVE_DELEGACION")
	private long cveDelegacion;

	public AdtCatSubdelImssPK() {
	}
	public long getCveSubdelImss() {
		return this.cveSubdelImss;
	}
	public void setCveSubdelImss(long cveSubdelImss) {
		this.cveSubdelImss = cveSubdelImss;
	}
	public long getCveDelegacion() {
		return this.cveDelegacion;
	}
	public void setCveDelegacion(long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdtCatSubdelImssPK)) {
			return false;
		}
		AdtCatSubdelImssPK castOther = (AdtCatSubdelImssPK)other;
		return 
			(this.cveSubdelImss == castOther.cveSubdelImss)
			&& (this.cveDelegacion == castOther.cveDelegacion);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveSubdelImss ^ (this.cveSubdelImss >>> 32)));
		hash = hash * prime + ((int) (this.cveDelegacion ^ (this.cveDelegacion >>> 32)));
		
		return hash;
	}
}