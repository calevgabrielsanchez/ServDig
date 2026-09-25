package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A5_MAT_PRIMA database table.
 * 
 */
@Embeddable
public class FdtA5MatPrimaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NU_CONSECUTIVO", unique=true, nullable=false, precision=22)
	private long nuConsecutivo;

	@Column(name="REG_PATRON", unique=true, nullable=false, length=8)
	private String regPatron;

	@Column(name="CVE_MODAL", unique=true, nullable=false, precision=2)
	private long cveModal;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

    public FdtA5MatPrimaPK() {
    }
	public long getNuConsecutivo() {
		return this.nuConsecutivo;
	}
	public void setNuConsecutivo(long nuConsecutivo) {
		this.nuConsecutivo = nuConsecutivo;
	}
	public String getRegPatron() {
		return this.regPatron;
	}
	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}
	public long getCveModal() {
		return this.cveModal;
	}
	public void setCveModal(long cveModal) {
		this.cveModal = cveModal;
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
		if (!(other instanceof FdtA5MatPrimaPK)) {
			return false;
		}
		FdtA5MatPrimaPK castOther = (FdtA5MatPrimaPK)other;
		return 
			(this.nuConsecutivo == castOther.nuConsecutivo)
			&& this.regPatron.equals(castOther.regPatron)
			&& (this.cveModal == castOther.cveModal)
			&& (this.idDictamen == castOther.idDictamen);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.nuConsecutivo ^ (this.nuConsecutivo >>> 32)));
		hash = hash * prime + this.regPatron.hashCode();
		hash = hash * prime + ((int) (this.cveModal ^ (this.cveModal >>> 32)));
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		
		return hash;
    }
}