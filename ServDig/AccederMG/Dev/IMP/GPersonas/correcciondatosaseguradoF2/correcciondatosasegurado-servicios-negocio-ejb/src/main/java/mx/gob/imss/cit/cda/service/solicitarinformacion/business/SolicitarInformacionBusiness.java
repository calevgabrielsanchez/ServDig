package mx.gob.imss.cit.cda.service.solicitarinformacion.business;

import java.util.Iterator;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.common.business.OperacionesSolicitudBusiness;
import mx.gob.imss.cit.cda.service.interfaces.SolicitarInformacionRemote;
import mx.gob.imss.cit.cda.service.solicitarinformacion.entity.SolicitarInformacionEntityLocal;
import mx.gob.imss.cit.cda.service.solicitarinformacion.utility.SolicitarInformacionUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "solicitarInformacionBusiness", mappedName = "solicitarInformacionBusiness")
public class SolicitarInformacionBusiness extends OperacionesSolicitudBusiness implements SolicitarInformacionRemote{
    
    private final Logger log = LoggerFactory.getLogger(SolicitarInformacionBusiness.class);
    
    @EJB
    private SolicitarInformacionUtilityLocal solicitarInformacionUtility;
    
    @EJB
    private SolicitarInformacionEntityLocal solicitarInformacionEntity;


    @Override
    public void avanzarSolicitarInformacion(Solicitud solicitud,
            Map<String, String> tramitesTareas, String observacion,
            String usuario, Boolean isAutorizador) throws SolicitudNoEncontradaException,
            TramiteNoEncontradoException, BPMException {
        
        log.info("Inicia Guardado de Solicitar informacion");

        getSolicitudBusiness().actualizarEstados(solicitud);

        for (Tramite tramite : solicitud.getTramites()) {
            TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp) tramite;
            getSolicitudBusiness().actualizarXmlTramite(tramiteCda);
            
            Iterator<Map.Entry<String, String>> entries = tramitesTareas.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                if (tramiteCda.getTramiteId().equals(Long.parseLong(entry.getKey()))) {
                    log.debug("Tarea {} del tramite {} ",entry.getValue(), entry.getKey());
                    getFlujoTrabajoRemote().solicitarInformacion(Long.parseLong(entry.getValue()),getSolicitarInformacionUtility().crearMensajeTarea(solicitud, usuario, tramiteCda, observacion));
                }
            }

            getSolicitarInformacionEntity().actualizarUsuarioSolicitarInformacion(tramiteCda.getTramiteId(), getSolicitarInformacionUtility().obtenerTipoUsuarioVentanilla(isAutorizador));
        }

    }
    
    public SolicitarInformacionUtilityLocal getSolicitarInformacionUtility() {
        return solicitarInformacionUtility;
    }

    public SolicitarInformacionEntityLocal getSolicitarInformacionEntity() {
        return solicitarInformacionEntity;
    }

}
