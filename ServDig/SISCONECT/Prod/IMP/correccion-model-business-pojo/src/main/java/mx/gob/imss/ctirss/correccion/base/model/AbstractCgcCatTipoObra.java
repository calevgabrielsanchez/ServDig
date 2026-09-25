package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CGC_CATTIPOOBRA database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcCatTipoObra extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public AbstractCgcCatTipoObraPK id;

	private String tipoobra;

	public AbstractCgcCatTipoObra() {
	}

	public AbstractCgcCatTipoObraPK getId() {
		return this.id;
	}

	public void setId(AbstractCgcCatTipoObraPK id) {
		this.id = id;
	}

	public String getTipoobra() {
		return this.tipoobra;
	}

	public void setTipoobra(String tipoobra) {
		this.tipoobra = tipoobra;
	}

}