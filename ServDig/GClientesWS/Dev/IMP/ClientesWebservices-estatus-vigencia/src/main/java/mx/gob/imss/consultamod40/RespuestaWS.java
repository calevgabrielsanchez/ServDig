
package mx.gob.imss.consultamod40;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="claveError" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mensajeError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "claveError",
    "mensajeError"
})
@XmlSeeAlso({
    RespuestaModalidad40 .class
})
public class RespuestaWS {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer claveError;
    @XmlElementRef(name = "mensajeError", type = JAXBElement.class)
    protected JAXBElement<String> mensajeError;

    /**
     * Gets the value of the claveError property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClaveError() {
        return claveError;
    }

    /**
     * Sets the value of the claveError property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClaveError(Integer value) {
        this.claveError = value;
    }

    /**
     * Gets the value of the mensajeError property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMensajeError() {
        return mensajeError;
    }

    /**
     * Sets the value of the mensajeError property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMensajeError(JAXBElement<String> value) {
        this.mensajeError = value;
    }

}
