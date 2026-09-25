package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIC_SUBDEL_COMPATIBLES database table.
 * 
 */
@Embeddable
public class DicSubdelCompatiblePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_SUBDELEGACION_ORIGEN")
	private long cveIdSubdelegacionOrigen;

	@Column(name="CVE_ID_SUBDELEGACION_DESTINO")
	private long cveIdSubdelegacionDestino;

    public DicSubdelCompatiblePK() {
    }
	public long getCveIdSubdelegacionOrigen() {
		return this.cveIdSubdelegacionOrigen;
	}
	public void setCveIdSubdelegacionOrigen(long cveIdSubdelegacionOrigen) {
		this.cveIdSubdelegacionOrigen = cveIdSubdelegacionOrigen;
	}
	public long getCveIdSubdelegacionDestino() {
		return this.cveIdSubdelegacionDestino;
	}
	public void setCveIdSubdelegacionDestino(long cveIdSubdelegacionDestino) {
		this.cveIdSubdelegacionDestino = cveIdSubdelegacionDestino;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DicSubdelCompatiblePK)) {
			return false;
		}
		DicSubdelCompatiblePK castOther = (DicSubdelCompatiblePK)other;
		return 
			(this.cveIdSubdelegacionOrigen == castOther.cveIdSubdelegacionOrigen)
			&& (this.cveIdSubdelegacionDestino == castOther.cveIdSubdelegacionDestino);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdSubdelegacionOrigen ^ (this.cveIdSubdelegacionOrigen >>> 32)));
		hash = hash * prime + ((int) (this.cveIdSubdelegacionDestino ^ (this.cveIdSubdelegacionDestino >>> 32)));
		
		return hash;
    }
}