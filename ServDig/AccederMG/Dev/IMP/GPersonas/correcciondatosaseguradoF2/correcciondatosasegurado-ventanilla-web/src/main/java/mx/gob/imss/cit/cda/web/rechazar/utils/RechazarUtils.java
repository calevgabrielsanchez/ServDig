package mx.gob.imss.cit.cda.web.rechazar.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.bandeja.vo.TareaTramite;
import mx.gob.imss.cit.cda.web.common.utils.SolicitudUtils;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.RazonCancelacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.springframework.stereotype.Component;

@Component
public class RechazarUtils extends SolicitudUtils {
    
    /**
     * Metodo para crear la solicitud de rechazo
     * 
     * @param solicitud
     * @param sequimientoSolicitud
     * @param fisica
     *            persona que cancelara la solicitud
     * @return solicitud
     */
    public Solicitud crearSolicitudRechazada(Solicitud solicitud, SeguimientoSolicitud seguimientoSolicitud, Fisica fisica) {
        
        solicitud.setSolicitudId(new Long(seguimientoSolicitud.getIdSolicitud()));
        // solicitud.setObservacion(seguimientoSolicitud.getResumen());
        solicitud.setRazonCancelacion(crearRazonCancelacion(seguimientoSolicitud.getDetalle()));
        Fisica responsable = new Fisica();
        responsable.setIdPersona(fisica.getIdPersona());
        solicitud.setPersonaInteresada(responsable);
        
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
        solicitud.setEstadoSolicitud(estadoSolicitud);
        
        solicitud.setTramites(crearTramitesRechazarSolicitud (solicitud.getTramites() , seguimientoSolicitud, fisica));
        
        return solicitud;
    }
    
    /**
     * Metodo para asignar el estado de rechazo a los tramites
     */
    private List<Tramite> crearTramitesRechazarSolicitud (List<Tramite> tramites,SeguimientoSolicitud seguimientoSolicitud, Fisica fisica){       
        
        List<Tramite> lstTramite = new ArrayList<Tramite>();
        
        for (Tramite tramitecda : tramites) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;
            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.RECHAZADO.getCodigo()));
            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.RECHAZADO.getCodigo());
            tramite.setEstadoTramite(estadoTramite);
            tramite.setTramiteId(new Long(seguimientoSolicitud.getIdTramite()));
            if (tramite.getObservacionesSubdelegacion() == null) {
                tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
            }
            
            tramite.getObservacionesSubdelegacion().add(crearObservacionTramiteRechazo(seguimientoSolicitud,estadoTramite,fisica));
            lstTramite.add(tramite); 
            
        }
        
        return lstTramite;
    }
    
    /**
     * Metodo para crear razón de rechazo
     */

    private RazonCancelacion crearRazonCancelacion(String detalle){
        RazonCancelacion razonCancelacion = new RazonCancelacion();
        razonCancelacion.setIdRazonCancelacion(RazonCancelacionEnum.INASISTENCIA.getId());
        razonCancelacion.setDescripcion(detalle);
        return razonCancelacion;
    }
    
    
    /**
     * Metodo para crear la observacion de cada tramite para el rechazo de la solicitud
     */
    private ObservacionesSubdelegacion crearObservacionTramiteRechazo(SeguimientoSolicitud seguimientoSolicitud, EstadoTramite estadoTramite,Fisica fisica){
        ObservacionesSubdelegacion observacionesSubdelegacion = new ObservacionesSubdelegacion();
        observacionesSubdelegacion.setDetalle(seguimientoSolicitud.getDetalle());
        observacionesSubdelegacion.setResumen(seguimientoSolicitud.getResumen());
        observacionesSubdelegacion.setIdTarea(seguimientoSolicitud.getIdTarea());
        observacionesSubdelegacion.setFechaActualizacion(new Date());
        observacionesSubdelegacion.setUsuario(fisica.getCurp());
        observacionesSubdelegacion.setAsignado(seguimientoSolicitud.getResponsable());
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
