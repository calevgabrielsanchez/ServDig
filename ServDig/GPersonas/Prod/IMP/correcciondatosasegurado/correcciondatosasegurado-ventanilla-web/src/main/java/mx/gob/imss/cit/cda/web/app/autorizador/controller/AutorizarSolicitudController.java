/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.autorizador.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractUpdateController;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.AutorizarSolicitud;

@Controller
public class AutorizarSolicitudController extends AbstractUpdateController<AutorizarSolicitud,AutorizarSolicitud>{

  @Autowired
	@Qualifier(BeansConstants.AUTORIZAR_SOLICITUD_HELPER)
  UpdateHelper<AutorizarSolicitud, AutorizarSolicitud> service;
  
  @Override  
  public UpdateHelper<AutorizarSolicitud, AutorizarSolicitud> getHelper() {
    return this.service;
  }
  
  @RequestMapping(RequestMappingConstants.AUTORIZAR_SOLICITUD)
	@ResponseBody	
  public ResponseEntity<AutorizarSolicitud> update(@RequestBody AutorizarSolicitud input, HttpServletRequest request){
	    return super.update(input, request);
  }
  
}
