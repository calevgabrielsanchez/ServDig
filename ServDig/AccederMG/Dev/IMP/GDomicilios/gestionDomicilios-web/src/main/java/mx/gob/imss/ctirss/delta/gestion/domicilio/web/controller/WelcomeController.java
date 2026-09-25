/**
 * WelcomeController.java
 * @package mx.gob.imss.gestionDomicilios.web.controller
 * @project gestionDomicilios-web	
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Controller
public class WelcomeController extends AbstractController {
	
	
	@RequestMapping(value = "/welcome")
    public String home() {
        return "welcome";
    }


}
