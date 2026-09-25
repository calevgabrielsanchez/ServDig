/**
 * Controller para los diferentes Widgets a desarrollar en el proyecto DELTA.
 */
package mx.gob.imss.ctirss.delta.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Marco S�nchez
 * 
 */
@Controller
@RequestMapping(value = "/widget")
public class WidgetsMediosController extends AbstractController {

	@Autowired
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;

	/**
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/medios/particulares/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String initMediosParticularesWidget(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		return initMediosParticularesWidgetCommon(model, request, idPersona, idTipoPersona);
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/medios/particulares/resumen/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String detalleMediosParticularesWidget(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		return detalleMediosParticularesWidgetCommon(model, request, idPersona, idTipoPersona);
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/medios/fiscales/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String initMediosFiscalesWidget(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);

		return "widgetMediosFiscalesInit";
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/medios/fiscales/resumen/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String detalleMediosFiscalesWidget(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		Persona persona = null;
		
		if(idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
			persona = new Fisica();
			persona.setIdPersona(idPersona);
		} else {
			persona = new Moral();
			((Moral) persona).setCveMoral(idPersona);
		}

		try {
			List<MedioContacto> mediosFiscales = this.mediosContactoServiceBusiness
					.consultarMediosFiscalesPersona(persona);

			/*
			 * Se recorre la lista de medios de contacto, para settear el campo
			 * desFormaContacto y as� poder mostrarlo en la vista
			 */
			for (MedioContacto medio : mediosFiscales) {
				if (medio instanceof CorreoElectronico) {
					medio.setDesFormaContacto(((CorreoElectronico) medio)
							.getCorreo());
				} else if (medio instanceof TelefonoMovil) {
					medio.setDesFormaContacto(((TelefonoMovil) medio)
							.getNumero());
				} else if (medio instanceof TelefonoFijo) {
					StringBuffer numTelFijo = new StringBuffer();
					TelefonoFijo telFijo = (TelefonoFijo) medio;

					if (StringUtils.isNotBlank(telFijo.getClaveLada())) {
						numTelFijo.append(telFijo.getClaveLada()).append(" ");
					}

					if (StringUtils.isNotBlank(telFijo.getNumero())) {
						numTelFijo.append(telFijo.getNumero()).append(" ");
					}

					if (StringUtils.isNotBlank(telFijo.getExtension())) {
						numTelFijo.append(telFijo.getExtension());
					}

					medio.setDesFormaContacto(numTelFijo.toString());
				} else if (medio instanceof Facebook) {
					medio.setDesFormaContacto(((Facebook) medio).getCuenta());
				} else if (medio instanceof Twitter) {
					medio.setDesFormaContacto(((Twitter) medio).getCuenta());
				}
			}

