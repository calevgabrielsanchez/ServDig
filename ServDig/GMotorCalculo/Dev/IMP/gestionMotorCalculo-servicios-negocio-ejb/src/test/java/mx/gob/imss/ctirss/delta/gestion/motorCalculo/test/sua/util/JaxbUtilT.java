/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.util;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * clase utilitaria para serializacion y desserealizacion de objetos en las pruebas
 * @author NOVUTECK1
 *
 */
public class JaxbUtilT {
    
    public static final <T> T unmarshaller (String fileXML, Class<T> clase) throws Exception {
        final JAXBContext jaxbCtx = JAXBContext.newInstance(clase);
        final Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();

        File xml = new File(fileXML); 
        T datos = (T)unmarshaller.unmarshal(xml);
        return datos;
    }
    
    public static final <T> String marshaller (T dato) throws Exception {
        final JAXBContext jaxbCtx = JAXBContext.newInstance(dato.getClass());
        final Marshaller marshaller = jaxbCtx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        final Writer writer = new StringWriter();

        marshaller.marshal(dato, writer);
        return writer.toString();
    }

}
