package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AUDITLOGDETAILS database table.
 * 
 */
@Embeddable
public class AuditlogdetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idauditlogdetail;

	@Column(insertable=false, updatable=false)
	private long idapplications;

	@Column(insertable=false, updatable=false)
	private long idusers;

	@Column(insertable=false, updatable=false)
	private long idenrollmentstation;

	@Column(insertable=false, updatable=false)
	private long idaudittypes;

	public AuditlogdetailPK() {
	}
	public long getIdauditlogdetail() {
		return this.idauditlogdetail;
	}
	public void setIdauditlogdetail(long idauditlogdetail) {
		this.idauditlogdetail = idauditlogdetail;
	}
	public long getIdapplications() {
		return this.idapplications;
	}
	public void setIdapplications(long idapplications) {
		this.idapplications = idapplications;
	}
	public long getIdusers() {
		return this.idusers;
	}
	public void setIdusers(long idusers) {
		this.idusers = idusers;
	}
	public long getIdenrollmentstation() {
		return this.idenrollmentstation;
	}
	public void setIdenrollmentstation(long idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}
	public long getIdaudittypes() {
		return this.idaudittypes;
	}
	public void setIdaudittypes(long idaudittypes) {
		this.idaudittypes = idaudittypes;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AuditlogdetailPK)) {
			return false;
		}
		AuditlogdetailPK castOther = (AuditlogdetailPK)other;
		return 
			(this.idauditlogdetail == castOther.idauditlogdetail)
			&& (this.idapplications == castOther.idapplications)
			&& (this.idusers == castOther.idusers)
			&& (this.idenrollmentstation == castOther.idenrollmentstation)
			&& (this.idaudittypes == castOther.idaudittypes);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idauditlogdetail ^ (this.idauditlogdetail >>> 32)));
		hash = hash * prime + ((int) (this.idapplications ^ (this.idapplications >>> 32)));
		hash = hash * prime + ((int) (this.idusers ^ (this.idusers >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		hash = hash * prime + ((int) (this.idaudittypes ^ (this.idaudittypes >>> 32)));
		
		return hash;
	}
}