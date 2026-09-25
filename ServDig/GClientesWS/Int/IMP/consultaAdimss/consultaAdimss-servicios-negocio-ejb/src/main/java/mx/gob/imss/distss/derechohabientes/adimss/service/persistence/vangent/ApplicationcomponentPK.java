package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the APPLICATIONCOMPONENTS database table.
 * 
 */
@Embeddable
public class ApplicationcomponentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idappcomponent;

	@Column(insertable=false, updatable=false)
	private long idapplication;

	public ApplicationcomponentPK() {
	}
	public long getIdappcomponent() {
		return this.idappcomponent;
	}
	public void setIdappcomponent(long idappcomponent) {
		this.idappcomponent = idappcomponent;
	}
	public long getIdapplication() {
		return this.idapplication;
	}
	public void setIdapplication(long idapplication) {
		this.idapplication = idapplication;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ApplicationcomponentPK)) {
			return false;
		}
		ApplicationcomponentPK castOther = (ApplicationcomponentPK)other;
		return 
			(this.idappcomponent == castOther.idappcomponent)
			&& (this.idapplication == castOther.idapplication);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idappcomponent ^ (this.idappcomponent >>> 32)));
		hash = hash * prime + ((int) (this.idapplication ^ (this.idapplication >>> 32)));
		
		return hash;
	}
}