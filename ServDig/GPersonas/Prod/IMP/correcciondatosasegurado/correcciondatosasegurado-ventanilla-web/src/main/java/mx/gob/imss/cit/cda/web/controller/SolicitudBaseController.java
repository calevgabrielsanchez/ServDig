package mx.gob.imss.cit.cda.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.constants.RolUsuarioEnum;
import mx.gob.imss.cit.cda.web.utils.RolLoginUtil;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;

/**
 * Clase que contiene los metodos comunes de solicitud
 * 
 * @author erik.ramirez
 *
 */
public class SolicitudBaseController extends AbstractController {
	
	@Autowired
	private RolLoginUtil rolLoginUtil;
	

	protected UserProfile getUsuarioEnSesion(HttpSession session) {
		log.info("Recuperando de la sesion la informacion del usuario");
		//return (UserProfile)super.getUsuarioEnSesion(session);
		UserProfile userProfile = (UserProfile)session.getAttribute(SessionConstants.USER_PROFILE);
		return userProfile;
//		return perfilMock();
	}

	

	   private UserProfile perfilMock(){
        UserProfile userProfile = new UserProfile();
        
            userProfile.setIdPersona(new Long(1));
            userProfile.setSubdelegacion("Col. Periodistas|Del. Miguel Hidalgo|Ciudad de México|C.P.: 11220");
            userProfile.setIdSubdelegacion(new Long(21));
            userProfile.setUsuario("MEVE810320MCMDZM09");
            userProfile.setIdDelegacion(new Long(06));
            userProfile.setSistemas(new String[]{"",""});
            userProfile.setNombreCompleto("Earia Guadalupe Alvarado Molina");
            RolUsuarioEnum rol = rolLoginUtil.getRolFuncionario();
            userProfile.setPerfil(rol.getRol());
            userProfile.setPerfilDescripcion(rol.getDescripcion());
        return userProfile; 
    }

}
