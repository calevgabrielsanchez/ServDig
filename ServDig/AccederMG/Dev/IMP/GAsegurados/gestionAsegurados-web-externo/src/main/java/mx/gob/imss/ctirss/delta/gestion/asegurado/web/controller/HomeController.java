/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: gestionAsegurados
 *  @Archivo:HomeController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Lucio Duran Silva
 *
 */
@Controller
@RequestMapping(value="/home")
public class HomeController extends AbstractController {
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("solicitud", new Solicitud());
		return "home";
	}
	
	
	
	
	
	
	@RequestMapping(value="/asegurado", method=RequestMethod.GET)
    public Object loginAsegurado(Model model, HttpSession session, HttpServletRequest request) {
		/*
		UsuarioSSO sso  = this.procesarUsuarioSSO(request);
		Usuario usuario = new Usuario();
		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion(sso.getNombre());
		pu.setIdPerfilUsuario(new Long(200));
		
		usuario.setPerfilUsuario(pu);
		
		//Subimos a la sesion la informacion del usuario
				session.setAttribute(KEY_USUARIO, usuario);
		        model.addAttribute("usuario", usuario);
		        model.addAttribute("solicitud", new Solicitud());
		        this.setFechaSistema(session);
        return "home";*/
		return new RedirectView("/asignacionNSS", true);
    }
}
