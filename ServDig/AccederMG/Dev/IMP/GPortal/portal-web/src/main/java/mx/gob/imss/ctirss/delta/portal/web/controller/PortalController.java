/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portal
 *  @Archivo:PortalController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.portal.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.ctirss.delta.portal.web.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import mx.gob.imss.cit.dacvass.servicios.externos.business.TokenAccesoConveniosServicesImp;
import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenConveniosRequestBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenConveniosResponseException;
import mx.gob.imss.cit.dacvass.servicios.externos.remote.ITokenAccesoConveniosRemote;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.UsuarioBuzonRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

/**
 * @author Lucio Duran Silva
 *
 */
@Controller
@RequestMapping(value="/portal")
public class PortalController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(PortalController.class);

	private static final int USUARIO_BUZON_MOSTRAR = 2;

	@Autowired ServiciosPersonaBusinessRemote serviciosPersonaBusiness;

	@Autowired
	PersonaBusinessRemote personaBusinessRemote;

	@Autowired
	ConsultaPersonaFisicaServiceBusinessRemote consultaFisicaServiceBusinessRemote;

	@Autowired
	PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;


	@RequestMapping(method = RequestMethod.GET, params="goto")
	public ModelAndView redirect(Model model, @RequestParam("goto") String redirectparam) {
		//Si viene de otra aplicacion, redirigir a j_spring_security_check de la otra aplicacion
		if (loginExiste() 
				&& !redirectparam.contains("/portal-web/")
				&& redirectparam.contains("j_spring_security_check")) {
			log.debug("***************************************");
			log.debug("Redirigiendo a : --> {}", redirectparam);
			log.debug("***************************************");
			return new ModelAndView (new RedirectView(redirectparam));
		}
		return new ModelAndView("portal", "usuario", new Usuario());
	}

	private boolean loginExiste() {
		boolean found = null != SecurityContextHolder.getContext().getAuthentication();
		log.debug("Desde /portal-web/portal --> loginExiste()? {}", found);
		return found;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "portal";
	}

	@RequestMapping(params="issessionalive", method=RequestMethod.POST)
	public @ResponseBody boolean jsonIssessionalive() {
		log.debug("Revisando si existe un inicio de sesion");
		boolean val = loginExiste();
		log.debug("Sesion existe: {}", val);
		return val;
	}

	public Usuario getUsuarioSesion(UsuarioSSO usuariosso) {
		Usuario usuario = new Usuario();
		usuario.setUsuario(usuariosso.getNombre());

		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion(usuariosso.getNombre());
		usuario.setPerfilUsuario(pu);

		// Se crean los objetos necesarios para ligar el usuario con la
		// subdelegacion y delegacion.

		if(usuariosso.getDelegacion() != null  && usuariosso.getSubdelegacion() != null){

			// LUDS Se agrego esta validacion para que si es en caso de un usuario EXTERNO no le llega la delegacion.
			UsuarioFuncionario uf = new UsuarioFuncionario();
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(usuariosso.getDelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.setUsuario(usuario);
			uf.getSubdelegacion().setId(usuariosso.getSubdelegacion().longValue());
			usuario.setUsuarioFuncionario(uf);
		}


		usuario.setCveIdUsuario(usuariosso.getIdPersona().toString());

		return usuario;
	}

	private Usuario getUsuarioSession(Usuario usuario, HttpSession session) {
		Usuario usuAux = (Usuario) session.getAttribute(KEY_USUARIO);

		if(usuAux != null && usuAux.getFisica() != null) {
			usuario.setFisica(usuAux.getFisica());
		}

		return usuario;
	}

	@RequestMapping(value = "/ingresar", method = RequestMethod.GET)
	public String login(Model model, HttpSession session,
			HttpServletRequest request, @ModelAttribute Usuario usrForm ) throws Exception{
		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);
		Usuario usuario = getUsuarioSesion(usuariosso);
		usuario = this.getUsuarioSession(usuario, session);
		Fisica fisica = null;


		model.addAttribute("usuario", usuario);
		model.addAttribute("idPersona", usuariosso.getIdPersona());
		model.addAttribute("curpPersona", usuariosso.getCurp());
		session.setAttribute("portalContext", PortalContextEnum.INDIVIDUO.getId());

		log.info("idPersona: " + usuariosso.getIdPersona());
		log.info("curpPersona: " + usuariosso.getCurp());


		try {
			consultaFisicaServiceBusinessRemote.actualizaNombreUsuarioPatronBDTU(usuario);
		}catch(Exception e) {
			log.error("ocurrio un error al querer actualizar a la persona" , e);
		}

		try {

			fisica = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(usuariosso.getIdPersona().longValue());
			usuario.setFisica(fisica);
			// Se sube el objeto domicilio particular de la persona
			if (fisica != null && fisica.getDomicilios() != null
					&& !fisica.getDomicilios().isEmpty()) {
				for (Domicilio domicilio : fisica.getDomicilios()) {
					if (domicilio.getDicTipoDomicilio() != null
							&& domicilio.getDicTipoDomicilio().getClave()
							.intValue() == TipoDomicilioEnum.PARTICULAR
							.getCodigo().intValue()) {
						request.setAttribute("domicilio", domicilio);
						break;
					}
				}
			}
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error("No fue encontrado el id de la persona fisica");
		}

		if(fisica != null && fisica.getRfc() != null && fisica.getCveFisica() != null)
			model.addAttribute("actualizaRfcUsuario", false);
		else
			model.addAttribute("actualizaRfcUsuario", true);
		
		model.addAttribute("idPersonaFisica", fisica.getCveFisica());
		model.addAttribute("rfcFisica", fisica.getRfc());
		model.addAttribute("tipoPersona", TipoPersonaEnum.FISICA.getId());
		model.addAttribute("objFisica", fisica);

		// Subimos a la sesion la informacion del usuario
		session.setAttribute(KEY_USUARIO, usuario);

		model.addAttribute("solicitud", new Solicitud());
		this.setFechaSistema(session);

		return "home";
	}

	@RequestMapping(value = "/persona/moral/ingresar/", method = RequestMethod.POST)
	public String ingresarPortalPersonaMoral(
			@ModelAttribute Moral personaMoral, Model model,
			HttpSession session, HttpServletRequest request) {


		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);
		Usuario usuario = getUsuarioSesion(usuariosso);
		usuario = this.getUsuarioSession(usuario, session);

		// Subimos a la sesion la informacion del usuario
		session.setAttribute(KEY_USUARIO, usuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("idPersona", usuariosso.getIdPersona());
		log.info("idPersona: " + usuariosso.getIdPersona());

		//cambio para obtener los datos de la PM - INC110489
		//personaMoral = serviciosPersonaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS(personaMoral.getIdPersona());
		log.debug("::: Voy a buscar a la PM :" + personaMoral.getIdPersona());
		personaMoral = serviciosPersonaBusiness.buscarPMyDPyDyMCEnIMSS(personaMoral.getIdPersona());

		model.addAttribute("solicitud", new Solicitud());
		model.addAttribute("idPersonaRepresentada", personaMoral.getIdPersona());
		model.addAttribute("idPersonaFM", personaMoral.getIdPersona());
		model.addAttribute("rfc", personaMoral.getRfc());
		String nombreCompleto = personaMoral.getRazonSocial();
		log.debug("sustituyo " + nombreCompleto + " por  " + nombreCompleto.replace("\n", " ").replace("\"", "\\\""));
		model.addAttribute("nombre", nombreCompleto.replace("\n", " ").replace("\"", "\\\""));
		model.addAttribute("tipoPersona", TipoPersona.TIPO_PERSONA_MORAL);
		session.setAttribute("portalContext", PortalContextEnum.EMPRESA.getId());
		this.setFechaSistema(session);

		return "personaRepresentadaPortal";
	}

	@RequestMapping(value = "/persona/fisica/ingresar/", method = RequestMethod.POST)
	public String ingresarPortalPersonaFisica(
			@ModelAttribute Fisica personaFisica, Model model,
			HttpSession session, HttpServletRequest request) {
		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);
		Usuario usuario = getUsuarioSesion(usuariosso);
		usuario = this.getUsuarioSession(usuario, session);

		// Subimos a la sesion la informacion del usuario
		session.setAttribute(KEY_USUARIO, usuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("idPersona", usuariosso.getIdPersona());
		log.info("idPersona: " + usuariosso.getIdPersona());


		try {
			personaFisica = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(personaFisica.getIdPersona());
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error("No fue encontrado el id de la persona fisica");
		}

		model.addAttribute("solicitud", new Solicitud());
		model.addAttribute("idPersonaRepresentada", personaFisica.getIdPersona());
		model.addAttribute("idPersonaFM", personaFisica.getCveFisica());
		model.addAttribute("curpPersona", personaFisica.getCurp());
		model.addAttribute("rfc", personaFisica.getRfc());
		String nombreCompleto = obtenerNombreCompletoPersonaFisica(personaFisica);
		model.addAttribute("nombre", nombreCompleto.replaceAll("\n", " "));
		model.addAttribute("tipoPersona", TipoPersona.TIPO_PERSONA_FISICA);
		session.setAttribute("portalContext", PortalContextEnum.EMPRESA.getId());
		this.setFechaSistema(session);

		return "personaRepresentadaPortal";
	}

	private String obtenerNombreCompletoPersonaFisica(Fisica persona){
		StringBuffer nombreRL = new StringBuffer(); 

		if(StringUtils.isNotEmpty(persona.getNombre()) )
			nombreRL.append(persona.getNombre());
		if(StringUtils.isNotEmpty(persona.getPrimerApellido()) )
			nombreRL.append(" "+persona.getPrimerApellido());
		if(StringUtils.isNotEmpty(persona.getSegundoApellido()) )
			nombreRL.append(" "+persona.getSegundoApellido());

		return nombreRL.toString();
	}

	@RequestMapping(value = "/patron/ingresar", method = RequestMethod.POST)
	public String ingresarPortalPatronal(
			@ModelAttribute SujetoObligado patronAsociado, Model model,
			HttpSession session, HttpServletRequest request) {
		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);
		Usuario usuario = getUsuarioSesion(usuariosso);
		usuario = this.getUsuarioSession(usuario, session);

		// Subimos a la sesion la informacion del usuario
		session.setAttribute(KEY_USUARIO, usuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("idPersona", usuariosso.getIdPersona());
		log.info("idPersona: " + usuariosso.getIdPersona());

		model.addAttribute("solicitud", new Solicitud());
		model.addAttribute("numeroRegistroPatronal", patronAsociado.getNumeroRegistroPatronal());
		model.addAttribute("modalidadPatron", patronAsociado.getModalidad().getNumModalidad());
		model.addAttribute("isRPC", patronAsociado.getClasificacion().getIndRegPatClase());
		model.addAttribute("idTipoRegPatron", patronAsociado.getIdTipoRegPatron());


		session.setAttribute("portalContext", PortalContextEnum.PATRONAL.getId());

		//seteo de los atributos para el token de convenios
		TokenConveniosRequestBean tokenBean = new TokenConveniosRequestBean();
		String tokenConvenios = "tokenConvenios";
		if (patronAsociado.getFisica() != null
				&& StringUtils.isNotBlank(patronAsociado.getFisica().getRfc())) {
			model.addAttribute("rfc", patronAsociado.getFisica().getRfc());
			model.addAttribute("idPersonaEmpresa", patronAsociado.getFisica().getIdPersona());
			model.addAttribute("idFiscalEmpresaFisica", patronAsociado.getFisica().getCveFisica());
			model.addAttribute("idFiscalEmpresa", patronAsociado.getFisica().getCveFisica());
			model.addAttribute("tipoPersona", TipoPersonaEnum.FISICA.getId());
			model.addAttribute("nombre",patronAsociado.getFisica().getNombre());
			model.addAttribute("curp", patronAsociado.getFisica().getCurp());
			tokenBean.setRazonSocial(obtenerNombreCompletoPersonaFisica(patronAsociado.getFisica()));

		} else if (patronAsociado.getMoral() != null
				&& StringUtils.isNotBlank(patronAsociado.getMoral().getRfc())) {
			model.addAttribute("rfc", patronAsociado.getMoral().getRfc());
			model.addAttribute("idPersonaEmpresa", patronAsociado.getMoral().getIdPersona());
			model.addAttribute("idFiscalEmpresaMoral", patronAsociado.getMoral().getIdPersona());
			model.addAttribute("idFiscalEmpresa", patronAsociado.getMoral().getCveMoral());
			model.addAttribute("tipoPersona", TipoPersonaEnum.MORAL.getId());
			model.addAttribute("nombre", patronAsociado.getMoral().getRazonSocial());
			tokenBean.setRazonSocial(patronAsociado.getMoral().getRazonSocial());
		}

		this.setFechaSistema(session);
		try {
			tokenBean.setCurpRepresentante(usuario.getUsuario());
			tokenBean.setNombreRepresentante(obtenerNombreCompletoPersonaFisica(usuario.getFisica()));
			tokenBean.setIdPersonaUsuario(usuario.getFisica().getIdPersona()+"");
			tokenBean.setRfcSolicitante(usuario.getFisica().getRfc());
			tokenBean.setRegistroPatronal(patronAsociado.getNumeroRegistroPatronal());

			ITokenAccesoConveniosRemote tokenService = new TokenAccesoConveniosServicesImp();
			tokenConvenios = tokenService.getTokenAccesoConvenios(tokenBean);
			log.debug("regrese de generar el token con valor " + tokenConvenios);

		}catch (TokenConveniosResponseException e) {
			log.error("error en la respuesta del servicio de token " + e.getMessage());
		}catch (Exception e) {
			log.error("error no cachado ocurrio un error en la conslta del token " + e.getMessage());
		}
		model.addAttribute("tokenConvenios", tokenConvenios);
		return "patronPortal";
	}

	@RequestMapping( value = "/asegurado/ingresar", method = RequestMethod.POST)
	public String ingresarPortalAsegurado(@ModelAttribute GrupoFamiliar asegurado, Model model, HttpSession session, HttpServletRequest request) {

		model.addAttribute("idPersona", asegurado.getAsignacionNSS().getIdPersona());
		model.addAttribute("idAsignacionNss", asegurado.getAsignacionNSS().getIdAsignacionNSS());
		model.addAttribute("nss", asegurado.getAsignacionNSS().getNss());
		model.addAttribute("curpPersona", asegurado.getAsignacionNSS().getCurp());
		String nombreCompleto = obtenerNombreCompletoPersonaFisica(asegurado.getAsignacionNSS());
		model.addAttribute("nombre", nombreCompleto);
		model.addAttribute("tipoPersona", TipoPersonaEnum.FISICA.getId());
		model.addAttribute("fechaInicioVigencia", asegurado.getFechaInicioVigencia());
		model.addAttribute("fechaFinVigencia", asegurado.getFechaFinVigencia());
		//el parentesco lo recibimos en el campo de sexo
		if(asegurado.getParentesco() != null){
			model.addAttribute("idParentesco", asegurado.getParentesco().getIdParentesco());	
		}else {
			model.addAttribute("idParentesco", 0);	
		}
		if(asegurado.getEstadoDerechohabiente() != null) {
			model.addAttribute("estadoDerechohabiente",asegurado.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
		} else {
			model.addAttribute("estadoDerechohabiente",1);
		}

		session.setAttribute("portalContext", PortalContextEnum.ASEGURADO.getId());

		return "aseguradoPortal";
	}

	@RequestMapping( value = "/derechohabiente/ingresar", method = RequestMethod.POST)
	public String ingresarPortalDerechohabiente(@ModelAttribute GrupoFamiliar formIntegranteGrupo, Model model, HttpSession session, HttpServletRequest request) {

		model.addAttribute("asignacion", formIntegranteGrupo.getAsignacionNSS());
		model.addAttribute("nss", formIntegranteGrupo.getAsignacionNSS().getNss());
		model.addAttribute("persona", formIntegranteGrupo.getDerechohabiente());
		model.addAttribute("curpPersonaDerechohabiente", formIntegranteGrupo.getDerechohabiente() != null ? formIntegranteGrupo.getDerechohabiente().getCurp() : "");
		model.addAttribute("parentesco", formIntegranteGrupo.getParentesco());
		String nombreCompleto = obtenerNombreCompletoPersonaFisica(formIntegranteGrupo.getDerechohabiente());
		model.addAttribute("nombre", nombreCompleto);
		model.addAttribute("tipoPersona", TipoPersonaEnum.FISICA.getId());
		model.addAttribute("estadoDerechohabiente",formIntegranteGrupo.getEstadoDerechohabiente().getIdEstadoDerechohabiente());

		session.setAttribute("portalContext", PortalContextEnum.DERECHOHABIENTE.getId());

		return "derechohabientePortal";
	}

	@RequestMapping(value = "/consultaRfcEnBuzonTributario", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> consultaRfcEnBuzonTributario(@RequestBody String rfc,
			HttpServletResponse response, HttpSession session, Locale locale) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			UsuarioBuzonRespuesta usuarioBuzonRespuesta = personaBusinessRemote.consultaRfcEnBuzonTributario(rfc, null);

			if(usuarioBuzonRespuesta.getEstatus() == USUARIO_BUZON_MOSTRAR){
				result.put("msjBuzon", usuarioBuzonRespuesta.getMensaje());
				String razonSocial = usuarioBuzonRespuesta.getRazonSocial();
				result.put("razonSocial", razonSocial!=null?razonSocial:"");
				result.put("exito", true);
			}else {
				result.put("exito", false);
			}

		}catch (Exception e){
			GestionPatronalBusinessException exception = new GestionPatronalBusinessException(e.getMessage());

			log.error(exception.getMessage());
			this.procesarErrorDeNegocio(exception, result, response);
		}
		return result;

	}

	/**
	 * Metodo que actualiza el RFC del usuario en dit_persona y valida 
	 */
	@RequestMapping(value = "/actualizaRfcUsuario", method = RequestMethod.GET)
	public void actualizaRfcUsuario(@RequestParam("rfc") String rfc,
			HttpServletRequest request, HttpSession session) throws Exception{
		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);
		Fisica fisica = null;
		log.debug("el rfc del certificado es: " + rfc);
		try {
			fisica = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(usuariosso.getIdPersona().longValue());
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error("No fue encontrado el id de la persona fisica");
		}
		log.debug("la fisica del usuario es" + fisica);
		if(fisica != null &&fisica.getRfc() != null) {
			if(!fisica.getRfc().equalsIgnoreCase(rfc)) 
				throw new Exception("El RFC del certificado es diferente al del usuario registrado");
		}
		if(fisica == null || fisica.getCveFisica() == null || fisica.getRfc() == null) {
			log.debug("llegue a actualizar el RFC");
			boolean actualizaRfcPersoona= false;
			try {
				if(fisica== null) {
					actualizaRfcPersoona = true;
					log.debug("la fisica es nula ");
					fisica = new Fisica();
				}
				if(fisica.getRfc()== null)
					actualizaRfcPersoona = true;
				log.debug("el rfc del certificado es " + rfc);
				fisica.setRfc(rfc);
				fisica.setIdPersona(usuariosso.getIdPersona().longValue());
				try {
					Long cveIdFiscia= personaFisicaServiceBusiness.obtenerIDPersonaFisica(fisica.getIdPersona());
					fisica.setCveFisica(cveIdFiscia);
				}catch (PersonaFisicaNoEncontradaException e) {
					log.error("error al consultar el idPesonaFisica", e);
					if(e.getMessage().contains("existen")) 
						throw e;
				}
				if(fisica.getCveFisica() == null) {
					log.debug("llegue a guardar la idPeronsafisica" + fisica);
					fisica =personaFisicaServiceBusiness.guardarPersonaFisica(fisica);
					log.debug("fisica despues de insertar en idPesonaFisica" + fisica);
				}
				log.debug("el valor del id fisica es" + fisica.getCveFisica());
				if(actualizaRfcPersoona) {
					log.debug("llege a actualzarel rfc de la persona" + fisica.getRfc());
					AfectarDatosPersonaWrapper datosPersona = new AfectarDatosPersonaWrapper();
					datosPersona.setModificarRFC(true);
					datosPersona.setFisica(fisica);
					log.debug("la fisica a actualziar es:" + fisica);
					personaFisicaServiceBusiness.afectarDatosPersonaFisica(datosPersona);
				}
			}catch(Exception e) {
				log.error("ocurrio un error al querer actualizar los datos del usuario", e);
				throw new Exception("ocurrio un error al querer actualizar el RFC del usuario " 
						+ e.getMessage());
			}
		}


	}

}
