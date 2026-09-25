package mx.gob.imss.cit.cda.web.mapping.converter;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class ConverterRegister {
	
	@Autowired
	private AnnotationMethodHandlerAdapter adapter;

	private HttpMessageConverter<?>[] messageConverters;
	
	/**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterRegister.class);
	
	public void setMessageConverters(HttpMessageConverter<?>[] messageConverters) {
		LOGGER.debug("setMessageConverters" + messageConverters);
		this.messageConverters = messageConverters != null ? 
				Arrays.copyOf(messageConverters, messageConverters.length) : null;
	}

	@PostConstruct
	public void bindMessageConverters() {
		LOGGER.debug("Setting the news Messages Converters..... " + messageConverters + "to the ...."+adapter);
		
		adapter.setMessageConverters(messageConverters);
		LOGGER.debug("", adapter.getMessageConverters());
	}

}
