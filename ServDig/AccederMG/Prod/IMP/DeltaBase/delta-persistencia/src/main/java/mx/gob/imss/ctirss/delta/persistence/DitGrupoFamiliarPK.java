package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_GRUPO_FAMILIAR database table.
 * 
 */
@Embeddable
public class DitGrupoFamiliarPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_PERSONA_INTEGRANTE", unique=true, nullable=false, precision=22)
	private long cveIdPersonaIntegrante;

	@Column(name="CVE_ID_ASIGNACION_NSS", unique=true, nullable=false, precision=22)
	private long cveIdAsignacionNss;

    public DitGrupoFamiliarPK() {
    	
    }
    
	public DitGrupoFamiliarPK(long cveIdAsignacionNss,long cveIdPersonaIntegrante) {
		super();
		this.cveIdPersonaIntegrante = cveIdPersonaIntegrante;
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}

	public long getCveIdAsignacionNss() {
		return this.cveIdAsignacionNss;
	}
	public void setCveIdAsignacionNss(long cveIdAsignacionNss) {
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}

	public long getCveIdPersonaIntegrante() {
		return cveIdPersonaIntegrante;
	}

	public void setCveIdPersonaIntegrante(long cveIdPersonaIntegrante) {
		this.cveIdPersonaIntegrante = cveIdPersonaIntegrante;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitGrupoFamiliarPK)) {
			return false;
		}
		DitGrupoFamiliarPK castOther = (DitGrupoFamiliarPK)other;
		return 
			(this.cveIdPersonaIntegrante == castOther.cveIdPersonaIntegrante)
			&& (this.cveIdAsignacionNss == castOther.cveIdAsignacionNss);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdPersonaIntegrante ^ (this.cveIdPersonaIntegrante >>> 32)));
		hash = hash * prime + ((int) (this.cveIdAsignacionNss ^ (this.cveIdAsignacionNss >>> 32)));
		
		return hash;
    }
}