package mx.gob.imss.ctirss.delta.framework.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class Utilerias {

    /**
     * Soporta null. Actualmente los valores null, 0 y -1 se consideran blanks.
     * @param idEntity
     * @return
     */
    public static Boolean isNotBlank(final Number idEntity) {
        return !isBlank(idEntity);
    }

    /**
     * Soporta null. Actualmente los valores null, 0 y -1 se consideran blanks.
     * @param idEntity
     * @return
     */
    public static Boolean isBlank(final Number idEntity) {
        Boolean isBlank = null;
        if (idEntity == null) {
            isBlank = Boolean.TRUE;
        } else {
            isBlank = idEntity.equals(0) || idEntity.equals(-1L) || idEntity.equals(-1);
        }
        return isBlank;
    }
    
    public static Long convertir(final Integer intValue) {
        Long longValue = null;
        if(intValue != null) {
            longValue = intValue.longValue();
        }
        return longValue;
    }

    public static Integer convertir(final Long longValue) {
        Integer intValue = null;
        if(longValue != null) {
            intValue = longValue.intValue();
        }
        return intValue;
    }
    
    public static Boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static Boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    // TODO CGM quitar main y crear test class...
    public static void main(String[] args) {
        Number it = null;
        System.out.println("isBlank: " + isBlank(it));
        it = 0;
        System.out.println("isBlank: " + isBlank(it));
        it = -1l;
        System.out.println("isBlank: " + isBlank(it));
        it = -1;
        System.out.println("isBlank: " + isBlank(it));
        it = -2L;
        System.out.println("isBlank: " + isBlank(it));
    }
    
    /**
     * Obtene en un String la fecha y hora actual en el formato siguiente
     * EJ: 10 DE JULIO DEL 2012 18:23 HRS
     * @return String de fecha y hora actual
     */
    public static String stringFechaHoraActual() {
    	StringBuffer fechaString = new StringBuffer();
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.setTime(new Date());
		
		fechaString.append(String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH)))
					.append(" de ")
					.append(convertirMesInt(fechaActual.get(Calendar.MONTH)))
					.append(" del ")
					.append(String.valueOf(fechaActual.get(Calendar.YEAR)))
					.append(String.valueOf(fechaActual.get(Calendar.HOUR_OF_DAY)))
					.append(":")
					.append(String.valueOf(fechaActual.get(Calendar.MINUTE)))
					.append(" HRS");
    	
    	return fechaString.toString();
    }
    
    public static String convertirMesInt (int mes) {
    	String cadena = "";
    	
    	switch (mes) {
		case 0:
			cadena = "Enero";
			break;
		case 1:
			cadena = "Febrero";
			break;
		case 2:
			cadena = "Marzo";
			break;
		case 3:
			cadena = "Abril";
			break;
		case 4:
			cadena = "Mayo";
			break;
		case 5:
			cadena = "Junio";
			break;
		case 6:
			cadena = "Julio";
			break;
		case 7:
			cadena = "Agosto";
			break;
		case 8:
			cadena = "Septiembre";
			break;
		case 9:
			cadena = "Octubre";
			break;
		case 10:
			cadena = "Noviembre";
			break;
		case 11:
			cadena = "Diciembre";
			break;
		}
    	    	
    	return cadena;
    }

    /**
     * Calcula el porcentaje de comparaci—n entre la cadena str1 ( base) y str2 ( comparacion)
     * a traves de calcular la distancia de Levenshtein, y sacar el porcentaje de dicha 
     * comparacion.
     * 
     * @param str1 : Cadena base para la comparacion
     * @param str2 : Cadena a comparar, es a la cual se determina el 
     *  porcentaje de comparacion con la base.
     * @return Porcentaje de str2 respecto a str1 
     */
    public float porcentajeComparacionCadenas(String str1 , String str2){
    	float porcentaje = 0.0f;
    	
    	float d = LevenshteinDistance.computeLevenshteinDistance(str1, str2);
    	
    	float l = str2.length();
    	
    	porcentaje = 100 - (( d / l) * 100);
    	
    	return porcentaje;
    }
           
    /** 
     * Método que convierte el total en numero a su correspondiente
     * en letra, mas la leyenda de pesos y 
     * 
     * @param total
     * @return
     */
    public static String totalConLetras(BigDecimal total) {
    
    	String sTotal= "";
    	if(total != null) {
	    	String[] pos = total.toString().split("\\.");
	    	
	    	String s1 = numeroEnTexto(Long.valueOf(pos[0]));
	    	s1 = s1.replace(s1.substring(0), s1.substring(0).toUpperCase());
	    	
	    	String s2 ="00";
			if (pos.length > 1) {
				s2 = pos[1];
				s2 = (s2.length() == 1) ? s2 + "0" : s2;
			}
	    	   	
	    	sTotal = s1 + " PESOS "+ s2 + "/100 M.N.";
	    }
    	return sTotal;
    }
    
    /** Método que transforma un numero a su valor en texto.
     * con rango 1 - 99,999,999,999
     * 
     * @param iNumero
     * @return
     */
    public static String numeroEnTexto(Long iNumero) {

		String sTexto = "";
        
		long iUnidad = iNumero%10;
        iNumero = iNumero/10;
        sTexto = unidadEnTexto((int) iUnidad);
        
        long iDecena = iNumero%10;
        iNumero = iNumero/10;
        sTexto = ValidaDecenas(sTexto, iUnidad, iDecena, "");
        sTexto = sTexto.replaceAll("uno", "un");
        
        long iCentena = iNumero%10;
        iNumero = iNumero/10;
        sTexto = ValidaCentenas(sTexto, iUnidad, iDecena, iCentena, "");
                    
        //Guarda el valor hasta los cientos
        String sTxtCen = sTexto;
        
        //-- Seccion de miles
        long iMil = iNumero%10;
        iNumero = iNumero/10;
        if(iMil == 1)
        		sTexto = "mil " + sTexto;
        else if (iMil > 1)
        		sTexto = unidadEnTexto((int) iMil) + " mil " + sTexto;
        
        
        long iDecMil = iNumero%10;
        iNumero = iNumero/10;
        if(iDecMil > 0) {
        	sTexto = ValidaDecenas(sTexto, iMil, iDecMil, " mil");
        	sTexto = sTexto.replaceAll("uno", "un");
        	sTexto =  sTexto + " " +sTxtCen;
		}
        
        long iCienMil = iNumero%10;
        iNumero = iNumero/10;
        if(iCienMil > 0) {
        	if(iDecMil ==0 && iMil==1)
        		sTexto = "un " + sTexto;
        	sTexto =  ValidaCentenas(sTexto, iMil, iDecMil, iCienMil, "mil");
        }
        
        //Guarda el valor hasta los miles
        String sTxtMiles = sTexto;
        
        //-- Seccion de milloness
        long iMillon = iNumero%10;
        iNumero = iNumero/10;
        if(iMillon>0) {
        	sTexto = (iMillon == 1) ? "un millon " + sTexto
        			: unidadEnTexto((int) iMillon)+" millones "+sTexto;
        }
        
        long iDecMillones = iNumero%10;
        iNumero = iNumero/10;
        if(iDecMillones > 0) {
        	sTexto = ValidaDecenas(sTexto, iMillon, iDecMillones, " millones");
        	sTexto = sTexto.replaceAll("uno", "un");
        	sTexto =  sTexto+" "+sTxtMiles;
		}
        
        long iCienMillones = iNumero%10;
        iNumero = iNumero/10;
        if(iCienMillones > 0) {
        	if(iDecMil ==0 && iMil==1)
        		sTexto = "un " + sTexto;
        	sTexto =  ValidaCentenas(sTexto, iMillon, iDecMillones, iCienMillones, "millones");
        }
        
        // Guarda el valor hasta los mil millones
        String sTxtMillones = sTexto;
        
        //-- Seccion de mil millones
        long iMilMi = iNumero%10;
        iNumero = iNumero/10;
        if (iMilMi > 0) {
            if(iMilMi == 1)
        		sTexto = (iMillon>0) ? "mil " + sTexto.replaceAll("millon ", "millones ")
        				: "mil millones "+sTexto;
            else
            	sTexto = (iMillon>0) ? unidadEnTexto((int) iMilMi) + " mil " + sTexto
        				:unidadEnTexto((int) iMilMi)+ " mil millones "+sTexto;
        }
                                
        long iDecMilMi = iNumero%10;
        if(iDecMilMi > 0) {
        	if(iMilMi == 0)
        	 sTexto = (iCienMillones >0) ? ValidaDecenas(sTexto, iMilMi, iDecMilMi, " mil ") +sTxtMillones
        			 : ValidaDecenas(sTexto, iMilMi, iDecMilMi, " mil millones") +sTxtMillones;
        	else
        		sTexto =(iCienMillones >0)? ValidaDecenas(sTexto, iMilMi, iDecMilMi, " mil ") +sTxtMillones
        				:ValidaDecenas(sTexto, iMilMi, iDecMilMi, " mil millones ") ;
        	sTexto = sTexto.replaceAll("uno", "un");
        }
        
        
        return sTexto;
    }
    
    /** Metodo para validar cientos, centenas de miles y de millones
     * 
     * @param sTexto
     * @param iUnidad
     * @param iDecena
     * @param iCentena
     * @param numero
     * @return
     */
    private static String ValidaCentenas(String sTexto, long iUnidad,
			long iDecena, long iCentena, String numero) {
		if ((iCentena!=1) && (iCentena!=5) && (iCentena!=7) && (iCentena!=9) && (iCentena!=0)) {
			sTexto = (iDecena==0 && iUnidad==0) ? unidadEnTexto((int) iCentena)+"cientos "+numero+" "+sTexto 
					: unidadEnTexto((int) iCentena)+"cientos "+sTexto;
		}
		else if ((iCentena==1) || (iCentena==5) || (iCentena==7) || (iCentena==9) ) {
				sTexto = (iDecena==0 && iUnidad==0)? (iCentena==1) ? "cien "+numero+" "+sTexto
						: centenaEnTexto((int) iCentena)+" "+numero+" "+sTexto 
						: centenaEnTexto((int) iCentena)+" "+sTexto;
		}
		return sTexto;
	}
	
	/** Metodo para validar decenas, decenas de miles y de millones
	 * 
	 * @param sTexto
	 * @param iUnidad
	 * @param iDecena
	 * @param numero
	 * @return
	 */
    private static String ValidaDecenas(String sTexto, long iUnidad, 
			long iDecena, String numero) {
		if ((iUnidad==0) && (iDecena>0))
				sTexto = decenaEnTexto((int) iDecena);
		else if (iDecena==1) 
				sTexto = decenas(10+((int)iUnidad)); 
		else if (iDecena > 1) {
				sTexto = decenaEnTexto((int)iDecena) + " y " + unidadEnTexto((int)iUnidad);
				sTexto = sTexto.replaceAll("veinte y ", "veinti");
		}
		return sTexto + numero;
	}
	
	/** Unidad
     *  Método que dado un número me lo devuelve en texto
     *  
     * @param iDecena
     * @return
     */
    public static String unidadEnTexto(int iNumero){
           switch(iNumero){
                    case 1: return "uno";
                    case 2: return "dos";
                    case 3: return "tres";
                    case 4: return "cuatro";
                    case 5: return "cinco";
                    case 6: return "seis";
                    case 7: return "siete";
                    case 8: return "ocho";
                    case 9: return "nueve";
                    //case 0: return "cero";
                    default: return "";
           }           
    }
   
    /** Decenas
     *  Método que devuelve las decenas
     *  
     * @param iDecena
     * @return
     */
    public static String decenaEnTexto(int iDecena){
    		switch (iDecena){
                    case 1: return "diez";
                    case 2: return "veinte";
                    case 3: return "treinta";
                    case 4: return "cuarenta";
                    case 5: return "cincuenta";
                    case 6: return "sesenta";
                    case 7: return "setenta";
                    case 8: return "ochenta";
                    case 9: return "noventa";              
                    default: return "";
            }
    }
   
   
    /** Decenas
     *  Las decenas del 10 no se pueden crear con una decena y la unidad. Cada uno es deferente.
     *  
     * @param iDecena
     * @return
     */
    public static String decenas(int iDecena){
           switch (iDecena){
                case 11: return "once";
                case 12: return "doce";
                case 13: return "trece";
                case 14: return "catorce";
                case 15: return "quince";
                case 16: return "dieciseis";
                case 17: return "diecisiete";
                case 18: return "dieciocho";
                case 19: return "diecinueve";            
                default: return "";
           }
    }
   
    /** Centenas
     *  Hay un factor común en dos,tres,cuatro,seis,ocho,
     *  Ciento, novecientos y quinientos no tinen nada en común.
     * @param iCentena
     * @return
     */
    public static String centenaEnTexto(int iCentena){
           switch (iCentena){
                case 1: return "ciento";
                case 5: return "quinientos";
                case 7: return "setecientos";
                case 9: return "novecientos";                          
                default: return "";
           }
    }
	
    
}
