package mx.gob.imss.ctirss.sso.admonusuarios.selloDigital;

import java.io.Serializable;

public class RespuestaFirmadoSimple implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8887365396308491089L;
    private String id;
    private String tramite;
    private String noSerie;
    private String sello;
   
    public String getId() {
            return id;
    }
    public void setId(String id) {
            this.id = id;
    }
   
    public String getTramite() {
            return tramite;
    }
    public void setTramite(String tramite) {
            this.tramite = tramite;
    }
    public String getNoSerie() {
            return noSerie;
    }
    public void setNoSerie(String noSerie) {
            this.noSerie = noSerie;
    }
    public String getSello() {
            return sello;
    }
    public void setSello(String sello) {
            this.sello = sello;
    }
   
    
}
