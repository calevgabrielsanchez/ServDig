package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDI_DICTAMEN_EXISTENTE database table.
 * 
 */
@Embeddable
public class FdiDictamenExistentePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEG_ORIG", unique=true, nullable=false, precision=2)
	private long cveDelegOrig;

	@Column(name="CVE_SDELEG_ORIG", unique=true, nullable=false, precision=2)
	private long cveSdelegOrig;

	@Column(name="CV_FOLIO_AVISO", unique=true, nullable=false, length=16)
	private String cvFolioAviso;

    public FdiDictamenExistentePK() {
    }
	public long getCveDelegOrig() {
		return this.cveDelegOrig;
	}
	public void setCveDelegOrig(long cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}
	public long getCveSdelegOrig() {
		return this.cveSdelegOrig;
	}
	public void setCveSdelegOrig(long cveSdelegOrig) {
		this.cveSdelegOrig = cveSdelegOrig;
	}
	public String getCvFolioAviso() {
		return this.cvFolioAviso;
	}
	public void setCvFolioAviso(String cvFolioAviso) {
		this.cvFolioAviso = cvFolioAviso;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdiDictamenExistentePK)) {
			return false;
		}
		FdiDictamenExistentePK castOther = (FdiDictamenExistentePK)other;
		return 
			(this.cveDelegOrig == castOther.cveDelegOrig)
			&& (this.cveSdelegOrig == castOther.cveSdelegOrig)
			&& this.cvFolioAviso.equals(castOther.cvFolioAviso);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveDelegOrig ^ (this.cveDelegOrig >>> 32)));
		hash = hash * prime + ((int) (this.cveSdelegOrig ^ (this.cveSdelegOrig >>> 32)));
		hash = hash * prime + this.cvFolioAviso.hashCode();
		
		return hash;
    }
}