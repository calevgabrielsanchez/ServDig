package mx.imss.ctirss.catalogos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


import mx.imss.ctirss.framework.base.model.AbstractModel;


/**
 * The persistent class for the DLC_DIVISION database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcDivision extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_DIVISION")
	private Long cveDivision;	
	
	@Column(name="CVE_CODIGODIV")
	private String cveCodigoDiv;
	
	@Column(name="TX_DIVISION")
	private String txDivision;
	
	@Column(name="FEC_FECHAALTA")
	private Date fecFechaAlta;
	
	@Column(name="FEC_FECHABAJA")
	private Date fecFechaBaja;


	
	public Long getCveDivision() {
		return cveDivision;
	}

	public void setCveDivision(Long cveDivision) {
		this.cveDivision = cveDivision;
	}

	public String getCveCodigoDiv() {
		return cveCodigoDiv;
	}

	public void setCveCodigoDiv(String cveCodigoDiv) {
		this.cveCodigoDiv = cveCodigoDiv;
	}

	public String getTxDivision() {
		return txDivision;
	}

	public void setTxDivision(String txDivision) {
		this.txDivision = txDivision;
	}

	public Date getFecFechaAlta() {
		return fecFechaAlta;
	}

	public void setFecFechaAlta(Date fecFechaAlta) {
		this.fecFechaAlta = fecFechaAlta;
	}

	public Date getFecFechaBaja() {
		return fecFechaBaja;
	}

	public void setFecFechaBaja(Date fecFechaBaja) {
		this.fecFechaBaja = fecFechaBaja;
	}

	
	

}
