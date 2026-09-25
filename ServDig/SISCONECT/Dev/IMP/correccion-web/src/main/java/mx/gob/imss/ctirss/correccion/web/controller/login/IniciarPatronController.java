/**
 * ClaseCatalogoController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.gob.imss.ctirss.correccion.web.controller.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.login.model.SegMenu;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;
import mx.gob.imss.ctirss.correccion.menu.service.interfaces.MenuService;
import mx.gob.imss.ctirss.correccion.model.CrtMenuPatron;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.TipoCertificado;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Vladimir Aguirre Piedragil
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 19/10/2011
 */
@Controller
public class IniciarPatronController extends AbsractSeguridadController{

	@Autowired
	private MenuService<SegMenu> menuServiceBean;

	@Autowired
	private IPatronesService patronesService;
	
	
	@Autowired
	private ICatalogoService<CrtMenuPatron> menuPatronServiceBean;
	
	

	@Autowired
	private ICatalogoService<SacSubdelegacion> servicio;
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(IniciarPatronController.class);

	
	@RequestMapping(value = "iniciarPatron")
	public String iniciarPatron(HttpServletResponse response, HttpServletRequest request, @RequestParam("patIDSEnpie") String patIDSEenpie) {
		
		String username = null;
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		
		logger.debug("El usuario es " +  username);
		
		logger.debug("patIDSEnpie :: " + patIDSEenpie);
		System.out.println("Procesamos en abstractController");
		UsuarioSSO usuarioSSO = this.procesarUsuarioSSO(request);
		System.out.println("El usuario procesado en abstractController es " + usuarioSSO.getCurp());
		System.out.println("Recuperando datos OPEN AM internet");
		LoginByRequestOpenAM loginByRequestOpenAM = new LoginByRequestOpenAM(request);
		SegUsuario us=loginByRequestOpenAM.getUsrFirmado();
		System.out.println("El usuario es " + us.getCurpUsuario());
		if(username != null) {
			us.setCurpUsuario(username);
		}
		
		SatPatron patron = this.patronesService.validaRegistroPatronalWS(patIDSEenpie.substring(0, 10), true);

		if (patron != null) {	
			logger.debug("camino a welcome");
			UserSession usrSession = this.transformarUsuario(patron);
			usrSession.setRegistroPatronal(patron.getRegistroPatronal());
			usrSession.setCveRol(5);
			usrSession.setCurpUsuario(us.getCurpUsuario());
			System.out.println("Query "+"FROM SacSubdelegacion where cvePk="+usrSession.getIdSubDelegacion());
			List<SacSubdelegacion> subde=servicio.consultaLibrePorClave(0L, "FROM SacSubdelegacion where CVE_PK="+usrSession.getIdSubDelegacion());
			System.out.println("Total subdele "+subde.size());
			System.out.println("NombreSubdelegacion "+subde.get(0).getNomNombre());
			System.out.println("CURP Usuario Internet "+usrSession.getCurpUsuario());
			usrSession.setNombreSubDelegacion(subde.get(0).getNomNombre());
			
			usrSession.setMenu(this.transformarMenu(menuServiceBean.obtenerMenuPatron()),request);
			request.getSession().setAttribute(ConstantesSession.USR_SESSION,usrSession);
			
			List<CrtMenuPatron> patronMenu=menuPatronServiceBean.consultaLibrePorClave(0L,"FROM CrtMenuPatron");
			HttpSession session = request.getSession();		
			session.setAttribute("menuPatron",patronMenu );
			
			
			return "homePatron";
		}
		logger.debug("Intento fallido!!!");
		request.setAttribute("segUsuario", new SegUsuario());
		return "rpInvalidoPatron";
	}

	/**
	 * 
	 * @param inUsrFirmado
	 * @return
	 */
	private UserSession transformarUsuario(SatPatron inUsrFirmado) {
		final UserSession usr = new UserSession();
		usr.setCveIdPatron(inUsrFirmado.getCvePK());
		usr.setNomUsuarioSistema(inUsrFirmado.getRegistroPatronal());
		usr.setNomNombre(inUsrFirmado.getRazonSocial());
		

		if (inUsrFirmado.getUbicacion() != null
				&& inUsrFirmado.getUbicacion().getMunicipio() != null
				&& inUsrFirmado.getUbicacion().getMunicipio()
						.getSacSubdelegacion() != null) {
			usr.setIdSubDelegacion(inUsrFirmado.getUbicacion().getMunicipio()
					.getSacSubdelegacion().getCvePk());
			usr.setCveCodigoSubDelegacion(inUsrFirmado.getUbicacion()
					.getMunicipio().getSacSubdelegacion().getCveCodigo());
			if (inUsrFirmado.getUbicacion().getMunicipio()
					.getSacSubdelegacion().getSacDelegacion() != null) {
				usr.setIdDelegacion(inUsrFirmado.getUbicacion().getMunicipio()
						.getSacSubdelegacion().getSacDelegacion().getCvePk());
				usr.setCveCodigoDelegacion(inUsrFirmado.getUbicacion()
						.getMunicipio().getSacSubdelegacion()
						.getSacDelegacion().getCveCodigo());
			}
		}


		return usr;
	}

}
