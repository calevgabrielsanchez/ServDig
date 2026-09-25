/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ComplementarCalificacionPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.ConsultaPersonaFisicaValidator;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaFisicaValidator;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Candidato;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Lucio Duran Silva
 *
 */
@Controller
@RequestMapping(value="/persona/fisica/ubicar")
@SessionAttributes(value={"fisica"})
public class ConsultaPersonaFisicaController extends AbstractController {

	private static final String KEY_DATOS_ENTRADA = "KEY_DATOS_ENTRADA_PERSONA_FISICA";
	
	private static final String KEY_TIPO_BUSQUEDA ="keyTipoBusqueda";
	
	private static final String KEY_TIPO_BUSQUEDA_SAT ="BUSQUEDA_CON_SAT";
	
	private static final String KEY_TIPO_BUSQUEDA_RENAPO ="BUSQUEDA_CON_RENAPO";
	
	@Autowired
	ConsultaPersonaFisicaServiceBusinessRemote consultaPersonaFisicaServiceBusiness;
	
	@Autowired
	ComplementarCalificacionPersonaFisicaServiceBusinessRemote complementarCalificacionPersonaFisicaServiceBusiness;
	
	@Autowired
	LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote localizarPersonaFisicaEnEntidadesExternasServiceBusiness;
	
	@Autowired
	PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	
	@RequestMapping( value="/renapo" ,method=RequestMethod.GET)
	public String iniciar(Model model,  HttpSession session){
		model.addAttribute("fisica", new Fisica());
		session.setAttribute(KEY_TIPO_BUSQUEDA, KEY_TIPO_BUSQUEDA_RENAPO);
		return "consulta.personafisica";
	}
	
	
	@RequestMapping( value="/sat" ,method=RequestMethod.GET)
	public String iniciarSat(Model model,  HttpSession session){
		model.addAttribute("fisica", new Fisica());
		session.setAttribute(KEY_TIPO_BUSQUEDA, KEY_TIPO_BUSQUEDA_SAT);
		return "consulta.personafisica";
	}
	
