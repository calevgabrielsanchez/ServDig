package mx.gob.imss.cit.cda.web.app.responsable.model;

import java.util.List;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;

public class NSSAdicional  extends BaseModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4410482686993506362L;
    
    private String idSolicitud;
    private String folio;
    private String idTarea;
    private String idTramite;
    private String nss;
    private String observacion;
    private String origen;
    private List<DocumentoProbatorio> documentosProbatorios;
    private List<DocumentoProbatorio> documentosProbatoriosEliminados;
    
    public String getIdSolicitud() {
        return idSolicitud;
    }
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    public String getFolio() {
        return folio;
    }
    public void setFolio(String folio) {
        this.folio = folio;
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
    public String getNss() {
        return nss;
    }
    public void setNss(String nss) {
        this.nss = nss;
    }
    public String getObservacion() {
        return observacion;
    }
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    public List<DocumentoProbatorio> getDocumentosProbatorios() {
        return documentosProbatorios;
    }
    public void setDocumentosProbatorios(
            List<DocumentoProbatorio> documentosProbatorios) {
        this.documentosProbatorios = documentosProbatorios;
    }
    public List<DocumentoProbatorio> getDocumentosProbatoriosEliminados() {
        return documentosProbatoriosEliminados;
    }
    public void setDocumentosProbatoriosEliminados(
            List<DocumentoProbatorio> documentosProbatoriosEliminados) {
        this.documentosProbatoriosEliminados = documentosProbatoriosEliminados;
    }
    public String getOrigen() {
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }

}
