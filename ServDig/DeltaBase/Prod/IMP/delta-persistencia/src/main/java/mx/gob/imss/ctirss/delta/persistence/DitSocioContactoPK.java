package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_SOCIO_CONTACTO database table.
 * 
 */
@Embeddable
public class DitSocioContactoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_SOCIO")
	private long cveIdSocio;

	@Column(name="CVE_ID_FORMA_CONTACTO")
	private long cveIdFormaContacto;

    public DitSocioContactoPK() {
    }
	public long getCveIdSocio() {
		return this.cveIdSocio;
	}
	public void setCveIdSocio(long cveIdSocio) {
		this.cveIdSocio = cveIdSocio;
	}
	public long getCveIdFormaContacto() {
		return this.cveIdFormaContacto;
	}
	public void setCveIdFormaContacto(long cveIdFormaContacto) {
		this.cveIdFormaContacto = cveIdFormaContacto;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitSocioContactoPK)) {
			return false;
		}
		DitSocioContactoPK castOther = (DitSocioContactoPK)other;
		return 
			(this.cveIdSocio == castOther.cveIdSocio)
			&& (this.cveIdFormaContacto == castOther.cveIdFormaContacto);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdSocio ^ (this.cveIdSocio >>> 32)));
		hash = hash * prime + ((int) (this.cveIdFormaContacto ^ (this.cveIdFormaContacto >>> 32)));
		
		return hash;
    }
}