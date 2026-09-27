package mx.gob.imss.cit.clienteswebservices.modalidad40.rest;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.ResourceBundle;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.CalculoPagosDTO;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.CalculoPagosRequest;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.CalculoPagosResponse;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaConsultaDTO;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaConsultaResponse;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaDTO;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaRequest;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.GeneracionMultilineaResponse;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.ModalidadResponseException;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.ValidaRetroactividadRequest;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.ValidaRetroactividadResponse;
import mx.gob.imss.cit.clienteswebservices.modalidad40.util.ResourceBundleConfiguration;
import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.ValidaRetroactividadDTO;

public class Modalidad40RestLocalImpl implements Modalidad40RestLocal{
	
	
	private final Logger log = LoggerFactory.getLogger(Modalidad40RestLocalImpl.class);
	private final String URL_VALIDA_RETROACTIVIDAD;
	private final String URL_CALCULO_PAGOS;
	private final String URL_GENERACION_MULTILINEA;
	
	
	public Modalidad40RestLocalImpl() {
		ResourceBundle resourceBundle = ResourceBundleConfiguration.getResourceServicioExterno();
		URL_VALIDA_RETROACTIVIDAD = resourceBundle.getString("servicios.externos.rest.url.valida.retroactividad");
		URL_CALCULO_PAGOS = resourceBundle.getString("servicios.externos.rest.url.calculo.pagos");
		URL_GENERACION_MULTILINEA = resourceBundle.getString("servicios.externos.rest.url.generacion.multilinea");
	}

	@Override
	public ValidaRetroactividadResponse validaRetroactividad(ValidaRetroactividadRequest consulta) throws ModalidadResponseException{
		
		
		ValidaRetroactividadResponse vrr = new ValidaRetroactividadResponse();
		ValidaRetroactividadDTO obj = new ValidaRetroactividadDTO();
		Gson gson = new Gson();
		try {		
			
			
			String json = gson.toJson(consulta);
			log.info("la url a consumir es " + URL_VALIDA_RETROACTIVIDAD );
			log.info("el objet transformado es: JSON [{}]", json);

			disableSSLCertificateChecking();
	    	
	       

	       
	            URL url = new URL(URL_VALIDA_RETROACTIVIDAD);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            
	            // Configurar método y propiedades
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type", "application/json; utf-8");
	            conn.setRequestProperty("Accept", "application/json");
	            conn.setDoOutput(true); // Habilitar envío de cuerpo (payload)

	            // Enviar datos
	            OutputStream os = conn.getOutputStream();
	            os.write(json.getBytes("utf-8"));
	            os.flush();
	            os.close();

	            // Leer código de respuesta
	            int responseCode = conn.getResponseCode();
	            System.out.println("Código de respuesta: " + responseCode);
	          
	            // Leer respuesta del servidor
	            BufferedReader br;
	            if (responseCode >= 200 && responseCode < 300) {
	                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
	            } else {
	                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
	            }

	           
	            
	            StringBuilder response = new StringBuilder();
	            String responseLine;
	            while ((responseLine = br.readLine()) != null) {
	                response.append(responseLine.trim());
	            }
	            br.close();

	            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
	            obj = gson.fromJson(jsonObject.toString(),ValidaRetroactividadDTO.class);
	            
	            
	          //{ "status": "ERROR", "errorCode": "MISSING_PARAMS", "errorMessage": null, "requestId": "20141014181739_11625805172", "downstreamModuleErrorCode": null, "object": [ "activity_code", "activity_name", "points", "frequency", "strategy", "vsa_app_access_token" ]}
	            log.info(""+jsonObject.get("idCalculo"));
	            System.out.println(jsonObject.get("idCalculo"));
	            System.out.println("Respuesta del servidor: " + response.toString());
	            conn.disconnect();
	            
	            
	            
	            vrr.setCodigo("200");
	            vrr.setDescripcion("NSS encontrado");
	            vrr.setVrDto(obj);
	            
	        } catch (Exception e) {
	        	vrr.setCodigo("300");
	            vrr.setDescripcion("NSS NO encontrado");
	            vrr.setVrDto(null);
	            e.printStackTrace();
	        }
		return vrr;
	}
	
	@Override
	public CalculoPagosResponse calculoPagos(CalculoPagosRequest cpr) throws ModalidadResponseException {
		
		
		CalculoPagosResponse cpResponse = new CalculoPagosResponse();
		CalculoPagosDTO obj = new CalculoPagosDTO();
		Gson gson = new Gson();
		try {		
			
			
			String json = gson.toJson(cpr);
			log.info("la url a consumir es " + URL_CALCULO_PAGOS );
			log.info("el objet transformado es: JSON [{}]", json);

			disableSSLCertificateChecking();
	    	
	       

	       
            URL url = new URL(URL_CALCULO_PAGOS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            // Configurar método y propiedades
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true); // Habilitar envío de cuerpo (payload)

            // Enviar datos
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("utf-8"));
            os.flush();
            os.close();

            // Leer código de respuesta
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);
          
