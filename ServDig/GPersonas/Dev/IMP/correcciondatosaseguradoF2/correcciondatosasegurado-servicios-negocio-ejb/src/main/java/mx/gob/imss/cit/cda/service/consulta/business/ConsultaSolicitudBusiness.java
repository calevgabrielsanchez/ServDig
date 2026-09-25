package mx.gob.imss.cit.cda.service.consulta.business;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.consulta.entity.ConsultaSolicitudLocal;
import mx.gob.imss.cit.cda.service.entity.DetalleNssCdaLocal;
import mx.gob.imss.cit.cda.service.entity.DocumentoCdaLocal;
import mx.gob.imss.cit.cda.service.interfaces.ConsultaSolicitudRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DetalleNss;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para módulo de Consulta de la solicitud de CDA
 * 
 * @author STK
 * 
 */
@Stateless(name = "consultaSolicitudBusiness", mappedName = "consultaSolicitudBusiness")
public class ConsultaSolicitudBusiness implements ConsultaSolicitudRemote {

    private final Logger logger = LoggerFactory.getLogger(ConsultaSolicitudBusiness.class);

    @EJB
    private ConsultaSolicitudLocal ConsultaSolicitudEntity;
    
    @EJB
    private DetalleNssCdaLocal DetalleNssCdaLocal;
    @EJB
    private DocumentoCdaLocal documentoCdaLocal;

    public String obtenerOrigenNssCapturado(Long cveIdTramite) {
        getLogger().debug("Consulta Origen del NSS por el tramite {}", cveIdTramite);
        DitDetalleNss ditDetalleNss = getConsultaSolicitudEntity()
                .consultarOrigenNssPorTramite(cveIdTramite.toString());
        if (ditDetalleNss != null) {
            return ditDetalleNss.getDicOrigenCapturaNssCda()
                    .getDesOrigenCapturaNssCda();
        }
        return null;
    }

    
    public ConsultaSolicitudLocal getConsultaSolicitudEntity() {
        return ConsultaSolicitudEntity;
    }

    public Logger getLogger() {
        return logger;
    }

    @Override
    public Long obtenerTipoNss(Long cveIdTramite) {
        
        DitDetalleNss ditDetalleNss = getConsultaSolicitudEntity()
                .consultarOrigenNssPorTramite(cveIdTramite.toString());
        if (ditDetalleNss != null) {
            return ditDetalleNss.getDicTipoNss().getCveTipoNss();
        }
        return null;
    }

}
