package mx.gob.imss.cit.cda.service.cancelar.business;

import java.util.Iterator;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.cancelar.utility.CancelarSolicitudUtilityLocal;
import mx.gob.imss.cit.cda.service.common.business.OperacionesSolicitudBusiness;
import mx.gob.imss.cit.cda.service.interfaces.CancelarSolicitudRemote;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Stateless(name = "cancelarSolicitudBusiness", mappedName = "cancelarSolicitudBusiness")
public class CancelarSolicitudBusiness extends OperacionesSolicitudBusiness implements CancelarSolicitudRemote{
    
    @EJB
    private CancelarSolicitudUtilityLocal cancelarSolicitudUtility;

    @Override
    public void cancelarTarea(Solicitud solicitud,
            Map<String, String> tramitesTareas, String usuario,
            Boolean isAutorizador) throws SolicitudException,
            NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
            NoExisteTransicionParaTareaUsuarioException,
            TereaSinUsuarioAsignadoException, NumberFormatException,
            SolicitudNoEncontradaException, TramiteNoEncontradoException {
        
        getLog().debug("Recibiendo los datos de la Solicitud id {} a cancelar ",new Object[] { solicitud.getSolicitudId() });
        
        getSolicitudBusiness().cancelarSolicitud(solicitud.getSolicitudId(), null,
                solicitud.getRazonCancelacion().getIdRazonCancelacion(),
                solicitud.getPersonaInteresada().getIdPersona().toString(),
                solicitud.getObservacion());
        
        getSolicitudBusiness().actualizarEstados(solicitud);

        for (Tramite tramite : solicitud.getTramites()) {
            TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp) tramite;
            getSolicitudBusiness().actualizarXmlTramite(tramiteCda);
            Iterator<Map.Entry<String, String>> entries = tramitesTareas.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                if (tramiteCda.getTramiteId().equals(Long.parseLong(entry.getKey()))) {
                    getLog().debug("Tarea {} del tramite {} ",entry.getValue(), entry.getKey());
                    getFlujoTrabajoRemote().completarTarea(Long.parseLong(entry.getValue()), getCancelarSolicitudUtility().crearMensajeTarea(solicitud,usuario,isAutorizador,tramiteCda));
                }
            }
            
        }

              
    }
    
    public CancelarSolicitudUtilityLocal getCancelarSolicitudUtility() {
        return cancelarSolicitudUtility;
    }

}
