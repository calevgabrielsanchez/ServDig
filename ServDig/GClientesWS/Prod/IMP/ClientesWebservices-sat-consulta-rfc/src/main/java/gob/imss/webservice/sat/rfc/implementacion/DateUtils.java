package gob.imss.webservice.sat.rfc.implementacion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;

public final class DateUtils {

    private static final Locale LOCALE_MX;

    static {
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
            	e.printStackTrace();
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
            	e.printStackTrace();
            }
        }
        return dateAsDate;
    }

}
