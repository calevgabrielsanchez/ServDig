package mx.gob.imss.cit.clienteswebservices.modalidad40.rest;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean.CalculoDTO;
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
			
			SSLContext sslContext = SSLContexts.custom()
					.loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(
					X509Certificate[] chain,
					String authType) {
					return true;
					}
					})
					.build();

					SSLConnectionSocketFactory sslsf =
					new SSLConnectionSocketFactory(
					sslContext,
					NoopHostnameVerifier.INSTANCE);

					CloseableHttpClient httpClient =
					HttpClients.custom()
					.setSSLSocketFactory(sslsf)
					.build();
			
	          String url = URL_VALIDA_RETROACTIVIDAD;
	          HttpPost httppost = new HttpPost(url);

	          
	         
	          
	          
	          httppost.setHeader("Content-Type","application/json");
	          
	          StringEntity jsonEntity = new StringEntity(json, "UTF-8");
	          jsonEntity.setContentType("application/json");
	          httppost.setEntity(jsonEntity);
	          

	          //Execute and get the response.
	          HttpResponse response = httpClient.execute(httppost);
	          HttpEntity entity = response.getEntity();
	          
	          System.out.println(entity.toString());
	          
			int status =
			response.getStatusLine().getStatusCode();
			
			System.out.println("STATUS = " + status);
			
			String respuesta =
			EntityUtils.toString(
			response.getEntity(),
			"UTF-8");
			
			System.out.println("RESPUESTA = " + respuesta);		
			
			JsonObject jsonObject = JsonParser.parseString(respuesta).getAsJsonObject();
			obj = gson.fromJson(respuesta,ValidaRetroactividadDTO.class);
			vrr.setCodigo(""+status);
			if(status == 200) {
	            vrr.setDescripcion(jsonObject.get("mensaje").getAsString());
	            vrr.setVrDto(obj);
			}else {
				 vrr.setDescripcion("NSS no encontrado");
	            vrr.setVrDto(null);
			}
			
			 
			 
		} catch (Exception e) {
        	vrr.setCodigo("300");
            vrr.setDescripcion("Hubo un error, y no se pudo procesar la peticion: "+e);
            vrr.setVrDto(null);
            e.printStackTrace();
        }
		return vrr;
			
			
	}
	
	@Override
	public CalculoPagosResponse calculoPagos(CalculoPagosRequest cpr) throws ModalidadResponseException {
		
		
		CalculoPagosResponse cpResponse = new CalculoPagosResponse();
		CalculoDTO obj = new CalculoDTO();
		Gson gson = new Gson();
		try {		
			
			
			String json = gson.toJson(cpr);
			log.info("la url a consumir es " + URL_CALCULO_PAGOS );
			log.info("el objet transformado es: JSON [{}]", json);

			disableSSLCertificateChecking();
	    	
			SSLContext sslContext = SSLContexts.custom()
					.loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(
					X509Certificate[] chain,
					String authType) {
					return true;
					}
					})
					.build();

					SSLConnectionSocketFactory sslsf =
					new SSLConnectionSocketFactory(
					sslContext,
					NoopHostnameVerifier.INSTANCE);

					CloseableHttpClient httpClient =
					HttpClients.custom()
					.setSSLSocketFactory(sslsf)
					.build();

			
			
	       
            URL url = new URL(URL_CALCULO_PAGOS);
            HttpPost httppost = new HttpPost(url.toURI());
            
            httppost.setHeader("Content-Type","application/json");
	          
	          StringEntity jsonEntity = new StringEntity(json, "UTF-8");
	          jsonEntity.setContentType("application/json");
	          httppost.setEntity(jsonEntity);

	        //Execute and get the response.
	          HttpResponse response = httpClient.execute(httppost);
	          HttpEntity entity = response.getEntity();
	          
	          System.out.println(entity.toString());
	          
			int status =
			response.getStatusLine().getStatusCode();
			
			System.out.println("STATUS = " + status);

			String respuesta =
					EntityUtils.toString(
					response.getEntity(),
					"UTF-8");
					
					System.out.println("RESPUESTA = " + respuesta);	

            JsonObject jsonObject = JsonParser.parseString(respuesta.toString()).getAsJsonObject();
            obj = gson.fromJson(jsonObject.toString(),CalculoDTO.class);
            
            cpResponse.setCodigo(""+status);
            cpResponse.setDescripcion(jsonObject.get("mensaje").getAsString() );
            cpResponse.setVrDto(obj);
            
        } catch (Exception e) {
        	cpResponse.setCodigo("300");
        	cpResponse.setDescripcion("Error, no se pudo generar la solicitud: "+e);
        	cpResponse.setVrDto(null);
            e.printStackTrace();
        }
		return cpResponse;
	}
	
	@Override
	public GeneracionMultilineaResponse generacionMultilinea(GeneracionMultilineaRequest consulta) throws ModalidadResponseException{
		
		
		GeneracionMultilineaResponse vrr = new GeneracionMultilineaResponse();
		GeneracionMultilineaDTO obj = new GeneracionMultilineaDTO();
		Gson gson = new Gson();
		try {		
			
			
			String json = gson.toJson(consulta);
			log.info("la url a consumir es " + URL_GENERACION_MULTILINEA );
			log.info("el objet transformado es: JSON [{}]", json);

			disableSSLCertificateChecking();
	    	
			SSLContext sslContext = SSLContexts.custom()
					.loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(
					X509Certificate[] chain,
					String authType) {
					return true;
					}
					})
					.build();

					SSLConnectionSocketFactory sslsf =
					new SSLConnectionSocketFactory(
					sslContext,
					NoopHostnameVerifier.INSTANCE);

					CloseableHttpClient httpClient =
					HttpClients.custom()
					.setSSLSocketFactory(sslsf)
					.build();
			
	          String url = URL_GENERACION_MULTILINEA;
	          HttpPost httppost = new HttpPost(url);

            httppost.setHeader("Content-Type","application/json");
	          
	          StringEntity jsonEntity = new StringEntity(json, "UTF-8");
	          jsonEntity.setContentType("application/json");
	          httppost.setEntity(jsonEntity);
	          

	          //Execute and get the response.
	          HttpResponse response = httpClient.execute(httppost);
	          HttpEntity entity = response.getEntity();
	          
	          System.out.println(entity.toString());
	          
			int status =
			response.getStatusLine().getStatusCode();
			
			System.out.println("STATUS = " + status);
			
			String respuesta =
			EntityUtils.toString(
			response.getEntity(),
			"UTF-8");
			
			System.out.println("RESPUESTA = " + respuesta);	
            JsonObject jsonObject = JsonParser.parseString(respuesta).getAsJsonObject();
            obj = gson.fromJson(jsonObject.toString(),GeneracionMultilineaDTO.class);
            
            vrr.setCodigo(""+status);
            vrr.setDescripcion(jsonObject.get("estado").getAsString());
            vrr.setVrDto(obj);
            
        } catch (Exception e) {
        	vrr.setCodigo("300");
            vrr.setDescripcion("No se pudo generar la multilinea: "+e);
            vrr.setVrDto(null);
            e.printStackTrace();
        }
		return vrr;
	}
	
	@Override
	public GeneracionMultilineaConsultaResponse generacionMultilineaConsulta(String nss) throws ModalidadResponseException{
		
		
		GeneracionMultilineaConsultaResponse vrr = new GeneracionMultilineaConsultaResponse();
		GeneracionMultilineaConsultaDTO obj = new GeneracionMultilineaConsultaDTO();
		Gson gson = new Gson();
		try {		
			
			
			//String idCodificado = URLEncoder.encode(nss, "UTF-8");
			
			log.info("la url a consumir es " + URL_GENERACION_MULTILINEA );
			log.info("el nss a buscar es: {}", nss);

			disableSSLCertificateChecking();
			
			// 1. Configuración de SSL para saltar los certificados (Misma del método anterior)
	        SSLContext sslContext = SSLContexts.custom()
	                .loadTrustMaterial(null, new TrustStrategy() {
	                    public boolean isTrusted(X509Certificate[] chain, String authType) {
	                        return true;
	                    }
	                })
	                .build();

	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslContext,
	                NoopHostnameVerifier.INSTANCE);

	        CloseableHttpClient httpClient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();
	    	
	     // 2. Construcción de la URL con el Path Parameter
	        String urlFinal = URL_GENERACION_MULTILINEA + "/nss/" + nss;
	        
	        // 3. Crear petición HTTP GET usando Apache HttpClient
	        HttpGet httpGet = new HttpGet(urlFinal);
	        httpGet.setHeader("Accept", "application/json");
	        
	        // 4. Ejecutar la petición
	        HttpResponse response = httpClient.execute(httpGet);
	        
	        // 5. Leer código de respuesta
	        int statusCode = response.getStatusLine().getStatusCode();
	        System.out.println("Código de respuesta: " + statusCode);
	      
	        // 6. Leer la respuesta del servidor de forma simplificada con EntityUtils
	        String respuestaStr = "";
	        HttpEntity entity = response.getEntity();
	        if (entity != null) {
	            respuestaStr = EntityUtils.toString(entity, "UTF-8");
	        }
	        
	        System.out.println("Respuesta del servidor: " + respuestaStr);

	        // 7. Parsear la respuesta JSON
	        JsonObject jsonObject = JsonParser.parseString(respuestaStr).getAsJsonObject();
	        obj = gson.fromJson(jsonObject.toString(), GeneracionMultilineaConsultaDTO.class);
	        
	        if (jsonObject.get("idSolicitud") != null) {
	            log.info("" + jsonObject.get("idSolicitud"));
	            System.out.println(jsonObject.get("idSolicitud"));
	        }
	        
	        // Cierre del cliente Apache
	        httpClient.close();
	        
	        vrr.setCodigo("" + statusCode);
	        vrr.setDescripcion("ID Solicitud encontrado");
	        vrr.setVrDto(obj);
	        
	    } catch (Exception e) {
	        vrr.setCodigo("300");
	        vrr.setDescripcion("ID Solicitud NO encontrado: "+e);
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

    public static void main(String[] args) {
		ValidaRetroactividadRequest vrr = new ValidaRetroactividadRequest();
		CalculoPagosRequest solicitud = new  CalculoPagosRequest();
		GeneracionMultilineaRequest genMultiReq = new  GeneracionMultilineaRequest();
		vrr.setNss("04016206015");
		vrr.setUsuario("MODALIDAD40");

		solicitud.setIdCalculo("616");
		solicitud.setNss("04016206015");
		solicitud.setMunicipioImss("02A06");
		solicitud.setSalarioElegido(new BigDecimal("1500.00"));
		solicitud.setOrigenCalculo("CONTRATACION");
		solicitud.setUsuario("MODALIDAD40");
		solicitud.setEntidadInegi("09");
		solicitud.setMunicipioInegi("016");
		genMultiReq.setIdCalculo("631");


		Modalidad40RestLocal m40rl = new Modalidad40RestLocalImpl(); //= new Modalidad40RestLocal();//validaRetroactividad()
		try {
			ValidaRetroactividadResponse response =	m40rl.validaRetroactividad(vrr);
			System.out.println("Response: service 1: "+response.getDescripcion());
			solicitud.setIdCalculo(response.getVrDto().getIdCalculo());
			CalculoPagosResponse calPagRes = m40rl.calculoPagos(solicitud);
			System.out.println("Response: service 2: "+calPagRes.getDescripcion());
			genMultiReq.setIdCalculo(calPagRes.getVrDto().getIdCalculo());
			GeneracionMultilineaResponse gmr = m40rl.generacionMultilinea(genMultiReq);
			System.out.println("Response: service 3: "+gmr.getDescripcion());
			System.out.println("Response: service 3: "+gmr.getVrDto().getIdSolicitud()  );
			m40rl.generacionMultilineaConsulta("04016206015");
		} catch (ModalidadResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}