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
public class ReporteFormatter implements Serializable {

	private static final long serialVersionUID = -78439013220997445L;
	private static final Locale LOCALE_MX = new Locale("es", "mx");
	
	public String getMes(final Date fecha) {
		if (fecha != null) {
			final Calendar fechaCal = new GregorianCalendar(LOCALE_MX);
			fechaCal.setTime(fecha);
			return fechaCal.getDisplayName(Calendar.MONTH, Calendar.LONG,
					LOCALE_MX).toUpperCase(LOCALE_MX);
		}
		return null;
	}

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
	
	public String getTituloFecha(Date fecha, Integer mes, Integer anio) {
		if(fecha != null) {
			return "Fecha de nacimiento:  ";
		} else {
			if(mes != null && anio!=null && mes != 0 && anio != 0) {
				return "Mes de nacimiento:  ";
			} else {
				return "Fecha de nacimiento:  ";
			}
		}
	}
	
	public String getNumeroAnio(final Integer anio) {
		if(anio != null && anio != 0) {
			
			return ""+anio;
		}
		
		return null;
	}
	
	public String getNumeroMes(final Integer mes) {
		if(mes != null && mes != 0) {
			String mesaux = ""+mes;
			
			if(mesaux.length() == 1) {
				mesaux = "0"+mes;
			}
			
			return mesaux;
		}
		
		return null;
	}
	
	public String getFechaOMes(Date fecha, Integer mes, Integer anio){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",LOCALE_MX);
		if(fecha != null) {
			return sdf.format(fecha);
		} else {
			if(mes != null && anio!=null && mes != 0 && anio != 0) {
				String mesaux = ""+mes;
				if(mesaux.length()==1) {
					mesaux = "0"+mes;
				}
				return mesaux;
			}
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
	
	public String getEdad(Date fechaNacimiento) {
		int edad = 0;
		

		if(fechaNacimiento != null) {
			
			Calendar fechaNac = new GregorianCalendar();
			fechaNac.setTime(fechaNacimiento);
			
			
		    int dia = fechaNac.get(Calendar.DATE);
		    int mes = fechaNac.get(Calendar.MONTH);
		    int anio = fechaNac.get(Calendar.YEAR);
		    
		    System.out.println("El dia de nacimiento es: " + dia + " mes: " + mes + " anio " + anio);
		    // cogemos los valores actuales
		    Date fecha_hoy = new Date();
		    Calendar fechaActual = new GregorianCalendar();
		    fechaActual.setTime(fecha_hoy);
			

		    int ahora_dia = fechaActual.get(Calendar.DATE);
		    int ahora_mes = fechaActual.get(Calendar.MONTH);
		    int ahora_anio =fechaActual.get(Calendar.YEAR);
		    
		    System.out.println("El dia de hoy es: " + ahora_dia + " mes: " + ahora_mes + " anio " + ahora_anio);
		    // realizamos el calculo
		    edad =(ahora_anio + 1900) - anio;
		    
		    if ( ahora_mes < mes )
		    {
		        edad--;
		    }
		    if ((mes == ahora_mes) && (ahora_dia < dia))
		    {
		        edad--;
		    }
		    
		    if (edad > 1900)
		    {
		        edad -= 1900;
		    }
		
		    // calculamos los meses
		    int meses=0;
		    if(ahora_mes>mes)
		        meses=ahora_mes-mes;
		    if(ahora_mes<mes)
		        meses=12-(mes-ahora_mes);
		
		} else {
			return null;
		}
		
		return ""+edad;
	}
}
