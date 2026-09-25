package mx.gob.imss.ctirss.delta.derechohabientes.web.filter;



import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * @author Lucio Duran Silva
 *
 */
@Component
public class FiltroDelta implements Filter {
	
	
	protected  final Log log = LogFactory.getLog( getClass() );
	
	
	private static final String URL_WELCOME = "/welcome";
	
	private static final String URL_LOGIN = "/login";
	
	private static final String URL_REDIRECT = "/logout";
	
	private static final String URL_COMBO = "/combo";
	
	private static final String URL_PORTAL = "/gestionDerechohabientes";
	
	
	/*Variable de nombre de objeto en sesion*/
	private static final String OBJ_IN_SESSION = "usuario";
	
	/*
	 * esta url es para los elementos estaticos (js, imagenes, css, etc)
	 */
	private static final String URL_STATIC = "static";
	
	private static final String CONTENT_TYPE_JSON = "json";
	

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		
		this.log.info("Destruyendo filtro de seguridad de derechohabientes");
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain ftrChain) throws IOException, ServletException {
		
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String url = req.getServletPath();
		String contentType = req.getContentType();
		
		Object objSession =  session.getAttribute(OBJ_IN_SESSION);
		
		
		this.log.debug("Objeto de sesion Login hola mexico =)[" + objSession +"]");
		this.log.debug(" URL solicitada [" + url + "]");
		
			ftrChain.doFilter(req, res);
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("Pasando por FiltroDelta.... ");

	}
	
	
	private boolean isPeticionPermitida(String url, HttpServletRequest request) {
	    boolean response = false;
	    this.log.debug("Validando si la peticion es permitida (no requiere de una sesion activa) [ " + url +" ]");
	    
		if(url.contains(URL_LOGIN)){
			response = true;
		}else if(url.contains(URL_REDIRECT)){
			response = true;
		} else if(url.contains(URL_STATIC)){
			response = true;
		} else if (url.contains(URL_WELCOME)){
			response = true;
		}else if (url.contains(URL_COMBO)){
			response = true;
		}else if (url.contains(URL_PORTAL)){
			response = true;
		}
		
		
		
		
		
		
		
		
		this.log.debug("isPeticionPermitida [ " + response  +"]");
	    return response;
	  }
	
	
	/**
	 * Metodo que verifica que la URL solicitada es de tipo JSON
	 * @param url
	 * @param contentType
	 * @return
	 */
	private boolean isPeticionJSON(String url , String contentType){
		this.log.debug("Validando si la peticion es de tipo json [ " + url  +" ] , [" + contentType +"]");
		boolean response = false;
		if(contentType != null){
			
			if(contentType.contains(CONTENT_TYPE_JSON)){
				response = true;
			}
		}else{
			this.log.debug("content type es nulo");
			
		}
		this.log.debug("isPeticionJSON[ " + response  +" ] ");
		return response;
	}
	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	private boolean isObjetoLoginEnSesion(HttpServletRequest req){
		boolean response = false;
		Object obSesion = req.getSession().getAttribute(OBJ_IN_SESSION);
		this.log.debug("Objeto de sesion encontrado [" + obSesion +"]");
		if(obSesion != null){
			response = true;
		}
		this.log.debug("isObjetoLoginEnSesion [" + response  +"]");
		return response;
	}
	
	
	
/**
 * 
 * @param req
 * @param ftrChain
 * @param res
 * @throws ServletException
 * @throws IOException
 */
	private void redireccionarLogin(HttpServletRequest req , 	FilterChain ftrChain , HttpServletResponse res) throws ServletException, IOException{
		this.storeError(req, "La sesi\u00F3n ha expirado o no se ha firmado al sistema.");
		req.getRequestDispatcher(URL_LOGIN).forward(req, res);
		ftrChain.doFilter(req, res);
	}
	

	/**
	 * 
	 * @param req
	 * @param ftrChain
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redireccionarLoginJSON(HttpServletRequest req , 	FilterChain ftrChain , HttpServletResponse res) throws ServletException, IOException{
	
		this.storeError(req, "La sesi\u00F3n ha expirado o no se ha firmado al sistema.");
		req.getRequestDispatcher(URL_REDIRECT ).forward(req, res);
		ftrChain.doFilter(req, res);
	}
	
	
	/**
	 * 
	 * @param req
	 * @param msj
	 */
	private void storeError(ServletRequest req, String msj) {
		   
	}
	
	
	/**
	 * 
	 */
	public static void main (String args[]){
	
		
	}

}
