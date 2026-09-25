/**
 * LoginController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.imss.ctirss.web.controller.registro;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.imss.ctirss.base.paginador.model.DatosEntradaPaginador;
import mx.imss.ctirss.base.paginador.model.DatosSalidaPaginador;
import mx.imss.ctirss.catalogos.base.model.AbstractDlcMenu;
import mx.imss.ctirss.catalogos.base.model.AbstractDlcPerfilUsuario;
import mx.imss.ctirss.catalogos.base.model.AbstractDlcUsuario;
import mx.imss.ctirss.catalogos.model.DlcMenu;
import mx.imss.ctirss.catalogos.model.DlcPerfilUsuario;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.catalogos.model.DlcUsuarioFuncionario;
import mx.imss.ctirss.denuncia.vo.DenunciaVO;
import mx.imss.ctirss.denuncia.vo.DenunciaVODT;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.login.model.SegMenu;
import mx.imss.ctirss.login.model.SegPerfilUsuario;
import mx.imss.ctirss.login.model.SegUsuario;
import mx.imss.ctirss.login.model.SegUsuarioFuncionario;
import mx.imss.ctirss.login.service.interfaces.IDenunciaService;
import mx.imss.ctirss.login.service.interfaces.LoginService;
import mx.imss.ctirss.login.service.interfaces.PerfilService;
import mx.imss.ctirss.menu.service.interfaces.MenuService;
import mx.imss.ctirss.model.DltDatospatron;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltPersona;
import mx.imss.ctirss.model.DltUsuarioden;
import mx.imss.ctirss.promocion.base.paginador.DenunciasWrapperDataTable;
import mx.imss.ctirss.service.interfaces.ICatalogoService;
import mx.imss.ctirss.session.ConstantesSession;
import mx.imss.ctirss.session.UserSession;
import mx.imss.ctirss.utils.Functions;

import mx.imss.ctirss.web.bean.DenunciaDTO;
import mx.imss.ctirss.web.controller.DenunciaController;
import mx.imss.ctirss.web.controller.login.AbsractSeguridadController;
import mx.imss.ctirss.web.controller.login.WelcomeController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * @author Adolfo Meza Morales
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 19/10/2011
 */
@Controller
@RequestMapping(value = "/registro")
public class RegistroController extends AbsractSeguridadController { 

	@Autowired
	private LoginService<DltUsuarioden> loginServiceBean;

	@Autowired
	private MenuService<DlcMenu> menuServiceBean;

	@Autowired
	private PerfilService<DlcPerfilUsuario> perfilService;
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	@Autowired 
	private ImageCaptchaService captchaService;
	
	
	@Autowired
	private IDenunciaService<AbstractModel> denunciaServiceBean;
	
	
	public static String NUMEROS = "0123456789";
	 
	public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
 
	public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
 
