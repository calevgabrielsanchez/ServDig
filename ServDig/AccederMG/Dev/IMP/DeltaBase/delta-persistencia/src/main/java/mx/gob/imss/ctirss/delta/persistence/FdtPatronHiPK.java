package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_PATRON_HIS database table.
 * 
 */
@Embeddable
public class FdtPatronHiPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="REG_PATRON", unique=true, nullable=false, length=8)
	private String regPatron;

	@Column(name="CVE_MODAL", unique=true, nullable=false, precision=2)
	private long cveModal;

	@Column(name="ID_AVISO", unique=true, nullable=false, precision=22)
	private long idAviso;

    public FdtPatronHiPK() {
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
	public long getIdAviso() {
		return this.idAviso;
	}
	public void setIdAviso(long idAviso) {
		this.idAviso = idAviso;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtPatronHiPK)) {
			return false;
		}
		FdtPatronHiPK castOther = (FdtPatronHiPK)other;
		return 
			this.regPatron.equals(castOther.regPatron)
			&& (this.cveModal == castOther.cveModal)
			&& (this.idAviso == castOther.idAviso);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.regPatron.hashCode();
		hash = hash * prime + ((int) (this.cveModal ^ (this.cveModal >>> 32)));
		hash = hash * prime + ((int) (this.idAviso ^ (this.idAviso >>> 32)));
		
		return hash;
    }
}