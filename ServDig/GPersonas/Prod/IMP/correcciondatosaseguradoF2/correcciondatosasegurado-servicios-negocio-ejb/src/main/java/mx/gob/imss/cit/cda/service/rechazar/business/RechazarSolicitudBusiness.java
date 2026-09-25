package mx.gob.imss.cit.cda.service.rechazar.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.common.business.OperacionesSolicitudBusiness;
import mx.gob.imss.cit.cda.service.interfaces.RechazarSolicitudRemote;
import mx.gob.imss.cit.cda.service.rechazar.utility.RechazarSolicitudUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Stateless(name = "rechazarSolicitudBusiness", mappedName = "rechazarSolicitudBusiness")
public class RechazarSolicitudBusiness extends OperacionesSolicitudBusiness implements RechazarSolicitudRemote {
    
    @EJB
    private RechazarSolicitudUtilityLocal RechazarSolicitudUtility;
    
    public void rechazarSolicitud(Solicitud solicitud, List<Long> idsTareasUsuario,
            String usuario) throws SolicitudNoEncontradaException,
            TramiteNoEncontradoException, BPMException {
        getLog().info("Inicia Rechazo Solicitud {}", solicitud.getSolicitudId());
     
        getSolicitudBusiness().actualizarEstados(solicitud);
        
        for (Tramite tramite : solicitud.getTramites()) {
            TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp) tramite;
            getSolicitudBusiness().actualizarXmlTramite(tramiteCda);
            
          
        }
        
    }
    

    public RechazarSolicitudUtilityLocal getRechazarSolicitudUtility() {
        return RechazarSolicitudUtility;
    }



}
