/**
 * AbstractController.java
 * @package mx.gob.imss.delta.framework.base.controller
 * @project delta-framework-base	
 */

package mx.gob.imss.ctirss.correccion.framework.base.controller;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.UserSession;

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
	
	protected void setDomicilioInegiSession(Hashtable<String,Object> ht,HttpServletRequest request){
				
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
	 * @param request <code>Request</code> relativo ala petician del usuario
	 * @return Usuario firmado
	 */
	protected UserSession getUsuarioFirmado(HttpServletRequest request) {
		return (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
	}
	
	/**
	 * Metodo que se encarga de definir la url segun el layout correspondiente 
	 * 
	 * @param ulr Es la ulr por default en el sistema
	 * @param urlPatron Corresponde a la URL que se debe redireccionar en caso de que el usuario tenga el rol correspondiente, tener cuidado en mandar en ese orden.
	 * @return URL discriminada
	 */
	protected String determinaURL(HttpServletRequest request,String url,String urlPatron){
		UserSession user=(UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		
		if((user.getCveRol()==ConstantesBusiness.ROL_USER_INTERNET)){
			return urlPatron;
		}else{
			return url;
		}
		 
		
	}
	
}
