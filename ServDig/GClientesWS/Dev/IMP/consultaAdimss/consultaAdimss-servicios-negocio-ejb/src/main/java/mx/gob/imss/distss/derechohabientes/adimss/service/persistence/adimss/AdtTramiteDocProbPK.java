package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ADT_TRAMITE_DOC_PROB database table.
 * 
 */
@Embeddable
public class AdtTramiteDocProbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DOC_PROBATORIO", insertable=false, updatable=false)
	private String cveDocProbatorio;

	@Column(name="CVE_TIPO_DOC_PROBATORIO", insertable=false, updatable=false)
	private long cveTipoDocProbatorio;

	@Column(name="CVE_FOLIO_EXPEDICION")
	private long cveFolioExpedicion;

	@Column(name="NUM_NSS_ASEG")
	private String numNssAseg;

	@Column(name="CVE_CALIDAD_ASEG")
	private long cveCalidadAseg;

	@Column(name="NUM_FOLIO_PERSONA")
	private long numFolioPersona;

	public AdtTramiteDocProbPK() {
	}
	public String getCveDocProbatorio() {
		return this.cveDocProbatorio;
	}
	public void setCveDocProbatorio(String cveDocProbatorio) {
		this.cveDocProbatorio = cveDocProbatorio;
	}
	public long getCveTipoDocProbatorio() {
		return this.cveTipoDocProbatorio;
	}
	public void setCveTipoDocProbatorio(long cveTipoDocProbatorio) {
		this.cveTipoDocProbatorio = cveTipoDocProbatorio;
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
		if (!(other instanceof AdtTramiteDocProbPK)) {
			return false;
		}
		AdtTramiteDocProbPK castOther = (AdtTramiteDocProbPK)other;
		return 
			this.cveDocProbatorio.equals(castOther.cveDocProbatorio)
			&& (this.cveTipoDocProbatorio == castOther.cveTipoDocProbatorio)
			&& (this.cveFolioExpedicion == castOther.cveFolioExpedicion)
			&& this.numNssAseg.equals(castOther.numNssAseg)
			&& (this.cveCalidadAseg == castOther.cveCalidadAseg)
			&& (this.numFolioPersona == castOther.numFolioPersona);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cveDocProbatorio.hashCode();
		hash = hash * prime + ((int) (this.cveTipoDocProbatorio ^ (this.cveTipoDocProbatorio >>> 32)));
		hash = hash * prime + ((int) (this.cveFolioExpedicion ^ (this.cveFolioExpedicion >>> 32)));
		hash = hash * prime + this.numNssAseg.hashCode();
		hash = hash * prime + ((int) (this.cveCalidadAseg ^ (this.cveCalidadAseg >>> 32)));
		hash = hash * prime + ((int) (this.numFolioPersona ^ (this.numFolioPersona >>> 32)));
		
		return hash;
	}
}