
package mx.gob.imss.cit.clienteswebservices.boveda.legada;

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
 *         &lt;element name="AltaDocumentoResult" type="{http://www.openuri.org/legado/}SalidaAlta" minOccurs="0"/>
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
    "altaDocumentoResult"
})
@XmlRootElement(name = "AltaDocumentoResponse")
public class AltaDocumentoResponse {

    @XmlElement(name = "AltaDocumentoResult")
    protected SalidaAlta altaDocumentoResult;

    /**
     * Gets the value of the altaDocumentoResult property.
     * 
     * @return
     *     possible object is
     *     {@link SalidaAlta }
     *     
     */
    public SalidaAlta getAltaDocumentoResult() {
        return altaDocumentoResult;
    }

    /**
     * Sets the value of the altaDocumentoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalidaAlta }
     *     
     */
    public void setAltaDocumentoResult(SalidaAlta value) {
        this.altaDocumentoResult = value;
    }

}
