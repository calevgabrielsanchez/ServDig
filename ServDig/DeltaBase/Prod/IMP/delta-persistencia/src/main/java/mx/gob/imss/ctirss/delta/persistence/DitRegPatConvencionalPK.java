package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_REG_PAT_CONVENCIONAL database table.
 * 
 */
@Embeddable
public class DitRegPatConvencionalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_MUNICIPIO_IMSS")
	private long cveIdMunicipioImss;

	@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private long cveIdPatronSujetoObligado;

	@Column(name="CVE_ID_MODALIDAD")
	private long cveIdModalidad;

    public DitRegPatConvencionalPK() {
    }
	public long getCveIdMunicipioImss() {
		return this.cveIdMunicipioImss;
	}
	public void setCveIdMunicipioImss(long cveIdMunicipioImss) {
		this.cveIdMunicipioImss = cveIdMunicipioImss;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitRegPatConvencionalPK)) {
			return false;
		}
		DitRegPatConvencionalPK castOther = (DitRegPatConvencionalPK)other;
		return 
			(this.cveIdMunicipioImss == castOther.cveIdMunicipioImss)
			&& (this.cveIdPatronSujetoObligado == castOther.cveIdPatronSujetoObligado)
			&& (this.cveIdModalidad == castOther.cveIdModalidad);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdMunicipioImss ^ (this.cveIdMunicipioImss >>> 32)));
		hash = hash * prime + ((int) (this.cveIdPatronSujetoObligado ^ (this.cveIdPatronSujetoObligado >>> 32)));
		hash = hash * prime + ((int) (this.cveIdModalidad ^ (this.cveIdModalidad >>> 32)));
		
		return hash;
    }
}