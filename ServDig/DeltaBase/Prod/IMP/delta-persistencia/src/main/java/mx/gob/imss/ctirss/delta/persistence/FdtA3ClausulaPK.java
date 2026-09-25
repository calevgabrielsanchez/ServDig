package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A3_CLAUSULA database table.
 * 
 */
@Embeddable
public class FdtA3ClausulaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="NU_CLAUSULA", unique=true, nullable=false, precision=22)
	private long nuClausula;

    public FdtA3ClausulaPK() {
    }
	public long getIdDictamen() {
		return this.idDictamen;
	}
	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
	}
	public long getNuClausula() {
		return this.nuClausula;
	}
	public void setNuClausula(long nuClausula) {
		this.nuClausula = nuClausula;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtA3ClausulaPK)) {
			return false;
		}
		FdtA3ClausulaPK castOther = (FdtA3ClausulaPK)other;
		return 
			(this.idDictamen == castOther.idDictamen)
			&& (this.nuClausula == castOther.nuClausula);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + ((int) (this.nuClausula ^ (this.nuClausula >>> 32)));
		
		return hash;
    }
}