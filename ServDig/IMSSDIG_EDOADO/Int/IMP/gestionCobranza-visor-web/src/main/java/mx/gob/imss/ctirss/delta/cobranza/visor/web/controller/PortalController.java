/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portal
 *  @Archivo:PortalController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.cobranza.visor.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.ctirss.delta.cobranza.visor.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Lucio Duran Silva
 *
 */
@Controller
@RequestMapping(value="/portal")
public class PortalController extends AbstractController {


	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model, HttpSession session,
			HttpServletRequest request) {
		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);
		Usuario usuario = getUsuarioSesion(usuariosso);

		// Subimos a la sesion la informacion del usuario
		session.setAttribute(KEY_USUARIO, usuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("idPersona", usuariosso.getIdPersona());
		log.info("idPersona: " + usuariosso.getIdPersona());

		model.addAttribute("solicitud", new Solicitud());
		this.setFechaSistema(session);

		return "home";
	}
	
	private Usuario getUsuarioSesion(UsuarioSSO usuariosso) {
		Usuario usuario = new Usuario();
		usuario.setUsuario(usuariosso.getNombre());

		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion(usuariosso.getNombre());
		usuario.setPerfilUsuario(pu);

		// Se crean los objetos necesarios para ligar el usuario con la
		// subdelegacion y delegacion.
		UsuarioFuncionario uf = new UsuarioFuncionario();
		uf.setDelegacion(new Delegacion());
		uf.getDelegacion().setId(usuariosso.getDelegacion().longValue());
		uf.setSubdelegacion(new Subdelegacion());
		uf.setUsuario(usuario);
		uf.getSubdelegacion().setId(usuariosso.getSubdelegacion().longValue());
		usuario.setUsuarioFuncionario(uf);

		return usuario;
	}
}