			request.setAttribute("mediosFiscales", mediosFiscales);
		} catch (PersonaSinMedioDeContactoException e) {
			log.warn(e);
		}

		return "widgetMediosFiscalesContenido";
	}

	/**
	 * @param model
	 * @param request
	 * @param numeroRegistroPatronal
	 * @return
	 */
	@RequestMapping(value = "/medios/centroTrabajo/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String initMediosCentroTrabajoWidget(Model model,
			HttpServletRequest request,
			@PathVariable String numeroRegistroPatronal) {
		SujetoObligado patron = new SujetoObligado();
		patron.setNumeroRegistroPatronal(numeroRegistroPatronal);
		model.addAttribute("sujetoObligado", patron);

		return "widgetMediosCentroTrabajoInit";
	}
	
	/**
	 * @param model
	 * @param request
	 * @param numeroRegistroPatronal
	 * @return
	 */
	@RequestMapping(value = "/medios/centroTrabajo/resumen/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String detalleMediosCentroTrabajoWidget(Model model,
			HttpServletRequest request,
			@PathVariable String numeroRegistroPatronal) {
		
		try{
			
		
		
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(numeroRegistroPatronal);

		SujetoObligado patron = sujetoObligadoServiceBusiness
				.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		CentroTrabajo centroTrabajo = patron.getCntroTrabajo();

		if (centroTrabajo == null || centroTrabajo.getMediosContacto() == null
				|| centroTrabajo.getMediosContacto().isEmpty()) {
			Long idSujetoObligado = patron.getCveIdSujetoObligado();
			centroTrabajo.setCveIdPatronSujetoObligado(idSujetoObligado);

			List<MedioContacto> mediosCentroTrabajo = mediosContactoServiceBusiness
					.consultarMedioContactoDeCentroTrabajo(centroTrabajo);

			if(mediosCentroTrabajo != null ){
				for (MedioContacto medio : mediosCentroTrabajo) {
					if(medio instanceof TelefonoFijo){
						String descFormaContacto = medio.getDesFormaContacto();
						medio.setDesFormaContacto(descFormaContacto.replaceAll("\\|", " "));
					}
					if(medio instanceof TelefonoMovil){
						String descFormaContacto = medio.getDesFormaContacto();
						medio.setDesFormaContacto(descFormaContacto.replaceAll("\\|", " "));
					}

					log.debug("Medio Titulo: " + medio.getTipoMedioContacto().getDescripcion());
					log.debug("Medio Desc: " + medio.getDesFormaContacto());
				}
			}
			
			
			centroTrabajo.setMediosContacto(mediosCentroTrabajo);
		}

		model.addAttribute("sujetoObligado", patron);
		
		}catch(Exception e){
			this.log.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return "widgetMediosCentroTrabajoContenido";
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/medios/particulares/{idPersona}/{idTipoPersona}/read-only", method = RequestMethod.GET)
	public String initMediosParticularesWidgetReadOnly(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {

		request.setAttribute("readOnly", true);

		return initMediosParticularesWidgetCommon(model, request, idPersona, idTipoPersona);
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/medios/particulares/resumen/{idPersona}/{idTipoPersona}/read-only", method = RequestMethod.GET)
	public String detalleMediosParticularesWidgetReadOnly(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {
	
		request.setAttribute("readOnly", true);

		return detalleMediosParticularesWidgetCommon(model, request, idPersona, idTipoPersona);
	}
	
	private String initMediosParticularesWidgetCommon(Model model,
			HttpServletRequest request, Long idPersona,
			Long idTipoPersona) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);

		return "widgetMediosParticularesInit";
	}
	
	private String detalleMediosParticularesWidgetCommon(Model model,
			HttpServletRequest request, Long idPersona,
			Long idTipoPersona) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		try {
			List<MedioContacto> mediosContacto = this.mediosContactoServiceBusiness
					.consultarMedioDeContactoPersona(persona);

			/*
			 * Se recorre la lista de medios de contacto, para settear el campo
			 * desFormaContacto y as� poder mostrarlo en la vista
			 */
			for (MedioContacto medio : mediosContacto) {
				if (medio instanceof CorreoElectronico) {
					medio.setDesFormaContacto(((CorreoElectronico) medio)
							.getCorreo());
				} else if (medio instanceof TelefonoMovil) {
					medio.setDesFormaContacto(((TelefonoMovil) medio)
							.getNumero());
				} else if (medio instanceof TelefonoFijo) {
					StringBuffer numTelFijo = new StringBuffer();
					TelefonoFijo telFijo = (TelefonoFijo) medio;

					if (StringUtils.isNotBlank(telFijo.getClaveLada())) {
						numTelFijo.append(telFijo.getClaveLada()).append(" ");
					}

					if (StringUtils.isNotBlank(telFijo.getNumero())) {
						numTelFijo.append(telFijo.getNumero()).append(" ");
					}

					if (StringUtils.isNotBlank(telFijo.getExtension())) {
						numTelFijo.append(telFijo.getExtension());
					}

					medio.setDesFormaContacto(numTelFijo.toString());
				} else if (medio instanceof Facebook) {
					medio.setDesFormaContacto(((Facebook) medio).getCuenta());
				} else if (medio instanceof Twitter) {
					medio.setDesFormaContacto(((Twitter) medio).getCuenta());
				}
			}

			request.setAttribute("mediosContacto", mediosContacto);
		} catch (PersonaSinMedioDeContactoException e) {
			log.warn(e);
		}

		return "widgetMediosParticularesContenido";
	}
}