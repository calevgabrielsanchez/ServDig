package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_UMF_TURNO database table.
 * 
 */
@Embeddable
public class DitUmfTurnoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_TURNO", precision=22)
	private long cveIdTurno;

	@Column(name="CVE_ID_UMF", precision=22)
	private long cveIdUmf;

    public DitUmfTurnoPK() {
    }
	public long getCveIdTurno() {
		return this.cveIdTurno;
	}
	public void setCveIdTurno(long cveIdTurno) {
		this.cveIdTurno = cveIdTurno;
	}
	public long getCveIdUmf() {
		return this.cveIdUmf;
	}
	public void setCveIdUmf(long cveIdUmf) {
		this.cveIdUmf = cveIdUmf;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitUmfTurnoPK)) {
			return false;
		}
		DitUmfTurnoPK castOther = (DitUmfTurnoPK)other;
		return 
			(this.cveIdTurno == castOther.cveIdTurno)
			&& (this.cveIdUmf == castOther.cveIdUmf);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdTurno ^ (this.cveIdTurno >>> 32)));
		hash = hash * prime + ((int) (this.cveIdUmf ^ (this.cveIdUmf >>> 32)));
		
		return hash;
    }
}