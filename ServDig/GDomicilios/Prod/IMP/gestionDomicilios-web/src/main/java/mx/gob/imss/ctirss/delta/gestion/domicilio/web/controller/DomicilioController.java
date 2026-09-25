/**
 * gestionDomicilios-web03/04/2012
 * mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller03/04/2012
 * DomicilioController.java
 * 03/04/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsentamientoNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.VialidadesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCamino;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCarretera;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoValidacionDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.web.validator.AsentamientoValidator;
import mx.gob.imss.ctirss.delta.web.validator.DomicilioConcluirValidator;
import mx.gob.imss.ctirss.delta.web.validator.DomicilioValidator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lucio Duran Silva
 * Instituto Mexicano del Seguro Social
 */
@Controller
@RequestMapping(value="/domicilio/nacional/ubicar")
public class DomicilioController extends AbstractController {

	private static final int LONGITUD_CODIGO_POSTAL_MIN = 5;
	private static final String KEY_TIPO_NUEVA_VIALIDAD = "tipoNuevaVialidad";
	private static final String KEY_TIPO_BUSQUEDA = "tipoBusquedaDomicilio";
	private static final String PANTALLA_DOMICILIOS = "seccionDomiciliosCommon";
	
	//Variables de sesion para DOMICILIOSS
	private static final String DOMICILIO_FISCAL_TO_SESSION = "_domicilioFiscalToSession";
	private static final String DOMICILIOS_PARTICULARES_TO_SESSION = "_domiciliosParticularesToSession";
	
	
	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String iniciar(Model model, @ModelAttribute Domicilio domicilio, HttpSession session){
		session.removeAttribute(KEY_TIPO_NUEVA_VIALIDAD);
		session.removeAttribute(KEY_TIPO_BUSQUEDA);
		
		if (domicilio.getCodigoPostal() != null
				&& StringUtils.isNotBlank(domicilio.getCodigoPostal()
						.getCodigoPostal())) {
			model.addAttribute("domicilio", domicilio);
			model.addAttribute("asentamiento", new Asentamiento());
		} else if (domicilio.getAsentamiento() != null
				&& domicilio.getAsentamiento().getLocalidad() != null) {
			model.addAttribute("domicilio", new Domicilio());
			model.addAttribute("asentamiento", domicilio.getAsentamiento());
		} else {
			model.addAttribute("domicilio", new Domicilio());
			model.addAttribute("asentamiento", new Asentamiento());
		}
		
		return "domicilio.nacional";
	}

	
	
