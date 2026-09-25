package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A3_PF_OTROS database table.
 * 
 */
@Embeddable
public class FdtA3PfOtroPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NU_OTRO", unique=true, nullable=false, precision=22)
	private long nuOtro;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

    public FdtA3PfOtroPK() {
    }
	public long getNuOtro() {
		return this.nuOtro;
	}
	public void setNuOtro(long nuOtro) {
		this.nuOtro = nuOtro;
	}
	public long getIdDictamen() {
		return this.idDictamen;
	}
	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtA3PfOtroPK)) {
			return false;
		}
		FdtA3PfOtroPK castOther = (FdtA3PfOtroPK)other;
		return 
			(this.nuOtro == castOther.nuOtro)
			&& (this.idDictamen == castOther.idDictamen);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.nuOtro ^ (this.nuOtro >>> 32)));
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		
		return hash;
    }
}