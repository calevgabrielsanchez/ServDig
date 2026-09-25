package mx.gob.imss.ctirss.delta.cobranza.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.ComprobanteFiscalServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.Pago;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "wizard/comprobanteFiscal")
public class WizardComprobanteFiscalController extends AbstractController {
	private static final Integer DOCE_MESES = 12;
	private static final Long ANIO_INICIAL_CONSULTA = 1997L;
	private static final Long ANIOS_DIFERENCIA = 6L;

	@Autowired
	private ComprobanteFiscalServiceRemote comprobanteFiscalService;

	@RequestMapping(value = "/{nrp}/{rfc}", method = RequestMethod.GET)
	public String initModificarDatosPersona(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable String nrp,
			@PathVariable String rfc) {
		Pago pagoFiscal = new Pago();
		pagoFiscal.setRfc(rfc);
		pagoFiscal.setNumeroRegistroPatronal(nrp);
		model.addAttribute("pagoFiscal", pagoFiscal);

		return "wizardComprobanteFiscalInit";
	}

	@RequestMapping(value = "/busqueda/comprobanteFiscal", method = RequestMethod.POST)
	public String obtenerComprobanteFiscalPorPeriodo(
			@ModelAttribute Pago pagoFiscal, BindingResult result, Model model,
			HttpSession session) {

		try {
			List<Pago> listPagos = comprobanteFiscalService
					.obtenerPagosporPeriodo(pagoFiscal);
			model.addAttribute("listPagos", listPagos);
		} catch (Exception e) {
			model.addAttribute("errorFormGeneral", e.getMessage());
		}

		return "wizardComprobanteFiscalContenido";
	}

	@ModelAttribute("listMesPeriodo")
	public Map<String, String> populateMesPeriodoList() {
		Map<String, String> listMesPeriodo = new LinkedHashMap<String, String>();

		for (Integer index = 1; index < 10; index++) {
			StringBuffer sb = new StringBuffer();
			sb.append("0").append(index);
			listMesPeriodo.put(sb.toString(), sb.toString());
		}
		for (Integer index = 10; index <= DOCE_MESES; index++) {
			listMesPeriodo.put(index.toString(), index.toString());
		}

		return listMesPeriodo; 
	}

	@ModelAttribute("listAnioPeriodo")
	public Map<String, String> populateAnioPeriodoList() {
		Long anioActual = obtenerAnioFechaActual();
		Integer anioInicial = (int) (anioActual + ANIOS_DIFERENCIA);
		
		Map<String, String> listAnioPeriodo = new LinkedHashMap<String, String>();

		for (Integer index = anioInicial; index >= ANIO_INICIAL_CONSULTA; index--) {
			//Long anioPeriodo = anioActual + index;
			listAnioPeriodo.put(index.toString(), index.toString());
		}

		return listAnioPeriodo;
	}

	private Long obtenerAnioFechaActual() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		Calendar calendario = Calendar.getInstance();
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		Date fechaActual = calendario.getTime();

		Long anio = new Long(0);
		if (fechaActual != null) {
			String _str = sdf.format(fechaActual);
			anio = Long.parseLong(_str);
		}

		return anio;
	}
}
