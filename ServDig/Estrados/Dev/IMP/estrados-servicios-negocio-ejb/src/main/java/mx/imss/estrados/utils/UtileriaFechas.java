package mx.imss.estrados.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

public class UtileriaFechas {
	
	private static final Logger logger = Logger.getLogger(UtileriaFechas.class);

	/**
	 * 
	 * @param fecha
	 * @param formato el formato puede ser:
	 * DateFormat.SHORT -> dd/mm/yy
	 *	DateFormat.MEDIUM -> dd/mm/yyyy
	 *	DateFormat.LONG -> dd de mm de yyyy
	 *	DateFormat.FULL -> dia dd de mes de yyy
	 * @return
	 */
	public static String cambiaFormatoFecha(Date fecha, int formato){
		DateFormat df = DateFormat.getDateInstance(formato);
		String s = df.format(fecha);
		return s;
		
	}
	
	public static java.util.Date parseStringToDate(String strDate) {
        java.util.Date utilDate = null;
        try {
        	if(!EstradosStringUtils.isReallyEmptyOrNull(strDate)) {
        		SimpleDateFormat formater = new SimpleDateFormat("dd/mm/yyyy");
                //formater.setLenient(false);
        		utilDate = formater.parse(strDate);
        	}
        } catch (ParseException ex) {
        	logger.debug("ERROR: Al convertir la cadena strDate: " + strDate + " en una utilDate."+ ex.getMessage());
        }
        return utilDate;
    }
	
	/**
     * Devuelve un String con la fecha que se le dio en el el formato especificado.
     * @param date Fecha que se desea convertir a String
     * @param formato Formato en el que sera devuleta la fecha.
     * @return 
     */
    public static String parseDateToString(java.util.Date utilDate, String formato) {
        String strDate = "";
        SimpleDateFormat format = new SimpleDateFormat(formato);
        try {
            strDate = format.format(utilDate);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.debug("ERROR: Al convertir la utilDate: " + utilDate + " en un String.", ex);
        }
        return strDate;
    }
	
}
