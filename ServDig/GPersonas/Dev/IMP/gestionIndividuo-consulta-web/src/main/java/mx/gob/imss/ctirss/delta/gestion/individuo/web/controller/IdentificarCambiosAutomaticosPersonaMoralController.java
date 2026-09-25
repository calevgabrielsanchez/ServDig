package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.enums.ErroresModificacionPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author 191807 131212
 *
 */
@Controller
@RequestMapping(value = "/persona/moral/identificar/cambios-automaticos")
public class IdentificarCambiosAutomaticosPersonaMoralController extends AbstractController{

	@Autowired
	private PersonaMoralBusinessRemote personaMoralBusiness;
	
	/**
	 * Metodo de prueba que se invoca al picarle en la URL de ingresar datos para comenzar
	 * el proceso de identificar cambios automaticos
	 * 
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/ingresar-datos-test", method = RequestMethod.GET)
    public String ingresarDatosTest(final Model model) {
    	
    	ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
    	
    	Moral moral = new Moral();
//    	moral.setIdPersona(4L);
//    	moral.setRfc("AEG270427UE5");
    	
//    	moral.setRfc("TAZ960904V78");
    	
    	moral.setIdPersona(334395L);
    	moral.setRfc("ADE0501173H6");
    	
    	icaDatosConsulta.setPersonaMoral(moral);
    	icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.TRUE);
    	
        model.addAttribute("icaDatosConsulta", icaDatosConsulta);
        model.addAttribute("tramite", new TramiteCambioInformacionPersona());
        
        return "ingresar-datos-moral-test";
    }
	
	/**
	 * Metodo que se invoca al picarle en la URL de ingresar datos para comenzar el proceso de identificar cambios automaticos
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "/ingresar-datos", method = RequestMethod.GET)
    public String ingresarDatos(final Model model) {
    	    	
        model.addAttribute("icaDatosConsulta", new ICADatosConsulta());
        return "ingresar-datos-moral";
    }

	@RequestMapping(value = "/publicar-modificacion-test", method = RequestMethod.GET)
	public String publicarModificacionTest(final Model model) {
		return "publicar-modificacion-moral-test";
	}

	/**
	 * Este metodo coordina las operaciones de consultar en IMSS, RENAPO, SAT y las comparaciones y muestra la pantalla de resultados
	 * @param aoData
	 * @param response
	 * @param model
	 * @return
	 * @throws ClienteWebserviceSatRfcException 
	 * @throws IllegalAccessException 
	 */	
	@RequestMapping(value = "/consultar-comparar", method = RequestMethod.POST)
	public String consultarComparar(@ModelAttribute ICADatosConsulta icaDatosConsulta,
			final BindingResult result, final Model model, final HttpSession session) {
        
    	ICADatosRespuesta icaDatosRespuesta = null;
    	   	
    	log.debug("USUARIO EXTERNO: " + icaDatosConsulta.getIsUsuarioExterno());
    	log.debug("MOSTRAR PANTALLA: " + icaDatosConsulta.getIndicadorMostrarPantalla());
    	
    	String vista = "resultado-consultar-comparar-moral";
	
    	try {
			icaDatosRespuesta = personaMoralBusiness.identificarCambios(icaDatosConsulta);
		} catch (PersonaNoEncontradaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);			
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_VALIDAR_DATOS_ENTIDAD_EXTERNA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.RFC_NO_LOCALIZADO
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ComparacionSinDiferenciasException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (DatosInsuficientesICAException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DATOS_INSUFICIENTES_ICA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ClienteWebserviceSatRfcException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_SAT
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} 
		
    	model.addAttribute("icaDatosRespuesta", icaDatosRespuesta);
    	session.setAttribute("icaDatosRespuesta", icaDatosRespuesta);
    	
        return vista;
    }
	
	/**
	 * Este metodo coordina las operaciones de consultar en IMSS, RENAPO, SAT y
	 * las comparaciones y genera el objeto de respuesta sin mostrar la pantalla
	 * de resultados
	 * 
	 * @param icaDatosConsulta
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 * @throws ClienteWebserviceSatRfcException
	 */
	@RequestMapping(value = "/consultar-comparar-JSON", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> consultarCompararJSON(
			@RequestBody ICADatosConsulta icaDatosConsulta,
			final HttpSession session, HttpServletResponse response) {
        
    	ICADatosRespuesta icaDatosRespuesta = null;
    	Map<String, Object> result = new HashMap<String, Object>();   	
    	
    	log.debug("MOSTRAR PANTALLA: " + icaDatosConsulta.getIndicadorMostrarPantalla());
    	
    	try {
			icaDatosRespuesta = personaMoralBusiness.identificarCambios(icaDatosConsulta);
			
			icaDatosRespuesta = this.personaMoralBusiness
					.integrarCambios(icaDatosRespuesta);
			
			/*
			 * Se settea nula la entidad externa para que en el JSON solo se
			 * tenga la información de la entidad IMSS con los cambios
			 * integrados
			 */
			icaDatosRespuesta.setPersonaMoralEE(null);
			
		} catch (PersonaNoEncontradaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			
			this.procesarErrorDeNegocio(e, result, response);
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_VALIDAR_DATOS_ENTIDAD_EXTERNA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			
			this.procesarErrorDeNegocio(e, result, response);
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.RFC_NO_LOCALIZADO
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			
			this.procesarErrorDeNegocio(e, result, response);
		} catch (ComparacionSinDiferenciasException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			
			this.procesarErrorDeNegocio(e, result, response);
		} catch (DatosInsuficientesICAException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DATOS_INSUFICIENTES_ICA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			
			this.procesarErrorDeNegocio(e, result, response);
		} catch (ClienteWebserviceSatRfcException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_SAT
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			
			this.procesarErrorDeNegocio(e, result, response);
		}
    	
    	result.put("icaDatosRespuesta", icaDatosRespuesta);
		   	
		return result;
    }
	
	
	/**
	 * Método encargado de llamar al servicio que integra las diferencias
	 * encontradas entre las entidad IMSS y la entidad Externa, el objeto
	 * resultante está en formato JSON
	 * 
	 * @param icaDatosRespuesta
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/integrarCambiosICA", method = RequestMethod.POST)
	public @ResponseBody
	ICADatosRespuesta integrarCambiosICA(final HttpSession session) {

		ICADatosRespuesta icaDatosRespuesta = (ICADatosRespuesta) session
				.getAttribute("icaDatosRespuesta");

		if(icaDatosRespuesta.getPersonaMoralIMSS() != null){
			icaDatosRespuesta = this.personaMoralBusiness
					.integrarCambios(icaDatosRespuesta);
		}

		/*
		 * Se settea nula la entidad externa para que en el JSON solo se tenga
		 * la información de la entidad IMSS con los cambios integrados
		 */
		icaDatosRespuesta.setPersonaMoralEE(null);

		session.removeAttribute("icaDatosRespuesta");

		return icaDatosRespuesta;
	}
	
	@RequestMapping(value = "/limpiar-ica", method = RequestMethod.POST)
	public @ResponseBody ICADatosRespuesta limpiarICA(final HttpSession session) {
	
		session.removeAttribute("icaDatosRespuesta");
		
		return null;
	}
	
}
