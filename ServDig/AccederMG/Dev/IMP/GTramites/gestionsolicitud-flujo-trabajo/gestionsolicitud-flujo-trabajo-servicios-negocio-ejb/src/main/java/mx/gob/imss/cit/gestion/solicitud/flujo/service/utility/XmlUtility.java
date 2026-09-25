package mx.gob.imss.cit.gestion.solicitud.flujo.service.utility;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Stateless(mappedName = "xmlUtility", name = "xmlUtility")
public class XmlUtility implements XmlUtilityLocal {

	/**
	 * Instancia para la lectura de archivos XML
	 */
	private final transient XStream xstream = new XStream(new DomDriver());

	/**
	 * Metodo para convertir a XML sin referencias
	 * 
	 * @param data
	 * @return
	 */
	public String convertToXMLWithNoReferences(final Serializable data) {
		return null == data ? "" : getXStreamForNoReferences().toXML(data);
	}

	/**
	 * 
	 * @return xstream
	 */
	private XStream getXStreamForNoReferences() {
		final XStream xstream = new XStream();

		xstream.autodetectAnnotations(true);
		xstream.setMode(XStream.NO_REFERENCES);

		return xstream;
	}

	/**
	 * Metodo para convertir un XML a Model
	 * 
	 * @param xmlAsString
	 * @return
	 */
	public Object convertToModel(final String xmlAsString) {
		return null == xmlAsString || xmlAsString.trim().isEmpty() ? null : xstream.fromXML(xmlAsString);
	}

}
