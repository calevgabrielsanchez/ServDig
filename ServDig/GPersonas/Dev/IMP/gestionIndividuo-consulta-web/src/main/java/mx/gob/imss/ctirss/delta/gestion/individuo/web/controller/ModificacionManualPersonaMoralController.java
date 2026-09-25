package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesModificacionException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaMoralMDMValidator;
import mx.gob.imss.ctirss.delta.model.enums.ErroresModificacionPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Marco S�nchez
 * 
 */

@Controller
@RequestMapping(value = "/persona/moral/modificacion-manual")
public class ModificacionManualPersonaMoralController extends
		AbstractController {

	@EJB
	private PersonaMoralBusinessRemote personaMoralBusiness;

	@RequestMapping(value = "/initModManualMoralTest", method = RequestMethod.GET)
	public String ingresarDatosTest(final Model model) {

		MDMDatosEntrada mdmDatosEntrada = new MDMDatosEntrada();

		Moral moral = new Moral();
		moral.setIdPersona(4L);
		mdmDatosEntrada.setPersonaMoral(moral);

		mdmDatosEntrada.setIndCapturaRFC(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaDomicilioFiscal(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaMediosContactoFiscales(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaRazonSocial(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaFechaConstitucion(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaTipoSociedad(Boolean.TRUE);
		
		mdmDatosEntrada.setIndCapturaActaConstitutiva(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaRegistroSindicato(Boolean.TRUE);
		
		mdmDatosEntrada.setIndAutorizacion(Boolean.TRUE);
		
		model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
		model.addAttribute("tramite", new TramiteCambioInformacionPersona());

		return "initModManualMoralTest";
	}

	@RequestMapping(value = "/initModManualMoral", method = RequestMethod.GET)
	public String initModManual(final Model model) {

		model.addAttribute("mdmDatosEntrada", new MDMDatosEntrada());

		return "initModManualMoral";
	}

	@RequestMapping(value = "/capturar", method = RequestMethod.POST)
	public String capturaModificacionManual(
			@ModelAttribute MDMDatosEntrada mdmDatosEntrada,
			final BindingResult result, final Model model,
			final HttpSession session) {

		log.debug("ID PERSONA -> "
				+ mdmDatosEntrada.getPersonaMoral().getIdPersona());

		try {
			mdmDatosEntrada = this.personaMoralBusiness
					.modificacionManual(mdmDatosEntrada);

		} catch (DatosInsuficientesModificacionException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());

			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DATOS_INSUFICIENTES_MDM
					.getCodigo(), e.getMessage());
			mdmDatosEntrada.setTraza(mensajes);
			
		} catch (PersonaNoEncontradaException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			mdmDatosEntrada.setTraza(mensajes);
		}

		model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
		return "capturarModifManualMoral";
	}

	@RequestMapping(value = "/procesarCaptura", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> procesarCapturaManual(
			@RequestBody MDMDatosEntrada mdmDatosEntrada,
			final HttpSession session, HttpServletResponse response) {
		log.debug(":::En ModificacionManualPersonaMoralController.procesarCapturaManual");
		Map<String, Object> result = new HashMap<String, Object>();
			
		if(mdmDatosEntrada.getPersonaMoral() != null){
			
			log.debug("ID PERSONA -> "
					+ mdmDatosEntrada.getPersonaMoral().getIdPersona());

			final Errors errors = new BindException(
					mdmDatosEntrada.getPersonaMoral(), "model");
	
			new PersonaMoralMDMValidator().validate(mdmDatosEntrada, errors);
	
			if (errors.hasErrors()) {
				procesaErroresDeCaptura(errors, result, response);
	
				return result;
			}
			
			mdmDatosEntrada = this.personaMoralBusiness.procesarModificacionManual(mdmDatosEntrada);
		}

		result.put("mdmDatosEntrada", mdmDatosEntrada);

		return result;
	}
}
