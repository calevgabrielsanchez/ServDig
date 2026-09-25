/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.autorizar.vo;

import mx.gob.imss.cit.cda.web.common.vo.SolicitudBase;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorizarSolicitud extends SolicitudBase {

    private static final long serialVersionUID = -7939352436183271747L;
    private String detalle;
    private String nombreCompletoAutorizador;
    private String nss;
    private String error;

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

    public String getNombreCompletoAutorizador() {
        return nombreCompletoAutorizador;
    }

    public void setNombreCompletoAutorizador(String nombreCompletoAutorizador) {
        this.nombreCompletoAutorizador = nombreCompletoAutorizador;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
