package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.movPat;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.movPat.internet.wizard.WizardClasificacionMovPatInternetController;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.DELTA_SESSION_VARIABLES;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;

@Controller
@RequestMapping(value="/movPat")
public class AccesoController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(AccesoController.class);
	
	
	@Autowired
	private AfiliacionServiceBusinessRemote afiliacionBusiness;

	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;

	@Autowired
	private WizardClasificacionMovPatInternetController wizardClasificacionMovPatInternetController;
	
	@Autowired
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;

	@RequestMapping(value = "/acceso/ventanilla/dummy", method = {RequestMethod.GET, RequestMethod.POST})
	public String accespDummy(@ModelAttribute Usuario usuario, BindingResult result,
			Model model, SessionStatus status, HttpSession session, HttpServletRequest request) {
		UsuarioSSO sso = this.getUsuarioSSODummy();
		Usuario usuarioSession = this.getUsuarioSesion(sso, session);
		log.debug("el usuario obj es" + usuarioSession.getCveIdUsuario() + " y el sso es " + sso.getCurp());
		model.addAttribute("idPersonaFisica", "1L");
		model.addAttribute("rfcFisica", "PRUEBAS");
		model.addAttribute("tipoPersona", "2");
		session.setAttribute("typeLogin", "1");
		model.addAttribute("objFisica", new Fisica());

		this.setFechaSistema(session);
		session.setAttribute(KEY_USUARIO, usuarioSession);
		
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_ORIGEN_SOLICITUD, OrigenSolicitudEnum.VENTANILLA.getId());

		return "viewHomeVentanilla";
	}

	@RequestMapping(value = "/acceso/ventanilla/home", method = {RequestMethod.GET, RequestMethod.POST})
	public String homme(@ModelAttribute Usuario usuario, BindingResult result,
							  Model model, SessionStatus status, HttpSession session, HttpServletRequest request) {

		UsuarioSSO sso = this.procesarUsuarioSSO(request); // TODO: quitar dummy cuando acceso quede funcional
//		UsuarioSSO sso = this.getUsuarioSSODummy();
		usuario = this.getUsuarioSesion(sso, session);
//		model.addAttribute("idPersonaFisica", "1L");
//		model.addAttribute("rfcFisica", "PREUBAS");
//		model.addAttribute("tipoPersona", "2");
		session.setAttribute("typeLogin", "1");
//		model.addAttribute("objFisica", new Fisica());
		this.setFechaSistema(session);
		session.setAttribute(KEY_USUARIO, usuario);
		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_ORIGEN_SOLICITUD, OrigenSolicitudEnum.VENTANILLA.getId());
		return "viewHomeVentanilla";

	}

	/**
	 * Cancela las solicitudes activas; limpia las variables de sesi?n
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping( value = "/acceso/logout")
	public @ResponseBody String logout(Model model, HttpSession session, HttpServletRequest request) {
		log.debug("Ingresando a Invalidar la sesi?n :: {}");
		session.removeAttribute("keyPatronesSustitucionFusion");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_ID_SOLICITUD);
		session.removeAttribute("folioSolicitud");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_TRAMITE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_TRAMITE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_OBLIGADO);
		session.removeAttribute("showFinalizarFD");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_DESCRIPCION_TIPO_TRAMITE);
		session.removeAttribute("mensajeError");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_SOLICITUD);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_RFC_SOLICITANTE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_CADENA_ORIGINAL);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_FIRMA_ELECTRONICA);
		session.invalidate();
		model.addAttribute("usuario", new Usuario());
		return "logout";
	}

	@RequestMapping(value = "/internet/home", method = { RequestMethod.GET, RequestMethod.POST })
	public String homeInternet(@ModelAttribute Usuario usuario, BindingResult result, Model model,
			SessionStatus status, HttpSession session, HttpServletRequest request) {

		SujetoObligado sujetoObligado = (SujetoObligado) session.getAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_OBLIGADO);
		Fisica personaRenapo = (Fisica) session.getAttribute(DELTA_SESSION_VARIABLES.KEY_PERSONA_FISICA_TRAMITANTE);
		Usuario usuarioSession = (Usuario) session.getAttribute(DELTA_SESSION_VARIABLES.KEY_USUARIO_SESION);
		log.debug("el usuario obj es" + usuarioSession.getCveIdUsuario());
		session.setAttribute("typeLogin", "1");
		this.setFechaSistema(session);
		session.setAttribute(KEY_USUARIO, usuarioSession);

		String nombre;
		String registroPatronal = sujetoObligado.getNumeroRegistroPatronal();  
		
		if (sujetoObligado.getFisica() != null) {
			nombre = sujetoObligado.getFisica().getNombreCompleto();
		} else {
			nombre = sujetoObligado.getMoral().getRazonSocial();
		}
		
		if (registroPatronal.length() == 8) {
			registroPatronal = sujetoObligado.getNumeroRegistroPatronal() + sujetoObligado.getModalidad().getNumModalidad();  
		} 
		

		session.setAttribute("nombre", personaRenapo.getNombreCompleto());
		session.setAttribute("curp", usuarioSession.getCveIdUsuario()); //curp
		session.setAttribute("registroPatronal", registroPatronal);

		//wizardClasificacionMovPatInternetController.initModificarDatosPersona(model, session, request, sujetoObligado.getNumeroRegistroPatronal(), 11);

		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_ORIGEN_SOLICITUD, OrigenSolicitudEnum.INTERNET.getId());

		return "viewHomeInternet";
	}

	/**
	 * @param sso
	 * @param session
	 * @return
	 */
	public Usuario getUsuarioSesion(UsuarioSSO sso, HttpSession session) {
		Usuario usuario = new Usuario();
		usuario.setUsuario(sso.getNombre().toUpperCase());
		usuario.setCveIdUsuario(sso.getCurp());
		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion(sso.getPerfil());
		pu.setIdPerfilUsuario(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());
		usuario.setPerfilUsuario(pu);
		UsuarioFuncionario uf = new UsuarioFuncionario();
		if (sso.getSubdelegacion() != null) {
			Subdelegacion subdel = afiliacionBusiness.obtenerSubdelegacion(sso.getSubdelegacion().longValue());
			usuario.setCveIdSubdelegacion(sso.getSubdelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			uf.getSubdelegacion().setClave(subdel.getClave());
			uf.getSubdelegacion().setDescripcion(subdel.getDescripcion());
			uf.getSubdelegacion().setDelegacion(new Delegacion());
			uf.getSubdelegacion().getDelegacion().setClave(subdel.getDelegacion().getClave());
			uf.getSubdelegacion().getDelegacion().setDescripcion(subdel.getDelegacion().getDescripcion());
			uf.getSubdelegacion().getDelegacion().setId(subdel.getDelegacion().getId());
			uf.setDelegacion(uf.getSubdelegacion().getDelegacion());
		}
		uf.setUsuario(usuario);
		usuario.setUsuarioFuncionario(uf);
		// TODO revisar si se tiene que setear este ID
		usuario.setCveIdUsuario(sso.getCurp());
		return usuario;
	}

	private UsuarioSSO getUsuarioSSODummy() {
		UsuarioSSO usuario = new UsuarioSSO();
		usuario.setNombre("MOV PAT USER");
		usuario.setIdUsuario(1);
		usuario.setCurp("HELA780902HTCRNL08");
		usuario.setPerfil("VENTANILLA");
		usuario.setDelegacion(39);
		usuario.setSubdelegacion(137);
		return usuario;
	}

	@RequestMapping(value = "/internet/acceso", method = {RequestMethod.GET, RequestMethod.POST})
	public String accesInternet(@ModelAttribute Usuario usuario, BindingResult result,
			Model model, SessionStatus status, HttpSession session, HttpServletRequest request) {

		return "viewLoginInternet";
	}
	
    @RequestMapping(value = "/internet/validarAcceso", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarAccesoInternet(
			@RequestParam("curp") String curp, @RequestParam("email") String email,
			@RequestParam("registroPatronal") String registroPatronal,
			HttpServletResponse response, HttpSession session, Locale locale){
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	try{
    		Fisica personaRenapo = localizarPersonaFisicaEnRENAPOServiceBusiness.localizarPersonaFisicaEnRENAPOxCURP(curp);
    		if(personaRenapo == null){
    			Object[] args = null;
    			String mensaje = messageSource.getMessage("error.renapo.curp.invalido", args, locale);
    			result.put("mensajeError", mensaje);
    			return result;
    		}
    		
    		SujetoObligado sujetoObligado = sujetoObligadoService.consultarPorNumeroRegistroPatronal(registroPatronal);
    		if(sujetoObligado == null){
    			Object[] args = null;
    			String mensaje = messageSource.getMessage("error.registro.patronal.inexistente.movPat", args, locale);
    			result.put("mensajeError", mensaje);
    			return result;
    		}
    		//Colocamos la informacion del CURP  Validar si es correcto
    		
    		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_PERSONA_FISICA_TRAMITANTE, personaRenapo);
    		
    		sujetoObligado.setNumeroRegistroPatronal(registroPatronal);
    		Usuario user = new Usuario();
    		user.setCorreo(email);
    		user.setCorreoConfirmacion(email);
    		user.setCveIdUsuario(curp);
    		PerfilUsuario pu = new PerfilUsuario();
    		pu.setDescripcion(CodigoRolTemporal.TRAMITADOR.name());
    		pu.setIdPerfilUsuario(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());
    		user.setPerfilUsuario(pu);
    	    		
    		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_USUARIO_SESION, user);
    		session.setAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_OBLIGADO, sujetoObligado);
    	} catch (CURPNoLocalizadoEnEntidadExternaException ce) {
    		result.put("mensajeError", ce.getMessage());
    	} catch (ClienteWebserviceRenapoCurpException cw) {
    		result.put("mensajeError", cw.getMessage());
    	} catch (ErrorValidacionDatosConsultaEnEntidaExternaException ev) {
    		result.put("mensajeError", ev.getMessage());
    	} catch (Exception e) {
    		String mensaje = messageSource.getMessage(e.getMessage(), null, locale);
    		result.put("mensajeError", mensaje);
		}
    	 
    	return result;
    }
	
    
	@RequestMapping(value = "/internet/clean/acceso",  method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cleanAccesInternet(@ModelAttribute Usuario usuario, BindingResult result,
			Model model, SessionStatus status, HttpSession session, HttpServletRequest request) {
		Map<String, Object> resultA = new HashMap<String, Object>();
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_USUARIO_SESION);
		session.removeAttribute("keyPatronesSustitucionFusion");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_ID_SOLICITUD);
		session.removeAttribute("folioSolicitud");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_TRAMITE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_TRAMITE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_SUJETO_OBLIGADO);
		session.removeAttribute("showFinalizarFD");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_DESCRIPCION_TIPO_TRAMITE);
		session.removeAttribute("mensajeError");
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_TIPO_SOLICITUD);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_RFC_SOLICITANTE);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_CADENA_ORIGINAL);
		session.removeAttribute(DELTA_SESSION_VARIABLES.KEY_FIRMA_ELECTRONICA);
		session.invalidate();
		return resultA;
	}
}
