/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.common.vo;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 *
 * Modelo para filtros de búsquedas comunes
 */
public class Filter extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String folio;
    private String origen;
    private String responsable;
    private String autorizo;
    private String estado;
    private String curp;
    
    public String getFolio() {
        return folio;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getAutorizo() {
        return autorizo;
    }

    public void setAutorizo(String autorizo) {
        this.autorizo = autorizo;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Filter [folio=" + folio + ", origen=" + origen
                + ", responsable=" + responsable + ", autorizo=" + autorizo
                + ", estado=" + estado + ", curp=" + curp + "]";
    }

}
