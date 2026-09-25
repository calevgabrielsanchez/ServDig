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
public class AbstractCgcCatStatus extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_STATUS")
	public long idStatus;

	@Column(name = "DESC_STATUS")
	private String descStatus;

	public AbstractCgcCatStatus() {
	}

	public long getIdStatus() {
		return this.idStatus;
	}

	public void setIdStatus(long idStatus) {
		this.idStatus = idStatus;
	}

	public String getDescStatus() {
		return this.descStatus;
	}

	public void setDescStatus(String descStatus) {
		this.descStatus = descStatus;
	}

}