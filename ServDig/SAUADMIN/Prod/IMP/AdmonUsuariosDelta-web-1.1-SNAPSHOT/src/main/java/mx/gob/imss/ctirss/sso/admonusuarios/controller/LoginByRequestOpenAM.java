package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

public class LoginByRequestOpenAM {
	
	private String curp;
	
	public LoginByRequestOpenAM(){
		
	}
	
	protected LoginByRequestOpenAM(HttpServletRequest request){
		this.setCurp(validaRequestOpenAM(request));
	}

	 private String validaRequestOpenAM(HttpServletRequest request){
		@SuppressWarnings("unchecked")
		Enumeration<String> elementos = request.getAttributeNames();
		String element= "";
		String curp="";
		while(elementos.hasMoreElements()){
			element = elementos.nextElement().toString();
			System.out.println(element + " : " + request.getAttribute(element));
			if(element.contains("SSO_UID")){
				Set curpMap = (HashSet)request.getAttribute(element);
				Iterator i = curpMap.iterator();
				curp= (String)i.next();
//				curp = (String)request.getAttribute(element);
				break;
			}
		}
		return curp;
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

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}
}
