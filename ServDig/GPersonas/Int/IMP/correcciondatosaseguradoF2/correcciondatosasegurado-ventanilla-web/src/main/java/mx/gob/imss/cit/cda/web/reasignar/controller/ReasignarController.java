package mx.gob.imss.cit.cda.web.reasignar.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractUpdateController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.ReasignacionSolicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReasignarController extends
        AbstractUpdateController<ReasignacionSolicitud, ReasignacionSolicitud> {

    @Autowired
    @Qualifier(BeansConstants.REASIGNAR_RESPONSABLE_HELPER)
    private UpdateHelper<ReasignacionSolicitud, ReasignacionSolicitud> service;

    @Override
    public UpdateHelper<ReasignacionSolicitud, ReasignacionSolicitud> getHelper() {
        return this.service;
    }

    @RequestMapping(RequestMappingConstants.UPDATE_REASIGNAR_RESPONSABLE)
    @ResponseBody
    @Override
    public ResponseEntity<ReasignacionSolicitud> update(
            @RequestBody ReasignacionSolicitud input, HttpServletRequest request) {
        return super.update(input, request);
    }

}
