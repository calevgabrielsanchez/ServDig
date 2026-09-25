package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_CUOTAS_PATRON_SUJETO_OBLIG database table.
 * 
 */
@Embeddable
public class DitCuotasPatronSujetoObligPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO", unique=true, nullable=false, precision=22)
	private long cveIdPatronSujetoObligado;

	@Column(name="NUM_PER", unique=true, nullable=false, precision=22)
	private long numPer;

	@Column(name="NUM_CRED", unique=true, nullable=false, precision=22)
	private long numCred;

    public DitCuotasPatronSujetoObligPK() {
    }
	public long getCveIdPatronSujetoObligado() {
		return this.cveIdPatronSujetoObligado;
	}
	public void setCveIdPatronSujetoObligado(long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}
	public long getNumPer() {
		return this.numPer;
	}
	public void setNumPer(long numPer) {
		this.numPer = numPer;
	}
	public long getNumCred() {
		return this.numCred;
	}
	public void setNumCred(long numCred) {
		this.numCred = numCred;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitCuotasPatronSujetoObligPK)) {
			return false;
		}
		DitCuotasPatronSujetoObligPK castOther = (DitCuotasPatronSujetoObligPK)other;
		return 
			(this.cveIdPatronSujetoObligado == castOther.cveIdPatronSujetoObligado)
			&& (this.numPer == castOther.numPer)
			&& (this.numCred == castOther.numCred);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdPatronSujetoObligado ^ (this.cveIdPatronSujetoObligado >>> 32)));
		hash = hash * prime + ((int) (this.numPer ^ (this.numPer >>> 32)));
		hash = hash * prime + ((int) (this.numCred ^ (this.numCred >>> 32)));
		
		return hash;
    }
}