package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CGC_CATTIPOOBRAFUENTE database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcCatTipoObraFuente extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_TIPOOBRA")
	public long idTipoobra;

	@Column(name = "DESC_TIPOOBRA")
	private String descTipoobra;

	public AbstractCgcCatTipoObraFuente() {
	}

	public long getIdTipoobra() {
		return this.idTipoobra;
	}

	public void setIdTipoobra(long idTipoobra) {
		this.idTipoobra = idTipoobra;
	}

	public String getDescTipoobra() {
		return this.descTipoobra;
	}

	public void setDescTipoobra(String descTipoobra) {
		this.descTipoobra = descTipoobra;
	}

}