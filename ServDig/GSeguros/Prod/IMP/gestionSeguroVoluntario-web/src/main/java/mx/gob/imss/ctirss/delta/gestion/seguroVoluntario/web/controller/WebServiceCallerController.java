package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.digital.modelo.interfaces.MensajeError;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class WebServiceCallerController extends AbstractController {
	protected <T> T callWebServiceSimpleParameter(WebServiceTemplate webService, String source, Class<T> resultClass) throws Exception {
		T result = null;
		try {
			StringWriter writer = new StringWriter();
			StreamResult streamResult = new StreamResult(writer);
			log.info("********** CADENA DE ENTRADA DEL WEB-SERVICE: " + source);
			webService.sendSourceAndReceiveToResult(new StreamSource(new StringReader(source)), streamResult);
			log.info("********** CADENA DE SALIDA DEL WEB-SERVICE: " + writer.toString());

            if(resultClass == Map.class){
                String response = writer.toString();
                Map map = new HashMap();

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new ByteArrayInputStream(response.getBytes()));
                NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
                Node item = null;
                for (int i = 0; i < nodeList.getLength(); i++) {
                    item = nodeList.item(i);
                    map.put(item.getNodeName(),item.getTextContent());
                }

                result = (T)map;

            }else{
                result = resultClass.newInstance();
                result = JaxbUtil.unmarshaller(writer.toString(), resultClass);
            }
		} catch (InstantiationException e) {
			throw new Exception("No se ha podido crear una instancia de la clase resultado.", e);
		} catch (IllegalAccessException e) {
			throw new Exception("No se ha podido crear una instancia de la clase resultado.", e);
		}
		return result;
	}
	
	protected <T> T callWebService(WebServiceTemplate webService, Object source, Class<T> resultClass, Class<?>[] classes) throws Exception {
		T result = null;
		try {
			StringWriter writer = new StringWriter();
			StreamResult streamResult = new StreamResult(writer);
			log.info("********** CADENA DE ENTRADA DEL WEB-SERVICE: " + JaxbUtil.marshaller(source, classes));
			webService.sendSourceAndReceiveToResult(new StreamSource(new StringReader(JaxbUtil.marshaller(source, classes))), streamResult);
			log.info("********** CADENA DE SALIDA DEL WEB-SERVICE: " + writer.toString());
			result = resultClass.newInstance();
			result = JaxbUtil.unmarshaller(writer.toString(), resultClass);
		} catch (InstantiationException e) {
			throw new Exception("No se ha podido crear una instancia de la clase resultado.", e);
		} catch (IllegalAccessException e) {
			throw new Exception("No se ha podido crear una instancia de la clase resultado.", e);
		}
		return result;
	}
	
	protected <T> T callWebService(WebServiceTemplate webService, Object source, Class<T> resultClass) throws Exception {
		return callWebService(webService, source, resultClass, new Class<?>[] {source.getClass()});
	}

	protected boolean hasError(MensajeError objeto) {
		return objeto.getErrorFormGeneral() != null && !objeto.getErrorFormGeneral().trim().isEmpty();
	}
}
