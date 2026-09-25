/**
 * portalExpediente-web29/02/2012
 * mx.gob.imss.ctirss.delta.portal.expediente.web.mapping.converter29/02/2012
 * ConverterRegister.java
 * 29/02/2012
 * 
 */
package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.mapping.converter;

import java.util.Arrays;

import javax.annotation.PostConstruct;

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

	public void setMessageConverters(
			HttpMessageConverter<?>[] arrMessageConverters) {
		System.out.println("setMessageConverters"
				+ Arrays.toString(arrMessageConverters));
		this.messageConverters = arrMessageConverters != null ? 
				Arrays.copyOf(arrMessageConverters, arrMessageConverters.length) : null;
	}

	@PostConstruct
	public void bindMessageConverters() {
		System.out.println("Setting the news Messages Converters..... "
				+ Arrays.toString(messageConverters) + "to the ...." + adapter);

		adapter.setMessageConverters(messageConverters);
		System.out.println(Arrays.toString(adapter.getMessageConverters()));
	}

}
