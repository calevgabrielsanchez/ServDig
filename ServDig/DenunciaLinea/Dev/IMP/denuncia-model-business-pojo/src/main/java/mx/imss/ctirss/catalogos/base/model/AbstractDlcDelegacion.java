package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.framework.base.model.AbstractModel;


/**
 * The persistent class for the DLC_DELEGACION database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcDelegacion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_PK")
	private Long cvePk;

	@Column(name="CVE_CODIGO")
	private String cveCodigo;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

    public AbstractDlcDelegacion() {
    }

	public String getCveCodigo() {
		return this.cveCodigo;
	}

	public void setCveCodigo(String cveCodigo) {
		this.cveCodigo = cveCodigo;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public Long getCvePk() {
		return cvePk;
	}

	public void setCvePk(Long cvePk) {
		this.cvePk = cvePk;
	}

}