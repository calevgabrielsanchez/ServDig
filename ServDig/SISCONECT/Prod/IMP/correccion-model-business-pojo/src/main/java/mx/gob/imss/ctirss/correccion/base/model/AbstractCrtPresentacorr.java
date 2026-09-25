package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractDocumentoElectronicoModel;

@MappedSuperclass
public class AbstractCrtPresentacorr extends AbstractDocumentoElectronicoModel {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CVE_PRESENTACION_CORRECCION_GENERATOR", sequenceName = "SEQ_CVE_PRESENTACORR")
	@GeneratedValue(generator = "CVE_PRESENTACION_CORRECCION_GENERATOR")
	@Column(name = "CVE_PRESENTACORR")
	private Integer cvePresentacorr;

	@Column(name = "CVE_SOLICITUDCORR")
	private Integer cveSolicitudcorr;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_AUTORIZACION")
	private Date fecAutorizacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_ACEPTACION")
	private Date fecAceptacion;

	@Column(name = "NU_TRABREG")
	private Integer nuTrabreg;

	@Column(name = "IMP_COP")
	private BigDecimal impCop;

	@Column(name = "IMP_COPACT")
	private BigDecimal impCopact;

	@Column(name = "IMP_COPREC")
	private BigDecimal impCoprec;

	@Column(name = "IMP_COPTOT")
	private BigDecimal impCoptot;

	@Column(name = "IMP_RCV")
	private BigDecimal impRcv;

	@Column(name = "IMP_RCVACT")
	private BigDecimal impRcvact;

	@Column(name = "IMP_RCVREC")
	private BigDecimal impRcvrec;

	@Column(name = "IMP_RCVTOT")
	private BigDecimal impRcvtot;

	@Column(name = "NU_COMPROBANTEPAGO")
	private BigDecimal nuComprobantepago;

	@Column(name = "NU_COMPROMOVAFIL")
	private BigDecimal nuCompromovafil;

	@Column(name = "NU_DOCTOSUSTENTO")
	private BigDecimal nuDoctosustento;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_ELABORAPRE")
	private Date fecElaborapre;

	@Column(name = "REF_LUGAR")
	private String refLugar;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREG")
	private Date fecFechareg;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	@Column(name = "TX_OBSERVACIONES")
	private String observaciones;

	@Column(name = "TX_REP_LEGAL_ELAB")
	private String representanteLegalElaboro;

	public void CrtPresentacorr() {
	}

	public void CrtPresentacorr(Integer cvePresentacorr,
			Integer cveSolicitudcorr, Date fecAutorizacion, Date fecAceptacion,
			Integer nuTrabreg, BigDecimal impCop, BigDecimal impCopact,
			BigDecimal impCoprec, BigDecimal impCoptot, BigDecimal impRcv,
			BigDecimal impRcvact, BigDecimal impRcvrec, BigDecimal impRcvtot,
			BigDecimal nuComprobantepago, BigDecimal nuCompromovafil,
			BigDecimal nuDoctosustento, Date fecElaborapre, String refLugar,
			Date fecFechareg, String cveUsuario) {
		this.cvePresentacorr = cvePresentacorr;
		this.cveSolicitudcorr = cveSolicitudcorr;
		this.fecAutorizacion = fecAutorizacion;
		this.fecAceptacion = fecAceptacion;
		this.nuTrabreg = nuTrabreg;
		this.impCop = impCop;
		this.impCopact = impCopact;
		this.impCoprec = impCoprec;
		this.impCoptot = impCoptot;
		this.impRcv = impRcv;
		this.impRcvact = impRcvact;
		this.impRcvrec = impRcvrec;
		this.impRcvtot = impRcvtot;
		this.nuComprobantepago = nuComprobantepago;
		this.nuCompromovafil = nuCompromovafil;
		this.nuDoctosustento = nuDoctosustento;
		this.fecElaborapre = fecElaborapre;
		this.refLugar = refLugar;
		this.fecFechareg = fecFechareg;
		this.cveUsuario = cveUsuario;
	}

