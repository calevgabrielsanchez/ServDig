
package mx.gob.imss.cit.ClienteWebServiceBoveda;

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
 *         &lt;element name="ConsultaDocumentoResult" type="{http://www.openuri.org/}SalidaConsulta" minOccurs="0"/>
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
    "consultaDocumentoResult"
})
@XmlRootElement(name = "ConsultaDocumentoResponse")
public class ConsultaDocumentoResponse {

    @XmlElement(name = "ConsultaDocumentoResult")
    protected SalidaConsulta consultaDocumentoResult;

    /**
     * Gets the value of the consultaDocumentoResult property.
     * 
     * @return
     *     possible object is
     *     {@link SalidaConsulta }
     *     
     */
    public SalidaConsulta getConsultaDocumentoResult() {
        return consultaDocumentoResult;
    }

    /**
     * Sets the value of the consultaDocumentoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalidaConsulta }
     *     
     */
    public void setConsultaDocumentoResult(SalidaConsulta value) {
        this.consultaDocumentoResult = value;
    }

}
