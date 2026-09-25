/**
 * visor-web29/02/2012
 * mx.gob.imss.ctirss.delta.correccion.mapping.converter29/02/2012
 * ConverterRegister.java
 * 29/02/2012
 * 
 */
package mx.gob.imss.ctirss.delta.correccion.mapping.converter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

/**
 * @author Lucio Duran Silva
 * Instituto Mexicano del Seguro Social
 */

public class ConverterRegister {
	
	
	
	@Autowired
	private AnnotationMethodHandlerAdapter adapter;

	private HttpMessageConverter<?>[] messageConverters;
	
	public void setMessageConverters(HttpMessageConverter<?>[] messageConverters) {
		System.out.println("setMessageConverters" + messageConverters);
		this.messageConverters = messageConverters;
	}

	@PostConstruct
	public void bindMessageConverters() {
		System.out.println("Setting the news Messages Converters..... " + messageConverters + "to the ...."+adapter);
		
		adapter.setMessageConverters(messageConverters);
		System.out.println(adapter.getMessageConverters());
	}

}
