package mx.imss.ctirss.catalogos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import mx.imss.ctirss.framework.base.model.AbstractModel;

@MappedSuperclass
public abstract class AbstractDlcActEconomica extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="CVE_ACTECONOMICA")
	private Long cveActEconomica;
	
	@Column(name="CVE_GRUPO")
	private Long cveGrupo;
	
	@Column(name="CVE_CODIGODIV")
	private String cveCodigoDiv;
	
	@Column(name="CVE_CODIGOGRU")
	private String cveCodigoGru;
	
	@Column(name="CVE_CODIGOACT")
	private String cveCodigoAct;
	
	@Column(name="TX_ACTIVIDAD")
	private String txActividad;
	
	@Column(name="FEC_FECHAALTA")
	private Date fecFechaAlta;
	
	@Column(name="FEC_FECHABAJA")
	private Date fecFechaBaja;

	public Long getCveActEconomica() {
		return cveActEconomica;
	}

	public void setCveActEconomica(Long cveActEconomica) {
		this.cveActEconomica = cveActEconomica;
	}

	public Long getCveGrupo() {
		return cveGrupo;
	}

	public void setCveGrupo(Long cveGrupo) {
		this.cveGrupo = cveGrupo;
	}

	public String getCveCodigoDiv() {
		return cveCodigoDiv;
	}

	public void setCveCodigoDiv(String cveCodigoDiv) {
		this.cveCodigoDiv = cveCodigoDiv;
	}

	public String getCveCodigoGru() {
		return cveCodigoGru;
	}

	public void setCveCodigoGru(String cveCodigoGru) {
		this.cveCodigoGru = cveCodigoGru;
	}

	public String getCveCodigoAct() {
		return cveCodigoAct;
	}

	public void setCveCodigoAct(String cveCodigoAct) {
		this.cveCodigoAct = cveCodigoAct;
	}

	public String getTxActividad() {
		return txActividad;
	}

	public void setTxActividad(String txActividad) {
		this.txActividad = txActividad;
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
