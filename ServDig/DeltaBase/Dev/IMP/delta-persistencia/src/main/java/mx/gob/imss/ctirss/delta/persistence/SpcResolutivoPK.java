package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SPC_RESOLUTIVO database table.
 * 
 */
@Embeddable
public class SpcResolutivoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_REGIMEN", insertable=false, updatable=false)
	private String idRegimen;

	@Column(name="CVE_RESOLUTIVO")
	private String cveResolutivo;

	@Column(name="CVE_TIPO_RESOLUCION")
	private String cveTipoResolucion;

	public SpcResolutivoPK() {
	}
	public String getIdRegimen() {
		return this.idRegimen;
	}
	public void setIdRegimen(String idRegimen) {
		this.idRegimen = idRegimen;
	}
	public String getCveResolutivo() {
		return this.cveResolutivo;
	}
	public void setCveResolutivo(String cveResolutivo) {
		this.cveResolutivo = cveResolutivo;
	}
	public String getCveTipoResolucion() {
		return this.cveTipoResolucion;
	}
	public void setCveTipoResolucion(String cveTipoResolucion) {
		this.cveTipoResolucion = cveTipoResolucion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SpcResolutivoPK)) {
			return false;
		}
		SpcResolutivoPK castOther = (SpcResolutivoPK)other;
		return 
			this.idRegimen.equals(castOther.idRegimen)
			&& this.cveResolutivo.equals(castOther.cveResolutivo)
			&& this.cveTipoResolucion.equals(castOther.cveTipoResolucion);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idRegimen.hashCode();
		hash = hash * prime + this.cveResolutivo.hashCode();
		hash = hash * prime + this.cveTipoResolucion.hashCode();
		
		return hash;
	}
}