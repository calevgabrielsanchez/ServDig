package mx.gob.imss.distss.derechohabientes.adimss.service.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fecha {

	
	public static String formatFecha(String fch) throws Exception{
		if(Validation.validarCampo(fch) && !fch.equals("--")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			Date date = format.parse(fch);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			return formatter.format(date);
		}else
			return "--";
	}
	
	public static String formatFecha(Date fch) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(fch);
	}
	
	public static String formatDateToString(Date fch) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatter.format(fch);
	}
	
	public static Date maxFecha(Date [] fchCompara) throws Exception{
		Date fchMax = new Date();
		for(int f = 0 ; fchCompara.length > f ; f++){
//			LOG.debug("Fecha: "+formatDateToString(fchCompara[f]));
			Date fchSDF = fchCompara[f];
			//si se trata del primer registro se almacena
			if(f==0){
				fchMax = fchSDF;
			}
			else{
				//si la nueva fecha es mayor a la que se encuentra actualmente, la fecha maxima se remplasa
				if(fchSDF.after(fchMax)){
					fchMax = fchSDF;
				}
			}
		}
		return fchMax;
	}
	
	public static String anioActual() throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(new Date());
	}
	
	public static String fechaActual(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(new Date());
	}
}
