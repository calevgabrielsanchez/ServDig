package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SPC_MODALIDAD database table.
 * 
 */
@Embeddable
public class SpcModalidadPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_MODALIDAD")
	private String idModalidad;

	@Column(name="ID_REGIMEN", insertable=false, updatable=false)
	private String idRegimen;

	public SpcModalidadPK() {
	}
	public String getIdModalidad() {
		return this.idModalidad;
	}
	public void setIdModalidad(String idModalidad) {
		this.idModalidad = idModalidad;
	}
	public String getIdRegimen() {
		return this.idRegimen;
	}
	public void setIdRegimen(String idRegimen) {
		this.idRegimen = idRegimen;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SpcModalidadPK)) {
			return false;
		}
		SpcModalidadPK castOther = (SpcModalidadPK)other;
		return 
			this.idModalidad.equals(castOther.idModalidad)
			&& this.idRegimen.equals(castOther.idRegimen);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idModalidad.hashCode();
		hash = hash * prime + this.idRegimen.hashCode();
		
		return hash;
	}
}