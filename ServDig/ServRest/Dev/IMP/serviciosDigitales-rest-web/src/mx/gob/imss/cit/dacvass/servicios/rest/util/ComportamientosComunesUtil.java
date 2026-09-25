package mx.gob.imss.cit.dacvass.servicios.rest.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;

public class ComportamientosComunesUtil {
	
	
	private static final Logger log = LoggerFactory.getLogger(ComportamientosComunesUtil.class); 
	
	
	public static  WebApplicationException getWebApplicationException(ServiciosRestException e) {
		
		log.error("Ocurrio un error y el codigo es" +  e.getErrorBean().getCode());
		
		if(Integer.parseInt(e.getErrorBean().getCode()) <= 499) {
			return new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).
				    entity(e.getErrorBean()).type(MediaType.APPLICATION_JSON).build());
		}
		else {
			return new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).
				    entity(e.getErrorBean()).type(MediaType.APPLICATION_JSON).build());
		}
		
	}
	
	public static Integer generaDigitoVerificador(String nss){  
		int suma = 0;
		int resultado = 0;
		for(int i = 1 ; i <= nss.length() ; i++){
			if(i%2==0){
				int multiplicacion = (Integer.parseInt(nss.charAt(i-1)+"")) * 2;
				if(multiplicacion > 9 ){
					suma = suma + ((multiplicacion-10)+1);
				}else{
					suma = suma + multiplicacion;
				}
			}else{
				suma = suma + (Integer.parseInt(nss.charAt(i-1)+""));
			}	
		}
		int modulo = suma%10;
		if(modulo == 0 ){
			resultado = 0;
		}else if( modulo < 10){
			resultado = 10-modulo;
		}
		return resultado;
	}

}
