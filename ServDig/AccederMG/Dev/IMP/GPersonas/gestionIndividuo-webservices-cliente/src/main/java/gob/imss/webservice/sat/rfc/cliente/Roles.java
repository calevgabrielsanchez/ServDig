
package gob.imss.webservice.sat.rfc.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Roles complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Roles">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="c_Rol" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="d_Rol" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="d_Tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Alta_Rol" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Baja_Rol" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Roles", propOrder = {
    "cRol",
    "dRol",
    "dTipo",
    "fAltaRol",
    "fBajaRol"
})
public class Roles {

    @XmlElement(name = "c_Rol", required = true, nillable = true)
    protected String cRol;
    @XmlElement(name = "d_Rol", required = true, nillable = true)
    protected String dRol;
    @XmlElement(name = "d_Tipo", required = true, nillable = true)
    protected String dTipo;
    @XmlElement(name = "f_Alta_Rol", required = true, nillable = true)
    protected String fAltaRol;
    @XmlElement(name = "f_Baja_Rol", required = true, nillable = true)
    protected String fBajaRol;

    /**
     * Gets the value of the cRol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRol() {
        return cRol;
    }

    /**
     * Sets the value of the cRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRol(String value) {
        this.cRol = value;
    }

    /**
     * Gets the value of the dRol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDRol() {
        return dRol;
    }

    /**
     * Sets the value of the dRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDRol(String value) {
        this.dRol = value;
    }

    /**
     * Gets the value of the dTipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDTipo() {
        return dTipo;
    }

    /**
     * Sets the value of the dTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDTipo(String value) {
        this.dTipo = value;
    }

    /**
     * Gets the value of the fAltaRol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAltaRol() {
        return fAltaRol;
    }

    /**
     * Sets the value of the fAltaRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAltaRol(String value) {
        this.fAltaRol = value;
    }

    /**
     * Gets the value of the fBajaRol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFBajaRol() {
        return fBajaRol;
    }

    /**
     * Sets the value of the fBajaRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFBajaRol(String value) {
        this.fBajaRol = value;
    }

}
