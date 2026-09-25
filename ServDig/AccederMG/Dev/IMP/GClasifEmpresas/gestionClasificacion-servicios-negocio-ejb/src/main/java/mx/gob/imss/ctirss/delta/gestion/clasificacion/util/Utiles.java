/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: Utiles.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.util
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;

import org.apache.log4j.Logger;

public class Utiles {
	
	private static final Logger log = Logger.getLogger(Utiles.class);

	public Utiles() {}
	
	public static Clasificacion armaClasificacion(Long cveIdFraccion, BigDecimal primaSRT){
		Clasificacion clasificacion = new Clasificacion();
		
		Fraccion fraccion = new Fraccion();
		fraccion.setId(cveIdFraccion);
		fraccion.setPrimaSRT(primaSRT);
		
		clasificacion.setFraccion(fraccion);
		
		return clasificacion;
	}	

	public static Fraccion armaFraccion(long cveIdFraccion, BigDecimal primaSRT){
		Fraccion fraccion = new Fraccion();
		
		fraccion.setId(cveIdFraccion);
		fraccion.setPrimaSRT(primaSRT);
		
		return fraccion;
	}	
	
	public static AnalisisClasificacionEmpresas armaModeloAnalisis(ClasificacionDTO dto){
		AnalisisClasificacionEmpresas modelo = new AnalisisClasificacionEmpresas();
		
		modelo.setCveIdAnalisis(new Long(dto.getCveIdAnalisis()));
		//Se asigna la fecha
		modelo.setFechaAutorizacion(new Date());
	 	//Asignando el usuario
	 	modelo.setClaveUsuarioAsignado(dto.getUsuario().getCveIdUsuario().toString());
	 	
	 	modelo.setCveIdDelegacion(new Long(dto.getCveIdDelegacion()));
	 	modelo.setCveIdSubdelegacion(new Long(dto.getCveIdSubdelegacion()));
	 	
	 	if(!dto.getCveIdFraccionAct().isEmpty()){
	 		modelo.setClasificacionActual(
	 			armaClasificacion(new Long(dto.getCveIdFraccionAct()).longValue(), 
	 				new BigDecimal(dto.getPrimaSRTAct())));
	 	}
		 	
	 	if(!dto.getCveIdFraccionPro().isEmpty()){
	 		modelo.setClasificacionPropuesta(
	 			armaClasificacion(new Long(dto.getCveIdFraccionPro()).longValue(), 
	 				new BigDecimal(dto.getPrimaSRTPro())));
	 	}
	 	
	 	if(!dto.getCveIdFraccionAnt().isEmpty()){
	 		modelo.setClasificacionAnterior(
	 			armaClasificacion(new Long(dto.getCveIdFraccionAnt()).longValue(), 
	 				new BigDecimal(dto.getPrimaSRTAnt())));
	 	}
		
		return modelo;
	}

	/**
	 * Convierte la cadena de entrada a mayúsculas siempre y cuando no sea null
	 * 
	 * @param strInput
	 * @return String
	 */
	public static String toUpperCase(final String strInput) {
		String strOutput = "";
		if (null != strInput) {
			strOutput = strInput.toUpperCase();
		}
		return strOutput;
	}

	/**
	 * Devuelve la cadena del objeto enviado
	 * 
	 * @param object
	 * @return String
	 */
	public static String toString(final Object object) {
		String strOutput = null;
		if (null == object) {
			strOutput = "";
		} else {
			strOutput = object.toString();
		}
		return strOutput;
	}
	
	/**
	 * Convierte un arreglo de bytes a cadena
	 * 
	 * @param bytes
	 * @return String
	 */
	public static String convertArrayToString(final byte[] bytes) {
		String strOutput = null;
		if (null == bytes) {
			strOutput = "";
		} else {
			strOutput = new String(bytes).toUpperCase();
		}
		return strOutput;
	}
	
	/**
	 * Convierte una cadena a bigdecimal
	 * 
	 * @param param
	 * @return BigDecimal
	 */
	public static BigDecimal convertStringToBigDecimal(final String param) {
		BigDecimal bdOutput = null;
		if (null != param) {
			try {
				bdOutput = BigDecimal.valueOf(Long.parseLong(param));
			} catch (final NumberFormatException e) {
				log.error(e.getMessage(), e);
			}
		}
		return bdOutput;
	}
	
	/**
	 * Convierte una fecha de tipo Date a una cadena con formato dd/MM/yyyy
	 * 
	 * @param date
	 * @return strDate
	 */
	public static String parseDateToStringDDMMYYYY(final Date date) {
		String strDate = null;
		if (null == date) {
			strDate = "";
		} else {
			strDate = Constantes.FORMATO_FECHA_DD_MM_YYYY.format(date);
		}
		return strDate;
	}
	
	/**
	 * Evalúa si la Cadena recibida puede ser usada como numérico
	 * @param cadena
	 * @return
	 */
	public static final boolean isNumerico(final String cadena){
		try{
			int i=Integer.parseInt(cadena);
			log.debug(i);
			return true;
		}catch(NumberFormatException err){
			return false;
		}
	}
	
