package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDI_H_BAJA_CPA database table.
 * 
 */
@Embeddable
public class FdiHBajaCpaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_BAJA", unique=true, nullable=false, precision=22)
	private long idBaja;

	@Column(name="CV_CURP", unique=true, nullable=false, length=18)
	private String cvCurp;

    public FdiHBajaCpaPK() {
    }
	public long getIdBaja() {
		return this.idBaja;
	}
	public void setIdBaja(long idBaja) {
		this.idBaja = idBaja;
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
		if (!(other instanceof FdiHBajaCpaPK)) {
			return false;
		}
		FdiHBajaCpaPK castOther = (FdiHBajaCpaPK)other;
		return 
			(this.idBaja == castOther.idBaja)
			&& this.cvCurp.equals(castOther.cvCurp);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idBaja ^ (this.idBaja >>> 32)));
		hash = hash * prime + this.cvCurp.hashCode();
		
		return hash;
    }
}