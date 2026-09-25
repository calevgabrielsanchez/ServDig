package mx.imss.ctirss.base.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DLT_FORMAPAGO database table.
 * 
 */
@Embeddable
public class AbstractDltFormapagoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_INFOTRABAJO" , insertable=false, updatable=false)
	private Long cveInfotrabajo;

	@Column(name="CVE_CONCEPTO", insertable=false, updatable=false)
	private Long cveConcepto;

	@Column(name="CVE_FORMAPAGO", insertable=false, updatable=false)
	private Long cveFormapago;

    public AbstractDltFormapagoPK() {
    }
	public Long getCveInfotrabajo() {
		return this.cveInfotrabajo;
	}
	public void setCveInfotrabajo(Long cveInfotrabajo) {
		this.cveInfotrabajo = cveInfotrabajo;
	}
	public Long getCveConcepto() {
		return this.cveConcepto;
	}
	public void setCveConcepto(Long cveConcepto) {
		this.cveConcepto = cveConcepto;
	}
	public Long getCveFormapago() {
		return this.cveFormapago;
	}
	public void setCveFormapago(Long cveFormapago) {
		this.cveFormapago = cveFormapago;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AbstractDltFormapagoPK)) {
			return false;
		}
		AbstractDltFormapagoPK castOther = (AbstractDltFormapagoPK)other;
		return 
			(this.cveInfotrabajo == castOther.cveInfotrabajo)
			&& (this.cveConcepto == castOther.cveConcepto)
			&& (this.cveFormapago == castOther.cveFormapago);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveInfotrabajo ^ (this.cveInfotrabajo >>> 32)));
		hash = hash * prime + ((int) (this.cveConcepto ^ (this.cveConcepto >>> 32)));
		hash = hash * prime + ((int) (this.cveFormapago ^ (this.cveFormapago >>> 32)));
		
		return hash;
    }
}