	/**
	 * valida el horario para la impresion de reportes
	 * @param cadena
	 * @return
	 */	
	public static String validaHorarioReportesNormativo() {
		
		Calendar calendarFechaActual = Calendar.getInstance();
		Calendar calendarHoraInicio = Calendar.getInstance();
		Calendar calendarHoraFin = Calendar.getInstance();

		ResourceBundle rb = ResourceBundle.getBundle("util");
		//Obtiene los horarios en que se permite el reporte de analisis
		int hrInicio = Integer.parseInt(rb.getString("reportes.hrInicio"));
		int minInicio = Integer.parseInt(rb.getString("reportes.minInicio"));
		int hrFin = Integer.parseInt(rb.getString("reportes.hrFin"));
		int minFin = Integer.parseInt(rb.getString("reportes.minFin"));

		calendarHoraInicio.set(Calendar.HOUR_OF_DAY, hrInicio);
		calendarHoraInicio.set(Calendar.MINUTE, minInicio);
		
		calendarHoraFin.set(Calendar.HOUR_OF_DAY, hrFin);
		calendarHoraFin.set(Calendar.MINUTE, minFin);

		System.out.println("calendarFechaActual: " + calendarFechaActual.getTime().toString());		
		System.out.println("calendarHoraInicio: " + calendarHoraInicio.getTime().toString());
		System.out.println("calendarHoraFin: " + calendarHoraFin.getTime().toString());

		if( !(calendarFechaActual.after(calendarHoraInicio) || calendarFechaActual.before(calendarHoraFin)) ) {			
			return "El horario para la generación del reporte nacional es desde las "
					+ getMinHr(hrInicio)
					+ ":"
					+ getMinHr(minInicio)
					+ " hrs a las "
					+ getMinHr(hrFin)
					+ ":"
					+ getMinHr(minFin)
					+ " hrs del día siguiente. Por favor intente en ese horario.";
		}
		
		return "true";		
	}
	
	public static String getMinHr(int min) {
		String res = min + "";
		if (min < 10)
			res = "0" + min;
		return res;
	}

	public static String validaPeriodo(String periodoDe, String periodoA, int limite) {
		Calendar calInicio = Calendar.getInstance();
		Calendar calFin = Calendar.getInstance();

		String[] fechaI = periodoDe.split("/");		
		calInicio.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fechaI[0]));
		calInicio.set(Calendar.MONTH, Integer.parseInt(fechaI[1]) - 1);
		calInicio.set(Calendar.YEAR, Integer.parseInt(fechaI[2]));
		
		String[] fechaF = periodoA.split("/");		
		calFin.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fechaF[0]));
		calFin.set(Calendar.MONTH, Integer.parseInt(fechaF[1]) - 1);
		calFin.set(Calendar.YEAR, Integer.parseInt(fechaF[2]));

		if( calFin.compareTo(calInicio) < 0 )
			return "La fecha final debe ser mayor o igual que la fecha de inicio";
			
		int dif = 0;
		int suma = 0;
		
		while(calFin.after(calInicio)){
			if(suma > 0)
				dif += 1;
			suma += 1;
			calInicio.set( Calendar.MONTH, (calInicio.get(Calendar.MONTH) + 1) );
		}
	
		if(dif > limite)
			return "El periodo de consulta no debe ser mayor a "+limite+" meses";
		else if (dif < limite)
			return "true";
		else{ // si es igual
			int diaInicio = Integer.parseInt(fechaI[0]);
			int diaFin = Integer.parseInt(fechaF[0]);
			if(diaFin >= diaInicio)
				return "El periodo de consulta no debe ser mayor a "+limite+" meses";
		}			

		return "true";
	}
	
	public static String validaLimiteDias(String strPeriodoInicio,
			String strPeriodoFin, int limiteDiasConcentrado) {

		Calendar calInicio = Calendar.getInstance();
		Calendar calFin = Calendar.getInstance();

		String[] fechaI = strPeriodoInicio.split("/");		
		calInicio.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fechaI[0]));
		calInicio.set(Calendar.MONTH, Integer.parseInt(fechaI[1]) - 1);
		calInicio.set(Calendar.YEAR, Integer.parseInt(fechaI[2]));
		
		String[] fechaF = strPeriodoFin.split("/");		
		calFin.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fechaF[0]));
		calFin.set(Calendar.MONTH, Integer.parseInt(fechaF[1]) - 1);
		calFin.set(Calendar.YEAR, Integer.parseInt(fechaF[2]));

		if( calFin.compareTo(calInicio) < 0 )
			return "La fecha Final debe ser mayor ó igual que la fecha de Inicio";
			
		// conseguir la representacion de la fecha en milisegundos
        long milis1 = calInicio.getTimeInMillis();
        long milis2 = calFin.getTimeInMillis();
        // calcular la diferencia en milisengundos
        long diff = milis2 - milis1;
        long diffDays = diff / (24 * 60 * 60 * 1000);
 
        System.out.println("diffDays: " + diffDays);
        
		if(diffDays > limiteDiasConcentrado)
			return "El periodo de consulta no debe ser mayor a "+limiteDiasConcentrado+" días";		

		return "true";
		
	}
}