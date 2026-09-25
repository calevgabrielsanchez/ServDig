package mx.gob.imss.cit.dacvass.servicios.externos.business;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenConveniosRequestBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenConveniosResponse;
import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenConveniosResponseException;
import mx.gob.imss.cit.dacvass.servicios.externos.remote.ITokenAccesoConveniosRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.util.ResourceBundleConfiguration;

public class TokenAccesoConveniosServicesImp implements ITokenAccesoConveniosRemote {
	
	private final Logger log = LoggerFactory.getLogger(TokenAccesoConveniosServicesImp.class);
	private Client client;
	private WebResource webResource;
	private final String URL_TOKEN_CONVENIOS;
	private final Integer TIME_OUT_CONEXION;
	private final Integer TIME_OUT_RESPUESTA;
	
	
	public TokenAccesoConveniosServicesImp() {
		ResourceBundle resourceBundle = ResourceBundleConfiguration.getResourceServicioExterno();
		URL_TOKEN_CONVENIOS = resourceBundle.getString("servicios.externos.rest.url.token.convenios");
		TIME_OUT_RESPUESTA = new Integer(resourceBundle.getString("servicios.externos.rest.timeOut.response"));
		TIME_OUT_CONEXION = new Integer(resourceBundle.getString("servicios.externos.rest.timeOut.connection"));
		client = Client.create();
	}

	public String getTokenAccesoConvenios(TokenConveniosRequestBean token) throws TokenConveniosResponseException, Exception {
		
		
		try {				
		Gson gson = new Gson();
		String json = gson.toJson(token);
		log.debug("la url a consumir es " + URL_TOKEN_CONVENIOS );
		log.debug("el objet transformado es: JSON [{}]", json);
		client.setConnectTimeout(TIME_OUT_CONEXION);
		client.setReadTimeout(TIME_OUT_RESPUESTA);
		webResource = client.resource(URL_TOKEN_CONVENIOS);

		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, json);
		
		if (response.getStatus() != 200) {
			throw new TokenConveniosResponseException("Error en el Servicio de de toquen Status: " + response.getStatus());
		}
		
		log.info("Codigo status del Servicio token convenios [Status {} Ok]", response.getStatus());
		
		TokenConveniosResponse objToken = response.getEntity(TokenConveniosResponse.class);
		if(objToken == null || objToken.getTokenInfo()== null || objToken.getTokenInfo().equals(""))
			throw new TokenConveniosResponseException("El servicio de token regreso nula la respuesta");

		return objToken.getTokenInfo();
		}catch(TokenConveniosResponseException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un erro al consultarel cliente del token ", e);
			throw e;
			
		}
	}
	
	public static void main(String[] args) {
		try {
		
			TokenConveniosRequestBean bean = new TokenConveniosRequestBean();
			bean.setCurpRepresentante("SAHJ790331HDFLRN01");
			bean.setNombreRepresentante("YO MERENGES");
			bean.setRazonSocial("EL MANGON");
			bean.setRegistroPatronal("Y321234510");
			bean.setRfcSolicitante("SAHJ7903319G6");
			
			ITokenAccesoConveniosRemote tokenService = new TokenAccesoConveniosServicesImp();
			String strToken = tokenService.getTokenAccesoConvenios(bean);
			System.out.println("la respuesta es:  " + strToken);
		}catch (TokenConveniosResponseException e) {
			System.out.println("error de negocio " + e.getMessage());
		}catch (Exception e) {
			System.out.println("error no cachado " + e.getMessage());
		}
		
	}


}
