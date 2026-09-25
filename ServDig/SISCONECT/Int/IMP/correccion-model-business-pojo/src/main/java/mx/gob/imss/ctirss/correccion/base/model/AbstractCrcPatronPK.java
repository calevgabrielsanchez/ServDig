package mx.gob.imss.ctirss.correccion.base.model;

import java.io.Serializable;

/**
 * The primary key class for the CRC_PATRON database table.
 * 
 */

public class AbstractCrcPatronPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String regPatron;

	private Long cveModal;

    public AbstractCrcPatronPK() {
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
		if (!(other instanceof AbstractCrcPatronPK)) {
			return false;
		}
		AbstractCrcPatronPK castOther = (AbstractCrcPatronPK)other;
		return 
			this.regPatron.equals(castOther.regPatron)
			&& (this.cveModal == castOther.cveModal);

    }
    
	public int hashCode() {
		return this.regPatron.hashCode()+this.cveModal.hashCode();
    }
}