package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.clasificacion.actividad.economica;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Webservice Usado actualmente para generar solicitudes de alta
 */
@Stateless
@WebService(name="wsClasificacionActividadEconomica"
        , portName="wsClasificacionActividadEconomicaPort"
        , serviceName="wsClasificacionActividadEconomica"
        , targetNamespace= "http://mx.gob.imss.ctirss.delta.gestion.patronal.service.business.clasificacion.actividad.economica")
public class WSClasificacionActividadEconomica 
    implements ClasificacionActividadEconomica {

    private static final Logger log = LoggerFactory.getLogger(WSClasificacionActividadEconomica.class);

    @EJB
    private transient SolicitudServiceBusinessRemote solicitudServiceBusiness;

    @EJB
    private transient ActividadEcServiceRemote ejb;

    @WebMethod
    @WebResult(name="solicitudResult" , targetNamespace= "http://mx.gob.imss.ctirss.delta.gestion.patronal.service.business.clasificacion.actividad.economica")
    public String crearSolicitudAltaPatronal(String numeroRegistroPatronal) {
        try {
            Solicitud solicitud = ejb.crearSolicitudAltaPatronal(numeroRegistroPatronal, null);

            log.info("Generando analisis nuevo.");
            log.info("*************************");
            
            solicitudServiceBusiness.cancelarAnalisisPorRegistroPatronal(
                    numeroRegistroPatronal.replaceFirst("(.{8}).*", "$1"),
                    EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave(),
                    solicitud);
        }
        catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            return "";
        }catch (ClasificacionException e) {
            log.error(e.getMessage(), e);
            return "";
        }
        
        return "OK";
    }

    @WebMethod
    @WebResult(name="bajaResult" , targetNamespace= "http://mx.gob.imss.ctirss.delta.gestion.patronal.service.business.clasificacion.actividad.economica")
    public String bajaPatronal(String registroPatronal) {
        try {
            ejb.bajaPatronal(registroPatronal);
        }
        catch (RuntimeException e) {
            log.error(e.getMessage(), e);
        }
        catch (GestionPatronalBusinessException e) {
            log.error(e.getMessage(), e);
            return "";
        }
        
        return null;
    }

}
