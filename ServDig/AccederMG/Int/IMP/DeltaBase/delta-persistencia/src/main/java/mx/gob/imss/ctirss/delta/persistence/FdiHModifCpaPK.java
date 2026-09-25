package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDI_H_MODIF_CPA database table.
 * 
 */
@Embeddable
public class FdiHModifCpaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_MODIF", unique=true, nullable=false, precision=22)
	private long idModif;

	@Column(name="CV_CURP", unique=true, nullable=false, length=18)
	private String cvCurp;

    public FdiHModifCpaPK() {
    }
	public long getIdModif() {
		return this.idModif;
	}
	public void setIdModif(long idModif) {
		this.idModif = idModif;
	}
	public String getCvCurp() {
		return this.cvCurp;
	}
	public void setCvCurp(String cvCurp) {
		this.cvCurp = cvCurp;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdiHModifCpaPK)) {
			return false;
		}
		FdiHModifCpaPK castOther = (FdiHModifCpaPK)other;
		return 
			(this.idModif == castOther.idModif)
			&& this.cvCurp.equals(castOther.cvCurp);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idModif ^ (this.idModif >>> 32)));
		hash = hash * prime + this.cvCurp.hashCode();
		
		return hash;
    }
}