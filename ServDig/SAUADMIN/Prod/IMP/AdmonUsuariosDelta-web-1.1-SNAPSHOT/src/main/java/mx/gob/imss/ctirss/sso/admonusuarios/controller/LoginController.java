package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.sso.admonusuarios.MB.SolicitudMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.SolicitudPrincipalMB;
import mx.gob.imss.ctirss.sso.admonusuarios.MB.UsuarioMB;
import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AprobadorDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.AprobadoresServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.modelo.UsuarioNominaResponse;

@ManagedBean(name="login")
@CustomScoped("#{window}")
public class LoginController{
	


	/**
	 * 
	 */

	private final static Logger logger = Logger.getLogger(LoginController.class);
	
//	@EJB
//	private AdmonUsuariosSessionRemote admonUsuarioService;

	@EJB
	private AprobadoresServiceLocal aprobadorUsuarioService;
	@EJB
	private BitacoraServiceLocal bitacoraService;
		
	@ManagedProperty(value="#{usuarioMB}")	 
	private UsuarioMB usuarioMB;

	private Properties props = new Properties();
			
	public boolean renovacion = false;
	public boolean finSession = false;


	public final static long TIEMPO_INACTIVIDAD=600;
	public final static long TIEMPO_MAXIMO_SESION=3000;
	
	public void validarCredenciales() throws AdmonUsuariosException, IOException {
		logger.debug("inicia el metodo valida credenciales");
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();		
//		if(request.getSession(false)!=null){
//			request.getSession().invalidate();
//			logout(request);
//		}
//		request.getSession(true);
//		LoginByRequestOpenAM login = new LoginByRequestOpenAM(request);
//		AprobadorDTO aprobador = aprobadorUsuarioService.ValidaAprobadorByCurp(login.getCurp());
//		request.getSession().setAttribute("User", aprobador);
//		logger.debug("Camino a seleccionar perfil de usuario");
		FacesContext.getCurrentInstance().getExternalContext().redirect("../index.jsp");
//		FacesContext.getCurrentInstance().getExternalContext().redirect("http://desarrollo.imss.gob.mx:7001/Sauimssdigital");
//		return "out";// para seleccionar el perfil
		logger.debug("finaliza el metodo valida credenciales");
	}
	

	public void  logoutSistema() throws  IOException, AdmonUsuariosException{
		logger.debug("inciia metodo logoutSistema");
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getSession(false)!=null){
			request.getSession().invalidate();
			try {
				logout(request);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.debug("finaliza metodo logoutSistema");
//		FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
//		response.sendRedirect(serviceUrl + "/UI/Login?goto=" + 
//				request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/")) + "/validarCredenciales.do");
		logger.debug("finaliza metodo logoutSistema");
		validarCredenciales();
	}
	

	private void logout(HttpServletRequest request)throws IOException {
		props.load(SolicitudMB.class.getResourceAsStream("/fqdn.properties"));
		String fqdnOut = (String)props.getProperty("fqdnOut");
		
		String url = fqdnOut + "/UI/Logout?realm=SAUDigital";
	    HttpURLConnection connection =(HttpURLConnection)(new URL(url).openConnection());
	    connection.setDoOutput(true);
	    connection.setDoInput(true);
	    connection.setRequestMethod("POST");
	    connection.setRequestProperty("Content-type","application/x-www-form-urlencoded");

	    forwardCookies(request, connection, getCookieNamesToForward());

	    OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
	    osw.write("dummy");
	    osw.flush();
	    osw.close();
	    connection.getResponseCode();
	}
	
	private Set getCookieNamesToForward() throws IOException {
	    Set nameSet = new HashSet();
		props.load(SolicitudMB.class.getResourceAsStream("/fqdn.properties"));
		String fqdnOut = (String)props.getProperty("fqdnOut");
		
	    String url = fqdnOut + "/identity/getCookieNamesToForward";
	    HttpURLConnection connection = (HttpURLConnection)(new URL(url).openConnection());
	    BufferedReader br = new BufferedReader(new InputStreamReader((InputStream)connection.getContent()));
	    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	        String line = null;
	        while ((line = br.readLine()) != null) {
	            if (line.startsWith("string=")) {
	                line = line.replaceFirst("string=", "");
	                nameSet.add(line);
	            }
	        }
	    }
	    
	    return nameSet;
	}
	
