package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_CENTRO_TRABAJO_CONTACTO database table.
 * 
 */
@Embeddable
public class DitCentroTrabajoContactoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_FORMA_CONTACTO")
	private long cveIdFormaContacto;

	@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private long cveIdPatronSujetoObligado;

    public DitCentroTrabajoContactoPK() {
    }
	public long getCveIdFormaContacto() {
		return this.cveIdFormaContacto;
	}
	public void setCveIdFormaContacto(long cveIdFormaContacto) {
		this.cveIdFormaContacto = cveIdFormaContacto;
	}
	public long getCveIdPatronSujetoObligado() {
		return this.cveIdPatronSujetoObligado;
	}
	public void setCveIdPatronSujetoObligado(long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitCentroTrabajoContactoPK)) {
			return false;
		}
		DitCentroTrabajoContactoPK castOther = (DitCentroTrabajoContactoPK)other;
		return 
			(this.cveIdFormaContacto == castOther.cveIdFormaContacto)
			&& (this.cveIdPatronSujetoObligado == castOther.cveIdPatronSujetoObligado);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdFormaContacto ^ (this.cveIdFormaContacto >>> 32)));
		hash = hash * prime + ((int) (this.cveIdPatronSujetoObligado ^ (this.cveIdPatronSujetoObligado >>> 32)));
		
		return hash;
    }
}