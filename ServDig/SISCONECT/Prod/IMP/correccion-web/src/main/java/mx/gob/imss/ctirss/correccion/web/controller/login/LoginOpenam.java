//package mx.gob.imss.ctirss.correccion.web.controller.login;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.util.Map;
//import java.util.Properties;
//import java.util.TreeMap;
//
//import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.HttpStatus;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.log4j.Logger;
//
//public class LoginOpenam {
//	private final static Logger logger = Logger.getLogger(LoginOpenam.class);
//	private Properties propiedades = new Properties();
//	private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//	private HttpClient client = new HttpClient();
//	private GetMethod method;
//	
//	
//	public String obtenerToken(SegUsuario segUsuario){
//		String token = null;
//		
//		try {
//			propiedades.load(classLoader.getResourceAsStream("openam.properties"));
//			String obtenerToken = propiedades.getProperty("openam.url.token");
//			
//			String param1 = propiedades.getProperty("user.openam");
//			String param2 = propiedades.getProperty("pwd.openam"); 
//			
//			String usuario = segUsuario.getNomUsuarioSistema();
//			String password = segUsuario.getRefPassword();
//			
//			String rutaToken = obtenerToken + param1 + usuario + "&" + param2 + password;
//			
//			method = new GetMethod(rutaToken);
//	        
//	        // Send GET request
//	        int statusCode = client.executeMethod(method);
//	        
//	        if (statusCode != HttpStatus.SC_OK) {
//	          System.err.println("Method failed: " + method.getStatusLine());
//	        }
//	        // Read the response body.
//	        byte[] responseBody = method.getResponseBody();
//	
//	        // Deal with the response.
//	        // Use caution: ensure correct character encoding and is not binary data
//	        token = new String(responseBody);
//	        if(!token.contains("token.id=")){
//	        	logger.error(token);
//	        	token = null;
//	        }
//	        System.out.println(token);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return token;
//	}
//	
//	
//	
//	public Map<String,String> obtenerDatosOpenAM(String token){
//        token = token.replace("token.id=", "");
//        String obtenerDatos = propiedades.getProperty("openam.url.datos");
//        Map<String,String> mapDatosOpenAM = new TreeMap<String, String>();
//        
//        // Process the response 
//	    String url = obtenerDatos + token;
//	    GetMethod methods = new GetMethod(url);
//	    int statusCodes;
//		try {
//			statusCodes = client.executeMethod(methods);
//        
//		    if (statusCodes != org.apache.commons.httpclient.HttpStatus.SC_OK) {
//		    	 System.err.println("Method failed: " + method.getStatusLine());
//		    }
//		     // Read the response body.
//		    byte[] responseBodys = methods.getResponseBody();
//	
//		     // Deal with the response.
//		     // Use caution: ensure correct character encoding and is not binary data
//		    
//		    InputStream bais = new ByteArrayInputStream(responseBodys);
//			Reader reader = new InputStreamReader(bais);
//			BufferedReader br = new BufferedReader(reader);
//			String s = "";
//			
//			String clave = "";
//			String valor = "";
//			while((s=br.readLine())!=null){
//				if(s.contains(".name")){
//					clave = s.split("=")[1];
//					mapDatosOpenAM.put(clave, "");
//				}
//				if(s.contains(".value")){
//					valor = s.split("=")[1];
//					mapDatosOpenAM.put(clave, valor.toUpperCase());
//				}
//			}
//
//		} catch (HttpException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return mapDatosOpenAM;
//	}
//}
//
