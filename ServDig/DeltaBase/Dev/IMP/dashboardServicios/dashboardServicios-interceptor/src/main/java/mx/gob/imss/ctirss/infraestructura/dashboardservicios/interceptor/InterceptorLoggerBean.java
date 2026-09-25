package mx.gob.imss.ctirss.infraestructura.dashboardservicios.interceptor;

import javax.interceptor.AroundInvoke;

import javax.interceptor.InvocationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

public class InterceptorLoggerBean {

	public static final String DATE_FORMAT = "yyyy-MM-dd H:mm:ss:SSS";

    @AroundInvoke
    public Object logCall(InvocationContext context) throws Exception{
    	
    	//CONFIGURA EL FORMATO DE FECHA
    	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    	
    	//FECHA INICIAL DE EJECUCION
        Date fechaInicial = new Date();
        long lFechaInicial = fechaInicial.getTime();
    	
        //OBTIENE LOS NOMBRES DE PAQUETE, CLASE Y METODO EJECUTADO
    	String sClase =  context.getTarget().getClass().getName();
    	String sPaquete = sClase; 
    	int pos = sPaquete.lastIndexOf('.'); 
    	if (pos >= 0) { 
    		sPaquete = sPaquete.substring(0, pos); 
    	}
    	String sClaseNombreSimple = context.getTarget().getClass().getSimpleName();
    	int iPosicionGuionBajo = sClaseNombreSimple.indexOf('_');
    	if (iPosicionGuionBajo >= 0) { 
    		sClaseNombreSimple = sClaseNombreSimple.substring(0, iPosicionGuionBajo); 
    	}
    	String sMetodo =  context.getMethod().getName();
    	
    	//OBTIENE LA REPRESENTACION CADENA DE LOS PARAMETROS DE ENTRADA AL SERVICIO
    	StringBuffer sParametrosLlamada = new StringBuffer();
    	Object parametros[] = context.getParameters();
    	int iNumParametro = 1;
    	for (Object parametro : parametros){
    		sParametrosLlamada.append("Parametro numero: " + iNumParametro++ );
    		sParametrosLlamada.append("\nTipo: " + parametro.getClass().getName() );
    		String sValorParametro = valorObjeto(parametro);
    		sParametrosLlamada.append("\nValor del parametro: \n" + sValorParametro + "\n\n");
    		
    	}
    	System.out.println("\n\n" + sParametrosLlamada + "\n\n");
        //EJECUTA LOG INICIAL DEL SERVICIO
        long iIdBitacora = iniciaBitacora(sPaquete + "." + sClaseNombreSimple, sMetodo, sdf.format(fechaInicial), sParametrosLlamada.toString());

        //EJECUTA EL METODO DEL EJB
        Object respuesta = context.proceed();
                
        //OBTIENE LA FECHA FINAL DE EJECUCION
        Date fechaFinal = new Date();
        long lFechaFinal = fechaFinal.getTime();
        long diff = lFechaFinal - lFechaInicial;
        long diffSeconds = diff / 1000;
                
        //OBTIENE LA REPRESENTACION CADENA DE LA RESPUESTA
		String sValorParametro = valorObjeto(respuesta);
	
        //EJECUTA LOG FINAL DE SERVICIO
        long iTiempoMilisegundos = finalizaBitacora(iIdBitacora, sdf.format(fechaInicial) , sdf.format(fechaFinal), sValorParametro);
        
        return respuesta;
    }
    
    public String valorObjeto (Object parametro){
    	String sValorParametro = "";
    	if (parametro != null){
    		
	        if (parametro instanceof List){
	        	List lista = (List)parametro;
	        	Iterator listaObjetos = lista.iterator();
	        	while (listaObjetos.hasNext()){
	        		Object objetoRespuesta = listaObjetos.next();
	        		sValorParametro = sValorParametro + valorObjeto(objetoRespuesta) + "\n\n ";
	        	}
	        }
	        else{
	    		try{
	    			sValorParametro = WebserviceTools.getStringXml(parametro);
	    		}
	    		catch(Exception e){
	    			//e.printStackTrace();
	    			sValorParametro = parametro.toString();
	    		}
	        }
    	}
		return sValorParametro;
    }
    
    public long iniciaBitacora(String sClaveServicio, String sClaveOperacion, String sFecha, String sParametrosLlamada){
    	long iIdBitacora = 0;
    	
    	String sParametros = "?cve-servicio=" + sClaveServicio +
    						"&cve-operacion=" + sClaveOperacion + 
    						"&fecha-inicio=" + URLEncoder.encode(sFecha) +
    						"&parametos-llamada=" + URLEncoder.encode(sParametrosLlamada);
    	String sUrl = "http://localhost:7001/Bitacora/resources/bitacora" + sParametros;
    	try {
			String sIdBitacora = httpGet(sUrl);
			iIdBitacora = Long.parseLong(sIdBitacora);
		} catch (IOException e) {
			System.out.println("LOGER MONITOR SERVICIOS. No fue posible logear la entrada/salida del servicio en la direccion: " + sUrl);
		}
    	return iIdBitacora;
    }

    public long finalizaBitacora(long iIdBitacora, String sFechaInicio, String sFechaFinal, String sRespuestaEjecucion){
    	long iTimpoMilisengundos = 0;
    	
    	String sParametros = "?id-bitacora=" + iIdBitacora +
    						"&fecha-inicio=" + URLEncoder.encode(sFechaInicio) + 
    						"&fecha-final=" + URLEncoder.encode(sFechaFinal) + 
    						"&respuesta-ejecucion=" + URLEncoder.encode(sRespuestaEjecucion);
    	String sUrl = "http://localhost:7001/Bitacora/resources/bitacora" + sParametros;
    	try {
			String sTiempoMilisegundos = httpGet(sUrl);
			iTimpoMilisengundos = Long.parseLong(sTiempoMilisegundos);
		} catch (IOException e) {
			System.out.println("LOGER MONITOR SERVICIOS. No fue posible logear la entrada/salida del servicio en la direccion: " + sUrl);
		}
    	return iTimpoMilisengundos;
    }
    
    
    public static String httpGet(String urlStr) throws IOException {
    	  URL url = new URL(urlStr);
    	  HttpURLConnection conn =
    	      (HttpURLConnection) url.openConnection();

    	  if (conn.getResponseCode() != 200) {
    	    throw new IOException(conn.getResponseMessage());
    	  }

    	  // Buffer the result into a string
    	  BufferedReader rd = new BufferedReader(
    	      new InputStreamReader(conn.getInputStream()));
    	  StringBuilder sb = new StringBuilder();
    	  String line;
    	  while ((line = rd.readLine()) != null) {
    	    sb.append(line);
    	  }
    	  rd.close();

    	  conn.disconnect();
    	  return sb.toString();
    	}    
}
