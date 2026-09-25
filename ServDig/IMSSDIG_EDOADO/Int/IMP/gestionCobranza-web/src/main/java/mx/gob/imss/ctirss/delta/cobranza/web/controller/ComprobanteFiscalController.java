package mx.gob.imss.ctirss.delta.cobranza.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.ComprobanteFiscalServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.Pago;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/edoadeudo/obtener/comprobanteFiscal")
public class ComprobanteFiscalController extends AbstractController {
	private static final Integer DOCE_MESES = 12;
	private static final Long ANIO_INICIAL_CONSULTA = 1997L;
    private static final Long ANIOS_DIFERENCIA = 6L;
	
	@Autowired
	private ComprobanteFiscalServiceRemote comprobanteFiscalService;

	@RequestMapping(value = "/ingresar-datos-test", method = RequestMethod.GET)
	public String ingresarDatosTest(final Model model) {
		Pago pagoFiscal = new Pago();
		model.addAttribute("pagoFiscal", pagoFiscal);

		Map<String, String> listMesPeriodo = new LinkedHashMap<String, String>();

		for (Integer index = 1; index < 10; index++) {
			StringBuffer sb = new StringBuffer();
			sb.append("0").append(index);
			listMesPeriodo.put(sb.toString(), sb.toString());
		}
		for (Integer index = 10; index <= DOCE_MESES; index++) {
			listMesPeriodo.put(index.toString(), index.toString());
		}
		model.addAttribute("listMesPeriodo", listMesPeriodo);

		Long anioActual = obtenerAnioFechaActual();
		Integer anioInicial = (int) (anioActual + ANIOS_DIFERENCIA);
		
		Map<String, String> listAnioPeriodo = new LinkedHashMap<String, String>();

		for (Integer index = anioInicial; index >= ANIO_INICIAL_CONSULTA; index--) {
			//Long anioPeriodo = anioActual + index;
			listAnioPeriodo.put(index.toString(), index.toString());
		}
		model.addAttribute("listAnioPeriodo", listAnioPeriodo);

		return "ingresarDatosPatronTest";
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

	@RequestMapping(value = "/porPeriodo", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String obtenerComprobanteFiscalPorPeriodo(
			@ModelAttribute Pago pagoFiscal, BindingResult result, Model model,
			HttpSession session) {
		log.debug("NRP " + pagoFiscal.getNumeroRegistroPatronal());
		log.debug("RFC " + pagoFiscal.getRfc());
		log.debug("Periodo " + pagoFiscal.getPeriodo());

		try {
			List<Pago> listPagos = comprobanteFiscalService
					.obtenerPagosporPeriodo(pagoFiscal);
			model.addAttribute("listPagos", listPagos);
		} catch (Exception e) {
			model.addAttribute("errorFormGeneral", e.getMessage());
		}

		return "obtenerListaComprobanteFiscal";
	}
	
	
	@RequestMapping(value = "/descargarComprobante", method = RequestMethod.POST)
	public String descargarComprobanteFiscal(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute Pago pagoFiscal, Model model, HttpSession session) {
		Calendar cal = Calendar.getInstance();
		Date fechaActual = cal.getTime();

		Double importe = Double.valueOf(request.getParameter("importe"));
        log.debug("Importe: " + importe);
        pagoFiscal.setTotal(importe);
		try {
			String xmlPago = comprobanteFiscalService.getCadenaComprobanteFiscal(pagoFiscal);
			pagoFiscal.setXmlComprobante(xmlPago);

			if (StringUtils.isNotBlank(xmlPago)) {
				comprobanteFiscalService.crearSolicitudDescargaCompFiscal(
						pagoFiscal, fechaActual, TipoDescargaArchivo.XML);
			} else {
				xmlPago = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			}

			model.addAttribute("fileContent", xmlPago);

			if (StringUtils.isNotBlank(pagoFiscal.getRfc())) {
				model.addAttribute("customizedFileName",
						"CFDI_" + pagoFiscal.getRfc());
			}
		} catch (EstadoAdeudoException e) {
			e.printStackTrace();
		}

		return "comprobanteFiscalXmlFormat";
	}
	
	@RequestMapping(value = "/descargarFacturaElectronica", method = RequestMethod.POST)
	public void descargarFacturaElectronica(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute Pago pagoFiscal, Model model, HttpSession session) {
		Calendar cal = Calendar.getInstance();
		Date fechaActual = cal.getTime();
        Double importe = Double.valueOf(request.getParameter("importe"));
        log.debug("Importe: " + importe);
        pagoFiscal.setTotal(importe);
		try {
			Map<String, Object> mapaFacturaElectronica = comprobanteFiscalService
					.descargarFacturaElectronica(pagoFiscal);
			if ((mapaFacturaElectronica != null)
					&& (((byte[]) mapaFacturaElectronica
							.get("facturaElectronica")) != null)) {
				comprobanteFiscalService.crearSolicitudDescargaCompFiscal(
						pagoFiscal, fechaActual, TipoDescargaArchivo.PDF);
				publicarDocumento(response, mapaFacturaElectronica, pagoFiscal.getRfc());
			} else {
				log.info("Factura Electrónica NO Generada.");
			}
		} catch (EstadoAdeudoException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody
	Solicitud limpiarSesion(final HttpSession session) {
		return null;
	}

	private void publicarDocumento(HttpServletResponse response, Map<String, Object> mapaFacturaElectronica, String rfc){
		try {
			String filename;
			if (StringUtils.isNotBlank(rfc)) {
				filename = "CFDI_" + rfc;
			} else {
				filename = ((String) mapaFacturaElectronica.get("nombreFacturaElectronica"));
			}

			byte[] archivo = (byte[]) mapaFacturaElectronica.get("facturaElectronica");

			if (archivo != null) {
				response.addHeader("Accept-Ranges", "bytes");
				response.addHeader("Cache-Control", "public");
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.addHeader("expires", "0");
				response.addHeader("Content-disposition", "inline;filename=" + filename);
				response.setContentLength(archivo.length);
				response.getOutputStream().write(archivo);
				response.getOutputStream().close();
				response.flushBuffer();
				log.info("Factura Electrónica Generada.");
			} else {
				this.log.debug("No hay documento");
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
