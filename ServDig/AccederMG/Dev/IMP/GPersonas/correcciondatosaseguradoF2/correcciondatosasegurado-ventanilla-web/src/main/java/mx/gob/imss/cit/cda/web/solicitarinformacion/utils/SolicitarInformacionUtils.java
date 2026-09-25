package mx.gob.imss.cit.cda.web.solicitarinformacion.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.bandeja.vo.TareaTramite;
import mx.gob.imss.cit.cda.web.common.utils.SolicitudUtils;
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

import org.springframework.stereotype.Component;

@Component
public class SolicitarInformacionUtils  extends SolicitudUtils {    
    
    /**
     * Metodo para crear la solicitud en solicitar datos adicionales
     * 
     * @param solicitud
     * @param requestUpdateEvent
     * @return solicitud
     */
    public Solicitud crearSolicitarInformacion(Solicitud solicitud,UpdateEvent<SeguimientoSolicitud> requestUpdateEvent) {
        Usuario usuarioSolicitante = new Usuario();
        usuarioSolicitante.setCveIdUsuario(requestUpdateEvent.getData().getCurpResponsable());
        usuarioSolicitante.setUsuario(requestUpdateEvent.getData() .getCurpResponsable());
        solicitud.setSolicitante(usuarioSolicitante);
        
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
        solicitud.setEstadoSolicitud(estadoSolicitud);
        solicitud.setTramites(crearTramitesSolicitarInformacion (solicitud.getTramites(), requestUpdateEvent));

        return solicitud;
    }
    
    
    /**
     * Metodo para asignar el estado en espera de informacion a los tramites
     */
    private List<Tramite> crearTramitesSolicitarInformacion (List<Tramite> tramites, UpdateEvent<SeguimientoSolicitud> requestUpdateEvent){
        List<Tramite> lstTramite = new ArrayList<Tramite>();
        
        for (Tramite tramitecda : tramites) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;
            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo()));
            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo());
            tramite.setEstadoTramite(estadoTramite);
            if (tramite.getObservacionesSubdelegacion() == null) {
                tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
            }
            tramite.getObservacionesSubdelegacion().add(crearObservacionTramiteSolicitarInfo (requestUpdateEvent, estadoTramite));
            lstTramite.add(tramite);
        }
        
        return lstTramite;
        
    }
    
    /**
     * Metodo para crear la observacion de cada metodo para la solicitud de información
     */
    private ObservacionesSubdelegacion crearObservacionTramiteSolicitarInfo (UpdateEvent<SeguimientoSolicitud> requestUpdateEvent, EstadoTramite estadoTramite){
        ObservacionesSubdelegacion observacionesSubdelegacion = new ObservacionesSubdelegacion();
        observacionesSubdelegacion.setDetalle(requestUpdateEvent.getData().getDetalle());
        observacionesSubdelegacion.setResumen(requestUpdateEvent.getData().getResumen());
        observacionesSubdelegacion.setIdTarea(requestUpdateEvent.getData().getIdTarea());
        observacionesSubdelegacion.setFechaActualizacion(new Date());
        observacionesSubdelegacion.setUsuario(requestUpdateEvent .getUserProfile().getUsuario());
        observacionesSubdelegacion.setAsignado(requestUpdateEvent.getData().getCurpResponsable());
        observacionesSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
        return observacionesSubdelegacion;
    }
    
    /**
     * Metodo para armar objeto mapeado tramite con su tarea que le corresponde 
     */
    public Map<String, String> armarTareasTramites(List<TareaTramite> tareasTramites) {
        Map<String, String> tareasTramitesMap = new HashMap<String, String>();
        for (TareaTramite tareaTramite : tareasTramites) {
            tareasTramitesMap.put(tareaTramite.getIdTramite(),tareaTramite.getIdTarea());
        }
        return tareasTramitesMap;
    }

}
