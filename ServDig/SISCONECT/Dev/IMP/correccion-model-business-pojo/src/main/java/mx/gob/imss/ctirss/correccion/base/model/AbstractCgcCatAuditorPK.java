package mx.gob.imss.ctirss.correccion.base.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CGC_CATAUDITOR database table.
 * 
 */
@Embeddable
public class AbstractCgcCatAuditorPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_SUBDELEGACION")
	private long idSubdelegacion;

	@Column(name = "ID_AUDITOR")
	private long idAuditor;

	public AbstractCgcCatAuditorPK() {
	}

	public long getIdSubdelegacion() {
		return this.idSubdelegacion;
	}

	public void setIdSubdelegacion(long idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
	}

	public long getIdAuditor() {
		return this.idAuditor;
	}

	public void setIdAuditor(long idAuditor) {
		this.idAuditor = idAuditor;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AbstractCgcCatAuditorPK)) {
			return false;
		}
		AbstractCgcCatAuditorPK castOther = (AbstractCgcCatAuditorPK) other;
		return (this.idSubdelegacion == castOther.idSubdelegacion)
				&& (this.idAuditor == castOther.idAuditor);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash
				* prime
				+ ((int) (this.idSubdelegacion ^ (this.idSubdelegacion >>> 32)));
		hash = hash * prime
				+ ((int) (this.idAuditor ^ (this.idAuditor >>> 32)));

		return hash;
	}
}