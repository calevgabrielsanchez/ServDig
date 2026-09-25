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
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

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
@RequestMapping(value="/home")
public class HomeController extends AbstractController {
	private static final Long ROL_VENTANILLA = 100L;
	private static final Long ROL_EXTERNO = 0L;

	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("solicitud", new Solicitud());
		return "home";
	}
	
	
	
	@RequestMapping(value="/ventanilla", method=RequestMethod.GET)
    public String login(Model model,  HttpSession session,HttpServletRequest request) {
		
		generarUsuarioSession(model, session, request);
        
        return "home";
    }
	
	public void generarUsuarioSession(Model model, HttpSession session,
			HttpServletRequest request) {

		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);

		String[] roles = { "ROLE_VENTANILLA" };
		boolean hasRol = this.checkGrantedAuthorities(roles);

		this.log.debug("Tiene el rol " + hasRol);

		Usuario usuario = new Usuario();
		usuario.setUsuario(usuariosso.getNombre());
		
		PerfilUsuario pu = new PerfilUsuario();
		if (hasRol) {
			pu.setIdPerfilUsuario(ROL_VENTANILLA);
		} else {
			pu.setIdPerfilUsuario(ROL_EXTERNO);
		}
		pu.setDescripcion(usuariosso.getNombre());
		usuario.setPerfilUsuario(pu);

		/*
		 * Se crean los objetos necesarios para ligar el usuario con la
		 * subdelegacion y delegacion.
		 */
		UsuarioFuncionario uf = new UsuarioFuncionario();

		if (usuariosso.getSubdelegacion() != null) {
			Subdelegacion sub = solicitudBusinessRemote
					.getDatosSubdelegacion(usuariosso.getSubdelegacion()
							.longValue());
			uf.setDelegacion(sub.getDelegacion());
			uf.setSubdelegacion(sub);
			
			/*
			 * Se checa si el usuario firmado pertenece a la delegacion Baja
			 * California Norte (2) subdelegacion Los Angeles(8), en caso de ser
			 * asi, se debe mostrar la opcion de SIME.
			 */
			if (sub != null && sub.getId().intValue() == 8) {
				model.addAttribute("SHOW_SIME", true);
			}
		}

		/*
		 * Se checa si el usuario tiene el rol requerido para la administración
		 * de series
		 */
		String[] rolesAdmonSeries = {"ROLE_TITULAR DE LA DIVISION DE SOPORTE A LOS PROCESOS DE AFILIACION"};
		hasRol = this.checkGrantedAuthorities(rolesAdmonSeries);
		
		if (hasRol) {
			model.addAttribute("SHOW_ADMON_SERIES", true);
		}
		
		
		uf.setUsuario(usuario);
		usuario.setUsuarioFuncionario(uf);
		
		// Subimos a la sesion la informacion del usuario
		session.setAttribute(KEY_USUARIO, usuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("solicitud", new Solicitud());
		this.setFechaSistema(session);
		
	}
	
}
