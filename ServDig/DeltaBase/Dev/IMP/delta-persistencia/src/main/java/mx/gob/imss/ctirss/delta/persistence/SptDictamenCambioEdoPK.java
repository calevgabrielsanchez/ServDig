package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SPT_DICTAMEN_CAMBIO_EDO database table.
 * 
 */
@Embeddable
public class SptDictamenCambioEdoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NUM_CAMBIO")
	private long numCambio;

	@Column(name="ID_ESTADO_DICTAMEN", insertable=false, updatable=false)
	private String idEstadoDictamen;

	@Column(name="CVE_ID_DICTAMEN", insertable=false, updatable=false)
	private long cveIdDictamen;

	public SptDictamenCambioEdoPK() {
	}
	public long getNumCambio() {
		return this.numCambio;
	}
	public void setNumCambio(long numCambio) {
		this.numCambio = numCambio;
	}
	public String getIdEstadoDictamen() {
		return this.idEstadoDictamen;
	}
	public void setIdEstadoDictamen(String idEstadoDictamen) {
		this.idEstadoDictamen = idEstadoDictamen;
	}
	public long getCveIdDictamen() {
		return this.cveIdDictamen;
	}
	public void setCveIdDictamen(long cveIdDictamen) {
		this.cveIdDictamen = cveIdDictamen;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SptDictamenCambioEdoPK)) {
			return false;
		}
		SptDictamenCambioEdoPK castOther = (SptDictamenCambioEdoPK)other;
		return 
			(this.numCambio == castOther.numCambio)
			&& this.idEstadoDictamen.equals(castOther.idEstadoDictamen)
			&& (this.cveIdDictamen == castOther.cveIdDictamen);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.numCambio ^ (this.numCambio >>> 32)));
		hash = hash * prime + this.idEstadoDictamen.hashCode();
		hash = hash * prime + ((int) (this.cveIdDictamen ^ (this.cveIdDictamen >>> 32)));
		
		return hash;
	}
}