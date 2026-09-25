/**
 * RBG clean service
 * 2013-AGO-03
 */
package mx.gob.imss.ctirss.correccion.base.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CGT_ANEXORP database table.
 * 
 */
@Embeddable
public class AbstractCgtAnexoRPPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String folio;

	private String rp;

	public AbstractCgtAnexoRPPK() {
	}

	public String getFolio() {
		return this.folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getRp() {
		return this.rp;
	}

	public void setRp(String rp) {
		this.rp = rp;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AbstractCgtAnexoRPPK)) {
			return false;
		}
		AbstractCgtAnexoRPPK castOther = (AbstractCgtAnexoRPPK) other;
		return this.folio.equals(castOther.folio)
				&& this.rp.equals(castOther.rp);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.folio.hashCode();
		hash = hash * prime + this.rp.hashCode();

		return hash;
	}
}