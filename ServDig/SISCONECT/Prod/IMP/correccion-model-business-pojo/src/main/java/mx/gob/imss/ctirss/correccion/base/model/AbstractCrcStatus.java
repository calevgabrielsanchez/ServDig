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
 * The persistent class for the CRC_STATUS database table.
 * 
 */
@MappedSuperclass
public class AbstractCrcStatus extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_STATUS")
	private long cveStatus;

	@Column(name = "TX_DESCRIPCION")
	private String txDescripcion;

	public AbstractCrcStatus() {
	}

	public long getCveStatus() {
		return this.cveStatus;
	}

	public void setCveStatus(long cveStatus) {
		this.cveStatus = cveStatus;
	}

	public String getTxDescripcion() {
		return this.txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
	}

}