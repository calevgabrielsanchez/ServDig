/**
 * CustomObjectMapper.java
 * @package mx.gob.imss.ctirss.delta.gestion.individuo.web.mapping.converter
 * @project gestionIndividuo-web	
 * 
 * Esta clase es para tener control en como Jackson realiza la conversion de objetos java a json y viceversa.
 * Para el caso especifico de controlar los formatos de la fecha.
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.mapping.converter;

import java.text.DateFormat;
import java.util.Locale;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 13/09/2011
 */
public class CustomObjectMapper extends MappingJacksonHttpMessageConverter {

    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("es_mx"));

    
    public CustomObjectMapper() {
        super();
        
        final ObjectMapper objectMapper = new ObjectMapper();
        
        
        objectMapper.getDeserializationConfig().setDateFormat(dateFormat);
        objectMapper.getSerializationConfig().setDateFormat(dateFormat);
        objectMapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        setObjectMapper(objectMapper);

    }

}
