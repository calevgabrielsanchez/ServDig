package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_SUBDELEG database table.
 * 
 */
@Embeddable
public class FdtSubdelegPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEG_ORIG", unique=true, nullable=false, precision=2)
	private long cveDelegOrig;

	@Column(name="SDELEG_ORIG", unique=true, nullable=false, precision=2)
	private long sdelegOrig;

    public FdtSubdelegPK() {
    }
	public long getCveDelegOrig() {
		return this.cveDelegOrig;
	}
	public void setCveDelegOrig(long cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}
	public long getSdelegOrig() {
		return this.sdelegOrig;
	}
	public void setSdelegOrig(long sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtSubdelegPK)) {
			return false;
		}
		FdtSubdelegPK castOther = (FdtSubdelegPK)other;
		return 
			(this.cveDelegOrig == castOther.cveDelegOrig)
			&& (this.sdelegOrig == castOther.sdelegOrig);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveDelegOrig ^ (this.cveDelegOrig >>> 32)));
		hash = hash * prime + ((int) (this.sdelegOrig ^ (this.sdelegOrig >>> 32)));
		
		return hash;
    }
}