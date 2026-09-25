package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_MOVIMIENTOS_COTIZANTE database table.
 * 
 */
@Embeddable
public class DitMovimientosCotizantePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_TIMESTAMP", unique=true, nullable=false)
	private java.util.Date fecTimestamp;

	@Column(name="CVE_ID_ASEGURADO", unique=true, nullable=false, precision=22)
	private long cveIdAsegurado;

	@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO", unique=true, nullable=false, precision=22)
	private long cveIdPatronSujetoObligado;

	@Column(name="NUM_PER", unique=true, nullable=false, precision=22)
	private long numPer;

	@Column(name="NUM_CRED", unique=true, nullable=false, precision=22)
	private long numCred;

    public DitMovimientosCotizantePK() {
    }
	public java.util.Date getFecTimestamp() {
		return this.fecTimestamp;
	}
	public void setFecTimestamp(java.util.Date fecTimestamp) {
		this.fecTimestamp = fecTimestamp;
	}
	public long getCveIdAsegurado() {
		return this.cveIdAsegurado;
	}
	public void setCveIdAsegurado(long cveIdAsegurado) {
		this.cveIdAsegurado = cveIdAsegurado;
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
		if (!(other instanceof DitMovimientosCotizantePK)) {
			return false;
		}
		DitMovimientosCotizantePK castOther = (DitMovimientosCotizantePK)other;
		return 
			this.fecTimestamp.equals(castOther.fecTimestamp)
			&& (this.cveIdAsegurado == castOther.cveIdAsegurado)
			&& (this.cveIdPatronSujetoObligado == castOther.cveIdPatronSujetoObligado)
			&& (this.numPer == castOther.numPer)
			&& (this.numCred == castOther.numCred);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fecTimestamp.hashCode();
		hash = hash * prime + ((int) (this.cveIdAsegurado ^ (this.cveIdAsegurado >>> 32)));
		hash = hash * prime + ((int) (this.cveIdPatronSujetoObligado ^ (this.cveIdPatronSujetoObligado >>> 32)));
		hash = hash * prime + ((int) (this.numPer ^ (this.numPer >>> 32)));
		hash = hash * prime + ((int) (this.numCred ^ (this.numCred >>> 32)));
		
		return hash;
    }
}