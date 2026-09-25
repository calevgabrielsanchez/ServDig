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
@RequestMapping(value = "/correoController")
public class EnviaCorreoController extends AbsractSeguridadController { 

	@Autowired
	private LoginService<DlcUsuario> loginServiceBean;

	@Autowired
	private MenuService<DlcMenu> menuServiceBean;

	@Autowired
	private PerfilService<DlcPerfilUsuario> perfilService;
	
	@Autowired 
	private ImageCaptchaService captchaService;

	public EnviaCorreoController() {
		

	}
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(EnviaCorreoController.class);

	
	@RequestMapping(value = "/enviaCorreo.do")
	public void enviaCorreo(){
		try
        {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "adolfo.meza@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("denunciaLinea@imss.gob.mx"));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress("adolfo.meza@gmail.com"));
            message.setSubject("Hola");
            message.setText(
                "Mensajito con Java Mail" + "de los buenos." + "poque si");

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("adolfo.meza@gmail.com", "la clave");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	

}
