package mx.gob.imss.cit.cda.web.bandeja.vo;

import mx.gob.imss.cit.cda.web.common.vo.Filter;

public class FilterBandeja extends Filter{

    /**
     * Filtros para las bandejas de Responsable y Autorizador
     */
    private static final long serialVersionUID = 1L;
    
    private String nss;
    private String fechaSolicitud;
    private String tramite;
    private String fechaActualizacion;
    private Boolean foliosAsociados;
    private Boolean foliosVencidos;
    private String fecha;
    
    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Boolean getFoliosVencidos() {
        return foliosVencidos;
    }

    public void setFoliosVencidos(Boolean foliosVencidos) {
        this.foliosVencidos = foliosVencidos;
    }
    
    public Boolean getFoliosAsociados() {
        return foliosAsociados;
    }

    public void setFoliosAsociados(Boolean foliosAsociados) {
        this.foliosAsociados = foliosAsociados;
    }
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    @Override
    public String toString() {
        return "FilterBandeja [nss=" + nss + ", fechaSolicitud="
                + fechaSolicitud + ", tramite=" + tramite
                + ", fechaActualizacion=" + fechaActualizacion
                + ", foliosAsociados=" + foliosAsociados + ", foliosVencidos="
                + foliosVencidos + ", fecha=" + fecha + "]";
    }

}
