
package mx.gob.imss.macii.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CODIGO_ERROR" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MENSAJE_ERROR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaWS", propOrder = {
    "codigoerror",
    "mensajeerror"
})
@XmlSeeAlso({
    RespuestaMACII.class
})
public class RespuestaWS {

    @XmlElementRef(name = "CODIGO_ERROR", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> codigoerror;
    @XmlElementRef(name = "MENSAJE_ERROR", type = JAXBElement.class, required = false)
    protected JAXBElement<String> mensajeerror;

    /**
     * Gets the value of the codigoerror property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCODIGOERROR() {
        return codigoerror;
    }

    /**
     * Sets the value of the codigoerror property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCODIGOERROR(JAXBElement<Integer> value) {
        this.codigoerror = value;
    }

    /**
     * Gets the value of the mensajeerror property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMENSAJEERROR() {
        return mensajeerror;
    }

    /**
     * Sets the value of the mensajeerror property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMENSAJEERROR(JAXBElement<String> value) {
        this.mensajeerror = value;
    }

}
