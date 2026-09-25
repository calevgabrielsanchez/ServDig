package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_HIST_ESTADO_PERSONA database table.
 * 
 */
@Embeddable
public class DitHistEstadoPersonaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ESTADO_PERSONA", unique=true, nullable=false, precision=22)
	private long cveEstadoPersona;

	@Column(name="CVE_ID_PERSONA", unique=true, nullable=false, precision=22)
	private long cveIdPersona;

    public DitHistEstadoPersonaPK() {
    }
	public long getCveEstadoPersona() {
		return this.cveEstadoPersona;
	}
	public void setCveEstadoPersona(long cveEstadoPersona) {
		this.cveEstadoPersona = cveEstadoPersona;
	}
	public long getCveIdPersona() {
		return this.cveIdPersona;
	}
	public void setCveIdPersona(long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitHistEstadoPersonaPK)) {
			return false;
		}
		DitHistEstadoPersonaPK castOther = (DitHistEstadoPersonaPK)other;
		return 
			(this.cveEstadoPersona == castOther.cveEstadoPersona)
			&& (this.cveIdPersona == castOther.cveIdPersona);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveEstadoPersona ^ (this.cveEstadoPersona >>> 32)));
		hash = hash * prime + ((int) (this.cveIdPersona ^ (this.cveIdPersona >>> 32)));
		
		return hash;
    }
}