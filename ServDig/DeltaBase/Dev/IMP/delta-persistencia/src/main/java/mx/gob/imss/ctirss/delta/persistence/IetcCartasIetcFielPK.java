package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the IETC_CARTAS_IETC_FIEL database table.
 * 
 */
@Embeddable
public class IetcCartasIetcFielPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_SERIAL", unique=true, nullable=false, length=20)
	private String cveSerial;

	@Column(name="CVE_REGCONTADOR", unique=true, nullable=false, precision=22)
	private long cveRegcontador;

    public IetcCartasIetcFielPK() {
    }
	public String getCveSerial() {
		return this.cveSerial;
	}
	public void setCveSerial(String cveSerial) {
		this.cveSerial = cveSerial;
	}
	public long getCveRegcontador() {
		return this.cveRegcontador;
	}
	public void setCveRegcontador(long cveRegcontador) {
		this.cveRegcontador = cveRegcontador;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IetcCartasIetcFielPK)) {
			return false;
		}
		IetcCartasIetcFielPK castOther = (IetcCartasIetcFielPK)other;
		return 
			this.cveSerial.equals(castOther.cveSerial)
			&& (this.cveRegcontador == castOther.cveRegcontador);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cveSerial.hashCode();
		hash = hash * prime + ((int) (this.cveRegcontador ^ (this.cveRegcontador >>> 32)));
		
		return hash;
    }
}