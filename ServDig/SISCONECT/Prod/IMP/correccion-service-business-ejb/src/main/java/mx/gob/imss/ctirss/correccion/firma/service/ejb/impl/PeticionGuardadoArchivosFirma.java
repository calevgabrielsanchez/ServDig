package mx.gob.imss.ctirss.correccion.firma.service.ejb.impl;

import java.util.List;

import mx.gob.imss.ctirss.correccion.firma.service.interfaces.Archivo;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public class PeticionGuardadoArchivosFirma extends AbstractModel {
	  /**
    *
    */
   private static final long serialVersionUID = -6732440060629107062L;
   String tramite;
   String rfc;
   String aplicacion;
   List<Archivo> archivos;
  
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

   public String getTramite() {
           return tramite;
   }
  
   public void setTramite(String tramite) {
           this.tramite = tramite;
   }

	public List<Archivo> getArchivos() {
		return archivos;
	}
	
	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}
  

}
