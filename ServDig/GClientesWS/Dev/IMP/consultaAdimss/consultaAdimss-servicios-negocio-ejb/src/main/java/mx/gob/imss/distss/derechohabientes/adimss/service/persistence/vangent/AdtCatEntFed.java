package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_ENT_FED database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_ENT_FED")
@NamedQuery(name="AdtCatEntFed.findAll", query="SELECT a FROM AdtCatEntFed a")
public class AdtCatEntFed implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ENTIDAD")
	private long cveEntidad;

	@Column(name="DES_ABREV_EDO")
	private String desAbrevEdo;

	@Column(name="DES_ENTIDAD")
	private String desEntidad;

	public AdtCatEntFed() {
	}

	public long getCveEntidad() {
		return this.cveEntidad;
	}

	public void setCveEntidad(long cveEntidad) {
		this.cveEntidad = cveEntidad;
	}

	public String getDesAbrevEdo() {
		return this.desAbrevEdo;
	}

	public void setDesAbrevEdo(String desAbrevEdo) {
		this.desAbrevEdo = desAbrevEdo;
	}

	public String getDesEntidad() {
		return this.desEntidad;
	}

	public void setDesEntidad(String desEntidad) {
		this.desEntidad = desEntidad;
	}

}