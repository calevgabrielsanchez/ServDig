/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.ConsultaPersonaFisicaValidator;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaFisicaCURPValidator;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaFisicaDatosBasicosValidator;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaFisicaRFCValidator;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Candidato;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lucio Duran Silva
 * 
 */
@Controller
@RequestMapping(value = "/servicios/internos/persona/fisica")
public class RESTConsultaPersonaFisicaController extends AbstractController {

	
	
	private static final String KEY_MAP_CANDIDATOS = "KEY_CANDIDATOS";
	private static final String KEY_DATOS_ENTRADA = "KEY_DATOS_ENTRADA_PERSONA_FISICA";
	private static final String KEY_PERSONA_RENAPO = "KEY_PERSONA_RENAPO";
	private static final String KEY_PERSONA_SAT = "KEY_PERSONA_SAT";
	private static final String KEY_LIST_CANDIDATOS = "KEY_LIST_CANDIDATOS";
	
	
	@Autowired
	ConsultaPersonaFisicaServiceBusinessRemote consultaPersonaFisicaServiceBusiness;

	

	@Autowired
	LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote localizarPersonaFisicaEnEntidadesExternasServiceBusiness;

	
	@Autowired
	ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	
	
	@RequestMapping( method=RequestMethod.GET)
	public String iniciar(Model model,  HttpSession session){
		model.addAttribute("fisica", new Fisica());
		
		
		/*
		 * Limpiamos el objeto de la sesion
		 */
		
		session.removeAttribute(KEY_MAP_CANDIDATOS);
		Map<Long, Candidato> mapCandidatos = new HashMap<Long, Candidato>();
		session.setAttribute(KEY_MAP_CANDIDATOS, mapCandidatos);
		
		session.removeAttribute(KEY_DATOS_ENTRADA);
		session.removeAttribute(KEY_PERSONA_RENAPO);
		session.removeAttribute(KEY_PERSONA_SAT);
		
		return "consulta.restpf";
	}
	
	
	
	
	/**
	 * 
	 * @param oForm
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/consultar/validar/datos", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarDatosConsulta(@RequestBody Fisica oForm,
			HttpServletResponse response, HttpSession session) {

		
		this.log.debug(" valida los datos de la consulta " + oForm);
		
		Map result = new HashMap<String, Object>();
		Errors errors = new BindException(oForm, "model");

		
		
		
		new ConsultaPersonaFisicaValidator().validate(oForm, errors);
		if( errors.hasErrors()){
			result.put("bValidacion" , Boolean.FALSE);
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		result.put("bValidacion" , Boolean.TRUE);
		session.setAttribute(KEY_DATOS_ENTRADA, oForm);

		return result;
	}
	
	
	/**
	 * 
	 * @param oForm
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/consultar/imss/curp", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> consultarCURPIMSS(@RequestBody Fisica oForm,
			HttpServletResponse response, HttpSession session) {

		
		this.log.debug(" paso de consulta de persona fisica en el IMSS por curp" + oForm);
		
		Map result = new HashMap<String, Object>();
		Errors errors = new BindException(oForm, "model");

		
		
		
		new PersonaFisicaCURPValidator().validate(oForm, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		
		
		// 1. Consultamos en el IMSS por CURP
		this.log.debug("Iniciando la consulta por CURP en el IMSS" + oForm.getCurp());
		
		List<Candidato> candidatos =  this.consultaPersonaFisicaServiceBusiness.consultarPorCURPEnIMSS(oForm);
		
		if(!candidatos.isEmpty()){
			this.log.debug(" Numero de registros encontrados :" +candidatos.size() );
			/*
			 * Agregamos la lista de candidatos a la sesion.
			 */
			addCandidato(candidatos, session);
			
