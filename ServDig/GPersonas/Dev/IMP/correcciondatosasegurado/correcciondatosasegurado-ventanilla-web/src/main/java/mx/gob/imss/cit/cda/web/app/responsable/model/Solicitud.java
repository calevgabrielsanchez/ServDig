/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import java.util.List;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;
import mx.gob.imss.cit.cda.web.support.model.Page;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author antonio
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Solicitud extends BaseModel {
	
	private static final long serialVersionUID = -8764357222776155605L;
	private String id;
	private String folio;
	private InformacionRENAPO informacionRENAPO;
	private DomicilioParticular domicilioParticular;
	private MotivoAclaracion motivoAclaracion;
	private Page<NSS> gridNSS;
	private List<Page<NSS>> gridsNSS;
	private Page<Documento> gridDocumentos;
	private List<Page<Documento>> gridsDocumentosNss;
	private Page<DocumentosNss> gridDocumentosNssOrigen;
	private List<Page<DocumentosNss>> gridsDocumentosNssOrigen;
	private Page<Documento> gridDocumentosBeneficiario;
	private TipoRegularizacion tipoRegularizacion;
	private String observacion;
	private String idTarea;
	private String idTramite;	
	private SubDelegacion subDelegacion;
	private String fechaInicio;
	private String estatus;
	private String responsable;
	private String curpResponsable;
	private Page<HistoriaLaboral> gridHistoriaLaboral;
	
	/*
	 * Estado de la pantalla del autorizador
	 */
	private String estadoAutorizacion;
	private Boolean esPropietario;
	private String observacionSubdelegacion;
	
	/*
	 * Estado de la pantalla del responsable 
	 */
	private String estadoResponsable;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 *            the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the informacionRENAPO
	 */
	public InformacionRENAPO getInformacionRENAPO() {
		return informacionRENAPO;
	}

	/**
	 * @param informacionRENAPO
	 *            the informacionRENAPO to set
	 */
	public void setInformacionRENAPO(InformacionRENAPO informacionRENAPO) {
		this.informacionRENAPO = informacionRENAPO;
	}

	/**
	 * @return the domicilioParticular
	 */
	public DomicilioParticular getDomicilioParticular() {
		return domicilioParticular;
	}

	/**
	 * @param domicilioParticular
	 *            the domicilioParticular to set
	 */
	public void setDomicilioParticular(DomicilioParticular domicilioParticular) {
		this.domicilioParticular = domicilioParticular;
	}

	/**
	 * @return the motivoAclaracion
	 */
	public MotivoAclaracion getMotivoAclaracion() {
		return motivoAclaracion;
	}

	/**
	 * @param motivoAclaracion
	 *            the motivoAclaracion to set
	 */
	public void setMotivoAclaracion(MotivoAclaracion motivoAclaracion) {
		this.motivoAclaracion = motivoAclaracion;
	}

	/**
	 * @return the gridNSS
	 */
	public Page<NSS> getGridNSS() {
		return gridNSS;
	}

	/**
	 * @param gridNSS
	 *            the gridNSS to set
	 */
	public void setGridNSS(Page<NSS> gridNSS) {
		this.gridNSS = gridNSS;
	}

	/**
	 * @return the tipoRegularizacion
	 */
	public TipoRegularizacion getTipoRegularizacion() {
		return tipoRegularizacion;
	}

	/**
	 * @param tipoRegularizacion
	 *            the tipoRegularizacion to set
	 */
	public void setTipoRegularizacion(TipoRegularizacion tipoRegularizacion) {
		this.tipoRegularizacion = tipoRegularizacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(String idTarea) {
		this.idTarea = idTarea;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	public SubDelegacion getSubDelegacion() {
		return subDelegacion;
	}

	public void setSubDelegacion(SubDelegacion subDelegacion) {
		this.subDelegacion = subDelegacion;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Page<Documento> getGridDocumentos() {
		return gridDocumentos;
	}

	public void setGridDocumentos(Page<Documento> gridDocumentos) {
		this.gridDocumentos = gridDocumentos;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getCurpResponsable() {
		return curpResponsable;
	}

	public void setCurpResponsable(String curpResponsable) {
		this.curpResponsable = curpResponsable;
	}

	public String getEstadoAutorizacion() {
		return estadoAutorizacion;
	}

	public void setEstadoAutorizacion(String estadoAutorizacion) {
		this.estadoAutorizacion = estadoAutorizacion;
	}

	public Page<HistoriaLaboral> getGridHistoriaLaboral() {
		return gridHistoriaLaboral;
	}

	public void setGridHistoriaLaboral(Page<HistoriaLaboral> gridHistoriaLaboral) {
		this.gridHistoriaLaboral = gridHistoriaLaboral;
	}
	
	public String getObservacionSubdelegacion() {
		return observacionSubdelegacion;
	}

	public void setObservacionSubdelegacion(String observacionSubdelegacion) {
		this.observacionSubdelegacion = observacionSubdelegacion;
	}

	public Boolean getEsPropietario() {
		return esPropietario;
	}

	public void setEsPropietario(Boolean esPropietario) {
		this.esPropietario = esPropietario;
	}

	public String getEstadoResponsable() {
		return estadoResponsable;
	}

	public void setEstadoResponsable(String estadoResponsable) {
		this.estadoResponsable = estadoResponsable;
	}

	public Page<Documento> getGridDocumentosBeneficiario() {
		return gridDocumentosBeneficiario;
	}

	public void setGridDocumentosBeneficiario(
			Page<Documento> gridDocumentosBeneficiario) {
		this.gridDocumentosBeneficiario = gridDocumentosBeneficiario;
	}

	public List<Page<NSS>> getGridsNSS() {
		return gridsNSS;
	}

	public void setGridsNSS(List<Page<NSS>> gridsNSS) {
		this.gridsNSS = gridsNSS;
	}

	public List<Page<Documento>> getGridsDocumentosNss() {
		return gridsDocumentosNss;
	}

	public void setGridsDocumentosNss(List<Page<Documento>> gridsDocumentosNss) {
		this.gridsDocumentosNss = gridsDocumentosNss;
	}
	
	public List<Page<DocumentosNss>> getGridsDocumentosNssOrigen() {
		return gridsDocumentosNssOrigen;
	}

	public void setGridsDocumentosNssOrigen(
			List<Page<DocumentosNss>> gridsDocumentosNssOrigen) {
		this.gridsDocumentosNssOrigen = gridsDocumentosNssOrigen;
	}
	
	public Page<DocumentosNss> getGridDocumentosNssOrigen() {
		return gridDocumentosNssOrigen;
	}

	public void setGridDocumentosNssOrigen(
			Page<DocumentosNss> gridDocumentosNssOrigen) {
		this.gridDocumentosNssOrigen = gridDocumentosNssOrigen;
	}

		
}
