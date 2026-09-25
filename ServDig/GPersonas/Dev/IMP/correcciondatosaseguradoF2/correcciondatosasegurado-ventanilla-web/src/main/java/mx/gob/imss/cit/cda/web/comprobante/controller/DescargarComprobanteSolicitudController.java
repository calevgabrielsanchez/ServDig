package mx.gob.imss.cit.cda.web.comprobante.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.cit.cda.core.events.CreateEvent;
import mx.gob.imss.cit.cda.core.events.CreatedEvent;
import mx.gob.imss.cit.cda.core.helper.CreateHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractCreateController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
*
* @author mon
*/
@Controller
@Scope("request")
public class DescargarComprobanteSolicitudController  extends AbstractCreateController<String, byte[]> {
    
    private final Logger log = LoggerFactory.getLogger(DescargarComprobanteSolicitudController.class);
    
    
    @Autowired
    @Qualifier(BeansConstants.DESCARGAR_COMPROBANTE_HELPER)
    private CreateHelper<String, byte[]> service;

    @Override
    public CreateHelper<String, byte[]> getHelper() {
        return this.service;
    }
    
    @RequestMapping(RequestMappingConstants.DESCARGAR_COMPROBANTE_SOLICITUD)
    public Object load( @RequestParam("documentoSolicitud") String input,HttpServletRequest request, HttpServletResponse response) {

        CreatedEvent<byte[]> createEvent = getHelper().requestEvent(
                new CreateEvent<String>(UUID.randomUUID(), input,
                        getUserProfile(request)));

        try {
            log.debug("Inicia Descarga de Comprobante de Solicitud");

            if (createEvent.isEntityCreated() && createEvent.getData() != null) {
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + input + ".pdf");
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
    
}
