/**
 * portalExpediente-web29/02/2012
 * mx.gob.imss.ctirss.delta.portal.expediente.web.mapping.converter29/02/2012
 * DateMapper.java
 * 29/02/2012
 * 
 */
package mx.gob.imss.cdsss.delta.portal.mapping.converter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Lucio Duran Silva Instituto Mexicano del Seguro Social
 */

@Component("jacksonDateMapper")
public class DateMapper extends ObjectMapper {

	private String mask = "dd/MM/yyyy";

	@PostConstruct
	public void afterPropertiesSet() throws Exception {

		System.out.println("DateMapper{");

		super.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
				false);

		// this one doesn't work at all, it's necessary to create and register a
		// factory
		// getSerializationConfig().withDateFormat(new SimpleDateFormat(mask));

		// I am using Jackson 1.9 asl
		CustomSerializerFactory factory = new CustomSerializerFactory();
		factory.addSpecificMapping(Date.class, new JsonSerializer<Date>() {

			@Override
			public Class<Date> handledType() {
				return Date.class;
			}

			@Override
			public void serialize(Date value, JsonGenerator jgen,
					SerializerProvider provider) throws IOException,
					JsonProcessingException {

				jgen.writeString(new SimpleDateFormat(mask).format(value));
			}
		});

		this.getDeserializationConfig().setDateFormat(
				new SimpleDateFormat(mask));

		this.setSerializerFactory(factory);
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

}
