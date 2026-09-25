package mx.gob.imss.ctirss.correccion.base.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CGC_CATTIPOOBRA database table.
 * 
 */
@Embeddable
public class AbstractCgcCatTipoObraPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_CODIGOOBRA")
	private long idCodigoobra;

	@Column(name = "ID_TIPOOBRA")
	private long idTipoobra;

	public AbstractCgcCatTipoObraPK() {
	}

	public long getIdCodigoobra() {
		return this.idCodigoobra;
	}

	public void setIdCodigoobra(long idCodigoobra) {
		this.idCodigoobra = idCodigoobra;
	}

	public long getIdTipoobra() {
		return this.idTipoobra;
	}

	public void setIdTipoobra(long idTipoobra) {
		this.idTipoobra = idTipoobra;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AbstractCgcCatTipoObraPK)) {
			return false;
		}
		AbstractCgcCatTipoObraPK castOther = (AbstractCgcCatTipoObraPK) other;
		return (this.idCodigoobra == castOther.idCodigoobra)
				&& (this.idTipoobra == castOther.idTipoobra);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime
				+ ((int) (this.idCodigoobra ^ (this.idCodigoobra >>> 32)));
		hash = hash * prime
				+ ((int) (this.idTipoobra ^ (this.idTipoobra >>> 32)));

		return hash;
	}
}