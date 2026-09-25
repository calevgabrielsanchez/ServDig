package mx.gob.imss.cit.cda.web.comprobante.helper;

import mx.gob.imss.cit.cda.core.events.CreateEvent;
import mx.gob.imss.cit.cda.core.events.CreatedEvent;
import mx.gob.imss.cit.cda.core.helper.CreateHelper;
import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
*
* @author antonio
*/
@Component(BeansConstants.DESCARGAR_COMPROBANTE_HELPER)
public class DescargarComprobanteSolicitudHelper implements CreateHelper<String, byte[]> {
    
    private final Logger log = LoggerFactory.getLogger(DescargarComprobanteSolicitudHelper.class);
    
    @Autowired
    @Qualifier("bovedaBusiness")
    private BovedaRemote bovedaBusiness;
    
    @Autowired
    private SolicitudBusinessRemote solicitudBusiness;

    @Override
    public CreatedEvent<byte[]> requestEvent( CreateEvent<String> requestCreateEvent){
        
        log.debug("CDA Cancelar Folio [{}]", requestCreateEvent.getData());
        
        byte[] docto = null;
        
        try {
            Solicitud solicitud = getSolicitudBusiness().consultarPorFolioSolicitud(requestCreateEvent.getData());
            docto = getBovedaBusiness().recuperarDocumento(solicitud,TipoDocumentoCDAEnum.ACUSE,recuperarIdDocumento(solicitud));
        } catch (BovedaCDAException bce) {
            log.error("Descargar Comprobante error", bce.getSituacion());
        }catch (SolicitudNoEncontradaException e) {
            log.error("Solicitud error", e);
        }
        
        return new CreatedEvent<byte[]>(requestCreateEvent.getKey(), docto);
        
    }
    
    private String recuperarIdDocumento(Solicitud sol)throws SolicitudNoEncontradaException {
        String documento = null;
        
        for (Tramite tramite: sol.getTramites()){
            TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp)tramite;
            if(tramiteCda.getIdDocumentoAcuse()!=null){
                documento= tramiteCda.getIdDocumentoAcuse(); 
                break;
            }
        } 
           
        return documento;
        
    }


    public BovedaRemote getBovedaBusiness() {
        return bovedaBusiness;
    }


    public SolicitudBusinessRemote getSolicitudBusiness() {
        return solicitudBusiness;
    }

   
    
}