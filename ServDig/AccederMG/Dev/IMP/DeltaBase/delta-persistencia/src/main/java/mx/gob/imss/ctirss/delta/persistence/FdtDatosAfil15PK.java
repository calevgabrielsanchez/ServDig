package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_DATOS_AFIL_15 database table.
 * 
 */
@Embeddable
public class FdtDatosAfil15PK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NU_CONSTRUCCION", unique=true, nullable=false, precision=22)
	private long nuConstruccion;

	@Column(name="REG_PATRON", unique=true, nullable=false, length=8)
	private String regPatron;

	@Column(name="CVE_MODAL", unique=true, nullable=false, precision=2)
	private long cveModal;

    public FdtDatosAfil15PK() {
    }
	public long getNuConstruccion() {
		return this.nuConstruccion;
	}
	public void setNuConstruccion(long nuConstruccion) {
		this.nuConstruccion = nuConstruccion;
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
		if (!(other instanceof FdtDatosAfil15PK)) {
			return false;
		}
		FdtDatosAfil15PK castOther = (FdtDatosAfil15PK)other;
		return 
			(this.nuConstruccion == castOther.nuConstruccion)
			&& this.regPatron.equals(castOther.regPatron)
			&& (this.cveModal == castOther.cveModal);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.nuConstruccion ^ (this.nuConstruccion >>> 32)));
		hash = hash * prime + this.regPatron.hashCode();
		hash = hash * prime + ((int) (this.cveModal ^ (this.cveModal >>> 32)));
		
		return hash;
    }
}