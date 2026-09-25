package mx.gob.imss.cit.cda.service.certificacion.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import mx.gob.imss.cit.cda.service.certificacion.utility.interfaces.GenerarCertificacionUtilityLocal;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "generarCertificacionUtility", mappedName = "generarCertificacionUtility")
public class GenerarCertificacionUtility implements GenerarCertificacionUtilityLocal {

    private final Logger log = LoggerFactory.getLogger(GenerarCertificacionUtility.class);
    
    @Override
    public FirmaElectronica armarFirma(Map<String, String> firma) {
        FirmaElectronica firmaElectronica = new FirmaElectronica();

        firmaElectronica.setCadenaOriginal((String) firma.get("cadenaOriginal"));
        firmaElectronica.setReciboNotarial((String) firma.get("tramite"));
        firmaElectronica.setSecuenciaNotaria((String) firma.get("tramite"));
        firmaElectronica.setSerialCertificado((String) firma.get("numeroSerie"));
        firmaElectronica.setRecibo((String) firma.get("selloDigital"));

        return firmaElectronica;
    }

    @Override
    public Solicitud datosFirmaSolicitud(Map<String, String> firma, Solicitud solicitudCorreccion) {
        
        solicitudCorreccion.setCadenaOriginal((String) firma.get("cadenaOriginal"));
        solicitudCorreccion.setSecuenciaDeNotaria((String) firma.get("tramite"));
        solicitudCorreccion.setSelloDigital((String) firma.get("selloDigital"));
        solicitudCorreccion.setNumeroSerieCertificado((String) firma.get("numeroSerie"));

        return solicitudCorreccion;
    }

    @Override
    public Solicitud actualizaEstadosSolicitudCertificacion(Solicitud solicitud, String usuario, String asignado) {

        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
        solicitud.setEstadoSolicitud(estadoSolicitud);            
        solicitud.setTramites(crearTramitesCertificacion (solicitud.getTramites(), usuario, asignado));
        
        return solicitud;

    }
         
    private List<Tramite> crearTramitesCertificacion (List<Tramite> tramites,String usuario, String asignado){
        List<Tramite> lstTramite = new ArrayList<Tramite>();
        
        for (Tramite tramitecda : tramites) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;            
            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
            estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.CERRADO.getCodigo()));
            tramite.setEstadoTramite(estadoTramite);
            log.debug("Numero de observaciones antes {}", tramite.getObservacionesSubdelegacion().size());
            
            if (tramite.getObservacionesSubdelegacion() == null) {
                tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
            }
                
                tramite.getObservacionesSubdelegacion().add(crearObservacionTramiteCertificacion(estadoTramite, usuario, asignado));
                log.debug("Numero de observaciones despues {}", tramite.getObservacionesSubdelegacion().size());
                lstTramite.add(tramite);                
            }
            return lstTramite;        
        }
         
        private ObservacionesSubdelegacion crearObservacionTramiteCertificacion (EstadoTramite estadoTramite, String usuario, String asignado ){
            ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion();
            obSubdelegacion.setFechaActualizacion(new Date());
            obSubdelegacion.setUsuario(usuario);
            obSubdelegacion.setAsignado(asignado);
            obSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
            
            return obSubdelegacion;
        
        }
}
