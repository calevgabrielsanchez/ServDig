package mx.gob.imss.cit.cda.web.reportes.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Scope("request")
public class ReportesController extends AbstractController {
    
    private final Logger logger = LoggerFactory
            .getLogger(ReportesController.class);

    @Autowired
    protected HttpSession httpSession;
    
    @RequestMapping(value = RequestMappingConstants.VIEW_REPORTES)
    public String inicio(HttpSession session) {
        UserProfile usuarioActivo = (UserProfile) httpSession.getAttribute(SessionConstants.USER_PROFILE);
        
        if(usuarioActivo!=null)
        {
            logger.debug("usuarioActivo no nulo ");
        }
        return "vistaReportes";
    }
    
    @RequestMapping(value = RequestMappingConstants.VIEW_DETALLE)
    public String consulaDetalle(HttpSession session) {
        UserProfile usuarioActivo = (UserProfile) httpSession.getAttribute(SessionConstants.USER_PROFILE);
        if(usuarioActivo!=null)
        {
            logger.debug("usuarioActivo no nulo");
        }
        
        return "vistaReportes";
    }


}