package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A4_CATREMUNERA database table.
 * 
 */
@Embeddable
public class FdtA4CatremuneraPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="ID_REMUNERACION", unique=true, nullable=false, precision=22)
	private long idRemuneracion;

    public FdtA4CatremuneraPK() {
    }
	public long getIdDictamen() {
		return this.idDictamen;
	}
	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
	}
	public long getIdRemuneracion() {
		return this.idRemuneracion;
	}
	public void setIdRemuneracion(long idRemuneracion) {
		this.idRemuneracion = idRemuneracion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtA4CatremuneraPK)) {
			return false;
		}
		FdtA4CatremuneraPK castOther = (FdtA4CatremuneraPK)other;
		return 
			(this.idDictamen == castOther.idDictamen)
			&& (this.idRemuneracion == castOther.idRemuneracion);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + ((int) (this.idRemuneracion ^ (this.idRemuneracion >>> 32)));
		
		return hash;
    }
}