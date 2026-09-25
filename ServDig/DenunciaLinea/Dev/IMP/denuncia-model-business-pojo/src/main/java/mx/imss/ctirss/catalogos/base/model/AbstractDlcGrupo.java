package mx.imss.ctirss.catalogos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.imss.ctirss.framework.base.model.AbstractModel;


/**
 * The persistent class for the DLC_GRUPO database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcGrupo extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_GRUPO")
	private Long cveGrupo;
	
	
	@Column(name="CVE_DIVISION")
	private Long cveDivision;
	
	
	@Column(name="CVE_CODIGODIV")
	private String cveCodigoDiv;
	
	
	@Column(name="CVE_CODIGOGRU")
	private String cveCodigoGrupo;
	
	
	@Column(name="TX_GRUPO")
	private String txGrupo;
	
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAALTA")
	private Date fecFechaAlta;
	
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHABAJA")
	private Date fecFechaBaja;


	
	
	public Long getCveGrupo() {
		return cveGrupo;
	}

	public void setCveGrupo(Long cveGrupo) {
		this.cveGrupo = cveGrupo;
	}

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

	public String getCveCodigoGrupo() {
		return cveCodigoGrupo;
	}

	public void setCveCodigoGrupo(String cveCodigoGrupo) {
		this.cveCodigoGrupo = cveCodigoGrupo;
	}

	public String getTxGrupo() {
		return txGrupo;
	}

	public void setTxGrupo(String txGrupo) {
		this.txGrupo = txGrupo;
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
