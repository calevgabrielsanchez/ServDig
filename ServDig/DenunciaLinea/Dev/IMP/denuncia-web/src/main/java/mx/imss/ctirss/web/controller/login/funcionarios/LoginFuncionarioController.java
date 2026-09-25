/**
 * LoginController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.imss.ctirss.web.controller.login.funcionarios;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.imss.ctirss.catalogos.model.DlcMenu;
import mx.imss.ctirss.catalogos.model.DlcPerfilUsuario;
import mx.imss.ctirss.catalogos.model.DlcRol;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.catalogos.model.DlcUsuarioFuncionario;
import mx.imss.ctirss.login.service.interfaces.LoginService;
import mx.imss.ctirss.login.service.interfaces.PerfilService;
import mx.imss.ctirss.menu.service.interfaces.MenuService;
import mx.imss.ctirss.session.ConstantesSession;
import mx.imss.ctirss.session.UserSession;
import mx.imss.ctirss.web.controller.login.AbsractSeguridadController;
import mx.imss.ctirss.web.controller.login.WelcomeFuncionarioController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Saul Rosales Piedragil
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 14/09/2012
 */
@Controller
@RequestMapping(value = "/loginFuncionario")
public class LoginFuncionarioController extends AbsractSeguridadController {

	@Autowired
	private LoginService<DlcUsuario> loginServiceBean;

	@Autowired
	private MenuService<DlcMenu> menuServiceBean;

	@Autowired
	private PerfilService<DlcPerfilUsuario> perfilService;

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(LoginFuncionarioController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getCreateForm(Model model) {
		logger.debug("Creando forma...");
		model.addAttribute(new DlcUsuarioFuncionario());

		return "acceso";
	}

	@RequestMapping(value = "/validarCredencialesFuncionario", method = RequestMethod.POST)
	public String validarCredenciales(DlcUsuario dlcUsuario,
			BindingResult result, HttpServletResponse response,
			HttpServletRequest request) {
		logger.info("login.getNomUsuarioSistema() :: "
				+ dlcUsuario.getNomUsuarioSistema());
		if (this.isNotEmpty(dlcUsuario.getNomUsuarioSistema())
				&& this.isNotEmpty(dlcUsuario.getRefPassword())) {
			DlcUsuario usrFirmado = this.loginServiceBean.validarCredencialesFuncionario(dlcUsuario);
			logger.info("usrFirmado :: " + usrFirmado);
			if (usrFirmado != null) {
							

				if (usrFirmado.getUsuarioFuncionario().getIndVigencia() != null
						&& usrFirmado.getUsuarioFuncionario().getIndVigencia()) {

					final UserSession usrSession = this.transformarUsuario(usrFirmado);
					
					DlcRol rol= loginServiceBean.consultaRolUsuario(usrFirmado.getCveIdUsuario());
					if(rol != null){
						usrSession.setCveRol(rol.getCveRol());
						usrSession.setDescripcionRol(rol.getDescRol());
					}
					
					request.getSession().setAttribute(ConstantesSession.USR_SESSION, usrSession);
					dlcUsuario.setCveIdUsuario(usrFirmado.getCveIdUsuario());
					final DlcPerfilUsuario spuParam = new DlcPerfilUsuario();
					spuParam.setDlcUsuario(usrFirmado);
					dlcUsuario.setPerfilesDisponibles(this.transformarPerfiles(perfilService
									.recuperarPerfiles(spuParam)));
					logger.info("Camino a seleccionar perfil de usuario");
					return "loginFuncionario/perfil";// para seleccionar el perfil
				} else {
					result.rejectValue("nomUsuarioSistema", "",
							"El usuario ha sido dado de baja.");
					return "acceso";
				}
			}
			logger.info("Intento fallido!!!");
			result.rejectValue("nomUsuarioSistema", "",
					"El nombre de usuario o la contrase\u00F1a introducidos no son correctos.");
		} else {
			result.rejectValue("nomUsuarioSistema", "",
					"El nombre de usuario y la contrase\u00F1a son requeridos.");
		}
		return "accesoFuncionario";
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	private boolean isNotEmpty(String val) {
		return (val != null && val.length() > 0 && val.trim().length() > 0);
	}

	/**
	 * 
	 * @param recuperarPerfiles
	 * @return
	 */
	private Map<Long, String> transformarPerfiles(
			List<DlcPerfilUsuario> recuperarPerfiles) {
		Map<Long, String> pds = new LinkedHashMap<Long, String>();
		if (recuperarPerfiles != null && !recuperarPerfiles.isEmpty()) {
			for (DlcPerfilUsuario perfil : recuperarPerfiles) {
				pds.put(perfil.getCveIdPerfilUsuario(),
						perfil.getDlcRol().getDescRol());
			}
		}
		return pds;
	}

	/**
	 * 
	 * @param dlcUsuario
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/seleccionarPerfilFuncionario", method = RequestMethod.POST)
	public String seleccionarPerfil(DlcUsuario dlcUsuario,
			HttpServletResponse response, HttpServletRequest request) {
		UserSession usrSession = super.getUsuarioFirmado(request);
		final DlcMenu menuParam = new DlcMenu();
		menuParam.setIdUsuario(usrSession.getCveIdUsuario());
		menuParam.setIdPerfil(dlcUsuario.getIdPerfil());
		usrSession.setMenu(this.transformarMenu(menuServiceBean.consultar(menuParam)), request);
		logger.debug("camino a welcome");
		return new WelcomeFuncionarioController().home();
	}

	/**
	 * 
	 * @param response
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/redirectFuncionario")
	public String redirectLogin(HttpServletResponse response, Model model,
			HttpServletRequest request) {
		logger.warn("Se ha perdido la sesion en una peticion JSon");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return new WelcomeFuncionarioController().home();
	}

	/**
	 * 
	 * @param inUsrFirmado
	 * @return
	 */
	private UserSession transformarUsuario(DlcUsuario inUsrFirmado) {
		final UserSession usr = new UserSession();
		usr.setCveIdUsuario(inUsrFirmado.getCveIdUsuario());
		usr.setNomUsuarioSistema(inUsrFirmado.getNomUsuarioSistema());
		usr.setNomNombre(inUsrFirmado.getNomNombre());
		usr.setNomPaterno(inUsrFirmado.getNomPaterno());
		usr.setNomMaterno(inUsrFirmado.getNomMaterno());
		usr.setCveIdPersona(inUsrFirmado.getCveIdPersona());
		if (inUsrFirmado.getUsuarioFuncionario() != null) {
			DlcUsuarioFuncionario fun = inUsrFirmado.getUsuarioFuncionario();
			usr.setCveIdFuncionario(fun.getCveIdUsuarioFuncionario());
			if (fun.getDlcDelegacion() != null) {
				usr.setIdDelegacion(fun.getDlcDelegacion().getCvePk());
				usr.setCveCodigoDelegacion(fun.getDlcDelegacion()
						.getCveCodigo());
				usr.setNombreDelegacion(fun.getDlcDelegacion().getNomNombre());
			}

			if (fun.getDlcSubdelegacion() != null) {
				usr.setIdSubDelegacion(fun.getDlcSubdelegacion().getCveSubdelegacion());
				usr.setCveCodigoSubDelegacion(fun.getDlcSubdelegacion().getCveCodigo());
				usr.setNombreSubDelegacion(fun.getDlcSubdelegacion().getNomNombre());
			}
		}
		return usr;
	}

}