	private void forwardCookies(HttpServletRequest request,
		    HttpURLConnection connection, Set<String> cookieNames) {

		    StringBuilder sb = new StringBuilder();
		    Cookie[] cookies = request.getCookies();
		    cookies = cookies == null ? new Cookie[0] : cookies;
		    for(Cookie cookie : cookies) {
		        String cookieName = cookie.getName();
		        if(cookieNames.contains(cookieName)) {
		            String cookieValue = cookie.getValue();
		            sb.append(cookieName);
		            sb.append("=");
		            sb.append(cookieValue);
		            sb.append(";");
		        }
		    }

		    if (sb.length() > 0) {
		        connection.setRequestProperty("Cookie", sb.toString());
		    }
		}
	
	public void validarUser() {
		logger.info("########## VALIDA USUARIO LOGIN ##########");
		
		if (!FacesContext.getCurrentInstance().isPostback()) {
			try {
				String error = "";
				
				LoginByRequestOpenAM login = new LoginByRequestOpenAM((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
				String curp = login.getCurp();
				
				// SUPER USUARIO PENSIONES
//				String curp = "IMSS12345678975653";
				
				// SUPER USUARIO DIT
//				String curp = "BAVC750915HDFRZS13";
								
				// DELEGACION PENSIONES
//				String curp = "MALE760915MASRPR00";
				
				// SUBDELEGACION PENSIONES
//				String curp = "IMSS12345678974910";
//				String curp = "MOMA810310HDFNRL02";
				
//				String curp = "IMSS12345678974876";
//				String curp = "VAOH900716HPLZRM00";
				
				// PRUEBAS
//				String curp = "BEGJ740302HDFRMN08";
				
				logger.info("########## CURP A VALIDAR COMO APROBADOR [" + curp + "] ##########");

				AprobadorDTO aprobador = aprobadorUsuarioService.validaAprobadorByCurp(curp);
				logger.info("########## APROBADOR [" + aprobador.toString() + "] ##########");
				String matricula = aprobador.getMatricula();
								
				UsuarioNominaResponse estatusNR = bitacoraService.datosSIAP(curp, matricula);
				Long estatus = Long.parseLong(estatusNR.getEstatus());
				
				logger.info("########## ESTATUS DEL USUARIO INGRESANDO [" + estatus + "] CON CURP [ " + curp +" ] ##########");
				
				if (estatus != 1L) {
					logger.error("El aprobador no se encontro en la base de datos");
					logger.error("...............................................");
					error = "Este usuario esta inactivo en el SIAP";
					FacesContext.getCurrentInstance().getExternalContext().redirect("../security/accesoDenegadoUsuarioInactivo.xhtml");
				} else if (aprobador != null) {
					logger.info("########## EL APROBADOR SE ENCONTRO REGISTRADO EN LA BASE ##########");
					HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

					session.setAttribute("timeIN", System.currentTimeMillis() / 1000);
					session.setAttribute("timeMS", System.currentTimeMillis() / 1000);

					session.setAttribute("usuarioAprobador", aprobador);
					usuarioMB.setAprobadorSession(aprobador);

					logger.info("########## SE GUARDA BITACORA DE AUTENTICACION ##########");
					
					bitacoraService.guardaLoginAprobadorBit(aprobador.getCveIdAprobador());

					if (usuarioMB.getAprobadorSession().getTipoAprobador() == 2 || usuarioMB.getAprobadorSession().getTipoAprobadorDpes() == 11332) {
						
						FacesContext.getCurrentInstance().getExternalContext().redirect("../aprobadores/consultaDelegacional.xhtml");
						
					} else if (usuarioMB.getAprobadorSession().getTipoAprobador() == 1 || usuarioMB.getAprobadorSession().getTipoAprobador() == 3) {
						
						FacesContext.getCurrentInstance().getExternalContext().redirect("../aprobadores/consultaGenerica.xhtml");
						
					} else {
						FacesContext.getCurrentInstance().getExternalContext().redirect("../aprobadores/consulta.xhtml");
					}
					
				} else {
					logger.error("El aprobador no se encontro en la base de datos");
					logger.error("...............................................");
					error = "Este usuario no tiene privilegios para accesar a este sistema";
					FacesContext.getCurrentInstance().getExternalContext().redirect("../security/accesoDenegado.xhtml");
				}

			} catch (AdmonUsuariosException e) {
				logger.error("###### Ocurrio error al valida usuario firmado en open am... {}" + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("###### Ocurrio error al valida usuario firmado en open am... {}" + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void validaSession() {
		try {
			if(renovacion||finSession)
			{
				logoutSistema();
			}
			else
			{
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		        long valorInactividad=(Long) session.getAttribute("timeIN"); 
		        long valorMaximoSession=(Long)session.getAttribute("timeMS"); 
		        long totalInactividad=(System.currentTimeMillis()/1000)-valorInactividad; 
		        long totalMaximoSession=(System.currentTimeMillis()/1000)-valorMaximoSession; 
//		        System.out.println("Valor Inactividad "+totalInactividad+" Maximo Session "+totalMaximoSession); 
		        if(totalInactividad>TIEMPO_INACTIVIDAD){ 
		        	renovacion = true;
		        } 
		        if(totalMaximoSession>TIEMPO_MAXIMO_SESION){ 
		        	finSession = true;
		        } 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void renovarSession()  
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("timeIN", System.currentTimeMillis()/1000);
		renovacion = false;
	}


	public Map<String,String> recuperaTiemposSession(HttpServletResponse response, HttpServletRequest request, HttpSession ses)
	{
        Map<String,String> mapaTiempos=new HashMap(); 
        boolean flagInactividad=true; 
        boolean flagMaximoSession=true; 
        
        long valorInactividad=(Long) request.getSession().getAttribute("timeIN"); 
        long valorMaximoSession=(Long)request.getSession().getAttribute("timeMS"); 
        
        long totalInactividad=(System.currentTimeMillis()/1000)-valorInactividad; 
        long totalMaximoSession=(System.currentTimeMillis()/1000)-valorMaximoSession; 
        System.out.println("Valor Inactividad "+totalInactividad+" Maximo Session "+totalMaximoSession); 
        if(totalInactividad>TIEMPO_INACTIVIDAD){ 
            flagInactividad=false; 
        } 
        
        if(totalMaximoSession>TIEMPO_MAXIMO_SESION){ 
            flagMaximoSession=false; 
        } 
        mapaTiempos.put("inactividad", String.valueOf(flagInactividad)); 
        mapaTiempos.put("maxsesion",  String.valueOf(flagMaximoSession)); 
        return mapaTiempos; 
	} 

	public String cerrarSession()
	{
		usuarioMB.setAprobadorSession(null);
		usuarioMB = new UsuarioMB();
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false); 
		if (session != null) { session.invalidate(); }
		
		
		return "login";
	}
	

	public UsuarioMB getUsuarioMB() {
		return usuarioMB;
	}
	public void setUsuarioMB(UsuarioMB usuarioMB) {
		this.usuarioMB = usuarioMB;
	}
	public boolean isRenovacion() {
		return renovacion;
	}
	public void setRenovacion(boolean renovacion) {
		this.renovacion = renovacion;
	}
	public boolean isFinSession() {
		return finSession;
	}
	public void setFinSession(boolean finSession) {
		this.finSession = finSession;
	}
}
