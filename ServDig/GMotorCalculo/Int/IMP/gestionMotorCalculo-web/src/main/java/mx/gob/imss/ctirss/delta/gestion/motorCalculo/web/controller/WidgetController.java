package mx.gob.imss.ctirss.delta.gestion.motorCalculo.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/widget")
public class WidgetController extends AbstractController {

	@RequestMapping(value = "/{idDummy}", method = RequestMethod.GET)
	public String initBeneficiosPersona(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idDummy) {

		model.addAttribute("idDummy", idDummy);

		return "widgetDummyInicio";
	}

	@RequestMapping(value = "/detalle/{idDummy}", method = RequestMethod.GET)
	public String detalleBeneficiosPersona(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idDummy) {

		String datosDummy = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In adipiscing nulla in lacus porttitor viverra. Donec tempus felis vitae dui consectetur, non commodo est placerat. Integer non eros est. Aliquam porttitor in orci sit amet tincidunt. Donec nec varius enim. Donec sit amet posuere velit. Phasellus commodo quam eu massa aliquam posuere. Quisque pharetra ipsum non urna porta, at dapibus mi pharetra. Sed auctor, arcu non consequat sodales, enim magna commodo sem, in ultricies ligula libero in dolor. Morbi gravida lacus id luctus hendrerit. Nam ac quam ullamcorper, imperdiet quam sed, vehicula risus. Sed ac risus a enim mollis pellentesque. In a metus suscipit, dapibus orci scelerisque, cursus dolor.";

		model.addAttribute("DATOS_DUMMY", datosDummy);

		return "widgetDummyContenido";
	}
}
