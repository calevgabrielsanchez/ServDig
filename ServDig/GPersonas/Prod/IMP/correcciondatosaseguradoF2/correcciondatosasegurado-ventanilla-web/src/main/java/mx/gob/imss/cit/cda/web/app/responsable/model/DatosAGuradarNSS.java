package mx.gob.imss.cit.cda.web.app.responsable.model;

import java.util.List;

public class DatosAGuradarNSS {

    private String idTramitePrincipal;
    private String tipoNSS;
    private List<String> tipoCorreccion;
    private String TipoRegularizacion;
    private String nssCorreccion;
    
    public String getIdTramitePrincipal() {
        return idTramitePrincipal;
    }
    public void setIdTramitePrincipal(String idTramitePrincipal) {
        this.idTramitePrincipal = idTramitePrincipal;
    }
    public String getTipoNSS() {
        return tipoNSS;
    }
    public void setTipoNSS(String tipoNSS) {
        this.tipoNSS = tipoNSS;
    }
    public List<String> getTipoCorreccion() {
        return tipoCorreccion;
    }
    public void setTipoCorreccion(List<String> tipoCorreccion) {
        this.tipoCorreccion = tipoCorreccion;
    }
    public String getTipoRegularizacion() {
        return TipoRegularizacion;
    }
    public void setTipoRegularizacion(String tipoRegularizacion) {
        TipoRegularizacion = tipoRegularizacion;
    }
    public String getNssCorreccion() {
        return nssCorreccion;
    }
    public void setNssCorreccion(String nssCorreccion) {
        this.nssCorreccion = nssCorreccion;
    }

   
}
