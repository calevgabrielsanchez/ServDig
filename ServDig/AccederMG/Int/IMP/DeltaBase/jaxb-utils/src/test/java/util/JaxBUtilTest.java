package util;

import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.distss.digital.jaxb.util.JaxbContextHelper;

import org.junit.Test;

public class JaxBUtilTest {

    @Test
    public void test() {
        
    	
    	
    	TramiteAsegurado tramite = new TramiteAsegurado();
    	tramite.setObservacion("prueba de parseo de XML con Jaxb...");
    	
    	
    	String xml = this.objectToXml(tramite);
    	System.out.println("XML ::: [" + xml + "]");
    	
    }

    
    
    private String objectToXml(final Object object) {
		String xml = null; // NOPMD TODO checar vs null object
		try {
			final JAXBContext jaxbCtx = JaxbContextHelper.getInstance();
			final Marshaller marshaller = jaxbCtx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			final Writer writer = new StringWriter();

			marshaller.marshal(object, writer);
			xml = writer.toString();
			// LOG.trace(writer.toString());
		} catch (JAXBException e) {
			
			
			System.err.println("Error marshalling objec[" + e.getMessage() +"]");
			
			
		}
		return xml;
	}
    
}
