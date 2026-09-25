
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for areaNormativaDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="areaNormativaDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveSsoareanorma" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="desAreanorma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "areaNormativaDTO", propOrder = {
    "cveSsoareanorma",
    "desAreanorma"
})
public class AreaNormativaDTO {

    protected long cveSsoareanorma;
    protected String desAreanorma;

    /**
     * Gets the value of the cveSsoareanorma property.
     * 
     */
    public long getCveSsoareanorma() {
        return cveSsoareanorma;
    }

    /**
     * Sets the value of the cveSsoareanorma property.
     * 
     */
    public void setCveSsoareanorma(long value) {
        this.cveSsoareanorma = value;
    }

    /**
     * Gets the value of the desAreanorma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesAreanorma() {
        return desAreanorma;
    }

    /**
     * Sets the value of the desAreanorma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesAreanorma(String value) {
        this.desAreanorma = value;
    }

}
