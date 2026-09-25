package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

import mx.gob.imss.ctirss.delta.cobranza.model.LoteCFDI;
import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;
import mx.gob.imss.ctirss.delta.cobranza.service.business.ComprobanteFiscalXMLPagoServiceBusiness;

public class Utilerias {
	
 private static final Logger LOG;
	
	static {
		LOG = LoggerFactory.getLogger(ComprobanteFiscalXMLPagoServiceBusiness.class);
	}
  
	/**
	 * @param ComprobanteDTO
	 * @param 
	 * @return 
	 * @throws 
	 */  	 
     public static String getPosfijoNombre(int cont){      
        String out = "";
        
        if(cont < 10){
            out = "000"+cont;
        }else if(cont < 100){
            out = "00"+cont;
        }else if(cont < 1000){
            out = "0" + cont;
        }else{
            out =""+cont;
        }
        return out;
     }
	    
	 /**
	  * @param ComprobanteDTO
	  * @param 
	  * @return 
	  * @throws 
	  */  
	  public static Date parseDateTime(String s) {
		 try{
			 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			 return formatter.parse(s);
		  	} catch (ParseException e) {
		  	  throw new RuntimeException(e);
	        }
	  }
	  
	/**
	  * @param ComprobanteDTO
	  * @param 
	  * @return 
	  * @throws 
	  */  
	  public static String printDateTime(Date dt) {
		  DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		  return (dt == null) ? null : formatter.format(dt);
	  }
	  
	 /** Metodo para asignar nombre al archivo XML
	  * @param 
	  * @param 
	  * @return String
	  * @throws 
	  */  
	  public static String nombreArchivoXML(){
		  StringBuilder prefijoNombreArchivo = new StringBuilder();
	      Calendar Calendario = Calendar.getInstance();
	        
	      String ano = Integer.toString(Calendario.get(Calendar.YEAR));
	      String mes = Integer.toString(Calendario.get(Calendar.MONTH) + 1);
	      String dia = Integer.toString(Calendario.get(Calendar.DATE));
	      String hora = Integer.toString(Calendario.get(Calendar.HOUR));
	      String minuto = Integer.toString(Calendario.get(Calendar.MINUTE));        
	      String segundo = Integer.toString(Calendario.get(Calendar.SECOND));
	        
	      prefijoNombreArchivo.append(Constantes.PREFIJO_NOMBRE_ARCHIVO_XML);
	      prefijoNombreArchivo.append(ano);
	      prefijoNombreArchivo.append(mes);
	      prefijoNombreArchivo.append(dia);
	      prefijoNombreArchivo.append(hora);
	      prefijoNombreArchivo.append(minuto);
	      prefijoNombreArchivo.append(segundo);
	        		  
		  return prefijoNombreArchivo.toString();
	  }
	  
	 /**
	  * @param bytesData
	  * @param String
	  * @return 
	  * @throws 
	  */  
	  public static String convierteBytesString(byte[] bytesData){
		  		 	      
	      String decodedDataUsingUTF8 = null;
	      try {
	            decodedDataUsingUTF8 = new String(bytesData, "UTF-8");  // Best way to decode using "UTF-8"
	            LOG.info("Text Decryted using UTF-8 : " + decodedDataUsingUTF8);
	          }catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	          }
	        
	       return  decodedDataUsingUTF8;
	  }
	  
	 /**
	  * @param ByteArrayInputStream
	  * @param 
	  * @return String
	  * @throws 
	  */  	 
	  public static String convertToString(ByteArrayInputStream is) {
		    int size = is.available();
		    char[] theChars = new char[size];
		    byte[] bytes    = new byte[size];

		    is.read(bytes, 0, size);
		    for (int i = 0; i < size;)
		        theChars[i] = (char)(bytes[i++]&0xff);

		    return new String(theChars);
	  }
	  
	  /**Metodo que regresa los folios cancelados en lotes
	  * @param ByteArrayInputStream
	  * @param 
	  * @return String
	  * @throws 
	  */
	  public static LoteCFDI[] convierteListEnArreglo(List<RegistroCFDI[]> registroCFDI){
		  LOG.info(" Tamano de la lista que contiene los arreglos de los registrosCFDi " + registroCFDI.size());
		  LoteCFDI[] loteRespuestaFolios = new LoteCFDI[registroCFDI.size()];
		  LoteCFDI loteCFDI = null;
		  int cont = 0;
		  for(RegistroCFDI[] regCFDI: registroCFDI){
			 loteCFDI= new LoteCFDI();
			  loteCFDI.setRegistros(regCFDI);
			  loteCFDI.setFechaLoteProceso(new Date());
			  loteRespuestaFolios[cont++] = loteCFDI;
		  }
		  LOG.info("Numero de lotes que creo ::: " + loteRespuestaFolios.length);		  
		  return loteRespuestaFolios;
	  }
	  
	  
	  /**Metodo que busca & en los RFC para reemplazarlo por la cadena &amp; 
	   * @param String
	   * @param 
	   * @return String
	   * @throws 
       */
	  public static String validaCarcateresEspeciales(String xml){
		  
		    Pattern pat = null;
	        Matcher mat = null;
	        
	        pat=Pattern.compile("&");
	        mat=pat.matcher(xml);
	        
	        if(mat.find()){
	            xml = xml.replace("&", "&amp;");            
	        }
	        
	        return xml;
	  }
	  	 	 	 
}
