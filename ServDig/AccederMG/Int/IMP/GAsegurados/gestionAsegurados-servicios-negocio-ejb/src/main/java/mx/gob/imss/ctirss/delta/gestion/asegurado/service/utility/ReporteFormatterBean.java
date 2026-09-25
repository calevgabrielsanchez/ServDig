package mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Permite dar formato a los campos requeridos en reportes de jasper,
 * particularmente aquellos relacionados con diversos formatos de fechas.
 * 
 * @author Cesar Garcia Mauricio
 * @since 03 Julio 2012
 * 
 */
public class ReporteFormatterBean implements Serializable {//, ReporteFormatter {

	private static final long serialVersionUID = -78439013220997445L;
	private static final Locale LOCALE_MX = new Locale("es", "mx");

	// @Override
	public String getMes(final Date fecha) {
		if (fecha != null) {
			final Calendar fechaCal = new GregorianCalendar(LOCALE_MX);
			fechaCal.setTime(fecha);
			return fechaCal.getDisplayName(Calendar.MONTH, Calendar.LONG,
					LOCALE_MX).toUpperCase(LOCALE_MX);
		}
		return null;
	}

	// @Override
	public Integer getAnio(final Date fecha) {
		if (fecha != null) {
			final Calendar fechaCal = new GregorianCalendar(LOCALE_MX);
			fechaCal.setTime(fecha);
			return fechaCal.get(Calendar.YEAR);
		}
		return null;
	}

	public String getNombreMes(final Integer mes) {
		if(mes != null) {
			DateFormatSymbols symbols = new DateFormatSymbols(LOCALE_MX);
		    String[] monthNames = symbols.getMonths();
		    return monthNames[mes - 1].toUpperCase(LOCALE_MX);
		}
		return null;
	}
	
	public String getFechaNacimiento(Date fecha, Integer mes, Integer anio) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",LOCALE_MX);
		if(fecha != null) {
			return sdf.format(fecha);
		} else {
			if(mes != null && anio!=null && mes != 0 && anio != 0) {
				String mesaux = ""+mes;
				if(mesaux.length()==1) {
					mesaux = "0"+mes;
				}
				return mesaux+"/"+anio;
			}
		}
		
		return null;
	}
}
