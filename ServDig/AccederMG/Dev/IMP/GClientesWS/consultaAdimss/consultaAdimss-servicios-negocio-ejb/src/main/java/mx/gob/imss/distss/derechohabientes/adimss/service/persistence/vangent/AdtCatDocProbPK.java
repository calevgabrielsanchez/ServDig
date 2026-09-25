package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ADT_CAT_DOC_PROB database table.
 * 
 */
@Embeddable
public class AdtCatDocProbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DOC_PROBATORIO")
	private String cveDocProbatorio;

	@Column(name="CVE_TIPO_DOC_PROBATORIO")
	private long cveTipoDocProbatorio;

	public AdtCatDocProbPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdtCatDocProbPK)) {
			return false;
		}
		AdtCatDocProbPK castOther = (AdtCatDocProbPK)other;
		return 
			this.cveDocProbatorio.equals(castOther.cveDocProbatorio)
			&& (this.cveTipoDocProbatorio == castOther.cveTipoDocProbatorio);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cveDocProbatorio.hashCode();
		hash = hash * prime + ((int) (this.cveTipoDocProbatorio ^ (this.cveTipoDocProbatorio >>> 32)));
		
		return hash;
	}
}