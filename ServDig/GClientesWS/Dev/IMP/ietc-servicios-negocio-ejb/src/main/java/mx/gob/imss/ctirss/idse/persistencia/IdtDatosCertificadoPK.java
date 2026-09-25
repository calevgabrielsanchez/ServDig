package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the IDT_DATOS_CERTIFICADO database table.
 * 
 */
@Embeddable
public class IdtDatosCertificadoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="REF_RFC_ASOCIADO")
	private String refRfcAsociado;

	@Column(name="CVE_SERIAL_FIEL")
	private String cveSerialFiel;

    public IdtDatosCertificadoPK() {
    }
	public String getRefRfcAsociado() {
		return this.refRfcAsociado;
	}
	public void setRefRfcAsociado(String refRfcAsociado) {
		this.refRfcAsociado = refRfcAsociado;
	}
	public String getCveSerialFiel() {
		return this.cveSerialFiel;
	}
	public void setCveSerialFiel(String cveSerialFiel) {
		this.cveSerialFiel = cveSerialFiel;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IdtDatosCertificadoPK)) {
			return false;
		}
		IdtDatosCertificadoPK castOther = (IdtDatosCertificadoPK)other;
		return 
			this.refRfcAsociado.equals(castOther.refRfcAsociado)
			&& this.cveSerialFiel.equals(castOther.cveSerialFiel);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.refRfcAsociado.hashCode();
		hash = hash * prime + this.cveSerialFiel.hashCode();
		
		return hash;
    }
}