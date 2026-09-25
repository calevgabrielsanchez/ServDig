
package mx.gob.imss.service.pagoscfdi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtienePagoCFDI complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtienePagoCFDI">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://pagoscfdi.service.imss.gob.mx/}messageWSConsPagosCFDIRegPatron" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtienePagoCFDI", propOrder = {
    "request"
})
public class ObtienePagoCFDI {

    protected MessageWSConsPagosCFDIRegPatron request;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link MessageWSConsPagosCFDIRegPatron }
     *     
     */
    public MessageWSConsPagosCFDIRegPatron getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageWSConsPagosCFDIRegPatron }
     *     
     */
    public void setRequest(MessageWSConsPagosCFDIRegPatron value) {
        this.request = value;
    }

}
