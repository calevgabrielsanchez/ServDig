package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A4_PERCEP_VARIABLES database table.
 * 
 */
@Embeddable
public class FdtA4PercepVariablePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NU_PERCEPCION", unique=true, nullable=false, precision=22)
	private long nuPercepcion;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="REG_PATRON", unique=true, nullable=false, length=8)
	private String regPatron;

	@Column(name="CVE_MODAL", unique=true, nullable=false, precision=2)
	private long cveModal;

    public FdtA4PercepVariablePK() {
    }
	public long getNuPercepcion() {
		return this.nuPercepcion;
	}
	public void setNuPercepcion(long nuPercepcion) {
		this.nuPercepcion = nuPercepcion;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtA4PercepVariablePK)) {
			return false;
		}
		FdtA4PercepVariablePK castOther = (FdtA4PercepVariablePK)other;
		return 
			(this.nuPercepcion == castOther.nuPercepcion)
			&& (this.idDictamen == castOther.idDictamen)
			&& this.regPatron.equals(castOther.regPatron)
			&& (this.cveModal == castOther.cveModal);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.nuPercepcion ^ (this.nuPercepcion >>> 32)));
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + this.regPatron.hashCode();
		hash = hash * prime + ((int) (this.cveModal ^ (this.cveModal >>> 32)));
		
		return hash;
    }
}