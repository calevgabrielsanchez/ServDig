package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the IETC_CERTIFICADO_REQ database table.
 * 
 */
@Embeddable
public class IetcCertificadoReqPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_REQUERIMIENTO", unique=true, nullable=false, precision=22)
	private long cveRequerimiento;

	@Column(name="CVE_REGCONTADOR", unique=true, nullable=false, precision=22)
	private long cveRegcontador;

	@Column(name="CVE_SERIAL", unique=true, nullable=false, length=20)
	private String cveSerial;

    public IetcCertificadoReqPK() {
    }
	public long getCveRequerimiento() {
		return this.cveRequerimiento;
	}
	public void setCveRequerimiento(long cveRequerimiento) {
		this.cveRequerimiento = cveRequerimiento;
	}
	public long getCveRegcontador() {
		return this.cveRegcontador;
	}
	public void setCveRegcontador(long cveRegcontador) {
		this.cveRegcontador = cveRegcontador;
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
		if (!(other instanceof IetcCertificadoReqPK)) {
			return false;
		}
		IetcCertificadoReqPK castOther = (IetcCertificadoReqPK)other;
		return 
			(this.cveRequerimiento == castOther.cveRequerimiento)
			&& (this.cveRegcontador == castOther.cveRegcontador)
			&& this.cveSerial.equals(castOther.cveSerial);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.cveRequerimiento ^ (this.cveRequerimiento >>> 32)));
		hash = hash * prime + ((int) (this.cveRegcontador ^ (this.cveRegcontador >>> 32)));
		hash = hash * prime + this.cveSerial.hashCode();
		
		return hash;
    }
}