	public static String ESPECIALES = "ñÑ";
	public RegistroController() {
		

	}
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(RegistroController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getCreateForm(Model model) {
		logger.debug("Creando forma...");
		model.addAttribute(new DltUsuarioden());

		return "inicioRegistro";
	}
	
	@RequestMapping(value = "/inicio.do")
	public String inicioDenuncia() {
			return "inicio";
	}
	
	@RequestMapping(value = "/irRegistro.do")
	public String irRegistro() {
			return "registro";
	}
	
	@RequestMapping(value = "/irLogin.do")
	public String irLogin() {
			return "login";
	}
	@RequestMapping(value = "/irRecuperar.do")
	public String irRecuperar() {
			return "recuperar";
	}
	@RequestMapping(value = "/irRecuperarConf.do")
	public String irRecuperarConf() {
			return "recuperarConf";
	}
	@RequestMapping(value = "/opciones.do")
	public String opcionesDenuncia() {
			return "opciones";
	}
	
	

	@RequestMapping(value = "/validarCredenciales", method = RequestMethod.POST)
	public String validarCredenciales(DlcUsuario dlcUsuario,
			BindingResult result, HttpServletResponse response,
			HttpServletRequest request) {
		if (this.isNotEmpty(dlcUsuario.getNomUsuarioSistema())&& this.isNotEmpty(dlcUsuario.getRefPassword())) {
			DlcUsuario usrFirmado = null;
			logger.debug("usrFirmado :: " + usrFirmado);
			if (usrFirmado != null) {
				if (usrFirmado.getDlcUsuarioFuncionarios() != null) {
					final UserSession usrSession = new UserSession();
					request.getSession().setAttribute(ConstantesSession.USR_SESSION, usrSession);
					dlcUsuario.setCveIdUsuario(usrFirmado.getCveIdUsuario());
					
					final DlcPerfilUsuario spuParam = new DlcPerfilUsuario();
				//	spuParam.setSegUsuario(usrFirmado);
				//	dlcUsuario.setPerfilesDisponibles(this.transformarPerfiles(perfilService.recuperarPerfiles(spuParam)));
					logger.debug("Camino a seleccionar perfil de usuario");
					return "login/perfil";// para seleccionar el perfil
				} else {
					result.rejectValue("nomUsuarioSistema", "",
							"El usuario ha sido dado de baja.");
					return "acceso";
				}
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
	public String seleccionarPerfil(DlcUsuario dlcUsuario,
			HttpServletResponse response, HttpServletRequest request) {
		//UserSession usrSession = super.getUsuarioFirmado(request);
		//final DlcMenu menuParam = new DlcMenu();
		//menuParam.setIdUsuario(usrSession.getCveIdUsuario());
		//menuParam.setIdPerfil(dlcUsuario.getIdPerfil());
	//	usrSession.setMenu(this.transformarMenu(menuServiceBean.consultar(menuParam)),request);
	//	logger.debug("camino a welcome");
		return "acceso";
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

	
	@RequestMapping(value="/obtenerPreguntas", method=RequestMethod.POST)
	public @ResponseBody ArrayList obtenerPreguntas(@RequestBody String usuario, HttpServletResponse response,HttpServletRequest request){
		//SE INICIA LA VALIDACION DEL CAPTCHA
		ArrayList listaPreguntas = new ArrayList();
		listaPreguntas = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DlcPregunta "  );
		return listaPreguntas;
	}
	
	@RequestMapping(value="/obtenerPreguntasPorID", method=RequestMethod.POST)
	public @ResponseBody ArrayList obtenerPreguntasPorID(@RequestBody long cvePregunta, HttpServletResponse response,HttpServletRequest request){
		//SE INICIA LA VALIDACION DEL CAPTCHA
		ArrayList listaPreguntas = new ArrayList();
		listaPreguntas = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DlcPregunta p where p.cvePregunta = " + cvePregunta);
		return listaPreguntas;
	}
	
	
	
	@RequestMapping(value="/registraUsuario", method=RequestMethod.POST)
	public @ResponseBody int registraUsuario(@RequestBody DltUsuarioden usuario, HttpServletResponse response,HttpServletRequest request){
		//SE INICIA LA VALIDACION DEL CAPTCHA
		System.out.println(usuario.getCaptcha());
		boolean verficaCaptcha= validaCaptcha(request, usuario.getCaptcha());
		if(!verficaCaptcha){
			return 1;
		}
		//Verificamos existencia de correo electronico
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltUsuarioden rn where rn.desEmail = '"+usuario.getDesEmail()+"'");
		
		if(lista.size()>0){
			return 2;
		}
		
		this.catalogoServiceBean.agregar(usuario);
		System.out.print("enviando a correo:" + usuario.getDesEmail());
		enviaCorreo(usuario.getDesEmail(), usuario.getDesEmail(), usuario.getDesPassword());
		
	
		return 3;
	}
	
	
	@RequestMapping(value="/validaCaptcha", method=RequestMethod.POST)
	public @ResponseBody int verificaCaptcha(@RequestBody DltUsuarioden usuario, HttpServletResponse response,HttpServletRequest request){
		//SE INICIA LA VALIDACION DEL CAPTCHA
		System.out.println(usuario.getCaptcha());
		boolean verficaCaptcha= validaCaptcha(request, usuario.getCaptcha());
		if(!verficaCaptcha){
			return 1;
		}else
			return 2;
		
	}
	
	@RequestMapping(value="/verificaCorreo", method=RequestMethod.POST)
	public @ResponseBody int verificaCorreo(@RequestBody DltUsuarioden usuario, HttpServletResponse response,HttpServletRequest request){
		//SE INICIA LA VALIDACION DEL CAPTCHA
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltUsuarioden rn where rn.desEmail = '"+usuario.getDesEmail()+"'");
		if(lista.size()>0){
			DltUsuarioden usuarioRegistrado = (DltUsuarioden)lista.get(0);
			if(usuarioRegistrado!=null){
				if(usuarioRegistrado.getDesPassword().equals(usuario.getDesPassword())){
						return 1;
					}else{
						return 2;
					}
				}
		}
		return 3;
		
		
		
		
	}
	
	@RequestMapping(value="/verificaCorreoRec", method=RequestMethod.POST)
	public @ResponseBody int verificaCorreoRec(@RequestBody DltUsuarioden usuario, HttpServletResponse response,HttpServletRequest request){
		//SE INICIA LA VALIDACION DEL CAPTCHA
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltUsuarioden rn where rn.desEmail = '"+usuario.getDesEmail()+"'");
		if(lista.size()>0){
			DltUsuarioden usuarioRegistrado = (DltUsuarioden)lista.get(0);
			if(usuarioRegistrado!=null){
				request.getSession().setAttribute("recUsu", usuarioRegistrado.getDesEmail());
				return 1;
					
				}
		}
		return 2;
	}
	
	@RequestMapping(value="/validaUsuarioInternet", method=RequestMethod.POST)
	public ModelAndView validaUsuarioInternet(DltUsuarioden usuario,BindingResult result, HttpServletResponse response,HttpServletRequest request){
		
		
		//Verificamos existencia de usuario
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltUsuarioden rn where rn.desEmail = '"+usuario.getDesEmail()+"'");
		if(lista.size()>0){
				DltUsuarioden usuarioRegistrado = (DltUsuarioden)lista.get(0);
				UserSession usrSession = new UserSession();
				if(usuarioRegistrado!=null){
					if(usuarioRegistrado.getDesPassword().equals(usuario.getDesPassword())){
						DenunciaDTO denunciaDTO = new DenunciaDTO();
						ArrayList denuncias = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltDenuncia rn where rn.dltUsuarioden.desEmail = '"+usuario.getDesEmail()+"'");
						//List<DltDenuncia> denuncias = denunciaServiceBean.getDenuncias(usuarioRegistrado);				
										
						denunciaDTO.setDenuncias(denuncias);
						
						ModelAndView mwNext= new ModelAndView("consultaCurp");
						mwNext.addObject("denunciaDTO", denunciaDTO);
						mwNext.addObject("dltUsuarioden", usuarioRegistrado);
						mwNext.addObject("denuncias", denuncias);
						
						DenunciaController dController = new DenunciaController();
						request.getSession().setAttribute("listaDenuncias", denuncias);
						usrSession.setIdTipoUsuario(new Integer(1));
						usrSession.setNomUsuarioSistema(usuarioRegistrado.getDesEmail());
						//usrSession.setCveIdPersona(usuarioRegistrado.getCveUsuarioden());
						usrSession.setDescripcionRol("Internet");
						usrSession.setCveIdUsuario(usuarioRegistrado.getCveUsuarioden());
						request.getSession().setAttribute(ConstantesSession.USR_SESSION, usrSession);
						request.getSession().setAttribute("denunciaDTO", denunciaDTO);
						return dController.consultaDenuncias(usuarioRegistrado, result, response, request, request.getSession());
						
					}
				}
		}
		result.rejectValue("desEmail", "",	"El usuario o contraseña son incorrectos");
		ModelAndView forward = new ModelAndView("login");
		BindingResult errors = new BeanPropertyBindingResult(usuario, "dltUsuarioden");
         errors.rejectValue("desEmail", "",	"El usuario o contraseña son incorrectos");
         //TODO: This is the part I don't like
         forward.getModel().put(BindingResult.MODEL_KEY_PREFIX + "dltUsuarioden", errors);
		
		 return forward;
	}
	
	@RequestMapping(value="/recuperarContrasena", method=RequestMethod.POST)
	public int recuperaContrasena(@RequestBody DltUsuarioden usuario,BindingResult result, HttpServletResponse response,HttpServletRequest request){
				//SE INICIA LA VALIDACION DEL CAPTCHA
				System.out.println(usuario.getCaptcha());
				boolean verficaCaptcha= validaCaptcha(request, usuario.getCaptcha());
				if(!verficaCaptcha){
					return 1;
				}
				//Se valida el correo electronico
				
				//Verificamos existencia de usuario
				ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltUsuarioden rn where rn.desEmail = '"+usuario.getDesEmail()+"'");
				if(lista.size()>0){
					DltUsuarioden usuarioRegistrado = (DltUsuarioden)lista.get(0);
					if(usuarioRegistrado!=null){
							//enviamos Correo
							System.out.println("Se envia correo");
							return 2;
						}
					}
			
				
				//enviaCorreo();
				
				return 3;
			}
			
	
	public boolean validaCaptcha(HttpServletRequest request, String captchaElement){
		boolean isResponseCorrect = false;
		String captchaId = request.getSession().getId();
		String response = captchaElement;

		try { 
		if(response != null){
			isResponseCorrect = captchaService.validateResponseForID(captchaId, response);
		}
		} catch (CaptchaServiceException e) {

		}
		return isResponseCorrect;
	}
	
	public static String getPassword(String key, int length) {
		String pswd = "";
 
		for (int i = 0; i < length; i++) {
			pswd+=(key.charAt((int)(Math.random() * key.length())));
		}
 
		return pswd;
	}
	
	public static String getPinNumber() {
		return getPassword(NUMEROS, 4);
	}
 
	public static String getPassword() {
		return getPassword(8);
	}
 
	public static String getPassword(int length) {
		return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
	}

	public void enviaCorreo(String toCorreo, String usuario, String contrasena){
		try
        {
  
//			Password: Denunci@2012*
		//Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "11.254.171.213");
            //props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "25");
            props.setProperty("mail.smtp.user", "denuncia.enlinea@imss.gob.mx");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("denuncia.enlinea@imss.gob.mx"));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress(toCorreo));
            message.setSubject("Confirmación de Registro en el sistema Denuncia en Linea IMSS","UTF-8");
            message.setContent(
            	" Contrase&ntilde;a de acceso al sistema <br><br>" +
            	" Estimado Usuario <b>" + usuario + "<b> <br>" +
            	" Por medio de la presente le confirmamos su CONTRASE&Ntilde;A DE ACCESO para el sistema de Atenci&oacute;n a"+
            	" Denuncias de Trabajadores por irregularidades en su Inscripci&oacute;n al Seguro Social por Internet."+
            	" Su contrase&ntilde;a de acceso es: <b>" + contrasena + "</b> <br><br>" +
            	" Recuerde que tiene la responsabilidad del buen uso de esta informaci&oacute;n."+
            	" Le recomendamos guardar este documento para futuras denuncias."+
            	"<br><br>" +
            	" Agradecemos su registro en el sistema  <br>"
            	, "text/html; charset=ISO-8859-1");
            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("denuncia.enlinea@imss.gob.mx", "Denunci@2012*");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        }
        catch (Exception e)
        {
        	logger.error("error al mandar correo");
        	logger.debug("error al mandar correo");
            e.printStackTrace();
        }
	}
	
	
	@RequestMapping(value="/paginaDenuncias", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<DltDenuncia> paginaDenuncias(@RequestBody DenunciasWrapperDataTable aoData ,HttpServletResponse response,HttpServletRequest request) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DltUsuarioden user = (DltUsuarioden) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());
		send.setModelo(new DltDenuncia());
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaDenuncias(send, user);
		for (int j=0; j< reply.getAaData().size(); j++){
			DltDenuncia denuncia =(DltDenuncia) reply.getAaData().get(j);
			//Verificamos existencia de usuario
			ArrayList list = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltPersona p where p.dltDenuncia.cveFoliodenuncia = "+denuncia.getCveFoliodenuncia());
			if(list.size()>0){
				for(int i = 0; i< list.size() ; i++){
					DltPersona persona = (DltPersona)list.get(i);
					if(persona.getCveTipodenunciante()!=null && denuncia.getCveTipodenunciante()!=null && persona.getCveTipodenunciante().intValue()==denuncia.getCveTipodenunciante().intValue()){
							denuncia.setPersona(persona);
							
					}
				}
			}else{
				DltPersona persona = new DltPersona();
				persona.setNombreCompleto("");
				denuncia.setPersona(persona);
			}
			if(denuncia.getPersona()==null){
				DltPersona persona = new DltPersona();
				persona.setNombreCompleto("");
				denuncia.setPersona(persona);
			}
			ArrayList patrones = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltDatospatron p where p.cveFoliodenuncia = "+denuncia.getCveFoliodenuncia());
			if(patrones.size()>0){
				for(int i = 0; i< patrones.size() ; i++){
					DltDatospatron patron = (DltDatospatron)patrones.get(i);
					if(patron.getIdPatronprincipal()==null){
							denuncia.setPatron(patron);
							
					}
				}
			}else{
				DltDatospatron patron = new DltDatospatron();
				patron.setDesNomrazonsocial("");
				denuncia.setPatron(patron);
			}
			if(denuncia.getPatron()==null){
				DltDatospatron patron = new DltDatospatron();
				patron.setDesNomrazonsocial("");
				denuncia.setPatron(patron);
			}
			reply.getAaData().set(j, denuncia);
			
		}
		//ArrayList listaDenuncias = (ArrayList) reply.getAaData();
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        return reply;
    }
	
	
	
	@RequestMapping(value="/consultarDenuncias", method=RequestMethod.POST )
    public @ResponseBody List<DenunciaVODT> consultarDenuncias(@RequestBody DenunciasWrapperDataTable aoData ,HttpServletResponse response,HttpServletRequest request) {
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		return denunciaServiceBean.recuperaDenunciasConsulta(user, true, null);
	}
	
	@RequestMapping(value="/consultarDenunciasFuncionario", method=RequestMethod.POST )
    public @ResponseBody List<DenunciaVODT> consultarDenunciasFuncionario(@RequestBody DenunciaVO den ,HttpServletResponse response,HttpServletRequest request) {
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		return denunciaServiceBean.recuperaDenunciasConsulta(user, false, den);
	}
	
	
	@RequestMapping(value="/reloadImagen", method=RequestMethod.POST )
    public @ResponseBody String reloadImagen(@RequestBody String var ,HttpServletResponse response,HttpServletRequest request) {
		
		return request.getContextPath() + "/captchaController/captcha.htm"+ "?" + new Date().getTime();
	}
	
	@RequestMapping(value="/consultaUsuario", method=RequestMethod.POST)
	public @ResponseBody DltUsuarioden consultaUsuario (@RequestBody DltUsuarioden user, HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses){
		DltUsuarioden usuario = new DltUsuarioden();
		  ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from DltUsuarioden d where d.desEmail = '"+ user.getDesEmail() +"'");
		if(lista!=null && lista.size()>0){
			usuario = (DltUsuarioden) lista.get(0);
		}
	return usuario;
	}
	
}
