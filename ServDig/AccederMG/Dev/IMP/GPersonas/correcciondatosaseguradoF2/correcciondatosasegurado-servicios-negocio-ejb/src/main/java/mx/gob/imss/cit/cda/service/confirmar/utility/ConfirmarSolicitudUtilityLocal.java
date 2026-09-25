package mx.gob.imss.cit.cda.service.confirmar.utility;

import javax.ejb.Local;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Local
public interface ConfirmarSolicitudUtilityLocal {
    
    MensajeTarea crearMensajeTarea(TramiteCorreccionCurp tramiteCda);

}
