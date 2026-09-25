package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the IETC_DATOS_CERTIFICADO database table.
 * 
 */
@Embeddable
public class IetcDatosCertificadoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_SERIAL", unique=true, nullable=false, length=20)
	private String cveSerial;

	@Column(name="CVE_REGCONTADOR", unique=true, nullable=false, precision=22)
	private long cveRegcontador;

    public IetcDatosCertificadoPK() {
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
		if (!(other instanceof IetcDatosCertificadoPK)) {
			return false;
		}
		IetcDatosCertificadoPK castOther = (IetcDatosCertificadoPK)other;
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