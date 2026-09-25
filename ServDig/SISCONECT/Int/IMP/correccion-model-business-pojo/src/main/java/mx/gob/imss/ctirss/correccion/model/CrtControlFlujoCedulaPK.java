package mx.gob.imss.ctirss.correccion.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CRT_CONTROL_FLUJO_CEDULAS database table.
 * 
 */
@Embeddable
public class CrtControlFlujoCedulaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_SOLICITUDCORR")
	private long cveSolicitudcorr;

	@Column(name="CVE_CEDULA")
	private long cveCedula;
	
	@Column(name="CVE_EJERCICIO")
	private Integer cveEjercicio;

    public CrtControlFlujoCedulaPK() {
    }
	public long getCveSolicitudcorr() {
		return this.cveSolicitudcorr;
	}
	public void setCveSolicitudcorr(long cveSolicitudcorr) {
		this.cveSolicitudcorr = cveSolicitudcorr;
	}
	public long getCveCedula() {
		return this.cveCedula;
	}
	public void setCveCedula(long cveCedula) {
		this.cveCedula = cveCedula;
	}

	public Integer getCveEjercicio() {
		return cveEjercicio;
	}
	public void setCveEjercicio(Integer cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CrtControlFlujoCedulaPK)) {
			return false;
		}
		CrtControlFlujoCedulaPK castOther = (CrtControlFlujoCedulaPK)other;
		return 
			(this.cveSolicitudcorr == castOther.cveSolicitudcorr)
			&& (this.cveCedula == castOther.cveCedula)
			&& (this.cveEjercicio == castOther.cveEjercicio);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveSolicitudcorr ^ (this.cveSolicitudcorr >>> 32)));
		hash = hash * prime + ((int) (this.cveCedula ^ (this.cveCedula >>> 32)));
		hash = hash * prime + ((int) (this.cveEjercicio ^ (this.cveEjercicio >>> 32)));
		
		return hash;
    }
}