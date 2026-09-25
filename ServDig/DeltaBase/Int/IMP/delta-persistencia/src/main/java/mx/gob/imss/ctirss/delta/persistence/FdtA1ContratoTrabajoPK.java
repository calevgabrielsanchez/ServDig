package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A1_CONTRATO_TRABAJO database table.
 * 
 */
@Embeddable
public class FdtA1ContratoTrabajoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="CV_CONTRATO", unique=true, nullable=false, precision=22)
	private long cvContrato;

    public FdtA1ContratoTrabajoPK() {
    }
	public long getIdDictamen() {
		return this.idDictamen;
	}
	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
	}
	public long getCvContrato() {
		return this.cvContrato;
	}
	public void setCvContrato(long cvContrato) {
		this.cvContrato = cvContrato;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtA1ContratoTrabajoPK)) {
			return false;
		}
		FdtA1ContratoTrabajoPK castOther = (FdtA1ContratoTrabajoPK)other;
		return 
			(this.idDictamen == castOther.idDictamen)
			&& (this.cvContrato == castOther.cvContrato);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + ((int) (this.cvContrato ^ (this.cvContrato >>> 32)));
		
		return hash;
    }
}