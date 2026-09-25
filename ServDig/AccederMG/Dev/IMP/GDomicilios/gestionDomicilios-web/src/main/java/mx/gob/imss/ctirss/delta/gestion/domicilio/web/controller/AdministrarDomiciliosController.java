package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/domicilio/administrar/particular")
public class AdministrarDomiciliosController extends AbstractController {

	@EJB
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;

	@RequestMapping(method = RequestMethod.GET)
	public String initAdministrarDomParticulares(final HttpSession session) {

		return "initAdmonDomicilios";
	}

	@RequestMapping(value = "/fisica/{idPersona}", method = RequestMethod.GET)
	public String obtenerDomParticularesPersonaFisica(final HttpSession session,
			@PathVariable Long idPersona) {

		session.removeAttribute("domicilios");

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		persona.setTipoPersona(tipoPersona);

		try {
			List<Domicilio> domicilios = this.domicilioServiceBusiness
					.consultarDomiciliosModificablesPersonaFisica(persona);
						
			session.setAttribute("domicilios", domicilios);
		} catch (DomicilioNoLocalizadoException e) {
			session.setAttribute("domicilios", new ArrayList<Domicilio>());
			log.error(e);
		}

		return "initAdmonDomicilios";
	}
	
	@RequestMapping(value = "/moral/{idPersona}", method = RequestMethod.GET)
	public String obtenerDomParticularesPersonaMoral(final HttpSession session,
			@PathVariable Long idPersona) {

		session.removeAttribute("domicilios");

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
		persona.setTipoPersona(tipoPersona);

		try {
			List<Domicilio> domicilios = this.domicilioServiceBusiness
					.consultarDomiciliosPersonaMoral(persona);
						
			session.setAttribute("domicilios", domicilios);
		} catch (DomicilioNoLocalizadoException e) {
			session.setAttribute("domicilios", new ArrayList<Domicilio>());
			log.error(e);
		}

		return "initAdmonDomicilios";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/agregar", method = RequestMethod.POST)
	public @ResponseBody
	Domicilio agregarDomicilio(final HttpSession session,
			@RequestBody Domicilio domicilio) {

		List<Domicilio> domicilios = (List<Domicilio>) session
				.getAttribute("domicilios");
		
		String descripcion = domicilio.getDicTipoDomicilio().getClave()
				.longValue() == TipoDomicilioEnum.PARTICULAR.getId() ? "Particular"
				: "para Escuchar y Recibir Notificaciones"; 
		domicilio.getDicTipoDomicilio().setDescripcion(descripcion);
		
		domicilio.setEstadoAdministracionDomicilio(EstadoAdministracionEnum.NUEVO);

		domicilios.add(domicilio);

		return domicilio;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validar-agregar-dom-particular", method = RequestMethod.POST )
	public @ResponseBody Map<String, ? extends Object> validarAgregarDomicilioParticular(
			HttpSession session, HttpServletResponse response ) {

		Map<String, Object> result = new HashMap<String, Object>();
    	
		Errors errors = new BindException(new Domicilio(), "model");

		List<Domicilio> domicilios = (List<Domicilio>) session
				.getAttribute("domicilios");
				
		//Se valida que la persona no tenga ya un domicilo particular		
		for(Domicilio domicilioAux : domicilios){
			if (domicilioAux.getDicTipoDomicilio().getClave().longValue() == TipoDomicilioEnum.PARTICULAR.getId()
					&& (domicilioAux.getEstadoAdministracionDomicilio() == null || 
					domicilioAux.getEstadoAdministracionDomicilio().getClave() != EstadoAdministracionEnum.ELIMINADO.getClave())) {
				errors.rejectValue("tipoDomicilio.clave","dom.particular.ya.existe");
				break;
			}
		}
		
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modificar/{indexDomicilio}", method = RequestMethod.GET)
	public String modificiarDomicilio(final HttpSession session,
			HttpServletRequest request, @PathVariable Integer indexDomicilio,
			Model model) {

		List<Domicilio> domicilios = (List<Domicilio>) session
				.getAttribute("domicilios");
		Domicilio domicilio = domicilios.get(indexDomicilio);

		request.setAttribute("indexDomicilio", indexDomicilio);
		model.addAttribute("domicilio", domicilio);

		return "admon.domicilio.complemento";
	}
	
	@RequestMapping(value = "/consultar-detalle/{idDomicilio}", method = RequestMethod.GET)
	public String consultarDetalleDomicilio(@PathVariable Integer idDomicilio,
			Model model) {

		Domicilio domicilio = new Domicilio();
		domicilio.setClave(idDomicilio);
		
		try {
			domicilio = this.domicilioServiceBusiness.consultarDomicilio(domicilio);
		} catch (DomicilioNoLocalizadoException e) {
			this.log.error(e);
			domicilio.setErrorFormGeneral(e.getMessage());
		}
		
		model.addAttribute("domicilio", domicilio);
		
		return "consulta.detalle.domicilio";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/confirmar-modificacion/{indexDomicilio}", method = RequestMethod.POST)
	public @ResponseBody Domicilio confirmarModificacion(final HttpSession session,
			@RequestBody Domicilio domicilio, @PathVariable Integer indexDomicilio) {

		List<Domicilio> domicilios = (List<Domicilio>) session
				.getAttribute("domicilios");
		
		Domicilio domicilioOriginal = domicilios.get(indexDomicilio.intValue());

		domicilio.setClave(domicilioOriginal.getClave());
		domicilio.setDicTipoDomicilio(domicilioOriginal.getDicTipoDomicilio());
		domicilio.setEstadoAdministracionDomicilio(EstadoAdministracionEnum.MODIFICADO);

		domicilios.set(indexDomicilio.intValue(), domicilio);

		return domicilio;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/eliminar/{indexDomicilio}", method = RequestMethod.GET)
	public String eliminarDomicilio(final HttpSession session,
			@PathVariable Integer indexDomicilio, Model model) {

		List<Domicilio> domicilios = (List<Domicilio>) session
				.getAttribute("domicilios");

		Domicilio domicilio = domicilios.get(indexDomicilio.intValue());
		
		domicilio.setEstadoAdministracionAnteriorDomicilio(domicilio.getEstadoAdministracionDomicilio());
		domicilio.setEstadoAdministracionDomicilio(EstadoAdministracionEnum.ELIMINADO);

		return "initAdmonDomicilios";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/obtener-domicilios", method = RequestMethod.POST)
	public @ResponseBody List<Domicilio> obtenerListaDomiciliosAdministrados(
			final HttpSession session) {

		List<Domicilio> domicilios = (List<Domicilio>) session
				.getAttribute("domicilios");

		return domicilios;
	}
	
	@RequestMapping(value = "/limpiar-domicilios", method = RequestMethod.POST)
	public @ResponseBody Domicilio limpiarListaDomiciliosAdministrados(
			final HttpSession session) {
	
		session.removeAttribute("domicilios");
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deshacer-eliminar/{indexDomicilio}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> deshacerEliminarDomicilio(final HttpSession session,
			@PathVariable Integer indexDomicilio, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		Errors errors = new BindException(new Domicilio(), "model");

		List<Domicilio> domicilios = (List<Domicilio>) session
				.getAttribute("domicilios");
		Domicilio domicilio = domicilios.get(indexDomicilio.intValue());
		
		//Se valida que la persona no tenga ya un domicilo particular		
		for(Domicilio domicilioAux : domicilios){
			if (domicilioAux.getDicTipoDomicilio().getClave().longValue() == TipoDomicilioEnum.PARTICULAR.getId()
					&& (domicilioAux.getEstadoAdministracionDomicilio() == null || 
					domicilioAux.getEstadoAdministracionDomicilio().getClave() != EstadoAdministracionEnum.ELIMINADO.getClave())) {
				errors.rejectValue("tipoDomicilio.clave","dom.particular.ya.existe");
				break;
			}
		}
		
		if( errors.hasErrors()){
			this.procesaErroresDeCaptura(errors, result, response);
		}else{
			domicilio.setEstadoAdministracionDomicilio(domicilio.getEstadoAdministracionAnteriorDomicilio());
		}
		
		return result;
	}
}