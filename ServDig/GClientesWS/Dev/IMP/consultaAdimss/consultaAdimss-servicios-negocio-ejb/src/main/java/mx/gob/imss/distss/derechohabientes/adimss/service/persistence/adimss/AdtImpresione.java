package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the ADT_IMPRESIONES database table.
 * 
 */
@Entity(name="mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss.AdtImpresione")
@Table(name="ADT_IMPRESIONES")
public class AdtImpresione implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name="CVE_CALIDAD_ASEG")
	private BigDecimal cveCalidadAseg;

	@Column(name="CVE_FOLIO_EXPEDICION")
	private BigDecimal cveFolioExpedicion;

	@Column(name="NUM_FOLIO_PERSONA")
	private BigDecimal numFolioPersona;
	@Id
	@Column(name="NUM_NSS_ASEG")
	private String numNssAseg;
//
//	@Column(name="STP_IMPRESION")
//	private Object stpImpresion;

	//bi-directional many-to-one association to AdcMotivoImp
	@ManyToOne
	@JoinColumn(name="CVE_MOTIVO_IMPRESION")
	private AdcMotivoImp adcMotivoImp;

	public AdtImpresione() {
	}

	public BigDecimal getCveCalidadAseg() {
		return this.cveCalidadAseg;
	}

	public void setCveCalidadAseg(BigDecimal cveCalidadAseg) {
		this.cveCalidadAseg = cveCalidadAseg;
	}

	public BigDecimal getCveFolioExpedicion() {
		return this.cveFolioExpedicion;
	}

	public void setCveFolioExpedicion(BigDecimal cveFolioExpedicion) {
		this.cveFolioExpedicion = cveFolioExpedicion;
	}

	public BigDecimal getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(BigDecimal numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

	public String getNumNssAseg() {
		return this.numNssAseg;
	}

	public void setNumNssAseg(String numNssAseg) {
		this.numNssAseg = numNssAseg;
	}

//	public Object getStpImpresion() {
//		return this.stpImpresion;
//	}
//
//	public void setStpImpresion(Object stpImpresion) {
//		this.stpImpresion = stpImpresion;
//	}

	public AdcMotivoImp getAdcMotivoImp() {
		return this.adcMotivoImp;
	}

	public void setAdcMotivoImp(AdcMotivoImp adcMotivoImp) {
		this.adcMotivoImp = adcMotivoImp;
	}

}