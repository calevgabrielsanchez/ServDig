/**
 * WelcomeController.java
 * @package mx.gob.imss.portal.web.controller
 * @project portal-web	
 */
package mx.gob.imss.ctirss.delta.portal.web.controller;

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
