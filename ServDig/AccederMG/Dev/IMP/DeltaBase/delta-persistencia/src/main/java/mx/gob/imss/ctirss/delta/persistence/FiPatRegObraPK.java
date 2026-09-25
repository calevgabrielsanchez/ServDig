package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FI_PAT_REG_OBRA database table.
 * 
 */
@Embeddable
public class FiPatRegObraPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NU_REG_OBRA", unique=true, nullable=false, precision=22)
	private long nuRegObra;

	@Column(name="REG_PATRON", unique=true, nullable=false, length=8)
	private String regPatron;

	@Column(name="CVE_MODAL", unique=true, nullable=false, precision=2)
	private long cveModal;

    public FiPatRegObraPK() {
    }
	public long getNuRegObra() {
		return this.nuRegObra;
	}
	public void setNuRegObra(long nuRegObra) {
		this.nuRegObra = nuRegObra;
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
		if (!(other instanceof FiPatRegObraPK)) {
			return false;
		}
		FiPatRegObraPK castOther = (FiPatRegObraPK)other;
		return 
			(this.nuRegObra == castOther.nuRegObra)
			&& this.regPatron.equals(castOther.regPatron)
			&& (this.cveModal == castOther.cveModal);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.nuRegObra ^ (this.nuRegObra >>> 32)));
		hash = hash * prime + this.regPatron.hashCode();
		hash = hash * prime + ((int) (this.cveModal ^ (this.cveModal >>> 32)));
		
		return hash;
    }
}