	@RequestMapping(value="/regresar", method=RequestMethod.GET)
	public String regresoAlInicio(Model model,  HttpSession session){
		
		// Se borra el objeto de la sesión
		session.removeAttribute("datosRespuestSession");
		
		return "consulta.personafisica";
	}
	
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public String consultar( @ModelAttribute Fisica fisica , BindingResult result , Model model, HttpSession session) {
			
		this.log.debug(" datos a consultar de la persona fisica [" + fisica +"]");
		session.removeAttribute(KEY_DATOS_ENTRADA);
		this.log.warn("Se borraron los parametros de entrada ");
		
		
		String keyTipoBusqueda = (String)session.getAttribute(KEY_TIPO_BUSQUEDA);
		
		if(keyTipoBusqueda.equals(KEY_TIPO_BUSQUEDA_RENAPO)){
			new ConsultaPersonaFisicaValidator().validateRenapo(fisica, result);
		}else{
			new ConsultaPersonaFisicaValidator().validateSat(fisica, result);
		}
		
		
		if(result.hasErrors()){
			return "consulta.personafisica";
		}
		
		List<Candidato> candidatos = null;
		try {
			candidatos = this.consultaPersonaFisicaServiceBusiness.consultarPersonaFisica(fisica);
			
			//ordenarCandidatos(candidatos);
			
		} catch (PersonasNoLocalizadasException e) {
			
			this.log.warn(e.getMessage());
			
			TramiteFisica tramite = new TramiteFisica();
			Fisica fisicaLocalizadaRENAPO = null;
			
			try {
				this.log.warn("Iniciando el flujo de localizacion de persona en las entidades externas"
						+ this.localizarPersonaFisicaEnEntidadesExternasServiceBusiness);
				fisicaLocalizadaRENAPO = localizarPersonaFisicaEnEntidadesExternasServiceBusiness
						.localizarPersonaFisicaEnEntidadesExternas(fisica);
			
				tramite.setFisica(fisicaLocalizadaRENAPO);
				model.addAttribute("fisica", fisicaLocalizadaRENAPO);
				// Este objeto es el que realmente se regresará a la gestión
				session.setAttribute("datosRespuestSession", tramite);
				
			}catch(NullPointerException np){
				e.printStackTrace();
				this.log.error(e);
				
			} catch (ErrorComparacionDatosRENAPOException e1) {
				this.log.error(e1);
				model.addAttribute("mensajeException" , e1.getMessage());
				model.addAttribute("warning" , Boolean.TRUE);
				model.addAttribute("fisica", fisica);
				return "consulta.personafisica";
				
			} catch (ErrorComparacionDatosSATException e2) {
				this.log.error(e2);
				model.addAttribute("mensajeException" , e2.getMessage());
				model.addAttribute("warning" , Boolean.TRUE);
				model.addAttribute("fisica", fisica);
				return "consulta.personafisica";
			} catch (CURPNoLocalizadoEnEntidadExternaException e3) {
				this.log.error(e3);
				model.addAttribute("mensajeException" , e3.getMessage());
				model.addAttribute("warning" , Boolean.TRUE);
				model.addAttribute("fisica", fisica);
				
				/*
				 * Se agrega la calificación IMSS ya que no se pudo localizar en
				 * las entidades externas
				 */
				agregarCalificacionIMSS(fisica);
				
				tramite.setFisica(fisica);
				model.addAttribute("fisica", fisica);
				session.setAttribute("datosRespuestSession", tramite);
				
				/*
				 * Redireccionamos a la pantalla de registro por complementar
				 * el cual requiere de autorizacion
				 */
				return "consulta.detallecandidatocomplementar";
			} catch (RFCNoLocalizadoEnEntidadExternaException e4) {
				this.log.error(e4);
				model.addAttribute("mensajeException" , e4.getMessage());
				model.addAttribute("warning" , Boolean.TRUE);
				model.addAttribute("fisica", fisica);
				
				/*
				 * Se agrega la calificación IMSS ya que no se pudo localizar en
				 * las entidades externas
				 */
				agregarCalificacionIMSS(fisica);
				
				tramite.setFisica(fisica);
				model.addAttribute("fisica", fisica);
				session.setAttribute("datosRespuestSession", tramite);
				
				/*
				 * Redireccionamos a la pantalla de registro por complementar
				 * el cual requiere de autorizacion
				 */
				return "consulta.detallecandidatocomplementar";
			} catch (ClienteWebserviceRenapoCurpException e1) {
				this.log.error(e1);
				model.addAttribute("mensajeException" , e1.getMessage());
				model.addAttribute("warning" , Boolean.TRUE);
				model.addAttribute("fisica", fisica);
				
				/*
				 * Se agrega la calificación IMSS ya que no se pudo localizar en
				 * las entidades externas
				 */
				agregarCalificacionIMSS(fisica);
				
				tramite.setFisica(fisica);
				model.addAttribute("fisica", fisica);
				session.setAttribute("datosRespuestSession", tramite);
				
				/*
				 * Redireccionamos a la pantalla de registro por complementar
				 * el cual requiere de autorizacion
				 */
				return "consulta.detallecandidatocomplementar";
			} catch (ClienteWebserviceSatRfcException e1) {
				this.log.error(e1);
				model.addAttribute("mensajeException" , e1.getMessage());
				model.addAttribute("warning" , Boolean.TRUE);
				model.addAttribute("fisica", fisica);
				
				/*
				 * Se agrega la calificación IMSS ya que no se pudo localizar en
				 * las entidades externas
				 */
				agregarCalificacionIMSS(fisica);
				
				tramite.setFisica(fisica);
				model.addAttribute("fisica", fisica);
				session.setAttribute("datosRespuestSession", tramite);
				
				/*
				 * Redireccionamos a la pantalla de registro por complementar
				 * el cual requiere de autorizacion
				 */
				return "consulta.detallecandidatocomplementar";
			}
			
			return "consulta.detallecandidato";
			
		}
		
		model.addAttribute("candidatos" , candidatos);
		
		/*
		 * Subimos a la sesion el objeto de la persona fisica
		 * consultada.
		 *  
		 */
		session.setAttribute(KEY_DATOS_ENTRADA, fisica);
		
		
		return "consulta.candidatos";
	}
	
