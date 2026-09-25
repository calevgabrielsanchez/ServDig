/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;
import java.util.List;


public class DetalleNssCda extends BaseModel{
    private static final long serialVersionUID = -8063619841578554604L;
    private Long claveDetalleNssCda;
    private String nss;
    private List <Documento> documentosProbatorios;
    private Long origen; 
    private Long idTipoNss;
    private String observaciones;

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    

    public Long getClaveDetalleNssCda() {
        return claveDetalleNssCda;
    }

    public void setClaveDetalleNssCda(Long claveDetalleNssCda) {
        this.claveDetalleNssCda = claveDetalleNssCda;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public List<Documento> getDocumentosProbatorios() {
        return documentosProbatorios;
    }

    public void setDocumentosProbatorios(List<Documento> documentosProbatorios) {
        this.documentosProbatorios = documentosProbatorios;
    }

    public Long getOrigen() {
        return origen;
    }

    public void setOrigen(Long origen) {
        this.origen = origen;
    }

    public Long getIdTipoNss() {
        return idTipoNss;
    }

    public void setIdTipoNss(Long idTipoNss) {
        this.idTipoNss = idTipoNss;
    }
        
        

}
