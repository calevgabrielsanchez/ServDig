package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FI_MUNICIPIOS database table.
 * 
 */
@Embeddable
public class FiMunicipioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ENT_FED", unique=true, nullable=false, precision=2)
	private long entFed;

	@Column(name="ID_MUNICIPIO", unique=true, nullable=false, length=50)
	private String idMunicipio;

    public FiMunicipioPK() {
    }
	public long getEntFed() {
		return this.entFed;
	}
	public void setEntFed(long entFed) {
		this.entFed = entFed;
	}
	public String getIdMunicipio() {
		return this.idMunicipio;
	}
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FiMunicipioPK)) {
			return false;
		}
		FiMunicipioPK castOther = (FiMunicipioPK)other;
		return 
			(this.entFed == castOther.entFed)
			&& this.idMunicipio.equals(castOther.idMunicipio);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.entFed ^ (this.entFed >>> 32)));
		hash = hash * prime + this.idMunicipio.hashCode();
		
		return hash;
    }
}