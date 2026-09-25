package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.NotificacionNoValidaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Notificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.NotificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Marco Sánchez
 * 
 */
@Controller
@RequestMapping(value = "/notificaciones")
public class NotificacionController extends AbstractController {

	@EJB
	private NotificacionServiceBusinessRemote notificacionServiceBusiness;
	@EJB
	private PersonaBusinessRemote personaBusiness;

	@RequestMapping(value = "/initTest", method = RequestMethod.GET)
	public String init(HttpServletRequest request) {
		
		Map<Integer, String> modulos = new HashMap<Integer, String>();
		for(ModuloEnum modulo : ModuloEnum.values()){
			modulos.put(modulo.getCodigo(), modulo.name());
		}
		
		request.setAttribute("modulos", modulos);
		
		return "notificacionesTest";
	}
	
	@RequestMapping(value = "/persona/obtener/{idPersona}/{idModulo}", method = RequestMethod.GET)
	public String obtenerCantidadNotificacionesPersona(HttpServletRequest request,
			@PathVariable Long idPersona, @PathVariable Long idModulo) {
		
		// Se obtiene la cantidad de notificaciones
		int numNotificaciones = consultaCantidadNotificacionesCommon(idPersona, idModulo, false);
		String nombrePersona = null;

		// Se obtiene el nombre de la persona
		if (numNotificaciones > 0) {
			Persona persona = new Persona();
			persona.setIdPersona(idPersona);
			TipoPersona tipoPersona = new TipoPersona();
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
			persona.setTipoPersona(tipoPersona);
			nombrePersona = this.personaBusiness.obtenerNombrePersona(persona);
		}
				
		request.setAttribute("nombrePersona", nombrePersona == null ? "" : nombrePersona);
		request.setAttribute("idPersona", idPersona);
		request.setAttribute("idModulo", idModulo);
		request.setAttribute("isMoral", false);
		request.setAttribute("numNotificaciones", numNotificaciones);
		
		return "initConsultaNotificaciones";
	}

	@RequestMapping(value = "/persona-moral/obtener/{idPersona}/{idModulo}", method = RequestMethod.GET)
	public String obtenerCantidadNotificacionesPersonaMoral(HttpServletRequest request,
			@PathVariable Long idPersona, @PathVariable Long idModulo) {

		// Se obtiene la cantidad de notificaciones
		int numNotificaciones = consultaCantidadNotificacionesCommon(idPersona, idModulo, true);
		String nombrePersona = null;

		// Se obtiene el nombre de la persona
		if (numNotificaciones > 0) {
			Persona persona = new Persona();
			persona.setIdPersona(idPersona);
			TipoPersona tipoPersona = new TipoPersona();
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
			persona.setTipoPersona(tipoPersona);
			nombrePersona = this.personaBusiness.obtenerNombrePersona(persona);
		}
		
		request.setAttribute("nombrePersona", nombrePersona == null ? "" : nombrePersona);
		request.setAttribute("idPersona", idPersona);
		request.setAttribute("idModulo", idModulo);
		request.setAttribute("isMoral", true);
		request.setAttribute("numNotificaciones", numNotificaciones);

		return "initConsultaNotificaciones";
	}
	
	@RequestMapping(value = "/persona/obtener-detalle/{idPersona}/{idModulo}", method = RequestMethod.GET)
	public String obtenerDetalleNotificacionesPersona(final HttpSession session, HttpServletRequest request,
			@PathVariable Long idPersona, @PathVariable Long idModulo) {

		List<Notificacion> notificaciones = consultaNotificacionesCommon(idPersona, idModulo, false);
		String nombrePersona = null;

		// Se obtiene el nombre de la persona
		if (notificaciones != null && !notificaciones.isEmpty()) {
			Persona persona = new Persona();
			persona.setIdPersona(idPersona);
			TipoPersona tipoPersona = new TipoPersona();
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
			persona.setTipoPersona(tipoPersona);
			nombrePersona = this.personaBusiness.obtenerNombrePersona(persona);
		}
		
		request.setAttribute("notificaciones", notificaciones);
		request.setAttribute("idPersona", idPersona);
		request.setAttribute("idModulo", idModulo);
		request.setAttribute("nombrePersona", nombrePersona == null ? "" : nombrePersona);
		request.setAttribute("isMoral", false);
		
		return "detalleConsultaNotificaciones";
	}

	@RequestMapping(value = "/persona-moral/obtener-detalle/{idPersona}/{idModulo}", method = RequestMethod.GET)
	public String obtenerDetalleNotificacionesPersonaMoral(final HttpSession session, HttpServletRequest request,
			@PathVariable Long idPersona, @PathVariable Long idModulo) {

		List<Notificacion> notificaciones = consultaNotificacionesCommon(idPersona, idModulo, true);
		String nombrePersona = null;

		// Se obtiene el nombre de la persona
		if (notificaciones != null && !notificaciones.isEmpty()) {
			Persona persona = new Persona();
			persona.setIdPersona(idPersona);
			TipoPersona tipoPersona = new TipoPersona();
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
			persona.setTipoPersona(tipoPersona);
			nombrePersona = this.personaBusiness.obtenerNombrePersona(persona);
		}
		
		request.setAttribute("notificaciones", notificaciones);
		request.setAttribute("idPersona", idPersona);
		request.setAttribute("idModulo", idModulo);
		request.setAttribute("nombrePersona", nombrePersona == null ? "" : nombrePersona);
		request.setAttribute("isMoral", true);

		return "detalleConsultaNotificaciones";
	}
	
