package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageResolver;


public class Utiles {

	public Utiles() {
	}

	public static String validaPeriodo(String periodoDe, String periodoA, int limite) {
		Calendar calInicio = Calendar.getInstance();
		Calendar calFin = Calendar.getInstance();

		String[] fechaI = periodoDe.split("-");		
		calInicio.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fechaI[2]));
		calInicio.set(Calendar.MONTH, Integer.parseInt(fechaI[1]) - 1);
		calInicio.set(Calendar.YEAR, Integer.parseInt(fechaI[0]));
		
		String[] fechaF = periodoA.split("-");		
		calFin.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fechaF[2]));
		calFin.set(Calendar.MONTH, Integer.parseInt(fechaF[1]) - 1);
		calFin.set(Calendar.YEAR, Integer.parseInt(fechaF[0]));

		if( calFin.compareTo(calInicio) < 0 )
			return "errorFechaFinalMayor";
			
		int dif = 0;
		int suma = 0;
		
		while(calFin.after(calInicio)){
			if(suma > 0)
				dif += 1;
			suma += 1;
			calInicio.set( Calendar.MONTH, calInicio.get((Calendar.MONTH)) + 1 );
		}
	
		if(dif > limite)
			return "errorPeriodoMayor";
		else if (dif < limite)
			return "true";
		else{ // si es igual
			int diaInicio = Integer.parseInt(fechaI[0]);
			int diaFin = Integer.parseInt(fechaF[0]);
			if(diaFin >= diaInicio)
				return "errorPeriodoMayor";
		}			

		return "true";
	}
	
	/**
	 * Convierte una cadena con formato dd/MM/yyyy a una fecha con formato
	 * yyyy-MM-dd
	 * @param strFecha
	 * @return date
	 */
	public static Date parseStringToDate(final String strDate) {
		Date date = null;
		Constantes.DATE_FORMAT_DD_MM_YYYY.setLenient(Boolean.FALSE);
		try {
			if (strDate.length() != 10) {
				date = null;
			} else {
				date = Constantes.DATE_FORMAT_YYYY_MM_DD
						.parse(Constantes.DATE_FORMAT_YYYY_MM_DD
								.format(Constantes.DATE_FORMAT_DD_MM_YYYY
										.parse(strDate)));
			}
		} catch (final ParseException e) {
			date = null;
		} finally {
			Constantes.DATE_FORMAT_DD_MM_YYYY.setLenient(Boolean.TRUE);
		}
		return date;
	}

	public static String getDateHHMM() {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return hourdateFormat.format(date);
	}
	

	public static final String getFechaActual(){
		String fechaActual="";
		Calendar c2 = Calendar.getInstance();
		switch(c2.get(Calendar.DAY_OF_WEEK)){
			case 2: fechaActual+="LUNES";break;
			case 3: fechaActual+="MARTES";break;
			case 4: fechaActual+="MIERCOLES";break;
			case 5: fechaActual+="JUEVES";break;
			case 6: fechaActual+="VIERNES";break;
			case 7: fechaActual+="SABADO";break;
			default: fechaActual+="DOMINGO";
		}
		fechaActual+=" " + c2.get(Calendar.DATE) + " DE " + "";
		switch(c2.get(Calendar.MONTH)){
			case 0: fechaActual+="ENERO";break;
			case 1: fechaActual+="FEBRERO";break;
			case 2: fechaActual+="MARZO";break;
			case 3: fechaActual+="ABRIL";break;
			case 4: fechaActual+="MAYO";break;
			case 5: fechaActual+="JUNIO";break;
			case 6: fechaActual+="JULIO";break;
			case 7: fechaActual+="AGOSTO";break;
			case 8: fechaActual+="SEPTIEMBRE";break;
			case 9: fechaActual+="OCTUBRE";break;
			case 10: fechaActual+="NOVIEMBRE";break;
			default: fechaActual+="DICIEMBRE";
		}
		return fechaActual+=" DEL " + c2.get(Calendar.YEAR);
	}
	
	public static final Clasificacion getClasificacion(final AnalisisClasificacionEmpresas analisisClasEmp) {
		final Clasificacion clas = new Clasificacion();
		final Division division = new Division();
		division.setId(Long.valueOf(analisisClasEmp.getClasificacionPropuesta().getFraccion().getGrupo().getDivision().getNumDivision()));
		final Grupo grupo = new Grupo();
		grupo.setId(Long.valueOf(analisisClasEmp.getClasificacionPropuesta().getFraccion().getGrupo().getNumGrupo()));
		grupo.setDivision(division);
		final Fraccion fraccion = new Fraccion();
		fraccion.setId(Long.valueOf(analisisClasEmp.getClasificacionPropuesta().getFraccion().getNumFraccion()));
		fraccion.setGrupo(grupo);
		clas.setFraccion(fraccion);
		if(analisisClasEmp.getClasificacionPropuesta().getPrimaSugerida() != null) {
			clas.setPrimaSugerida(analisisClasEmp.getClasificacionPropuesta().getPrimaSugerida());
		}
		return clas;
	}
	
	/**
	 * Construye el mensaje a presentar en pantalla
	 * @param isError si es TRUE  -> Indica que el mensaje a presentar es un error
	 *                si es FALSE -> Indica que el mensaje a presentar es informativo
	 * @param code
	 * @return messageResolver
	 */	
	public static final MessageResolver construirMensaje(final boolean isError,
			final String code) {
		final MessageBuilder messageBuilder = new MessageBuilder().code(code);
		if (isError) {
			messageBuilder.error();
		} else {
			messageBuilder.info();
		}
		return messageBuilder.build();
	}
	
	public static String validaFechaFinMayor(String periodoDe, String periodoA) {
		Calendar calInicio = Calendar.getInstance();
		Calendar calFin = Calendar.getInstance();
		System.out.println("----- Las fechas que recibi son: periodoDe: " + periodoDe + ", periodoA: " + periodoA);
		String[] fechaI = periodoDe.split("-");		
		calInicio.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fechaI[2]));
		calInicio.set(Calendar.MONTH, Integer.parseInt(fechaI[1]) - 1);
		calInicio.set(Calendar.YEAR, Integer.parseInt(fechaI[0]));
		
		String[] fechaF = periodoA.split("-");		
		calFin.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fechaF[2]));
		calFin.set(Calendar.MONTH, Integer.parseInt(fechaF[1]) - 1);
		calFin.set(Calendar.YEAR, Integer.parseInt(fechaF[0]));

		System.out.println(calInicio.compareTo(calFin));
		if(calInicio.compareTo(calFin) <= 0 ){
			System.out.println("----- La fecha final es mayor");
			return "fechaFinalMayor";
		}else{
			System.out.println("----- La fecha final NO es mayor");
			return "true";			
		}
	}
	
	
	public static String tipoAcuse(String idAcuse){
		
		String acuse;
		
		switch (Integer.parseInt(idAcuse)) {
		
		case 1:acuse = "MacInscripcion";
			
			break;
			
		case 2:acuse = "MacSubInscripcion";
		
			break;
			
		case 3:acuse = "MacDelegacional";
		
		break;

		default: acuse = "MacSubdelegacional";
			break;
		}
		
		
		return acuse;

	}
	
	
	
}