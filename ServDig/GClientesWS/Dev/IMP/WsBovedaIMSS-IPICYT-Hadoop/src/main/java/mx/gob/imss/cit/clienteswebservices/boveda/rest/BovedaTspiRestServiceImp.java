package mx.gob.imss.cit.clienteswebservices.boveda.rest;



import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.imss.cit.clienteswebservices.boveda.rest.bean.BovedaResponseException;
import mx.gob.imss.cit.clienteswebservices.boveda.rest.bean.ConsultaEstatusDocBovedaRequest;
import mx.gob.imss.cit.clienteswebservices.boveda.rest.bean.ConsultaEstatusDocBovedaResponse;
import mx.gob.imss.cit.clienteswebservices.boveda.util.ResourceBundleConfiguration;


public class BovedaTspiRestServiceImp implements IBovedaTspiRestLocal{
	
	
	private final Logger log = LoggerFactory.getLogger(BovedaTspiRestServiceImp.class);
	private Client client;
	private WebResource webResource;
	private final String URL_COSULTA_BOVEDA;
	private final Integer TIME_OUT_CONEXION;
	private final Integer TIME_OUT_RESPUESTA;
	
	
	public BovedaTspiRestServiceImp() {
		ResourceBundle resourceBundle = ResourceBundleConfiguration.getResourceServicioExterno();
		URL_COSULTA_BOVEDA = resourceBundle.getString("servicios.externos.rest.url.consulta.boveda");
		TIME_OUT_RESPUESTA = new Integer(resourceBundle.getString("servicios.externos.rest.timeOut.response"));
		TIME_OUT_CONEXION = new Integer(resourceBundle.getString("servicios.externos.rest.timeOut.connection"));
		client = Client.create();
	}

	@Override
	public ConsultaEstatusDocBovedaResponse consultaEstatusDoc(ConsultaEstatusDocBovedaRequest consulta) throws BovedaResponseException{
		try {				
			Gson gson = new Gson();
			String json = gson.toJson(consulta);
			log.debug("la url a consumir es " + URL_COSULTA_BOVEDA );
			log.debug("el objet transformado es: JSON [{}]", json);
			client.setConnectTimeout(TIME_OUT_CONEXION);
			client.setReadTimeout(TIME_OUT_RESPUESTA);
			webResource = client.resource(URL_COSULTA_BOVEDA);

			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, json);
			
			if (response.getStatus() != 200) {
				throw new BovedaResponseException("Error en el Servicio de consulta de boveda TCPI: " + response.getStatus());
			}
			
			log.info("Codigo status del Servicio token convenios [Status {} Ok]", response.getStatus());
			
			ConsultaEstatusDocBovedaResponse objResponse = response.getEntity(ConsultaEstatusDocBovedaResponse.class);
			if(objResponse == null )
				throw new BovedaResponseException("El servicio de boveda regreso nula la respuesta");

			return objResponse;
			}catch(BovedaResponseException e) {
				throw e;
			}catch (Exception e) {
				log.error("ocurrio un error al consultar el cliente de boveda", e);
				throw new BovedaResponseException("Ocurrio un error al consultar el servicio de boveda " , e);
				
			}
	}
	
	

}
