/**
 * 
 */
package mx.gob.imss.cit.cda.web.base;

import java.io.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * clase utilitaria para serializacion y desserealizacion de objetos en las
 * pruebas
 * 
 * @author NOVUTECK1
 * 
 */
public class JaxbUtilT {

	public static final <T> T unmarshaller(String fileXML, Class<T> clase)
			throws Exception {
		
		final JAXBContext jaxbCtx = JAXBContext.newInstance(clase);
		final Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();

		File xml = new File(fileXML);
		
		T datos = (T) unmarshaller.unmarshal(xml);
		
		return datos;
	}

	public static final <T> String marshaller(T dato) throws Exception {
		
		final JAXBContext jaxbCtx = JAXBContext.newInstance(dato.getClass());
		final Marshaller marshaller = jaxbCtx.createMarshaller();
		
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		final Writer writer = new StringWriter();

		marshaller.marshal(dato, writer);
		
		return writer.toString();
	}

    public static final <T> T obtainObjectFromXml(Class<T> t, String xml) throws JAXBException, UnsupportedEncodingException {
        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{t});
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));

        return (T)unmarshaller.unmarshal(stream);
    }

}
