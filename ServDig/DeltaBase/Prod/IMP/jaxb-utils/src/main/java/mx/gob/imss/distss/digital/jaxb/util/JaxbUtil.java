/**
 * 
 */
package mx.gob.imss.distss.digital.jaxb.util;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import mx.gob.imss.distss.digital.jaxb.util.JaxbContextHelper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author Lucio Durán Silva
 * 
 */
public class JaxbUtil {

	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(JaxbUtil.class);
	}

	/**
	 * Convierte un Objeto en un String con la estructura de un XML.
	 * 
	 * @param listaClases
	 * @param object
	 * @return
	 */
	public static String objectToXml(final Object object) {
		String xml = null;
		
			final JAXBContext jaxbCtx = JaxbContextHelper.getInstance();
			
			xml = objectToXml (object, jaxbCtx);

		return xml;
	}

	/**
	 * Convierte un String con estructura XML en una grafica de objetos.
	 * 
	 * @param listaClases
	 * @param xml
	 * @return
	 */
	public static Object xmlToObject(final String xml) {
		Object object = null; // NOPMD
		if (xml == null) {
			LOG.warn("Se ha pasado null a metodo JaxbUtil.xmlToObject");
		} else {

				final JAXBContext jaxbCtx = JaxbContextHelper.getInstance();
				
				object = xmlToObject (xml,jaxbCtx);

		}
		return object;
	}
	
	/**
	 * Genera un objeto a partir de su representacion xml
	 * 
	 * @param xml Cadena con la representacion xml del objeto a generar
	 * @param clase la clase del objeto qe se creara a partir de su xml
	 * @param <T> Tipo de dato que se recupera del xml
	 * @return El objeto generadao a partir del xml
	 * @throws JAXBException Errors al parseo de la informacion
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T unmarshaller(String xml, Class<T> clase)
			throws JAXBException {

		T datos = (T) xmlToObject(xml);

		return datos;
	}

	/**
	 * Genera la representacion en XML de un objeto
	 * 
	 * @param dato El objeto a ser representado en xml
	 * @param <T> Tipo de dato a ser transformado
	 * @return La cade con la representacion xml del objeto
	 * @throws JAXBException Error al parsear el objeto
	 */
	public static final <T> String marshaller(T dato) throws JAXBException {

		String marshalledString = objectToXml(dato);

		return StringUtils.isNotBlank(marshalledString) ? marshalledString
				.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
				: marshalledString;
	}

	public static final <T> String marshaller(T dato, Class<?>[] classes)
			throws JAXBException {
		
		return marshaller(dato);
	}
	
	public static Object xmlToObject(final String xml, JAXBContext contexto) {
		Object object = null; // NOPMD
		if (xml == null) {
			LOG.warn("Se ha pasado null a metodo JaxbUtil.xmlToObject");
		} else {
			try {
				final Reader reader = new StringReader(xml);
				final Unmarshaller unmarshaller = contexto.createUnmarshaller();

				object = unmarshaller.unmarshal(reader);

			} catch (JAXBException e) {
				LOG.error("Error al convertir XML a objeto.", e);
				e.printStackTrace();
			}
		}
		return object;
	}
	
	public static String objectToXml(final Object object, JAXBContext contexto) {
		String xml = null;
		try {
			final Marshaller marshaller = contexto.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			final Writer writer = new StringWriter();

			marshaller.marshal(object, writer);
			xml = writer.toString();
			// LOG.trace(writer.toString());
		} catch (JAXBException e) {
			LOG.error("Error marshalling objec[" + e.getMessage() + "]", e);

			e.printStackTrace();

		}
		return xml;
	}
	@SuppressWarnings("unchecked")
	public static final <T> T unmarshaller(String xml, JAXBContext contexto)
			throws JAXBException {

		T datos = (T) xmlToObject(xml,contexto);

		return datos;
	}
	
	public static final <T> String marshaller(T dato, JAXBContext contexto) throws JAXBException {

		String marshalledString = objectToXml(dato, contexto);

		return StringUtils.isNotBlank(marshalledString) ? marshalledString
				.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
				: marshalledString;
	}
	
}
