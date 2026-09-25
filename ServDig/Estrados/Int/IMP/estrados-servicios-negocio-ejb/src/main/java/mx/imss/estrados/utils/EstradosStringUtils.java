package mx.imss.estrados.utils;

import org.apache.log4j.Logger;

public class EstradosStringUtils {

	private static final Logger log = Logger.getLogger(EstradosStringUtils.class);
    
    public static String assignValues(String string, Object[] values) {
        String a = string;
        for(int i = 0; i < values.length; i++) {
            a = a.replaceAll("\\{" + i + "}", values[i].toString());
        }
        return a.toString();
    }
    
    public static boolean isEmptyOrNull(String cadena) {
        return cadena == null || cadena.isEmpty();
    }

    public static boolean isReallyEmptyOrNull(String cadena) {
        return cadena == null || cadena.trim().isEmpty();
    }
    
    public static String convierteParam(String cadena) {
        if(cadena == null) {
            return null;
        } else {
            return cadena.trim().toUpperCase();
        }
    }
    
    public static Integer parseStringToInt(String string) {
        Integer numero = null;
        if(!isReallyEmptyOrNull(string)) {
            try {
                numero = Integer.parseInt(string);
            } catch (NumberFormatException ex) {
                log.info("Error al convertir la cadena");
                return null;
            }
        }
        return numero;
    }
    
    public static Double parseStringToDouble(String string) {
        Double numero = null;
        if(!isReallyEmptyOrNull(string)) {
            try {
                numero = Double.parseDouble(string);
            } catch (NumberFormatException ex) {
                log.info("Error al convertir la cadena");
                return null;
            }
        }
        return numero;
    }
    
    public static String getNullString(String cadena) {
        return isReallyEmptyOrNull(cadena) ? null : cadena;
    }
	
}
