/**
 * portalExpediente-web29/02/2012
 * mx.gob.imss.ctirss.delta.portal.expediente.web.mapping.converter29/02/2012
 * ConverterRegister.java
 * 29/02/2012
 * 
 */
package mx.gob.imss.ctirss.delta.portal.web.mapping.converter;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

/**
 * @author Lucio Duran Silva Instituto Mexicano del Seguro Social
 */

public class ConverterRegister {

	@Autowired
	private AnnotationMethodHandlerAdapter adapter;

	private HttpMessageConverter<?>[] messageConverters;
	
	/**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterRegister.class);
    
	public void setMessageConverters(
			HttpMessageConverter<?>[] arrMessageConverters) {
		LOGGER.debug("setMessageConverters"
				+ Arrays.toString(arrMessageConverters));
		this.messageConverters = arrMessageConverters != null ? 
				Arrays.copyOf(arrMessageConverters, arrMessageConverters.length) : null;
	}

	@PostConstruct
	public void bindMessageConverters() {
		LOGGER.debug("Setting the news Messages Converters..... "
				+ Arrays.toString(messageConverters) + "to the ...." + adapter);

		adapter.setMessageConverters(messageConverters);
		LOGGER.debug(Arrays.toString(adapter.getMessageConverters()));
	}

}
