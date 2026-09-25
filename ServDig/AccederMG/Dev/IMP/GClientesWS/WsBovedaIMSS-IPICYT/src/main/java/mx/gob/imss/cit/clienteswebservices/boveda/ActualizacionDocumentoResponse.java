
package mx.gob.imss.cit.clienteswebservices.boveda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="ActualizacionDocumentoResult" type="{http://www.openuri.org/}SalidaActualizacion" minOccurs="0"/>
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
    "actualizacionDocumentoResult"
})
@XmlRootElement(name = "ActualizacionDocumentoResponse")
public class ActualizacionDocumentoResponse {

    @XmlElement(name = "ActualizacionDocumentoResult")
    protected SalidaActualizacion actualizacionDocumentoResult;

    /**
     * Gets the value of the actualizacionDocumentoResult property.
     * 
     * @return
     *     possible object is
     *     {@link SalidaActualizacion }
     *     
     */
    public SalidaActualizacion getActualizacionDocumentoResult() {
        return actualizacionDocumentoResult;
    }

    /**
     * Sets the value of the actualizacionDocumentoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalidaActualizacion }
     *     
     */
    public void setActualizacionDocumentoResult(SalidaActualizacion value) {
        this.actualizacionDocumentoResult = value;
    }

}
