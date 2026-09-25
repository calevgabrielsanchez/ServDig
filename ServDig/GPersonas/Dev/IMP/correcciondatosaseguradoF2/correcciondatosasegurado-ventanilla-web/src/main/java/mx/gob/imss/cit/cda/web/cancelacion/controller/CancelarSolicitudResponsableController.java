/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.cancelacion.controller;

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

/**
 *
 * @author antonio
 */
@Controller
public class CancelarSolicitudResponsableController extends AbstractUpdateController<SeguimientoSolicitud, SeguimientoSolicitud> {

    @Autowired
    @Qualifier(BeansConstants.CANCELAR_SOLICITUD_HELPER)
    private UpdateHelper<SeguimientoSolicitud, SeguimientoSolicitud> service;

    @Override
    public UpdateHelper<SeguimientoSolicitud, SeguimientoSolicitud> getHelper() {
        return this.service;
    }

    @RequestMapping(RequestMappingConstants.RESPONSABLE_CANCELAR_SOLICITUD)
    @ResponseBody
    @Override
    public ResponseEntity<SeguimientoSolicitud> update(@RequestBody SeguimientoSolicitud input,HttpServletRequest request) {
        return super.update(input, request);
    }

}
