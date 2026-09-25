package mx.gob.imss.cit.cda.service.reasignar.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Stateless(name = "reasignarSolicitudUtility", mappedName = "reasignarSolicitudUtility")
public class ReasignarSolicitudUtility implements
        ReasignarSolicitudUtilityLocal {

    @EJB
    private CorreccionDatosAseguradoUtilityLocal correccionAseguradoUtilityLocal;

    private static final String TRAMITE_REASIGNADO = "REASIGNADA";

    @Override
    public MensajeTarea crearMensajeTarea(Solicitud solicitud, String usuario,
            TramiteCorreccionCurp tramiteCda, String observacion) {
        MensajeTarea mensajeTarea = new MensajeTarea();
        mensajeTarea.setFechaActualizacion(getCorreccionAseguradoUtilityLocal()
                .convertDateToString(new Date()));
        mensajeTarea.setUsuario(usuario);
        mensajeTarea.setEstado(TRAMITE_REASIGNADO);
        mensajeTarea.setObservacion(observacion);
        Map<String, String> participantes = new HashMap<String, String>();
        participantes.put(ParticipantesEnum.RESPONSABLE.getDescripcion(),
                usuario);
        mensajeTarea.setParticipantes(participantes);
        return mensajeTarea;
    }

    public CorreccionDatosAseguradoUtilityLocal getCorreccionAseguradoUtilityLocal() {
        return correccionAseguradoUtilityLocal;
    }
}
