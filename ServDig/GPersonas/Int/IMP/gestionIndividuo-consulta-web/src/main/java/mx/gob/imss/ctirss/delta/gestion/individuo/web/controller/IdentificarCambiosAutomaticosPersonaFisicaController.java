package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.ErroresModificacionPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;

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
@RequestMapping(value = "/persona/fisica/identificar/cambios-automaticos")
public class IdentificarCambiosAutomaticosPersonaFisicaController extends AbstractController{

	@Autowired
	PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@Autowired
	AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;
	
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
    	
    	Fisica fisica = new Fisica();
//    	fisica.setIdPersona(8328068L);
//    	fisica.setCurp("SAEM860110HDFNSR01");
//    	fisica.setRfc("SAEM860110GQ7");
    	
//    	fisica.setRfc("JAGM830110NA9");
    	
//    	fisica.setIdPersona(25128350L);
//    	fisica.setCurp("PODJ720902HDFNZQ06");
//    	fisica.setRfc("PODJ7209024R8");
    	
    	fisica.setIdPersona(3676007L);
    	fisica.setCurp("DEGH740522HMCLRC09");
    	fisica.setRfc("DEGH740522794");
    	
    	icaDatosConsulta.setPersonaFisica(fisica);
    	
    	icaDatosConsulta.setIndicadorConsultaRENAPO(Boolean.TRUE);
    	icaDatosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
    	icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.TRUE);
    	icaDatosConsulta.setIsUsuarioExterno(Boolean.TRUE);
    	
        model.addAttribute("icaDatosConsulta", icaDatosConsulta);
        model.addAttribute("tramite", new TramiteCambioInformacionPersona());
        
        return "ingresar-datos-fisica-test";
    }

    @RequestMapping(value = "/publicar-modificacion-test", method = RequestMethod.GET)
	public String publicarModificacionTest(final Model model) {
		return "publicar-modificacion-fisica-test";
	}

    /**
     * Método que muestra la pantalla con que contiene el formulario oculto
     * para el envío de datos para la ICA
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/ingresar-datos", method = RequestMethod.GET)
    public String ingresarDatos(final Model model) {
    	
        model.addAttribute("icaDatosConsulta", new ICADatosConsulta());
        return "ingresar-datos-fisica";
    }
    
	/**
	 * Este metodo coordina las operaciones de consultar en IMSS, RENAPO, SAT y
	 * las comparaciones y muestra la pantalla de resultados
	 * 
	 * @param aoData
	 * @param response
	 * @param model
	 * @return
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ClienteWebserviceSatRfcException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/consultar-comparar", method = RequestMethod.POST)
	public String consultarComparar(@ModelAttribute ICADatosConsulta icaDatosConsulta,
			final BindingResult result, final Model model, final HttpSession session) {
        
    	ICADatosRespuesta icaDatosRespuesta = null;
    	   	
    	log.debug("USUARIO EXTERNO: " + icaDatosConsulta.getIsUsuarioExterno());
    	log.debug("MOSTRAR PANTALLA: " + icaDatosConsulta.getIndicadorMostrarPantalla());
    	
    	String vista = "resultado-consultar-comparar-fisica";
	
    	try {
			icaDatosRespuesta = personaFisicaServiceBusiness.identificarCambios(icaDatosConsulta);
		} catch (PersonaNoEncontradaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.CURP_NO_LOCALIZADO
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
		} catch (ErrorComparacionDatosRENAPOException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_COMPARACION_DATOS_RENAPO
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
		} catch(DatosInsuficientesICAException e){
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DATOS_INSUFICIENTES_ICA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (DiferenciasRENAPOContraSAT e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DIFERENCIAS_RENAPO_SAT
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_FISICA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		}catch(ClienteWebserviceRenapoCurpException e){
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_RENAPO.getCodigo(), e.getMessage());
			model.addAttribute("ocultarBotones", true);
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
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ClienteWebserviceSatRfcException
	 */
	@RequestMapping(value = "/consultar-comparar-JSON", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> consultarCompararJSON(
			@RequestBody ICADatosConsulta icaDatosConsulta,
			final HttpSession session, HttpServletResponse response) {
        
    	ICADatosRespuesta icaDatosRespuesta = null;
    	Map<String, Object> result = new HashMap<String, Object>();
    	   	
    	log.debug("USUARIO EXTERNO: " + icaDatosConsulta.getIsUsuarioExterno());
    	log.debug("MOSTRAR PANTALLA: " + icaDatosConsulta.getIndicadorMostrarPantalla());
    	
    	try {
			icaDatosRespuesta = personaFisicaServiceBusiness.identificarCambios(icaDatosConsulta);
			
			icaDatosRespuesta = this.personaFisicaServiceBusiness
					.integrarCambios(icaDatosRespuesta);
			
			/*
			 * Se settea nula la entidad externa para que en el JSON solo se
			 * tenga la información de la entidad IMSS con los cambios
			 * integrados
			 */
			icaDatosRespuesta.setPersonaFisicaEE(null);
			
		} catch (PersonaNoEncontradaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			
			this.procesarErrorDeNegocio(e, result, response);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.CURP_NO_LOCALIZADO
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
		} catch (ErrorComparacionDatosRENAPOException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_COMPARACION_DATOS_RENAPO
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
		} catch (DiferenciasRENAPOContraSAT e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DIFERENCIAS_RENAPO_SAT
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			
			this.procesarErrorDeNegocio(e, result, response);
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_FISICA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			
			this.procesarErrorDeNegocio(e, result, response);
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_RENAPO
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

		if(icaDatosRespuesta.getPersonaFisicaIMSS() != null){
			icaDatosRespuesta = this.personaFisicaServiceBusiness
					.integrarCambios(icaDatosRespuesta);
		}

		/*
		 * Código para checar cómo se genera la solicitud después de integrar
		 * los cambios 
		 * try {
		 * this.afectarDatosPersonaBusiness.crearSolicitudICA(icaDatosRespuesta); 
		 * } catch (SolicitudNoValidaException e) { this.log.error(e); }
		 */
		
		/*
		 * Se settea nula la entidad externa para que en el JSON solo se tenga
		 * la información de la entidad IMSS con los cambios integrados
		 */
		icaDatosRespuesta.setPersonaFisicaEE(null);

		return icaDatosRespuesta;
	}
	
	@RequestMapping(value = "/limpiar-ica", method = RequestMethod.POST)
	public @ResponseBody ICADatosRespuesta limpiarICA(final HttpSession session) {
	
		session.removeAttribute("icaDatosRespuesta");
		
		return null;
	}	
}