	@RequestMapping(value = "/porCodigoPostal", method = RequestMethod.POST)
	public String ubicarPorCodigoPostal(@ModelAttribute Domicilio domicilio,
			BindingResult result, Model model, HttpSession session) {

		this.log.debug(" datos complementarios por codigo [" + domicilio + "]");
		new DomicilioValidator().validate(domicilio, result);
		if (result.hasErrors()) {
			model.addAttribute("asentamiento", new Asentamiento());
			return "domicilio.nacional";
		}

		model.addAttribute("domicilio", domicilio);

		Asentamiento asentamiento = domicilio.getAsentamiento();
		
		// Obtenemos el detalle del asentamiento.
		try {
			asentamiento.setCodigoPostal(domicilio.getCodigoPostal());
			asentamiento = this.domicilioServiceBusinessRemote
					.getAsentamiento(asentamiento);

			this.log.debug("Asentamiento localizado" + asentamiento);

			model.addAttribute("asentamiento", asentamiento);

			// Seteamos el asentamiento del domicilio.
			domicilio.setAsentamiento(asentamiento);

		} catch (DomicilioNoLocalizadoException e) {
			result.addError(new ObjectError("asentamiento.clave", e
					.getMessage()));
			this.log.error(e);
			return "domicilio.nacional";
		} catch (AsentamientoNoLocalizadoException e) {
			model.addAttribute("asentamiento", new Asentamiento());
			result.addError(new ObjectError("asentamiento.clave", e
					.getMessage()));
			this.log.error(e);
			return "domicilio.nacional";

		} 

		session.setAttribute("FROM_CODIGO_POSTAL", true);
		session.removeAttribute("FROM_MUNICIPIO");

		return "domicilio.complemento";
	}
	
	
	@RequestMapping(value = "/porMunicipio", method = RequestMethod.POST)
	public String ubicarPorMunicipio(@ModelAttribute Asentamiento asentamiento,
			BindingResult result, Model model, HttpSession session) {

		this.log.debug(" datos complementarios por municipio [" + asentamiento
				+ "]");
		new AsentamientoValidator().validate(asentamiento, result);
		if (result.hasErrors()) {
			model.addAttribute("domicilio", new Domicilio());
			return "domicilio.nacional";
		}

		Domicilio domicilio = new Domicilio();

		// Obtenemos el detalle del asentamiento.
		try {

			asentamiento = this.domicilioServiceBusinessRemote
					.getAsentamiento(asentamiento);

			this.log.debug("Asentamiento localizado" + asentamiento);

			/**
			 * Validacion del codigo postal del asentamiento
			 */

			CodigoPostal cp = asentamiento.getCodigoPostal();
			if (cp == null) {
				throw new DomicilioNoLocalizadoException();
			} else {
				if (cp.getCodigoPostal() == null) {
					throw new DomicilioNoLocalizadoException();
				}
			}

			model.addAttribute("asentamiento", asentamiento);
			domicilio.setAsentamiento(asentamiento);
			model.addAttribute("domicilio", domicilio);

		} catch (DomicilioNoLocalizadoException e) {
			model.addAttribute("domicilio", new Domicilio());
			result.rejectValue("clave", "", e.getMessage());
			this.log.error(e);
			return "domicilio.nacional";
		} catch (AsentamientoNoLocalizadoException e) {
			model.addAttribute("domicilio", new Domicilio());
			result.rejectValue("clave", "", e.getMessage());
			this.log.error(e);
			return "domicilio.nacional";

		}

		session.setAttribute("FROM_MUNICIPIO", true);
		session.removeAttribute("FROM_CODIGO_POSTAL");		

		return "domicilio.complemento";
	}
	
