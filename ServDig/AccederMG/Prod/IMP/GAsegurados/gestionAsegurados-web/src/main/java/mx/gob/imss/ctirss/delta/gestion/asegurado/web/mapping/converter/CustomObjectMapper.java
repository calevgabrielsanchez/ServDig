/**
 * CustomObjectMapper.java
 * @package mx.gob.imss.gestionAsegurados.web.mapping.converter
 * @project gestionAsegurados-web	
 * 
 * Esta clase es para tener control en como Jackson realiza la conversion de objetos java a json y viceversa.
 * Para el caso especifico de controlar los formatos de la fecha.
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.web.mapping.converter;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 13/09/2011
 */
public class CustomObjectMapper extends MappingJacksonHttpMessageConverter {

	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public CustomObjectMapper(){
		super();
		 ObjectMapper objectMapper = new ObjectMapper();
		 objectMapper.getSerializationConfig().setDateFormat(df);
		 objectMapper.getDeserializationConfig().setDateFormat(df); 
		    objectMapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		    setObjectMapper(objectMapper);

	}
	
	
}
