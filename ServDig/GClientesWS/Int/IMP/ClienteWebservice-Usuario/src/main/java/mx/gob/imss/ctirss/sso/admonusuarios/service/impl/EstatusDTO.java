
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for estatusDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="estatusDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveSsoestatus" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="desEstatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estatusDTO", propOrder = {
    "cveSsoestatus",
    "desEstatus"
})
public class EstatusDTO {

    protected long cveSsoestatus;
    protected String desEstatus;

    /**
     * Gets the value of the cveSsoestatus property.
     * 
     */
    public long getCveSsoestatus() {
        return cveSsoestatus;
    }

    /**
     * Sets the value of the cveSsoestatus property.
     * 
     */
    public void setCveSsoestatus(long value) {
        this.cveSsoestatus = value;
    }

    /**
     * Gets the value of the desEstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesEstatus() {
        return desEstatus;
    }

    /**
     * Sets the value of the desEstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesEstatus(String value) {
        this.desEstatus = value;
    }

}
