package mx.gob.imss.ctirss.correccion.web.controller.login;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuarioFuncionario;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.UserSession;

public class LoginByRequestOpenAM {
	
	private SegUsuario usrFirmado; 
	
	public LoginByRequestOpenAM(){
		
	}
	
	protected LoginByRequestOpenAM(HttpServletRequest request){
		this.setUsrFirmado(validaRequestOpenAM(request));
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
	 
	 protected void imprimirUsuarioSession(HttpServletRequest request) {
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
	 }

	 private SegUsuario validaRequestOpenAM(HttpServletRequest request){
		 
		usrFirmado = new SegUsuario();
		Enumeration<String> elementos = request.getAttributeNames();
		Map<String,Object> datos= new TreeMap<String, Object>();
		Map<String,String> datosOpenAM= new TreeMap<String, String>();
		String element= "";
		Map<Long, String> perfiles = new LinkedHashMap<Long, String>();
		Long j= 0L;
		while(elementos.hasMoreElements()){
			
			element = elementos.nextElement().toString();
			//System.out.println(element + " : " + request.getAttribute(element));
			
			if(element.startsWith(ConstantesSession.PREFIX_ATTR_CADENA)){
				datos.put(element, request.getAttribute(element));
			}
			if(element.equals(ConstantesSession.PREFIX_ATTR_PERFIL)){
//				@SuppressWarnings("unchecked")
//				Object objeto=request.getAttribute(element);
//				if(objeto!=null){
//					HashSet<String> profile =(HashSet<String>)objeto;
//					Iterator<String> itera = profile.iterator();
//					while(itera.hasNext()){
//						String[] p= itera.next().toString().split(",");
//						for(int i = 0; i<p.length; i++){
//								j++;
//								String perfil = p[i];
//								perfiles.put(j, perfil);
//						}
//					}					
//				}	
			}
		}
		try{
	//		INICIO Preparado para la cokie
	//		Cookie [] coquis = request.getCookies();
	//		HttpSession sesion = request.getSession();
	//		String valorCokie;
	//		for(int i=0; i<coquis.length; i++){
	//			if(coquis[i].getName()=="iPlanetDirectoryPro"){
	//				valorCokie = coquis[i].getValue();
	//			}
	//		}
	//		Cookie cookie = new Cookie("iPlanetDirectoryPro", valorCokie);
	//			FIN
			
			
			datosOpenAM = quitaCorchetes(datos);
			if(perfiles.size()==0){
				perfiles.put(0L, (String)datosOpenAM.get(ConstantesSession.ATTR_OPENAM.PERFIL.getAtributo()));
			}
			usrFirmado.setNomUsuarioSistema(datosOpenAM.get(ConstantesSession.ATTR_OPENAM.USUARIO.getAtributo()));
			usrFirmado.setCurpUsuario(datosOpenAM.get(ConstantesSession.ATTR_OPENAM.USUARIO.getAtributo()));
			usrFirmado.setNomNombre(datosOpenAM.get(ConstantesSession.ATTR_OPENAM.NOMBRE.getAtributo()));
			usrFirmado.setNomMaterno((String)datosOpenAM.get(ConstantesSession.ATTR_OPENAM.AP_MATERNO.getAtributo()));
			usrFirmado.setNomPaterno((String)datosOpenAM.get(ConstantesSession.ATTR_OPENAM.AP_PATERNO.getAtributo()));
			SacSubdelegacion codigosDelSub = new SacSubdelegacion();
			codigosDelSub.setUsuarioFirmado(new UserSession());
			codigosDelSub.getUsuarioFirmado().setCveCodigoDelegacion((String)datosOpenAM.get(ConstantesSession.ATTR_OPENAM.ID_DEL.getAtributo()));
			codigosDelSub.getUsuarioFirmado().setCveCodigoSubDelegacion((String)datosOpenAM.get(ConstantesSession.ATTR_OPENAM.ID_SUBDEL.getAtributo()));
			usrFirmado.setUsuarioFuncionario(new SegUsuarioFuncionario());
			usrFirmado.getUsuarioFuncionario().setSacSubdelegacion(codigosDelSub);
			usrFirmado.setPerfilesDisponibles(perfiles);
			return usrFirmado;
		}catch(NullPointerException e){
			return null;
		}
		
		
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
	 
	private Map<String, String> quitaCorchetes(Map<String,Object> datos ){
		Map<String,String> datosOpenAM =  new TreeMap<String, String>();
		Set<String> clavesMapa = datos.keySet();
		Iterator<String> itera = clavesMapa.iterator();
		String clave;
		String valor;
		while(itera.hasNext()){
			clave = itera.next();
			valor = datos.get(clave).toString();
			valor = valor.replace("[", "");
			valor = valor.replace("]", "");
			datosOpenAM.put(clave, valor);
		}
		return datosOpenAM;
	}

	public SegUsuario getUsrFirmado() {
		return usrFirmado;
	}

	public void setUsrFirmado(SegUsuario usrFirmado) {
		this.usrFirmado = usrFirmado;
	}
}
