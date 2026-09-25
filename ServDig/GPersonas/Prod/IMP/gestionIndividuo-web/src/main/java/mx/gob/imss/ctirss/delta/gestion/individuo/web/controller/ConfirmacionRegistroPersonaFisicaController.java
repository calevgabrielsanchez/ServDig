
package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Samuel Rodriguez Grajeda
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Controller
@RequestMapping(value = "/persona/fisica")
public class ConfirmacionRegistroPersonaFisicaController extends AbstractController {
	
	@RequestMapping(value="/registro/confirmacion", method=RequestMethod.POST)
    public String confirmarRegistroPersonaFisica(@ModelAttribute Fisica oForm, BindingResult result, Model model, SessionStatus status, HttpSession session) throws Exception {
		return "confirmacionRegistroPersonaFisica";
	}
	
}
