/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.consulta.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author antonio
 */
@Controller
public class ReadSolicitudController
        extends AbstractReadController<Solicitud, Solicitud> {

    @Autowired
    @Qualifier(BeansConstants.READ_SOLICITUD_HELPER)
    private ReadHelper<Solicitud, Solicitud> service;

    @Override
    public ReadHelper<Solicitud, Solicitud> getHelper() {
        return service;
    }

    @RequestMapping(RequestMappingConstants.READ_SOLICITUD)
    @ResponseBody
    @Override
    public ResponseEntity<Solicitud> load(@RequestBody Solicitud input,
            HttpServletRequest request) {
        
        ResponseEntity<Solicitud> solicitud = super.load(input, request);
        
        if( solicitud != null && solicitud.getBody() != null){
          request.setAttribute("renapoSolicitud",solicitud.getBody().getInformacionRENAPO());
        }
        
        return solicitud;
    }

}
