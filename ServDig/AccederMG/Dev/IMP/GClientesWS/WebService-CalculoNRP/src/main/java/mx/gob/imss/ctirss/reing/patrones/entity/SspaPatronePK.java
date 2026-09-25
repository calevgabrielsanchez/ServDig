package mx.gob.imss.ctirss.reing.patrones.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SSPA_PATRONES database table.
 * 
 */
@Embeddable
public class SspaPatronePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="REG_PATRON")
	private String regPatron;

	@Column(name="CVE_MODAL")
	private long cveModal;

    public SspaPatronePK() {
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
		if (!(other instanceof SspaPatronePK)) {
			return false;
		}
		SspaPatronePK castOther = (SspaPatronePK)other;
		return 
			this.regPatron.equals(castOther.regPatron)
			&& (this.cveModal == castOther.cveModal);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.regPatron.hashCode();
		hash = hash * prime + ((int) (this.cveModal ^ (this.cveModal >>> 32)));
		
		return hash;
    }
}