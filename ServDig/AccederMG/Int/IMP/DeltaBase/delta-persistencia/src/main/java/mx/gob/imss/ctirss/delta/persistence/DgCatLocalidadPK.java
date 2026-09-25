package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DG_CAT_LOCALIDAD database table.
 * 
 */
@Embeddable
public class DgCatLocalidadPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ENT", unique=true, nullable=false, length=2)
	private String cveEnt;

	@Column(name="CVE_MUN", unique=true, nullable=false, length=3)
	private String cveMun;

	@Column(name="CVE_LOC", unique=true, nullable=false, length=4)
	private String cveLoc;

	@Column(name="CVE_PERIODO", unique=true, nullable=false, precision=2)
	private long cvePeriodo;

    public DgCatLocalidadPK() {
    }
	public String getCveEnt() {
		return this.cveEnt;
	}
	public void setCveEnt(String cveEnt) {
		this.cveEnt = cveEnt;
	}
	public String getCveMun() {
		return this.cveMun;
	}
	public void setCveMun(String cveMun) {
		this.cveMun = cveMun;
	}
	public String getCveLoc() {
		return this.cveLoc;
	}
	public void setCveLoc(String cveLoc) {
		this.cveLoc = cveLoc;
	}
	public long getCvePeriodo() {
		return this.cvePeriodo;
	}
	public void setCvePeriodo(long cvePeriodo) {
		this.cvePeriodo = cvePeriodo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DgCatLocalidadPK)) {
			return false;
		}
		DgCatLocalidadPK castOther = (DgCatLocalidadPK)other;
		return 
			this.cveEnt.equals(castOther.cveEnt)
			&& this.cveMun.equals(castOther.cveMun)
			&& this.cveLoc.equals(castOther.cveLoc)
			&& (this.cvePeriodo == castOther.cvePeriodo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cveEnt.hashCode();
		hash = hash * prime + this.cveMun.hashCode();
		hash = hash * prime + this.cveLoc.hashCode();
		hash = hash * prime + ((int) (this.cvePeriodo ^ (this.cvePeriodo >>> 32)));
		
		return hash;
    }
}