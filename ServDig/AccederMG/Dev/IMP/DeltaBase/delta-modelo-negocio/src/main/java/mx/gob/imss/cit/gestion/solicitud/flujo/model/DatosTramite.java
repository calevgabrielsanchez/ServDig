/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.gestion.solicitud.flujo.model;

import java.io.Serializable;

/**
 * Bean para los datos de tramites
 *
 * @author softtek
 *
 */
public class DatosTramite implements Serializable {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 5776966850392077028L;

    private String folio;

    private String nss;

    private String tipoTramite;

    private String fechaSolicitud;

    private String subdelegacion;
	
	private String estadoTramite;
	
	public String getEstadoTramite() {
        return estadoTramite;
    }

    public void setEstadoTramite(String estadoTramite) {
        this.estadoTramite = estadoTramite;
    }
	

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getSubdelegacion() {
        return subdelegacion;
    }

    public void setSubdelegacion(String subdelegacion) {
        this.subdelegacion = subdelegacion;
    }

}
