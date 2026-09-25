package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_DOCTOS_PERSONA database table.
 * 
 */
@Embeddable
public class DitDoctosPersonaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_PERSONA")
	private long cveIdPersona;

	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private long cveIdDocumentoProbatorio;

    public DitDoctosPersonaPK() {
    }
	public long getCveIdPersona() {
		return this.cveIdPersona;
	}
	public void setCveIdPersona(long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}
	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}
	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitDoctosPersonaPK)) {
			return false;
		}
		DitDoctosPersonaPK castOther = (DitDoctosPersonaPK)other;
		return 
			(this.cveIdPersona == castOther.cveIdPersona)
			&& (this.cveIdDocumentoProbatorio == castOther.cveIdDocumentoProbatorio);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdPersona ^ (this.cveIdPersona >>> 32)));
		hash = hash * prime + ((int) (this.cveIdDocumentoProbatorio ^ (this.cveIdDocumentoProbatorio >>> 32)));
		
		return hash;
    }
}