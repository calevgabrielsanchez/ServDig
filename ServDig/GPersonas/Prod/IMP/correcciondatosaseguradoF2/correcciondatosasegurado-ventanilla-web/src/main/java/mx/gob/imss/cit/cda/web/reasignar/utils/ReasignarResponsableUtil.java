package mx.gob.imss.cit.cda.web.reasignar.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.web.app.responsable.model.ReasignacionSolicitud;
import mx.gob.imss.cit.cda.web.bandeja.vo.TareaTramite;
import mx.gob.imss.cit.cda.web.common.utils.SolicitudUtils;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoCDARemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReasignarResponsableUtil extends SolicitudUtils {

	@Autowired
    private FlujoTrabajoCDARemote flujoTrabajoCDA;

    /**
     * Metodo para crear la solicitud y reasignarla
     *
     * @param solicitud
     * @param requestUpdateEvent
     * @return solicitud
     */
    public Solicitud crearSolicitudReasignar(Solicitud solicitud,
            UpdateEvent<ReasignacionSolicitud> requestUpdateEvent) {

        getLog().debug("---CDA--- Responsable curp {}", requestUpdateEvent.getData()
                .getCurp());
        Usuario usuario = new Usuario();
        usuario.setCveIdUsuario(requestUpdateEvent.getData().getCurp());
        usuario.setUsuario(requestUpdateEvent.getData().getCurp());

        solicitud.setSolicitante(usuario);
        solicitud.setSolicitudId(Long.parseLong(requestUpdateEvent.getData()
                .getIdSolicitud()));

        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud
                .setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
                        .getCodigo());
        solicitud.setEstadoSolicitud(estadoSolicitud);

        solicitud.setTramites(crearTramitesReasignar(solicitud.getTramites(),
                requestUpdateEvent));

        return solicitud;
    }

    /**
     * Metodo para asignar el estado reasignado a los tramites
     */
    private List<Tramite> crearTramitesReasignar(List<Tramite> tramites,
            UpdateEvent<ReasignacionSolicitud> requestUpdateEvent) {
        List<Tramite> lstTramite = new ArrayList<Tramite>();

        EstadoTramite estadoTramite = new EstadoTramite();
        estadoTramite.setDescripcion(EstadoNegocioEnum.REASIGNADA
                .getDescripcion());
        estadoTramite
                .setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_TRAMITADOR
                        .getCodigo());

        for (Tramite tramitecda : tramites) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;
            tramite.setEstadoTramite(estadoTramite);
            tramite.setDetalleReasignacion(requestUpdateEvent.getData()
                    .getDetalle());

            if (tramite.getObservacionesSubdelegacion() == null) {
                tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
            }
            tramite.getObservacionesSubdelegacion().add(
                    crearObservacionTramiteReasignar(requestUpdateEvent));
            getLog().debug("---CDA--- Id Solicitud {}  Id Tramite {} ",
                    requestUpdateEvent.getData().getIdSolicitud(),
                    tramite.getTramiteId());
            lstTramite.add(tramite);
        }

        return lstTramite;

    }

    /**
     * Metodo para crear la observacion de cada tramite para reasignar la
     * solicitud
     */
    private ObservacionesSubdelegacion crearObservacionTramiteReasignar(
            UpdateEvent<ReasignacionSolicitud> requestUpdateEvent) {
        ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion();
        obSubdelegacion.setDetalle(requestUpdateEvent.getData().getDetalle());
        obSubdelegacion.setFechaActualizacion(new Date());
        obSubdelegacion.setUsuario(requestUpdateEvent.getUserProfile()
                .getUsuario());
        obSubdelegacion.setAsignado(requestUpdateEvent.getData().getCurp());
        obSubdelegacion.setCveEstado(EstadoNegocioEnum.REASIGNADA.getCodigo());
        return obSubdelegacion;
    }

    /**
     * Metodo para armar objeto mapeado tramite con su tarea que le corresponde - en desuso
     */
    public Map<String, String> armarTareasTramites(
            UpdateEvent<ReasignacionSolicitud> requestUpdateEvent) {
        Map<String, String> tareasTramitesMap = new HashMap<String, String>();
        for (TareaTramite tareaTramite : requestUpdateEvent.getData()
                .getTareasTramites()) {
            tareasTramitesMap.put(tareaTramite.getIdTramite(),
                    tareaTramite.getIdTarea());
        }
        return tareasTramitesMap;
    }
}
