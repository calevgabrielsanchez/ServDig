package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIC_BODEGAS database table.
 * 
 */
@Embeddable
public class DicBodegaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DEL_CTL", unique=true, nullable=false, precision=2)
	private long cveDelCtl;

	@Column(name="CVE_SUB_CTL", unique=true, nullable=false, precision=2)
	private long cveSubCtl;

	@Column(name="CVE_BODEGA", unique=true, nullable=false, precision=2)
	private long cveBodega;

    public DicBodegaPK() {
    }
	public long getCveDelCtl() {
		return this.cveDelCtl;
	}
	public void setCveDelCtl(long cveDelCtl) {
		this.cveDelCtl = cveDelCtl;
	}
	public long getCveSubCtl() {
		return this.cveSubCtl;
	}
	public void setCveSubCtl(long cveSubCtl) {
		this.cveSubCtl = cveSubCtl;
	}
	public long getCveBodega() {
		return this.cveBodega;
	}
	public void setCveBodega(long cveBodega) {
		this.cveBodega = cveBodega;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DicBodegaPK)) {
			return false;
		}
		DicBodegaPK castOther = (DicBodegaPK)other;
		return 
			(this.cveDelCtl == castOther.cveDelCtl)
			&& (this.cveSubCtl == castOther.cveSubCtl)
			&& (this.cveBodega == castOther.cveBodega);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveDelCtl ^ (this.cveDelCtl >>> 32)));
		hash = hash * prime + ((int) (this.cveSubCtl ^ (this.cveSubCtl >>> 32)));
		hash = hash * prime + ((int) (this.cveBodega ^ (this.cveBodega >>> 32)));
		
		return hash;
    }
}