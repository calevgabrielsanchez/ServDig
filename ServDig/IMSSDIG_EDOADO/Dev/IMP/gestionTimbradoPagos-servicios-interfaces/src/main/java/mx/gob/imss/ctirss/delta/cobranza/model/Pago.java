/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lucio Duran Silva
 *
 */
@XmlRootElement
public class Pago implements Serializable{
	
	
	private String nrp;
	
	private String rfc;
	
	
	private String xmlPago;
	

	public String getXmlPago() {
		return xmlPago;
	}

	public void setXmlPago(String xmlPago) {
		this.xmlPago = xmlPago;
	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	

}
