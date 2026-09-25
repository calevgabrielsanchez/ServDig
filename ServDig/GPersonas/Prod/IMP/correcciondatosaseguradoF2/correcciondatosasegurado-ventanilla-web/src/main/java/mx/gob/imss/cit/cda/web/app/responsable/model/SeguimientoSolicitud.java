/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import mx.gob.imss.cit.cda.web.common.vo.SolicitudBase;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author antonio
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeguimientoSolicitud extends SolicitudBase {

    private static final long serialVersionUID = 714522471822230928L;
    private String resumen;
    private String detalle;
    private String responsable;
    private String nss;
    private String correoAsegurado;
    private Boolean isAutorizador;

    /**
     * @return the resumen
     */
    public String getResumen() {
        return resumen;
    }

    /**
     * @param resumen
     *            the resumen to set
     */
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle
     *            the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getCorreoAsegurado() {
        return correoAsegurado;
    }

    public void setCorreoAsegurado(String correoAsegurado) {
        this.correoAsegurado = correoAsegurado;
    }

    public Boolean getIsAutorizador() {
        return isAutorizador;
    }

    public void setIsAutorizador(Boolean isAutorizador) {
        this.isAutorizador = isAutorizador;
    }

    @Override
    public String toString() {
        return "SeguimientoSolicitud [resumen=" + resumen + ", detalle=" + detalle
                + ", responsable=" + responsable 
                + ", nss=" + nss + ", correoAsegurado=" + correoAsegurado
                + ", isAutorizador=" + isAutorizador + "]";
    }
    
}
