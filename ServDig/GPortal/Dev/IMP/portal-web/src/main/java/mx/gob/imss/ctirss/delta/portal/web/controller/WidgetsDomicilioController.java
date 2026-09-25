package mx.gob.imss.ctirss.delta.portal.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Marco Sánchez
 * 
 */
@Controller
@RequestMapping(value = "/widget")
public class WidgetsDomicilioController extends AbstractController {

	@EJB
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;

	/**
	 * Inicializa el widget
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/domicilios/particulares/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String initDomiciliosParticularesWidget(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		return initDomiciliosParticularesWidgetCommon(model, request, idPersona, idTipoPersona);
	}

	/**
	 * Obtiene la información necesaria para mostrar dentro del widget
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/domicilios/particulares/resumen/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String detalleDomiciliosParticularesWidget(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		return detalleDomiciliosParticularesWidgetCommon(model, request, idPersona, idTipoPersona);
	}

	/**
	 * Inicializa el widget
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/domicilios/fiscales/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String initDomiciliosFiscalesWidget(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);

		return "widgetDomiciliosFiscalesInit";
	}

	/**
	 * Obtiene la información necesaria para mostrar dentro del widget
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/domicilios/fiscales/resumen/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String detalleDomiciliosFiscalesWidget(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		Persona persona = null;
		
		if(idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
			persona = new Fisica();
			persona.setIdPersona(idPersona);
		} else {
			persona = new Moral();
			persona.setIdPersona(idPersona);
		}
		
		try {
			DomicilioFiscal domFiscal = this.domicilioServiceBusiness.consultarDomicilioFiscalPersona(persona);
			
			request.setAttribute("domFiscal", domFiscal);
		} catch (DomicilioNoLocalizadoException e) {
			this.log.warn(e);
		}

		return "widgetDomiciliosFiscalesContenido";
	}

	/**
	 * @param model
	 * @param request
	 * @param numeroRegistroPatronal
	 * @return
	 */
	@RequestMapping(value = "/domicilios/centroTrabajo/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String initDomicilioCentroTrabajoWidget(Model model,
			HttpServletRequest request,
			@PathVariable String numeroRegistroPatronal) {
		SujetoObligado patron = new SujetoObligado();
		patron.setNumeroRegistroPatronal(numeroRegistroPatronal);
		model.addAttribute("sujetoObligado", patron);

		return "widgetDomiciliosCentroTrabajoInit";
	}

	/**
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param numeroRegistroPatronal
	 * @return
	 */
	@RequestMapping(value = "/domicilios/centroTrabajo/resumen/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String detalleDomicilioCentroTrabajoWidget(Model model,
			HttpServletRequest request,
			@PathVariable String numeroRegistroPatronal) {

		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(numeroRegistroPatronal);

		SujetoObligado patron = sujetoObligadoServiceBusiness
				.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		model.addAttribute("sujetoObligado", patron);

		return "widgetDomiciliosCentroTrabajoContenido";
	}
	
	@RequestMapping(value = "/domicilios/particulares/{idPersona}/{idTipoPersona}/read-only", method = RequestMethod.GET)
	public String initDomiciliosParticularesWidgetReadOnly(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {
		
		request.setAttribute("readOnly", true);

		return initDomiciliosParticularesWidgetCommon(model, request, idPersona, idTipoPersona);
	}
	
	/**
	 * Obtiene la información necesaria para mostrar dentro del widget
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/domicilios/particulares/resumen/{idPersona}/{idTipoPersona}/read-only", 
			method = RequestMethod.GET)
	public String detalleDomiciliosParticularesWidgetReadOnly(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {
		
		request.setAttribute("readOnly", true);

		return detalleDomiciliosParticularesWidgetCommon(model, request, idPersona, idTipoPersona);
	}
	
	private String initDomiciliosParticularesWidgetCommon(Model model,
			HttpServletRequest request, Long idPersona, Long idTipoPersona) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);

		return "widgetDomiciliosParticularesInit";
	}

	private String detalleDomiciliosParticularesWidgetCommon(Model model,
			HttpServletRequest request, Long idPersona, Long idTipoPersona) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		/* Se crea la lista con los tipos de domicilios a buscar,
		 * para este caso sólo se requieren los domicilios particulares
		 */
		List<Long> tiposDomicilio = new ArrayList<Long>();
		if(idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()){
			tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getId());
		} else {
			tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getId());
			tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR_MORAL.getId());
		}
		
		try {
			List<Domicilio> domicilios = this.domicilioServiceBusiness.obtenerDomiciliosPersonaPorTipo(persona, tiposDomicilio);
			
			/*
			 * Si la persona tiene más de un domicilio particular, sólo se
			 * mostrará uno
			 */
			Domicilio domicilio = domicilios.get(0);
			
			request.setAttribute("domicilio", domicilio);
		} catch (DomicilioNoLocalizadoException e) {
			log.error(e);
		}

		return "widgetDomiciliosParticularesContenido";
	}
}
