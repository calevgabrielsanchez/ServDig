/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:ConverterRegister.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.individuo.web.mapping.converter
 *  @Fecha:28/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.mapping.converter;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

/**
 * @author Lucio Duran Silva
 *
 */

public class ConverterRegister {

	
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private AnnotationMethodHandlerAdapter adapter;

	private HttpMessageConverter<?>[] messageConverters;
	
	
	
	public void setMessageConverters(HttpMessageConverter<?>[] messageConverters) {
		System.out.println("setMessageConverters" + Arrays.toString(messageConverters));
		this.messageConverters = messageConverters != null ? 
				Arrays.copyOf(messageConverters, messageConverters.length) : null;
	}

	@PostConstruct
	public void bindMessageConverters() {
		this.log.debug("ConverterRegister bindMessageConverters... ");
	adapter.setMessageConverters(messageConverters);
	}


	
}

