package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsentamientoNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.web.dto.BusquedaDelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.web.validator.AsentamientoValidator;
import mx.gob.imss.ctirss.delta.web.validator.DomicilioValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/domicilio/nacional/ubicar/delegacion")
public class DomicilioDelegacionController extends AbstractController{
	
	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	private static final int LONGITUD_CODIGO_POSTAL_MIN = 5;
	private static final String KEY_ID_DELEGACION = "idDelegacion";
	private static final String KEY_ID_UMF_ORIGEN = "idUmfOrigenSession";
	private static final String KEY_ID_UMF_DESTINO = "idUmfDestinoSession";
	
	/**
	 * Metodo para iniciar la busqueda de un domicilio de acuerdo dentro deladelegacion especificada
	 * @param model
	 * @return String
	 */
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String home(@RequestParam Long idDelegacion, @RequestParam Long idUmfOrigen, @RequestParam Long idUmfDestino,
			Model model, HttpServletRequest request, HttpSession session){
		
		model.addAttribute("domicilio", new Domicilio());
		log.debug("El id de la delegacion que llega es: " + idDelegacion);
		log.debug("El id de la umf origen que llegas es: " + idUmfOrigen);
		log.debug("El id de la umf destino que llega es: " + idUmfDestino);
		
		session.setAttribute(KEY_ID_DELEGACION, idDelegacion);
		session.setAttribute(KEY_ID_UMF_ORIGEN, idUmfOrigen);
		session.setAttribute(KEY_ID_UMF_DESTINO, idUmfDestino);
		
		return "domicilio.nacionalDelegacion";
		
	}
	
	@RequestMapping(value="/porCodigoPostal" , method=RequestMethod.POST)
	public String ubicarPorCodigoPostal( @ModelAttribute Domicilio domicilio , BindingResult result , Model model, HttpServletRequest request, HttpSession session){
		Long idDelegacion = (Long) session.getAttribute(KEY_ID_DELEGACION);
		
		this.log.debug(" datos complementarios por codigo [" + domicilio +"]");
		new DomicilioValidator().validate(domicilio, result);
		if(result.hasErrors()){
			request.setAttribute("idDelegacion", idDelegacion);
			return "domicilio.nacionalDelegacion";
		}
		
		
		model.addAttribute("domicilio", domicilio);
		
		
		Asentamiento asentamiento = domicilio.getAsentamiento();
		
		//Obtenemos el detalle del asentamiento.
		try {
			asentamiento.setCodigoPostal(domicilio.getCodigoPostal());
			asentamiento =  this.domicilioServiceBusinessRemote.getAsentamiento(asentamiento);
			
			this.log.debug("Asentamiento localizado" + asentamiento);
			
			model.addAttribute("asentamiento", asentamiento);
			
			//Seteamos el asentamiento del domicilio.
			domicilio.setAsentamiento(asentamiento);
			request.setAttribute("idDelegacion", idDelegacion > 0 ? idDelegacion : "");
		} catch (DomicilioNoLocalizadoException e) {
			result.addError(new ObjectError("asentamiento.clave", e.getMessage()));
			request.setAttribute("idDelegacion", idDelegacion);
			this.log.error(e);
			return "domicilio.nacionalUmf"; 
		} catch (AsentamientoNoLocalizadoException e) {
			result.addError(new ObjectError("asentamiento.clave", e.getMessage()));
			request.setAttribute("idDelegacion", idDelegacion);
			this.log.error(e);
			return "domicilio.nacionalDelegacion";
			
		}
		
		return "domicilio.complemento";
	}
	
	@RequestMapping(value="/porAsentamiento" , method=RequestMethod.POST)
	public String ubicarPorMunicipio( @ModelAttribute Asentamiento asentamiento , BindingResult result , Model model, HttpServletRequest request,
			HttpSession session){
		
		Long idDelegacion = (Long) session.getAttribute(KEY_ID_DELEGACION);
		
		this.log.debug(" datos complementarios por municipio [" + asentamiento +"]");
		new AsentamientoValidator().validate(asentamiento,result);
		if(result.hasErrors()){
			model.addAttribute("domicilio", new Domicilio());
			request.setAttribute("idDelegacion", idDelegacion);
			return "domicilio.nacionalDelegacion";
		}
		
		Domicilio domicilio = new Domicilio();
		
		//Obtenemos el detalle del asentamiento.
		try {

			asentamiento =  this.domicilioServiceBusinessRemote.getAsentamiento(asentamiento);

			this.log.debug("Asentamiento localizado" + asentamiento);

			/**
			 * Validacion del codigo postal del asentamiento
			 */
			CodigoPostal cp = asentamiento.getCodigoPostal();
			if(cp == null){
				throw new DomicilioNoLocalizadoException();
			}else{
				if( cp.getCodigoPostal() == null){
					throw new DomicilioNoLocalizadoException();
				}
			}

			model.addAttribute("asentamiento", asentamiento);
			domicilio.setAsentamiento(asentamiento);
			model.addAttribute("domicilio", domicilio);
			request.setAttribute("idDelegacion", idDelegacion);
		} catch (DomicilioNoLocalizadoException e) {
			model.addAttribute("domicilio", new Domicilio());
			result.rejectValue("clave","", e.getMessage());
			this.log.error(e);
			return "domicilio.nacionalDelegacion"; 
		} catch (AsentamientoNoLocalizadoException e) {
			model.addAttribute("domicilio", new Domicilio());
			result.rejectValue("clave","", e.getMessage());
			this.log.error(e);
			return "domicilio.nacionalDelegacion";

		}
		
		return "domicilio.complemento"; 
	}
	
