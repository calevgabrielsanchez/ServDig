/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: gestionAsegurados
 *  @Archivo:LoginController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller
 *  @Fecha:07/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.web.validator.LoginValidator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Lucio Duran Silva
 *
 */
@Controller
@RequestMapping(value="/login")
public class LoginController extends AbstractController {
	
	
	@RequestMapping(method=RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }
	
	
	
	@RequestMapping(value="/entrar",method=RequestMethod.POST)
	public String entrar(@ModelAttribute Usuario usuario, BindingResult result,
			Model model, SessionStatus status, HttpSession session) {
        
		//TODO: Aplicacion del validator del objeto Usuario
		//TODO: Manejo de los errores y mensajes para la presentacion.
		
		new LoginValidator().validate(usuario, result);
		if(result.hasErrors()){
			return "portal";
		}
		
		
		/**
		 * Validacion alterna para el manejo de los roles
		 */
		//Validacion del usuario de rol Ventanilla
		if(usuario.getUsuario().toUpperCase().equals("VENTANILLA")){
			PerfilUsuario pu = new PerfilUsuario();
			//TODO: Falta crear un enum con los diferentes tipos de perfil 
			pu.setIdPerfilUsuario(new Long(100));
			pu.setDescripcion("Operador de Ventanilla");
			usuario.setPerfilUsuario(pu);
			// Se crean los objetos necesarios para ligar el usuario con la subdelegacion y delegacion.
			UsuarioFuncionario uf = new UsuarioFuncionario();
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(new Long(39));
			uf.setSubdelegacion(new Subdelegacion());
			uf.setUsuario(usuario);
			uf.getSubdelegacion().setId(new Long(137));
			
			usuario.setUsuarioFuncionario(uf);
		}else{
			// Si no es un usuario de VENTANILLA
			
			PerfilUsuario pu = new PerfilUsuario();
			pu.setDescripcion("Usuario externo");
			pu.setIdPerfilUsuario(new Long(200));
			
			usuario.setPerfilUsuario(pu);
			
			
		}
		
		//Subimos a la sesion la informacion del usuario
		session.setAttribute(KEY_USUARIO, usuario);
		this.setFechaSistema(session);
		model.addAttribute("usuario", usuario);
		model.addAttribute("solicitud", new Solicitud());
        return "home";
    }
	

}
