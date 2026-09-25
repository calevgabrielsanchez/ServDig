package mx.gob.imss.cit.cda.web.autorizar.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractUpdateController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.autorizar.vo.AutorizarSolicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AutorizarSolicitudController extends AbstractUpdateController<AutorizarSolicitud, AutorizarSolicitud> {

    @Autowired
    @Qualifier(BeansConstants.AUTORIZAR_SOLICITUD_HELPER)
    private UpdateHelper<AutorizarSolicitud, AutorizarSolicitud> service;

    @Override
    public UpdateHelper<AutorizarSolicitud, AutorizarSolicitud> getHelper() {
        return this.service;
    }

    @RequestMapping(RequestMappingConstants.AUTORIZAR_SOLICITUD)
    @ResponseBody
    @Override
    public ResponseEntity<AutorizarSolicitud> update(@RequestBody AutorizarSolicitud input, HttpServletRequest request) {
        return super.update(input, request);
    }

}
