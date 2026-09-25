package mx.gob.imss.ctirss.correccion.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractDocumentoElectronicoModel;

/**
 * The persistent class for the CRT_ANEXOSOLCORRPAT database table.
 * 
 */
@MappedSuperclass
public class AbstractCrtAnexosolcorrpat extends
		AbstractDocumentoElectronicoModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CVE_PATRON_GENERATOR", sequenceName = "CVE_ANEXOSOLCORRPAT")
	@GeneratedValue(generator = "CVE_PATRON_GENERATOR")
	@Column(name = "CVE_ANEXOSOLCORRPAT")
	private Integer cveAnexoSolicitudCorrPat;

	@Column(name = "CVE_SOLICITUDCORR")
	private Integer cveSolicitudCorr;

	@Column(name = "CVE_FK_PATRON")
	private Long cvePatron;

	@Column(name = "CVE_FK_PATRON_PR")
	private Long cvePatronPr;

	@Column(name = "TX_RAZON_SOCIAL")
	private String txRazonSocial;

	@Column(name = "TX_REP_LEGAL")
	private String txRepresentanteLegal;

	@Column(name = "IN_TP_PATRON")
	private String tipoPatron;

	@Column(name = "TX_ACTIVIDAD")
	private String txActividad;

	@Column(name = "TX_CLASE")
	private String txClase;

	@Column(name = "TX_FRACCION")
	private String txFraccion;

	@Column(name = "TX_PRIMA")
	private String txPrima;

	@Column(name = "NU_TRABAJADORES")
	private Integer numTrabajadores;

	@Column(name = "CVE_DELEG_ORIG")
	private Integer cveDelegacionOrig;

	@Column(name = "SDELEG_ORIG")
	private Integer cveSubdelegacionOrig;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREG")
	private Date fecFechaRegistro;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	@Column(name = "TX_CURP")
	private String txCurp;

	@Column(name = "DOMICILIO_ID")
	private Integer cveDomicilio;

	@Column(name = "TX_RFC")
	private String txRfc;

	@Column(name = "TX_EMAIL")
	private String txEmail;

	@Column(name = "TX_TELEFONO")
	private String txTelefono;

	public AbstractCrtAnexosolcorrpat(Integer cveAnexoSolicitudCorrPat,
			Long cvePatron, Long cvePatronPr, String txRazonSocial) {
		super();
		this.cveAnexoSolicitudCorrPat = cveAnexoSolicitudCorrPat;
		this.cvePatron = cvePatron;
		this.cvePatronPr = cvePatronPr;
		this.txRazonSocial = txRazonSocial;
	}

	public AbstractCrtAnexosolcorrpat(Integer cveAnexoSolicitudCorrPat,
			Long cvePatron, Long cvePatronPr, String txRazonSocial,
			String registroPatronal, String tipoPatron, Integer cveDomicilio,
			Integer numTrabajadores, String txActividad, String txClase,
			String txFraccion, String txPrima) {
		super();
		this.cveAnexoSolicitudCorrPat = cveAnexoSolicitudCorrPat;
		this.cvePatron = cvePatron;
		this.cvePatronPr = cvePatronPr;
		this.txRazonSocial = txRazonSocial;
		this.tipoPatron = tipoPatron;
		this.cveDomicilio = cveDomicilio;
		this.numTrabajadores = numTrabajadores;
		this.txActividad = txActividad;
		this.txClase = txClase;
		this.txFraccion = txFraccion;
		this.txPrima = txPrima;
	}

	public AbstractCrtAnexosolcorrpat(Integer cveAnexoSolicitudCorrPat,
			Long cvePatron, Long cvePatronPr, String txRazonSocial,
			String registroPatronal, String tipoPatron, Integer cveDomicilio,
			Integer numTrabajadores, String txActividad, String txClase,
			String txFraccion, String txPrima, String txTelefono) {
		super();
		this.cveAnexoSolicitudCorrPat = cveAnexoSolicitudCorrPat;
		this.cvePatron = cvePatron;
		this.cvePatronPr = cvePatronPr;
		this.txRazonSocial = txRazonSocial;
		this.tipoPatron = tipoPatron;
		this.cveDomicilio = cveDomicilio;
		this.numTrabajadores = numTrabajadores;
		this.txActividad = txActividad;
		this.txClase = txClase;
		this.txFraccion = txFraccion;
		this.txPrima = txPrima;
		this.txTelefono = txTelefono;
	}
	public Integer getCveAnexoSolicitudCorrPat() {
		return cveAnexoSolicitudCorrPat;
	}

	public void setCveAnexoSolicitudCorrPat(Integer cveAnexoSolicitudCorrPat) {
		this.cveAnexoSolicitudCorrPat = cveAnexoSolicitudCorrPat;
	}

	public Integer getCveSolicitudCorr() {
		return cveSolicitudCorr;
	}

	public void setCveSolicitudCorr(Integer cveSolicitudCorr) {
		this.cveSolicitudCorr = cveSolicitudCorr;
	}

	public Long getCvePatron() {
		return cvePatron;
	}

	public void setCvePatron(Long cvePatron) {
		this.cvePatron = cvePatron;
	}

	public Long getCvePatronPr() {
		return cvePatronPr;
	}

	public void setCvePatronPr(Long cvePatronPr) {
		this.cvePatronPr = cvePatronPr;
	}

	public String getTxRazonSocial() {
		return txRazonSocial;
	}

	public void setTxRazonSocial(String txRazonSocial) {
		this.txRazonSocial = txRazonSocial;
	}

	public String getTxRepresentanteLegal() {
		return txRepresentanteLegal;
	}

	public void setTxRepresentanteLegal(String txRepresentanteLegal) {
		this.txRepresentanteLegal = txRepresentanteLegal;
	}

	public String getTipoPatron() {
		return tipoPatron;
	}

	public void setTipoPatron(String tipoPatron) {
		this.tipoPatron = tipoPatron;
	}

	public String getTxActividad() {
		return txActividad;
	}

	public void setTxActividad(String txActividad) {
		this.txActividad = txActividad;
	}

	public String getTxClase() {
		return txClase;
	}

	public void setTxClase(String txClase) {
		this.txClase = txClase;
	}

	public String getTxFraccion() {
		return txFraccion;
	}

	public void setTxFraccion(String txFraccion) {
		this.txFraccion = txFraccion;
	}

	public String getTxPrima() {
		return txPrima;
	}

	public void setTxPrima(String txPrima) {
		this.txPrima = txPrima;
	}

	public Integer getNumTrabajadores() {
		return numTrabajadores;
	}

	public void setNumTrabajadores(Integer numTrabajadores) {
		this.numTrabajadores = numTrabajadores;
	}

	public Integer getCveDelegacionOrig() {
		return cveDelegacionOrig;
	}

	public void setCveDelegacionOrig(Integer cveDelegacionOrig) {
		this.cveDelegacionOrig = cveDelegacionOrig;
	}

	public Integer getCveSubdelegacionOrig() {
		return cveSubdelegacionOrig;
	}

	public void setCveSubdelegacionOrig(Integer cveSubdelegacionOrig) {
		this.cveSubdelegacionOrig = cveSubdelegacionOrig;
	}

	public Date getFecFechaRegistro() {
		return fecFechaRegistro;
	}

	public void setFecFechaRegistro(Date fecFechaRegistro) {
		this.fecFechaRegistro = fecFechaRegistro;
	}

	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public String getTxCurp() {
		return txCurp;
	}

	public void setTxCurp(String txCurp) {
		this.txCurp = txCurp;
	}

	public Integer getCveDomicilio() {
		return cveDomicilio;
	}

	public void setCveDomicilio(Integer cveDomicilio) {
		this.cveDomicilio = cveDomicilio;
	}

	public String getTxRfc() {
		return txRfc;
	}

	public void setTxRfc(String txRfc) {
		this.txRfc = txRfc;
	}

	public String getTxEmail() {
		return txEmail;
	}

	public void setTxEmail(String txEmail) {
		this.txEmail = txEmail;
	}

	public String getTxTelefono() {
		return txTelefono;
	}

	public void setTxTelefono(String txTelefono) {
		this.txTelefono = txTelefono;
	}

	public AbstractCrtAnexosolcorrpat(Long cvePatronPr, String txRazonSocial,
			Integer cveAnexoSolicitudCorrPat) {
		super();
		this.cvePatronPr = cvePatronPr;
		this.txRazonSocial = txRazonSocial;
		this.cveAnexoSolicitudCorrPat = cveAnexoSolicitudCorrPat;
	}

	public AbstractCrtAnexosolcorrpat() {
		super();
	}

}