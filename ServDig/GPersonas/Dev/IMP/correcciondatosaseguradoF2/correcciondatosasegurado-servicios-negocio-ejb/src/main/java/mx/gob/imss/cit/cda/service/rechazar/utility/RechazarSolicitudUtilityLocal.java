package mx.gob.imss.cit.cda.service.rechazar.utility;

import javax.ejb.Local;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Local
public interface RechazarSolicitudUtilityLocal {
    
    MensajeTarea crearMensajeTarea(Solicitud solicitud);

}
