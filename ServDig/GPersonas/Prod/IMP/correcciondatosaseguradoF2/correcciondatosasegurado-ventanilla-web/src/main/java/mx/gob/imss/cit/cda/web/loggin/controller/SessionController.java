package mx.gob.imss.cit.cda.web.loggin.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.web.app.common.model.InformacionSesion;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/session/*")
public class SessionController extends SessionBaseController {

    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SessionController.class);
   

    @RequestMapping(value = "/validar/session", method = RequestMethod.POST)
    @ResponseBody
    public void validarDatosConsulta(Model model,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        LOGGER.debug("Verificando la session del usuario");
        try {

            InformacionSesion info_sesion = null;
            info_sesion = (InformacionSesion) session
                    .getAttribute(SessionConstants.INFORMACION_SESION);
            if (info_sesion != null) {
                info_sesion.setValidaAvisoSession(false);
                session.removeAttribute(SessionConstants.INFORMACION_SESION);
                session.setAttribute(SessionConstants.INFORMACION_SESION,
                        info_sesion);
            }
        } catch (Exception e) {
            LOGGER.error("CDA- Ocurrio un error validando session usuario", e);
        }

    }

   
  
}
