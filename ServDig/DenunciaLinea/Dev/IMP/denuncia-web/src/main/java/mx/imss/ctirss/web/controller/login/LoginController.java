/**
 * LoginController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.imss.ctirss.web.controller.login;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.imss.ctirss.catalogos.base.model.AbstractDlcMenu;
import mx.imss.ctirss.catalogos.base.model.AbstractDlcPerfilUsuario;
import mx.imss.ctirss.catalogos.base.model.AbstractDlcUsuario;
import mx.imss.ctirss.catalogos.model.DlcMenu;
import mx.imss.ctirss.catalogos.model.DlcPerfilUsuario;
import mx.imss.ctirss.catalogos.model.DlcRol;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.catalogos.model.DlcUsuarioFuncionario;
import mx.imss.ctirss.login.model.SegMenu;
import mx.imss.ctirss.login.model.SegPerfilUsuario;
import mx.imss.ctirss.login.model.SegUsuario;
import mx.imss.ctirss.login.model.SegUsuarioFuncionario;
import mx.imss.ctirss.login.service.interfaces.LoginService;
import mx.imss.ctirss.login.service.interfaces.PerfilService;
import mx.imss.ctirss.menu.service.interfaces.MenuService;
import mx.imss.ctirss.model.DltDatospatron;
import mx.imss.ctirss.session.ConstantesSession;
import mx.imss.ctirss.session.UserSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Adolfo Meza Morales
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 19/10/2011
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends AbsractSeguridadController { 

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
			.getLogger(LoginController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getCreateForm(Model model) {
		logger.debug("Creando forma...");
		model.addAttribute(new DlcUsuarioFuncionario());

		return "acceso";
	}
	
	@RequestMapping(value = "/inicio.do")
	public String inicioDenuncia() {
			return "inicio";
	}

	@RequestMapping(value = "/validarCredenciales", method = RequestMethod.POST)
	public String validarCredenciales(DlcUsuarioFuncionario usuarioFuncionario,
			BindingResult result, HttpServletResponse response,
			HttpServletRequest request) {
		if (this.isNotEmpty(usuarioFuncionario.getDlcUsuario().getNomUsuarioSistema())&& this.isNotEmpty(usuarioFuncionario.getDlcUsuario().getRefPassword())) {
			DlcUsuario usrFirmado = this.loginServiceBean.validarCredenciales(usuarioFuncionario.getDlcUsuario());
			logger.debug("usrFirmado :: " + usrFirmado);
			if (usrFirmado != null && usrFirmado.getRefPassword().trim().equals(usuarioFuncionario.getDlcUsuario().getRefPassword().trim())) {
				if (usrFirmado.getDlcUsuarioFuncionarios() != null )  {
					final UserSession usrSession =  this.transformarUsuario(usrFirmado);
					
					DlcRol rol= loginServiceBean.consultaRolUsuario(usrFirmado.getCveIdUsuario());
					if(rol != null){
						usrSession.setCveRol(rol.getCveRol());
						usrSession.setDescripcionRol(rol.getDescRol());
					}
					usrSession.setIdSubDelegacion(new Long(usrFirmado.getCveSubdelegacion()));
					request.getSession().setAttribute(ConstantesSession.USR_SESSION, usrSession);
					usuarioFuncionario.getDlcUsuario().setCveIdUsuario(usrFirmado.getCveIdUsuario());
					
					final DlcPerfilUsuario spuParam = new DlcPerfilUsuario();
					spuParam.setDlcUsuario(usrFirmado);
					usuarioFuncionario.getDlcUsuario().setPerfilesDisponibles(this.transformarPerfiles(perfilService.recuperarPerfiles(spuParam)));
					logger.debug("Camino a seleccionar perfil de usuario");
					return "login/perfil";// para seleccionar el perfil
				} else {
					
					return "acceso";
				}
			}
			
			logger.debug("Intento fallido!!!");
			result.rejectValue("dlcUsuario.nomUsuarioSistema", "",
					"El nombre de usuario o la contrase\u00F1a introducidos no son correctos.");
		} else {
			result.rejectValue("dlcUsuario.nomUsuarioSistema", "",
					"El nombre de usuario y la contrase\u00F1a son requeridos.");
		}
			
		 
	  return "acceso";
		
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
	private Map<Long, String> transformarPerfiles(List<DlcPerfilUsuario> recuperarPerfiles) {
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
	 * @param segUsuario
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/seleccionarPeril", method = RequestMethod.POST)
	public String seleccionarPerfil(DlcUsuario dlcUsuario,
			HttpServletResponse response, HttpServletRequest request) {
		UserSession usrSession = super.getUsuarioFirmado(request);
		//final DlcMenu menuParam = new DlcMenu();
		//menuParam.setIdUsuario(usrSession.getCveIdUsuario());
		//menuParam.setIdPerfil(dlcUsuario.getIdPerfil());
		//usrSession.setMenu(this.transformarMenu(menuServiceBean.consultar(menuParam)),request);
		usrSession.setIdTipoUsuario(new Integer(2));
		String folio=request.getParameter("folio");
		if(folio!=null){
			System.out.println("Valor "+folio);
			request.getSession().setAttribute("numFolio",folio);
			request.getSession().setAttribute("cveFolioDenuncia",folio);
			
		}else{
			request.getSession().setAttribute("numFolio","");
			request.getSession().setAttribute("cveFolioDenuncia","");
		}
		request.getSession().setAttribute(ConstantesSession.USR_SESSION, usrSession);
		logger.debug("camino a welcome");
		return "consultaCurpFunc";
	}

	/**
	 * 
	 * @param response
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/redirect")
	public String redirectLogin(HttpServletResponse response, Model model,
			HttpServletRequest request) {
		logger.warn("Se ha perdido la sesion en una peticion JSon");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return new WelcomeController().home();
	}

	private UserSession transformarUsuario(DlcUsuario inUsrFirmado) {
		final UserSession usr = new UserSession();
		usr.setCveIdUsuario(inUsrFirmado.getCveIdUsuario());
		usr.setNomUsuarioSistema(inUsrFirmado.getNomUsuarioSistema());
		usr.setNomNombre(inUsrFirmado.getNomNombre().trim());
		usr.setNomPaterno(inUsrFirmado.getNomPaterno().trim());
		usr.setNomMaterno(inUsrFirmado.getNomMaterno().trim());
		usr.setCveIdPersona(inUsrFirmado.getCveIdPersona());
		if (inUsrFirmado.getUsuarioFuncionario() != null) {
			DlcUsuarioFuncionario fun = inUsrFirmado.getUsuarioFuncionario();
			usr.setCveIdFuncionario(fun.getCveIdUsuarioFuncionario());
			if (fun.getDlcDelegacion() != null) {
				usr.setIdDelegacion(new Long(fun.getDlcDelegacion().getCvePk()
						.longValue()));
				usr.setCveCodigoDelegacion(fun.getDlcDelegacion()
						.getCveCodigo());
				usr.setNombreDelegacion(fun.getDlcDelegacion().getNomNombre());
			}

			if (fun.getDlcDelegacion() != null) {
				usr.setIdSubDelegacion(new Long(fun.getDlcDelegacion()
						.getCvePk().longValue()));
				usr.setCveCodigoSubDelegacion(fun.getDlcDelegacion()
						.getCveCodigo());
				usr.setNombreSubDelegacion(fun.getDlcDelegacion()
						.getNomNombre());
			}
			if(fun.getDesCargo()!=null){
				usr.setDesCargo(fun.getDesCargo());
			}
		}
		return usr;
	}

}
