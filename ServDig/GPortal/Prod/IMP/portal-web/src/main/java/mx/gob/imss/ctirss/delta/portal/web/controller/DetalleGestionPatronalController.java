package mx.gob.imss.ctirss.delta.portal.web.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/portal/patronal")
public class DetalleGestionPatronalController extends AbstractController {
	@Autowired
	SujetoObligadoServiceBusinessRemote sujetoObligadoService;

	@RequestMapping(method = RequestMethod.GET)
	public String inicio(Model model, HttpSession session,
			HttpServletRequest request) {
		return "testPatronal";
	}

	@RequestMapping(value = "/obtenerDetallePatron", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> obtenerDetallePatronPorRFC(@RequestBody String rfc,
			HttpServletResponse response, HttpSession session, Locale locale) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (StringUtils.isBlank(rfc)) {
				throw new GestionPatronalBusinessException("dato.rfc.requerido");
			}

			SujetoObligado sujetoObligado = sujetoObligadoService.obtenerDetallePorRFC(rfc);
			sujetoObligado.setSujetosObligados(null);
			result.put("sujetoObligado", sujetoObligado);
			return result;
		} catch (GestionPatronalBusinessException e) {
			String message = messageSource.getMessage(e.getMessage(), null, locale);
			GestionPatronalBusinessException exception = new GestionPatronalBusinessException(message);

			log.error(exception);
			this.procesarErrorDeNegocio(exception, result, response);
			return result;
		}
	
	}
}
