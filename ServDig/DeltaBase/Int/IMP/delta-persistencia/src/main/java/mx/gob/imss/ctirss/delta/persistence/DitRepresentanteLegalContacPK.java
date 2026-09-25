package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DIT_REPRESENTANTE_LEGAL_CONTAC database table.
 * 
 */
@Embeddable
public class DitRepresentanteLegalContacPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_REPRESENTANTE_LEGAL")
	private long cveIdRepresentanteLegal;

	@Column(name="CVE_ID_FORMA_CONTACTO")
	private long cveIdFormaContacto;

	/*@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private long cveIdPatronSujetoObligado;*/

    public DitRepresentanteLegalContacPK() {
    }
	public long getCveIdRepresentanteLegal() {
		return this.cveIdRepresentanteLegal;
	}
	public void setCveIdRepresentanteLegal(long cveIdRepresentanteLegal) {
		this.cveIdRepresentanteLegal = cveIdRepresentanteLegal;
	}
	public long getCveIdFormaContacto() {
		return this.cveIdFormaContacto;
	}
	public void setCveIdFormaContacto(long cveIdFormaContacto) {
		this.cveIdFormaContacto = cveIdFormaContacto;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DitRepresentanteLegalContacPK)) {
			return false;
		}
		DitRepresentanteLegalContacPK castOther = (DitRepresentanteLegalContacPK)other;
		return 
			(this.cveIdRepresentanteLegal == castOther.cveIdRepresentanteLegal)
			&& (this.cveIdFormaContacto == castOther.cveIdFormaContacto);
    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveIdRepresentanteLegal ^ (this.cveIdRepresentanteLegal >>> 32)));
		hash = hash * prime + ((int) (this.cveIdFormaContacto ^ (this.cveIdFormaContacto >>> 32)));
		
		return hash;
    }
}