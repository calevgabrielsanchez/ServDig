/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portal
 *  @Archivo:PortalController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.solicitud.visor.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.solicitud.visor.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
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
@RequestMapping(value = "/portal")
public class PortalController extends AbstractController {

	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;

	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model, HttpSession session,
			HttpServletRequest request) {

		initCommon(model, session, request, false);

		session.removeAttribute("FILTROS_SESSION");

		return "home";
	}

	@RequestMapping(value = "/regresar", method = RequestMethod.POST)
	public String regresar(Model model, HttpSession session,
			HttpServletRequest request) {

		initCommon(model, session, request, true);

		return "home";
	}

	private void initCommon(Model model, HttpSession session,
			HttpServletRequest request, boolean fromRegresar) {

		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);
		Usuario usuario = getUsuarioSesion(usuariosso);

		// Subimos a la sesion la informacion del usuario
		session.setAttribute(KEY_USUARIO, usuario);
		model.addAttribute("usuario", usuario);
		model.addAttribute("idPersona", usuariosso.getIdPersona());
		log.info("idPersona: " + usuariosso.getIdPersona());

		model.addAttribute("solicitud", new Solicitud());
		this.setFechaSistema(session);

		request.setAttribute("fromRegresar", fromRegresar);
	}

	private Usuario getUsuarioSesion(UsuarioSSO usuariosso) {
		Usuario usuario = new Usuario();
		usuario.setUsuario(usuariosso.getNombre());

		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion(usuariosso.getPerfil());
		usuario.setPerfilUsuario(pu);

		// Se crean los objetos necesarios para ligar el usuario con la
		// subdelegacion y delegacion.
		UsuarioFuncionario uf = new UsuarioFuncionario();
		String[] listSistemas = usuariosso.getSistemas();

		List<Modulo> listModulo = null;
		Modulo objSistema = null;
		if (listSistemas != null && listSistemas.length > 0) {
			String strSistema = null;
			listModulo = new ArrayList<Modulo>();
			for (int i = 0; i < listSistemas.length; i++) {
				strSistema = listSistemas[i];
				this.log.debug("Módulo a buscar en el ENUM [" + strSistema
						+ "]");
				if (ModuloEnum.getEnumByDesc(strSistema) != null) {
					objSistema = new Modulo();
					objSistema.setDescripcion(strSistema);
					objSistema.setIdModulo(ModuloEnum.getEnumByDesc(strSistema)
							.getCodigo().longValue());
					listModulo.add(objSistema);
				} else {
					this.log.warn("El sistema [" + strSistema
							+ "] no fue encontrado en el ENUM");
				}
			}
			uf.setModuloSistema(listModulo);
		}
		
		if (usuariosso.getSubdelegacion() != null) {

			Subdelegacion subdelgacion = this.domicilioServiceBusiness
					.obtenerSubdelegacionPorId(usuariosso.getSubdelegacion()
							.longValue());
			this.log.debug("Subdelegacion encontrada para usuario en el visor - "
					+ subdelgacion);

			uf.setSubdelegacion(subdelgacion);
			uf.setDelegacion(subdelgacion.getDelegacion());
		} else if (usuariosso.getDelegacion() != null) {

			Delegacion delegacion = this.domicilioServiceBusiness
					.obtenerDelegacionPorId(usuariosso.getDelegacion()
							.longValue());

			this.log.debug("Delegacion encontrada para usuario en el visor - "
					+ delegacion);

			uf.setDelegacion(delegacion);

		} else {
			this.log.info("El usuario " + usuario.getUsuario()
					+ " no cuenta con delegacion ni subdelegacion");
		}

		uf.setUsuario(usuario);
		usuario.setUsuarioFuncionario(uf);

		return usuario;
	}
}
