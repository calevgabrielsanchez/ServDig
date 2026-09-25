package mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.IllegalFormatException;

import javax.xml.bind.DatatypeConverter;

public class XmlDateTimeConverter {

	public static Date parseDateBackup(String s) {
		return DatatypeConverter.parseDate(s).getTime();
	}

	public static Date parseDate(String xmlDateTime) {
		Date fecha = null;
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        if ( xmlDateTime.length() != 25 )  {
        	System.out.println("XmlDateTimeConverter. Date not in expected xml datetime format");
        }

        StringBuilder sb = new StringBuilder(xmlDateTime);
        sb.deleteCharAt(22);
        try{
        	fecha = simpleDateFormat.parse(sb.toString());
        }
        catch(Exception e){
        	System.out.println("XmlDateTimeConverter. Error al convertir la fecha a objeto");
        }
        return fecha;
    }

	public static String printDateBackup(Date dt) {
		String sResultado = null;
		if (dt != null){
			Calendar cal = new GregorianCalendar();
			cal.setTime(dt);
			sResultado = DatatypeConverter.printDate(cal);			
		}
		return sResultado;
	}
	
    public static String printDate(Date xmlDateTime) throws IllegalFormatException  {
    	String sResultado = null;
		if (xmlDateTime != null){
	    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	        String s =  simpleDateFormat.format(xmlDateTime);
	        StringBuilder sb = new StringBuilder(s);
	        sb.insert(22, ':');
	        sResultado = sb.toString();
		}
		return sResultado;		
    }	
}
