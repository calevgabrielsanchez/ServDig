package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SPC_MUNICIPIO_DELEGACION database table.
 * 
 */
@Embeddable
public class SpcMunicipioDelegacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_MUNICIPIO_DELEGACION")
	private String idMunicipioDelegacion;

	@Column(name="CVE_ENTIDAD_FEDERATIVA")
	private String cveEntidadFederativa;

	public SpcMunicipioDelegacionPK() {
	}
	public String getIdMunicipioDelegacion() {
		return this.idMunicipioDelegacion;
	}
	public void setIdMunicipioDelegacion(String idMunicipioDelegacion) {
		this.idMunicipioDelegacion = idMunicipioDelegacion;
	}
	public String getCveEntidadFederativa() {
		return this.cveEntidadFederativa;
	}
	public void setCveEntidadFederativa(String cveEntidadFederativa) {
		this.cveEntidadFederativa = cveEntidadFederativa;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SpcMunicipioDelegacionPK)) {
			return false;
		}
		SpcMunicipioDelegacionPK castOther = (SpcMunicipioDelegacionPK)other;
		return 
			this.idMunicipioDelegacion.equals(castOther.idMunicipioDelegacion)
			&& this.cveEntidadFederativa.equals(castOther.cveEntidadFederativa);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idMunicipioDelegacion.hashCode();
		hash = hash * prime + this.cveEntidadFederativa.hashCode();
		
		return hash;
	}
}