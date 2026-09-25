package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the IFR_CERTIFICADO_REQ database table.
 * 
 */
@Embeddable
public class IfrCertificadoReqPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_REQUERIMIENTO")
	private long cveRequerimiento;

	@Column(name="CVE_SERIAL")
	private String cveSerial;

	public IfrCertificadoReqPK() {
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
		if (!(other instanceof IfrCertificadoReqPK)) {
			return false;
		}
		IfrCertificadoReqPK castOther = (IfrCertificadoReqPK)other;
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