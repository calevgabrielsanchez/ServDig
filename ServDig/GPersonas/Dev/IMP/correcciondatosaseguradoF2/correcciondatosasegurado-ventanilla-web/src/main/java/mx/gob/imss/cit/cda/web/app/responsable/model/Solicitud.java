/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.cit.cda.web.common.vo.SolicitudBase;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author antonio
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Solicitud extends SolicitudBase {

    private static final long serialVersionUID = -8764357222776155605L;
    private InformacionBeneficiario informacionBeneficiario;
    private DomicilioParticular domicilioParticular;
    private MotivoAclaracion motivoAclaracion;
    private TipoRegularizacion tipoRegularizacion;
    private String observacion;
    private String estatus;
    private String responsable;
    private boolean defuncion;
    private Page<HistoriaLaboral> gridHistoriaLaboral;
    private Page<NSS> gridNSS;// pediente de quitar
    private ArrayList <String> definicion;
    private String cveCorreccionDatos;
    private boolean bloqueoCuentaIlogia;
    private boolean bloqueoCorrespondeOtroAsegurado;

    public String getCveCorreccionDatos() {
		return cveCorreccionDatos;
	}

	public void setCveCorreccionDatos(String cveCorreccionDatos) {
		this.cveCorreccionDatos = cveCorreccionDatos;
	}

	public ArrayList<String> getDefinicion() {
		return definicion;
	}

	public void setDefinicion(ArrayList<String> definicion) {
		this.definicion = definicion;
	}

	/**
     * Lista de Nss
     */
    private List<Page<NSS>> gridsNSS;

    /**
     * Lista documentos de cada Nss
     */
    private List<Page<Documento>> gridsDocumentosNss;

    /**
     * Documentos asegurado en registro
     */
    private Page<Documento> gridDocumentos;

    /**
     * Documentos asegurado en registro
     */
    private Page<Documento> gridDocumentosAdicionales;

    /**
     * Documentos nss solicitar info
     */
    private Page<DocumentosNss> gridDocumentosNssAdicionales;

    /**
     * Documentos de cada nss y quien los agrego
     */
    private List<Page<DocumentosNss>> gridsDocumentosNssOrigen;

    /**
     * Documentos del beneficiario/representante legal
     */
    private Page<Documento> gridDocumentosBeneficiario;
    /**
     * Documentos del beneficiario solicitar info
     */
    private Page<Documento> gridDocsBeneficiarioAdicionales;
    /**
     * Documentos del representante legal
     */
    private Page<Documento> gridDocumentosRepLegal;
    private Page<Documento> gridDocumentosRepLegalAdicionales;

    /**
     * Documentos del beneficiario/representante legal solicitar info
     */
    private Page<DocumentosBeneficiario> gridDocumentosBeneficiarioAdicionales;

    private Page<DetalleNssCda> gridNssSolicitud;

    private Page<DetalleNssCda> gridNssVentanilla;

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

    /*
     * Estado de la pantalla 
     */
    private String idEstadoSolicitud;

    /**
     * @return the domicilioParticular
     */
    public DomicilioParticular getDomicilioParticular() {
        return domicilioParticular;
    }

    /**
     * @param domicilioParticular the domicilioParticular to set
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
     * @param motivoAclaracion the motivoAclaracion to set
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
     * @param gridNSS the gridNSS to set
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
     * @param tipoRegularizacion the tipoRegularizacion to set
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

    public Page<DocumentosNss> getGridDocumentosNssAdicionales() {
        return gridDocumentosNssAdicionales;
    }

    public void setGridDocumentosNssAdicionales(
            Page<DocumentosNss> gridDocumentosNssAdicionales) {
        this.gridDocumentosNssAdicionales = gridDocumentosNssAdicionales;
    }

    public boolean isDefuncion() {
        return defuncion;
    }

    public void setDefuncion(boolean defuncion) {
        this.defuncion = defuncion;
    }

    public InformacionBeneficiario getInformacionBeneficiario() {
        return informacionBeneficiario;
    }

    public void setInformacionBeneficiario(
            InformacionBeneficiario informacionBeneficiario) {
        this.informacionBeneficiario = informacionBeneficiario;
    }

    public Page<DocumentosBeneficiario> getGridDocumentosBeneficiarioAdicionales() {
        return gridDocumentosBeneficiarioAdicionales;
    }

    public void setGridDocumentosBeneficiarioAdicionales(
            Page<DocumentosBeneficiario> gridDocumentosBeneficiarioAdicionales) {
        this.gridDocumentosBeneficiarioAdicionales = gridDocumentosBeneficiarioAdicionales;
    }

    public Page<Documento> getGridDocumentosAdicionales() {
        return gridDocumentosAdicionales;
    }

    public void setGridDocumentosAdicionales(
            Page<Documento> gridDocumentosAdicionales) {
        this.gridDocumentosAdicionales = gridDocumentosAdicionales;
    }

    public String getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(String idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public Page<DetalleNssCda> getGridNssSolicitud() {
        return gridNssSolicitud;
    }

    public void setGridNssSolicitud(Page<DetalleNssCda> gridNssSolicitud) {
        this.gridNssSolicitud = gridNssSolicitud;
    }

    public Page<DetalleNssCda> getGridNssVentanilla() {
        return gridNssVentanilla;
    }

    public void setGridNssVentanilla(Page<DetalleNssCda> gridNssVentanilla) {
        this.gridNssVentanilla = gridNssVentanilla;
    }

    public Page<Documento> getGridDocumentosRepLegal() {
        return gridDocumentosRepLegal;
    }

    public void setGridDocumentosRepLegal(Page<Documento> gridDocumentosRepLegal) {
        this.gridDocumentosRepLegal = gridDocumentosRepLegal;
    }

    public Page<Documento> getGridDocumentosRepLegalAdicionales() {
        return gridDocumentosRepLegalAdicionales;
    }

    public void setGridDocumentosRepLegalAdicionales(Page<Documento> gridDocumentosRepLegalAdicionales) {
        this.gridDocumentosRepLegalAdicionales = gridDocumentosRepLegalAdicionales;
    }

    public Page<Documento> getGridDocsBeneficiarioAdicionales() {
        return gridDocsBeneficiarioAdicionales;
    }

    public void setGridDocsBeneficiarioAdicionales(Page<Documento> gridDocsBeneficiarioAdicionales) {
        this.gridDocsBeneficiarioAdicionales = gridDocsBeneficiarioAdicionales;
    }

	public boolean isBloqueoCuentaIlogia() {
		return bloqueoCuentaIlogia;
	}

	public void setBloqueoCuentaIlogia(boolean bloqueoCuentaIlogia) {
		this.bloqueoCuentaIlogia = bloqueoCuentaIlogia;
	}

	public boolean isBloqueoCorrespondeOtroAsegurado() {
		return bloqueoCorrespondeOtroAsegurado;
	}

	public void setBloqueoCorrespondeOtroAsegurado(
			boolean bloqueoCorrespondeOtroAsegurado) {
		this.bloqueoCorrespondeOtroAsegurado = bloqueoCorrespondeOtroAsegurado;
	}

}
