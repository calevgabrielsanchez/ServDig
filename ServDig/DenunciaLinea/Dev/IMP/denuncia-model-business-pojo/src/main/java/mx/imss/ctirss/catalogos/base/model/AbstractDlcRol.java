package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.framework.base.model.AbstractModel;


/**
 * The persistent class for the DLC_ROL database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcRol extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ROL")
	private long cveRol;

	@Column(name="DESC_ROL")
	private String descRol;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

    public AbstractDlcRol() {
    }

	public long getCveRol() {
		return this.cveRol;
	}

	public void setCveRol(long cveRol) {
		this.cveRol = cveRol;
	}

	public String getDescRol() {
		return this.descRol;
	}

	public void setDescRol(String descRol) {
		this.descRol = descRol;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

}