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

	 private SegUsuario validaRequestOpenAM(HttpServletRequest request){
		SegUsuario usrFirmado = new SegUsuario();
		Enumeration<String> elementos = request.getAttributeNames();
		Map<String,Object> datos= new TreeMap<String, Object>();
		Map<String,String> datosOpenAM= new TreeMap<String, String>();
		String element= "";
		Map<Long, String> perfiles = new LinkedHashMap<Long, String>();
		Long j= 0L;
		while(elementos.hasMoreElements()){
			
			element = elementos.nextElement().toString();
			System.out.println(element + " : " + request.getAttribute(element));
			
			if(element.startsWith(ConstantesSession.PREFIX_ATTR_CADENA)){
				datos.put(element, request.getAttribute(element));
			}
			if(element.equals(ConstantesSession.PREFIX_ATTR_PERFIL)){
				@SuppressWarnings("unchecked")
				HashSet<String> profile =(HashSet<String>)request.getAttribute(element);
				Iterator<String> itera = profile.iterator();
				while(itera.hasNext()){
					String[] p= itera.next().toString().split(",");
					for(int i = 0; i<p.length; i++){
//						if(p[i].startsWith(ConstantesSession.PREFIX_DATO_PERFIL)){
							j++;
//							String perfil = p[i].replace(ConstantesSession.PREFIX_DATO_PERFIL, "");
							String perfil = p[i];
							perfiles.put(j, perfil);
//						}
					}
				}
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
