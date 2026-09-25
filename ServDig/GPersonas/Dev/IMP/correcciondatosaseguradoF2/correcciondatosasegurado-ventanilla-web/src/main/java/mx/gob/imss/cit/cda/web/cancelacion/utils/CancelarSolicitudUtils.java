package mx.gob.imss.cit.cda.web.cancelacion.utils;

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
public class CancelarSolicitudUtils  extends SolicitudUtils{

    /**
     * Metodo para crear la solicitud de cancelacion
     * @param solicitud
     * @param seguimientoSolicitud
     * @param fisica
     * @return 
     */
    public Solicitud crearSolicitudCancelacion(Solicitud solicitud,SeguimientoSolicitud seguimientoSolicitud, Fisica fisica) {

        RazonCancelacion razonCancelacion = new RazonCancelacion();
        Fisica responsable = new Fisica();
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        solicitud.setSolicitudId(new Long(seguimientoSolicitud.getIdSolicitud()));
        razonCancelacion.setIdRazonCancelacion(RazonCancelacionEnum.INASISTENCIA.getId());
        razonCancelacion.setDescripcion(seguimientoSolicitud.getDetalle());
        solicitud.setRazonCancelacion(razonCancelacion);
        responsable.setIdPersona(fisica.getIdPersona());
        solicitud.setPersonaInteresada(responsable);
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
        solicitud.setEstadoSolicitud(estadoSolicitud);
        solicitud.setTramites(crearTramitesCancelacion (solicitud.getTramites(), seguimientoSolicitud, fisica));
        return solicitud;

    }
    
    /**
     * Metodo para asignar el estado cancelado a los tramites
     */
    private List<Tramite> crearTramitesCancelacion (List<Tramite> tramites, SeguimientoSolicitud seguimientoSolicitud, Fisica fisica){
        List<Tramite> lstTramite = new ArrayList<Tramite>();
        
        for (Tramite tramitecda : tramites) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;
            
            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo()));
            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo());
            tramite.setEstadoTramite(estadoTramite);
            
            if (tramite.getObservacionesSubdelegacion() == null) {
                tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
            }
            tramite.getObservacionesSubdelegacion().add(crearObservacionTramiteCancelacion(seguimientoSolicitud, fisica, estadoTramite));
            lstTramite.add(tramite);

        }
        
        return lstTramite;
        
    }
    
    /**
     * Metodo para crear la observacion de cada metodo para la cancelacion
     */
    private ObservacionesSubdelegacion crearObservacionTramiteCancelacion (SeguimientoSolicitud seguimientoSolicitud, Fisica fisica,EstadoTramite estadoTramite ){
        ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion();
        obSubdelegacion.setDetalle(seguimientoSolicitud.getDetalle());
        obSubdelegacion.setResumen(seguimientoSolicitud.getResumen());
        obSubdelegacion.setFechaActualizacion(new Date());
        obSubdelegacion.setUsuario(fisica.getCurp());
        obSubdelegacion.setAsignado(seguimientoSolicitud .getCurpResponsable());
        obSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
        return obSubdelegacion;
    }

    /**
     * Metodo para armar objeto mapeado tramite con su tarea que le corresponde 
     * @param tareasTramites
     * @return 
     */
    public Map<String, String> armarTareasTramites(List<TareaTramite> tareasTramites) {
        Map<String, String> tareasTramitesMap = new HashMap<String, String>();
        for (TareaTramite tareaTramite : tareasTramites) {
            tareasTramitesMap.put(tareaTramite.getIdTramite(),tareaTramite.getIdTarea());
        }
        return tareasTramitesMap;
    }

}
