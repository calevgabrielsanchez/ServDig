package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@MappedSuperclass
public class AbstractCrtTramitePresentado extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_CVE_TRAMITEPRES_GENERATOR", sequenceName="SEQ_CVE_TRAMITEPRES")
	@GeneratedValue(generator="SEQ_CVE_TRAMITEPRES_GENERATOR")
	@Column(name="CVE_TRAMITEPRES")
	private Long cveTramitePres;
	
	@Column(name="CVE_SOLICITUDCORR")
	private Long cveSolcorr;
	
	@Column(name="CVE_TRAMITE")
	private Integer cveTramite;
	
	@Column(name="CVE_MENSAJE")
	private Integer cveMensaje;
	
	@Column(name="ID_TRAMITE_REFNOTARIA")
	private String idTramiteRefNotaria;
	
	@Column(name="CVE_USUARIOCURP")
	private String cveUsuarioCurp;
	
	@Column(name="FEC_FECHAREG")
	private Date fecFechaReg;
	
	
	@Column(name="DES_RAZONSOCIAL")
	private String desRazonSocial;
	
	@Column(name="DES_REGPATRONAL")
	private String desRegPatronal;
	
	@Column(name="URL_ACUSE_FIRMA")
	private String urlAcuseFirma;

	public Long getCveTramitePres() {
		return cveTramitePres;
	}

	public void setCveTramitePres(Long cveTramitePres) {
		this.cveTramitePres = cveTramitePres;
	}

	public Long getCveSolcorr() {
		return cveSolcorr;
	}

	public void setCveSolcorr(Long cveSolcorr) {
		this.cveSolcorr = cveSolcorr;
	}

	public Integer getCveTramite() {
		return cveTramite;
	}

	public void setCveTramite(Integer cveTramite) {
		this.cveTramite = cveTramite;
	}

	public Integer getCveMensaje() {
		return cveMensaje;
	}

	public void setCveMensaje(Integer cveMensaje) {
		this.cveMensaje = cveMensaje;
	}

	public String getIdTramiteRefNotaria() {
		return idTramiteRefNotaria;
	}

	public void setIdTramiteRefNotaria(String idTramiteRefNotaria) {
		this.idTramiteRefNotaria = idTramiteRefNotaria;
	}

	public String getCveUsuarioCurp() {
		return cveUsuarioCurp;
	}

	public void setCveUsuarioCurp(String cveUsuarioCurp) {
		this.cveUsuarioCurp = cveUsuarioCurp;
	}

	public Date getFecFechaReg() {
		return fecFechaReg;
	}

	public void setFecFechaReg(Date fecFechaReg) {
		this.fecFechaReg = fecFechaReg;
	}

	public String getDesRazonSocial() {
		return desRazonSocial;
	}

	public void setDesRazonSocial(String desRazonSocial) {
		this.desRazonSocial = desRazonSocial;
	}

	public String getDesRegPatronal() {
		return desRegPatronal;
	}

	public void setDesRegPatronal(String desRegPatronal) {
		this.desRegPatronal = desRegPatronal;
	}

	public String getUrlAcuseFirma() {
		return urlAcuseFirma;
	}

	public void setUrlAcuseFirma(String urlAcuseFirma) {
		this.urlAcuseFirma = urlAcuseFirma;
	}
	

	
}
