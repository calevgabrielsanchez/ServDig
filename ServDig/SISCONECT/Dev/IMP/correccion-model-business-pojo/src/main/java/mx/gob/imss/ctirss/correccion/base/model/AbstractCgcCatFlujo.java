/**
 * RBG clean service
 * 2013-AGO-03
 */

package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@MappedSuperclass
public abstract class AbstractCgcCatFlujo extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_FLUJO")
	public long idFlujo;

	@Column(name = "DESC_FLUJO")
	private String descFlujo;

	public AbstractCgcCatFlujo() {
	}

	public long getIdFlujo() {
		return idFlujo;
	}

	public void setIdFlujo(long idFlujo) {
		this.idFlujo = idFlujo;
	}

	public String getDescFlujo() {
		return descFlujo;
	}

	public void setDescFlujo(String descFlujo) {
		this.descFlujo = descFlujo;
	}

}