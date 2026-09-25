package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

public class Test {

	/**
	 * @param args
	 */
	
	

	public static void main(String[] args) {

		double numeroOriginal = 123.4567;

		// Convertir a BigDecimal y truncar a 5 decimales
		BigDecimal bd = new BigDecimal(numeroOriginal);
		bd = bd.setScale(5, RoundingMode.DOWN);

		// Formatear como cadena
		DecimalFormat df = new DecimalFormat("0.00000");
		String resultado = df.format(bd.doubleValue());

		System.out.println("Número formateado en cadena: " + resultado);
	}
	
//	public static void main(String[] args) {
//
////		String x = "LOCALIDAD -------------------- C.P. ----- MUNICIPIO --------------------------------------------------, -------------------------------------------";
////		
////		if(x.startsWith("LOCALIDAD -------------------- C.P. ----- MUNICIPIO -------")){
////			System.out.println("ok IF");
////		}else{
////			System.out.println("No IF");
////		}
////		
////		Calendar c = Calendar.getInstance();
////		c.set(2021, 10, 31);
////		int num_dia = Calendar.DAY_OF_YEAR;
////		
////		System.out.println("Dia: " + num_dia);
//		
//		Fisica fisica = new Fisica();
//		
//		fisica.setNombre(null);
//		fisica.setPrimerApellido("MAYA");
//		fisica.setSegundoApellido(null);
//				
//		String nombre2 = fisica.getNombre() + " " + fisica.getPrimerApellido()!=null ? fisica.getPrimerApellido() : "" + " " + fisica.getSegundoApellido()!=null ? fisica.getSegundoApellido() : "";
//
//		String nombre3 = fisica.getPrimerApellido()!=null ? fisica.getNombre() + " " + fisica.getPrimerApellido() : "" + " " + fisica.getSegundoApellido()!=null ? fisica.getSegundoApellido() : "";
//
//		nombre3 = fisica.getSegundoApellido()!=null ? nombre3 + " " + fisica.getSegundoApellido() : nombre3;
//		
//		StringBuffer nombre=new StringBuffer();
//		
//		if(!StringUtils.isBlank(fisica.getNombre())) {
//			nombre.append(fisica.getNombre());
//		}
//		if(!StringUtils.isBlank(fisica.getPrimerApellido())) {
//			nombre.append(" "+fisica.getPrimerApellido());
//		}
//		if(!StringUtils.isBlank(fisica.getSegundoApellido())) {
//			nombre.append(" "+fisica.getSegundoApellido());
//		}
//		
//		
//		System.out.println(nombre2);
//		
//		System.out.println(nombre3);
//		
//		System.out.println(nombre);
//		
//
//	}

}
