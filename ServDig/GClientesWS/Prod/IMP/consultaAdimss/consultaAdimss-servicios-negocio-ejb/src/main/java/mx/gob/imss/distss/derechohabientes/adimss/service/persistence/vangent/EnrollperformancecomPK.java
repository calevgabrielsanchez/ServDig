package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLPERFORMANCECOM database table.
 * 
 */
@Embeddable
public class EnrollperformancecomPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idenrollperfcom;

	@Column(insertable=false, updatable=false)
	private long idcompanymanager;

	public EnrollperformancecomPK() {
	}
	public long getIdenrollperfcom() {
		return this.idenrollperfcom;
	}
	public void setIdenrollperfcom(long idenrollperfcom) {
		this.idenrollperfcom = idenrollperfcom;
	}
	public long getIdcompanymanager() {
		return this.idcompanymanager;
	}
	public void setIdcompanymanager(long idcompanymanager) {
		this.idcompanymanager = idcompanymanager;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnrollperformancecomPK)) {
			return false;
		}
		EnrollperformancecomPK castOther = (EnrollperformancecomPK)other;
		return 
			(this.idenrollperfcom == castOther.idenrollperfcom)
			&& (this.idcompanymanager == castOther.idcompanymanager);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idenrollperfcom ^ (this.idenrollperfcom >>> 32)));
		hash = hash * prime + ((int) (this.idcompanymanager ^ (this.idcompanymanager >>> 32)));
		
		return hash;
	}
}