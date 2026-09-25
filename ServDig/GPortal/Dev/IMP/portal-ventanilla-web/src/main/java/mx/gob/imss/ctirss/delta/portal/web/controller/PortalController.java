/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portalExpediente
 *  @Archivo:HomeController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.portal.expediente.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.ctirss.delta.portal.web.controller;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.InfoComplementariaTramiteException;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteInfo;
import mx.gob.imss.ctirss.delta.portal.web.model.TipoFiltroEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Lucio Duran Silva
 * 
 */
@Controller
@RequestMapping(value = "/portal")
public class PortalController extends AbstractController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PortalController.class);
	
	@Autowired
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	@Autowired
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;
	@Autowired
	private SolicitudTramiteBusinessRemote solicitudTramiteBusiness;

	@RequestMapping(method = RequestMethod.GET, params = "goto")
	public ModelAndView redirect(Model model,
			@RequestParam("goto") String redirectparam) {
		/*
		 * Si viene de otra aplicacion, redirigir a j_spring_security_check de
		 * la otra aplicacion
		 */
		if (loginExiste() && !redirectparam.contains("/portal-ventanilla-web/")
				&& redirectparam.contains("j_spring_security_check")) {
			LOG.debug("***************************************");
			LOG.debug("Redirigiendo a : --> {}", redirectparam);
			LOG.debug("***************************************");
			return new ModelAndView(new RedirectView(redirectparam));
		}
		return new ModelAndView("portal", "usuario", new Usuario());
	}

	private boolean loginExiste() {
		boolean found = null != SecurityContextHolder.getContext()
				.getAuthentication();
		LOG.debug("Desde /portal-ventanill-web/portal --> loginExiste()? {}",
				found);
		return found;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model, HttpSession session,
			HttpServletRequest request) {

		generarUsuarioSession(model, session, request);

		return "home";
	}

	@RequestMapping(value = "/tramite", method = RequestMethod.POST)
	public String gestionTramite(Model model, HttpServletRequest request) {

		TramiteInfo tramiteInfo = null;
		
		String idTramite = request.getParameter("tramite");
		String filtro = request.getParameter("filtro");
		
		String tipoTramiteCifrado = null;

		this.log.debug("Tramite recibido para iniciar en ventanilla "
				+ idTramite + " con tipo de filtro "
				+ TipoFiltroEnum.obternerEnumById(Integer.valueOf(filtro)).getDesc());

		try {
			tipoTramiteCifrado = Base64Cipher.cifrar(idTramite);

			this.log.debug("Tramite cifrado recibido para iniciar en ventanilla -> "
					+ idTramite);
			
			try {
				tramiteInfo = this.solicitudTramiteBusiness
						.obtenerInfoComplementariaTramite(
								Long.parseLong(idTramite),
								OrigenSolicitudEnum.VENTANILLA.getId());
			} catch (NumberFormatException e) {
				this.log.error("Error al obtener los datos complementarios del trámite", e);
			} catch (InfoComplementariaTramiteException e) {
				this.log.error(e);
			}
			
			List<Long> tiposTramite = new ArrayList<Long>();
			List<Map<String, Object>> documentos = null;
			
			tiposTramite.add(Long.valueOf(idTramite));
			
			documentos = this.documentoProbatorioServiceBusiness.getDocumentosClasificadosPorTipoTramite(tiposTramite);
			boolean changeTittleRP = false;
			if(TipoFiltroEnum.obternerEnumById(Integer.valueOf(filtro)).getDesc().equals(TipoFiltroEnum.RFC_FISICA.getDesc()))
				changeTittleRP = true;
			else if (TipoFiltroEnum.obternerEnumById(Integer.valueOf(filtro)).getDesc().equals(TipoFiltroEnum.RFC_MORAL.getDesc()))
				changeTittleRP = true;
			
			model.addAttribute("changeTittleRP", changeTittleRP);
			model.addAttribute("tipoTramiteCifrado", tipoTramiteCifrado);
			model.addAttribute("tramite", idTramite);
			model.addAttribute("tipoFiltro", filtro);
			model.addAttribute("documentos", documentos);
			model.addAttribute("tramiteInfo", tramiteInfo);

		} catch (InvalidKeyException e) {
			this.log.error(e);
		} catch (IllegalBlockSizeException e) {
			this.log.error(e);
		} catch (BadPaddingException e) {
			this.log.error(e);
		}

		return "vista.tramite";
	}

	private void generarUsuarioSession(Model model, HttpSession session,
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
