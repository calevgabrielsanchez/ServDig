/**
 * RBG clean service
 * 2013-AGO-03
 */
package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CGC_CATPROCESO database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcCatProceso extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_PROCESO")
	public long idProceso;

	@Column(name = "DESC_PROCESO")
	private String descProceso;

	public AbstractCgcCatProceso() {
	}

	public long getIdProceso() {
		return this.idProceso;
	}

	public void setIdProceso(long idProceso) {
		this.idProceso = idProceso;
	}

	public String getDescProceso() {
		return this.descProceso;
	}

	public void setDescProceso(String descProceso) {
		this.descProceso = descProceso;
	}

}