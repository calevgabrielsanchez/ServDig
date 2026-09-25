/**
 * RBG clean service
 * 2013-AGO-03
 */

package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;

/**
 * The persistent class for the CGC_CATTIPOORIGEN database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcCatTipoOrigen extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_TIPOORIGEN")
	public long idTipoorigen;

	@Column(name = "DESC_ORIGEN")
	private String descripcion;

	// bi-directional many-to-one association to AbstractCgcCatorigen
	@ManyToOne
	@JoinColumn(name = "ID_ORIGEN")
	private CgcCatOrigen cgcCatOrigen;

	// bi-directional many-to-one association to AbstractCgcCattipo
	@ManyToOne
	@JoinColumn(name = "ID_TIPO")
	private CgcCatTipo cgcCatTipo;

	public AbstractCgcCatTipoOrigen() {
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getIdTipoorigen() {
		return idTipoorigen;
	}

	public void setIdTipoorigen(long idTipoorigen) {
		this.idTipoorigen = idTipoorigen;
	}

	public CgcCatOrigen getCgcCatOrigen() {
		return cgcCatOrigen;
	}

	public void setCgcCatOrigen(CgcCatOrigen cgcCatOrigen) {
		this.cgcCatOrigen = cgcCatOrigen;
	}

	public CgcCatTipo getCgcCatTipo() {
		return cgcCatTipo;
	}

	public void setCgcCatTipo(CgcCatTipo cgcCatTipo) {
		this.cgcCatTipo = cgcCatTipo;
	}

}