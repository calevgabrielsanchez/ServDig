/**
 * LoginController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.imss.ctirss.web.controller.registro;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

import mx.imss.ctirss.catalogos.base.model.AbstractDlcMenu;
import mx.imss.ctirss.catalogos.base.model.AbstractDlcPerfilUsuario;
import mx.imss.ctirss.catalogos.base.model.AbstractDlcUsuario;
import mx.imss.ctirss.catalogos.model.DlcMenu;
import mx.imss.ctirss.catalogos.model.DlcPerfilUsuario;
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
import mx.imss.ctirss.model.DltUsuarioden;
import mx.imss.ctirss.session.ConstantesSession;
import mx.imss.ctirss.session.UserSession;
import mx.imss.ctirss.web.controller.login.AbsractSeguridadController;
import mx.imss.ctirss.web.controller.login.WelcomeController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * @author Adolfo Meza Morales
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 19/10/2011
 */
@Controller
@RequestMapping(value = "/passwordController")
public class GeneradorPasswordsController extends AbsractSeguridadController { 

	@Autowired
	private LoginService<DlcUsuario> loginServiceBean;

	@Autowired
	private MenuService<DlcMenu> menuServiceBean;

	@Autowired
	private PerfilService<DlcPerfilUsuario> perfilService;
	
	@Autowired 
	private ImageCaptchaService captchaService;

	public GeneradorPasswordsController() {
		

	}
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(GeneradorPasswordsController.class);

	 
		public static String NUMEROS = "0123456789";
	 
		public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	 
		public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
	 
		public static String ESPECIALES = "Ò—";
	
	
	@RequestMapping(value = "/getPassword.do")
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
 
}
