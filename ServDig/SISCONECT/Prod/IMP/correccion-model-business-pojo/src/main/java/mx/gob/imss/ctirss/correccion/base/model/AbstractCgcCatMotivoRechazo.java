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
 * The persistent class for the CGC_CATMOTIVORECHAZO database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcCatMotivoRechazo extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_MOTIVORECHAZO")
	public long idMotivorechazo;

	private String motivorechazo;

	public AbstractCgcCatMotivoRechazo() {
	}

	public long getIdMotivorechazo() {
		return this.idMotivorechazo;
	}

	public void setIdMotivorechazo(long idMotivorechazo) {
		this.idMotivorechazo = idMotivorechazo;
	}

	public String getMotivorechazo() {
		return this.motivorechazo;
	}

	public void setMotivorechazo(String motivorechazo) {
		this.motivorechazo = motivorechazo;
	}
}