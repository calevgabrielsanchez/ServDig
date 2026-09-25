/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionXMLPagoException;
import mx.gob.imss.ctirss.delta.cobranza.model.Pago;


/**
 * @author Lucio Duran Silva
 *
 */
public class GeneracionPagoServiceUtility {
	
	
	public static final Class[] MARSHALLING_CLASSES;
	private static final JaxbUtil JAXB_UTIL;
	
	static {
		MARSHALLING_CLASSES = new Class[] { Pago.class};
		JAXB_UTIL = new JaxbUtil(MARSHALLING_CLASSES);
	}
	
	public String getXMLPago(Pago pago )throws ErrorEnGeneracionXMLPagoException{
		String xmlPago = null;
		xmlPago = JAXB_UTIL.objectToXml(pago);
		return xmlPago;
	}
}
