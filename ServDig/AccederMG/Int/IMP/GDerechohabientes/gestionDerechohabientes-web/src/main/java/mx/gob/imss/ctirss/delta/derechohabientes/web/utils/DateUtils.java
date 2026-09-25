package mx.gob.imss.ctirss.delta.derechohabientes.web.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateUtils {

    private static final Locale LOCALE_MX;
    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(DateUtils.class);
        LOCALE_MX = new Locale("es", "mx");
    }

    private DateUtils() {
        // TO PMD
    }

    public static XMLGregorianCalendar dateToGregorianCalendar(final Date fecha) {
        XMLGregorianCalendar fechaXml = null; // NOPMD

        if (fecha != null) {
            try {
                final GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(fecha);
                fechaXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            } catch (DatatypeConfigurationException e) {
                LOG.error("In method dateToGregorianCalendar", e);
            }
        }
        return fechaXml;
    }

    public static Date xmlGregorianCalendarToDate(final XMLGregorianCalendar calendar) {
        Date fecha = null; // NOPMD

        if (calendar != null) {
            final Locale localeMx = new Locale("es", "mx");
            fecha = calendar.toGregorianCalendar(TimeZone.getDefault(), localeMx, calendar).getTime();
        }
        return fecha;
    }
    
    public static Date sumaAnios(Date fecha, int anios){
		Date fechaNew = null;
		int years = 0;
		String fns = DateUtils.dateToStringConFormato(fecha, "dd/MM/yyyy");
		int as = Integer.parseInt(fns.substring(6));
		years = as + anios;
		String nuevaFecha = fns.substring(0, 6) + years;
		fechaNew = DateUtils.dateToDateConFormato(nuevaFecha, "dd/MM/yyyy");	
		
		return fechaNew;
	}
    
    /**
     * SRG 120412 Este metodo regresa un String con la fecha FORMATEADA de
     * acuerdo al parametro formatoFecha
     * 
     * @param fecha
     * @param formatoFecha
     * @return
     */
    public static String dateToStringConFormato(final Date fecha, final String formatoFecha) {
        String dateAsString = null; // NOPMD
        if (fecha != null && StringUtils.isNotBlank(formatoFecha)) {
            dateAsString = new SimpleDateFormat(formatoFecha, LOCALE_MX).format(fecha);
        }
        return dateAsString;
    }
    
    /**
     * SRG 260412 Este metodo regresa un String con la fecha FORMATEADA de
     * acuerdo al DateFormat formatoFecha
     * 
     * @param fecha
     * @param formatoFecha
     * @return
     */
    public static String dateToStringConFormato(final Date fecha, final DateFormat formatoFecha) {
        String dateAsString = null; // NOPMD
        if (fecha != null && formatoFecha != null) {
            dateAsString = formatoFecha.format(fecha);
        }
        return dateAsString;
    }

    /**
     * SRG 120412 Este metodo regresa un Date con la fecha FORMATEADA de acuerdo
     * al parametro formatoFecha
     * 
     * @param fecha
     * @param formatoFecha
     * @return
     * @throws ParseException
     */
    public static Date dateToDateConFormato(final String fecha, final String formatoFecha) {
        Date dateAsDate = null; // NOPMD
        if (fecha != null && StringUtils.isNotBlank(formatoFecha)) {
            try {
                dateAsDate = new SimpleDateFormat(formatoFecha, LOCALE_MX).parse(fecha);
            } catch (ParseException e) {
                LOG.error("Error al parsear Fecha", e);
            }
        }
        return dateAsDate;
    }
    
    public static Date stringToDate(String format, String string) {  
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        
        Date date = null;
        try {  
            date = formatter.parse(string);            
        }
        catch(ParseException pe) { 
        	date = null; 
        }
        
        return date;                    
    }
    
    public static Date sumarDiasFecha(Date fecha,Integer dias){
		Calendar cal=Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DATE, dias);
		return cal.getTime();
	}

    /**
	 * Cuenta la diferencia de Dias entre dos fechas
	 */
	public static int getDaysBetweenDates(Date fechaInicial,Date fechafinal){
		int dias=0;
		Calendar calTemp=Calendar.getInstance();
		Calendar calFinal=Calendar.getInstance();
		calTemp.setTime(fechaInicial);
		calFinal.setTime(fechafinal);
		
		while(calTemp.compareTo(calFinal)==0){//si son iguales termina
			calTemp.roll(Calendar.DATE, true);
		}
		return dias;
	}
	
	public static long getEdad(Date fechaNacimiento) {
		Date hoy = new Date();
		Calendar fechaHoy = new GregorianCalendar();
		Calendar fechaNacimientoC = new GregorianCalendar();
		fechaHoy.setTime(hoy);
		fechaNacimientoC.setTime(fechaNacimiento);

		int restar = 0;
		long resultado = 0;

		if (fechaHoy.get(Calendar.MONTH) < fechaNacimientoC.get(Calendar.MONTH)) {
			restar += 1;
		}
		else
		if (fechaHoy.get(Calendar.MONTH) == fechaNacimientoC.get(Calendar.MONTH)) {
			if (fechaHoy.get(Calendar.DATE) < fechaNacimientoC.get(Calendar.DATE)) {
				restar += 1;
			}
		}

		resultado = fechaHoy.get(Calendar.YEAR)	- fechaNacimientoC.get(Calendar.YEAR);
		resultado -= restar;

		return resultado;

	}
	
	
	public static long getEdadRedondeadaEnAnios(Date fechaNacimiento) {
		Date hoy = new Date();
		Calendar fechaHoy = new GregorianCalendar();
		Calendar fechaNacimientoC = new GregorianCalendar();
		fechaHoy.setTime(hoy);
		fechaNacimientoC.setTime(fechaNacimiento);

		int restar = 0;
		long resultado = 0;
		int sumar =0;

		if (fechaHoy.get(Calendar.MONTH) < fechaNacimientoC.get(Calendar.MONTH)) {
			restar += 1;
		}
		else
		if (fechaHoy.get(Calendar.MONTH) == fechaNacimientoC.get(Calendar.MONTH)) {
			if (fechaHoy.get(Calendar.DATE) < fechaNacimientoC.get(Calendar.DATE)) {
				restar += 1;
			}
		}
	
		
		if (fechaHoy.get(Calendar.MONTH) > fechaNacimientoC.get(Calendar.MONTH)) {
			sumar += 1;
		}
		else
		if (fechaHoy.get(Calendar.MONTH) == fechaNacimientoC.get(Calendar.MONTH)) {
			if (fechaHoy.get(Calendar.DATE) > fechaNacimientoC.get(Calendar.DATE)) {
				sumar += 1;
			}
		}
		
		resultado = fechaHoy.get(Calendar.YEAR)	- fechaNacimientoC.get(Calendar.YEAR);
		resultado -= restar;
		resultado += sumar;

		return resultado;

	}
	
	
	public static Date getFechaFinVigencia(Date fechaNacimiento, int edadFinVigenica) {
		Calendar c = Calendar.getInstance();
		c.setTime(fechaNacimiento);
		c.add(Calendar.YEAR, edadFinVigenica);
		
		return c.getTime();
	}
	
	/*
	public static void main(String[] args){
		Calendar c = Calendar.getInstance();
		
		c.set(1982, 1, 22);
		
		System.out.println("F Nacimiento "+c.getTime());
		
		System.out.println("F Vencimeinto "+DateUtils.getFechaFinVigencia(c.getTime(), 25));
		
	}*/
	
	public static void main(String[] args){
		try{
		Calendar cal =  Calendar.getInstance();
		cal.set(2012, 11,26);
		
		System.out.println("la feccha en años queda como:" + getEdadRedondeadaEnAnios(cal.getTime()));
		}
		catch(Exception e){
			System.out.println("error" + e.getMessage());
		}
		
		
	}
	
	
	/**
	 * Calcula la edad de la persona basandose &uacute;nicamente en mes y a&ntilde;o de
	 * nacimiento.
	 * 
	 * Se tomar&aacute; el primer d&iacute;a del siguiente mes como la fecha de nacimiento
	 * 
	 * @param mes
	 * @param anio
	 * @return
	 */
	public static long getEdadSinDia(Integer mes, Integer anio) {
		Date hoy = new Date();
		Calendar fechaHoy = new GregorianCalendar();
		Calendar fechaNacimientoC = new GregorianCalendar();
		fechaHoy.setTime(hoy);
		fechaNacimientoC.setTime(getPrimerDiaSiguienteMes(anio,mes));

		int restar = 0;
		long resultado = 0;

		if (fechaHoy.get(Calendar.MONTH) < fechaNacimientoC.get(Calendar.MONTH)) {
			restar += 1;
		}
		else
		if (fechaHoy.get(Calendar.MONTH) == fechaNacimientoC.get(Calendar.MONTH)) {
			if (fechaHoy.get(Calendar.DATE) < fechaNacimientoC.get(Calendar.DATE)) {
				restar += 1;
			}
		}

		resultado = fechaHoy.get(Calendar.YEAR)	- fechaNacimientoC.get(Calendar.YEAR);
		resultado -= restar;

		return resultado;

	}
	
	
	public static Date getPrimerDiaSiguienteMes(Integer anioNacimiento,
			Integer mes) {

		// ------------------------------------------------------------------
		// Si el año se envía con 2 posiciones, se ajusta a 4
		// ------------------------------------------------------------------
		if ((anioNacimiento != null) && (anioNacimiento < 1900))
			anioNacimiento = formatAnioNac(anioNacimiento);

		Calendar cal = Calendar.getInstance();
		cal.set(anioNacimiento, mes,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));

		return cal.getTime();
	}

	public static Date getUltimoDiaSiguienteMes(Integer anioNacimiento,
			Integer mes) {

		// ------------------------------------------------------------------
		// Si el año se envía con 2 posiciones, se ajusta a 4
		// ------------------------------------------------------------------
		if ((anioNacimiento != null) && (anioNacimiento < 1900))
			anioNacimiento = formatAnioNac(anioNacimiento);

		Calendar cal = Calendar.getInstance();
		cal.set(anioNacimiento, mes,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		return cal.getTime();
	}
	
	public static Integer formatAnioNac(Integer anioNac){
		if(anioNac != null){
			return anioNac + 1900;
		}else{
			return null;
		}
	}

	    
	  
	
}
