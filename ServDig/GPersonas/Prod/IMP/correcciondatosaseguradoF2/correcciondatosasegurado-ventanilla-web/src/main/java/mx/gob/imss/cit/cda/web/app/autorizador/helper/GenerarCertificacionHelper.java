package mx.gob.imss.cit.cda.web.app.autorizador.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.gob.imss.cit.cda.core.events.CreateEvent;
import mx.gob.imss.cit.cda.core.events.CreatedEvent;
import mx.gob.imss.cit.cda.core.helper.CreateHelper;
import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.service.interfaces.CorreccionDatosRemote;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualNoDisponibleException;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.service.interfaces.GenerarCertificacionRemote;
import mx.gob.imss.cit.cda.service.interfaces.ManejadorReportesRemote;
import mx.gob.imss.cit.cda.web.app.autorizador.utils.GenerarCertificacionUtil;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.CorreccionDatosAseguradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DetalleNssCda;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(BeansConstants.GENERAR_CERTIFICACION_HELPER)
public class GenerarCertificacionHelper implements
        CreateHelper<SeguimientoSolicitud, byte[]> {

    private final Logger log = LoggerFactory
            .getLogger(GenerarCertificacionHelper.class);

    @Autowired
    private GenerarCertificacionRemote generarCertificacionBusiness;

    @Autowired
    private SolicitudBusinessRemote solicitudBusiness;

    @Autowired
    @Qualifier("manejadorReportesBusiness")
    private ManejadorReportesRemote manejadorReportesBusiness;

    @Autowired
    private GenerarCertificacionUtil generarCertificacionUtil;

    @Autowired
    @Qualifier("cuentaIndividualBusiness")
    private CuentaIndividualRemote cuentaIndividualBusiness;

    @Autowired
    @Qualifier("envioCorreoElectronicoBusiness")
    private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;

    @Autowired
    @Qualifier("correccionDatosBusiness")
    private CorreccionDatosRemote correccionDatosBussines;

    @Value("${url.envio.correo.seguimiento.ciudadano}")
    private String URL_ENVIO_CORREO_SEGUIMIENTO_CIUDADANO;

    @Autowired
    @Qualifier("bovedaBusiness")
    private BovedaRemote bovedaRemote;

    @Override
    public CreatedEvent<byte[]> requestEvent(
            CreateEvent<SeguimientoSolicitud> requestCreateEvent) {
        log.debug("---CDA--- Generando Solicitud {}", requestCreateEvent
                .getData().getIdSolicitud());
        byte[] documentoCertificado = null;
        String idDocumento = null;
        boolean enviaCorreoAsegurado = false;

        try {
            Solicitud sol = solicitudBusiness.consultarPorFolioSolicitud(requestCreateEvent.getData().getFolio());
            TramiteCorreccionCurp tramite = generarCertificacionUtil.obtenerTramitePrincipal(sol);
            log.debug("---CDA-inicia certificacion");
            log.debug("---CDA-solicitud estado: {} ",sol.getEstadoSolicitud().getIdEstadoSolicitud());
            log.debug("---CDA-solicitud estado" +sol.getEstadoSolicitud().getIdEstadoSolicitud());

            if (!sol.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
                log.debug("---CDA--- Consulta la firma digital");
                generarCertificacionBusiness.solicitarFirmaDigital(sol, tramite.getPersonaRENAPO());
                enviaCorreoAsegurado = true;
            }
            if (tramite.getIdDocumentoCertificacion() != null) {
                documentoCertificado = recuperarCertificacion(sol, tramite);
            }

            /*Se consultan el NSS certificador y asociado*/
            List<DetalleNssCda> listaNss = generarCertificacionBusiness.obtenerListaNSS(tramite.getTramiteId());
            String nssCertificador = this.getNSSCertificador(listaNss);
            if (nssCertificador == null) {
                throw new CorreccionDatosAseguradoException("No fue posible generar el documento de certificaci\u00F3n");
            }

            if (tramite.getListaNSS() == null || tramite.getListaNSS().isEmpty()) {
                log.debug("La lista de NSS es nula, se agregan los NSS consultados previamente");
                tramite.setListaNSS(new ArrayList<String>());
                for (DetalleNssCda nss : listaNss) {
                    tramite.getListaNSS().add(nss.getNss());
                }
            }
            Solicitud solicitudCorregida = null;
            if (documentoCertificado == null) {
                
                log.info("---CDA--- No se encontro el documento se procede a crear un nuevo documento Solicitud ID {}", sol.getSolicitudId());
                documentoCertificado = generarCertificacion(sol, tramite, nssCertificador);

                /* Se cambia el orden en que se ejecuta el proceso, ahora se guarda primero el documento en boveda*/
                idDocumento = subirDocumento(sol, documentoCertificado);

                if(StringUtils.isNotBlank(idDocumento)){
                    log.debug("---CDA--- El documento se guardo correctamente, id del documento {} ", idDocumento);

                    log.debug("---CDA-Estado solicitud: {} ", sol.getEstadoSolicitud().getIdEstadoSolicitud());

                    if (!sol.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
                        try {
                            solicitudCorregida = generarCertificacionBusiness.finalizaTramiteCorreccionDatosBasicosAsegurado(sol,
                                    generarCertificacionUtil.armarTareasTramites(requestCreateEvent),
                                    requestCreateEvent.getUserProfile().getUsuario(), tramite, nssCertificador);
                        } catch (CorreccionDatosAseguradoException ex) {
                            bovedaRemote.eliminarDocumento(idDocumento);
                            log.debug("No se pudo continuar");
                            throw new CorreccionDatosAseguradoException(ex.getSituacion());
                        }
                    } else {
                        solicitudCorregida = sol;
                        enviaCorreoAsegurado = true;
                    }

                    if (enviaCorreoAsegurado) {
                        log.debug("---CDA--- enviando correo de asegurado con certificacion {}", solicitudCorregida.getNoFolioSolicitud());
                        enviarCorreoAsegurado(sol, documentoCertificado, tramite);
                        log.debug("---CDA--- Se envio el correo");
                    }

                    solicitudBusiness.actualizarXmlTramite(agregarIdDocumento(generarCertificacionUtil.obtenerTramitePrincipal(solicitudCorregida), idDocumento));
                } else {
                    log.warn("---CDA--- Ocurrio un error al subir el documento no regreso idDocumento");
                    throw new BovedaCDAException("Fallo el tiempo de respuesta de la boveda", "Ocurrio un error no fue posible subir el documento, por favor reintente");
                }
            }

            if (solicitudCorregida != null){
                sol = solicitudCorregida;
            }

            log.debug("---CDA-Estado solicitud: {} ", sol.getEstadoSolicitud().getIdEstadoSolicitud());

            if (sol.getEstadoSolicitud().getIdEstadoSolicitud() == 5 || sol.getEstadoSolicitud().getIdEstadoSolicitud().toString().equals("5")) {
            	log.debug("---CDA-Terminar tarea Certificacion INICIO ");
            	generarCertificacionBusiness.finalizaTramiteCorreccionDatosBasicosAsegurado(sol, generarCertificacionUtil.armarTareasTramites(requestCreateEvent),
                requestCreateEvent.getUserProfile().getUsuario(), tramite, nssCertificador);
            	log.debug("---CDA-Terminar tarea Certificacion FIN ");
            }

            // Se genera una nueva persona con asociada al NSS homonimo o invasivo
            correccionDatosBussines.registrarNuevaPersona(tramite.getTramiteId());

            return prepararRespuesta(null, documentoCertificado, requestCreateEvent);

        } catch (Exception e) {
            if(idDocumento != null) {
                try {
                    bovedaRemote.eliminarDocumento(idDocumento);
                } catch (BovedaCDAException ex) {
                    log.debug("---CDA--- Ocurrio un error al intentar eliminar el documento ", ex);
                }
            }
            return prepararRespuesta(e, null, requestCreateEvent);
        }
    }

    private byte[] recuperarCertificacion(Solicitud sol, TramiteCorreccionCurp tramite) {
        byte[] documentoCertificado = null;
        try {
            log.debug("---CDA--- Entra a recuperar documento");
            documentoCertificado = bovedaRemote.recuperarDocumento(sol,
                    TipoDocumentoCDAEnum.CERTIFICADO,
                    tramite.getIdDocumentoCertificacion());
            log.debug("---CDA--- Sale de recuperar documento {} ", documentoCertificado);
        } catch (BovedaCDAException bce) {
            log.error(bce.getSituacion(), bce);
        }

        return documentoCertificado;
    }

    private byte[] generarCertificacion(Solicitud sol, TramiteCorreccionCurp tramite, String nssCertificador) throws CorreccionDatosAseguradoException,
            NumberFormatException, CuentaIndividualNoDisponibleException {
        Set<List<PeriodoMovimientoAfiliatorio>> parametrosCuentas = new LinkedHashSet<List<PeriodoMovimientoAfiliatorio>>();
        log.debug("---CDA--- Entra por movimiento cuenta {} ", nssCertificador);
        parametrosCuentas.add(cuentaIndividualBusiness.consultarMovimientosCuentaIndividual(nssCertificador));

        log.debug("---CDA--- Entra por ultimo movimiento");
        parametrosCuentas.add(cuentaIndividualBusiness.consultarUltimoMovimientoCuentaIndividual(nssCertificador));

        log.debug("---CDA--- Entra a generar el sello {} ", parametrosCuentas.toString());
        FirmaElectronica firma = generarCertificacionBusiness.selloDigitalCertificacion(
                tramite.getPersonaRENAPO(), sol, parametrosCuentas, tramite);
        sol.setFirmaElectronica(firma);

        return manejadorReportesBusiness.ejecutaReporteCertificacion(generarCertificacionUtil.
                generarParametrosReporte(sol, tramite, nssCertificador, parametrosCuentas));
    }

    private String getNSSCertificador(List<DetalleNssCda> listaNss) {

        for (DetalleNssCda nss : listaNss) {
            if (nss.getIdTipoNss().equals(TipoNSSCorreccionEnum.CERTIFICADOR.getId())) {
                return nss.getNss();
            }
        }
        return null;
    }

    private String subirDocumento(Solicitud sol, byte[] documentoCertificado) throws IllegalArgumentException, BovedaCDAException {
        log.debug("---CDA--- Entra a subir el documento");
        return bovedaRemote.subirDocumento(documentoCertificado, sol, TipoDocumentoCDAEnum.CERTIFICADO);
    }

    private String getCorreoElectronico(Fisica persona) {
        String correoValido = "";

        if (persona.getMediosContacto() != null
                && !persona.getMediosContacto().isEmpty()) {
            for (MedioContacto med : persona.getMediosContacto()) {
                if (med instanceof CorreoElectronico) {
                    correoValido = ((CorreoElectronico) med).getCorreo();
                }

            }
        }
        return correoValido;
    }

    public void enviarCorreoAsegurado(Solicitud sol, byte[] certificacion, TramiteCorreccionCurp tramite) {
//        TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) sol
//                .getTramites().get(0);
        CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();
        String url = "http://" + URL_ENVIO_CORREO_SEGUIMIENTO_CIUDADANO;
        // Enviar correo electronico al asegurado
        String correoAsegurado = getCorreoElectronico(tramite
                .getPersonaRENAPO());
        log.debug("---CDA--- Correo del asegurado {} ", correoAsegurado);
        if (!correoAsegurado.isEmpty()) {
            correoElectronicoDTO.setCorreoPara(new String[1]);
            correoElectronicoDTO.getCorreoPara()[0] = correoAsegurado;
            correoElectronicoDTO
                    .setAsunto(StringEscapeUtils
                            .unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
            correoElectronicoDTO.setCuerpoCorreo(generarCertificacionUtil
                    .contenidoCorreoAtendida(sol, url, tramite));
            if (certificacion != null) {
                Map<String, byte[]> adjuntosMap = new HashMap<String, byte[]>();
                // TODO Cambiar nombre en la llave del mapa con el nombre del
                // documento que sera adjuntado.
                adjuntosMap.put("Certificado.pdf", certificacion);
                correoElectronicoDTO.setAdjuntos(adjuntosMap);
            }

            log.debug("CORREO CERTIFICACION....... {}",
                    correoElectronicoDTO.getCuerpoCorreo());
            try {
                log.debug("---CDA--- Intenta enviar correo");
                envioCorreoElectronicoBusinessRemote.enviarCorreo(
                        correoElectronicoDTO,
                        EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
            } catch (Exception e) {
                log.debug("---CDA--- Error al enviar correo al asegurado", e);
            }
        }
        // Termino de enviar correo
    }

    private TramiteCorreccionCurp agregarIdDocumento(TramiteCorreccionCurp tramiteCorreccionCurp,
            String idDocumento) {

        tramiteCorreccionCurp.setIdDocumentoCertificacion(idDocumento);

        return tramiteCorreccionCurp;

    }

    @SuppressWarnings("unchecked")
    private CreatedEvent<byte[]> prepararRespuesta(Exception e, byte[] documentoCertificado, CreateEvent<SeguimientoSolicitud> requestCreateEvent) {

        if (documentoCertificado != null && documentoCertificado.length != 0) {
            return new CreatedEvent<byte[]>(requestCreateEvent.getKey(),
                    documentoCertificado);
        }
        if (e instanceof BovedaCDAException) {
            BovedaCDAException bce = (BovedaCDAException) e;
            log.error("---CDA--- Error {} {}", bce.getSituacion(), bce);
            return CreatedEvent.error(requestCreateEvent.getKey(), bce.getMessage() + " " +  (bce.getCause() != null ? bce.getCause().toString() : "") ,
                    bce.getMensajeError());
        } else {
            log.error("---CDA---Error: ", e);
            return CreatedEvent.error(requestCreateEvent.getKey(), e.getCause() != null ? e.getCause().toString() : "" , e.getMessage());

        }

    }

}
