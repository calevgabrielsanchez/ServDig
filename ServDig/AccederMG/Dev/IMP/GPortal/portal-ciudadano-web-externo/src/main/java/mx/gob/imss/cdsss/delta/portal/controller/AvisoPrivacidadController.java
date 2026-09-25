/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portalExpediente
 *  @Archivo:HomeController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.portal.expediente.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.cdsss.delta.portal.controller;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/avisoPrivacidad")
public class AvisoPrivacidadController extends AbstractController {

	private static final String AVISO_PRIVACIDAD = "avisoPrivacidad";

	@RequestMapping(method = RequestMethod.GET)
	public String init(){
		return AVISO_PRIVACIDAD;
	}
}
