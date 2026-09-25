package mx.gob.imss.ctirss.correccion.firma.service.ejb.impl;

import java.io.Serializable;

public class PeticionFirmadoSimple implements Serializable {
    /**
    *
    */
   private static final long serialVersionUID = 2623130180415405301L;
   private String tramite;
   private String rfc;
   private String aplicacion;
   private String id_llavefirma;
   private String cadenaoriginal;
  
   public String getTramite() {
           return tramite;
   }
   public void setTramite(String tramite) {
           this.tramite = tramite;
   }
   public String getRfc() {
           return rfc;
   }
   public void setRfc(String rfc) {
           this.rfc = rfc;
   }
   public String getAplicacion() {
           return aplicacion;
   }
   public void setAplicacion(String aplicacion) {
           this.aplicacion = aplicacion;
   }
   public String getId_llavefirma() {
           return id_llavefirma;
   }
   public void setId_llavefirma(String id_llavefirma) {
           this.id_llavefirma = id_llavefirma;
   }
   public String getCadenaoriginal() {
           return cadenaoriginal;
   }
   public void setCadenaoriginal(String cadenaoriginal) {
           this.cadenaoriginal = cadenaoriginal;
   }
  
  

} 
