
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
 *         &lt;element name="BajaDocumentoResult" type="{http://www.openuri.org/}SalidaBaja" minOccurs="0"/>
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
    "bajaDocumentoResult"
})
@XmlRootElement(name = "BajaDocumentoResponse")
public class BajaDocumentoResponse {

    @XmlElement(name = "BajaDocumentoResult")
    protected SalidaBaja bajaDocumentoResult;

    /**
     * Gets the value of the bajaDocumentoResult property.
     * 
     * @return
     *     possible object is
     *     {@link SalidaBaja }
     *     
     */
    public SalidaBaja getBajaDocumentoResult() {
        return bajaDocumentoResult;
    }

    /**
     * Sets the value of the bajaDocumentoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalidaBaja }
     *     
     */
    public void setBajaDocumentoResult(SalidaBaja value) {
        this.bajaDocumentoResult = value;
    }

}
