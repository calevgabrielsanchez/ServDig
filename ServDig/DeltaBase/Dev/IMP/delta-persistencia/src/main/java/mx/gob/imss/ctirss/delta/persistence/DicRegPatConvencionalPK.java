package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIC_REG_PAT_CONVENCIONAL database table.
 * 
 */
@Embeddable
public class DicRegPatConvencionalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private long cveIdPatronSujetoObligado;

	@Column(name="CVE_ID_MODALIDAD")
	private long cveIdModalidad;

	@Column(name="CVE_ID_DELEGACION")
	private long cveIdDelegacion;

	@Column(name="CVE_ID_SUBDELEGACION")
	private long cveIdSubdelegacion;

    public DicRegPatConvencionalPK() {
    }
	public long getCveIdPatronSujetoObligado() {
		return this.cveIdPatronSujetoObligado;
	}
	public void setCveIdPatronSujetoObligado(long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}
	public long getCveIdModalidad() {
		return this.cveIdModalidad;
	}
	public void setCveIdModalidad(long cveIdModalidad) {
		this.cveIdModalidad = cveIdModalidad;
	}
	public long getCveIdDelegacion() {
		return this.cveIdDelegacion;
	}
	public void setCveIdDelegacion(long cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}
	public long getCveIdSubdelegacion() {
		return this.cveIdSubdelegacion;
	}
	public void setCveIdSubdelegacion(long cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DicRegPatConvencionalPK)) {
			return false;
		}
		DicRegPatConvencionalPK castOther = (DicRegPatConvencionalPK)other;
		return 
			(this.cveIdPatronSujetoObligado == castOther.cveIdPatronSujetoObligado)
			&& (this.cveIdModalidad == castOther.cveIdModalidad)
			&& (this.cveIdDelegacion == castOther.cveIdDelegacion)
			&& (this.cveIdSubdelegacion == castOther.cveIdSubdelegacion);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdPatronSujetoObligado ^ (this.cveIdPatronSujetoObligado >>> 32)));
		hash = hash * prime + ((int) (this.cveIdModalidad ^ (this.cveIdModalidad >>> 32)));
		hash = hash * prime + ((int) (this.cveIdDelegacion ^ (this.cveIdDelegacion >>> 32)));
		hash = hash * prime + ((int) (this.cveIdSubdelegacion ^ (this.cveIdSubdelegacion >>> 32)));
		
		return hash;
    }
}