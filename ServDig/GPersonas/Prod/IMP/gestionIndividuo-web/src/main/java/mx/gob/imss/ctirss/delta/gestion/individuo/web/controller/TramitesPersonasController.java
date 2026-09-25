/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Cesar Garcia Mauricio
 * @since 09/03/2012
 * 
 */
@Controller
@RequestMapping(value = "/persona/tramites")
public class TramitesPersonasController extends AbstractController {
	/**
	 * 
	 * @return bienvenido view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String toHomePage(final HttpSession session,
			HttpServletRequest request) {
		cleanSession(session);
		session.setAttribute("role", "internet"); // role por omision
		LOG.debug("To " + BIENVENIDO_VIEW + " view1...");

		UsuarioSSO sso = this.procesarUsuarioSSO(request);

		/*
		 * Procesamos la información del openSSO
		 */
		if (sso != null) {
			ServletContext context = session.getServletContext();
			String rolVentanilla = context.getInitParameter("rolVentanilla");
	        String rolInternet = context.getInitParameter("rolInternet");
	        String[] arrayRolVentanilla = {rolVentanilla};
	        String[] arrayRolInternet = {rolInternet};

			Usuario usuario = new Usuario();
			usuario.setUsuario(sso.getNombre());

			PerfilUsuario pu = new PerfilUsuario();
			if (this.checkGrantedAuthorities(arrayRolVentanilla)) {
				pu.setIdPerfilUsuario(100L);
			} else if (this.checkGrantedAuthorities(arrayRolInternet)) {
				pu.setIdPerfilUsuario(200L);
			}
			pu.setDescripcion(sso.getPerfil());
			usuario.setPerfilUsuario(pu);

			UsuarioFuncionario uf = new UsuarioFuncionario();
			if (sso.getDelegacion() != null) {
				uf.setDelegacion(new Delegacion());
				uf.getDelegacion().setId(sso.getDelegacion().longValue());
			}
			if (sso.getSubdelegacion() != null) {
				usuario.setCveIdSubdelegacion(sso.getSubdelegacion().longValue());
				uf.setSubdelegacion(new Subdelegacion());
				uf.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			}
			uf.setUsuario(usuario);
			usuario.setUsuarioFuncionario(uf);

			// Subimos a la sesion la informacion del usuario
			session.setAttribute(KEY_USUARIO, usuario);
			this.setFechaSistema(session);
		}

		return BIENVENIDO_VIEW;
	}

    /**
     * @return orquestador view name
     */
    @RequestMapping(value = "/agregar/{tipoPersona}", method = RequestMethod.GET)
