
package gob.imss.webservice.renapo.curp.cliente;

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
 *         &lt;element name="ConsultaDatosCURPResult" type="{http://www.openuri.org/}CurpKioscosBean" minOccurs="0"/>
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
    "consultaDatosCURPResult"
})
@XmlRootElement(name = "ConsultaDatosCURPResponse")
public class ConsultaDatosCURPResponse {

    @XmlElement(name = "ConsultaDatosCURPResult")
    protected CurpKioscosBean consultaDatosCURPResult;

    /**
     * Gets the value of the consultaDatosCURPResult property.
     * 
     * @return
     *     possible object is
     *     {@link CurpKioscosBean }
     *     
     */
    public CurpKioscosBean getConsultaDatosCURPResult() {
        return consultaDatosCURPResult;
    }

    /**
     * Sets the value of the consultaDatosCURPResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurpKioscosBean }
     *     
     */
    public void setConsultaDatosCURPResult(CurpKioscosBean value) {
        this.consultaDatosCURPResult = value;
    }

}
