package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIC_TIEMPOS_MOVTOS_EJEC database table.
 * 
 */
@Embeddable
public class DicTiemposMovtosEjecPK implements Serializable {
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

	@Column(name="NUM_PERIODO", unique=true, nullable=false, precision=22)
	private long numPeriodo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_NLA", unique=true, nullable=false)
	private java.util.Date fecNla;

    public DicTiemposMovtosEjecPK() {
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
	public long getNumPeriodo() {
		return this.numPeriodo;
	}
	public void setNumPeriodo(long numPeriodo) {
		this.numPeriodo = numPeriodo;
	}
	public java.util.Date getFecNla() {
		return this.fecNla;
	}
	public void setFecNla(java.util.Date fecNla) {
		this.fecNla = fecNla;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DicTiemposMovtosEjecPK)) {
			return false;
		}
		DicTiemposMovtosEjecPK castOther = (DicTiemposMovtosEjecPK)other;
		return 
			(this.cveDelCtl == castOther.cveDelCtl)
			&& (this.cveSubCtl == castOther.cveSubCtl)
			&& this.cveTipoEjec.equals(castOther.cveTipoEjec)
			&& (this.cveEjec == castOther.cveEjec)
			&& (this.numPeriodo == castOther.numPeriodo)
			&& this.fecNla.equals(castOther.fecNla);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveDelCtl ^ (this.cveDelCtl >>> 32)));
		hash = hash * prime + ((int) (this.cveSubCtl ^ (this.cveSubCtl >>> 32)));
		hash = hash * prime + this.cveTipoEjec.hashCode();
		hash = hash * prime + ((int) (this.cveEjec ^ (this.cveEjec >>> 32)));
		hash = hash * prime + ((int) (this.numPeriodo ^ (this.numPeriodo >>> 32)));
		hash = hash * prime + this.fecNla.hashCode();
		
		return hash;
    }
}