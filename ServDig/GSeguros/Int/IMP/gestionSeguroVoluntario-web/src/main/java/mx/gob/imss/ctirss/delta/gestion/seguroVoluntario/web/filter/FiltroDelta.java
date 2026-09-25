package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.filter;



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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FiltroDelta implements Filter {
	
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FiltroDelta.class);
	
	
	
	private static final String URL_WELCOME = "/welcome";
	
	private static final String URL_LOGIN = "/login";
	
	private static final String URL_REDIRECT = "/logout";
	
	private static final String URL_COMBO = "/combo";
	
	private static final String URL_PORTAL = "/solicitud";
	
	private static final String URL_UBICAR_DOMICILIO_NACIONAL = "/domicilio/nacional";
	
	
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
		
		this.LOGGER.info("Inicializando filtro de seguridad del sistema de gestion patronal");
		
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
		
		
		this.LOGGER.info("Objeto de sesion Login [" + objSession +"]");
		this.LOGGER.info(" URL solicitada [" + url + "]");
		this.LOGGER.info("Content - Type [" + contentType + "]");
		
		
		
		if(this.isPeticionPermitida(url, req)){
			/*
			 * Si la peticion esta marcada como permitida, esto quiere decir que no importa si la sesion es nueva 
			 * o que no exista el objeto de la sesion y que puede ser procesada
			 */
			ftrChain.doFilter(req, res);
			
		}else{
			
			/*
			 * Si la peticion no es permitida, se debe de validar que el objeto de sesion (Login) exista
			 * y que si es una peticion de tipo JSON (ajax) redireccione correctamente al login.
			 */
			
			if(session.isNew()){
				
				this.LOGGER.info("La sesion es nueva " + session);
				
				if (isPeticionJSON(url, contentType)){
					
					this.redireccionarLoginJSON(req,ftrChain, res);
					
				}else{
					this.LOGGER.debug("Redireccionando a la pantalla principal por que la sesion es nueva");
					this.redireccionarLogin(req, ftrChain, res);
				}
				
				
				
			}else{
				
				/*
				 * Ya existe la sesion
				 */
				
				this.LOGGER.info("La sesion ya existe " + session);
				
				if(!isObjetoLoginEnSesion(req)){
					
					if (isPeticionJSON(url, contentType)){
						this.redireccionarLoginJSON(req,ftrChain, res);
					}else{
						this.LOGGER.debug("Redireccionando a la pantalla principal por que no existe el objeto de sesion ");
						this.redireccionarLogin(req, ftrChain, res);
					}
					
				}else{
					this.LOGGER.debug(" La peticion puede proceder sin problemas");
					ftrChain.doFilter(req, res);
				}
			}
		}
		
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		this.LOGGER.debug("Pasando por FiltroDelta.... ");

	}
	
	
	private boolean isPeticionPermitida(String url, HttpServletRequest request) {
	    boolean response = false;
	    this.LOGGER.debug("Validando si la peticion es permitida (no requiere de una sesion activa) [ " + url +" ]");
	    
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
		}else if(url.contains(URL_UBICAR_DOMICILIO_NACIONAL)){
			response = true;
		}
		
		
		
		
		
		
		
		
		this.LOGGER.debug("isPeticionPermitida [ " + response  +"]");
	    return response;
	  }
	
	
	/**
	 * Metodo que verifica que la URL solicitada es de tipo JSON
	 * @param url
	 * @param contentType
	 * @return
	 */
	private boolean isPeticionJSON(String url , String contentType){
		this.LOGGER.debug("Validando si la peticion es de tipo json [ " + url  +" ] , [" + contentType +"]");
		boolean response = false;
		if(contentType != null){
			
			if(contentType.contains(CONTENT_TYPE_JSON)){
				response = true;
			}
		}else{
			this.LOGGER.debug("content type es nulo");
			
		}
		this.LOGGER.debug("isPeticionJSON[ " + response  +" ] ");
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
		this.LOGGER.debug("Objeto de sesion encontrado [" + obSesion +"]");
		if(obSesion != null){
			response = true;
		}
		this.LOGGER.debug("isObjetoLoginEnSesion [" + response  +"]");
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
