package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DG_ASENTAMIENTO database table.
 * 
 */
@Embeddable
public class DgAsentamientoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ASEN", unique=true, nullable=false, length=13)
	private String cveAsen;

	@Column(name="CVE_ENT", unique=true, nullable=false, length=2)
	private String cveEnt;

	@Column(name="CVE_MUN", unique=true, nullable=false, length=3)
	private String cveMun;

	
	
    public DgAsentamientoPK() {
    }
	public String getCveAsen() {
		return this.cveAsen;
	}
	public void setCveAsen(String cveAsen) {
		this.cveAsen = cveAsen;
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
	
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DgAsentamientoPK)) {
			return false;
		}
		DgAsentamientoPK castOther = (DgAsentamientoPK)other;
		return 
			this.cveAsen.equals(castOther.cveAsen)
			&& this.cveEnt.equals(castOther.cveEnt)
			&& this.cveMun.equals(castOther.cveMun);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cveAsen.hashCode();
		hash = hash * prime + this.cveEnt.hashCode();
		hash = hash * prime + this.cveMun.hashCode();
		
		
		return hash;
    }
}