package mx.imss.estrados.web.controller;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import mx.imss.ctirss.catalogos.model.DlcDelegacion;
//import mx.imss.ctirss.catalogos.model.DlcSubdelegacion;
//import mx.imss.ctirss.catalogos.model.DlcUsuario;
//import mx.imss.ctirss.catalogos.model.DlcUsuarioFuncionario;

public class LoginByRequestOpenAM {
	
	//private DlcUsuarioFuncionario usuario;
	
	public LoginByRequestOpenAM(){
		
	}
	
	public LoginByRequestOpenAM(HttpServletRequest request,  HttpSession ses){
		validaRequestOpenAM(request, ses);
	}

	 private void validaRequestOpenAM(HttpServletRequest request, HttpSession ses){

		Enumeration<String> elementos = request.getAttributeNames();
		String element= "";
		
		UsuarioVO user = new UsuarioVO();
	
		while(elementos.hasMoreElements()){
			element = elementos.nextElement().toString();
			System.out.println("ELEMENTO ------------------------------------------------------------------  ->"+  element);
			if(element.contains("SSO")){
				System.out.println(element + " : " + request.getAttribute(element).toString());

//				if(!quitaCorchetesString(request.getAttribute("SSO_SUBDELEGACION").toString()).equals("")){
//					user.setSubDeleg(""+Integer.parseInt(quitaCorchetesString(request.getAttribute("SSO_SUBDELEGACION").toString())));
//				}
//				user.setNombre(quitaCorchetesString(request.getAttribute("SSO_CN").toString()));
//				user.setaPaterno(quitaCorchetesString(request.getAttribute("SSO_SN").toString()));
//				user.setaMaterno(quitaCorchetesString(request.getAttribute("SSO_GIVENNAME").toString()));
				user.setUid(quitaCorchetesString(request.getAttribute("SSO_UID").toString()));
//				user.setCorreo(quitaCorchetesString(request.getAttribute("SSO_MAIL").toString()));
//				user.setSubDeleg(quitaCorchetesString(request.getAttribute("SSO_SUBDELEGACION").toString()));
//				user.setDeleg(quitaCorchetesString(request.getAttribute("SSO_DELEGACION").toString()));
				//DlcDelegacion del = new DlcDelegacion();
				//del.setCveCodigo(quitaCorchetesString(request.getAttribute("SSO_DELEGACION").toString()));
				//userFuncionario.setDlcDelegacion(del);
				//DlcSubdelegacion subdel = new DlcSubdelegacion();
				/*if(!quitaCorchetesString(request.getAttribute("SSO_SUBDELEGACION").toString()).equals("")){
					subdel.setCveSubdelegacion(Long.parseLong(quitaCorchetesString(request.getAttribute("SSO_SUBDELEGACION").toString())));
				}*/
				
			}
			
		/*	if(element.contains("IMSS")){
				System.out.println(element + " : " + request.getAttribute(element));
				Map<Long, String> perfil = quitaCorchetes((Set)request.getAttribute("IMSS_PERFILES"));
				user.setPerfil(quitaCorchetes((Set)request.getAttribute("IMSS_PERFILES")));
				//userFuncionario.getDlcUsuario().setPerfilesDisponibles(perfil);
				//userFuncionario.setDesCargo(quitaCorchetesString(perfil.values().toString()));

			}*/
		}
		//this.setUsuario(userFuncionario);
		System.out.println("ses ---A " +ses.getId());
		System.out.println("req ----A " +request.getSession().getId());
	//	ses.setAttribute("usuarioLogin", user);
		//ses.setAttribute("test", user.getUid());
		//ses.setAttribute("timeIN", System.currentTimeMillis()/1000);
		//ses.setAttribute("timeMS", System.currentTimeMillis()/1000);
		request.setAttribute("test1", "hola");
		request.getSession().setAttribute("test", user.getUid());
		request.getSession().setAttribute("usuarioLogin", user);
		request.getSession().setAttribute("timeIN", System.currentTimeMillis()/1000);
		request.getSession().setAttribute("timeMS", System.currentTimeMillis()/1000);
	}
	 
	private Map<Long, String> quitaCorchetes(Set<String> datos ){
		Map<Long,String> datosOpenAM =  new TreeMap<Long, String>();
//		Set<String> perfiles = new TreeSet<String>();
		Iterator<String> itera = datos.iterator();
//		String clave;
		String valor;
		int i=0;
		String valores[];
		while(itera.hasNext()){
			valor = itera.next();
			valores = valor.split(",");
//			valor = datos.iterator()
			valor = valores[i];
			valor = valor.replace("[", "");
			valor = valor.replace("]", "");
			
			datosOpenAM.put((long)i++, valor);
			break;
		}
		return datosOpenAM;
	}

	private String quitaCorchetesString(String cadena ){
		cadena = cadena.replace("[", "");
		cadena = cadena.replace("]", "");
			
		return cadena;
	}
	

	
}
