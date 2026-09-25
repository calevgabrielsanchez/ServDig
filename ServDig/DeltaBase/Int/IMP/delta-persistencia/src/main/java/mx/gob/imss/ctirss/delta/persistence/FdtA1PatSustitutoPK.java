package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A1_PAT_SUSTITUTO database table.
 * 
 */
@Embeddable
public class FdtA1PatSustitutoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_PAT_SUST", unique=true, nullable=false, precision=22)
	private long idPatSust;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

    public FdtA1PatSustitutoPK() {
    }
	public long getIdPatSust() {
		return this.idPatSust;
	}
	public void setIdPatSust(long idPatSust) {
		this.idPatSust = idPatSust;
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
		if (!(other instanceof FdtA1PatSustitutoPK)) {
			return false;
		}
		FdtA1PatSustitutoPK castOther = (FdtA1PatSustitutoPK)other;
		return 
			(this.idPatSust == castOther.idPatSust)
			&& (this.idDictamen == castOther.idDictamen);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idPatSust ^ (this.idPatSust >>> 32)));
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		
		return hash;
    }
}