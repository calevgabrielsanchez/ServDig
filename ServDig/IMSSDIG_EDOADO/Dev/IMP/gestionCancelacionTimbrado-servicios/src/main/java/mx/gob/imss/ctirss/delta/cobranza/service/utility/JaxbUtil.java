package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author Lucio Duran Silva
 * 
 */
public class JaxbUtil {

	private static final Logger LOG;
	@SuppressWarnings("rawtypes")
	private transient final Class[] classes;

	static {
		LOG = LoggerFactory.getLogger(JaxbUtil.class);
	}

	public JaxbUtil(@SuppressWarnings("rawtypes") final Class[] classes) {
		this.classes = classes.clone();
	}
	
	/**
	 * Convierte un Objeto en un String con la estructura de un XML.
	 * 
	 * @param listaClases
	 * @param object
	 * @return
	 */
	public String objectToXml(final Object object) {
		String xml = null; // NOPMD TODO checar vs null object
		try {
			final JAXBContext jaxbCtx = JAXBContext.newInstance(classes,
					null);
			final Marshaller marshaller = jaxbCtx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			final Writer writer = new StringWriter();

			marshaller.marshal(object, writer);
			xml = writer.toString();
			// LOG.trace(writer.toString());
		} catch (JAXBException e) {
			LOG.error("Error marshalling objec[" + e.getMessage() +"]", e);
			
			e.printStackTrace();
			
		}
		return xml;
	}

	/**
	 * Convierte un String con estructura XML en una grafica de objetos.
	 * 
	 * @param listaClases
	 * @param xml
	 * @return
	 */
    public Object xmlToObject(final String xml) {
        Object object = null; // NOPMD
        if (xml == null) {
            LOG.warn("Se ha pasado null a metodo JaxbUtil.xmlToObject");
        } else {
            try {
                final JAXBContext jaxbCtx = JAXBContext.newInstance(classes, null);
                final Reader reader = new StringReader(xml);
                final Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();

                object = unmarshaller.unmarshal(reader);

            } catch (JAXBException e) {
                LOG.error("Error al convertir XML a objeto.", e);
                e.printStackTrace();
            }
        }
        return object;
    }

}
