package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_HIST_PERSONA_CALIFICACION database table.
 * 
 */
@Embeddable
public class DitHistPersonaCalificacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_CALIFICACION", unique=true, nullable=false, precision=22)
	private Long cveIdCalificacion;

	@Column(name="CVE_ID_PERSONA", unique=true, nullable=false, precision=22)
	private Long cveIdPersona;

    public DitHistPersonaCalificacionPK() {
    }
	public Long getCveIdCalificacion() {
		return this.cveIdCalificacion;
	}
	public void setCveIdCalificacion(Long cveIdCalificacion) {
		this.cveIdCalificacion = cveIdCalificacion;
	}
	public Long getCveIdPersona() {
		return this.cveIdPersona;
	}
	public void setCveIdPersona(Long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitHistPersonaCalificacionPK)) {
			return false;
		}
		DitHistPersonaCalificacionPK castOther = (DitHistPersonaCalificacionPK)other;
		return 
			(this.cveIdCalificacion == castOther.cveIdCalificacion)
			&& (this.cveIdPersona == castOther.cveIdPersona);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdCalificacion ^ (this.cveIdCalificacion >>> 32)));
		hash = hash * prime + ((int) (this.cveIdPersona ^ (this.cveIdPersona >>> 32)));
		
		return hash;
    }
}