/**
 * RBG clean service
 * 2013-AGO-03
 */
package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgcCatDelegacion;

/**
 * The persistent class for the CGC_CATSUBDELEGACION database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcCatSubdelegacion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_SUBDELEGACION")
	public long idSubdelegacion;

	@Column(name = "CVE_OFICIAL")
	private String cveOficial;

	@Column(name = "CVE_SUBDELEGACION")
	private BigDecimal cveSubdelegacion;

	@Column(name = "DESC_SUBDELEGACION")
	private String descSubdelegacion;

	// bi-directional many-to-one association to AbstractCgcCatdelegacion
	@ManyToOne
	@JoinColumn(name = "CVE_DELEGACION")
	private CgcCatDelegacion cgcCatdelegacion;

	public AbstractCgcCatSubdelegacion() {
	}

	public long getIdSubdelegacion() {
		return this.idSubdelegacion;
	}

	public void setIdSubdelegacion(long idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
	}

	public String getCveOficial() {
		return this.cveOficial;
	}

	public void setCveOficial(String cveOficial) {
		this.cveOficial = cveOficial;
	}

	public BigDecimal getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}

	public void setCveSubdelegacion(BigDecimal cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public String getDescSubdelegacion() {
		return this.descSubdelegacion;
	}

	public void setDescSubdelegacion(String descSubdelegacion) {
		this.descSubdelegacion = descSubdelegacion;
	}

	public CgcCatDelegacion getCgcCatdelegacion() {
		return cgcCatdelegacion;
	}

	public void setCgcCatdelegacion(CgcCatDelegacion cgcCatdelegacion) {
		this.cgcCatdelegacion = cgcCatdelegacion;
	}

}