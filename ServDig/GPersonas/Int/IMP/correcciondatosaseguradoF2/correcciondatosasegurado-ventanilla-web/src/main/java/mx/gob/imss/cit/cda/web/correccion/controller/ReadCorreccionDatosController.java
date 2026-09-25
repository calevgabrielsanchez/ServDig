package mx.gob.imss.cit.cda.web.correccion.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;
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
public class ReadCorreccionDatosController extends AbstractReadController<Solicitud, CorreccionDatos> {

    

    @Autowired
    @Qualifier(BeansConstants.READ_CORRECION_DATOS_HELPER)
    private ReadHelper<Solicitud, CorreccionDatos> helper;

    @Override
    public ReadHelper<Solicitud, CorreccionDatos> getHelper() {
        return helper;
    }
    
 
    

    private final Logger LOGGER = LoggerFactory
            .getLogger(ReadCorreccionDatosController.class);

    
    @RequestMapping("obtenerOrigenFuentes.do")
    @ResponseBody
    public ResponseEntity<CorreccionDatos> obtenerOrigenFuentes(@RequestBody Solicitud input,
            HttpServletRequest request, HttpSession session) throws IOException {
        LOGGER.info("CDA-- Obteniendo fuentes de datos para la solicitud {}", input.getIdSolicitud());
       
        return super.load(input, request);
        
       
    }
    
}
