package mx.gob.imss.cit.cda.web.autorizar.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.web.autorizar.vo.AutorizarSolicitud;
import mx.gob.imss.cit.cda.web.bandeja.vo.TareaTramite;
import mx.gob.imss.cit.cda.web.common.utils.SolicitudUtils;
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
public class AutorizarSolicitudUtils  extends SolicitudUtils {
    
    /**
     * Metodo para asignar el estado "autorizado" a la solicitud
     * @param solicitud
     * @param autorizarSolicitud
     * @param usuario
     * @return 
     */
    public Solicitud crearSolicitudAutorizacion(Solicitud solicitud,AutorizarSolicitud autorizarSolicitud, String usuario) {
        
        // Se actualiza debido a que no lo regresa el sistema
        solicitud.getSolicitante().setCveIdUsuario(autorizarSolicitud.getCurpResponsable());
        
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
        solicitud.setEstadoSolicitud(estadoSolicitud);
        solicitud.setTramites(crearTramitesAutorizacion(solicitud.getTramites(),autorizarSolicitud, usuario));
        return solicitud;
    }

    /**
     * Metodo para asignar el estado de autorizado a los tramites
     * @param tramites
     * @param autorizarSolicitud
     * @param usuario
     * @return 
     */
    private List<Tramite> crearTramitesAutorizacion(List<Tramite> tramites, AutorizarSolicitud autorizarSolicitud, String usuario){
        List<Tramite> lstTramite = new ArrayList<Tramite>();
        
        for (Tramite tramitecda : tramites) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;          
            
            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.ANALISIS_COMPLETADO.getCodigo()));
            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.ANALISIS_COMPLETADO.getCodigo());
            tramite.setEstadoTramite(estadoTramite);
            
            if (tramite.getObservacionesSubdelegacion() == null) {
                tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
            }
            tramite.getObservacionesSubdelegacion().add(crearObservacionTramiteAutorizacion (autorizarSolicitud, estadoTramite, usuario));
            lstTramite.add(tramite);
        }
        
        return lstTramite;
        
    }
    
    /**
     * Metodo para crear la observacion de cada tramite para la autorizacion 
     * @param autorizarSolicitud
     * @param estadoTramite
     * @param usuario
     * @return 
     */
    private ObservacionesSubdelegacion crearObservacionTramiteAutorizacion(AutorizarSolicitud autorizarSolicitud,
            EstadoTramite estadoTramite, String usuario ){
        ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion();
        obSubdelegacion.setFechaActualizacion(new Date());
        obSubdelegacion.setUsuario(usuario);
        obSubdelegacion.setAsignado(autorizarSolicitud.getCurpResponsable());
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
