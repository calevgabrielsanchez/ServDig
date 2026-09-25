package mx.gob.imss.cit.cda.service.reasignar.business;

import java.util.Iterator;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.common.business.OperacionesSolicitudBusiness;
import mx.gob.imss.cit.cda.service.interfaces.ReasignarSolicitudRemote;
import mx.gob.imss.cit.cda.service.reasignar.utility.ReasignarSolicitudUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "reasignarSolicitudBusiness", mappedName = "reasignarSolicitudBusiness")
public class ReasignarSolicitudBusiness extends OperacionesSolicitudBusiness implements ReasignarSolicitudRemote{
    
    private final Logger log = LoggerFactory.getLogger(ReasignarSolicitudBusiness.class);
    
    @EJB
    private ReasignarSolicitudUtilityLocal reasignarSolicitudUtility;
    
    public void reasignarTareaAutorizador(Solicitud solicitud,Map<String, String> tareas, String observacion, String usuario)
            throws SolicitudNoEncontradaException,TramiteNoEncontradoException, BPMException {
        
        getSolicitudBusiness().actualizarUsuarioSolicitud(solicitud);
        if (solicitud.getSolicitante() != null) {
            log.debug("---CDA--- Usuario Solicitante {} Sol {}", solicitud.getSolicitante().getCveIdUsuario(), solicitud .getSolicitudId());
        }
        
        getSolicitudBusiness().actualizarEstados(solicitud);
        
        for (Tramite tramite : solicitud.getTramites()) {
            TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp) tramite;
            getSolicitudBusiness().actualizarXmlTramite(tramiteCda);
            Iterator<Map.Entry<String, String>> entries = tareas.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                if (tramiteCda.getTramiteId().equals(Long.parseLong(entry.getKey()))) {
                    log.debug("Tarea {} del tramite {} ",entry.getValue(), entry.getKey());
                    getFlujoTrabajoCDARemote().reasignarTareaCDA(usuario, Long.parseLong(entry.getValue()),getReasignarSolicitudUtility().crearMensajeTarea(solicitud, usuario,  null,  observacion));
                }
            }
            
        }

    }

    public ReasignarSolicitudUtilityLocal getReasignarSolicitudUtility() {
        return reasignarSolicitudUtility;
    }

}
