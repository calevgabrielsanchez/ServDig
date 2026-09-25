package mx.gob.imss.cit.cda.web.confirmar.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;
import mx.gob.imss.cit.cda.web.bandeja.vo.TareaTramite;
import mx.gob.imss.cit.cda.web.common.utils.SolicitudUtils;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CertificacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoNSSCorreccion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoRegularizacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoRegularizacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfirmarSolicitudUtil extends SolicitudUtils{

    @Value("${encabezado}")
    private String ENCABEZADO_CORREOS;

    @Value("${pie.pagina}")
    private String PIE_PAGINA;

    @Value("${contenido.correo.atencion.autorizador}")
    private String CONTENIDO_CORREO_ATENCION_AUTORIZADOR;

    public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud crearSolicitudConfirmacionDatos(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud,UpdateEvent<Solicitud> requestUpdateEvent) {

        mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitudIMSS = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();

        solicitudIMSS.setSolicitudId(Long.parseLong(requestUpdateEvent.getData().getIdSolicitud()));
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
        solicitudIMSS.setEstadoSolicitud(estadoSolicitud);
        solicitud.setTramites(crearTramitesConfirmacion (solicitud.getTramites(), requestUpdateEvent ));

        return solicitudIMSS;

    }
    
    /**
     * Metodo para asignar el estado en espera de autorizacion a los tramites
     */
    private List<Tramite> crearTramitesConfirmacion (List<Tramite> tramites,UpdateEvent<Solicitud> requestUpdateEvent){
        
        List<Tramite> lstTramite = new ArrayList<Tramite>();
        
        for (Tramite tramitecda : tramites) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;
            
//            // TODO actualizar el detalle de la correccion
//            // Tipo de Regularizacion de la solicitud
//            tramite.setTipoRegularizacion(getTipoRegularizacion(requestUpdateEvent.getData()));
//
//            // Tipo de cada NSS && Tipo de Correccion del NSS
//            tramite.setCertificacionNSS(getCertificacionNSS(requestUpdateEvent.getData()));

            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo()));
            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo());
           
            tramite.setEstadoTramite(estadoTramite);

            if (tramite.getObservacionesSubdelegacion() == null) {
                tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
            }
            tramite.getObservacionesSubdelegacion().add(crearObservacionTramiteConfirmacion(requestUpdateEvent, estadoTramite));
            
            lstTramite.add(tramite);

        }
        
        return lstTramite;
        
        
    }
    
    /**
     * Metodo para crear la observacion de cada metodo para la confirmación de la solicitud
     */
    private ObservacionesSubdelegacion crearObservacionTramiteConfirmacion (UpdateEvent<Solicitud> requestUpdateEvent,EstadoTramite estadoTramite){
        ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion();
        obSubdelegacion.setFechaActualizacion(new Date());
        obSubdelegacion.setUsuario(requestUpdateEvent.getUserProfile().getUsuario());
        obSubdelegacion.setAsignado(((Solicitud) requestUpdateEvent.getData()).getCurpResponsable());
        obSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
        return obSubdelegacion;
    }

    /**
     * Metodo para Tipo de regularizacion de cada NSS TipoRegularizacionNSS vs TipoNSS
     */
    private CertificacionNSS getCertificacionNSS(Solicitud solicitud) {
        CertificacionNSS certificacionNSS = new CertificacionNSS();

        TipoNSSCorreccion correccionNSS = new TipoNSSCorreccion();

        // TODO iterar los datos por NSS en lugar de obtener de manera fija la posicion 0
        List<TipoRegularizacionNSS> tiposRegularizacionNSS = null;
        if (solicitud.getGridNSS().getData().get(0).getGrupoCorreccion().getIdRegularizacionNSS() != null
         &&!solicitud.getGridNSS().getData().get(0).getGrupoCorreccion().getIdRegularizacionNSS().isEmpty()) {
            
            tiposRegularizacionNSS = new ArrayList<TipoRegularizacionNSS>();
            Set<Long> setTiposRegularizacionNSS = new HashSet<Long>(solicitud.getGridNSS().getData().get(0).getGrupoCorreccion().getIdRegularizacionNSS());
            for (Long idTipoRegularizacionNSS : setTiposRegularizacionNSS) {
                TipoRegularizacionNSS regulariacionNSS = new TipoRegularizacionNSS();
                regulariacionNSS.setIdTipoRegularizacionNSS(idTipoRegularizacionNSS);
                tiposRegularizacionNSS.add(regulariacionNSS);
            }

        }

        correccionNSS.setDesTipoNSSAclaracion(solicitud.getGridNSS().getData().get(0).getTipoCorreccion());

        certificacionNSS.setTipoRegularizacionNSS(tiposRegularizacionNSS);
        certificacionNSS.setTipoNSS(correccionNSS);
        return certificacionNSS;
    }

    /**
     * Metodo para crear Tipo de Regularizacion de la solicitud
     */
    private TipoRegularizacion getTipoRegularizacion(Solicitud solicitud) {
        TipoRegularizacion tipo = new TipoRegularizacion();
        Long idTipoRegularizacion = solicitud.getTipoRegularizacion().getTipoRegularizacionId();
        tipo.setIdTipoRegularizacion(idTipoRegularizacion);
        return tipo;
    }

    public String contenidoCorreoAtencionAutorizador(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud,String autorizador, String origenMovimiento) {

        StringBuffer body = new StringBuffer();

        body.append(ENCABEZADO_CORREOS);

        getLog().debug("---CDA--- correo autorizador atenciÃ³n folio {}",solicitud.getNoFolioSolicitud());

        body.append(MessageFormat.format(
                CONTENIDO_CORREO_ATENCION_AUTORIZADOR,
                new Object[] {
                        solicitud.getNoFolioSolicitud(),
                        autorizador,
                        solicitud.getNoFolioSolicitud(),
                        ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                                .getPersonaRENAPO().getCurp(),
                        StringEscapeUtils
                                .escapeHtml(((TramiteCorreccionCurp) solicitud
                                        .getTramites().get(0))
                                        .getPersonaRENAPO().getNombreCompleto()),
                        ((TramiteCorreccionCurp) solicitud.getTramites().get(0))
                                .getListaNSS().get(0), origenMovimiento }));

        body.append(PIE_PAGINA);

        return body.toString();
    }

    public Map<String, String> armarTareasTramites(List<TareaTramite> tareasTramites) {
        Map<String, String> tareasTramitesMap = new HashMap<String, String>();
        for (TareaTramite tareaTramite : tareasTramites) {
            tareasTramitesMap.put(tareaTramite.getIdTramite(),tareaTramite.getIdTarea());
        }
        return tareasTramitesMap;
    }

}
