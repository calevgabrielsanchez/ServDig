
package mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.model.InfoPatronSalidaxRFC;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{java:vo}InfoPatronSalidaxRFC"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "_return"
})
@XmlRootElement(name = "getInformacionPatronxRFCResponse")
public class GetInformacionPatronxRFCResponse {

    @XmlElement(name = "return", required = true)
    protected InfoPatronSalidaxRFC _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link InfoPatronSalidaxRFC }
     *     
     */
    public InfoPatronSalidaxRFC getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoPatronSalidaxRFC }
     *     
     */
    public void setReturn(InfoPatronSalidaxRFC value) {
        this._return = value;
    }

}
