package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesModificacionException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaFisicaMDMValidator;
import mx.gob.imss.ctirss.delta.model.enums.ErroresModificacionPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;

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
 * @author Marco Sánchez
 * 
 */

@Controller
@RequestMapping(value = "/persona/fisica/modificacion-manual")
public class ModificacionManualPersonaFisicaController extends
		AbstractController {

	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;

	@RequestMapping(value = "/initModManualFisicaTest", method = RequestMethod.GET)
	public String ingresarDatosTest(final Model model) {

		MDMDatosEntrada mdmDatosEntrada = new MDMDatosEntrada();

		Fisica fisica = new Fisica();
		fisica.setIdPersona(7250163L);
		mdmDatosEntrada.setPersonaFisica(fisica);

		mdmDatosEntrada.setIndCapturaDatosRENAPO(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaNombre(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaCURP(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaSexo(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaFechaNacimiento(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaLugarNacimiento(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaDocumentoProbatorio(Boolean.TRUE);
		
		mdmDatosEntrada.setIndCapturaDatosSAT(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaRFC(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaDomicilioFiscal(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaMediosContactoFiscales(Boolean.TRUE);
		
		mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaDomicilioParticular(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaMediosContactoParticular(Boolean.TRUE);
		
		mdmDatosEntrada.setIndAutorizacion(Boolean.TRUE);

		model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
		model.addAttribute("tramite", new TramiteCambioInformacionPersona());

		return "initModManualFisicaTest";
	}

	@RequestMapping(value = "/initModManualFisica", method = RequestMethod.GET)
	public String initModManual(final Model model) {

		model.addAttribute("mdmDatosEntrada", new MDMDatosEntrada());

		return "initModManualFisica";
	}

	@RequestMapping(value = "/capturar", method = RequestMethod.POST)
	public String capturaModificacionManual(
			@ModelAttribute MDMDatosEntrada mdmDatosEntrada,
			final BindingResult result, final Model model,
			final HttpSession session) {

		log.debug("ID PERSONA -> "
				+ mdmDatosEntrada.getPersonaFisica().getIdPersona());

		try {
			mdmDatosEntrada = this.personaFisicaServiceBusiness
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
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_FISICA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			mdmDatosEntrada.setTraza(mensajes);
		}

		model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
		return "capturarModifManualFisica";
	}

	@RequestMapping(value = "/procesarCaptura", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> procesarCapturaManual(
			@RequestBody MDMDatosEntrada mdmDatosEntrada,
			final HttpSession session, HttpServletResponse response) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(mdmDatosEntrada.getPersonaFisica() != null){
			log.debug("ID PERSONA -> "
					+ mdmDatosEntrada.getPersonaFisica().getIdPersona());
		
			final Errors errors = new BindException(
					mdmDatosEntrada.getPersonaFisica(), "model");
	
			new PersonaFisicaMDMValidator().validate(mdmDatosEntrada, errors);
	
			if (errors.hasErrors()) {
				procesaErroresDeCaptura(errors, result, response);
	
				return result;
			}
			
			try {
				mdmDatosEntrada = this.personaFisicaServiceBusiness.procesarModificacionManual(mdmDatosEntrada);
			} catch (ErrorComparacionDatosRENAPOException e) {
				log.error(e);
				mdmDatosEntrada = new MDMDatosEntrada();
				mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
				this.procesarErrorDeNegocio(e, result, response);
			} catch (PersonaFisicaNoEncontradaException e) {
				log.error(e);
				mdmDatosEntrada = new MDMDatosEntrada();
				mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
				this.procesarErrorDeNegocio(e, result, response);
			}
		}

		result.put("mdmDatosEntrada", mdmDatosEntrada);

		return result;
	}
}
