/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.common.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

/**
 *
 * @author antonio
 */
@Controller
public class ReadUserProfileController extends AbstractController {

  @RequestMapping(RequestMappingConstants.READ_USER_PROFILE)
	@ResponseBody	  
  public ResponseEntity<UserProfile> load(HttpServletRequest request){
    return new ResponseEntity<UserProfile>( getUserProfile(request) ,HttpStatus.OK);
  }
}
