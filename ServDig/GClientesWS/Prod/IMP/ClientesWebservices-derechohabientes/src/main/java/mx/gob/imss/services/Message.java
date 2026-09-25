
package mx.gob.imss.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for message complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="message">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveIdAsignacionNSS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveIdEstadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "message", propOrder = {
    "cveIdAsignacionNSS",
    "cveIdEstadoDerechohabiente"
})
public class Message {

    protected Integer cveIdAsignacionNSS;
    protected Integer cveIdEstadoDerechohabiente;

    /**
     * Gets the value of the cveIdAsignacionNSS property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdAsignacionNSS() {
        return cveIdAsignacionNSS;
    }

    /**
     * Sets the value of the cveIdAsignacionNSS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdAsignacionNSS(Integer value) {
        this.cveIdAsignacionNSS = value;
    }

    /**
     * Gets the value of the cveIdEstadoDerechohabiente property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdEstadoDerechohabiente() {
        return cveIdEstadoDerechohabiente;
    }

    /**
     * Sets the value of the cveIdEstadoDerechohabiente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdEstadoDerechohabiente(Integer value) {
        this.cveIdEstadoDerechohabiente = value;
    }

}
