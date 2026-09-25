/**
 * Controller para los diferentes Widgets a desarrollar en el proyecto DELTA.
 */
package mx.gob.imss.ctirss.delta.portal.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
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
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.portal.web.utils.ParametrosConfig;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Lucio Duran Silva
 * 
 */
@Controller
@RequestMapping(value = "/widget")
public class WidgetController extends AbstractController {
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@Autowired
	private PersonaMoralBusinessRemote personaMoralBusiness;
	@Autowired
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusiness;
	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	@Autowired
	private PersonaBusinessRemote personaBusiness;

	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping(value = "/persona/fisica/{idPersona}", method = RequestMethod.GET)
	public String initPersonaFisicaWidget(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersona) {
		
		return initPersonaFisicaWidgetCommon(model, session, request, idPersona);
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping(value = "/persona/fisica/detalle/{idPersona}", method = RequestMethod.GET)
	public String detallePersonaFisicaWidget(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersona) {
		
		return detallePersonaFisicaWidgetCommon(model, session, request, idPersona);
	}

	@RequestMapping(value = "/persona/identidad/{idPersona}/{idTipoPersona}/{muestraDomicilio}/{muestraMedios}", method = RequestMethod.GET)
	public String initPersonaIdentidadWidget(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona,
			@PathVariable Boolean muestraDomicilio,
			@PathVariable Boolean muestraMedios) {
		return initPersonaIdentidadWidgetCommon(model, session, request,
				idPersona, idTipoPersona, muestraDomicilio, muestraMedios);
	}
	
	/**
	 * Resuelve el widget para la pantalla del asegurado, el cambio consiste en no mostrar la opción
	 * de Actualizar datos de contacto en el menu "Acciones".
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @param muestraDomicilio
	 * @param muestraMedios
	 * @return
	 */
	@RequestMapping(value = "/persona/identidad/asegurado/{idPersona}/{idTipoPersona}/{muestraDomicilio}/{muestraMedios}", method = RequestMethod.GET)
	public String initPersonaIdentidadWidgetAsegurado(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona,
			@PathVariable Boolean muestraDomicilio,
			@PathVariable Boolean muestraMedios) {
		
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);
		model.addAttribute("muestraDomicilio", muestraDomicilio);
		model.addAttribute("muestraMedios", muestraMedios);

		return "widgetPersonaIdentidadAseguradoInit";
	}
	
	/**
	 * Resuelve el widget para la pantalla del derechohabiente, el cambio consiste en no mostrar la opción
	 * de Actualizar datos de contacto en el menu "Acciones".
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @param idTipoPersona
	 * @param muestraDomicilio
	 * @param muestraMedios
	 * @return
	 */
	@RequestMapping(value = "/persona/identidad/derechohabiente/{idPersona}/{idTipoPersona}/{muestraDomicilio}/{muestraMedios}", method = RequestMethod.GET)
	public String initPersonaIdentidadWidgetDerechohabiente(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona,
			@PathVariable Boolean muestraDomicilio,
			@PathVariable Boolean muestraMedios) {
		
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);
		model.addAttribute("muestraDomicilio", muestraDomicilio);
		model.addAttribute("muestraMedios", muestraMedios);

