package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ADT_CREDENCIAL database table.
 * 
 */
@Embeddable
public class AdtCredencialPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_FOLIO_EXPEDICION")
	private long cveFolioExpedicion;

	@Column(name="NUM_NSS_ASEG")
	private String numNssAseg;

	@Column(name="CVE_CALIDAD_ASEG", insertable=false, updatable=false)
	private long cveCalidadAseg;

	@Column(name="NUM_FOLIO_PERSONA", insertable=false, updatable=false)
	private long numFolioPersona;

	public AdtCredencialPK() {
	}
	public long getCveFolioExpedicion() {
		return this.cveFolioExpedicion;
	}
	public void setCveFolioExpedicion(long cveFolioExpedicion) {
		this.cveFolioExpedicion = cveFolioExpedicion;
	}
	public String getNumNssAseg() {
		return this.numNssAseg;
	}
	public void setNumNssAseg(String numNssAseg) {
		this.numNssAseg = numNssAseg;
	}
	public long getCveCalidadAseg() {
		return this.cveCalidadAseg;
	}
	public void setCveCalidadAseg(long cveCalidadAseg) {
		this.cveCalidadAseg = cveCalidadAseg;
	}
	public long getNumFolioPersona() {
		return this.numFolioPersona;
	}
	public void setNumFolioPersona(long numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdtCredencialPK)) {
			return false;
		}
		AdtCredencialPK castOther = (AdtCredencialPK)other;
		return 
			(this.cveFolioExpedicion == castOther.cveFolioExpedicion)
			&& this.numNssAseg.equals(castOther.numNssAseg)
			&& (this.cveCalidadAseg == castOther.cveCalidadAseg)
			&& (this.numFolioPersona == castOther.numFolioPersona);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveFolioExpedicion ^ (this.cveFolioExpedicion >>> 32)));
		hash = hash * prime + this.numNssAseg.hashCode();
		hash = hash * prime + ((int) (this.cveCalidadAseg ^ (this.cveCalidadAseg >>> 32)));
		hash = hash * prime + ((int) (this.numFolioPersona ^ (this.numFolioPersona >>> 32)));
		
		return hash;
	}
}