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
public class ReasignacionSolicitud extends SolicitudBase {

    private static final long serialVersionUID = -7939352436183271747L;
    
    private String curp;
    private String detalle;
    private String nss;
    private String correoElectronico;
    private String nombreCompleto;
    private String responsable;

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

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    @Override
    public String toString() {
        return "ReasignacionSolicitud [curp="
                + curp + ", detalle=" + detalle  + ", nss=" + nss
                + ", correoElectronico=" + correoElectronico
                + ", nombreCompleto=" + nombreCompleto + ", responsable="
                + responsable + "]";
    }

}