		return "widgetPersonaIdentidadDerechohabienteInit";
	}

	@RequestMapping(value = "/persona/identidad/detalle/{idPersona}/{idTipoPersona}/{muestraDomicilio}/{muestraMedios}", method = RequestMethod.GET)
	public String detallePersonaIdentidadWidget(Model model,
			HttpSession session, HttpServletRequest request,
			@PathVariable Long idPersona, @PathVariable Long idTipoPersona,
			@PathVariable Boolean muestraDomicilio,
			@PathVariable Boolean muestraMedios) {
		return detallePersonaIdentidadWidgetCommon(model, session, request,
				idPersona, idTipoPersona, muestraDomicilio, muestraMedios);
	}

	@RequestMapping(value = "/persona/identidad/{idPersona}/{idTipoPersona}/{muestraDomicilio}/{muestraMedios}/read-only", method = RequestMethod.GET)
	public String initPersonaIdentidadWidgetReadOnly(Model model,
			HttpSession session, HttpServletRequest request,
			@PathVariable Long idPersona, @PathVariable Long idTipoPersona,
			@PathVariable Boolean muestraDomicilio,
			@PathVariable Boolean muestraMedios) {
		request.setAttribute("readOnly", true);
		return initPersonaIdentidadWidgetCommon(model, session, request,
				idPersona, idTipoPersona, muestraDomicilio, muestraMedios);
	}

	@RequestMapping(value = "/persona/identidad/detalle/{idPersona}/{idTipoPersona}/{muestraDomicilio}/{muestraMedios}/read-only", method = RequestMethod.GET)
	public String detallePersonaIdentidadWidgetReadOnly(Model model,
			HttpSession session, HttpServletRequest request,
			@PathVariable Long idPersona, @PathVariable Long idTipoPersona,
			@PathVariable Boolean muestraDomicilio,
			@PathVariable Boolean muestraMedios) {
		request.setAttribute("readOnly", true);
		return detallePersonaIdentidadWidgetCommon(model, session, request,
				idPersona, idTipoPersona, muestraDomicilio, muestraMedios);
	}

	@RequestMapping(value = "/general/fiscales/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String initPersonaFiscalWidget(Model model,
			HttpServletRequest request, @PathVariable Long idPersona,
			@PathVariable Long idTipoPersona) {
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);
		
		request.setAttribute("IND_SHOW_MSG_PAT", ParametrosConfig
				.getParametro("32D.ind_mostrar_msg_num_pat") == null ? false
				: ParametrosConfig.getParametro("32D.ind_mostrar_msg_num_pat"));
		request.setAttribute("MSG_PAT",
				ParametrosConfig.getParametro("32D.msg_num_pat").toString());

		return "widgetPersonaFiscalInit";
	}

	@RequestMapping(value = "/general/fiscales/resumen/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String detallePersonaFiscalWidget(Model model,
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
		
		// Domicilio
		try {			
			DomicilioFiscal domFiscal = this.domicilioServiceBusiness
				.consultarDomicilioFiscalPersona(persona);
			request.setAttribute("domFiscal", domFiscal);
		} catch (DomicilioNoLocalizadoException e) {
			this.log.warn(e);
		}	
			
		// Medios
		try {			
			List<MedioContacto> mediosFiscales = this.mediosContactoServiceBusiness
					.consultarMediosFiscalesPersona(persona);			
			request.setAttribute("mediosFiscales", mediosFiscales);
		} catch (PersonaSinMedioDeContactoException e) {
			this.log.warn(e);
		}
		
		return "widgetPersonaFiscalContenido";
	}

	/**
	 * @param model
	 * @param session
	 * @param request
	 * @param numeroRegistroPatronal
	 * @return
	 */
	@RequestMapping(value = "/centroTrabajo/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String initPatronCentroTrabajoWidget(Model model,
			HttpSession session, HttpServletRequest request,
			@PathVariable String numeroRegistroPatronal) {
		SujetoObligado patron = new SujetoObligado();
		patron.setNumeroRegistroPatronal(numeroRegistroPatronal);
		model.addAttribute("patron", patron);

		return "widgetPatronCentroTrabajoInit";
	}

	/**
	 * @param model
	 * @param session
	 * @param request
	 * @param numeroRegistroPatronal
	 * @return
	 */
	@RequestMapping(value = "/centroTrabajo/detalle/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String detallePatronCentroTrabajoWidget(Model model,
			HttpSession session, HttpServletRequest request,
			@PathVariable String numeroRegistroPatronal) {
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(numeroRegistroPatronal);

		SujetoObligado patron = sujetoObligadoServiceBusiness
				.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		model.addAttribute("patron", patron);

		return "widgetPatronCentroTrabajoContenido";
	}

	@RequestMapping(value = "/general/centroTrabajo/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String initPatronGeneralCentroTrabajoWidget(Model model,
			HttpSession session, HttpServletRequest request,
			@PathVariable String numeroRegistroPatronal) {
		SujetoObligado patron = new SujetoObligado();
		patron.setNumeroRegistroPatronal(numeroRegistroPatronal);
		model.addAttribute("patron", patron);
		model.addAttribute("sujetoObligado", patron);

		return "widgetPatronGralCentroTrabajoInit";
	}

	@RequestMapping(value = "/general/centroTrabajo/detalle/{numeroRegistroPatronal}", method = RequestMethod.GET)
	public String detallePatronGeneralCentroTrabajoWidget(Model model,
			HttpSession session, HttpServletRequest request,
			@PathVariable String numeroRegistroPatronal) {
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(numeroRegistroPatronal);

		SujetoObligado patron = sujetoObligadoServiceBusiness
				.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		CentroTrabajo centroTrabajo = patron.getCntroTrabajo();
		List<MedioContacto> mediosCentroTrabajo = centroTrabajo!=null ? centroTrabajo.getMediosContacto() : null;
		
		if (centroTrabajo == null || centroTrabajo.getMediosContacto() == null
				|| centroTrabajo.getMediosContacto().isEmpty()) {
			if (centroTrabajo == null) {
				centroTrabajo = new CentroTrabajo();
			}
			Long idSujetoObligado = patron.getCveIdSujetoObligado();
			centroTrabajo.setCveIdPatronSujetoObligado(idSujetoObligado);

			mediosCentroTrabajo = mediosContactoServiceBusiness
					.consultarMedioContactoDeCentroTrabajo(centroTrabajo);
		}
		
		if(mediosCentroTrabajo != null ){
			for (MedioContacto medio : mediosCentroTrabajo) {
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_FIJO.getCodigo().longValue())){
					String descFormaContacto = medio.getDesFormaContacto();
					String telefonoFormateado = parseTelefono(descFormaContacto);
					medio.setDesFormaContacto(telefonoFormateado);
				}
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_MOVIL.getCodigo().longValue())){
					String descFormaContacto = medio.getDesFormaContacto();
					medio.setDesFormaContacto(descFormaContacto.replaceAll("\\|", " "));
				}

				log.debug("Medio Titulo: " + medio.getTipoMedioContacto().getDescripcion());
				log.debug("Medio Desc: " + medio.getDesFormaContacto());
			}
		}
		
		
		centroTrabajo.setMediosContacto(mediosCentroTrabajo);
		
		model.addAttribute("patron", patron);
		model.addAttribute("sujetoObligado", patron);

		return "widgetPatronGralCentroTrabajoContenido";
	}
	
	private String parseTelefono(String telefono){
		if(telefono==null || (telefono !=null && telefono.equals("")) || (telefono !=null && telefono.equals("||")))
			return "";
		
		String[] telefonoSeccion = telefono.split("\\|");
		
		StringBuffer telefonoFormateado = new StringBuffer(); 
		telefonoFormateado.append("(").append(telefonoSeccion[0]).append(")");
		if(telefonoSeccion.length>1)
			telefonoFormateado.append(" ").append(telefonoSeccion[1]);
		if(telefonoSeccion.length>2)
			telefonoFormateado.append("-").append(telefonoSeccion[2]);
		
		return telefonoFormateado.toString();
	}
	
	
	/**
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersonaMoral
	 * @return
	 */
	@RequestMapping(value = "/persona/moral/{idPersonaMoral}", method = RequestMethod.GET)
	public String initPersonaMoralWidget(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersonaMoral) {
		
		return initPersonaMoralWidgetCommon(model, session, request, idPersonaMoral);
	}

	/**
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersonaMoral
	 * @return
	 */
	@RequestMapping(value = "/persona/moral/detalle/{idPersonaMoral}", method = RequestMethod.GET)
	public String detallePersonaMoralWidget(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersonaMoral) {
		
		return detallePersonaMoralWidgetCommon(model, session, request, idPersonaMoral); 
	}
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping(value = "/persona/fisica/{idPersona}/read-only", method = RequestMethod.GET)
	public String initPersonaFisicaWidgetReadOnly(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersona) {
		
		request.setAttribute("readOnly", true);
		
		return initPersonaFisicaWidgetCommon(model, session, request, idPersona);
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersona
	 * @return
	 */
	@RequestMapping(value = "/persona/fisica/detalle/{idPersona}/read-only", method = RequestMethod.GET)
	public String detallePersonaFisicaWidgetReadOnly(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersona) {
		
		request.setAttribute("readOnly", true);

		return detallePersonaFisicaWidgetCommon(model, session, request, idPersona);
	}
	
	/**
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersonaMoral
	 * @return
	 */
	@RequestMapping(value = "/persona/moral/{idPersonaMoral}/read-only", method = RequestMethod.GET)
	public String initPersonaMoralWidgetReadOnly(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersonaMoral) {
		
		request.setAttribute("readOnly", true);

		return initPersonaMoralWidgetCommon(model, session, request, idPersonaMoral);
	}

	/**
	 * @param model
	 * @param session
	 * @param request
	 * @param idPersonaMoral
	 * @return
	 */
	@RequestMapping(value = "/persona/moral/detalle/{idPersonaMoral}/read-only", method = RequestMethod.GET)
	public String detallePersonaMoralWidgetReadOnly(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Long idPersonaMoral) {
		
		request.setAttribute("readOnly", true);

		return detallePersonaMoralWidgetCommon(model, session, request, idPersonaMoral);
	}
	
	private String initPersonaFisicaWidgetCommon(Model model, HttpSession session,
			HttpServletRequest request, Long idPersona) {
		
		Fisica fisica = new Fisica();
		fisica.setIdPersona(idPersona);
		model.addAttribute("fisica", fisica);
		
		return "widgetPersonaFisicaInit";
	}
	
	private String detallePersonaFisicaWidgetCommon(Model model, HttpSession session,
			HttpServletRequest request, Long idPersona) {
		
		model.addAttribute("fisica", new Fisica());

		try {
			Fisica fisica = this.serviciosPersonaBusiness.buscarPersonaFisicaWidget(idPersona);
			model.addAttribute("fisica", fisica);
		} catch (PersonaFisicaNoEncontradaException e) {
			this.log.error(e);
			model.addAttribute("error", e.getMessage());
		} catch (PersonaNoEncontradaException e) {
			this.log.error(e);
			model.addAttribute("error", e.getMessage());
		}

		return "widgetPersonaFisicaContenido";
	}

	private String initPersonaIdentidadWidgetCommon(Model model,
			HttpSession session, HttpServletRequest request, Long idPersona,
			Long idTipoPersona, Boolean muestraDomicilio, Boolean muestraMedios) {

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		model.addAttribute("persona", persona);
		model.addAttribute("muestraDomicilio", muestraDomicilio);
		model.addAttribute("muestraMedios", muestraMedios);

		return "widgetPersonaIdentidadInit";
	}

	private String detallePersonaIdentidadWidgetCommon(Model model,
			HttpSession session, HttpServletRequest request, Long idPersona,
			Long idTipoPersona, Boolean muestraDomicilio, Boolean muestraMedios) {
		model.addAttribute("muestraDomicilio", muestraDomicilio);
		model.addAttribute("muestraMedios", muestraMedios);

		// Datos de persona
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(idTipoPersona);
		persona.setTipoPersona(tipoPersona);

		try {
			// Datos Generales de la Persona
			List<Long> tiposDomicilio = new ArrayList<Long>();
			if (idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
				
				Fisica fisica = null;
				try {
					fisica = this.serviciosPersonaBusiness.buscarPersonaFisicaWidget(idPersona);
				} catch (PersonaFisicaNoEncontradaException e) {
					this.log.warn("La persona no cuenta con caracter fiscal:" + e.getMessage());
				}
				
				model.addAttribute("fisica", fisica);

				tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getId());
				
				try {
					String nss = personaBusiness.obtenerNssPersona(fisica.getIdPersona());
					
					this.log.debug("La persona " + fisica.getIdPersona()
							+ " ya cuenta con NSS [" + nss + "]");
					model.addAttribute("NSS_RECUPERADO", nss);
					
				} catch (PersonaConVariosNSSException e) {
					this.log.warn("La persona cuenta con varios NSS: " + e.getMessage());
				} catch (PersonaSinNSSException e) {
					this.log.warn(e);
				}				
			} else {
				Moral moral = personaMoralBusiness.getPersonaMoral(idPersona);
				moral.setCveMoral(moral.getIdPersona());
				model.addAttribute("moral", moral);

				tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getId());
				tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR_MORAL.getId());
			}

			// Domicilios
			if (muestraDomicilio) {
				try {
					List<Domicilio> domicilios = this.domicilioServiceBusiness
							.obtenerDomiciliosPersonaPorTipo(persona, tiposDomicilio);
					
					Domicilio domicilio = domicilios.get(0);
					request.setAttribute("domicilio", domicilio);
					
				} catch (DomicilioNoLocalizadoException e) {
					this.log.warn(e);
				}
			}
	
			// Medios de Contacto
			if (muestraMedios) {
				try {
					List<MedioContacto> mediosContacto = this.mediosContactoServiceBusiness
							.consultarMedioDeContactoPersona(persona);
					
					for (MedioContacto medio : mediosContacto) {
						if (medio instanceof CorreoElectronico) {
							medio.setDesFormaContacto(((CorreoElectronico) medio).getCorreo());
						} else if (medio instanceof TelefonoMovil) {
							medio.setDesFormaContacto(((TelefonoMovil) medio).getNumero());
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
					this.log.warn(e);
				}
			}
		} catch (PersonaNoEncontradaException e) {
			this.log.error(e);
			model.addAttribute("error", e.getMessage());
		} 
		
		return "widgetPersonaIdentidadContenido";
	}
	
	private String initPersonaMoralWidgetCommon(Model model, HttpSession session,
			HttpServletRequest request, Long idPersonaMoral) {
		
		Moral moral = new Moral();
		moral.setIdPersona(idPersonaMoral);
		moral.setCveMoral(idPersonaMoral);
		model.addAttribute("moral", moral);

		return "widgetPersonaMoralInit";
	}
	
	private String detallePersonaMoralWidgetCommon(Model model, HttpSession session,
			HttpServletRequest request, Long idPersonaMoral) {
		
		//Moral moral = serviciosPersonaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS(idPersonaMoral);
		Moral moral = personaMoralBusiness.getPersonaMoral(idPersonaMoral);
		moral.setCveMoral(moral.getIdPersona());
		model.addAttribute("moral", moral);

		return "widgetPersonaMoralContenido";
	}
	
	/**** IVRO INDIVIDUAL LEO .*****/
	
