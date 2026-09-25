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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author antonio
 */

public abstract class AbstractController {

    @Autowired
    protected HttpSession httpSession;


    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public UserProfile getUserProfile(HttpServletRequest request) {
        return (UserProfile) request.getSession().getAttribute(
                SessionConstants.USER_PROFILE);
    }

}
