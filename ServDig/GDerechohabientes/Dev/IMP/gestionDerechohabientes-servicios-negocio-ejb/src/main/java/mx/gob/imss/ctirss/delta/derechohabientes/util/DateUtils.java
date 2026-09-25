package mx.gob.imss.ctirss.delta.derechohabientes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;


public class DateUtils {
	
	private static final Locale LOCALE_MX;
    private static final Logger LOG;
    
	static {
        LOG = LoggerFactory.getLogger(DateUtils.class);
        LOCALE_MX = new Locale("es", "mx");
    }
	
	public static Long getEdadPorFechaOPorMesAnio(Fisica fisica) {
		Long edad = null;
		try {
			if(fisica.getFechaNacimiento() != null) {
				edad = DateUtils.getEdadRedondeadaEnAnios(fisica.getFechaNacimiento());
			} else {
				edad = DateUtils.getEdadSinDia(fisica.getMesRegistroNac(),fisica.getAnioRegistroNac());
			}
		} catch(Exception e) {
			LOG.error("No fue posible calcular la edad de la persona", e);
		}
		
		return edad;
	}
	/**
	 * Regresa si la fecha dada es habil dada una fecha y una lista de fechas inhabiles
	 * @param fechaActual
	 * @param fechasInhabiles
	 * @return
	 */
	public static boolean isHabil(Date fechaActual,List<Date> fechasInhabiles){
		Calendar calIna=Calendar.getInstance();
		Calendar calActual=Calendar.getInstance();
		
		calActual.setTime(fechaActual);
		
		int day=calActual.get(Calendar.DATE);
		int mont=calActual.get(Calendar.MONTH);
		int year=calActual.get(Calendar.YEAR);
		if(isEndOfWeek(fechaActual)){
			return false;
		}
		for (Date fechaIn:fechasInhabiles){
			calIna.setTime(fechaIn);
			
			if((calIna.get(Calendar.DATE)==day)&&(calIna.get(Calendar.MONTH)==mont)&&(calIna.get(Calendar.YEAR)==year)){
				return false;
			}
		}
		return true;
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
	
	public static long getEdadRedondeadaEnAnios(Date fechaNacimiento) {
	
		Calendar fechaHoy = new GregorianCalendar();
		Calendar fechaNacimientoC = new GregorianCalendar();
		fechaHoy.setTime(new Date());
		fechaNacimientoC.setTime(fechaNacimiento);
		
		int diaN = fechaNacimientoC.get(Calendar.DATE);
		int mesN = fechaNacimientoC.get(Calendar.MONTH);
		int anioN = fechaNacimientoC.get(Calendar.YEAR);
		
		int diaH =  fechaHoy.get(Calendar.DATE);
		int mesH = fechaHoy.get(Calendar.MONTH);
		int anioH = fechaHoy.get(Calendar.YEAR);
		
		long edad = anioH - anioN;
		
		if(mesH < mesN) {
			edad--;
		} else if(mesH == mesN && diaH < diaN) {
			edad --;
		}

		return edad;

	}
	
	/**
	 * 
	 * 
	 * Regresa si la fecha dada es habil dada una fecha y una lista de fechas inhabiles
	 * @param fechaActual
	 * @param fechasInhabiles
	 * @return
	 */
	public static boolean isEndOfWeek(Date fechaActual){
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(fechaActual);
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		
		if(dayOfWeek==Calendar.SUNDAY||dayOfWeek==Calendar.SATURDAY){
			return true;
		}
	
		return false;
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
	 * Regresa la prxima fecha habil apartir de una fecha y una lista de fechas inabiles
	 * Si esta fecha es habil regresa esta misma
	 */
	public static Date getProximaFechaHAbil(Date fechaActual,List<Date> fechasInhabiles){

		while(!isHabil(fechaActual, fechasInhabiles)){
			fechaActual=recorrerUnDia(fechaActual);
		}
		return fechaActual;
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
	 
	/**
	 * Cuenta la diferencia de Dias entre dos fechas
	 */
	public static int getDaysBetweenDates(Date fechaInicial,Date fechafinal){
		final long MILLSECS_POR_DIA = 24 * 60 * 60 * 1000;
		int dias=0;
		
		
		Calendar calTemp=Calendar.getInstance();
		Calendar calFinal=Calendar.getInstance();
		
		calTemp.setTime(fechaInicial);		
		calFinal.setTime(fechafinal);
		
		dias = (int) (( calTemp.getTimeInMillis() - calFinal.getTimeInMillis() )/MILLSECS_POR_DIA);
		
		return dias;
	}
	/**
	 * Recorre la fecha dada un dia
	 * @param fecha
	 * @return
	 */
	public static Date recorrerUnDia(Date fecha){
		Calendar cal=Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.DATE,cal.get(Calendar.DATE)+1);
		return cal.getTime();
	}
	
	/**
	 * reorre n dias la fecha dada  
	 * @param fecha
	 * @param nDias
	 * @return
	 */
	public static Date recorrerNDias(Date fecha,int nDias){
		for(int i=0;i<nDias;i++){
			fecha=recorrerUnDia(fecha);
		}
		
		return fecha;
	}
	/**
	 * Recorre la fecha dada un dia atras
	 * @param fecha
	 * @return
	 */
	public static Date recorrerUnDiaAtras(Date fecha){
		Calendar cal=Calendar.getInstance();
		cal.setTime(fecha);
		cal.roll(Calendar.DATE, false);
		return cal.getTime();
	}

	/**
	 * Recorre la fecha dada un dia atras
	 * @param fecha
	 * @return
	 */
	public static Date sumarDiasFecha(Date fecha,Integer dias){
		Calendar cal=Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DATE, dias);
		return cal.getTime();
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
	
	public static String dateFormat(Date fecha){
		SimpleDateFormat sDF=new SimpleDateFormat("dd/MM/yyyy");
		return sDF.format(fecha);
	}
	
	public static String dateFormat_yyyy_MM_dd(Date fecha){
		SimpleDateFormat sDF=new SimpleDateFormat("yyyy/MM/dd");
		return sDF.format(fecha);
	}
	
	public static int dayOfYear(Date fechaActual){
		Calendar cal=Calendar.getInstance();
		cal.setTime(fechaActual);					
		return Calendar.DAY_OF_YEAR;
	}
	
	public static int hora(Date fechaActual){
		Calendar cal=Calendar.getInstance();
		cal.setTime(fechaActual);					
		return Calendar.HOUR_OF_DAY;
	}
	
	public static int minutos(Date fechaActual){
		Calendar cal=Calendar.getInstance();
		cal.setTime(fechaActual);					
		return Calendar.MINUTE;
	}
	
	public static int segundos(Date fechaActual){
		Calendar cal=Calendar.getInstance();
		cal.setTime(fechaActual);					
		return Calendar.SECOND;
	}
	
	/**
	 * Regresa un string a partir de fecha en el formato indicado
	 * @param fecha
	 * @param formato
	 * @return
	 */
	public static String dateFormatCustom(Date fecha, String formato){
		SimpleDateFormat sDF=new SimpleDateFormat(formato,LOCALE_MX);
		return sDF.format(fecha);
	}
	
	public static String getNombreMes(int mes){
		String nombreMes ="";
			switch(mes){
				case 1:
					nombreMes ="Enero";
					break;
				case 2:
					nombreMes ="Febrero";
					break;
				case 3:
					nombreMes ="Marzo";
					break;
				case 4:
					nombreMes ="Abril";
					break;
				case 5:
					nombreMes ="Mayo";
					break;
				case 6:
					nombreMes ="Junio";
					break;
				case 7:
					nombreMes ="Julio";
					break;
				case 8:
					nombreMes ="Agosto";
					break;
				case 9:
					nombreMes ="Septiembre";
					break;
				case 10:
					nombreMes ="Octubre";
					break;
				case 11:
					nombreMes ="Noviembre";
					break;
				case 12:
					nombreMes ="Diciembre";
					break;	
			}
		
		return nombreMes;
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
	
   /**
    * 
    * @param fechaNacimiento
    * @param edadFinVigenica
    * @return
    */
   public static Date getFechaFinVigencia(Date fechaNacimiento, int edadFinVigenica) {
		Calendar c = Calendar.getInstance();
		c.setTime(fechaNacimiento);
		c.add(Calendar.YEAR, edadFinVigenica);
		
		return c.getTime();
	}
   
   
   public static boolean validarEdadPadres(Date fechaPadre, Date fechaAsegurado) {
		if(fechaPadre == null || fechaAsegurado == null){
			return false;
   		}
	   	Calendar calendarPadre = Calendar.getInstance();
		calendarPadre.setTime(fechaPadre);
		
		Calendar calendarAsegurado = Calendar.getInstance();
		calendarAsegurado.setTime(fechaAsegurado);
		
		int anioA = calendarAsegurado.get(Calendar.YEAR);  
		int mesA = calendarAsegurado.get(Calendar.MONTH) + 1;  
		int diaA = calendarAsegurado.get(Calendar.DAY_OF_MONTH);
		
		int anioP = calendarPadre.get(Calendar.YEAR);
		int mesP = calendarPadre.get(Calendar.MONTH) + 1; 
		int diaP = calendarPadre.get(Calendar.DAY_OF_MONTH);
		
		
		int anioR = anioA - anioP;
		if(anioR > 10){
			return true;
		}else if(anioR == 10){
			int mesR = mesA - mesP;
			if(mesR > 0){
				return true;
			}else if(mesR == 0){
				int diaR = diaA - diaP;
				if(diaR > 0){
					return true;
				} else{
					return false;
				}
			} else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	
	public static boolean validarEdadHijos(Date fechaHijo, Date fechaAsegurado,Boolean aseguradoConFecha) {
		if(fechaHijo == null || fechaAsegurado == null){
			return false;
		}
		
		Calendar calendarHijo = Calendar.getInstance();
		calendarHijo.setTime(fechaHijo);
		
		Calendar calendarAsegurado = Calendar.getInstance();
		calendarAsegurado.setTime(fechaAsegurado);
		
		
		int anioA = calendarAsegurado.get(Calendar.YEAR);  
		int mesA = calendarAsegurado.get(Calendar.MONTH) + 1;  
		int diaA = calendarAsegurado.get(Calendar.DAY_OF_MONTH);
		
		int anioH = calendarHijo.get(Calendar.YEAR);
		int mesH = calendarHijo.get(Calendar.MONTH) + 1; 
		int diaH = calendarHijo.get(Calendar.DAY_OF_MONTH);
		
		int anioR = anioH - anioA;
		if(anioR > 10){
			return true;
		}else if(anioR == 10){
			int mesR = mesH - mesA;
			if(mesR > 0 ){
				return true;
			}else if(mesR == 0){
				//verificamos si el asegurado tiene fehca denacimiento
				//si no tiene fecha se regresa false ya que el mes debe ser menor
				if(!aseguradoConFecha) {
					System.out.println("el asegurado llego si año de nacimiento");
					return false;
				}else {
					//si el asegurado tiene fecha se compararan los dias
					int diaR = diaH - diaA;
					System.out.println("la diferencia en dias es" + diaR);
					
					if(diaR >= 0){
						return true;
					}else{
						return false;
					}
				}
			}else{
				return false;
			}
		}else{
			return false;
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
	
	
	 public static Date getPrimerDiaSiguienteMes(Integer anioNacimiento, Integer mes) {
		 
		// ------------------------------------------------------------------
		// Si el año se envía con 2 posiciones, se ajusta a 4
		// ------------------------------------------------------------------
		if( (anioNacimiento != null) && (anioNacimiento < 1900) )
			anioNacimiento = DeltaUtils.formatAnioNac(anioNacimiento);
		 
		    Calendar cal = Calendar.getInstance();
	        cal.set(anioNacimiento, mes, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
	        
	        return cal.getTime();
	 }

	 public static Date getUltimoDiaSiguienteMes(Integer anioNacimiento, Integer mes) {
	    	
	    	// ------------------------------------------------------------------
			// Si el año se envía con 2 posiciones, se ajusta a 4
			// ------------------------------------------------------------------
			if( (anioNacimiento != null) && (anioNacimiento < 1900) )
				anioNacimiento = DeltaUtils.formatAnioNac(anioNacimiento);
	    	
	        Calendar cal = Calendar.getInstance();
	        cal.set(anioNacimiento, mes, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	        
	        return cal.getTime();
	 }
	 
	 
	
	
}