//	@RequestMapping(value = "/persona/ivro/{idPersona}/{idTipoPersona}/{muestraDomicilio}/{rfc}", method = RequestMethod.GET)
//	public String initPersonaIvroWidget(Model model, HttpSession session,
//			HttpServletRequest request, @PathVariable Long idPersona,
//			@PathVariable Long idTipoPersona,
//			@PathVariable Boolean muestraDomicilio,
//			@PathVariable String rfc) {
//		return initPersonaIvroWidgetCommon(model, session, request,
//				idPersona, idTipoPersona, muestraDomicilio, rfc);
//	}
//	
//	private String initPersonaIvroWidgetCommon(Model model,
//			HttpSession session, HttpServletRequest request, Long idPersona,
//			Long idTipoPersona, Boolean muestraDomicilio, String rfc) {
//
//		Persona persona = new Persona();
//		persona.setIdPersona(idPersona);
//
//		TipoPersona tipoPersona = new TipoPersona();
//		tipoPersona.setIdTipoPersona(idTipoPersona);
//		persona.setTipoPersona(tipoPersona);
//		
//		// Datos Generales de la Persona
//		try {
//					List<Long> tiposDomicilio = new ArrayList<Long>();
//					if (idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
//						
//						Fisica fisica = null;
//						try {
//							fisica = this.serviciosPersonaBusiness.buscarPersonaFisicaWidget(idPersona);
//						} catch (PersonaFisicaNoEncontradaException e) {
//							this.log.warn("La persona no cuenta con caracter fiscal:" + e.getMessage());
//						}
//						
//						model.addAttribute("fisica", fisica);
//
//						tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getId());
//						
//						try {
//							String nss = personaBusiness.obtenerNssPersona(fisica.getIdPersona());
//							
//							this.log.debug("La persona " + fisica.getIdPersona()
//									+ " ya cuenta con NSS [" + nss + "]");
//							model.addAttribute("NSS_RECUPERADO", nss);
//							
//						} catch (PersonaConVariosNSSException e) {
//							this.log.warn("La persona cuenta con varios NSS: " + e.getMessage());
//						} catch (PersonaSinNSSException e) {
//							this.log.warn(e);
//						}				
//					} else {
//						Moral moral = personaMoralBusiness.getPersonaMoral(idPersona);
//						moral.setCveMoral(moral.getIdPersona());
//						model.addAttribute("moral", moral);
//
//						tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getId());
//						tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR_MORAL.getId());
//					}
//			} catch (PersonaNoEncontradaException e) {
//					this.log.error(e);
//					model.addAttribute("error", e.getMessage());
//			} 		
//
//		model.addAttribute("persona", persona);
//		model.addAttribute("muestraDomicilio", muestraDomicilio);
//		model.addAttribute("rfc", rfc);
//
//		return VIEW_WIDGET_IVRO_PERSONAL_START;
//	}
//	
//	@RequestMapping(value = "/persona/ivro/indiv/detalle/{idPersona}/{idTipoPersona}/{muestraDomicilio}/{rfc}", method = RequestMethod.GET)
//	public String detallePersonaIvroWidget(Model model,
//			HttpSession session, HttpServletRequest request,
//			@PathVariable Long idPersona, @PathVariable Long idTipoPersona,
//			@PathVariable Boolean muestraDomicilio,
//			@PathVariable String rfc) {
//		return detallePersonaIvroIndivWidgetCommon(model, session, request,
//				idPersona, idTipoPersona, muestraDomicilio, rfc);
//	}
//	
//	private String detallePersonaIvroIndivWidgetCommon(Model model,
//			HttpSession session, HttpServletRequest request, Long idPersona,
//			Long idTipoPersona, Boolean muestraDomicilio, String rfc) {
//		model.addAttribute("muestraDomicilio", muestraDomicilio);
//		model.addAttribute("rfc", rfc);
//
//		// Datos de persona
//		Persona persona = new Persona();
//		persona.setIdPersona(idPersona);
//
//		TipoPersona tipoPersona = new TipoPersona();
//		tipoPersona.setIdTipoPersona(idTipoPersona);
//		persona.setTipoPersona(tipoPersona);
//		
//		EstadoSeguroIvro status = new EstadoSeguroIvro();
//		status.setIdEstadoSeguro(EstadoSeguroIvroEnum.ACTIVO.getId());
//		status.setDescripcion(EstadoSeguroIvroEnum.ACTIVO.name());
//		
//		
//		Calendar calendar = new GregorianCalendar();
//		calendar.setTime(new Date());
//		calendar.add(Calendar.DAY_OF_MONTH, 1);
//		
//		SeguroIvro  ivro =  new SeguroIvro();
//		SeguroIvro [] segurosArray = new SeguroIvro[2];
//		ivro.setFechaInicio(new Date());
//		ivro.setFechaFin(calendar.getTime());
//		ivro.setEstadoSeguro(status);
//		
//		calendar.add(Calendar.DAY_OF_MONTH, 3);
//		status = new EstadoSeguroIvro();
//		status.setIdEstadoSeguro(EstadoSeguroIvroEnum.NUEVO.getId());
//		status.setDescripcion(EstadoSeguroIvroEnum.NUEVO.name());
//		SeguroIvro  ivro2 =  new SeguroIvro();
//		ivro2.setFechaInicio(new Date());
//		ivro2.setFechaFin(calendar.getTime());
//		ivro2.setEstadoSeguro(status);
//		
//		segurosArray[0] = ivro;
//		segurosArray[1] = ivro2;
//		SegurosIvro seguros  = new SegurosIvro();
//		seguros.setSeguroIvro(segurosArray);
//		
//		
//		model.addAttribute("persona", persona);
//		model.addAttribute("compra", false);
//		model.addAttribute("renova", true);
//		model.addAttribute("seguros", seguros.getSeguroIvro());
//
//		return VIEW_WIDGET_IVRO_PERSONAL_BODY;
//	}
}
