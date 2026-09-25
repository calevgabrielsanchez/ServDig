/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DateMapper.java
 *  @Paquete:mx.gob.imss.ctirss.delta.custom.mapping.converter
 *  @Fecha:28/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.mapping.converter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.deser.CustomDeserializerFactory;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Lucio Duran Silva
 * 
 */
@Component("jacksonDateMapper")
public class DateMapper extends ObjectMapper {

    private static final Log LOG = LogFactory.getLog(DateMapper.class);

    public DateMapper() {
        super();
        LOG.debug("DateMapper initializer ....");
    }

    private String mask = "dd/MM/yyyy";

    @PostConstruct
    public void afterPropertiesSet() throws Exception {

        LOG.debug("afterPropertiesSet setting....");

        super.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        CustomSerializerFactory factory = new CustomSerializerFactory();
        factory.addSpecificMapping(Date.class, new JsonSerializer<Date>() {
            @Override
            public Class<Date> handledType() {
                return Date.class;
            }

            @Override
            public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

                jgen.writeString(new SimpleDateFormat(mask).format(value));
            }
        });

        CustomDeserializerFactory desfactory = new CustomDeserializerFactory();
        desfactory.addSpecificMapping(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Date date = null;
                try {
                    date = new SimpleDateFormat(mask).parse(jp.getText());
                } catch (ParseException e) {
                    LOG.error("Parsing date", e);
                }
                return date;
            }

        });
        LOG.debug("mask " + mask);
        LOG.debug("setting the factory [" + factory + " ]");
        super.setSerializerFactory(factory);
        super.getDeserializationConfig().setDateFormat(new SimpleDateFormat(mask));
        super.getSerializationConfig().setDateFormat(new SimpleDateFormat(mask));
        LOG.debug("setting the SimpleDateFormat [" + super.getSerializationConfig().getDateFormat() + " ]");
        LOG.debug("setting the SimpleDateFormat [" + super.getDeserializationConfig().getDateFormat() + " ]");
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

}