            // Leer respuesta del servidor
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            }

           
            
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            obj = gson.fromJson(jsonObject.toString(),CalculoPagosDTO.class);
            
            
          //{ "status": "ERROR", "errorCode": "MISSING_PARAMS", "errorMessage": null, "requestId": "20141014181739_11625805172", "downstreamModuleErrorCode": null, "object": [ "activity_code", "activity_name", "points", "frequency", "strategy", "vsa_app_access_token" ]}
            log.info(""+jsonObject.get("idTramite"));
            System.out.println(jsonObject.get("idTramite"));
            System.out.println("Respuesta del servidor: " + response.toString());
            conn.disconnect();
            
            
            
            cpResponse.setCodigo("200");
            cpResponse.setDescripcion("Calculo Generado Correctamente");
            cpResponse.setVrDto(obj);
            
        } catch (Exception e) {
        	cpResponse.setCodigo("300");
        	cpResponse.setDescripcion("Calculo No generado");
        	cpResponse.setVrDto(null);
            e.printStackTrace();
        }
		return cpResponse;
	}
	
	@Override
	public GeneracionMultilineaResponse genracionMultilinea(GeneracionMultilineaRequest consulta) throws ModalidadResponseException{
		
		
		GeneracionMultilineaResponse vrr = new GeneracionMultilineaResponse();
		GeneracionMultilineaDTO obj = new GeneracionMultilineaDTO();
		Gson gson = new Gson();
		try {		
			
			
			String json = gson.toJson(consulta);
			log.info("la url a consumir es " + URL_GENERACION_MULTILINEA );
			log.info("el objet transformado es: JSON [{}]", json);

			disableSSLCertificateChecking();
	    	
       

       
            URL url = new URL(URL_GENERACION_MULTILINEA);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            // Configurar método y propiedades
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true); // Habilitar envío de cuerpo (payload)

            // Enviar datos
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("utf-8"));
            os.flush();
            os.close();

            // Leer código de respuesta
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);
          
            // Leer respuesta del servidor
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            }

           
            
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            obj = gson.fromJson(jsonObject.toString(),GeneracionMultilineaDTO.class);
            
            
            log.info(""+jsonObject.get("idSolicitud"));
            System.out.println(jsonObject.get("idSolicitud"));
            System.out.println("Respuesta del servidor: " + response.toString());
            conn.disconnect();
            
            
            
            vrr.setCodigo("202");
            vrr.setDescripcion("ID Calculo encontrado");
            vrr.setVrDto(obj);
            
        } catch (Exception e) {
        	vrr.setCodigo("300");
            vrr.setDescripcion("ID Calculo NO encontrado");
            vrr.setVrDto(null);
            e.printStackTrace();
        }
		return vrr;
	}
	
	@Override
	public GeneracionMultilineaConsultaResponse genracionMultilineaConsulta(String idSolicitud) throws ModalidadResponseException{
		
		
		GeneracionMultilineaConsultaResponse vrr = new GeneracionMultilineaConsultaResponse();
		GeneracionMultilineaConsultaDTO obj = new GeneracionMultilineaConsultaDTO();
		Gson gson = new Gson();
		try {		
			
			String idCodificado = URLEncoder.encode(idSolicitud, "UTF-8");
			
			log.info("la url a consumir es " + URL_GENERACION_MULTILINEA );
			log.info("el idSolicitud a buscar es: {}", idSolicitud);

			disableSSLCertificateChecking();
	    	
            URL url = new URL(URL_GENERACION_MULTILINEA+"/"+idCodificado); //idSolicitud
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            // Configurar método y propiedades
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Leer código de respuesta
            int statusCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + statusCode);
          
            // Leer respuesta del servidor
            BufferedReader br;
            if (statusCode >= 200 && statusCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            }

           
            
            StringBuilder response = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                response.append(output.trim());
            }
            br.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            obj = gson.fromJson(jsonObject.toString(),GeneracionMultilineaConsultaDTO.class);
            
            log.info(""+jsonObject.get("idSolicitud"));
            System.out.println(jsonObject.get("idSolicitud"));
            System.out.println("Respuesta del servidor: " + response.toString());
            conn.disconnect();
            
            
            
            vrr.setCodigo(""+statusCode);
            vrr.setDescripcion("ID Solicitud encontrado");
            vrr.setVrDto(obj);
            
        } catch (Exception e) {
        	vrr.setCodigo("300");
            vrr.setDescripcion("ID Solicitud NO encontrado");
            vrr.setVrDto(null);
            e.printStackTrace();
        }
		return vrr;
	}
	
	// Método para saltarse la validación SSL (Compatible con Java 6)
    private static void disableSSLCertificateChecking() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
            };

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) { return true; }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}