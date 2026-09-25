package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.framework.base.model.AbstractModel;


/**
 * The persistent class for the DLC_STATUS database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcStatus extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_STATUS")
	private Long idStatus;

	@Column(name="DES_STATUS")
	private String desStatus;

    public AbstractDlcStatus() {
    }

	public Long getIdStatus() {
		return this.idStatus;
	}

	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}

	public String getDesStatus() {
		return this.desStatus;
	}

	public void setDesStatus(String desStatus) {
		this.desStatus = desStatus;
	}

}