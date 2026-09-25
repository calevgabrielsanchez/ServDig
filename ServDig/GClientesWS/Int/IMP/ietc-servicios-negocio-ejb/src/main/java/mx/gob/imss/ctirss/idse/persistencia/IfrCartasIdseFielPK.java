package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the IFR_CARTAS_IDSE_FIEL database table.
 * 
 */
@Embeddable
public class IfrCartasIdseFielPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_REQUERIMIENTO", insertable=false, updatable=false)
	private long cveRequerimiento;

	@Column(name="CVE_SERIAL", insertable=false, updatable=false)
	private String cveSerial;

	public IfrCartasIdseFielPK() {
	}
	public long getCveRequerimiento() {
		return this.cveRequerimiento;
	}
	public void setCveRequerimiento(long cveRequerimiento) {
		this.cveRequerimiento = cveRequerimiento;
	}
	public String getCveSerial() {
		return this.cveSerial;
	}
	public void setCveSerial(String cveSerial) {
		this.cveSerial = cveSerial;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IfrCartasIdseFielPK)) {
			return false;
		}
		IfrCartasIdseFielPK castOther = (IfrCartasIdseFielPK)other;
		return 
			(this.cveRequerimiento == castOther.cveRequerimiento)
			&& this.cveSerial.equals(castOther.cveSerial);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveRequerimiento ^ (this.cveRequerimiento >>> 32)));
		hash = hash * prime + this.cveSerial.hashCode();
		
		return hash;
	}
}