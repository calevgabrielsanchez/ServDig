package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A3_GRUPO_FACTORES database table.
 * 
 */
@Embeddable
public class FdtA3GrupoFactorePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="CV_GRUPO", unique=true, nullable=false, precision=22)
	private long cvGrupo;

	@Column(name="NU_FACTORES", unique=true, nullable=false, precision=22)
	private long nuFactores;

    public FdtA3GrupoFactorePK() {
    }
	public long getIdDictamen() {
		return this.idDictamen;
	}
	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
	}
	public long getCvGrupo() {
		return this.cvGrupo;
	}
	public void setCvGrupo(long cvGrupo) {
		this.cvGrupo = cvGrupo;
	}
	public long getNuFactores() {
		return this.nuFactores;
	}
	public void setNuFactores(long nuFactores) {
		this.nuFactores = nuFactores;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtA3GrupoFactorePK)) {
			return false;
		}
		FdtA3GrupoFactorePK castOther = (FdtA3GrupoFactorePK)other;
		return 
			(this.idDictamen == castOther.idDictamen)
			&& (this.cvGrupo == castOther.cvGrupo)
			&& (this.nuFactores == castOther.nuFactores);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + ((int) (this.cvGrupo ^ (this.cvGrupo >>> 32)));
		hash = hash * prime + ((int) (this.nuFactores ^ (this.nuFactores >>> 32)));
		
		return hash;
    }
}