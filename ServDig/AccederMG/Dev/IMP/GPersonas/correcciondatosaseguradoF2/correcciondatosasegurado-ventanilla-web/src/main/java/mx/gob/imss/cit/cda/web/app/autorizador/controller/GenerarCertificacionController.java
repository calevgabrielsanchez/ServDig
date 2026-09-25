package mx.gob.imss.cit.cda.web.app.autorizador.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.cit.cda.core.events.CreateEvent;
import mx.gob.imss.cit.cda.core.events.CreatedEvent;
import mx.gob.imss.cit.cda.core.helper.CreateHelper;
import mx.gob.imss.cit.cda.web.app.autorizador.utils.SeguimientoSolicitudEditor;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractCreateController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("request")
public class GenerarCertificacionController extends
        AbstractCreateController<SeguimientoSolicitud, byte[]> {

    @Autowired
    @Qualifier(BeansConstants.GENERAR_CERTIFICACION_HELPER)
    private CreateHelper<SeguimientoSolicitud, byte[]> service;

    private final Logger log = LoggerFactory
            .getLogger(GenerarCertificacionController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        logInitBinder(binder);
        binder.registerCustomEditor(SeguimientoSolicitud.class,
                new SeguimientoSolicitudEditor());
    }

    private void logInitBinder(WebDataBinder binder) {
        log.debug("Creando DataBinder---" + binder);
    }

    private void logSegumientoSolicitud(SeguimientoSolicitud input,
            HttpServletRequest request, HttpServletResponse response) {
        log.debug("CDA---" + request.getContentLength());
        log.debug("CDA---" + request.getContentType());
        log.debug("CDA---" + request.getContextPath());
        log.debug("SeguimientoSolicitud [idSolicitud=" + input.getIdSolicitud()
                + ", folio=" + input.getFolio() + ", resumen="
                + input.getResumen() + ", detalle=" + input.getDetalle()
                + ", idTarea=" + input.getIdTarea() + ", idTramite="
                + input.getIdTramite() + ", informacionRENAPO="
                + input.getInformacionRENAPO() + ", fechaInicio="
                + input.getFechaInicio() + ", subDelegacion="
                + input.getSubDelegacion() + ", responsable="
                + input.getResponsable() + ", curpResponsable="
                + input.getCurpResponsable() + ", nss=" + input.getNss() + "]");
        if(response!=null)
        {
            log.debug("response no null");
        }
    }

    @RequestMapping(RequestMappingConstants.GENERAR_CERTIFICACION)
    public Object load(
            @RequestParam("seguimientoSolicitud") SeguimientoSolicitud input,
            HttpServletRequest request, HttpServletResponse response) {

        logSegumientoSolicitud(input, request, response);

        log.debug("---CDA--- Generando Certificacion Id Solicitud  {}",
                input.getIdSolicitud());
        CreatedEvent<byte[]> createEvent = getHelper().requestEvent(
                new CreateEvent<SeguimientoSolicitud>(UUID.randomUUID(), input,
                        getUserProfile(request)));

        try {

            if (createEvent.isEntityCreated() && createEvent.getData() != null) {
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + input.getFolio() + ".pdf");
                response.setContentLength((int) createEvent.getData().length);
                response.setContentType("application/pdf");
                response.getOutputStream().write(createEvent.getData());

                return null;
            } else {
                Map<String, Object> errores = new HashMap<String, Object>();
                errores.put("errorBoveda", createEvent.getMensajeExcepcion());
                errores.put("errorNegocio", createEvent.getMensajeNegocio());
                return new ModelAndView(
                        RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE,
                        errores);
            }
        } catch (IOException ioe) {
            Map<String, Object> errores = new HashMap<String, Object>();
            errores.put("errorBoveda", ioe.getMessage());
            errores.put(
                    "errorNegocio",
                    MensajesBovedaCDAEnum
                            .obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002
                                    .getCodigo()));
            return new ModelAndView(
                    RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, errores);
        }
    }

    @Override
    public CreateHelper<SeguimientoSolicitud, byte[]> getHelper() {
        return this.service;
    }

}
