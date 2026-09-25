package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the D_COP_SUBDELEGACION database table.
 * 
 */
@Embeddable
public class DCopSubdelegacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEGACION")
	private Long cveDelegacion;

	@Column(name="CVE_SUBDELEGACION")
	private Long cveSubdelegacion;

    public DCopSubdelegacionPK() {
    }
	public Long getCveDelegacion() {
		return this.cveDelegacion;
	}
	public void setCveDelegacion(Long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public Long getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}
	public void setCveSubdelegacion(Long cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DCopSubdelegacionPK)) {
			return false;
		}
		DCopSubdelegacionPK castOther = (DCopSubdelegacionPK)other;
		return 
			(this.cveDelegacion == castOther.cveDelegacion)
			&& (this.cveSubdelegacion == castOther.cveSubdelegacion);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveDelegacion ^ (this.cveDelegacion >>> 32)));
		hash = hash * prime + ((int) (this.cveSubdelegacion ^ (this.cveSubdelegacion >>> 32)));
		
		return hash;
    }
}