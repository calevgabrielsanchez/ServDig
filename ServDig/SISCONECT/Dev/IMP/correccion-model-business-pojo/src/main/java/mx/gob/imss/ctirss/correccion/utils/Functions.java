package mx.gob.imss.ctirss.correccion.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import mx.gob.imss.ctirss.correccion.session.UserSession;

public abstract class Functions {
    
	public static String currencyMask(Double value){
		
		if(value == null) value= 0.0;
		
		String x = String.valueOf(value);
		
		
		
		x = x.replace(NumberFormat.getCurrencyInstance().getCurrency().getSymbol(),"");
		
		//NumberFormat formatter = NumberFormat.getCurrencyInstance();
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
		
		return formatter.format(Double.parseDouble(x)); 
		
		
	}
	public static String currencyMask(String value){
		
		if(value.equalsIgnoreCase("null")) value= "0.0";
		
		//NumberFormat formatter = NumberFormat.getCurrencyInstance(); Para identificar cualquier currency (Server Configuration)
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
		
		//value = value.replace(NumberFormat.getCurrencyInstance().getCurrency().getSymbol(),"");
		
		value = value.replace("$","");
		
		return formatter.format(Double.parseDouble(value));
	}
	
	
    //genera una fecha apartir de una cadena
    public static Date stringToDate(String fecha) {
    	if(fecha.equals("")){
    		return null;
    	}
        fecha = fecha.replaceAll("/","-");
        String[] datos = fecha.split("-");        
        if(datos.length>0)
        {
            Calendar cal = Calendar.getInstance();
            cal.set(cal.DAY_OF_MONTH, new Integer(datos[0]).intValue());
            cal.set(cal.MONTH, new Integer(datos[1]).intValue() - 1);
            cal.set(cal.YEAR, new Integer(datos[2]).intValue());
            Date retorno = cal.getTime();
            return retorno;
        }
        else
        	return new Date();
    }

    //genera una fecha apartir de una cadena con formato yyyy/mm/dd
    public static Date stringToDate2(String fecha) {
        fecha = fecha.replaceAll("/","-");
        String[] datos = fecha.split("-");        
        if(datos.length>0)
        {
            Calendar cal = Calendar.getInstance();
            cal.set(cal.DAY_OF_MONTH, new Integer(datos[2]).intValue());
            cal.set(cal.MONTH, new Integer(datos[1]).intValue() - 1);
            cal.set(cal.YEAR, new Integer(datos[0]).intValue());
            Date retorno = cal.getTime();
            return retorno;
        }
        else
        	return new Date();
    }
    
    //obtiene la cadena de la fecha para desplegar en pantalla
    public static String dateToString(Date fecha) {
    	if(fecha!=null)
    	{
            String fec = "";
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
            String dia = cal.get(cal.DAY_OF_MONTH)+"";
            String mes = (cal.get(cal.MONTH) + 1)+"";
            String anio = cal.get(cal.YEAR)+"";
            return  (dia.length()==1?"0"+dia:dia)+ "-" +  (mes.length()==1?"0"+mes:mes)+ "-" +anio; 
    	}
    	else
    		return "";
    }
    
    /**
     * Convierte un Date en String con formato yy/MM/dd
	 * @author Enrique Duran Jimenez
	 * @param fecha Date()
	 * @return yy/MM/dd
	 */
    public static String dateToString3(Date fecha) {
    	if(fecha!=null)
    	{
            String fec = "";
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
            String dia = cal.get(cal.DAY_OF_MONTH)+"";
            String mes = (cal.get(cal.MONTH) + 1)+"";
            String anio = cal.get(cal.YEAR)+"";
            return  (dia.length()==1?"0"+dia:dia)+ "/" +  (mes.length()==1?"0"+mes:mes)+ "/" +anio; 
    	}
    	else
    		return "";
    }
    
    public static String dateToString2(Date fecha) {
    	if(fecha==null){
    		return "";
    	}
        String fec = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        String dia = cal.get(cal.DAY_OF_MONTH)+"";
        String mes = (cal.get(cal.MONTH) + 1)+"";
        String anio = cal.get(cal.YEAR)+"";
        return  (dia.length()==1?"0"+dia:dia)+ "-" +  (mes.length()==1?"0"+mes:mes)+ "-" +anio; 
    }

