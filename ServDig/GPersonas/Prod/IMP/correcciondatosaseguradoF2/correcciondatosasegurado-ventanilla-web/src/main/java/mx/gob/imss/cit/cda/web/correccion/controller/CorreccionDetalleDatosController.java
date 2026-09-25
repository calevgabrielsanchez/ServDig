package mx.gob.imss.cit.cda.web.correccion.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.controller.AbstractReadController;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.controller.EditarDatosController;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CorreccionDatos;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ResumenCorrecion;

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
public class CorreccionDetalleDatosController extends   AbstractReadController<CorreccionDatos, ResumenCorrecion> {
    
    
    @Autowired
    @Qualifier(BeansConstants.READ_DETALLE_DATOS_HELPER)
    private ReadHelper<CorreccionDatos, ResumenCorrecion> helper;

    @Override
    public ReadHelper<CorreccionDatos, ResumenCorrecion> getHelper() {
        return helper;
    }
    

    private final Logger LOGGER = LoggerFactory
            .getLogger(EditarDatosController.class);

    
    @RequestMapping("obtenerDetalleOrigenFuentes.do")
    @ResponseBody
    public ResponseEntity<ResumenCorrecion> obtenerDetalleFuentes(@RequestBody CorreccionDatos input,
            HttpServletRequest request, HttpSession session) throws IOException {
        
        LOGGER.info("CDA-- Obteniendo detalle cambio de datos para la solicitud {}", input.getFolioSolicitud());
       
        return super.load(input, request);
        
       
    }
    

}
