package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DitGrupoFamiliarCL3PK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_PERSONA_INTEGRANTE", unique=true, nullable=false, precision=22)
	private long cveIdPersonaIntegrante;

	@Column(name="CVE_ID_ASIGNACION_NSS", unique=true, nullable=false, precision=22)
	private long cveIdAsignacionNss;
	
	
	public long getCveIdPersonaIntegrante() {
		return cveIdPersonaIntegrante;
	}

	public void setCveIdPersonaIntegrante(long cveIdPersonaIntegrante) {
		this.cveIdPersonaIntegrante = cveIdPersonaIntegrante;
	}

	public long getCveIdAsignacionNss() {
		return cveIdAsignacionNss;
	}

	public void setCveIdAsignacionNss(long cveIdAsignacionNss) {
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitGrupoFamiliarCL3PK)) {
			return false;
		}
		DitGrupoFamiliarCL3PK castOther = (DitGrupoFamiliarCL3PK)other;
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
