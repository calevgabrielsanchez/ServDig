package mx.gob.imss.cit.cda.service.confirmar.business;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.common.business.OperacionesSolicitudBusiness;
import mx.gob.imss.cit.cda.service.confirmar.utility.ConfirmarSolicitudUtilityLocal;
import mx.gob.imss.cit.cda.service.interfaces.ConfirmarSolicitudRemote;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Stateless(name = "confirmarSolicitudBusiness", mappedName = "confirmarSolicitudBusiness")
public class ConfirmarSolicitudBusiness extends OperacionesSolicitudBusiness implements ConfirmarSolicitudRemote{

    @EJB
    private ConfirmarSolicitudUtilityLocal confirmarSolicitudUtility;
    
    
    
  
    
    
    
    @Override
    public void avanzarTareaResponsable(Solicitud solicitud,
            Map<String, String> tramitesTareas)
            throws SolicitudNoEncontradaException,
            TramiteNoEncontradoException, NoExisteTareaUsuarioException,
            EstadoTareaUsuarioNoValidoException,
            NoExisteTransicionParaTareaUsuarioException,
            TereaSinUsuarioAsignadoException, NumberFormatException {
        getLog().debug("Recibiendo los datos de la Solicitud id {} a enviar a autorizacion");

//        getSolicitudBusiness().actualizarEstados(solicitud);
        
        
        for (Tramite tramite : solicitud.getTramites()) {
            TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp) tramite;
                       
//           getSolicitudBusiness().actualizarXmlTramite(tramiteCda);
            
            getLog().debug("cda-- ESTADO DEL TRAMITE: "+tramite.getEstadoTramite());
            
            TareaBandeja tarea =getFlujoTrabajoRemote().getTareaActivaPorIdTramite(tramite.getTramiteId());
            getLog().debug("cda-- TAREA USUARIO: "+tarea.getIdTareaUsuario());
            getFlujoTrabajoRemote().completarTarea(tarea.getIdTareaUsuario(), getConfirmarSolicitudUtility().crearMensajeTarea(tramiteCda));
            
            
//            while (entries.hasNext()) {
//                Map.Entry<String, String> entry = entries.next();
//                if (tramiteCda.getTramiteId().equals(Long.parseLong(entry.getKey()))) {
//                    getLog().debug("Tarea {} del tramite {} ",entry.getValue(), entry.getKey());
//                    getFlujoTrabajoRemote().completarTarea(Long.parseLong(entry.getValue()), getConfirmarSolicitudUtility().crearMensajeTarea(tramiteCda));
//                }
//            }
            
        }

    }

    
    

    public void avanzarTareaTramiteResponsable(List<Tramite> tramites) throws TramiteNoEncontradoException, IllegalArgumentException{
       
    	        
        for (Tramite tramite : tramites) {
            TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp) tramite;
            getSolicitudBusiness().actualizarXmlTramite(tramiteCda);
            
            
            TareaBandeja tarea =getFlujoTrabajoRemote().getTareaActivaPorIdTramite(tramite.getTramiteId());
            
//            getFlujoTrabajoRemote().completarTarea(tarea.getIdTareaUsuario(), getConfirmarSolicitudUtility().crearMensajeTarea(tramiteCda));
            
            
            
        }

    }
    
    public ConfirmarSolicitudUtilityLocal getConfirmarSolicitudUtility() {
        return confirmarSolicitudUtility;
    }
    
}
