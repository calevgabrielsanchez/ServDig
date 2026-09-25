package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CGT_COREDISINADI database table.
 * 
 */
@Embeddable
public class CgtCoredisinadiPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEG_ORIG", unique=true, nullable=false, precision=2)
	private long cveDelegOrig;

	@Column(name="SDELEG_ORIG", unique=true, nullable=false, precision=2)
	private long sdelegOrig;

	@Column(name="TX_NUMAVISO", unique=true, nullable=false, length=10)
	private String txNumaviso;

    public CgtCoredisinadiPK() {
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
	public String getTxNumaviso() {
		return this.txNumaviso;
	}
	public void setTxNumaviso(String txNumaviso) {
		this.txNumaviso = txNumaviso;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CgtCoredisinadiPK)) {
			return false;
		}
		CgtCoredisinadiPK castOther = (CgtCoredisinadiPK)other;
		return 
			(this.cveDelegOrig == castOther.cveDelegOrig)
			&& (this.sdelegOrig == castOther.sdelegOrig)
			&& this.txNumaviso.equals(castOther.txNumaviso);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveDelegOrig ^ (this.cveDelegOrig >>> 32)));
		hash = hash * prime + ((int) (this.sdelegOrig ^ (this.sdelegOrig >>> 32)));
		hash = hash * prime + this.txNumaviso.hashCode();
		
		return hash;
    }
}