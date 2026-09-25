package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;


import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/menutramites")
public class MenuTramiteController extends AbstractController {

	@Autowired ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	
	
	@RequestMapping(value = "/principal", method = RequestMethod.GET)
	public String login(Model model, HttpSession session,
			HttpServletRequest request) {
		
		Log.info("Redireccionando a pagina principal");
		return "viewLoginAltaPatronal";
	}
	
	
	@RequestMapping(value = "/ingresar", method = RequestMethod.GET)
	public String ingresarTramites(
			@ModelAttribute SujetoObligado patronAsociado, Model model,
			HttpSession session, HttpServletRequest request)  {
		
		
		System.out.println("Parametro OrigeenHD  "+request.getParameter("tramite"));
		System.out.println("Request atrribute "+request.getAttribute("tramite"));
		
		System.out.println("Tramites "+request.getParameterNames());
		
		
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
				
		model.addAttribute("idPersonaFisica", fisica.getCveFisica());
		model.addAttribute("rfcFisica", fisica.getRfc());
		model.addAttribute("tipoPersona", TipoPersonaEnum.FISICA.getId());
		model.addAttribute("objFisica", fisica);
		
		
		// Subimos a la sesion la informacion del usuario
		session.setAttribute(KEY_USUARIO, usuario);
		
		model.addAttribute("solicitud", new Solicitud());
		this.setFechaSistema(session);	
		System.out.println("Sesion generada");
		return "viewPrincipal";
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
	
}
