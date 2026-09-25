package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CRC_TIPOCORR database table.
 * 
 */
@MappedSuperclass
public class AbstractCrcTipoCorr extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_TIPOCORR")
	public long cveTipocorr;

	@Column(name = "ID_TIPOCORR")
	private BigDecimal idTipocorr;

	@Column(name = "TX_DESCRIPCION")
	private String txDescripcion;

	public AbstractCrcTipoCorr() {
	}

	public long getCveTipocorr() {
		return this.cveTipocorr;
	}

	public void setCveTipocorr(long cveTipocorr) {
		this.cveTipocorr = cveTipocorr;
	}

	public BigDecimal getIdTipocorr() {
		return this.idTipocorr;
	}

	public void setIdTipocorr(BigDecimal idTipocorr) {
		this.idTipocorr = idTipocorr;
	}

	public String getTxDescripcion() {
		return this.txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
	}

}