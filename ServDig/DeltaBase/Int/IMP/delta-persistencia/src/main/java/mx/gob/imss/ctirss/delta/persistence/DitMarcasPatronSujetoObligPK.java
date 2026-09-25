package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_MARCAS_PATRON_SUJETO_OBLIG database table.
 * 
 */
@Embeddable
public class DitMarcasPatronSujetoObligPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NUM_PER", unique=true, nullable=false, precision=22)
	private long numPer;

	@Column(name="NUM_CRED", unique=true, nullable=false, precision=22)
	private long numCred;

	@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO", unique=true, nullable=false, precision=22)
	private long cveIdPatronSujetoObligado;

    public DitMarcasPatronSujetoObligPK() {
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
	public long getCveIdPatronSujetoObligado() {
		return this.cveIdPatronSujetoObligado;
	}
	public void setCveIdPatronSujetoObligado(long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitMarcasPatronSujetoObligPK)) {
			return false;
		}
		DitMarcasPatronSujetoObligPK castOther = (DitMarcasPatronSujetoObligPK)other;
		return 
			(this.numPer == castOther.numPer)
			&& (this.numCred == castOther.numCred)
			&& (this.cveIdPatronSujetoObligado == castOther.cveIdPatronSujetoObligado);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.numPer ^ (this.numPer >>> 32)));
		hash = hash * prime + ((int) (this.numCred ^ (this.numCred >>> 32)));
		hash = hash * prime + ((int) (this.cveIdPatronSujetoObligado ^ (this.cveIdPatronSujetoObligado >>> 32)));
		
		return hash;
    }
}