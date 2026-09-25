/**
 * AbstractController.java
 * @package mx.gob.imss.delta.framework.base.controller
 * @project delta-framework-base	
 */

package mx.imss.ctirss.framework.base.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.imss.ctirss.session.ConstantesSession;
import mx.imss.ctirss.session.MenuVO;
import mx.imss.ctirss.session.UserSession;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */

public class AbstractController {

	protected void setDomicilioInegiSession(String hashTableKey, Object obj,HttpServletRequest request){
		Hashtable<String,Object> ht = null;
		if(request.getSession().getAttribute(ConstantesSession.DOMICILIO_GEOGRAFICO)==null){
			ht = new Hashtable<String,Object>();
			    
		}else{
			ht = (Hashtable<String,Object>) request.getSession().getAttribute(ConstantesSession.DOMICILIO_GEOGRAFICO);
			
		}
		
		if(hashTableKey==null)hashTableKey="htkDefault";
		
		ht.put(hashTableKey, obj);
		request.getSession().setAttribute(ConstantesSession.DOMICILIO_GEOGRAFICO, ht);
		
	}
	
	protected void removeDomicilioInegiSession(HttpServletRequest request){
		
		request.getSession().removeAttribute(ConstantesSession.DOMICILIO_GEOGRAFICO);
		
	}
	
	protected Object getDomicilioInegiSession(HttpServletRequest request){
		
		return request.getSession().getAttribute(ConstantesSession.DOMICILIO_GEOGRAFICO);
		
	}
	
	/**
	 * Obtiene el usuario firmado, el cual se subio a sesion al pasar por el LoginController.
	 * @param request <code>Request</code> relativo ala petici�n del usuario
	 * @return Usuario firmado
	 */
	protected UserSession getUsuarioFirmado(HttpServletRequest request) {
		return (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
	}
	
	
}