	@RequestMapping(value="/complementar" , method=RequestMethod.POST)
	public String  complementar( @ModelAttribute Fisica fisica  , BindingResult result , Model model, HttpSession session) {
		this.log.debug(" datos a complementar de la persona fisica [" + fisica +"]");
		
		//Recuperamos los datos de entrada que se encuentran en la sesion
		Fisica entrada = (Fisica)session.getAttribute(KEY_DATOS_ENTRADA);
		
		TramiteFisica tramite = new TramiteFisica();
		ICADatosRespuesta datosRespuesta = null;
		
		/*
		 * Se inicia el proceso de complementacion de califiaciones de lapersona candidato
		 */
		try {
			
//			fisica = this.complementarCalificacionPersonaFisicaServiceBusiness.complementarCalificaciones(fisica, entrada);
			
			ICADatosConsulta datosConsulta = new ICADatosConsulta();
			datosConsulta.setPersonaFisica(fisica);
			datosConsulta.setIndicadorConsultaRENAPO(Boolean.TRUE);
			datosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
			datosConsulta.setIndicadorMostrarPantalla(Boolean.FALSE);
		
			datosRespuesta = this.personaFisicaServiceBusiness.identificarCambios(datosConsulta);
			
			datosRespuesta = this.personaFisicaServiceBusiness.integrarCambios(datosRespuesta);
			
			/*
			 * Se settea nula la entidad externa para que en el JSON solo se
			 * tenga la información de la entidad IMSS con los cambios
			 * integrados
			 */
			datosRespuesta.setPersonaFisicaEE(null);
			
			this.log.debug(" PERSONA FISICA COMPLEMENTADA " + datosRespuesta.getPersonaFisicaIMSS());
			
			tramite.setDatosICA(datosRespuesta);
			
			model.addAttribute("fisica", datosRespuesta.getPersonaFisicaIMSS());
			
			// Este objeto es el que realmente se regresará a la gestión
			session.setAttribute("datosRespuestSession", tramite);
			
		} catch (ErrorComparacionDatosRENAPOException e) {
			datosRespuesta = new ICADatosRespuesta();
			datosRespuesta.setPersonaFisicaIMSS(entrada);
			tramite.setDatosICA(datosRespuesta);
			model.addAttribute("datosRespuesta", tramite);
			model.addAttribute("fisica", entrada);
			this.log.error(e);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			
			return "consulta.personafisica";
		} catch (ClienteWebserviceSatRfcException e) {
			this.log.error(e);
			datosRespuesta = new ICADatosRespuesta();
			datosRespuesta.setPersonaFisicaIMSS(entrada);
			tramite.setDatosICA(datosRespuesta);
			model.addAttribute("datosRespuesta", tramite);
			model.addAttribute("fisica", entrada);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);

			session.setAttribute("datosRespuestSession", tramite);
			
			return "consulta.detallecandidatocomplementar";
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			this.log.error(e + "fisica ==" +  fisica +"==");
			datosRespuesta = new ICADatosRespuesta();
			datosRespuesta.setPersonaFisicaIMSS(entrada);
			tramite.setDatosICA(datosRespuesta);
			model.addAttribute("datosRespuesta", tramite);
			model.addAttribute("fisica", entrada);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			/*
			 * Redireccionamos a la pantalla de registro por complementar
			 * el cual requiere de autorizacion
			 */
			session.setAttribute("datosRespuestSession", tramite);
			
			return "consulta.detallecandidatocomplementar";
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			this.log.error(e);
			datosRespuesta = new ICADatosRespuesta();
			datosRespuesta.setPersonaFisicaIMSS(entrada);
			tramite.setDatosICA(datosRespuesta);
			model.addAttribute("datosRespuesta", tramite);
			model.addAttribute("fisica", entrada);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			/*
			 * Redireccionamos a la pantalla de registro por complementar
			 * el cual requiere de autorizacion
			 */
			session.setAttribute("datosRespuestSession", tramite);
			
			return "consulta.detallecandidatocomplementar";
		} catch (ClienteWebserviceRenapoCurpException e) {
			this.log.error(e);
			datosRespuesta = new ICADatosRespuesta();
			datosRespuesta.setPersonaFisicaIMSS(entrada);
			tramite.setDatosICA(datosRespuesta);
			model.addAttribute("datosRespuesta", tramite);
			model.addAttribute("fisica", entrada);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			/*
			 * Redireccionamos a la pantalla de registro por complementar
			 * el cual requiere de autorizacion
			 */
			session.setAttribute("datosRespuestSession", tramite);
			
			return "consulta.detallecandidatocomplementar";
		} catch (PersonaNoEncontradaException e) {
			datosRespuesta = new ICADatosRespuesta();
			datosRespuesta.setPersonaFisicaIMSS(entrada);
			tramite.setDatosICA(datosRespuesta);
			model.addAttribute("datosRespuesta", tramite);
			model.addAttribute("fisica", entrada);
			this.log.error(e);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			return "consulta.personafisica";
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			datosRespuesta = new ICADatosRespuesta();
			datosRespuesta.setPersonaFisicaIMSS(entrada);
			tramite.setDatosICA(datosRespuesta);
			model.addAttribute("datosRespuesta", tramite);
			model.addAttribute("fisica", entrada);
			this.log.error(e);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			return "consulta.personafisica";
		} catch (ComparacionSinDiferenciasException e) {
			datosRespuesta = new ICADatosRespuesta();
			datosRespuesta.setPersonaFisicaIMSS(entrada);
			tramite.setDatosICA(datosRespuesta);
			model.addAttribute("datosRespuesta", tramite);
			model.addAttribute("fisica", entrada);
			this.log.error(e);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			
			session.setAttribute("datosRespuestSession", tramite);
			
			return "consulta.detallecandidatocomplementar";
		} catch (DatosInsuficientesICAException e) {
			datosRespuesta = new ICADatosRespuesta();
			datosRespuesta.setPersonaFisicaIMSS(entrada);
			tramite.setDatosICA(datosRespuesta);
			model.addAttribute("datosRespuesta", tramite);
			model.addAttribute("fisica", entrada);
			this.log.error(e);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			return "consulta.personafisica";
		} catch (DiferenciasRENAPOContraSAT e) {
			datosRespuesta = new ICADatosRespuesta();
			datosRespuesta.setPersonaFisicaIMSS(entrada);
			tramite.setDatosICA(datosRespuesta);
			model.addAttribute("datosRespuesta", tramite);
			model.addAttribute("fisica", entrada);
			this.log.error(e);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			return "consulta.personafisica";
		} catch (PersonaFisicaNoEncontradaException e) {
			datosRespuesta = new ICADatosRespuesta();
			datosRespuesta.setPersonaFisicaIMSS(entrada);
			tramite.setDatosICA(datosRespuesta);
			model.addAttribute("datosRespuesta", tramite);
			model.addAttribute("fisica", entrada);
			this.log.error(e);
			model.addAttribute("mensajeException" , e.getMessage());
			model.addAttribute("warning" , Boolean.TRUE);
			return "consulta.personafisica";
		}
		
