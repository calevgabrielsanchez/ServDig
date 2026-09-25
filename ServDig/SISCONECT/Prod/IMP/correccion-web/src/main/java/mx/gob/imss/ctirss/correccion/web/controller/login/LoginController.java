/**
 * LoginController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.gob.imss.ctirss.correccion.web.controller.login;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.login.model.SegMenu;
import mx.gob.imss.ctirss.correccion.login.model.SegPerfilUsuario;
import mx.gob.imss.ctirss.correccion.login.model.SegRol;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuarioFuncionario;
import mx.gob.imss.ctirss.correccion.login.service.interfaces.LoginService;
import mx.gob.imss.ctirss.correccion.login.service.interfaces.PerfilService;
import mx.gob.imss.ctirss.correccion.menu.service.interfaces.MenuService;
import mx.gob.imss.ctirss.correccion.model.CrtMenuPatron;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.MenuElement;
import mx.gob.imss.ctirss.correccion.session.MenuVO;
import mx.gob.imss.ctirss.correccion.session.UserSession;

import org.apache.log4j.Logger;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Marco Antonio Nieto Plett
 * @author Vladimir Aguirre Piedragil
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 19/10/2011
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends AbsractSeguridadController {

	@Autowired
	private LoginService<SegUsuario> loginServiceBean;

	@Autowired
	private MenuService<SegMenu> menuServiceBean;
	
	
	
	@Autowired
	private ICatalogoService<CrtMenuPatron> menuPatronServiceBean;
	

	@Autowired
	private PerfilService<SegPerfilUsuario> perfilService;
	
	@Autowired 
	private ICatalogoService<SacSubdelegacion> catalogoDAOLocal;
	
	@Autowired 
	private ICatalogoService<SacDelegacion> delegacionDAO;
	
	
	@Autowired 
	private ICatalogoService<SegRol> rolDAO;
	
	@Autowired 
	private ICatalogoService<CrtSolicitudcorr>  catalogoDAO;
	
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoService;
	

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(LoginController.class);

//	@RequestMapping(method = RequestMethod.POST)
//	public String getCreateForm(Model model) {
//		logger.debug("Creando forma...");
//		model.addAttribute(new SegUsuario());
//
//		return "acceso";
//	}

	@RequestMapping(value = "/validarCredenciales", method = RequestMethod.GET)
	public String validarCredenciales(SegUsuario segUsuario,
			BindingResult result, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		
		SegUsuario usrFirmado ;
		LoginByRequestOpenAM requestOpenAM = new LoginByRequestOpenAM(request);
		usrFirmado = requestOpenAM.getUsrFirmado();
		
		logger.debug("login.getNomUsuarioSistema() :: "	+ segUsuario.getNomUsuarioSistema());
		
		if (usrFirmado != null) {
			
			logger.debug("usrFirmado :: " + usrFirmado);
		//usrFirmado=loginServiceBean.consultaVigenciaUsuario(usrFirmado);
			
			if (usrFirmado != null) {
				logger.info("usrFirmado.getUsuarioFuncionario().getIndVigencia() "+usrFirmado.getUsuarioFuncionario().getIndVigencia());
//				if (usrFirmado.getUsuarioFuncionario().getIndVigencia() != null
//						&& usrFirmado.getUsuarioFuncionario().getIndVigencia()) {
				
				
				
					final UserSession usrSession = this.transformarUsuario(usrFirmado);
					
					SegRol rol=null;
					Map<Long, String> pds = new LinkedHashMap<Long, String>();					
					
					Set set = usrFirmado.getPerfilesDisponibles().entrySet();
					Iterator iter = set.iterator();
					String rolUser=null;
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						rolUser=entry.getValue().toString();
//						rolUser="Usuario Internet (Patrón)";//borrarJVH2 
						List<SegRol>listaROL=rolDAO.consultaLibrePorClave(0L, "from SegRol rol where rol.descRol='"+rolUser+"'");
						if(listaROL.isEmpty()){
							continue;
						}
						rol= listaROL.get(0);
						pds.put(rol.getCveRol(),rol.getDescRol());
					}
					
					System.out.println("RolUser "+rolUser);

//					SegRol rol= loginServiceBean.consultaRolUsuario(usrFirmado.getCveIdUsuario());
					

					if(rol != null){
						usrSession.setCveRol(rol.getCveRol());
						usrSession.setDescripcionRol(rol.getDescRol());
//						usrSession.setRegistroPatronal("B7010662106");
//						usrSession.setCveIdPatron(70L);
//						usrSession.setRepresentateLegal("JorgeRepreLegal");
					}

					request.getSession().setAttribute(ConstantesSession.USR_SESSION, usrSession);
					segUsuario.setCveIdUsuario(usrFirmado.getCveIdUsuario());
					
//					final SegPerfilUsuario spuParam = new SegPerfilUsuario();
//					spuParam.setSegUsuario(usrFirmado);
//					
//					segUsuario.setPerfilesDisponibles(this.transformarPerfiles(perfilService.recuperarPerfiles(spuParam)));
					
					
					segUsuario.setPerfilesDisponibles(pds);
					logger.debug("Camino a seleccionar perfil de usuario");
					return "login/perfil";// para seleccionar el perfil
//				} else {
//					result.rejectValue("nomUsuarioSistema", "",
//							"El usuario ha sido dado de baja.");
//					return "acceso";
//				}
			}
			logger.debug("Intento fallido!!!");
			result.rejectValue("nomUsuarioSistema", "",
					"El nombre de usuario o la contrase\u00F1a introducidos no son correctos.");
		} else {
			result.rejectValue("nomUsuarioSistema", "",
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
	private Map<Long, String> transformarPerfiles(
			List<SegPerfilUsuario> recuperarPerfiles) {
		Map<Long, String> pds = new LinkedHashMap<Long, String>();
		if (recuperarPerfiles != null && !recuperarPerfiles.isEmpty()) {
			for (SegPerfilUsuario perfil : recuperarPerfiles) {
				pds.put(perfil.getCveIdPerfilUsuario(),
						perfil.getSegRol().getDescRol());
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
	public String seleccionarPerfil(SegUsuario segUsuario,
			HttpServletResponse response, HttpServletRequest request) {
		UserSession usrSession = super.getUsuarioFirmado(request);
		final SegMenu menuParam = new SegMenu();
		//menuParam.setIdUsuario(usrSession.getCveIdUsuario());
		menuParam.setIdPerfil(segUsuario.getIdPerfil());
		menuParam.setCveRol(segUsuario.getIdPerfil());
		logger.debug("**************************************************************************************************************************");
		logger.debug("Valor idUsuario "+usrSession.getCurpUsuario());
		logger.debug("Valor id Perfil "+segUsuario.getIdPerfil());
		logger.debug("Rol "+usrSession.getCveRol());
		logger.debug("CveIdPatron "+usrSession.getCveIdPatron());
		
		logger.debug("RegPatronal "+usrSession.getRegistroPatronal());
		
		logger.debug("usrSession.getCveRol(): "+ usrSession.getCveRol());
		if((usrSession.getCveRol()==ConstantesBusiness.ROL_USER_INTERNET)){
			//Es usuario patron			
			logger.debug("Es usuario patron");	
			List<CrtMenuPatron> patronMenu=menuPatronServiceBean.consultaLibrePorClave(0L,"FROM CrtMenuPatron order by NUM_ORDEN");
			HttpSession session = request.getSession();		
			session.setAttribute("menuPatron",patronMenu );
			return "homePatron";
		}else{
			//Es usuario normal
			logger.debug("Es usuario normal");
			
			List<SegRol>listaROL=rolDAO.consultaLibrePorClave(0L, "from SegRol rol where rol.cveRol='"+segUsuario.getIdPerfil()+"'");
			SegRol rol= listaROL.get(0);
			if(rol != null){
				usrSession.setCveRol(rol.getCveRol());
				usrSession.setDescripcionRol(rol.getDescRol());
			}
			
			
			
			List<SegMenu> listMenu=menuServiceBean.consultar(menuParam);
			usrSession.setMenu(this.transformarMenu(listMenu),request);
			HttpSession session = request.getSession();		
			session.setAttribute("menuCorreccion", this.transformarMenu(listMenu));
			return new WelcomeController().home(request);
		}

				
		//logger.debug("camino a welcome");
		//return new WelcomeController().home();
		//logger.debug("");
	}
	
	
	@RequestMapping(value = "/redireccionaInicio", method = RequestMethod.POST)
	public String redireccionaInicio(SegUsuario segUsuario,
			HttpServletResponse response, HttpServletRequest request) {
			
		return "homePatron";
	}
	
	
	@RequestMapping(value = "/getIdSession", method = RequestMethod.POST)
	public @ResponseBody HashMap<String,String> recuperaMenu(HttpServletResponse response, HttpServletRequest request){
		System.out.println("Recuperando IDSession");
		HttpSession session = request.getSession();
		HashMap<String,String> mapa=new HashMap<String, String>();
		String element;
		Enumeration<String> elementos = request.getAttributeNames();
		mapa.put("idSession", session.getId());
		while(elementos.hasMoreElements()){			
			element = elementos.nextElement().toString();
			mapa.put(element, request.getAttribute(element).toString());
		}
		
		
		
		return mapa;
	}
	
	
	@RequestMapping(value = "/recuperaMenu", method = RequestMethod.POST)
	public @ResponseBody List<MenuElement> recuperaMenu(@RequestBody CrtSolicitudcorr solicitud,
			HttpServletResponse response, HttpServletRequest request) {
		System.out.println("La solicitud a recuperar es  "+solicitud.getCveSolicitudCorr());
		//List<MenuVO> mapa=new ArrayList<MenuVO>();
		UserSession usrSession = super.getUsuarioFirmado(request);
		MenuVO vo=null;
		HttpSession session = request.getSession();
		
		System.out.println("El rol usuario es "+usrSession.getCveRol());
		
		List<CrtMenuPatron> menuPatron= (List<CrtMenuPatron>) session.getAttribute("menuPatron");	
		System.out.println("menuPatron "+menuPatron.size());
//		for(MenuVO men:menuSesion){
//			vo=new MenuVO();
//			vo.setCveFkMenuItem(men.getCveFkMenuItem());
//			vo.setCveFkRol(men.getCveFkRol());
//			vo.setCvePK(men.getCvePK());
//			vo.setDesEtiqueta(men.getDesEtiqueta());
//			vo.setDesURL(men.getDesURL());
//			vo.setNumOrden(men.getNumOrden());		
//			mapa.add(vo);
//		}
		
	
		
		List<MenuElement> lista=new ArrayList<MenuElement>();
		MenuElement item=null;
		for(CrtMenuPatron menu:menuPatron){
			item=new MenuElement();
			
			item.setCvePkMenu(menu.getCveIdMenu().longValue());
			item.setCveFkMenuItem(menu.getCveFkMenu()!=null ?menu.getCveFkMenu().toString():"");		
			if(menu.getDesVinculo()!=null)
				item.setHref(menu.getDesVinculo());
			item.setNombreProceso(menu.getDesDescripcion());
			item.setDetalleModulo(recuperaValorDetalle(menu.getFecSolicitudCorr(),solicitud.getCveSolicitudCorr().longValue(),menu.getFecSolicitudProrr()));
			item.setLinkBloqueado(defineBloqueLiga(menu.getFecLimitePresentaCorr(),solicitud.getCveSolicitudCorr().longValue()));
			lista.add(item);			
		}
		
		
		
//		for(MenuElement ele:lista){			
//			for(MenuElement eleHijo:lista){					
//				if(eleHijo.getCveFkMenuItem()!=null && ele.getCvePkMenu().intValue()==Integer.valueOf(eleHijo.getCveFkMenuItem())){
//					ele.getChildren().add(eleHijo);
//				}
//			}
//		}
		
		//List<MenuElement> listaFinal=new ArrayList<MenuElement>();
	

		session.setAttribute("cveSolcorr",solicitud.getCveSolicitudCorr());
		session.setAttribute("numeroFolio",solicitud.getNuFolio());
		
		return lista;
		
	}
	
	private boolean defineBloqueLiga(String query,Long cveSolcorr){
		
		if(query==null || query.equals("")){
			return false;
		}
		
		String SQL=query;
		SQL = SQL.replace("{1}", cveSolcorr.toString());
		List<?> lista=catalogoService.consultaSQL(SQL);
		if(lista!=null && !lista.isEmpty()){
			return true;
		}
		return false;
	}
	
	
	private String recuperaValorDetalle(String query,Long cveSolcorr,String etiqueta){
		String valor="";
		if(query==null){
			return "";
		}
		if(etiqueta==null){
			etiqueta="";
		}
		
		System.out.println("El query es  "+query);
		//String SQL = "SELECT NU_NUMTRAB,CVE_SOLICITUDCORR FROM CRT_SOLICITUDCORR where CVE_SOLICITUDCORR={1}";
		String SQL=query;
		SQL = SQL.replace("{1}", cveSolcorr.toString());
		System.out.println("query "+SQL);
		List<?> lista=catalogoService.consultaSQL(SQL);
		if(lista!=null && !lista.isEmpty()){
			Iterator<?> iter = lista.iterator();
			Object[] currentObj = null;		
		
			while(iter.hasNext()){				
				currentObj = (Object[])iter.next();
				valor=String.valueOf(currentObj[0]);
			}
		}
		
		return etiqueta+" "+valor;
	}
	
	@RequestMapping(value = "/recuperaListaCorreciones", method = RequestMethod.POST)
	public @ResponseBody List<CrtSolicitudcorr> recuperaCorrecciones(HttpServletResponse response, HttpServletRequest request) {

		UserSession usrSession = super.getUsuarioFirmado(request);
		StringBuilder query=new StringBuilder();
//		query.append("SELECT sol FROM CrtSolicitudcorr sol,SatPatron patron where sol.cvePatron=patron.cvePK  and patron.registroPatronal="+"'Y5459616107'");//borrarJVH2
		query.append("SELECT sol FROM CrtSolicitudcorr sol,SatPatron patron where sol.cvePatron=patron.cvePK  and patron.registroPatronal='"+usrSession.getRegistroPatronal()+"' ");
		System.out.println("query "+query.toString());
		List<CrtSolicitudcorr> lista=catalogoDAO.consultaLibrePorClave(0L, query.toString());
		return lista;
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
		return new WelcomeController().home(request);
	}

	/**
	 * 
	 * @param inUsrFirmado
	 * @return
	 */
	private UserSession transformarUsuario(SegUsuario inUsrFirmado) {
		final UserSession usr = new UserSession();
		
		usr.setCveIdUsuario(inUsrFirmado.getCveIdUsuario());
		usr.setNomUsuarioSistema(inUsrFirmado.getNomUsuarioSistema());
		usr.setNomNombre(inUsrFirmado.getNomNombre()+" "+inUsrFirmado.getNomPaterno()+" "+inUsrFirmado.getNomMaterno());
		usr.setNomPaterno(inUsrFirmado.getNomPaterno());
		usr.setNomMaterno(inUsrFirmado.getNomMaterno());
		usr.setCveIdPersona(inUsrFirmado.getCveIdPersona());
		usr.setCurpUsuario(inUsrFirmado.getCurpUsuario());
		List<SacSubdelegacion> subdelegacionLista=new ArrayList<SacSubdelegacion>();
		
		List<SacDelegacion> delegacionLista=delegacionDAO.consultaLibrePorClave(0l, "from SacDelegacion dele where dele.cvePk="+inUsrFirmado.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoDelegacion());
		String codigoSubde=inUsrFirmado.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoSubDelegacion();
		if(codigoSubde!=null && !codigoSubde.equals(""))
		subdelegacionLista=catalogoDAOLocal.consultaLibrePorClave(0l, "from SacSubdelegacion sub where sub.cvePk="+inUsrFirmado.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoSubDelegacion()+" and sub.sacDelegacion.cvePk="+inUsrFirmado.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoDelegacion());
		
		
		if(!delegacionLista.isEmpty()){
			SacDelegacion del=delegacionLista.get(0);
			System.out.println("Delegacio  "+del.getCveCodigo()+" "+del.getCvePk());
			usr.setIdDelegacion(del.getCvePk());
			usr.setCveCodigoDelegacion(del.getCveCodigo());
			usr.setNombreDelegacion(del.getNomNombre());
		}
	
		if(!subdelegacionLista.isEmpty()){
			SacSubdelegacion sub=subdelegacionLista.get(0);
			System.out.println("Subedelega "+sub.getCveCodigo()+" "+sub.getCvePk());
			usr.setIdSubDelegacion(sub.getCvePk());
			usr.setCveCodigoSubDelegacion(sub.getCveCodigo());
			usr.setNombreSubDelegacion(sub.getNomNombre());			
		}
		
		usr.setCveIdFuncionario(0L);
		
		
		
//		if (inUsrFirmado.getUsuarioFuncionario() != null) {
//			SegUsuarioFuncionario fun = inUsrFirmado.getUsuarioFuncionario();
//			usr.setCveIdFuncionario(fun.getCveIdUsuarioFuncionario());
//			if (fun.getSacDelegacion() != null) {
//				usr.setIdDelegacion(new Long(fun.getSacDelegacion().getCvePk()
//						.longValue()));
//				usr.setCveCodigoDelegacion(fun.getSacDelegacion()
//						.getCveCodigo());
//				usr.setNombreDelegacion(fun.getSacDelegacion().getNomNombre());
//			}

//			if (fun.getSacSubdelegacion() != null) {
//				usr.setIdSubDelegacion(new Long(fun.getSacSubdelegacion()
//						.getCvePk().longValue()));
//				usr.setCveCodigoSubDelegacion(fun.getSacSubdelegacion()
//						.getCveCodigo());
//				usr.setNombreSubDelegacion(fun.getSacSubdelegacion()
//						.getNomNombre());
//			}
		//}
		return usr;
	}

	
	
	@RequestMapping(value = "/validarFolioCorreccionDelegacion", method = RequestMethod.POST)
	public @ResponseBody boolean validarFolioCorreccionDelegacion(@RequestBody CrtSolicitudcorr solicitud,HttpServletResponse response, HttpServletRequest request) {

		UserSession usrSession = super.getUsuarioFirmado(request);
		System.out.println("El rol es "+usrSession.getCveRol());
		StringBuilder query=new StringBuilder();
		int idFormaPresenta;		
		
		if((usrSession.getCveRol()==ConstantesBusiness.ROL_USER_INTERNET)){
			idFormaPresenta=1;
		}else{
			idFormaPresenta=0;
		}
		
		query.append("SELECT sol FROM CrtSolicitudcorr sol where sol.idFormaPresenta="+idFormaPresenta+" and sol.nuFolio='"+solicitud.getNuFolio()+"'");
		List<CrtSolicitudcorr> lista=catalogoDAO.consultaLibrePorClave(0L, query.toString());
		if(lista.isEmpty()){
			//El folio fue dado de alta desde la subdelegacion o desde internet segun sea el ROL
			return false;
		}else{
			return true;	
		}
		
	}
	
}
