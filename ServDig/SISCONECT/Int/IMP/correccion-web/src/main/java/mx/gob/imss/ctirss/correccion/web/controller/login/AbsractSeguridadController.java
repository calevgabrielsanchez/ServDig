/**
 * 
 */
package mx.gob.imss.ctirss.correccion.web.controller.login;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.login.model.SegMenu;
import mx.gob.imss.ctirss.correccion.session.MenuVO;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;

/**
 * @author Vladimir Aguirre Piedragil
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 09/01/2012
 */
public abstract class AbsractSeguridadController extends AbstractController{
	
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
		 System.out.println("recuperando los datos del usuario enviados por el OpenAM");
		 
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
			 System.out.println("los atributos del reques son:" + (String)req.nextElement());
		 }
		 
		 System.out.println(" SSO - User [" + sso_user + "]" );
		 System.out.println(" SSO - Delegacion [" + sso_delegacion + "]" );
		 System.out.println(" SSO - Subdelegacion [" + sso_subdelegacion + "]" );
		 System.out.println(" SSO - UMF [" + sso_umf + "]" );
		 System.out.println(" SSO - CURP [" + sso_cupr + "]" );
		 System.out.println(" SSO - Perfil [" + sso_perfil + "]" );
		 System.out.println(" SSO - Id Persona [" + sso_id_persona + "]" );
		 System.out.println(" SSO - SISTEMAS [" + sso_list_sistemas + "]" );
		 System.out.println(" SSO - PERFILES [" + sso_perfiles + "]" );
		 
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
			 System.out.println(e);
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
	 * Transforma el menu del back end en un menu del front end
	 * 
	 * @param listaIn
	 * @return
	 */
	protected List<MenuVO> transformarMenu(List<SegMenu> listaIn) {
		final List<MenuVO> listaResp = new ArrayList<MenuVO>();
		MenuVO resp = null;
		for (SegMenu in : listaIn) {
			resp = new MenuVO();
			resp.setCvePK(in.getCveIdMenu());
			if (in.getSegMenu() != null) {
				resp.setCveFkMenuItem(String.valueOf(in.getSegMenu().getCveIdMenu()));
			}
			// resp.setCveFkRol(in.getCveFkRol());
			resp.setDesEtiqueta(in.getDesDescripcion());
			resp.setDesURL(in.getDesVinculo());
			resp.setNumOrden(in.getNumOrden());
			listaResp.add(resp);
		}
		return listaResp;
	}
}