	@RequestMapping(value = "/persona/obtener-detalle-json/{idPersona}/{idModulo}", method = RequestMethod.POST)
	public @ResponseBody List<Notificacion> obtenerJSONNotificacionesPersona(final HttpSession session, HttpServletRequest request,
			@PathVariable Long idPersona, @PathVariable Long idModulo) {

		return consultaNotificacionesCommon(idPersona, idModulo, false);
	}

	@RequestMapping(value = "/persona-moral/obtener-detalle-json/{idPersona}/{idModulo}", method = RequestMethod.POST)
	public @ResponseBody List<Notificacion> obtenerJSONNotificacionesPersonaMoral(final HttpSession session, 
			HttpServletRequest request, @PathVariable Long idPersona, @PathVariable Long idModulo) {

		return consultaNotificacionesCommon(idPersona, idModulo, true);
	}
	
	@RequestMapping(value = "/expirar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> expirarNotificaciones (
			final HttpSession session, HttpServletResponse response, 
			@RequestBody String[] idNotificaciones) {
		
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		
		Notificacion notificacion = null;
		for (String idNotificacion : idNotificaciones){
			notificacion = new Notificacion();
			notificacion.setIdNotificacion(Long.valueOf(idNotificacion));
			notificaciones.add(notificacion);
		}
		
		this.notificacionServiceBusiness.expirarNotificaciones(notificaciones);
		
		return null;
	}
	
	@RequestMapping(value = "/persona/obtener-resumen/{idPersona}")
	public String consultarNotificacionesPersonaFisicaResumen (@PathVariable Long idPersona,
			HttpServletRequest request) {
		
		List<Notificacion> notificaciones = consultaNotificacionesCommon(idPersona, null, false);
		
		request.setAttribute("notificaciones", notificaciones);
		
		return "notificacionesResumen";
	}
	
	@RequestMapping(value = "/persona-moral/obtener-resumen/{idPersona}")
	public String consultarNotificacionesPersonaMoralResumen (@PathVariable Long idPersona,
			HttpServletRequest request) {
		
		List<Notificacion> notificaciones = consultaNotificacionesCommon(idPersona, null, true);
		
		request.setAttribute("notificaciones", notificaciones);
		
		return "notificacionesResumen";
	}
	
	@RequestMapping(value = "/persona/obtener-cantidad/{idPersona}")
	public @ResponseBody Integer consultarNumeroNotificacionesPersonaFisica (@PathVariable Long idPersona,
			HttpServletRequest request) {
		
		Integer numNotificaciones = consultaCantidadNotificacionesCommon(idPersona, null, false);
		
		return numNotificaciones;
	}
	
	@RequestMapping(value = "/persona-moral/obtener-cantidad/{idPersona}")
	public @ResponseBody Integer consultarNumeroNotificacionesPersonaMoral (@PathVariable Long idPersona,
			HttpServletRequest request) {
		
		Integer numNotificaciones = consultaCantidadNotificacionesCommon(idPersona, null, true);
		
		return numNotificaciones;
	}
	
	@RequestMapping(value = "/notificacion/detalle/{idNotificacion}")
	public String obtenerDetalleNotificacion (@PathVariable Long idNotificacion,
			HttpServletRequest request) {
		
		Notificacion notificacion = null;
		
		try {
			notificacion = this.notificacionServiceBusiness.obtenerNotificacion(idNotificacion);
		} catch (NotificacionNoValidaException e) {
			this.log.error(e);
			notificacion = new Notificacion();
			notificacion.setErrorFormGeneral(e.getMessage());
		}
		
		request.setAttribute("notificacion", notificacion);
		
		return "detalle.notificacion";
	}
	
	private List<Notificacion> consultaNotificacionesCommon(Long idPersona,
			Long idModulo, boolean isMoral) {
		
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		TipoPersona tipoPersona = new TipoPersona();
		
		if(isMoral){
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
		} else {
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		}
		
		persona.setTipoPersona(tipoPersona);
		
		List<Notificacion> notificaciones = null;
		
		if(idModulo != null) {
			Modulo modulo = new Modulo();
			modulo.setIdModulo(idModulo);
			
			notificaciones = this.notificacionServiceBusiness
					.obtenerNotificacionesPersonaModulo(persona, modulo);
		} else {
			notificaciones = this.notificacionServiceBusiness
					.obtenerNotificacionesPersona(persona);
		}

		return notificaciones;
	}
	
	private Integer consultaCantidadNotificacionesCommon(Long idPersona,
			Long idModulo, boolean isMoral) {
		
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		TipoPersona tipoPersona = new TipoPersona();
		
		if(isMoral){
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
		} else {
			tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		}
		
		persona.setTipoPersona(tipoPersona);
		
		Integer numNotificaciones = null;
		
		if(idModulo != null) {
			Modulo modulo = new Modulo();
			modulo.setIdModulo(idModulo);
			
			numNotificaciones = this.notificacionServiceBusiness
					.obtenerNumeroNotificacionesPersonaModulo(persona, modulo);
		} else {
			numNotificaciones = this.notificacionServiceBusiness.obtenerNumeroNotificacionesPersona(persona);
		}

		return numNotificaciones;
	}
}
