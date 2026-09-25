
package mx.gob.imss.ctirss.sso.admonusuarios.renapo.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="consultaCURPResult" type="{http://www.openuri.org/}CurpKioscosBean" minOccurs="0"/>
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
    "consultaCURPResult"
})
@XmlRootElement(name = "consultaCURPResponse")
public class ConsultaCURPResponse {

    protected CurpKioscosBean consultaCURPResult;

    /**
     * Gets the value of the consultaCURPResult property.
     * 
     * @return
     *     possible object is
     *     {@link CurpKioscosBean }
     *     
     */
    public CurpKioscosBean getConsultaCURPResult() {
        return consultaCURPResult;
    }

    /**
     * Sets the value of the consultaCURPResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurpKioscosBean }
     *     
     */
    public void setConsultaCURPResult(CurpKioscosBean value) {
        this.consultaCURPResult = value;
    }

}