    	/**
    	 * 
    	 * @param fecha Date()
    	 * @return yyMMdd
    	 */
    public static String dateToNumberAsString(Date fecha) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(fecha);
    }
    /**
     * 
     * @param fecha dd-MM-yyyy
     * @return yyMMdd
     */
    public static String dateToNumberAsString(String fecha) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        final SimpleDateFormat sdfStr = new SimpleDateFormat("dd-MM-yyyy");
        try {
			return sdf.format(sdfStr.parse(fecha));
		} catch (ParseException e) {
			// TODO Jhonmy
			e.printStackTrace();
		}
        return null;
    }
    
    /**
     * @author Enrique Duran Jimenez
     * @param fecha dd/MM/yyyy
     * @return yyMMdd
     */
    public static String dateToNumberAsStringD(String fecha) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        final SimpleDateFormat sdfStr = new SimpleDateFormat("dd/MM/yyyy");
        try {
			return sdf.format(sdfStr.parse(fecha));
		} catch (ParseException e) {
			// TODO Jhonmy
			e.printStackTrace();
		}
        return null;
    }
    //obtiene la cadena de la fecha para desplegar en pantalla
    public static String dateToStringSql(Date fecha) {
        String fec = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        String dia = cal.get(cal.DAY_OF_MONTH)+"";
        String mes = (cal.get(cal.MONTH) + 1)+"";
        String anio = cal.get(cal.YEAR)+"";
        fec = anio+ "-" +(mes.length()==1?"0"+mes:mes)+"-"+(dia.length()==1?"0"+dia:dia);
        return  fec; 
    }

    //agrega a una fecha dada dias en especifico
    public static Date addDayToDate(Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(cal.DAY_OF_MONTH, dias);
        Date retorno = cal.getTime();
        return retorno;
    }

    
    //agrega a una fecha dada dias en especifico
    public static Date addHabilesToDate(Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        
        while(dias>0)
        {
            cal.add(cal.DAY_OF_MONTH, 1);
            if(cal.get(cal.DAY_OF_WEEK)!=cal.SATURDAY&&cal.get(cal.DAY_OF_WEEK)!=cal.SUNDAY)
            	dias = dias -1;
        }
        Date retorno = cal.getTime();
        return retorno;
    }

    //agrega a una fecha dada dias en especifico
    public static int getYear(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        return cal.get(cal.YEAR);
    }

    public static String getDato(Date fecha, int dato) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        if(dato==cal.DAY_OF_MONTH)
        	return llenaCeros(cal.get(dato)+"", 2);
        if(dato==cal.MONTH)
        	return llenaCeros((cal.get(dato)+1)+"", 2);
        return llenaCeros(cal.get(dato)+"", 4);
    }

    
    public static Date mergeDate(Date fec, int daysToMove, boolean forward){
        String pattern = "d/MM/yyyy";
        Calendar result = Calendar.getInstance();
        result.setTime(fec);
        result.setTimeInMillis(result.getTimeInMillis());
        result.set(Calendar.HOUR_OF_DAY, 0);
        int counter = daysToMove;
        counter += 1;
        long dayInMillist = 86400000l; 
        while (counter > 1) {
            if (forward)
                result.setTimeInMillis(result.getTimeInMillis() + dayInMillist);
            else
                result.setTimeInMillis(result.getTimeInMillis() - dayInMillist);
            Date date = result.getTime();
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            if (result.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && result.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
                counter--;
            } 
        }
        Date retorno = result.getTime();
        return retorno;
    }
    
    public static String llenaCeros(String in,int posiciones)
    {
        if(in!=null)
        {
            while(in.length()<posiciones)
            {
                in = "0"+in;
            }
        }
        return in;
    }
    
    public static java.util.Date FormateaFecha(String fecha,String separator) {
        
        Integer vDia=0, vMes=0, vAno=0;
        StringTokenizer tokens = new StringTokenizer(fecha,separator); 
        
        try{
            vDia = new Integer(tokens.nextToken()); 
            vMes = new Integer(tokens.nextToken()); 
            vAno = new Integer(tokens.nextToken()); 
        }catch(Exception e){
            return null;
        }
        
        if(vAno.toString().length()==2) vAno=new Integer("20"+vAno.toString());
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(Calendar.YEAR, vAno);
            cal.set(Calendar.MONTH, vMes-1);
            cal.set(Calendar.DAY_OF_MONTH, vDia);
           // java.util.Date d = dateFormatter.parse(cal.getTimeInMillis());
     //   dateFormatter.parse(.toString());
            return new java.util.Date(cal.getTimeInMillis());
        } catch (Throwable e) {
            return null;
        }
    }
    
    /**
     * Metodo que calcula la diferencia en dias entre dos fechas 
     * regresa el numero de dias de diferencia
     * 
     * @param fechainicial , fecha posterior
     * @author Oscar Beltran Ortega
     * @return long dias de diferencia
     * @version 1.0.0
     */
	public static long calculaDifDiasFechas(Date fechaActual,Date fechaPosterior){
		final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al daa 
		
		long diferencia = ( fechaActual.getTime() - fechaPosterior.getTime() )/MILLSECS_PER_DAY; 
		
		return diferencia;
	}
	
	
	public static BigDecimal parserBigDecimal(Object ob){
		if(ob==null){
			return BigDecimal.ZERO;
		}else{
			return new BigDecimal(String.valueOf(ob));
		}
	}

	public static String bigDecimalToString(BigDecimal ob){
		if(ob==null){
			return "0";
		}else{
			return ob.toString();
		}
	}
	
	public static String getCveSubdelegacionOficial(UserSession user){
		
//		user.getCveCodigoDelegacion();
		String cveSubdelOficial = "";
		if(user.getCveCodigoDelegacion()!= null){
			if(user.getCveCodigoDelegacion().length()<2 ){
				cveSubdelOficial = "0"+ user.getCveCodigoDelegacion();
			}else{ 
				cveSubdelOficial = user.getCveCodigoDelegacion();
			}
		}
		if(user.getCveCodigoSubDelegacion() != null){
			if(user.getCveCodigoSubDelegacion().length() <2){
				cveSubdelOficial = cveSubdelOficial + "0" + user.getCveCodigoSubDelegacion();
			}else{
				cveSubdelOficial = cveSubdelOficial + user.getCveCodigoSubDelegacion();
			}
		}
		
		return cveSubdelOficial;
	}
	
}
