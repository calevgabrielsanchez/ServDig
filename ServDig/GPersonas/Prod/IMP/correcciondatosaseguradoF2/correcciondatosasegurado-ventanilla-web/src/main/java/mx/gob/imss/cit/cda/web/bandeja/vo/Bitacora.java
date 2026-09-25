/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.bandeja.vo;

import mx.gob.imss.cit.cda.web.app.responsable.model.Estatus;
import mx.gob.imss.cit.cda.web.common.vo.SolicitudBase;
import mx.gob.imss.cit.cda.web.support.model.Page;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author yisus
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bitacora extends SolicitudBase {

    private static final long serialVersionUID = 1L;

    private String origen;
    private Page<Estatus> gridEstatus;

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Page<Estatus> getGridEstatus() {
        return gridEstatus;
    }

    public void setGridEstatus(Page<Estatus> gridEstatus) {
        this.gridEstatus = gridEstatus;
    }
}
