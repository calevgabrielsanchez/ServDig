package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the "PARAMETERS" database table.
 * 
 */
@Embeddable
public class ParameterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idparameters;

	@Column(insertable=false, updatable=false)
	private long idapplications;

	public ParameterPK() {
	}
	public long getIdparameters() {
		return this.idparameters;
	}
	public void setIdparameters(long idparameters) {
		this.idparameters = idparameters;
	}
	public long getIdapplications() {
		return this.idapplications;
	}
	public void setIdapplications(long idapplications) {
		this.idapplications = idapplications;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ParameterPK)) {
			return false;
		}
		ParameterPK castOther = (ParameterPK)other;
		return 
			(this.idparameters == castOther.idparameters)
			&& (this.idapplications == castOther.idapplications);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idparameters ^ (this.idparameters >>> 32)));
		hash = hash * prime + ((int) (this.idapplications ^ (this.idapplications >>> 32)));
		
		return hash;
	}
}