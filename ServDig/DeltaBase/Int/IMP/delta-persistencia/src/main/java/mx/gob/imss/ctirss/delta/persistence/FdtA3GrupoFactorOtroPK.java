package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A3_GRUPO_FACTOR_OTRO database table.
 * 
 */
@Embeddable
public class FdtA3GrupoFactorOtroPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="CV_GRUPO", unique=true, nullable=false, precision=22)
	private long cvGrupo;

	@Column(name="NU_FACTORES", unique=true, nullable=false, precision=22)
	private long nuFactores;

	@Column(name="NU_OTRO", unique=true, nullable=false, precision=22)
	private long nuOtro;

    public FdtA3GrupoFactorOtroPK() {
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
	public long getNuOtro() {
		return this.nuOtro;
	}
	public void setNuOtro(long nuOtro) {
		this.nuOtro = nuOtro;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtA3GrupoFactorOtroPK)) {
			return false;
		}
		FdtA3GrupoFactorOtroPK castOther = (FdtA3GrupoFactorOtroPK)other;
		return 
			(this.idDictamen == castOther.idDictamen)
			&& (this.cvGrupo == castOther.cvGrupo)
			&& (this.nuFactores == castOther.nuFactores)
			&& (this.nuOtro == castOther.nuOtro);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + ((int) (this.cvGrupo ^ (this.cvGrupo >>> 32)));
		hash = hash * prime + ((int) (this.nuFactores ^ (this.nuFactores >>> 32)));
		hash = hash * prime + ((int) (this.nuOtro ^ (this.nuOtro >>> 32)));
		
		return hash;
    }
}