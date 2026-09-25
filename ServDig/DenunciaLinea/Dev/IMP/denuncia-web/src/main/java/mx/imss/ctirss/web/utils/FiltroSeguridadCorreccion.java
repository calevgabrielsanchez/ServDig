/**
 * 
 */
package mx.imss.ctirss.web.utils;

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

import mx.imss.ctirss.session.ConstantesSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Vladimir Aguirre
 * @author Lucio Dur�n
 * @author Joaquin Guevara
 * 
 */
@Component
public class FiltroSeguridadCorreccion implements Filter {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * Patron
	 */
	private static final String SERVLET_PATTERN = ".do";
	/**
	 * Presenta la pantalla de inicio (acceso).
	 */
	private static final String URL_WELCOME = "/welcome" + SERVLET_PATTERN;
	/**
	 * Valida credenciales (login) para funcionarios.
	 */
	private static final String URL_LOGIN = "/login/validarCredenciales";
	/**
	 * Valida credenciales (login) para patrones.
	 */
	private static final String URL_INICIAR_PATRON = "/iniciarPatron";
	
	private static final String URL_CONSULTA_DICTAMEN = "/consulta/dictamen";
	
	private static final String URL_DENUNCIA_LINEA_PATRON = "/denunciaLinea/datosPatron";
	
	private static final String URL_DENUNCIA_LINEA_TRABA = "/denunciaLinea/datosTrabajador";
	/**
	 * Salir del sistema (logout).
	 */
	private static final String URL_LOGOUT = "/logout" + SERVLET_PATTERN;
	/**
	 * Redirect para cuando se termino la sesi�n para una ptici�n Json.
	 */
	private static final String URL_REDIRECT = "/login/redirect"
			+ SERVLET_PATTERN;

	/*
	 * esta url es para los elementos estaticos (js, imagenes, css, etc)
	 */
	private static final String URL_STATIC = "static";

	private static final String CONTENT_TYPE_JSON = "json";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

		this.log.info("Inicializando filtro de seguridad del sistema de correccion en linea");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain ftrChain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		final String url = req.getServletPath();
		final String contentType = req.getContentType();

		this.log.info(" URL solicitada [" + url + "]");
		this.log.info("Content - Type [" + contentType + "]");

		
			ftrChain.doFilter(req, res);


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {

	}

	/**
	 * Las unicas peticiones permitidas son con las que entran al sistema
	 * (validar credenciales para funcionarios y patrones, welcome) y con las que se salen del sistema
	 * (logout, redirect).<br>
	 * <b>No</b> se deben agregar m�s peticiones dentro de este m�todo.
	 * 
	 * @param url
	 * @param request
	 * @return <code>true</code> si la petici�n es permitida, <code>false</code> en caso contrario
	 */
	private boolean isPeticionPermitida(String url, HttpServletRequest request) {
		boolean response = false;
		this.log.debug("Validando si la peticion es permitida (no requiere de una sesion activa) ["
				+ url + "]");

		if (url.contains(URL_LOGIN)) {
			response = true;
		} else if (url.contains(URL_INICIAR_PATRON)) {
			response = true;
		} else if (url.contains(URL_LOGOUT)) {
			response = true;
		} else if (url.contains(URL_WELCOME)) {
			response = true;
		} else if (url.contains(URL_REDIRECT)) {
			response = true;
		} else if (url.contains(URL_STATIC)) {
			response = true;
		}else if(url.contains(URL_DENUNCIA_LINEA_PATRON)){
			response = true;
		}else if(url.contains(URL_DENUNCIA_LINEA_TRABA)){
			response = true;
		}

		this.log.debug("isPeticionPermitida [" + response + "]");
		return response;
	}

	/**
	 * Metodo que verifica que la URL solicitada es de tipo JSON
	 * 
	 * @param url
	 * @param contentType
	 * @return
	 */
	private boolean isPeticionJSON(String url, String contentType) {
		this.log.debug("Validando si la peticion es de tipo json [" + url
				+ "] , [" + contentType + "]");
		boolean response = false;
		if (contentType != null) {
			if (contentType.contains(CONTENT_TYPE_JSON)) {
				response = true;
			}
		}
		this.log.debug("isPeticionJSON [" + response + "]");
		return response;
	}

	/**
	 * 
	 * @param req
	 * @return
	 */
	private boolean isObjetoLoginEnSesion(HttpServletRequest req) {
		boolean response = false;
		Object obSesion = req.getSession().getAttribute(
				ConstantesSession.USR_SESSION);
		
		if (obSesion != null) {
			response = true;
		}

		this.log.debug("isObjetoLoginEnSesion [" + response + "]");
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
	private void redireccionarLogin(HttpServletRequest req,
			FilterChain ftrChain, HttpServletResponse res)
			throws ServletException, IOException {
		this.storeError(req,
				"La sesi\u00F3n ha expirado o no se ha firmado al sistema.");

		res.sendRedirect(req.getContextPath() +URL_LOGOUT);
	}

	/**
	 * 
	 * @param req
	 * @param ftrChain
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redireccionarLoginJSON(HttpServletRequest req,
			FilterChain ftrChain, HttpServletResponse res)
			throws ServletException, IOException {

		this.storeError(req,
				"La sesi\u00F3n ha expirado o no se ha firmado al sistema.");

		res.setStatus(HttpServletResponse.SC_FORBIDDEN);
		req.getRequestDispatcher(URL_REDIRECT).forward(req, res);
		
	}

	/**
	 * 
	 * @param req
	 * @param msj
	 */
	private void storeError(ServletRequest req, String msj) {

	}

}
