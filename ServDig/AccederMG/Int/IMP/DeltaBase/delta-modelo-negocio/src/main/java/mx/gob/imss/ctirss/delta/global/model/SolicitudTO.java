package mx.gob.imss.ctirss.delta.global.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Certificado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;

public class SolicitudTO extends AbstractModel {

	private static final long serialVersionUID = 1L;
	private Long solicitudId;
	private Date fechaSolicitud;
	private String noFolioSolicitud;
	private EstadoSolicitud estadoSolicitud;
	private TipoSolicitud tipoSolicitud;

	private List<TramiteTO> tramites;
	private PersonaTO persona;
	private RegistroPatronalTO registroPatronal;
	private Certificado certificado;
	private List<String> correosNotificacion;
	private String archivoAProcesar;
	private String usuarioResponsable;
	private String observacion;

	private OrigenSolicitud origenSolicitud;

	/**
	 * Atributos necesarios para guardar lo relacionado a la firma digital
	 */
	private boolean firmadaDigitalmente;
	private String cadenaOriginal;
	private String selloDigital;
	private String secuenciaDeNotaria;
	private String numeroSerieCertificado;
	private FirmaElectronica firmaElectronica;

	/**
	 * Atributo auxiliar a la lista de trámites, es para utilizarlo en el
	 * OSB
	 */
	private TramiteTO[] tramitesAux;

	public Long getSolicitudId() {
		return solicitudId;
	}

	public void setSolicitudId(Long solicitudId) {
		this.solicitudId = solicitudId;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getNoFolioSolicitud() {
		return noFolioSolicitud;
	}

	public void setNoFolioSolicitud(String noFolioSolicitud) {
		this.noFolioSolicitud = noFolioSolicitud;
	}

	public EstadoSolicitud getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public TipoSolicitud getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public List<TramiteTO> getTramites() {
		return tramites;
	}

	public void setTramites(List<TramiteTO> tramites) {
		this.tramites = tramites;
	}

	public PersonaTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaTO persona) {
		this.persona = persona;
	}

	public RegistroPatronalTO getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(RegistroPatronalTO registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public Certificado getCertificado() {
		return certificado;
	}

	public void setCertificado(Certificado certificado) {
		this.certificado = certificado;
	}

	public List<String> getCorreosNotificacion() {
		return correosNotificacion;
	}

	public void setCorreosNotificacion(List<String> correosNotificacion) {
		this.correosNotificacion = correosNotificacion;
	}

	public String getArchivoAProcesar() {
		return archivoAProcesar;
	}

	public void setArchivoAProcesar(String archivoAProcesar) {
		this.archivoAProcesar = archivoAProcesar;
	}

	public String getUsuarioResponsable() {
		return usuarioResponsable;
	}

	public void setUsuarioResponsable(String usuarioResponsable) {
		this.usuarioResponsable = usuarioResponsable;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public OrigenSolicitud getOrigenSolicitud() {
		return origenSolicitud;
	}

	public void setOrigenSolicitud(OrigenSolicitud origenSolicitud) {
		this.origenSolicitud = origenSolicitud;
	}

	public boolean isFirmadaDigitalmente() {
		return firmadaDigitalmente;
	}

	public void setFirmadaDigitalmente(boolean firmadaDigitalmente) {
		this.firmadaDigitalmente = firmadaDigitalmente;
	}
	
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	public String getSelloDigital() {
		return selloDigital;
	}

	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}

	public String getSecuenciaDeNotaria() {
		return secuenciaDeNotaria;
	}

	public void setSecuenciaDeNotaria(String secuenciaDeNotaria) {
		this.secuenciaDeNotaria = secuenciaDeNotaria;
	}

	public String getNumeroSerieCertificado() {
		return numeroSerieCertificado;
	}

	public void setNumeroSerieCertificado(String numeroSerieCertificado) {
		this.numeroSerieCertificado = numeroSerieCertificado;
	}

	public FirmaElectronica getFirmaElectronica() {
		return firmaElectronica;
	}

	public void setFirmaElectronica(FirmaElectronica firmaElectronica) {
		this.firmaElectronica = firmaElectronica;
	}

	public TramiteTO[] getTramitesAux() {
		return tramitesAux;
	}

	public void setTramitesAux(TramiteTO[] tramitesAux) {
		this.tramitesAux = tramitesAux != null ? tramitesAux.clone() : null;
		this.tramites = Arrays.asList(tramitesAux);
	}
}