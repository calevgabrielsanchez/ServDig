package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import java.math.BigDecimal;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Column;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@MappedSuperclass
public class AbstractCrcMes  extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 465046429815677930L;
	
	private BigDecimal cveMes;
	private String txMes;
	
	@Id
	@Column(name = "CVE_MES", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCveMes() {
		return this.cveMes;
	}

	public void setCveMes(BigDecimal cveMes) {
		this.cveMes = cveMes;
	}

	@Column(name = "TX_MES", length = 20)
	public String getTxMes() {
		return this.txMes;
	}

	public void setTxMes(String txMes) {
		this.txMes = txMes;
	}
}
