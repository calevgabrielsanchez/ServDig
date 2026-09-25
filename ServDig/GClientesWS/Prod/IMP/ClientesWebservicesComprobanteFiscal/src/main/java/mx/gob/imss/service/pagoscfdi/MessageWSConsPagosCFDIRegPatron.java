
package mx.gob.imss.service.pagoscfdi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageWSConsPagosCFDIRegPatron complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="messageWSConsPagosCFDIRegPatron">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="registroPatronal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="periodo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageWSConsPagosCFDIRegPatron", propOrder = {
    "registroPatronal",
    "periodo"
})
public class MessageWSConsPagosCFDIRegPatron {

    @XmlElement(required = true)
    protected String registroPatronal;
    protected int periodo;

    /**
     * Gets the value of the registroPatronal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistroPatronal() {
        return registroPatronal;
    }

    /**
     * Sets the value of the registroPatronal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistroPatronal(String value) {
        this.registroPatronal = value;
    }

    /**
     * Gets the value of the periodo property.
     * 
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * Sets the value of the periodo property.
     * 
     */
    public void setPeriodo(int value) {
        this.periodo = value;
    }

}
