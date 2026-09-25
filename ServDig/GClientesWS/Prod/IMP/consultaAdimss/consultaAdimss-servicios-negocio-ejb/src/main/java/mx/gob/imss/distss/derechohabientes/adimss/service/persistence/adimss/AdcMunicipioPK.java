package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ADC_MUNICIPIOS database table.
 * 
 */
@Embeddable
public class AdcMunicipioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ENT_FEDERATIVA", insertable=false, updatable=false)
	private long cveEntFederativa;

	@Column(name="CVE_MUNICIPIO")
	private String cveMunicipio;

	public AdcMunicipioPK() {
	}
	public long getCveEntFederativa() {
		return this.cveEntFederativa;
	}
	public void setCveEntFederativa(long cveEntFederativa) {
		this.cveEntFederativa = cveEntFederativa;
	}
	public String getCveMunicipio() {
		return this.cveMunicipio;
	}
	public void setCveMunicipio(String cveMunicipio) {
		this.cveMunicipio = cveMunicipio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdcMunicipioPK)) {
			return false;
		}
		AdcMunicipioPK castOther = (AdcMunicipioPK)other;
		return 
			(this.cveEntFederativa == castOther.cveEntFederativa)
			&& this.cveMunicipio.equals(castOther.cveMunicipio);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveEntFederativa ^ (this.cveEntFederativa >>> 32)));
		hash = hash * prime + this.cveMunicipio.hashCode();
		
		return hash;
	}
}