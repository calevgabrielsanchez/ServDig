package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A3_MUESTRA database table.
 * 
 */
@Embeddable
public class FdtA3MuestraPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="REG_PATRON", unique=true, nullable=false, length=8)
	private String regPatron;

	@Column(name="CVE_MODAL", unique=true, nullable=false, precision=2)
	private long cveModal;

	@Column(name="NU_MUESTRA", unique=true, nullable=false, precision=22)
	private long nuMuestra;

    public FdtA3MuestraPK() {
    }
	public long getIdDictamen() {
		return this.idDictamen;
	}
	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
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
	public long getNuMuestra() {
		return this.nuMuestra;
	}
	public void setNuMuestra(long nuMuestra) {
		this.nuMuestra = nuMuestra;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtA3MuestraPK)) {
			return false;
		}
		FdtA3MuestraPK castOther = (FdtA3MuestraPK)other;
		return 
			(this.idDictamen == castOther.idDictamen)
			&& this.regPatron.equals(castOther.regPatron)
			&& (this.cveModal == castOther.cveModal)
			&& (this.nuMuestra == castOther.nuMuestra);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + this.regPatron.hashCode();
		hash = hash * prime + ((int) (this.cveModal ^ (this.cveModal >>> 32)));
		hash = hash * prime + ((int) (this.nuMuestra ^ (this.nuMuestra >>> 32)));
		
		return hash;
    }
}