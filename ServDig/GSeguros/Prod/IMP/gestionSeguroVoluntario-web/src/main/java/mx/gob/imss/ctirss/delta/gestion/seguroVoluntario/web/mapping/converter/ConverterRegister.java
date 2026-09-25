/**
 * portalExpediente-web29/02/2012
 * mx.gob.imss.ctirss.delta.portal.expediente.web.mapping.converter29/02/2012
 * ConverterRegister.java
 * 29/02/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.mapping.converter;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

/**
 * @author Lucio Duran Silva Instituto Mexicano del Seguro Social
 */
//@Component
public class ConverterRegister {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private AnnotationMethodHandlerAdapter adapter;

	private HttpMessageConverter<?>[] messageConverters;

	public void setMessageConverters(
			HttpMessageConverter<?>[] arrMessageConverters) {
		log.info("setMessageConverters"
				+ Arrays.toString(arrMessageConverters));
		this.messageConverters = arrMessageConverters != null ? 
				Arrays.copyOf(arrMessageConverters, arrMessageConverters.length) : null;
	}

	@PostConstruct
	public void bindMessageConverters() {
		log.info("Setting the news Messages Converters..... "
				+ Arrays.toString(messageConverters) + "to the ...." + adapter);

		adapter.setMessageConverters(messageConverters);
		log.info(Arrays.toString(adapter.getMessageConverters()));
	}

}
