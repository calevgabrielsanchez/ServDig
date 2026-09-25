package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_CEDRAZ_DICTAMEN database table.
 * 
 */
@Embeddable
public class FdtCedrazDictamenPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="ID_CEDULA", unique=true, nullable=false, precision=22)
	private long idCedula;

    public FdtCedrazDictamenPK() {
    }
	public long getIdDictamen() {
		return this.idDictamen;
	}
	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
	}
	public long getIdCedula() {
		return this.idCedula;
	}
	public void setIdCedula(long idCedula) {
		this.idCedula = idCedula;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtCedrazDictamenPK)) {
			return false;
		}
		FdtCedrazDictamenPK castOther = (FdtCedrazDictamenPK)other;
		return 
			(this.idDictamen == castOther.idDictamen)
			&& (this.idCedula == castOther.idCedula);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + ((int) (this.idCedula ^ (this.idCedula >>> 32)));
		
		return hash;
    }
}