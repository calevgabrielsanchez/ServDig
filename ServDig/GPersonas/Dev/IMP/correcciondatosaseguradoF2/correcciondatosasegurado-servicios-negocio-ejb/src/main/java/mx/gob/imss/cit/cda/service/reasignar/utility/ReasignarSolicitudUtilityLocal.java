package mx.gob.imss.cit.cda.service.reasignar.utility;

import javax.ejb.Local;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Local
public interface ReasignarSolicitudUtilityLocal {

    MensajeTarea crearMensajeTarea(Solicitud solicitud, String usuario,
            TramiteCorreccionCurp tramiteCda, String observacion);
}
