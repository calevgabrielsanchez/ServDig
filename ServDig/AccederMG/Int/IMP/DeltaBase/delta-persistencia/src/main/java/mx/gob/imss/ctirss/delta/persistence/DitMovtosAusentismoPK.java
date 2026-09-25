package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_MOVTOS_AUSENTISMO database table.
 * 
 */
@Embeddable
public class DitMovtosAusentismoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO", unique=true, nullable=false, precision=22)
	private long cveIdPatronSujetoObligado;

	@Column(name="CVE_ID_ASEGURADO", unique=true, nullable=false, precision=22)
	private long cveIdAsegurado;

    public DitMovtosAusentismoPK() {
    }
	public long getCveIdPatronSujetoObligado() {
		return this.cveIdPatronSujetoObligado;
	}
	public void setCveIdPatronSujetoObligado(long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}
	public long getCveIdAsegurado() {
		return this.cveIdAsegurado;
	}
	public void setCveIdAsegurado(long cveIdAsegurado) {
		this.cveIdAsegurado = cveIdAsegurado;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitMovtosAusentismoPK)) {
			return false;
		}
		DitMovtosAusentismoPK castOther = (DitMovtosAusentismoPK)other;
		return 
			(this.cveIdPatronSujetoObligado == castOther.cveIdPatronSujetoObligado)
			&& (this.cveIdAsegurado == castOther.cveIdAsegurado);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdPatronSujetoObligado ^ (this.cveIdPatronSujetoObligado >>> 32)));
		hash = hash * prime + ((int) (this.cveIdAsegurado ^ (this.cveIdAsegurado >>> 32)));
		
		return hash;
    }
}