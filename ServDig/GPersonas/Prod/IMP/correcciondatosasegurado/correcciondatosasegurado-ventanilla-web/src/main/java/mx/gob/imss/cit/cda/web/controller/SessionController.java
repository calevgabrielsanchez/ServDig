package mx.gob.imss.cit.cda.web.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.web.app.common.model.InformacionSesion;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/session/*")
public class SessionController extends AbstractController{

	@Value("${url.openam}")
	private String openAMUrl;
	
	@RequestMapping(value = "/validar/session", method = RequestMethod.POST)
	public @ResponseBody void validarDatosConsulta(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		this.log.debug("entre a validar la session");
		try{
		
			InformacionSesion info_sesion = null;
			info_sesion = (InformacionSesion)session.getAttribute(SessionConstants.INFORMACION_SESION);
			if(info_sesion != null){
				info_sesion.setValidaAvisoSession(false);
				session.removeAttribute(SessionConstants.INFORMACION_SESION);
				session.setAttribute(SessionConstants.INFORMACION_SESION, info_sesion);
			}
		}catch(Exception e){
			this.log.error("ocurrio un error no cachado", e);
		}
		
		
	}

	public UserProfile validarSesionUsuario(HttpSession session, UserProfile usr){
		//Tratamos de obtener el objeto usuario de la sesion
		UserProfile usuario = usr;
		
		this.setVariablesSesionUsuario(session, usuario);
		//retornamos el usuario encontrado
		return usuario;
	}
	
	private void setVariablesSesionUsuario(HttpSession session, UserProfile usuario) {
		//Creamos el objeto para el encabezado
		InformacionSesion infoSesion = null;
		infoSesion = (InformacionSesion)session.getAttribute(SessionConstants.INFORMACION_SESION);
		if(infoSesion == null){
			infoSesion = new InformacionSesion();
			infoSesion.setRefreshCtx(openAMUrl);
			//Establecemos los atributos en el objeto busqueda
			infoSesion.setFechaSistema(new Date());
			infoSesion.setUsuario(usuario.getUsuario());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE,30);
			infoSesion.setFechaAvisoSession(cal.getTime());
			cal.add(Calendar.MINUTE,10);
			infoSesion.setFechaFinSession(cal.getTime());
			infoSesion.setValidaAvisoSession(true);
		}
			log.debug(" la fecha sistemas es [" +infoSesion.getFechaSistema()+ "] fecha setFechaAvisoSession " + 
				infoSesion.getFechaAvisoSession() +"] la fecha fin session es [" +infoSesion.getFechaFinSession() +"]");
			
		//Establecemos los objetos en la sesion
		session.setAttribute(SessionConstants.INFORMACION_SESION, infoSesion);
		session.setAttribute(SessionConstants.USER_PROFILE, usuario);
	}
	
	public void limpiarSesion(HttpSession session) {
		session.removeAttribute(SessionConstants.INFORMACION_SESION);
		session.removeAttribute(SessionConstants.USER_PROFILE);
	}
}
