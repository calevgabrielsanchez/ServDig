package mx.gob.imss.ctirss.reing.patrones.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the APC_FRACCION database table.
 * 
 */
@Embeddable
public class ApcFraccionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_FRACCION")
	private long cveFraccion;

	@Column(name="CVE_GRUPO")
	private long cveGrupo;

	@Column(name="CVE_DIVISION")
	private long cveDivision;

    public ApcFraccionPK() {
    }
	public long getCveFraccion() {
		return this.cveFraccion;
	}
	public void setCveFraccion(long cveFraccion) {
		this.cveFraccion = cveFraccion;
	}
	public long getCveGrupo() {
		return this.cveGrupo;
	}
	public void setCveGrupo(long cveGrupo) {
		this.cveGrupo = cveGrupo;
	}
	public long getCveDivision() {
		return this.cveDivision;
	}
	public void setCveDivision(long cveDivision) {
		this.cveDivision = cveDivision;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ApcFraccionPK)) {
			return false;
		}
		ApcFraccionPK castOther = (ApcFraccionPK)other;
		return 
			(this.cveFraccion == castOther.cveFraccion)
			&& (this.cveGrupo == castOther.cveGrupo)
			&& (this.cveDivision == castOther.cveDivision);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveFraccion ^ (this.cveFraccion >>> 32)));
		hash = hash * prime + ((int) (this.cveGrupo ^ (this.cveGrupo >>> 32)));
		hash = hash * prime + ((int) (this.cveDivision ^ (this.cveDivision >>> 32)));
		
		return hash;
    }
}