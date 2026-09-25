/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Joaquin Ponte
 * @since 09/03/2012
 * 
 */
@Controller
@RequestMapping(value = "/")
public class PaginaInicialPersonasController extends AbstractController {

    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String haciaElOrquestador(final Model model) {
        model.addAttribute("parametro", "ninguno");
        model.addAttribute("titulo", "Proyecto DELTA");
        return "PaginaInicialPersonas";
    }
}