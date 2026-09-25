package mx.gob.imss.cit.ws.externo.boveda.documental.rest;

import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import mx.gob.imss.cit.ws.externo.boveda.documental.schema.EntradaAlta;
import mx.gob.imss.cit.ws.externo.boveda.documental.schema.EntradaConsulta;
import mx.gob.imss.cit.ws.externo.boveda.documental.schema.SalidaAlta;
import mx.gob.imss.cit.ws.externo.boveda.documental.schema.SalidaConsulta;
import mx.gob.imss.cit.ws.externo.boveda.documental.utils.RutaBovedaEnum;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

public class BovedaRestClient {

	private static String rutaRestBoveda;
	private static final String REST_RESOURCE = "enviroment.rest.resource";
	private static final String PATH_ENVIROMENT = "/enviromentConfig.properties";
	private static final String RESOURCE_CREATE = "/document/create";
	private static final String RESOURCE_CONSULTAR = "/document/get";
	private static Properties enviroment;
	private static final int SERVICE_TIMEOUT = 30000;
    private static final int CONNECT_TIMEOUT = 10000;
	
    /**
     * Metodo para realizar la consulta del documento
     * @param entradaConsulta
     * @return
     */
	public static SalidaConsulta consultaDocumento(EntradaConsulta entradaConsulta) {
		return hacerPeticionPost(RESOURCE_CONSULTAR, entradaConsulta, SalidaConsulta.class);
	}
	
	/**
	 * Metodo para realizar el alta de un documento
	 * @param entradaAlta
	 * @return
	 */
	public static SalidaAlta altaDocumento(EntradaAlta entradaAlta) {
		return hacerPeticionPost(RESOURCE_CREATE, entradaAlta, SalidaAlta.class);
		
	}
	
	private static <T,S> T hacerPeticionPost(String url,S datosConsulta, Class<T> respuesta) {
		init();
		//Creamos el cliente REST indicando que usaremos JSON
		Client cliente = ClientBuilder.newClient().register(new JacksonFeature());
		//Establecemos los tiempos de conexion y de respuesta del servicio REST
		cliente.property(ClientProperties.CONNECT_TIMEOUT, CONNECT_TIMEOUT);
		cliente.property(ClientProperties.READ_TIMEOUT, SERVICE_TIMEOUT);
		Entity<S> peticion =Entity.entity(datosConsulta, MediaType.APPLICATION_JSON);
		//Realizamos la peticion a la ruta de la boveda
		return cliente.target(rutaRestBoveda+url).request(MediaType.APPLICATION_JSON).post(peticion, respuesta);
	}
	
	private static void init() {
		if(rutaRestBoveda == null) {
			if (enviroment == null) {
				enviroment = new Properties();
				try {
					enviroment.load(RutaBovedaEnum.class.getResourceAsStream(PATH_ENVIROMENT));
					rutaRestBoveda = enviroment.getProperty(REST_RESOURCE);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
