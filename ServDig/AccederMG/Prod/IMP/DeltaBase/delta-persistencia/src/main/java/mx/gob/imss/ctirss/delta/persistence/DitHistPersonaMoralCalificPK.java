package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_HIST_PERSONA_MORAL_CALIFIC database table.
 * 
 */
@Embeddable
public class DitHistPersonaMoralCalificPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_CALIFICACION", unique=true, nullable=false, precision=22)
	private long cveIdCalificacion;

	@Column(name="CVE_ID_PERSONA_MORAL", unique=true, nullable=false, precision=22)
	private long cveIdPersonaMoral;

    public DitHistPersonaMoralCalificPK() {
    }
	public long getCveIdCalificacion() {
		return this.cveIdCalificacion;
	}
	public void setCveIdCalificacion(long cveIdCalificacion) {
		this.cveIdCalificacion = cveIdCalificacion;
	}
	public long getCveIdPersonaMoral() {
		return this.cveIdPersonaMoral;
	}
	public void setCveIdPersonaMoral(long cveIdPersonaMoral) {
		this.cveIdPersonaMoral = cveIdPersonaMoral;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitHistPersonaMoralCalificPK)) {
			return false;
		}
		DitHistPersonaMoralCalificPK castOther = (DitHistPersonaMoralCalificPK)other;
		return 
			(this.cveIdCalificacion == castOther.cveIdCalificacion)
			&& (this.cveIdPersonaMoral == castOther.cveIdPersonaMoral);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdCalificacion ^ (this.cveIdCalificacion >>> 32)));
		hash = hash * prime + ((int) (this.cveIdPersonaMoral ^ (this.cveIdPersonaMoral >>> 32)));
		
		return hash;
    }
}