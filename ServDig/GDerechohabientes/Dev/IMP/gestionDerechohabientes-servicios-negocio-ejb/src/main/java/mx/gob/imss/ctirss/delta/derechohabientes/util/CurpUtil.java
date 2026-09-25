package mx.gob.imss.ctirss.delta.derechohabientes.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CurpUtil{
	
	private static Log logIdee = LogFactory.getLog(CurpUtil.class);
	
	public static String dateFormatCustom(Date fecha, String formato){
		SimpleDateFormat sDF=new SimpleDateFormat(formato);
		return sDF.format(fecha);
	}
	
	
	public static int codGen(int ri,int k,int a){
		int y;
		int respuesta =0;
		int ResX ;
		
		y = ((ri - a) / k);
		
		if (y < 0) {
			y = 0;
		}
		
		ResX = ((ri + k) - ((((y * k) + a) + k)));
		
		respuesta =ResX;
	
		if(ResX <= 0){
			respuesta =0;
		}
		
		return respuesta;
	} 
	
	
	
	public static String validarNombreCompuesto(String nombreApellido, Boolean apellido) {
		String[] cadena = nombreApellido.split(" ");
		List<String> letrasExclusion = apellido ? CurpUtil.letrasApellidos() : CurpUtil.letrasNombres();
		String cadenaReturn = null;
		if(cadena.length > 1) {
			for(int i=0; i< cadena.length ; i++) {
				
				if(!CurpUtil.empiezaCon(cadena[i], letrasExclusion) && cadena[i].trim().length() > 0) {
					cadenaReturn = cadena[i];
					break;
				} 
			}
		} else {
			cadenaReturn = cadena[0];
		}
		
		if(cadenaReturn == null) {
			cadenaReturn = nombreApellido;
		}
		
		return cadenaReturn;
	}
	
	public static Boolean empiezaCon(String palabra, List<String> letrasIniciales) {

		for(String letrasInicial : letrasIniciales) {
			if(palabra.trim().equals(letrasInicial)) {
				return true;
			}
		}
	
		return false;
	}
	
	public static List<String> letrasNombres() {
		
		List<String> letras = new ArrayList<String>();
		
		letras.add("MARIA");
		letras.add("MA");
		letras.add("M");
		letras.add("JOSE");
		letras.add("J");
		letras.add("DA");
		letras.add("DAS");
		letras.add("DE");
		letras.add("DEL");
		letras.add("DER");
		letras.add("DI");
		letras.add("DIE");
		letras.add("DD");
		letras.add("EL");
		letras.add("LA");
		letras.add("LOS");
		letras.add("LAS");
		letras.add("LE");
		letras.add("LES");
		letras.add("MAC");
		letras.add("MC");
		letras.add("VAN");
		letras.add("VON");
		letras.add("Y");
		
		return letras;
		
	}
	
	public static List<String> letrasApellidos() {

		List<String> letras = new ArrayList<String>();

		letras.add("DA");
		letras.add("DAS");
		letras.add("DE");
		letras.add("DEL");
		letras.add("DER");
		letras.add("DI");
		letras.add("DIE");
		letras.add("DD");
		letras.add("EL");
		letras.add("LA");
		letras.add("LOS");
		letras.add("LAS");
		letras.add("LE");
		letras.add("LES");
		letras.add("MAC");
		letras.add("MC");
		letras.add("VAN");
		letras.add("VON");
		letras.add("Y");

		return letras;

	}

	public static List<Long> base26(long valorBase){
		int i =1;
		List<Long> vectOut=new ArrayList<Long>();
		Long mod26 =0L;
		long int26 =0;
		
		int26 = valorBase/36;
		mod26= valorBase -(36 *int26);	
		vectOut.add(mod26);
		
		do{
			mod26= int26 % 36;
			int26 = int26/36;
			vectOut.add(mod26);
			i+=1;
		}while ( int26>0  || i<=2); 
		
		return vectOut;
	}
	
	
	public static String getCodigo(int llave){
		Map<Integer,String> matCalidad =  new HashMap<Integer,String>();
			matCalidad.put(1, "1"); 
		    matCalidad.put(2, "2"); 
		    matCalidad.put(3, "2");
		    matCalidad.put(4, "2");
		    matCalidad.put(5, "2");
		    matCalidad.put(6, "2");
		    matCalidad.put(7, "2");
		    matCalidad.put(8, "2");
		    matCalidad.put(9, "2");
		    matCalidad.put(10, "2");
		    matCalidad.put(11, "3");
		    matCalidad.put(12, "4");
		    matCalidad.put(13, "5");
		    matCalidad.put(14, "6");
		    matCalidad.put(15, "7");
		    matCalidad.put(16, "8");
		    matCalidad.put(17, "9");
		    matCalidad.put(18, "0");
		    matCalidad.put(19, "A");
		    matCalidad.put(20, "B");
		    matCalidad.put(21, "C");
		    matCalidad.put(22, "D");
		    matCalidad.put(23, "E");
		    matCalidad.put(24, "F");
		    matCalidad.put(25, "G");
		    matCalidad.put(26, "H");
		    matCalidad.put(27, "I");
		    matCalidad.put(28, "J");
		    matCalidad.put(29, "K");
		    matCalidad.put(30, "L");
		    matCalidad.put(31, "M");
		    matCalidad.put(32, "N");
		    matCalidad.put(33, "O");
		    matCalidad.put(34, "P");
		    matCalidad.put(35, "Q");
		    matCalidad.put(36, "R");
		    matCalidad.put(37, "S");
		    matCalidad.put(38, "T");
		    matCalidad.put(39, "U");
		    
		   String calidad= matCalidad.get(llave);
		   
		   return calidad;
	}

}
