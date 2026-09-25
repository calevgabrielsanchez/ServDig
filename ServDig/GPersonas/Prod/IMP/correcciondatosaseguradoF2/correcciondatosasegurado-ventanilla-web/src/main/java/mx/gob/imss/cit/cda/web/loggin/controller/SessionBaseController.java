package mx.gob.imss.cit.cda.web.loggin.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import mx.gob.imss.cit.cda.web.app.common.model.InformacionSesion;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;


/**
 * Clase que contiene los metodos genericos para el manejo de la session 
 * y logueo del usuario
 * 
 * @author erik.ramirez
 *
 */
public class SessionBaseController extends AbstractController{
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SessionController.class);
    
    @Value("${url.openam}")
    private String openAMUrl;

  
    
    /**
     * Validar Usuario en sesion
     * 
     * @param session Objeto HttpSession
     * @param usr Datos del usuario logueado
     * @return  Datos del usuario
     */
    public UserProfile validarSesionUsuario(HttpSession session, UserProfile usr) {
        // Tratamos de obtener el objeto usuario de la sesion
        UserProfile usuario = usr;
        
        this.setVariablesSesionUsuario(session, usuario);
        // retornamos el usuario encontrado
        return usuario;
    }

    /**
     * Se agrega el usuario logueado a session
     * 
     * @param session Objeto HttpSession
     * @param usuario Datos del usuario logueado
     */
    private void setVariablesSesionUsuario(HttpSession session,
            UserProfile usuario) {
        // Creamos el objeto para el encabezado
        InformacionSesion infoSesion = null;
        infoSesion = (InformacionSesion) session
                .getAttribute(SessionConstants.INFORMACION_SESION);
        if (infoSesion == null) {
            infoSesion = new InformacionSesion();
            infoSesion.setRefreshCtx(openAMUrl);
            // Establecemos los atributos en el objeto busqueda
            infoSesion.setFechaSistema(new Date());
            infoSesion.setUsuario(usuario.getUsuario());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 30);
            infoSesion.setFechaAvisoSession(cal.getTime());
            cal.add(Calendar.MINUTE, 10);
            infoSesion.setFechaFinSession(cal.getTime());
            infoSesion.setValidaAvisoSession(true);
        }
        LOGGER.debug(" la fecha sistemas es [" + infoSesion.getFechaSistema()
                + "] fecha setFechaAvisoSession "
                + infoSesion.getFechaAvisoSession()
                + "] la fecha fin session es ["
                + infoSesion.getFechaFinSession() + "]");

        // Establecemos los objetos en la sesion
        session.setAttribute(SessionConstants.INFORMACION_SESION, infoSesion);
        session.setAttribute(SessionConstants.USER_PROFILE, usuario);
    }

    
    /**
     * Metodo que limpia la session del usuario
     * 
     * @param session Datos de la session
     */
    public void limpiarSesion(HttpSession session) {
        session.removeAttribute(SessionConstants.INFORMACION_SESION);
        session.removeAttribute(SessionConstants.USER_PROFILE);
    }
    
}
