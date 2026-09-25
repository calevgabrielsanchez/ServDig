package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/widget/domicilio/utility")
public class WidgetDomicilioUtilityController extends AbstractController {

	@Autowired
	DomicilioServiceBusinessRemote domicilioServiceBusiness;

	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String initRegistroUsuario(Model model, HttpSession session,
			HttpServletRequest request) {

		String view = "widgetUmfCodigoPostalInit";
		List<UnidadMedicaFamiliar> lstUmf = new ArrayList<UnidadMedicaFamiliar>();
		model.addAttribute("lstUmf", lstUmf);
		return view;
	}

	/*
	 * metodo con la firma para recivir solo el CP esta activo para realizar
	 * pruebas desde la url y no por forma
	 */

	@RequestMapping(value = "/umf/{codigoPostal}", method = RequestMethod.GET)
	public String initUmfWidget(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable String codigoPostal) {

		String msgError = null;

		
		List<UnidadMedicaFamiliar> lstUmf = new ArrayList<UnidadMedicaFamiliar>();
		try {
			lstUmf = this.domicilioServiceBusiness
					.getUmfByCodigoPostal(codigoPostal);

		} catch (UmfNoLocalizadaException ex) {
			this.log.error("no se encontr\u00F3 ninguna umF para el asentamiento");
			msgError = "No se encontr\u00F3 ninguna umf para el c\u00F3digo postal";
		} catch (Exception e) {
			this.log.error("error no especificado ", e);
			msgError = "No se encontr\u00F3 ninguna umf para el c\u00F3digo postal";
		}

		model.addAttribute("lstUmf", lstUmf);
		model.addAttribute("msgError", msgError);
		this.log.debug("antes del return");
		return "widgetUmfCodigoPostalInit";

	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping(value = "/consulta/umf/asentamiento", method = RequestMethod.POST)
	public String initUmfWidget(Model model, HttpSession session,
			HttpServletRequest request,
			@ModelAttribute Asentamiento asentamiento) {

		this.log.debug("Se van a obtener las UMF a traves del asentamiento");

		String msgError = null;
		List<UnidadMedicaFamiliar> lstUmf = null;

		if (asentamiento.getCodigoPostal() != null
				&& StringUtils.isNotBlank(asentamiento.getCodigoPostal()
						.getCodigoPostal())) {
			try {
				lstUmf = this.domicilioServiceBusiness
						.getUmfByCodigoPostal(asentamiento.getCodigoPostal()
								.getCodigoPostal());
			} catch (UmfNoLocalizadaException ex) {
				this.log.error("no se encontr\u00F3 ninguna UMF para el asentamiento");
				msgError = "No se encontr\u00F3 ninguna UMF para el c\u00F3digo postal";
			} catch (Exception e) {
				this.log.error("Error no especificado ", e);
				msgError = "Ocurri\u00F3 un error inesperado al consultar las UMF ";
			}
		} else {
			msgError = "Para mostrar la lista de UMF es necesario contar con un domicilio particular.";
		}

		model.addAttribute("lstUmf", lstUmf);
		model.addAttribute("msgError", msgError);

		return "widgetUmfCodigoPostalInit";

	}

	@RequestMapping(value = "/consulta/municipioImss/centroTrabajo", method = RequestMethod.POST)
	public String initMunicipioImssWidget(Model model, HttpSession session,
			HttpServletRequest request,
			@ModelAttribute CentroTrabajo centroTrabajo) {
		String msgError = null;

		log.debug("--------------------------------ZCentro de Trabajo: " + centroTrabajo);
		List<MunicipioIMSS> listMunicipioImss = new ArrayList<MunicipioIMSS>();
		try {
			Municipio objMunicipio = (centroTrabajo.getAsentamiento()!= null 
				&& centroTrabajo.getAsentamiento().getLocalidad()!=null) 
				? centroTrabajo.getAsentamiento().getLocalidad().getMunicipio() : null;
			
			listMunicipioImss = domicilioServiceBusiness.getMunicipioIMSSbyEstadoMunCP(
					objMunicipio, centroTrabajo.getCodigoPostal().getCodigoPostal());
		} catch (MunicipioImssNoLocalizadoException e) {
			this.log.error("error no especificado ", e);
			msgError = e.getMessage();
		}

		model.addAttribute("lstMunicipioImss", listMunicipioImss);
		model.addAttribute("msgError", msgError);

		return "widgetMunicipioImssAsentamientoInit";
	}
	
	@RequestMapping(value = "/consulta/municipioImss", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> initMunicipioImssWidgetREST(Model model, HttpSession session,
			HttpServletRequest request,
			@RequestBody CentroTrabajo centroTrabajo) {
		Map<String, Object> result = new HashMap<String, Object>();
		String msgError = null;

		log.debug("--------------------------------ZCentro de Trabajo: " + centroTrabajo);
		List<MunicipioIMSS> listMunicipioImss = new ArrayList<MunicipioIMSS>();
		try {
			Municipio objMunicipio = (centroTrabajo.getAsentamiento()!= null 
				&& centroTrabajo.getAsentamiento().getLocalidad()!=null) 
				? centroTrabajo.getAsentamiento().getLocalidad().getMunicipio() : null;
			
			listMunicipioImss = domicilioServiceBusiness.getMunicipioIMSSbyEstadoMunCP(
					objMunicipio, centroTrabajo.getCodigoPostal().getCodigoPostal());
		} catch (MunicipioImssNoLocalizadoException e) {
			this.log.error("error no especificado ", e);
			msgError = e.getMessage();
		}

		result.put("municipios", listMunicipioImss);
		result.put("error", msgError);

		return result;
	}
}