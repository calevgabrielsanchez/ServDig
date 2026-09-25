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
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.web.validator.DomicilioValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/domicilio/nacional/ubicar/byUmf")
public class DomicilioUmfController extends AbstractController{

	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	private static final int LONGITUD_CODIGO_POSTAL_MIN = 5;
	private static final String ID_UMF_PERSONA_SESSION = "idUmfPersonaSession";
	private static final String ID_UMF_USUARIO_SESSION = "idUmfUsuarioSession";
	private static final String ID_TIPO_TRAMITE_SESSION = "idTipoTramiteSession";
	
	@RequestMapping( method={RequestMethod.GET, RequestMethod.POST})
	public String home(@ModelAttribute Domicilio domicilio,@RequestParam Long idUmfUsuario, @RequestParam Long idUmfPersona, @RequestParam Long tipoTramite, 
			Model model, HttpServletRequest request, HttpSession session){
		
		model.addAttribute("domicilio", domicilio == null ? new Domicilio() : domicilio);
		session.setAttribute(ID_UMF_PERSONA_SESSION, idUmfPersona);
		session.setAttribute(ID_UMF_USUARIO_SESSION, idUmfUsuario);
		session.setAttribute(ID_TIPO_TRAMITE_SESSION, tipoTramite);
		
		return "domicilio.nacionalUmf";
	}
	
	@RequestMapping( value = "/limpiar-session")
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {

		session.removeAttribute(ID_UMF_PERSONA_SESSION);
		session.removeAttribute(ID_UMF_USUARIO_SESSION);
		session.removeAttribute(ID_TIPO_TRAMITE_SESSION);

		return null;
	}

	@RequestMapping(value="/porCodigoPostal" , method=RequestMethod.POST)
	public String ubicarPorCodigoPostal( @ModelAttribute Domicilio domicilio , 
			BindingResult result , Model model, HttpSession session){
		
		this.log.debug(" datos complementarios por codigo [" + domicilio +"]");
		new DomicilioValidator().validate(domicilio, result);
		if(result.hasErrors()){
			return "domicilio.nacionalUmf";
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
			
		} catch (DomicilioNoLocalizadoException e) {
			result.addError(new ObjectError("asentamiento.clave", e.getMessage()));
			this.log.error(e);
			return "domicilio.nacionalUmf"; 
		} catch (AsentamientoNoLocalizadoException e) {
			result.addError(new ObjectError("asentamiento.clave", e.getMessage()));
			this.log.error(e);
			return "domicilio.nacionalUmf";
		}
		
		session.removeAttribute("FROM_CODIGO_POSTAL");
		session.removeAttribute("FROM_MUNICIPIO");
		
		return "domicilio.complemento";
	}
	
	@RequestMapping(value="/asentamiento/get/codigoPostal", method=RequestMethod.GET	)
	public @ResponseBody Map<String, ? extends Object> getAsentamientosPorCodigo(@RequestParam("codigo") String codigo, HttpServletResponse response, HttpSession session){
		
		Long idUmfUsuario = (Long) session.getAttribute(ID_UMF_USUARIO_SESSION);
		Long idUmfPersona = (Long) session.getAttribute(ID_UMF_PERSONA_SESSION);
		Long idTipoTramite = (Long) session.getAttribute(ID_TIPO_TRAMITE_SESSION);
		
		return this.getAsentamientosPorCP(codigo, idUmfUsuario, idUmfPersona, idTipoTramite, response);
		
	}
	
	@RequestMapping(value="/asentamiento/get/codigoPostalYUmf", method=RequestMethod.GET	)
	public @ResponseBody Map<String, ? extends Object> getAsentamientosPorCodigoYUMF(@RequestParam("codigo") String codigo, @RequestParam("idUmf") Long idUmf,HttpServletResponse response, HttpSession session){
		
		Long idUmfUsuario = idUmf;
		Long idUmfPersona = idUmf;
		Long idTipoTramite = 44L;
		
		return this.getAsentamientosPorCP(codigo, idUmfUsuario, idUmfPersona, idTipoTramite, response);
		
	}
	
	
	
	@SuppressWarnings({ "unused", "unchecked" })
	private Map<String, Object> getAsentamientosPorCP(String codigo, Long idUmfUsuario, Long idUmfPersona, Long idTipoTramite, HttpServletResponse response ) {
		
		this.log.warn("codigo postal :" + codigo);
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
			Map<String,Object> asentamientos = this.domicilioServiceBusinessRemote.findAsentamientosByUmfCp(idUmfUsuario,idUmfPersona, codigo, idTipoTramite);
			this.log.debug("Asentamientos[ " + (List<Asentamiento>) asentamientos.get("asentamientos")+"]");
			
			result = asentamientos;
			
		} catch (DomicilioNoLocalizadoException e) {
			this.procesarErrorDeNegocio(e, result, response);
		}
		
		return result;
	}
}