	/**
	 * Metodo para obtener las entidades federativas de una delegacion
	 * @param delegacion
	 * @return
	 */
	@RequestMapping( value = "/getEstadosByDelegacion", method = RequestMethod.POST)
	public @ResponseBody List<EntidadFederativa> getEstadosByDelegacion(@RequestBody BusquedaDelegacion busqueda) {
		
		List<EntidadFederativa> estados = domicilioServiceBusinessRemote.findEstadosByDelegacion(busqueda.getIdDelegacion());
		
		return estados;
	}
	
	@RequestMapping( value = "/getMunicipiosByDelegacionEstado")
	public @ResponseBody List<Municipio> getMunicipiosByDelegacionEstado(@RequestBody BusquedaDelegacion busqueda) {
		
		List<Municipio> municipios = domicilioServiceBusinessRemote.findMunicipiosByDelegacionEstado(busqueda.getIdDelegacion(), busqueda.getIdEstado());
		return municipios;
	}
	
	@RequestMapping( value = "/getAsentamientosByDelEstMun")
	public @ResponseBody List<Asentamiento> getAsentamientoByDelegacionEstadoMunicipio(@RequestBody BusquedaDelegacion busqueda) {
		List<Asentamiento> asentamientos =domicilioServiceBusinessRemote.finAsentamientosByDelegacionEstadoMunicipio(busqueda.getIdDelegacion(), busqueda.getIdEstado(), busqueda.getIdMunicipio());
		return asentamientos;
	}
	
	@RequestMapping(value="/asentamiento/get/codigoPostal", method=RequestMethod.GET	)
	public @ResponseBody Map<String, ? extends Object> getAsentamientosPorCodigo(@RequestParam String codigo ,
			HttpServletResponse response, HttpSession session){
		
		this.log.warn("codigo postal :" + codigo);
		Long idDelegacion = (Long) session.getAttribute(KEY_ID_DELEGACION);
		Long idUmfOrigen = (Long) session.getAttribute(KEY_ID_UMF_ORIGEN);
		Long idUmfDestino = (Long) session.getAttribute(KEY_ID_UMF_DESTINO);
		
		
		return this.busquedaCodigosPostales(response, codigo, idDelegacion, idUmfOrigen, idUmfDestino);
	}
	
	@RequestMapping(value="/asentamiento/get/conParametros", method=RequestMethod.GET	)
	public @ResponseBody Map<String, ? extends Object> getAsentamientosPorCodigo(@RequestParam String codigo , @RequestParam Long idDelegacion,
			@RequestParam Long idUmfOrigen, @RequestParam Long idUmfDestino, HttpServletResponse response, HttpSession session){
		
		return this.busquedaCodigosPostales(response, codigo, idDelegacion, idUmfOrigen, idUmfDestino);
	}
	
	
	@SuppressWarnings("unused")
	private Map<String, Object> busquedaCodigosPostales(HttpServletResponse response,String codigo, Long idDelegacion, Long idUmfOrigen, Long idUmfDestino) {
		
		Integer cp = null;
		Map<String,Object> result = new HashMap<String,  Object>();
		CodigoPostal codigoPostal = new CodigoPostal();
		
		Errors errors = new BindException(codigoPostal, "model");
		if(codigo == null || codigo.isEmpty()){
			this.log.warn("codigo postal nulo regresando el error");
			errors.rejectValue("codigoPostal", "field.required"  );
			this.procesaErroresDeCaptura(errors, result, response);
		}else{
			
			if(codigo.length() < LONGITUD_CODIGO_POSTAL_MIN){
				this.log.error("La longitud del codigo postal no es valida ...");
				errors.rejectValue("codigoPostal", "field.wrong.format" );
				this.procesaErroresDeCaptura(errors, result, response);
			}
			
			
			try {
				cp = Integer.valueOf(codigo);
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
			List<Asentamiento> asentamientos = this.domicilioServiceBusinessRemote.findAsentamientosByDelegacionInUmfOrigenUmfDestino(idDelegacion, codigo, idUmfOrigen, idUmfDestino);
			this.log.debug("Asentamientos[ " + asentamientos +"]");
			
			result.put("asentamientos", asentamientos);
			
		} catch (DomicilioNoLocalizadoException e) {
			this.procesarErrorDeNegocio(e, result, response);
		}
		
		return result;
	}
}