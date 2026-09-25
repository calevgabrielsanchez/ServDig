/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author antonio
 */

public abstract class AbstractController {
	
	@Autowired
	protected HttpSession httpSession;
	
	@Autowired
	private DomicilioServiceBusinessRemote domicilioService;
	
	@Autowired
	@Qualifier("componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  public UserProfile getUserProfile(HttpServletRequest request){
	  return (UserProfile) request.getSession().getAttribute(SessionConstants.USER_PROFILE);
  }
    
}
