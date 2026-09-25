package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CARDSCONTROL database table.
 * 
 */
@Embeddable
public class CardscontrolPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long foliodel;

	private long folioal;

	public CardscontrolPK() {
	}
	public long getFoliodel() {
		return this.foliodel;
	}
	public void setFoliodel(long foliodel) {
		this.foliodel = foliodel;
	}
	public long getFolioal() {
		return this.folioal;
	}
	public void setFolioal(long folioal) {
		this.folioal = folioal;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CardscontrolPK)) {
			return false;
		}
		CardscontrolPK castOther = (CardscontrolPK)other;
		return 
			(this.foliodel == castOther.foliodel)
			&& (this.folioal == castOther.folioal);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.foliodel ^ (this.foliodel >>> 32)));
		hash = hash * prime + ((int) (this.folioal ^ (this.folioal >>> 32)));
		
		return hash;
	}
}