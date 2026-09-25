package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_DATOS_CLEM_ARTICULO database table.
 * 
 */
@Embeddable
public class DitDatosClemArticuloPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_CLEM")
	private long cveIdClem;

	@Column(name="CVE_ID_ARTICULO")
	private long cveIdArticulo;

    public DitDatosClemArticuloPK() {
    }
	public long getCveIdClem() {
		return this.cveIdClem;
	}
	public void setCveIdClem(long cveIdClem) {
		this.cveIdClem = cveIdClem;
	}
	public long getCveIdArticulo() {
		return this.cveIdArticulo;
	}
	public void setCveIdArticulo(long cveIdArticulo) {
		this.cveIdArticulo = cveIdArticulo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitDatosClemArticuloPK)) {
			return false;
		}
		DitDatosClemArticuloPK castOther = (DitDatosClemArticuloPK)other;
		return 
			(this.cveIdClem == castOther.cveIdClem)
			&& (this.cveIdArticulo == castOther.cveIdArticulo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdClem ^ (this.cveIdClem >>> 32)));
		hash = hash * prime + ((int) (this.cveIdArticulo ^ (this.cveIdArticulo >>> 32)));
		
		return hash;
    }
}