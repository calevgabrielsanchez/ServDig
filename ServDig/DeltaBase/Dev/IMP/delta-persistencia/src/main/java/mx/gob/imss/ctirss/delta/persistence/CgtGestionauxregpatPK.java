package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CGT_GESTIONAUXREGPAT database table.
 * 
 */
@Embeddable
public class CgtGestionauxregpatPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEG_ORIG", unique=true, nullable=false, precision=2)
	private long cveDelegOrig;

	@Column(name="SDELEG_ORIG", unique=true, nullable=false, precision=2)
	private long sdelegOrig;

	@Column(name="TX_NUMAVISO", unique=true, nullable=false, length=10)
	private String txNumaviso;

	@Column(name="TX_REGPATRONAL", unique=true, nullable=false, length=11)
	private String txRegpatronal;

	@Column(name="TX_REGPATRONALINSC", unique=true, nullable=false, length=11)
	private String txRegpatronalinsc;

    public CgtGestionauxregpatPK() {
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
	public String getTxRegpatronal() {
		return this.txRegpatronal;
	}
	public void setTxRegpatronal(String txRegpatronal) {
		this.txRegpatronal = txRegpatronal;
	}
	public String getTxRegpatronalinsc() {
		return this.txRegpatronalinsc;
	}
	public void setTxRegpatronalinsc(String txRegpatronalinsc) {
		this.txRegpatronalinsc = txRegpatronalinsc;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CgtGestionauxregpatPK)) {
			return false;
		}
		CgtGestionauxregpatPK castOther = (CgtGestionauxregpatPK)other;
		return 
			(this.cveDelegOrig == castOther.cveDelegOrig)
			&& (this.sdelegOrig == castOther.sdelegOrig)
			&& this.txNumaviso.equals(castOther.txNumaviso)
			&& this.txRegpatronal.equals(castOther.txRegpatronal)
			&& this.txRegpatronalinsc.equals(castOther.txRegpatronalinsc);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveDelegOrig ^ (this.cveDelegOrig >>> 32)));
		hash = hash * prime + ((int) (this.sdelegOrig ^ (this.sdelegOrig >>> 32)));
		hash = hash * prime + this.txNumaviso.hashCode();
		hash = hash * prime + this.txRegpatronal.hashCode();
		hash = hash * prime + this.txRegpatronalinsc.hashCode();
		
		return hash;
    }
}