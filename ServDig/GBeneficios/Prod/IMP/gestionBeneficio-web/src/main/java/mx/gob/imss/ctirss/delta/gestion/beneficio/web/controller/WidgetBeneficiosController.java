package mx.gob.imss.ctirss.delta.gestion.beneficio.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/widget/beneficios")
public class WidgetBeneficiosController extends AbstractController {

	private static final int TIPO_REL_BENEF_PERSONA = 1;
	private static final int TIPO_REL_BENEF_SUJ_OBLIG = 2;
	
	private static final String WIDGET_BENEFICIO_INICIO = "widgetBeneficiosInit";
	private static final String WIDGET_BENEFICIO_DETALLE = "widgetBeneficiosContenido";
	private static final String KEY_ATRIBUTE_ERROR = "error";
	
	@Autowired
	private BeneficioRissServiceBusinessRemote beneficioRissServiceBusiness;

	@RequestMapping(value = "/persona/{idPersona}", method = RequestMethod.GET)
	public String initBeneficiosPersona(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersona) {

		Fisica fisica = new Fisica();
		fisica.setIdPersona(idPersona);
		model.addAttribute("persona", fisica);
		request.setAttribute("tipoRelacionBeneficio", TIPO_REL_BENEF_PERSONA);
		return WIDGET_BENEFICIO_INICIO;
	}

	@RequestMapping(value = "/persona/detalle/{idPersona}", method = RequestMethod.GET)
	public String detalleBeneficiosPersona(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersona) {
		List<Beneficio> beneficios =  beneficioRissServiceBusiness
			.obtenerBeneficiosPorIdPersona(idPersona, false);
		request.setAttribute("LISTA_BENEFICIOS", beneficios);
		
		return WIDGET_BENEFICIO_DETALLE;
	}
	
	@RequestMapping(value = "/patsujoblig/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String initBeneficiosSujetoObligado(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable String numeroRegistroPatronal) {
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(numeroRegistroPatronal);
		model.addAttribute("sujetoObligado", sujetoObligado);
		request.setAttribute("tipoRelacionBeneficio", TIPO_REL_BENEF_SUJ_OBLIG);
		return WIDGET_BENEFICIO_INICIO;
	}

	@RequestMapping(value = "/patsujoblig/detalle/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String detalleBeneficiosSujetoObligado(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable String numeroRegistroPatronal) {		
		try {			
			List<Beneficio> beneficios =  beneficioRissServiceBusiness
				.obtenerBeneficiosPorNRP(numeroRegistroPatronal, false);
			request.setAttribute("LISTA_BENEFICIOS", beneficios);
		} catch (BeneficioRissException e) {
			this.log.error(e);
			request.setAttribute(KEY_ATRIBUTE_ERROR, e.getMessage());
		}
		return WIDGET_BENEFICIO_DETALLE;
	}

	
}
