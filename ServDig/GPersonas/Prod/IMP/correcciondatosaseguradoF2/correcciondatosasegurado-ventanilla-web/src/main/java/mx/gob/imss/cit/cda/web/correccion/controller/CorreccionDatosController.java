package mx.gob.imss.cit.cda.web.correccion.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CorreccionDatos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CorreccionDatosController extends   AbstractReadController<CorreccionDatos, CorreccionDatos> {
    
    
    @Autowired
    @Qualifier(BeansConstants.CORRECION_DATOS_HELPER)
    private ReadHelper<CorreccionDatos, CorreccionDatos> helper;

    @Override
    public ReadHelper<CorreccionDatos, CorreccionDatos> getHelper() {
        return helper;
    }
    
 
    
   
    private final Logger LOGGER = LoggerFactory
            .getLogger(CorreccionDatosController.class);

    
    @RequestMapping("guardarCorrecion.do")
    @ResponseBody
    public ResponseEntity<CorreccionDatos> obtenerOrigenFuentes(@RequestBody CorreccionDatos input,
            HttpServletRequest request, HttpSession session) throws IOException {
        LOGGER.info("CDA-- Guardando correcciones de datos en XML..");
       
        UserProfile user = (UserProfile) session
                .getAttribute(SessionConstants.USER_PROFILE);
        
        input.setUsuarioCorreccion(user.getUsuario());
        
        return super.load(input, request);
        
       
    }
    


}
