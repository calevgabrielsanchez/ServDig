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
 * The persistent class for the CGC_CATSITUACIONCO database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcCatSituacionCO extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_SITUACIONCO")
	public long idSituacionco;

	@Column(name = "DESC_SITUACIONCO")
	private String descSituacionco;

	public AbstractCgcCatSituacionCO() {
	}

	public long getIdSituacionco() {
		return this.idSituacionco;
	}

	public void setIdSituacionco(long idSituacionco) {
		this.idSituacionco = idSituacionco;
	}

	public String getDescSituacionco() {
		return this.descSituacionco;
	}

	public void setDescSituacionco(String descSituacionco) {
		this.descSituacionco = descSituacionco;
	}

}