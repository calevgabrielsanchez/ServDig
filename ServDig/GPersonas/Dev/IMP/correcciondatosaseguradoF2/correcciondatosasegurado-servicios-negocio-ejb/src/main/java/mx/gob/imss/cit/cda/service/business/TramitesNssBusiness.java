package mx.gob.imss.cit.cda.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.interfaces.TramiteNssRemote;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "tramitesNssBusiness", mappedName = "tramitesNssBusiness")
public class TramitesNssBusiness implements TramiteNssRemote {
    
    private final Logger log = LoggerFactory.getLogger(TramitesNssBusiness.class);
    
    @EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;
    
    @Override
    public List<TramiteCorreccionCurp> obtenerTramitesSolicitudByOrigen(String folioId,Long idOrigen) throws SolicitudNoEncontradaException,
    TramiteNoEncontradoException{
        log.debug("******** obtenerTramitesSolicitudByOrigen ***********");
        log.debug("******** folio *********** {} ", folioId);
        log.debug("******** idOrigen *********** {} ", idOrigen);
        Solicitud solicitud = solicitudBusiness.consultarPorFolioSolicitud(folioId);
        List<TramiteCorreccionCurp> tramites = new ArrayList<TramiteCorreccionCurp>();
        for(Tramite tramite : solicitud.getTramites()){
            TramiteCorreccionCurp tramiteCurp = (TramiteCorreccionCurp) tramite;
            
            if(idOrigen.intValue() == OrigenCapturaCDAEnum.SOLICITUD.getClave().intValue()){
                log.debug("******** origenTramite *********** {} ", tramiteCurp.getOrigenTramite());
                if(tramiteCurp.getOrigenTramite() ==0){
                    tramites.add(tramiteCurp);
                }else if(tramiteCurp.getOrigenTramite()==idOrigen.intValue()){
                    tramites.add(tramiteCurp);
                }
            }

        }
      return tramites;  
    }

}
