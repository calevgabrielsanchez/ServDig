/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.confirmar.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractUpdateController;
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
 *El responsable confirma la solicitud corregida y la envia para que sea autorizada (Autorizador)
 * @author antonio
 */
@Controller
public class ConfirmarSolicitudController extends AbstractUpdateController<Solicitud, Solicitud> {

    @Autowired
    @Qualifier(BeansConstants.CONFIRMAR_SOLICITUD_HELPER)
    private UpdateHelper<Solicitud, Solicitud> service;

    @Override
    public UpdateHelper<Solicitud, Solicitud> getHelper() {
        return this.service;
    }

    @RequestMapping(RequestMappingConstants.RESPONSABLE_CONFIRMAR_SOLICITUD)
    @ResponseBody
    @Override
    public ResponseEntity<Solicitud> update(@RequestBody Solicitud input, HttpServletRequest request) {
        return super.update(input, request);
    }

}