		return "consulta.detallecandidato";
	}
	
	@RequestMapping(value="/validar" , method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object>  agregar(@RequestBody Fisica oForm ,  HttpServletResponse response ){
    	
    	Map result = new HashMap < String , Object>();
    	Errors errors = new BindException(oForm, "model");
    	
    	new PersonaFisicaValidator().validate(oForm, errors);
		
    	if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
    	
    	result.put("oForm", oForm);

    	return result;
	}
	
	/**
	 * Método que obtiene el objeto de respuesta, que contiene la información de
	 * la persona complementada
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/obtener-objeto-respuesta", method = RequestMethod.POST)
	public @ResponseBody TramiteFisica obtenerObjetoRespuesta (HttpSession session) {
		
		TramiteFisica datosRespuesta = (TramiteFisica) session.getAttribute("datosRespuestSession");
		
		if(datosRespuesta == null) {
			this.log.warn("El objeto de repuesta de la búsqueda de persona es nulo!");
		}
		
		return datosRespuesta;
	}
	
	/**
	 * Método que borra de la sesión el objeto de respuesta, que contiene la
	 * información de la persona complementada
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/limpiar-objeto-respuesta", method = RequestMethod.POST)
	public @ResponseBody Fisica limpiarObjetoRespuesta (HttpSession session) {
		
		session.removeAttribute("datosRespuestSession");
		
		return null;
	}
    
	/**
	 * Método auxiliar para agregar la calificación IMSS a una persona
	 * 
	 * @param fisica
	 */
	private void agregarCalificacionIMSS (Fisica fisica) {
		
		Calificacion calificacion = new Calificacion();
		calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_IMSS
				.getCodigo().longValue());
		calificacion.setDescripcion(CalificacionEnum.VALIDADO_IMSS.getDescripcion());
		
		PersonaCalificacion personaCalificacion = new PersonaCalificacion();
		personaCalificacion.setCalificacion(calificacion);
		personaCalificacion.setFechaCalificacion(new Date());
		
		// Se checa si tiene algun otra calificación, de ser así, se quitan
		if (!fisica.getPersonaCalificaciones().isEmpty()) {
			fisica.getPersonaCalificaciones().clear();
		}
		
		fisica.getPersonaCalificaciones().add(personaCalificacion);
	}
}
