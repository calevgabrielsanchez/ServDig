package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.RespuestaPensionesRest;
@Service
@Path("v1/json")
public class JsonService {
	

	    
	    @GET
		@Path("/pension/{curpID}")
		@Produces({"application/json"})
	    public RespuestaPensionesRest getDatosPension(@PathParam("curpID") String curpID) {
	    	try {
	    	Client client = Client.create();
    		WebResource webResource = client.resource("http://172.16.162.132/TSPI/sistrap/ConsultaAutenticacion/");
    		String cadena = "{\"curp\":\""+curpID+"\"}";
    		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, cadena);
    		if (response.getStatus() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "
    					+ response.getStatus());
    		}
    		
            
    		 response = webResource.type("application/json").post(ClientResponse.class, cadena);
    		RespuestaPensionesRest  output = response.getEntity(RespuestaPensionesRest.class);
    		System.out.println("tag de oobjeto" + output.getMensajeError() + "y con valor" +output.getResolucionPension().size());
    		return output;
	    	}catch(Exception e) {
	    		System.out.println("ocurrio un error al generar el cliente" + e.getMessage());
	    	}
	        return null;
	 
	   }



}