	/**
	 * 
	 * @param domicilio
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/regresar/datos/complementarios" , method=RequestMethod.POST)
	public String regresarADatosComplementarios( @ModelAttribute Domicilio domicilio , BindingResult result , Model model){
		
		model.addAttribute("domicilio", domicilio);
		return "domicilio.complemento"; 
	}
	
	
	@RequestMapping( value="/datos/complementarios" , method=RequestMethod.POST)
	public String capturarDatosComplementarios(  Model model){
		return "domicilio.nacionalComplementos";
	}
	
	
	/**
	 * 
	 * @param domicilio
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/complemento/guardar", method = RequestMethod.POST)
	public String guardarDatosComplementarios(
			@ModelAttribute Domicilio domicilio, BindingResult result,
			Model model, HttpSession session) {

		String tipoVialidad = (String)session.getAttribute(KEY_TIPO_NUEVA_VIALIDAD);
		
		this.log.warn("Se hara la valdacion con el tipo de vialidad nueva : " + tipoVialidad);
		try {
			this.log.warn(" datos complementarios a guardar [" + domicilio
					+ "]");
			new DomicilioConcluirValidator().validate(domicilio, result);
			
			this.log.warn(" Se supero la validacion de domicilio ....");
			
			if (result.hasErrors()) {
				model.addAttribute("asentamiento", domicilio.getAsentamiento());
				model.addAttribute("domicilio", domicilio);
				return "domicilio.complemento";
			}

			// Debemos de recuperar los detalles de las vialidades...
			this.log.warn("Se va obtener el detalle de las vialidades ...");
			
			if (domicilio.getVialidadPrimaria() != null
					&& domicilio.getVialidadPrimaria().getClave() != null) {
				try {
					domicilio.setVialidadPrimaria(this.domicilioServiceBusinessRemote
									.getVialidad(domicilio.getVialidadPrimaria()));
	
				} catch (VialidadesNoLocalizadasException e) {
					this.log.warn("Error al localizar la vialidad primaria ...");
				}
			}

			if (domicilio.getVialidadReferenciaPrimaria() != null
					&& domicilio.getVialidadReferenciaPrimaria().getClave() != null) {
				try {
					domicilio.setVialidadReferenciaPrimaria(this.domicilioServiceBusinessRemote
									.getVialidad(domicilio
											.getVialidadReferenciaPrimaria()));
				} catch (VialidadesNoLocalizadasException e) {
					this.log.warn("Error al localizar la vialidad referencia primaria ...");
				}
			}
			
			if (domicilio.getVialidadReferenciaSecundaria() != null
					&& domicilio.getVialidadReferenciaSecundaria().getClave() != null) {
				try {
					domicilio.setVialidadReferenciaSecundaria(this.domicilioServiceBusinessRemote
									.getVialidad(domicilio
											.getVialidadReferenciaSecundaria()));
				} catch (VialidadesNoLocalizadasException e) {
					this.log.warn("Error al localizar la vialidad referencia secundaria ...");
				}
			}

			if (domicilio.getVialidadReferenciaPosterior() != null
					&& domicilio.getVialidadReferenciaPosterior().getClave() != null) {
				try {
					domicilio.setVialidadReferenciaPosterior(this.domicilioServiceBusinessRemote
									.getVialidad(domicilio
											.getVialidadReferenciaPosterior()));
				} catch (VialidadesNoLocalizadasException e) {
					this.log.warn("Error al localizar la vialidad referencia posterior ...");
				}
			}
			
			String nombreVialidad = DomicilioController.construirNombreVialidadPrimaria(domicilio);
			domicilio.setCalle(nombreVialidad);
			if(domicilio.getVialidadPrimaria()!= null) {
				domicilio.getVialidadPrimaria().setNombre(nombreVialidad);
			} else {
				domicilio.setVialidadPrimaria(new Vialidad());
				domicilio.getVialidadPrimaria().setNombre(nombreVialidad);
			}
			
			//Se establece el nombre de la vialidad 
		} catch (Exception e) {
			this.log.error("Error al complementar el domicilio: ",e);
		}
		
		model.addAttribute("domicilio", domicilio);

		return "domicilio.complementoTerminar";
	}
	
	
	/**
	 * Metodo de prueba para revisar la prueba de concepto de
	 * sesion intramodular.
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/fromSession", method=RequestMethod.GET	)
	public @ResponseBody Domicilio getDomicilioCapturadoSession(HttpSession session ){
		this.log.debug("recuperando el domicilio de la sesion ..." + session.getId());
		Domicilio domicilio = (Domicilio)session.getAttribute("domiciliocapturado");
		return domicilio;
	}
		
	@RequestMapping(value="/asentamiento/get/codigoPostal", method={RequestMethod.GET, RequestMethod.POST}	)
	public @ResponseBody Map<String, ? extends Object> getAsentamientosPorCodigo(@RequestParam String codigo , HttpServletResponse response){
		
		this.log.warn("codigo postal :" + codigo);
		Integer cp = null;
		Map result = new HashMap<String,  Object>();
		CodigoPostal codigoPostal = new CodigoPostal();
		
		Errors errors = new BindException(codigoPostal, "model");
		if(codigo == null || codigo.isEmpty()){
			this.log.warn("codigo postal nulo regresando el error");
			errors.rejectValue("codigoPostal", "field.required"  );
			this.procesaErroresDeCaptura(errors, result, response);
		}else{
			
			/**
			 * Validamos que la longitud del dato del codigo
			 * postal sea correcta a 5 posiciones.
			 * Solicitado por la gestion de derechohabientes.
			 * LUDS: 02/10/12
			 */
			
