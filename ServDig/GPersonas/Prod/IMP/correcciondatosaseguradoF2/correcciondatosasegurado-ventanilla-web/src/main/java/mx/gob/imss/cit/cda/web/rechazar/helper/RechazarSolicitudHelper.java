package mx.gob.imss.cit.cda.web.rechazar.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.RechazarSolicitudRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoNotificacionEnum;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.rechazar.utils.RechazarUtils;
import mx.gob.imss.cit.cda.web.utils.CorreosUtils;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.TipoTransicionEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(BeansConstants.RECHAZAR_SOLICITUD_HELPER)
public class RechazarSolicitudHelper implements UpdateHelper<SeguimientoSolicitud, SeguimientoSolicitud> {

    @Autowired
    @Qualifier("rechazarSolicitudBusiness")
    private RechazarSolicitudRemote rechazarSolicitudBusiness;

    @Autowired
    private RechazarUtils rechazarUtils;

    @Autowired
    private CorreosUtils correosUtils;

    @Autowired
    @Qualifier("envioCorreoElectronicoBusiness")
    private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;

    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionBusiness;

    @Autowired
    private SolicitudBusinessRemote solicitudBusiness;
    
    @Autowired
    private FlujoTrabajoRemote flujoTrabajoBusiness;

    
    private final Logger log = LoggerFactory.getLogger(RechazarSolicitudHelper.class);

    @SuppressWarnings("unchecked")
    @Override
    public UpdatedEvent<SeguimientoSolicitud> requestEvent(UpdateEvent<SeguimientoSolicitud> requestUpdateEvent) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            log.debug("---CDA--- Rechazar idPersona [{}] idTarea [{}]",requestUpdateEvent.getUserProfile().getIdPersona(),requestUpdateEvent.getData().getIdTarea());

            Solicitud solicitud = solicitudBusiness.consultarPorFolioSolicitud(requestUpdateEvent.getData().getFolio());
              
            for (Tramite t : solicitud.getTramites()) {
                log.debug(" ----solicitud.tramiteId {}: " , t.getTramiteId());
            }
            //Map<String, String> tramitesTareas = getTareaActivaTramites(solicitud.getTramites());
            
            List<Long> tramitesTareas = getTareaActivaPorTramites(solicitud.getTramites());
          
            // Persona que cancelara la solicitud de seguimiento
            Fisica fisica = new Fisica(requestUpdateEvent.getUserProfile().getIdPersona());
            fisica.setCurp(requestUpdateEvent.getUserProfile().getUsuario());
            
            log.debug("---CDA--- TramitesTareas: "+ tramitesTareas);
//            getRechazarSolicitudBusiness().rechazarSolicitud(getRechazarUtils().crearSolicitudRechazada(solicitud, requestUpdateEvent.getData(), fisica), 
//                                                              tramitesTareas, requestUpdateEvent.getUserProfile().getUsuario());
            
            MensajeTarea mensajeTarea = new MensajeTarea();
            mensajeTarea.setObservacion("");
            mensajeTarea.setTipoTransicion(TipoTransicionEnum.PRINCIPAL.getId());
            mensajeTarea.setEstado(EstadoNegocioEnum.RECHAZADA.getDescripcion());
            mensajeTarea.setFechaActualizacion(simpleDateFormat.format(new Date()));
        
            for (Iterator<Long> iterator = tramitesTareas.iterator(); iterator.hasNext();) {
                Long next = iterator.next();
                flujoTrabajoBusiness.completarTarea(next, mensajeTarea);
            }
            
            
            getRechazarSolicitudBusiness().rechazarSolicitud(getRechazarUtils().crearSolicitudRechazada(solicitud, requestUpdateEvent.getData(), fisica), 
                                                              tramitesTareas, requestUpdateEvent.getUserProfile().getUsuario());
            log.debug("---CDA--- TramitesTareas: "+ tramitesTareas);
            log.debug("---CDA--- CURPResponsable: "+ requestUpdateEvent.getData().getResponsable());
            
            mx.gob.imss.ctirss.delta.model.Usuario responsable = responsablesDelegacionBusiness.recuperaUsuarioEsquemaSeguridadByCURP(requestUpdateEvent.getData().getResponsable());
            
            if (responsable.getFisica().getCorreoElectronico() != null && responsable.getFisica().getCorreoElectronico().getCorreo() != null) {
                envioCorreoElectronicoBusinessRemote.enviarCorreo(correosUtils.crearCorreoElectronicoDTO(requestUpdateEvent.getData(),
                                                                                                         TipoNotificacionEnum.RECHAZO, 
                                                                                                         responsable),
                                                                   EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
            }
            
            log.debug("---CDA--- Rechazo de la solicitud por ventanilla ");
            
            return new UpdatedEvent<SeguimientoSolicitud>(requestUpdateEvent.getKey(), requestUpdateEvent.getData());
            
        } catch (ClienteWebserviceResponsablesSubdelegacionException e) {
            log.debug("---CDA--- Error ClienteWebserviceResponsablesSubdelegacionException {}",e);
        } catch (Exception e) {
            log.error("---------------------CDA Error al rechazar---------------------{}",e);
        }

        return UpdatedEvent.notUpdated(requestUpdateEvent.getKey());
    }

    public RechazarUtils getRechazarUtils() {
        return rechazarUtils;
    }

    public RechazarSolicitudRemote getRechazarSolicitudBusiness() {
        return rechazarSolicitudBusiness;
    }

    private Map<String, String> getTareaActivaTramites(List<Tramite> tramitesSol){
        log.debug("Ingresa a obtener Tarea Activa de Tramites");
        Map<String, String> tramitesTareaActiva = new HashMap<String, String>();
        if (!tramitesSol.isEmpty()) {
          for (Tramite tramite : tramitesSol) {
            log.debug(" ----- idTramite de solicitud: {}", tramite.getTramiteId()); 
            TareaBandeja tareaActiva = flujoTrabajoBusiness.getTareaActivaPorIdTramite(tramite.getTramiteId());
            tramitesTareaActiva.put(tareaActiva.getIdTramite().toString(), tareaActiva.getIdTareaUsuario().toString());
            log.debug("--- tareaActiva.idTramite {} , tareaActiva.idTarea {}", tareaActiva.getIdTramite(), tareaActiva.getIdTareaUsuario());
          }  
        } else {
          log.debug("Lista de Tramites en Solicitud vacía");  
        }        
        return tramitesTareaActiva;
    }
    
    private List<Long> getTareaActivaPorTramites(List<Tramite> tramitesSol){
        log.debug("Ingresa a obtener Tarea Activa de Tramites");
        List<Long> idsTareasUsuario = new ArrayList<Long>();
        if (!tramitesSol.isEmpty()) {
          for (Tramite tramite : tramitesSol) {
            log.debug(" ----- idTramite de solicitud: {}", tramite.getTramiteId()); 
            TareaBandeja tareaActiva = flujoTrabajoBusiness.getTareaActivaPorIdTramite(tramite.getTramiteId());
            log.debug("--- tareaActiva.getIdTareaUsuario() {}", tareaActiva.getIdTareaUsuario());
            idsTareasUsuario.add(tareaActiva.getIdTareaUsuario());
            
          }  
        } else {
          log.debug("Lista de Tramites en Solicitud vacía");  
        }        
        return idsTareasUsuario;
    }
    
}
