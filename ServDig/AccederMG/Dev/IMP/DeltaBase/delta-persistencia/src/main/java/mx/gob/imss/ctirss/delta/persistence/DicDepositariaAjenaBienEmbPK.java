package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIC_DEPOSITARIA_AJENA_BIEN_EMB database table.
 * 
 */
@Embeddable
public class DicDepositariaAjenaBienEmbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DEPOSITARIA", unique=true, nullable=false, precision=22)
	private long cveDepositaria;

	@Column(name="NUM_FOL", unique=true, nullable=false, precision=5)
	private long numFol;

	@Column(name="CVE_DEL_CTL", unique=true, nullable=false, precision=2)
	private long cveDelCtl;

	@Column(name="CVE_SUB_CTL", unique=true, nullable=false, precision=2)
	private long cveSubCtl;

	@Column(name="NUM_EMBARGO", unique=true, nullable=false, precision=1)
	private long numEmbargo;

	@Column(name="CVE_PATRON", unique=true, nullable=false, length=8)
	private String cvePatron;

	@Column(name="CVE_MOD", unique=true, nullable=false, length=2)
	private String cveMod;

    public DicDepositariaAjenaBienEmbPK() {
    }
	public long getCveDepositaria() {
		return this.cveDepositaria;
	}
	public void setCveDepositaria(long cveDepositaria) {
		this.cveDepositaria = cveDepositaria;
	}
	public long getNumFol() {
		return this.numFol;
	}
	public void setNumFol(long numFol) {
		this.numFol = numFol;
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
	public long getNumEmbargo() {
		return this.numEmbargo;
	}
	public void setNumEmbargo(long numEmbargo) {
		this.numEmbargo = numEmbargo;
	}
	public String getCvePatron() {
		return this.cvePatron;
	}
	public void setCvePatron(String cvePatron) {
		this.cvePatron = cvePatron;
	}
	public String getCveMod() {
		return this.cveMod;
	}
	public void setCveMod(String cveMod) {
		this.cveMod = cveMod;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DicDepositariaAjenaBienEmbPK)) {
			return false;
		}
		DicDepositariaAjenaBienEmbPK castOther = (DicDepositariaAjenaBienEmbPK)other;
		return 
			(this.cveDepositaria == castOther.cveDepositaria)
			&& (this.numFol == castOther.numFol)
			&& (this.cveDelCtl == castOther.cveDelCtl)
			&& (this.cveSubCtl == castOther.cveSubCtl)
			&& (this.numEmbargo == castOther.numEmbargo)
			&& this.cvePatron.equals(castOther.cvePatron)
			&& this.cveMod.equals(castOther.cveMod);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveDepositaria ^ (this.cveDepositaria >>> 32)));
		hash = hash * prime + ((int) (this.numFol ^ (this.numFol >>> 32)));
		hash = hash * prime + ((int) (this.cveDelCtl ^ (this.cveDelCtl >>> 32)));
		hash = hash * prime + ((int) (this.cveSubCtl ^ (this.cveSubCtl >>> 32)));
		hash = hash * prime + ((int) (this.numEmbargo ^ (this.numEmbargo >>> 32)));
		hash = hash * prime + this.cvePatron.hashCode();
		hash = hash * prime + this.cveMod.hashCode();
		
		return hash;
    }
}