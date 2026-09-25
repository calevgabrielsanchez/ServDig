/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portalExpediente
 *  @Archivo:HomeController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.portal.expediente.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.beneficio.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Lucio Duran Silva
 * 
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController extends AbstractController {

	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model, HttpSession session,
			HttpServletRequest request) {

		generarUsuarioSession(model, session, request);
		
		return "home";
	}

	
	public void generarUsuarioSession(Model model, HttpSession session,
			HttpServletRequest request) {

		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);

		Usuario usuario = new Usuario();
		usuario.setUsuario(usuariosso.getNombre());

		PerfilUsuario pu = new PerfilUsuario();
		
		pu.setDescripcion(usuariosso.getNombre());
		usuario.setPerfilUsuario(pu);

		/*
		 * Se crean los objetos necesarios para ligar el usuario con la
		 * subdelegacion y delegacion.
		 */
		UsuarioFuncionario uf = new UsuarioFuncionario();

		if (usuariosso.getSubdelegacion() != null) {
			Subdelegacion sub = this.domicilioServiceBusiness
					.obtenerSubdelegacionPorId(usuariosso.getSubdelegacion()
							.longValue());
			uf.setDelegacion(sub.getDelegacion());
			uf.setSubdelegacion(sub);
		}

		uf.setUsuario(usuario);
		usuario.setUsuarioFuncionario(uf);

		// Subimos a la sesion la informacion del usuario
		session.setAttribute(KEY_USUARIO, usuario);
		model.addAttribute("usuario", usuario);
		
		this.setFechaSistema(session);

	}
}
