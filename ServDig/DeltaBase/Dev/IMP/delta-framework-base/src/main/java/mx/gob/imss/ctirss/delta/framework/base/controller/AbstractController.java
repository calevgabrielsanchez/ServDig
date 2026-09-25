/**
 * AbstractController.java
 * @package mx.gob.imss.delta.framework.base.controller
 * @project delta-framework-base	
 */
package mx.gob.imss.ctirss.delta.framework.base.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.web.ObjectError;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
public class AbstractController {

	protected final Log log = LogFactory.getLog(getClass());

    protected static final String MENSAJE_ERROR_BUSQUEDAS = "La persona <PERSONA> no fue localizada en IMSS ni en <ENTIDAD_EXTERNA>. Favor de intentar nuevamente con los criterios de busqueda de datos basicos";
    
	protected static final String KEY_CODE_ERROR_FIELDS = "erroresCaptura";

	protected static final String KEY_CODE_ERROR_BUSINESS = "erroresNegocio";
	
	protected static final String KEY_USUARIO ="usuario";

	protected static final String KEY_ORIGEN_CONTEXT = "ORIGEN_APP";	

	@Autowired
	protected MessageSource messageSource;
	
	
	 /**
	  * Metodo que procesa los errores de los campos requeridos para las peticiones asincronas
	  * @param errors : Errores de captura
	  * @param result: Objeto de respuesta de la peticion asincrona
	  */
	 protected void procesaErroresDeCaptura(final Errors errors , final Map<String, Object> result, final HttpServletResponse response) {
		 Map <String, String> erroresCampos = new HashMap<String, String>();
		 
		 
		 List<ObjectError > erroresList = new ArrayList<ObjectError>();
		 
			for (Object object : errors.getAllErrors()) {
	            if(object instanceof FieldError) {
	                FieldError fieldError = (FieldError) object;
	                erroresCampos.put(fieldError.getField(), messageSource.getMessage(fieldError, null));
	                ObjectError e = new ObjectError();
	                e.setCampo(fieldError.getField());
	                e.setMensaje(messageSource.getMessage(fieldError, null));
	                erroresList.add(e);
	            }
	        }
			result.put(KEY_CODE_ERROR_FIELDS, erroresList);
			response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED );
			
	 }
	 
	 /**
	  * 
	  * @param exception
	  * @param result
	  */
	 protected void procesarErrorDeNegocio(AbstractException exception, Map result, HttpServletResponse response){
		 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		 result.put(KEY_CODE_ERROR_BUSINESS, exception.getMessage());
		 
	 }

	 /**
		 * Metodo para inicializar el CustomDateEditor para el tratamiento (conversion) 
		 * de los objetos tipo Date.
		 * @param request
		 * @param binder
		 * @throws Exception
		 */
		 @InitBinder
		 protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
			 try{
		    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		    df.setLenient(false);
//		    CustomDateEditor editor = new CustomDateEditor(df, false);
		    CustomDateEditor editor = new CustomDateEditor(df, true); // se cambia el valor a 'true' para evitar que Spring mande errores de validacion al intentar
		    														  // convertir un String vacio en Date (en registroPersonaFisica.jsp cuando no se mete nada en
		    														  // el campo Fecha de Nacimiento)
		    binder.registerCustomEditor(Date.class, editor);
			 }catch (Exception e) {
				this.log.error(e);
				
			}
		}
	 
	 /**
	  * 
	  * @param session
	  */
	 protected void setFechaSistema(HttpSession session){
		 
		 Locale mxLocale = new Locale("es", "MX");
		 
		 DateFormat df = DateFormat.getDateInstance(DateFormat.FULL,mxLocale); 
		 String fecha = df.format(new Date(System.currentTimeMillis()));
		 session.setAttribute("fechaSistema", fecha);
		 
	 } 
	 
	 
	 /**
	  * Obtiene la informacion del usuario en la sesion
	  * @param session
	  * @return
	  */
	 protected Object getUsuarioEnSesion(HttpSession session){
		 this.log.info("Recuperando de la sesion la informacion del usuario");
		 return session.getAttribute("usuario");
		 
	 }
	 
	 
	 
	 protected static final  String SSO_USER_KEY = "SSO_UID";
	 protected static final  String SSO_USER_DELEGACION_KEY = "SSO_DELEGACION";
	 protected static final  String SSO_USER_SUBDELEGACION_KEY = "SSO_SUBDELEGACION";
	 protected static final  String SSO_USER_UMF_KEY = "SSO_UMF";
	 protected static final  String SSO_USER_CURP = "SSO_CURP";
	 protected static final  String SSO_USER_ROL_KEY = "SSO_PERFIL";
	 protected static final  String SSO_ID_PERSONA_KEY = "SSO_IDPERSONA";
	 protected static final  String SSO_USER_SISTEMAS = "IMSS_SISTEMAS";
	 protected static final  String SSO_IMSS_PERFILES = "IMSS_PERFILES";
	 
	 /**
	  * Metodo para recuperar la informacion obtenida
	  * del proceso de autentificacion del OpenAM.
	  * 
	  * @param request
	  * @return {@link} UsuarioSSO 
	  */
	 protected UsuarioSSO procesarUsuarioSSO(HttpServletRequest request){
		 this.log.debug("recuperando los datos del usuario enviados por el OpenAM");
		 
		 UsuarioSSO usuario = new UsuarioSSO();
		 
		 String[] listSistemas = null;
		 String[] listPerfiles = null;
		
		 
		 String sso_user =     (String) this.getValueFromHash( (HashSet) request.getAttribute(SSO_USER_KEY));
		 String sso_delegacion = (String) this.getValueFromHash( (HashSet) request.getAttribute(SSO_USER_DELEGACION_KEY));
		 String sso_subdelegacion = (String)this.getValueFromHash( (HashSet)  request.getAttribute(SSO_USER_SUBDELEGACION_KEY));
		 String sso_umf = (String)this.getValueFromHash( (HashSet)  request.getAttribute(SSO_USER_UMF_KEY));
		 String sso_cupr = (String) this.getValueFromHash( (HashSet) request.getAttribute(SSO_USER_CURP));
		 String sso_perfil = (String) this.getValueFromHash( (HashSet) request.getAttribute(SSO_USER_ROL_KEY));
		 String sso_id_persona = (String) this.getValueFromHash( (HashSet) request.getAttribute(SSO_ID_PERSONA_KEY));
		 String sso_list_sistemas =(String) this.getValueFromHash( (HashSet) request.getAttribute(SSO_USER_SISTEMAS));
		 String sso_perfiles =(String) this.getValueFromHash( (HashSet) request.getAttribute(SSO_IMSS_PERFILES));
		 
		 Enumeration req = request.getAttributeNames();
		 while(req.hasMoreElements()){
			 this.log.debug("los atributos del reques son:" + (String)req.nextElement());
		 }
		 
		 
		 
		 
		 
		 this.log.debug(" SSO - User [" + sso_user + "]" );
		 this.log.debug(" SSO - Delegacion [" + sso_delegacion + "]" );
		 this.log.debug(" SSO - Subdelegacion [" + sso_subdelegacion + "]" );
		 this.log.debug(" SSO - UMF [" + sso_umf + "]" );
		 this.log.debug(" SSO - CURP [" + sso_cupr + "]" );
		 this.log.debug(" SSO - Perfil [" + sso_perfil + "]" );
		 this.log.debug(" SSO - Id Persona [" + sso_id_persona + "]" );
		 this.log.debug(" SSO - SISTEMAS [" + sso_list_sistemas + "]" );
		 this.log.debug(" SSO - PERFILES [" + sso_perfiles + "]" );
		 
		 if(StringUtils.isNotBlank(sso_list_sistemas)){
			 listSistemas = sso_list_sistemas.split(",");
			 if(listSistemas.length>0){
				 usuario.setSistemas(listSistemas);
			 }
		 }
		 
		 if(StringUtils.isNotBlank(sso_perfiles)){
			 listPerfiles = sso_perfiles.split(",");
			 if(listPerfiles.length>0){
				 usuario.setPerfiles(listPerfiles);
			 }
		 }
		 
		 
		 
		 if (StringUtils.isNotBlank(sso_user)) {
			 usuario.setNombre(sso_user.trim());
		 } else {
			 usuario.setNombre(sso_user);
		 }
		 if (StringUtils.isNotBlank(sso_cupr)) {
			usuario.setCurp(sso_cupr.trim());
		 } else {
			 usuario.setCurp(sso_cupr);
		 }
		 if (StringUtils.isNotBlank(sso_perfil)) {
			 usuario.setPerfil(sso_perfil.trim());
		 } else {
			 usuario.setPerfil(sso_perfil);
		 }
		 try{
			 
			 if(sso_delegacion != null && !sso_delegacion.isEmpty()){
				 usuario.setDelegacion(Integer.parseInt(sso_delegacion));
			 }
			 if(sso_subdelegacion != null && !sso_subdelegacion.isEmpty()){
				 usuario.setSubdelegacion(Integer.parseInt(sso_subdelegacion));
			 }
			 if(sso_umf != null && !sso_subdelegacion.isEmpty()) {
				 usuario.setUmf(Integer.parseInt(sso_umf));
			 }
			 usuario.setIdPersona(new Integer (sso_id_persona));
		 }catch (Exception e) {
			this.log.error(e);
		}
		 
		 
		 /*Subimos a la sesion el usuario de sso */
		 
		 return usuario;
	 }
	 
	 
	 private Object getValueFromHash(HashSet hash){
		 if(hash != null){
			 Iterator it = hash.iterator();
			 while( it.hasNext()){
				 Object obj = it.next();
				  System.out.println("--->" +obj);
				  return obj;
			 }
		 }
		
		 return null;
	 }
	 
	 
	 /**
	  * Metodo para verificar si el usuario cuenta con los privilegios necesarios
	  * Se obtienen los permisos del contexto de spring y se verifica.
	  * @param rolesToCheck
	  * @return Un booleano verdadero en caso de contar con el privilegio.
	  */
	 public Boolean checkGrantedAuthorities (String[] rolesToCheck) {
		 Boolean hasGrantedAuthority = Boolean.FALSE;
		 if(rolesToCheck == null || rolesToCheck.length <=0){
			 this.log.warn("No se recibio la lista de roles a verificar");
			 return hasGrantedAuthority;
		 }
		 GrantedAuthority[]  authorities =   SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		 if(authorities != null && authorities.length > 0){
			 for (GrantedAuthority grantedAuthority : authorities) {
				 this.log.debug( grantedAuthority.getAuthority());
				 String authority =  grantedAuthority.getAuthority();
				 for( String roleToCheck : rolesToCheck){
					 if(roleToCheck.equals(authority)){
						 this.log.debug("Se encontro el rol ( privilegio ) -- TRUE");
						 return hasGrantedAuthority.TRUE;
					 }
				 }
			}
			 
		 }
		 return hasGrantedAuthority;
	 }

	public String getOrigenContext(HttpServletRequest request) {
		String origenContext = (String) request.getSession()
				.getServletContext().getInitParameter(KEY_ORIGEN_CONTEXT);
		return origenContext;
	}
}