//  public String haciaElOrquestador(final @RequestParam String tipoPersona, final HttpSession session, final Model model) {
    public String haciaElOrquestador(final @PathVariable String tipoPersona, final HttpSession session, final Model model) {
        LOG.debug("To orquestador view with param " + tipoPersona);
        final String role = (String) session.getAttribute("role");
        LOG.trace("Role de usuario: " + role);
        if ("fisica".equalsIgnoreCase(tipoPersona)) {
            model.addAttribute("isFisica", Boolean.TRUE);
            model.addAttribute("isMoral", Boolean.FALSE);
        } else if ("moral".equalsIgnoreCase(tipoPersona)) {
            model.addAttribute("isFisica", Boolean.FALSE);
            model.addAttribute("isMoral", Boolean.TRUE);
        }

        return "orquestador";
    }

	@RequestMapping(value = "/internet", method = RequestMethod.GET)
	public String dispatchInternetUser(final HttpSession session,
			HttpServletRequest request) {
		cleanSession(session);
		session.setAttribute("role", "internet");
		LOG.debug("To " + BIENVENIDO_VIEW + " view2...");

		UsuarioSSO sso = this.procesarUsuarioSSO(request);

		/*
		 * Procesamos la información del openSSO
		 */
		if (sso != null) {
			ServletContext context = session.getServletContext();
			String rolVentanilla = context.getInitParameter("rolVentanilla");
	        String rolInternet = context.getInitParameter("rolInternet");
	        String[] roles = {rolVentanilla, rolInternet};

			this.checkGrantedAuthorities(roles);
			Usuario usuario = new Usuario();

			PerfilUsuario pu = new PerfilUsuario();
			pu.setIdPerfilUsuario(new Long(100));
			pu.setDescripcion(sso.getNombre());
			usuario.setPerfilUsuario(pu);

			UsuarioFuncionario uf = new UsuarioFuncionario();
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(sso.getDelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			uf.setUsuario(usuario);
			usuario.setUsuarioFuncionario(uf);

			// Subimos a la sesion la informacion del usuario
			session.setAttribute(KEY_USUARIO, usuario);
			this.setFechaSistema(session);
		}

		return BIENVENIDO_VIEW;
	}

	@RequestMapping(value = "/ventanilla", method = RequestMethod.GET)
	public String dispatchVentanillaUser(final HttpSession session,
			HttpServletRequest request) {
		cleanSession(session);
		session.setAttribute("role", "ventanilla");
		LOG.debug("To " + BIENVENIDO_VIEW + " view3...");

		UsuarioSSO sso = this.procesarUsuarioSSO(request);

		/*
		 * Procesamos la información del openSSO
		 */
		if (sso != null) {
			ServletContext context = session.getServletContext();
			String rolVentanilla = context.getInitParameter("rolVentanilla");
	        String rolInternet = context.getInitParameter("rolInternet");
	        String[] roles = {rolVentanilla, rolInternet};

			this.checkGrantedAuthorities(roles);
			Usuario usuario = new Usuario();

			PerfilUsuario pu = new PerfilUsuario();
			pu.setIdPerfilUsuario(new Long(100));
			pu.setDescripcion(sso.getNombre());
			usuario.setPerfilUsuario(pu);

			UsuarioFuncionario uf = new UsuarioFuncionario();
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(sso.getDelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			uf.setUsuario(usuario);
			usuario.setUsuarioFuncionario(uf);

			// Subimos a la sesion la informacion del usuario
			session.setAttribute(KEY_USUARIO, usuario);
			this.setFechaSistema(session);
		}

		return BIENVENIDO_VIEW;
	}

    /**
     * 110512
     * Este metodo es para abrir una nueva pantalla de registro de persona fisica desde el boton que aparece en la parte inferior del data teibol (Trámite de registro de 
     * personas físicas) de la pagina de busqueda de persona fisica. Ya que es necesario meter en la sesion el rol "role" con el valor "ventanilla", no podemos 
     * accesar a la pagina directamente. Entonces hay que meter primero este dato en la sesion y despues direccionar a la jsp de registro de personas fisicas.
     * 
     * Este mismo metodo tambien funciona para entrar con un rol "internet" en caso de que posteriormente se requiera
     * @param idPersona
     * @param modelo
     * @return
     */
    @RequestMapping(value = "/agregar/{tipoPersona}/{rol}", method = RequestMethod.GET)
    public String buscarDatosComplementarios(final @PathVariable String tipoPersona, final @PathVariable String rol, final HttpSession session, final Model model) {
        
        LOG.debug("Se entrará desde la pantalla DE BUSQUEDA DE PERSONAS a la pantalla Orquestador.jsp y de acuerdo al parametro: " + tipoPersona + " va a abrir el orquestador especifico");
        
        cleanSession(session);
        session.setAttribute("role", rol);
        
        final String role = (String) session.getAttribute("role");
        LOG.trace("Role de usuario: " + role);
        if ("fisica".equalsIgnoreCase(tipoPersona)) {
            model.addAttribute("isFisica", Boolean.TRUE);
            model.addAttribute("isMoral", Boolean.FALSE);
//            model.addAttribute("subtitulo", "Registro de Persona Física");
        } else if ("moral".equalsIgnoreCase(tipoPersona)) {
            model.addAttribute("isFisica", Boolean.FALSE);
            model.addAttribute("isMoral", Boolean.TRUE);
//            model.addAttribute("subtitulo", "Registro de Persona Moral");
        }

        return "orquestador";
        
    }

    private void cleanSession(final HttpSession session) {
        session.removeAttribute("wrapperSessionDatosPersonaFisicaSalidaPaginador");
        session.removeAttribute("wrapperSessionDatosPersonaMoralSalidaPaginador");
    }

    private static final Logger LOG;
    private static final String BIENVENIDO_VIEW = "bienvenido";

    static {
        LOG = LoggerFactory.getLogger(TramitesPersonasController.class);
    }

}