package mx.gob.imss.ctirss.delta.cobranza.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.CartaNoAdeudoServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.enums.RespuestaOpinion32DEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/wizard/cartaNoAdeudo")
public class WizardCartaNoAdeudoController extends AbstractController {

	@Autowired
	private CartaNoAdeudoServiceRemote cartaNoAdeudoService;

	private static final String KEY_ATRIBUTE_ERROR = "error";

	@RequestMapping(value = "/{idPersona}/{idTipoPersona}/{rfc}/{usuario}", method = RequestMethod.GET)
	public String prepararCartaNoAdeudo(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Long idPersona, @PathVariable Long idTipoPersona, @PathVariable String rfc,
			@PathVariable String usuario) {
		
		request.setAttribute("idPersona", idPersona);
		request.setAttribute("idTipoPersona", idTipoPersona);
		request.setAttribute("rfc", rfc);
		request.setAttribute("usuario", usuario);
		
		return "prepararCartaNoAdeudo";
	}
	
	@RequestMapping(value = "generar", method = RequestMethod.POST)
	public String iniciarCartaNoAdeudo(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, @RequestParam Long idPersona,
			@RequestParam Long idTipoPersona, @RequestParam String rfc,
			@RequestParam String usuario, @RequestParam String token) {
		
		try {
			Persona persona = new Persona();
			persona.setIdPersona(idPersona);
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(idTipoPersona);
			persona.setRfc(rfc);

			String origen = session.getServletContext().getInitParameter("ORIGEN_APP");
			OrigenSolicitudEnum origenEnum = OrigenSolicitudEnum.getByDesc(origen);
	    	Long idOrigen = origenEnum.getId();
	    	
			Map<String, Object> mapaSolicitud = cartaNoAdeudoService
					.generarSolicitudCartaNoAdeudo(persona, usuario, idOrigen);
			
			publicarDocumento(response, mapaSolicitud, persona.getRfc(), token);
		} catch (EstadoAdeudoException e) {
			e.printStackTrace();
			request.setAttribute(KEY_ATRIBUTE_ERROR, e.getMessage());
			return "errorCartaNoAdeudo";
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void publicarDocumento(HttpServletResponse response,
			Map<String, Object> mapa, String rfc, String token) throws EstadoAdeudoException {
		try {
			byte[] archivo = (byte[]) mapa.get("documento");

			response.reset();
			
			Cookie cookie = new Cookie("32DCookie", token);
			response.addCookie(cookie);
			
			response.addHeader("Accept-Ranges", "bytes");
			response.addHeader("Cache-Control", "public");
			response.addHeader("Cache-Control", "must-revalidate");
			response.addHeader("Pragma", "public");
			response.addHeader("expires", "0");
			response.addHeader("Content-Disposition",
					"attachment; filename=\"CartaNoAdeudo_" + rfc + ".pdf\"");
			response.setContentType("application/pdf");
			response.setContentLength(archivo.length);
			response.getOutputStream().write(archivo);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			response.flushBuffer();
			log.info("Documento Generado.");
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new EstadoAdeudoException(
					"Ocurri&oacute; un error al intentar mostrar la carta de no adeudo.");
		}
	}

	@RequestMapping(value = "/validarPersona/{idTipoPersona}/{rfc}", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> validaPersonaParaAlta(HttpSession session,
			HttpServletRequest request, @PathVariable String rfc,
			@PathVariable Long idTipoPersona) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Persona persona = new Persona();
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(idTipoPersona);
			persona.setRfc(rfc);
			cartaNoAdeudoService.validarPersonaMismoRFC(persona);
			result.put("respuesta", 0);
		} catch (EstadoAdeudoException e) {
			result.put("respuesta", 1);
			result.put(KEY_ATRIBUTE_ERROR, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/getEstatus", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getOpinionRFC(HttpServletRequest request, HttpSession session, @RequestBody Persona persona) {
		String origen = session.getServletContext().getInitParameter("ORIGEN_APP");
		
		log.debug("Entro a checar la opinion");
		Map<String, Object> result = new HashMap<String, Object>();
		OrigenSolicitudEnum origenEnum = OrigenSolicitudEnum.getByDesc(origen);
    	Long idOrigen = origenEnum.getId();
    	RespuestaOpinion32DEnum respuesta = null;
    	String error = null;
    	
    	if(StringUtils.isNotBlank(persona.getRfc())) {
    		log.error("El rfc al que le buscare la opinion es el " + persona.getRfc());
    		persona.setRfc(persona.getRfc().trim().toUpperCase());
    		persona.setTipoPersona(new TipoPersona());
    		String rfc = persona.getRfc();
    		if(rfc.length() == 13){
    			persona.getTipoPersona().setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
    			log.error("Se setea el tipo de persona fisica");
    		} else if(rfc.length() == 12){
    			persona.getTipoPersona().setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
    			log.error("Se setea el tipo de persona fisica");
    		} else {
    			error = "El RFC proporcionado es incorrecto";
    		}
     	} else {
     		error = "Es necesario contar con el RFC de la persona f\u00EDsica o moral";
    	}
    	
    	if(error == null) {
    		
    		log.error("Se buscara la opinion para el rfc: " + persona.getRfc() + " y tipo de persona " + persona.getTipoPersona().getIdTipoPersona());
    		try {
    			respuesta = cartaNoAdeudoService.getOpinionRFC(persona, "", idOrigen);
    		} catch(EstadoAdeudoException e) {
    			log.error("Ocurrio un error al obtener la opinion para el rfc " + persona.getRfc(), e);
    			error = e.getSituacion();
    		} 
    	}
    	
    	
    	result.put("mensaje", error == null ? "OK" : error);
    	if(respuesta != null) {
    		result.put("codigoRespuesta", respuesta.getId());
    		result.put("descEstatus", respuesta.getDesc());
    	} else {
    		result.put("codigoRespuesta", 0);
    	}
    	
		return result;
	}

}
