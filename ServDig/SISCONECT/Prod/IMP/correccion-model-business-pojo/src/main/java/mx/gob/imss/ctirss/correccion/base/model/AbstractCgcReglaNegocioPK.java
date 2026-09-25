/**
 * RBG clean service
 * 2013-AGO-03
 */
package mx.gob.imss.ctirss.correccion.base.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CGC_REGLANEGOCIO database table.
 * 
 */
@Embeddable
public class AbstractCgcReglaNegocioPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_FLUJO")
	private long idFlujo;

	@Column(name = "NOMBRECONTROL")
	private String nombrecontrol;

	public AbstractCgcReglaNegocioPK() {
	}

	public long getIdFlujo() {
		return this.idFlujo;
	}

	public void setIdFlujo(long idFlujo) {
		this.idFlujo = idFlujo;
	}

	public String getNombrecontrol() {
		return this.nombrecontrol;
	}

	public void setNombrecontrol(String nombrecontrol) {
		this.nombrecontrol = nombrecontrol;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AbstractCgcReglaNegocioPK)) {
			return false;
		}
		AbstractCgcReglaNegocioPK castOther = (AbstractCgcReglaNegocioPK) other;
		return (this.idFlujo == castOther.idFlujo)
				&& this.nombrecontrol.equals(castOther.nombrecontrol);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idFlujo ^ (this.idFlujo >>> 32)));
		hash = hash * prime + this.nombrecontrol.hashCode();

		return hash;
	}
}