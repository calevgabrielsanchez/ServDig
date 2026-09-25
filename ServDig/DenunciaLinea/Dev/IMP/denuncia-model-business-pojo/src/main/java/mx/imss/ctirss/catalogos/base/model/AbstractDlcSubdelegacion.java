package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.framework.base.model.AbstractModel;

import java.math.BigDecimal;


/**
 * The persistent class for the DLC_SUBDELEGACION database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcSubdelegacion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_SUBDELEGACION")
	private Long cveSubdelegacion;

	@Column(name="CVE_CODIGO")
	private String cveCodigo;

	@Column(name="CVE_FK_DELEGACION")
	private BigDecimal cveFkDelegacion;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

    public AbstractDlcSubdelegacion() {
    }

	public String getCveCodigo() {
		return this.cveCodigo;
	}

	public void setCveCodigo(String cveCodigo) {
		this.cveCodigo = cveCodigo;
	}

	public BigDecimal getCveFkDelegacion() {
		return this.cveFkDelegacion;
	}

	public void setCveFkDelegacion(BigDecimal cveFkDelegacion) {
		this.cveFkDelegacion = cveFkDelegacion;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public Long getCveSubdelegacion() {
		return cveSubdelegacion;
	}

	public void setCveSubdelegacion(Long cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

}