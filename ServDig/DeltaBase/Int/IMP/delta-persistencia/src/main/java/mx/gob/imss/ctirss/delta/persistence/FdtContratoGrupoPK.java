package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_CONTRATO_GRUPO database table.
 * 
 */
@Embeddable
public class FdtContratoGrupoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CV_CONTRATO", unique=true, nullable=false, precision=22)
	private long cvContrato;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="CV_GRUPO", unique=true, nullable=false, precision=22)
	private long cvGrupo;

    public FdtContratoGrupoPK() {
    }
	public long getCvContrato() {
		return this.cvContrato;
	}
	public void setCvContrato(long cvContrato) {
		this.cvContrato = cvContrato;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtContratoGrupoPK)) {
			return false;
		}
		FdtContratoGrupoPK castOther = (FdtContratoGrupoPK)other;
		return 
			(this.cvContrato == castOther.cvContrato)
			&& (this.idDictamen == castOther.idDictamen)
			&& (this.cvGrupo == castOther.cvGrupo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cvContrato ^ (this.cvContrato >>> 32)));
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + ((int) (this.cvGrupo ^ (this.cvGrupo >>> 32)));
		
		return hash;
    }
}