			if(codigo.length() < LONGITUD_CODIGO_POSTAL_MIN){
				this.log.error("La longitud del codigo postal no es valida ...");
				errors.rejectValue("codigoPostal", "field.wrong.format" );
				this.procesaErroresDeCaptura(errors, result, response);
			}
			
			
			try {
				cp = Integer.valueOf(codigo);
				log.info("Codigo Postal Transformado: " + cp);
			} catch (Exception e) {
				this.log.warn("codigo postal no valido , el formato no es valido.");
				errors.rejectValue("codigoPostal", "" , "Debe ser n\u00FAmerico." );
				this.procesaErroresDeCaptura(errors, result, response);
			}
		}
		
		if(errors.hasErrors()){
			this.log.warn("regresando los errores ....");
			return result;
		}
		
		
		codigoPostal.setCodigoPostal(codigo);
		try {
			List<Asentamiento> asentamientos = this.domicilioServiceBusinessRemote.getAsentamientoPorCodigoPosta(codigoPostal);
			this.log.debug("Asentamientos[ " + asentamientos +"]");
			
			result.put("asentamientos", asentamientos);
			
		} catch (DomicilioNoLocalizadoException e) {
			this.procesarErrorDeNegocio(e, result, response);
		}
		
		return result;
		
	}
	
	

	@RequestMapping(value="/asentamiento/get/municipio", method=RequestMethod.GET	)
	public @ResponseBody Map<String, ? extends Object> getAsentamientosPorMunicipio(@RequestParam String cveEnt, @RequestParam String cveMun , HttpServletResponse response){
		
		this.log.debug("");
		Map result = new HashMap<String,  Object>();
		Municipio municipio = new Municipio();
		Errors errors = new BindException(municipio, "model");
		if(cveEnt == null || cveEnt.isEmpty() || cveMun == null || cveMun.isEmpty() ){
			this.procesaErroresDeCaptura(errors, result, response);
		}
		
		municipio.setClave(cveMun);
		municipio.setEntidadFederativa(new EntidadFederativa());
		municipio.getEntidadFederativa().setClave(cveEnt);
		
		
		
		try {
			List<Asentamiento> asentamientos = this.domicilioServiceBusinessRemote.getAsentamientoPorMunicipio(municipio);
			result.put("asentamientos", asentamientos);
		} catch (DomicilioNoLocalizadoException e) {
			this.procesarErrorDeNegocio(e, result, response);
		}
		
		return result;
		
	}
	
	@RequestMapping(value="/municipio/get/localidad", method=RequestMethod.GET	)
	public @ResponseBody Map<String, ? extends Object> getLocalidadesPorMunicipio(@RequestParam String cveEnt, @RequestParam String cveMun , HttpServletResponse response){
		
		this.log.debug("llege localidad con entidad un [" +cveEnt+"] y mun [" + cveMun +"]");
		Map result = new HashMap<String,  Object>();
		Municipio municipio = new Municipio();
		Errors errors = new BindException(municipio, "model");
		if(cveEnt == null || cveEnt.isEmpty() || cveMun == null || cveMun.isEmpty() ){
			this.procesaErroresDeCaptura(errors, result, response);
		}
		
		municipio.setClave(cveMun);
		municipio.setEntidadFederativa(new EntidadFederativa());
		municipio.getEntidadFederativa().setClave(cveEnt);
		
		
		
		try {
			List<Localidad> localidades = this.domicilioServiceBusinessRemote.getLocalidadesPorMunicipio(municipio);
			if(localidades == null || localidades.isEmpty()){
				
				Localidad localidad = new Localidad();
				localidad.setMunicipio(municipio);
				Vialidad vialidad = new Vialidad();
				vialidad.setNombre("NINGUNO");
				TipoVialidad tipoVialidad = new TipoVialidad();
				tipoVialidad.setClave(5);
				vialidad.setTipoVialidad(tipoVialidad);
				int periodo = 4;
				try {
				Domicilio domicilio = this.domicilioServiceBusinessRemote.obtenerVialidadElegida(localidad, periodo, vialidad);
				if(domicilio != null) {
					localidades = new ArrayList<Localidad>();
					localidades.add(domicilio.getLocalidad());
				}
				}catch (Exception e) {
					log.error("ocurrio un errore al consultar la vialidad y localidad default" , e);
				}
			}
			result.put("localidades", localidades);
		} catch (DomicilioNoLocalizadoException e) {
			this.procesarErrorDeNegocio(e, result, response);
		}
		
		return result;
		
	}
	
	
	
	@RequestMapping(value="/get/vialidades", method=RequestMethod.GET	)
	public @ResponseBody
	Map<String, ? extends Object> getVialidades(@RequestParam String cveEnt,
			@RequestParam String cveMun, @RequestParam String cveLoc,
			HttpServletResponse response) {
		
		this.log.debug("clave entidad :" + cveEnt);
		this.log.debug("clave municipio :" + cveMun);
		this.log.debug("clave localidad :" + cveLoc);
		
		Map result = new HashMap<String,  Object>();
		
		
		Localidad localidad = new Localidad();
		localidad.setClave(cveLoc);
		localidad.setMunicipio(new Municipio());
		localidad.getMunicipio().setClave(cveMun);
		localidad.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		localidad.getMunicipio().getEntidadFederativa().setClave(cveEnt);
		
//		Errors errors = new BindException(codigoPostal, "model");
//		if(codigo == null || codigo.isEmpty()){
//			this.procesaErroresDeCaptura(errors, result, response);
//		}
		
		try {
			
			List<Vialidad> vialidades = this.domicilioServiceBusinessRemote.getVialidades(localidad);
			this.log.debug("Vialidades[ " + vialidades +"]");
			
			result.put("vialidades", vialidades);
			
		} catch (VialidadesNoLocalizadasException e) {
			this.procesarErrorDeNegocio(e, result, response);
		}
		
		return result;
		
	}
	
	
	
	@RequestMapping(value="/get/vialidades/portipo", method=RequestMethod.GET	)
	public @ResponseBody
	Map<String, ? extends Object> getVialidadesPorTipoVialidad(@RequestParam String cveEnt,
			@RequestParam String cveMun, @RequestParam String cveLoc, @RequestParam String tpoVialidad,
			HttpServletResponse response) {
		
		this.log.debug("clave entidad :" + cveEnt);
		this.log.debug("clave municipio :" + cveMun);
		this.log.debug("clave localidad :" + cveLoc);
		this.log.debug("clave tpoVialidad :" + tpoVialidad);
		
		Map result = new HashMap<String,  Object>();
		
		
		Localidad localidad = new Localidad();
		localidad.setClave(cveLoc);
		localidad.setMunicipio(new Municipio());
		localidad.getMunicipio().setClave(cveMun);
		localidad.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		localidad.getMunicipio().getEntidadFederativa().setClave(cveEnt);
		
		
		
		 TipoVialidad tipoVialidad = new TipoVialidad();
		 tipoVialidad.setClave(new Integer(tpoVialidad));
		 
		
//		Errors errors = new BindException(codigoPostal, "model");
//		if(codigo == null || codigo.isEmpty()){
//			this.procesaErroresDeCaptura(errors, result, response);
//		}
		
		try {
			
			List<Vialidad> vialidades = this.domicilioServiceBusinessRemote.getVialidadesPorTipoVialidad(localidad, tipoVialidad);

			
			result.put("vialidades", vialidades);
			
		} catch (VialidadesNoLocalizadasException e) {
			this.procesarErrorDeNegocio(e, result, response);
		}
		
		
		return result;
		
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/map", method=RequestMethod.GET)
	public String verMapa(Model model){
		model.addAttribute("domicilio", new Domicilio());
		model.addAttribute("asentamiento", new Asentamiento());
		return "domicilio.map";
	}
	
	@RequestMapping(value = "/get/vialidades/por-nombre", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> getVialidadesPorNombre(
			@RequestParam String cveEnt, @RequestParam String cveMun,
			@RequestParam String nomVialidad,
			@RequestParam Boolean isVialidadP,
			@RequestParam(required = false) Integer periodo,
			HttpServletResponse response) {
		
		this.log.debug("clave entidad :" + cveEnt);
		this.log.debug("clave municipio :" + cveMun);
		this.log.debug("nomVialidad :" + nomVialidad);
		
		Vialidad noUbicada = new Vialidad(0, "DA CLICK AQUI SI NO ENCONTRASTE TU VIALIDAD", 0, "N/A");
		Map<String,  Object> result = new HashMap<String,  Object>();
				
		Localidad localidad = new Localidad();
		localidad.setMunicipio(new Municipio());
		localidad.getMunicipio().setClave(cveMun);
		localidad.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		localidad.getMunicipio().getEntidadFederativa().setClave(cveEnt);
			
		if (periodo == null) {
			periodo = 4;
		}
		
		try {
			List<Vialidad> vialidades = this.domicilioServiceBusinessRemote
					.obtenerVialidadesAutocompletar(localidad, periodo,
							nomVialidad.toUpperCase());
			
			//Si estamos buscando la vialidad primaria agregamos la opcion de no localizada
			if(isVialidadP) {
				//Verificamos el tamaño de la lista, en caso de ser mayor a 10 hacemos un sublist para agregar la
				//opcion de no localizada
				if(vialidades.size()>10) {
					vialidades = vialidades.subList(0, 10);
				}
				//Se agrega la opcion no localizada
				vialidades.add(noUbicada);
			}
			result.put("vialidades", vialidades);
			
		} catch (VialidadesNoLocalizadasException e) {
			//En caso de que estemos buscando la referenca primaria y no hayamos encontrado
			//ninguna vialidad agregamos la opcion de no localizada
			if(isVialidadP) {
				List<Vialidad> vialidades = new ArrayList<Vialidad>();
				vialidades.add(noUbicada);
				result.put("vialidades", vialidades);
			} else { //En caso de ser alguna vialidad de referencia se manda error indicando que no se encontraron vialidades
				this.procesarErrorDeNegocio(e, result, response);
			}
			
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/get/vialidad/elegida", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> getVialidadElegida(
			@RequestParam String cveEnt, @RequestParam String cveMun, 
			@RequestParam String nomVialidad,
			@RequestParam(required = false) Integer periodo, 
			@RequestParam Integer cveTipoVialidad,
			HttpServletResponse response) {
		
		this.log.debug("clave entidad :" + cveEnt);
		this.log.debug("clave municipio :" + cveMun);
		this.log.debug("clave cveTipoVialidad :" + cveTipoVialidad);
		this.log.debug("nomVialidad:" + nomVialidad);
		
		Map<String,  Object> result = new HashMap<String,  Object>();
				
		Localidad localidad = new Localidad();
		localidad.setMunicipio(new Municipio());
		localidad.getMunicipio().setClave(cveMun);
		localidad.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		localidad.getMunicipio().getEntidadFederativa().setClave(cveEnt);
		
		Vialidad vialidad = new Vialidad();
		vialidad.setNombre(nomVialidad);
		TipoVialidad tipoVialidad = new TipoVialidad();
		tipoVialidad.setClave(cveTipoVialidad);
		vialidad.setTipoVialidad(tipoVialidad);
		
		if (periodo == null) {
			periodo = 4;
		}
		
		try {
			
			Domicilio domicilio = this.domicilioServiceBusinessRemote
					.obtenerVialidadElegida(localidad, periodo, vialidad);
			result.put("vialidadElegida", domicilio.getVialidadPrimaria());
			result.put("localidadVialidadPrimaria", domicilio.getLocalidad());
		} catch (VialidadesNoLocalizadasException e) {
			this.procesarErrorDeNegocio(e, result, response);
		}
		
		return result;
		
	}
	
	@RequestMapping(value="/setNuevaVialidadPrimaria", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> setNuevoTipoVialidad(@RequestBody TipoVialidad vialidad,HttpSession session) {
		
		Map<String,Object> result = new HashMap<String, Object>();
		session.setAttribute(KEY_TIPO_NUEVA_VIALIDAD, vialidad.getDescripcion());
		
		log.warn("Se establece el tipo de la nueva vialidad " + vialidad.getDescripcion() + " en la sesion se encuentra: " + session.getAttribute(KEY_TIPO_NUEVA_VIALIDAD));
		
		result.put("tipo", vialidad.getDescripcion());
		
		return result;
	}
	
	@RequestMapping(value="/setTipoBusqueda", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> setTipoBusqueda(@RequestBody TipoVialidad vialidad,HttpSession session) {
		
		Map<String,Object> result = new HashMap<String, Object>();
		session.setAttribute(KEY_TIPO_BUSQUEDA, vialidad.getDescripcion());
		
		log.warn("se establece el tpo de busquda: " + vialidad.getDescripcion() + " en la sesion se encuentra : " + session.getAttribute(KEY_TIPO_BUSQUEDA));
		
		result.put("tipo", vialidad.getDescripcion());
		return result;
	}
	
	public static String construirNombreVialidadPrimaria(Domicilio domicilio) {
		
		//ser arma el nombre de la vialidad con los datos anteriores
		StringBuffer bufferVial = new StringBuffer();
		
		DomicilioCamino camino = domicilio.getDomicilioCamino();
		DomicilioCarretera carretera = domicilio.getDomicilioCarretera();
		String calle = domicilio.getCalle();
		
		if(domicilio.getVialidadPrimaria().getClave() == null){
			if(StringUtils.isBlank(calle)) {
				if(camino != null && camino.getTerminoGeneral() != null && camino.getTerminoGeneral().getClave() != null && camino.getTerminoGeneral().getClave()!=-1) {
					camino.setOrigen(camino.getOrigen().toUpperCase());
					camino.setDestino(camino.getDestino().toUpperCase());
					camino.setCadenamiento(camino.getCadenamiento().toUpperCase());
					
					bufferVial = new StringBuffer();
					
					bufferVial.append(camino.getTerminoGeneral().getDescripcion() + " ");
					bufferVial.append(camino.getOrigen() +"-" +camino.getDestino() +" ");
					bufferVial.append(camino.getMargen().getDescripcion() +" ");
					bufferVial.append(camino.getCadenamiento());	
					
					return bufferVial.toString();
				}
				
				if(carretera!= null && carretera.getTerminoGeneral() != null && carretera.getTerminoGeneral().getClave() != null && carretera.getTerminoGeneral().getClave() != -1) {
					bufferVial = new StringBuffer();
					
					carretera.setOrigen(carretera.getOrigen().toUpperCase());
					carretera.setDestino(carretera.getDestino().toUpperCase());
					carretera.setCadenamiento(carretera.getCadenamiento().toUpperCase());
					
					bufferVial.append(carretera.getTerminoGeneral().getDescripcion() + " ");
					bufferVial.append(carretera.getAdministracion().getDescripcion() + " ");
					bufferVial.append(carretera.getDerechoTransito().getDescripcion() + " ");
					bufferVial.append(carretera.getCodigoCarretera() +" ");
					bufferVial.append(carretera.getOrigen() +"-" +carretera.getDestino() +" ");
					bufferVial.append(carretera.getCadenamiento());
					
					return bufferVial.toString();
				}
			} else {
				return calle;
			}
		} else {
			if(StringUtils.isBlank(domicilio.getCalle())){
				return domicilio.getVialidadPrimaria().getNombre();
			} else {
				domicilio.setCalle(domicilio.getCalle().toUpperCase());
				return domicilio.getCalle();
			}
			
		}
		
		return "";
	}

	@RequestMapping(value = "/validarDomicilioExistente/{idTipoValidacion}/{idPersona}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, String> validarDomicilioPersonaFisica(
			@PathVariable Long idPersona, @PathVariable Integer idTipoValidacion,
			HttpServletResponse response, HttpServletRequest request,
			HttpSession session, Locale locale) {
		
		Map<String, String> result = new HashMap<String, String>();
		try {
			limpiarDomiciliosDeSesion(session);	//TODO Inicializar domicilios
			boolean existeDomFiscal = false;
			boolean existeDomParticular = false;
			
			Persona persona = new Fisica();
			persona.setIdPersona(idPersona);
			TipoPersona tipoPersona = new TipoPersona();
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
			persona.setTipoPersona(tipoPersona);

			try {
				DomicilioFiscal domicilioFiscal = domicilioServiceBusinessRemote
						.consultarDomicilioFiscalPersona(persona);
				session.setAttribute(DOMICILIO_FISCAL_TO_SESSION, domicilioFiscal);
				if (domicilioFiscal != null && domicilioFiscal.getClave() != null) {
					existeDomFiscal = true;
				}
			} catch (DomicilioNoLocalizadoException e) {
				log.warn(e);
			}

			List<Long> tiposDomicilio = new ArrayList<Long>();
			tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getId());

			try {
				List<Domicilio> domiciliosParticulares = domicilioServiceBusinessRemote
						.obtenerDomiciliosPersonaPorTipo(persona, tiposDomicilio);
				session.setAttribute(DOMICILIOS_PARTICULARES_TO_SESSION, domiciliosParticulares);
				if(!CollectionUtils.isEmpty(domiciliosParticulares)){
					existeDomParticular = true;
				}
			} catch (DomicilioNoLocalizadoException e) {
				log.warn(e);
			}
			
			if (idTipoValidacion.equals(TipoValidacionDomicilioEnum.PARTICULAR_FISCAL.getCodigo())) {
				if (existeDomParticular && existeDomFiscal) {
					result.put("mensajeExito", "La validacion de los domicilios fiscal y particular se ha realizado exitosamente");
					result.put("procesaDomicilios", "procesaDomicilios");
				} else if (!existeDomParticular) {
					result.put("DomicilioParticular", "Usted no cuenta con un domicilio particular para realizar el tramite");
				} else if (!existeDomFiscal) {
					result.put("DomicilioFiscal", "Usted no cuenta con un domicilio fiscal para realizar el tramite");
				}				
			} else if(idTipoValidacion.equals(TipoValidacionDomicilioEnum.SOLO_PARTICULAR.getCodigo())) {
				if (!existeDomParticular) {
					result.put("DomicilioParticular", "Usted no cuenta con un domicilio particular para realizar el tramite");
				} else {
					result.put("mensajeExito", "La validacion del domicilio particular se ha realizado exitosamente");
					result.put("procesaDomicilios", "procesaDomicilios");
				}
			} else if(idTipoValidacion.equals(TipoValidacionDomicilioEnum.SOLO_FISCAL.getCodigo())) {
				if (!existeDomFiscal) {
					result.put("DomicilioFiscal", "Usted no cuenta con un domicilio fiscal para realizar el tramite");
				} else {
					result.put("mensajeExito", "La validacion del domicilio fiscal se ha realizado exitosamente");
					result.put("procesaDomicilios", "procesaDomicilios");
				}
			}			
		} catch (Exception e) {
			result.put("mensajeError", e.getMessage());
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "mostrarDomiciliosExistentes/{idTipoValidacion}/{idPersona}", method = { RequestMethod.GET, RequestMethod.POST })
	public String mostrarDomiciliosExistentes(Model model, HttpSession session, HttpServletRequest request, 
			@PathVariable Long idPersona, @PathVariable Integer idTipoValidacion){
		log.debug("MostrarDomiciliosExistentes");
		log.debug("TipoValidacion " + idTipoValidacion);
		log.debug("Persona " + idPersona);
		DomicilioFiscal domicilioFiscal = (DomicilioFiscal) session.getAttribute(DOMICILIO_FISCAL_TO_SESSION);
		List<Domicilio> listaDomiciliosParticulares = (List<Domicilio>) session.getAttribute(DOMICILIOS_PARTICULARES_TO_SESSION);
		
		//Models de domicilios
		model.addAttribute("domicilioFiscal", domicilioFiscal);
		model.addAttribute("listaDomiciliosParticulares", listaDomiciliosParticulares);
		
		limpiarDomiciliosDeSesion(session);
		return PANTALLA_DOMICILIOS;
	}
	
	private void limpiarDomiciliosDeSesion(final HttpSession session) {
		session.removeAttribute(DOMICILIO_FISCAL_TO_SESSION);
		session.removeAttribute(DOMICILIOS_PARTICULARES_TO_SESSION);		
	}
	
}