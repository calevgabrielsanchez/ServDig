package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;

public class DocumentoProbatorio extends Documento implements Serializable {

    private static final long serialVersionUID = -1753374178261138473L;

    private String nombre;
    private Integer tipoDocumento;
    private long idDocumentoPorTipo;
    private String tipoPer;
    private String idDocBoveda;
    private String noFolioSolicitud;
    private Long solicitudId;

    public long getIdDocumentoPorTipo() {
        return idDocumentoPorTipo;
    }

    public void setIdDocumentoPorTipo(long idDocumentoPorTipo) {
        this.idDocumentoPorTipo = idDocumentoPorTipo;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "DocumentoProbatorio [claveTipoDocumento=" + cveIdDocumento
                + ", desDocumento=" + desDocumento + ", nombre=" + nombre
                + ", idDocumentoPorTipo=" + idDocumentoPorTipo + ", persona="
                + tipoPer + "]";
    }

    public String getTipoPer() {
        return tipoPer;
    }

    public void setTipoPer(String tipoPer) {
        this.tipoPer = tipoPer;
    }

    public void setIdDocBoveda(String idDocBoveda) {
        this.idDocBoveda = idDocBoveda;
    }

    public String getIdDocBoveda() {
        return idDocBoveda;
    }

    public String getNoFolioSolicitud() {
        return noFolioSolicitud;
    }

    public void setNoFolioSolicitud(String noFolioSolicitud) {
        this.noFolioSolicitud = noFolioSolicitud;
    }

    public Long getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Long solicitudId) {
        this.solicitudId = solicitudId;
    }
    
}
