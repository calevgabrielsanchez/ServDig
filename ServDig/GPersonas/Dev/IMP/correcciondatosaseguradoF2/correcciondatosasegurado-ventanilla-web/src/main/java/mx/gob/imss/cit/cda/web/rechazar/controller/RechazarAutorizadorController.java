package mx.gob.imss.cit.cda.web.rechazar.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractUpdateController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RechazarAutorizadorController extends AbstractUpdateController<SeguimientoSolicitud, SeguimientoSolicitud> {

    @Autowired
    @Qualifier(BeansConstants.RECHAZAR_SOLICITUD_HELPER)
    private UpdateHelper<SeguimientoSolicitud, SeguimientoSolicitud> service;

    @Override
    public UpdateHelper<SeguimientoSolicitud, SeguimientoSolicitud> getHelper() {
        return this.service;
    }

    @RequestMapping(RequestMappingConstants.RECHAZAR_SOLICITUD)
    @ResponseBody
    @Override
    public ResponseEntity<SeguimientoSolicitud> update(@RequestBody SeguimientoSolicitud input, HttpServletRequest request) {
        return super.update(input, request);
    }
}
