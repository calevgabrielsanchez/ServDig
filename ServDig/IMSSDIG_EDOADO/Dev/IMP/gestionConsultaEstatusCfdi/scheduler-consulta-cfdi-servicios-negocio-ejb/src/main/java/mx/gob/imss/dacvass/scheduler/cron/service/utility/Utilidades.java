package mx.gob.imss.dacvass.scheduler.cron.service.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utilidades {
	
	private static final Logger log = LoggerFactory.getLogger(Utilidades.class);
	
	public static String escaparAmpersand(String cadena) {
		
		if (cadena.contains("&")) {
			log.info("########## STRING CON AMPERSAND SIN ESCAPAR [" + cadena + "] ##########");
			
			cadena = cadena.replaceAll("&", "&amp;");
			
			log.info("########## STRING CON AMPERSAND CON ESCAPE [" + cadena + "] ##########");
		}
		
		return cadena;
	}

}
