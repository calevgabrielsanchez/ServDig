package mx.gob.imss.ctirss.delta.gestion.patronal.web.beans;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSocios;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

public class SolicitudAltaPatronalDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TramiteSujetoObligado tramiteSujetoObligado;
	private TramiteRepresentanteLegal tramiteRepresentanteLegal;
	private TramiteSocios tramiteSocios;
	private Tramite tramite;
	private FirmaElectronica firmaEmpresa;
	private FirmaElectronica firmaRepresentante;
	private Solicitud solicitud;
	private SujetoObligado sujetoObligado;
	private Fisica busquedaPersona;
	
	
	public SolicitudAltaPatronalDTO() {
		tramiteSujetoObligado = new TramiteSujetoObligado();
		tramiteSujetoObligado.setSujetoObligado(new SujetoObligado());
        tramiteSujetoObligado.getSujetoObligado().setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		tramiteRepresentanteLegal = new TramiteRepresentanteLegal();
		tramiteSocios = new TramiteSocios();
		firmaEmpresa = new FirmaElectronica();
		firmaRepresentante = new FirmaElectronica();
		tramiteRepresentanteLegal.setSujetoObligado(new SujetoObligado());
		solicitud = new  Solicitud();
		sujetoObligado = new SujetoObligado();
		tramite = new  Tramite();
		busquedaPersona = new Fisica();
	}
	
	public TramiteSujetoObligado getTramiteSujetoObligado() {
		return tramiteSujetoObligado;
	}
	
	public void setTramiteSujetoObligado(TramiteSujetoObligado tramiteSujetoObligado) {
		this.tramiteSujetoObligado = tramiteSujetoObligado;
	}
	
	public TramiteRepresentanteLegal getTramiteRepresentanteLegal() {
		return tramiteRepresentanteLegal;
	}
	
	public void setTramiteRepresentanteLegal(
			TramiteRepresentanteLegal tramiteRepresentanteLegal) {
		this.tramiteRepresentanteLegal = tramiteRepresentanteLegal;
	}
	
	public TramiteSocios getTramiteSocios() {
		return tramiteSocios;
	}
	
	public void setTramiteSocios(TramiteSocios tramiteSocios) {
		this.tramiteSocios = tramiteSocios;
	}

	public FirmaElectronica getFirmaEmpresa() {
		return firmaEmpresa;
	}

	public void setFirmaEmpresa(FirmaElectronica firmaEmpresa) {
		this.firmaEmpresa = firmaEmpresa;
	}

	public FirmaElectronica getFirmaRepresentante() {
		return firmaRepresentante;
	}

	public void setFirmaRepresentante(FirmaElectronica firmaRepresentante) {
		this.firmaRepresentante = firmaRepresentante;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public SujetoObligado getSujetoObligado() {
		return sujetoObligado;
	}

	public void setSujetoObligado(SujetoObligado sujetoObligado) {
		this.sujetoObligado = sujetoObligado;
	}

	public Tramite getTramite() {
		return tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

	public Fisica getBusquedaPersona() {
		return busquedaPersona;
	}

	public void setBusquedaPersona(Fisica busquedaPersona) {
		this.busquedaPersona = busquedaPersona;
	}
	
}