			result.put("candidatos", candidatos);
			result.put("bCandidatos", Boolean.TRUE);
		}else{
			this.log.warn("No se encontraron personas con el CURP en el IMSS");
			result.put("bCandidatos", Boolean.FALSE);
		}
		
		

		return result;
	}
	
	/**
	 * 
	 * @param oForm
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/consultar/imss/rfc", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> consultarRFCIMSS(@RequestBody Fisica oForm,
			HttpServletResponse response, HttpSession session) {

		
		this.log.debug(" paso de consulta de persona fisica en el IMSS por rfc" + oForm);
		
		Map result = new HashMap<String, Object>();
		Errors errors = new BindException(oForm, "model");

		new PersonaFisicaRFCValidator().validate(oForm, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		// 1. Consultamos en el IMSS por RFC
		this.log.debug("Iniciando la consulta por RFC en el IMSS" + oForm.getRfc());
		
		List<Candidato> candidatos =  this.consultaPersonaFisicaServiceBusiness.consultarPorRFCEnIMSS(oForm);
		
		if(!candidatos.isEmpty()){
			this.log.debug(" Numero de registros encontrados :" +candidatos.size() );
			/*
			 * Agregamos la lista de candidatos a la sesion.
			 */
			addCandidato(candidatos, session);
			
			result.put("candidatos", candidatos);
			result.put("bCandidatos", Boolean.TRUE);
		}else{
			this.log.warn("No se encontraron personas con el RFC en el IMSS");
			result.put("bCandidatos", Boolean.FALSE);
		}
		
		

		return result;
	}

	/**
	 * 
	 * @param oForm
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/consultar/imss/datosbasicos", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> consultarDatosBasicosIMSS(@RequestBody Fisica oForm,
			HttpServletResponse response, HttpSession session) {

		
		this.log.debug(" paso de consulta de persona fisica en el IMSS por datos basicos" + oForm);
		
		Map result = new HashMap<String, Object>();
		Errors errors = new BindException(oForm, "model");

		new PersonaFisicaDatosBasicosValidator().validate(oForm, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		// 1. Consultamos en el IMSS por RFC
		this.log.debug("Iniciando la consulta por RFC en el IMSS" + oForm.getRfc());
		
		
		//dUMMY
		
		Fisica fdummy = new Fisica();
		fdummy.setNombre(oForm.getNombre());
		fdummy.setPrimerApellido(oForm.getPrimerApellido());
		fdummy.setSegundoApellido(oForm.getSegundoApellido());
		
		List<Candidato> candidatos =  this.consultaPersonaFisicaServiceBusiness.consultarPorDatosBasicosEnIMSS(fdummy);
		
		if(!candidatos.isEmpty()){
			this.log.debug(" Numero de registros encontrados :" +candidatos.size() );
			/*
			 * Agregamos la lista de candidatos a la sesion.
			 */
			addCandidato(candidatos, session);
			
			result.put("candidatos", candidatos);
			result.put("bCandidatos", Boolean.TRUE);
		}else{
			this.log.warn("No se encontraron personas con el RFC en el IMSS");
			result.put("bCandidatos", Boolean.FALSE);
		}
		
		

		return result;
	}
	
	
	
	
	@RequestMapping(value = "/validar/transicion/siguiente", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarTransicionSiguiente(@RequestBody Fisica oForm,
			HttpServletResponse response, HttpSession session) {

		
		this.log.debug(" validando si es posible realizar la transicion a la siguiente pantalla ..." + oForm);
		
		Map result = new HashMap<String, Object>();

		// 1. Debemos de validar si es posible seguir a la siguiente pantalla...
		
		
		// Propiedad para indicar que el flujo puede realizar la transicion
		result.put("bseguir", Boolean.TRUE);
		// Mensaje de la operacion
		result.put("smessage", "");
		//Numero de candidatos encontrados.
		result.put("inumero", new Integer(0));
		
		
		
		// recuperamos el mapa de la session.
		
		Map<Long, Candidato> mapCandidatos = (HashMap<Long, Candidato>)session.getAttribute(KEY_MAP_CANDIDATOS);
		
		if(mapCandidatos == null){
			
			this.log.error("El mapa de los candidatos es nulo...");
			PersonasNoLocalizadasException exception = new PersonasNoLocalizadasException();
			this.procesarErrorDeNegocio(exception, result, response);
			return result;
		}
		
		
		List candidatos = new LinkedList<Candidato>();
		for (Map.Entry<Long, Candidato> entry : mapCandidatos.entrySet()) {
			candidatos.add(entry.getValue());
		}
		
		if(candidatos.isEmpty()){
			this.log.error("La lista  de los candidatos es nulo...");
			PersonasNoLocalizadasException exception = new PersonasNoLocalizadasException();
			this.procesarErrorDeNegocio(exception, result, response);
			return result;
		}
		
		result.put("inumero", new Integer(candidatos.size()));

		// Subimos la lista a la session...
		
		session.setAttribute(KEY_LIST_CANDIDATOS, candidatos);
		

		return result;
	}

	
	/**
	 * 
	 * @param candidato
	 * @param session
	 */
	private void addCandidato(List<Candidato> candidatos, HttpSession session){
		
		//1. Recuperamos el mapa de la sesion
		
		Map<Long, Candidato> mapCandidatos = (Map<Long, Candidato>) session.getAttribute(KEY_MAP_CANDIDATOS);
		
		
		//2. Recorremos la lista de candidatos, para agregarlos al mapa...
		
		for( Candidato c : candidatos){
			this.log.debug("Candidatos resultados de busquedas::" + c.getPersona().getIdPersona());
			Candidato cf = mapCandidatos.get(c.getPersona().getIdPersona());
			
			if(cf == null){
				this.log.debug("El candidato no existe en el mapa , lo agregamos." + c.getPersona().getIdPersona());
				mapCandidatos.put(c.getPersona().getIdPersona(), c);
			}else{
				this.log.debug("El candidato ya existe en el mapa, sumamos probabilidades:" + cf.getProbabilidad());
				Long p = cf.getProbabilidad();
				p = p + c.getProbabilidad();
				cf.setProbabilidad(p);
				this.log.debug("% De probabilidad del candidato " + p + "-"+ cf.getPersona().getIdPersona());
			}
			
		}
		
		session.setAttribute(KEY_MAP_CANDIDATOS, mapCandidatos);
	}
	
	
	
	@RequestMapping(value = "/consultar/renapo/curp", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> consultarEnRenapo(@RequestBody Fisica oForm,
			HttpServletResponse response, HttpSession session) throws ClienteWebserviceRenapoCurpException{

		
		this.log.debug(" paso de consulta de persona fisica en el RENAPO por curp" + oForm.getCurp());
		
		Map result = new HashMap<String, Object>();
		Errors errors = new BindException(oForm, "model");

		new PersonaFisicaCURPValidator().validate(oForm, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		result.put("oPersona" , new Fisica());
		
		try {
			
			Fisica fisica = this.localizarPersonaFisicaEnEntidadesExternasServiceBusiness
					.localizarPersonaFisicaEnRENAPO(oForm);
			
			session.setAttribute(KEY_PERSONA_RENAPO, fisica);
			result.put("oPersona", fisica);
			
		} catch (ErrorComparacionDatosRENAPOException e) {
			this.procesarErrorDeNegocio(e, result, response);
			return result;
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			this.procesarErrorDeNegocio(e, result, response);
			return result;
		}
		
		

		return result;
	}
	
	
	@RequestMapping(value = "/consultar/sat/rfc", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> consultarEnSat(@RequestBody Fisica oForm,
			HttpServletResponse response, HttpSession session) {

		
		this.log.debug(" paso de consulta de persona fisica en el SAT por rfc" + oForm.getRfc());
		
		Map result = new HashMap<String, Object>();
		Errors errors = new BindException(oForm, "model");

		new PersonaFisicaRFCValidator().validate(oForm, errors);
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		result.put("oPersona" , new Fisica());
		
		try {
			
			Fisica fisica = this.localizarPersonaFisicaEnEntidadesExternasServiceBusiness
					.localizarPersonaFisicaEnSAT(oForm);
			
			session.setAttribute(KEY_PERSONA_SAT, fisica);
			result.put("oPersona", fisica);
			
		} catch (ErrorComparacionDatosSATException e) {
			this.procesarErrorDeNegocio(e, result, response);
			return result;
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			this.procesarErrorDeNegocio(e, result, response);
			return result;
		} catch (ClienteWebserviceSatRfcException e) {
			this.procesarErrorDeNegocio(e, result, response);
			return result;
		}
		
		

		return result;
	}
	
	
	@RequestMapping(value = "/{idPersona}", method = RequestMethod.GET)
	public String getDatosPersonaFisica(HttpServletRequest request,@PathVariable Long idPersona) {
			
			Fisica persona; 
			try {
				persona = (Fisica)this.serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(idPersona);
				request.setAttribute("persona", persona);
			} catch (PersonaFisicaNoEncontradaException e) {
				
				request.setAttribute("error", e.getMessage());
			}
			
		
		return "personaResumen";
	}
	
	
	
}
