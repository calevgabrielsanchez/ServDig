package mx.gob.imss.cit.dacvass.servicios.externos.business;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenSISTRequestBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenSISTResponse;
import mx.gob.imss.cit.dacvass.servicios.externos.model.TokenSISTResponseException;
import mx.gob.imss.cit.dacvass.servicios.externos.remote.ITokenAccesoSISTRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.util.ResourceBundleConfiguration;

public class TokenAccesoSISTServicesImp implements ITokenAccesoSISTRemote {
	
	private final Logger log = LoggerFactory.getLogger(TokenAccesoSISTServicesImp.class);
	private Client client;
	private WebResource webResource;
	private final String URL_TOKEN_SIST;
	private final Integer TIME_OUT_CONEXION;
	private final Integer TIME_OUT_RESPUESTA;
	
	
	
	public TokenAccesoSISTServicesImp() {
		ResourceBundle resourceBundle = ResourceBundleConfiguration.getResourceServicioExterno();
		URL_TOKEN_SIST = resourceBundle.getString("servicios.externos.rest.url.token.sist");
		TIME_OUT_RESPUESTA = new Integer(resourceBundle.getString("servicios.externos.rest.timeOut.response"));
		TIME_OUT_CONEXION = new Integer(resourceBundle.getString("servicios.externos.rest.timeOut.connection"));
		client = Client.create();
	}

	public String getTokenAccesoSIST(TokenSISTRequestBean token) throws TokenSISTResponseException, Exception {
		try {				
		Gson gson = new Gson();
		String json = gson.toJson(token);
		
		log.debug("el objet transformado es: JSON [{}]", json);
		String urlToken = URL_TOKEN_SIST;
		log.debug("la url a consumir es " + urlToken );
		client.setConnectTimeout(TIME_OUT_CONEXION);
		client.setReadTimeout(TIME_OUT_RESPUESTA);
		webResource = client.resource(urlToken);

		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, json);
		
		if (response.getStatus() != 200) {
			throw new TokenSISTResponseException("Error en el Servicio de toquen SIST Status: " + response.getStatus());
		}
		
		log.info("Codigo status del Servicio token SIST [Status {} Ok]", response.getStatus());
		
		TokenSISTResponse objToken = response.getEntity(TokenSISTResponse.class);
		if(objToken == null || objToken.getJsonResultado()== null || objToken.getJsonResultado().equals(""))
			throw new TokenSISTResponseException("El servicio de token SIST regreso nula la respuesta");

		return objToken.getJsonResultado();
		}catch(TokenSISTResponseException e) {
			throw e;
		}catch (Exception e) {
			log.error("ocurrio un erro al consultar el cliente del token ", e);
			throw e;			
		}
	}

}