	public Integer getCvePresentacorr() {
		return cvePresentacorr;
	}

	public void setCvePresentacorr(Integer cvePresentacorr) {
		this.cvePresentacorr = cvePresentacorr;
	}

	public Integer getCveSolicitudcorr() {
		return cveSolicitudcorr;
	}

	public void setCveSolicitudcorr(Integer cveSolicitudcorr) {
		this.cveSolicitudcorr = cveSolicitudcorr;
	}

	public Date getFecAutorizacion() {
		return fecAutorizacion;
	}

	public void setFecAutorizacion(Date fecAutorizacion) {
		this.fecAutorizacion = fecAutorizacion;
	}

	public Date getFecAceptacion() {
		return fecAceptacion;
	}

	public void setFecAceptacion(Date fecAceptacion) {
		this.fecAceptacion = fecAceptacion;
	}

	public Integer getNuTrabreg() {
		return nuTrabreg;
	}

	public void setNuTrabreg(Integer nuTrabreg) {
		this.nuTrabreg = nuTrabreg;
	}

	public BigDecimal getImpCop() {
		return impCop;
	}

	public void setImpCop(BigDecimal impCop) {
		this.impCop = impCop;
	}

	public BigDecimal getImpCopact() {
		return impCopact;
	}

	public void setImpCopact(BigDecimal impCopact) {
		this.impCopact = impCopact;
	}

	public BigDecimal getImpCoprec() {
		return impCoprec;
	}

	public void setImpCoprec(BigDecimal impCoprec) {
		this.impCoprec = impCoprec;
	}

	public BigDecimal getImpCoptot() {
		return impCoptot;
	}

	public void setImpCoptot(BigDecimal impCoptot) {
		this.impCoptot = impCoptot;
	}

	public BigDecimal getImpRcv() {
		return impRcv;
	}

	public void setImpRcv(BigDecimal impRcv) {
		this.impRcv = impRcv;
	}

	public BigDecimal getImpRcvact() {
		return impRcvact;
	}

	public void setImpRcvact(BigDecimal impRcvact) {
		this.impRcvact = impRcvact;
	}

	public BigDecimal getImpRcvrec() {
		return impRcvrec;
	}

	public void setImpRcvrec(BigDecimal impRcvrec) {
		this.impRcvrec = impRcvrec;
	}

	public BigDecimal getImpRcvtot() {
		return impRcvtot;
	}

	public void setImpRcvtot(BigDecimal impRcvtot) {
		this.impRcvtot = impRcvtot;
	}

	public BigDecimal getNuComprobantepago() {
		return nuComprobantepago;
	}

	public void setNuComprobantepago(BigDecimal nuComprobantepago) {
		this.nuComprobantepago = nuComprobantepago;
	}

	public BigDecimal getNuCompromovafil() {
		return nuCompromovafil;
	}

	public void setNuCompromovafil(BigDecimal nuCompromovafil) {
		this.nuCompromovafil = nuCompromovafil;
	}

	public BigDecimal getNuDoctosustento() {
		return nuDoctosustento;
	}

	public void setNuDoctosustento(BigDecimal nuDoctosustento) {
		this.nuDoctosustento = nuDoctosustento;
	}

	public Date getFecElaborapre() {
		return fecElaborapre;
	}

	public void setFecElaborapre(Date fecElaborapre) {
		this.fecElaborapre = fecElaborapre;
	}

	public String getRefLugar() {
		return refLugar;
	}

	public void setRefLugar(String refLugar) {
		this.refLugar = refLugar;
	}

	public Date getFecFechareg() {
		return fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getRepresentanteLegalElaboro() {
		return representanteLegalElaboro;
	}

	public void setRepresentanteLegalElaboro(String representanteLegalElaboro) {
		this.representanteLegalElaboro = representanteLegalElaboro;
	}
}
