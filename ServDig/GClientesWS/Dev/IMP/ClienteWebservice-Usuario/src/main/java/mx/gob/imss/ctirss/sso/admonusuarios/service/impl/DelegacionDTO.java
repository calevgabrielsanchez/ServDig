
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for delegacionDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="delegacionDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveDelegacion" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="nombreDelegacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delegacionDTO", propOrder = {
    "cveDelegacion",
    "nombreDelegacion"
})
public class DelegacionDTO {

    protected long cveDelegacion;
    protected String nombreDelegacion;

    /**
     * Gets the value of the cveDelegacion property.
     * 
     */
    public long getCveDelegacion() {
        return cveDelegacion;
    }

    /**
     * Sets the value of the cveDelegacion property.
     * 
     */
    public void setCveDelegacion(long value) {
        this.cveDelegacion = value;
    }

    /**
     * Gets the value of the nombreDelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreDelegacion() {
        return nombreDelegacion;
    }

    /**
     * Sets the value of the nombreDelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreDelegacion(String value) {
        this.nombreDelegacion = value;
    }

}
