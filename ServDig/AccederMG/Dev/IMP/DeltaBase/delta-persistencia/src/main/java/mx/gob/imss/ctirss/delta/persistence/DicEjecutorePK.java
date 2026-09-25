package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIC_EJECUTORES database table.
 * 
 */
@Embeddable
public class DicEjecutorePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DEL_CTL", unique=true, nullable=false, precision=2)
	private long cveDelCtl;

	@Column(name="CVE_SUB_CTL", unique=true, nullable=false, precision=2)
	private long cveSubCtl;

	@Column(name="CVE_TIPO_EJEC", unique=true, nullable=false, length=1)
	private String cveTipoEjec;

	@Column(name="CVE_EJEC", unique=true, nullable=false, precision=3)
	private long cveEjec;

    public DicEjecutorePK() {
    }
	public long getCveDelCtl() {
		return this.cveDelCtl;
	}
	public void setCveDelCtl(long cveDelCtl) {
		this.cveDelCtl = cveDelCtl;
	}
	public long getCveSubCtl() {
		return this.cveSubCtl;
	}
	public void setCveSubCtl(long cveSubCtl) {
		this.cveSubCtl = cveSubCtl;
	}
	public String getCveTipoEjec() {
		return this.cveTipoEjec;
	}
	public void setCveTipoEjec(String cveTipoEjec) {
		this.cveTipoEjec = cveTipoEjec;
	}
	public long getCveEjec() {
		return this.cveEjec;
	}
	public void setCveEjec(long cveEjec) {
		this.cveEjec = cveEjec;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DicEjecutorePK)) {
			return false;
		}
		DicEjecutorePK castOther = (DicEjecutorePK)other;
		return 
			(this.cveDelCtl == castOther.cveDelCtl)
			&& (this.cveSubCtl == castOther.cveSubCtl)
			&& this.cveTipoEjec.equals(castOther.cveTipoEjec)
			&& (this.cveEjec == castOther.cveEjec);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveDelCtl ^ (this.cveDelCtl >>> 32)));
		hash = hash * prime + ((int) (this.cveSubCtl ^ (this.cveSubCtl >>> 32)));
		hash = hash * prime + this.cveTipoEjec.hashCode();
		hash = hash * prime + ((int) (this.cveEjec ^ (this.cveEjec >>> 32)));
		
		return hash;
    }
}