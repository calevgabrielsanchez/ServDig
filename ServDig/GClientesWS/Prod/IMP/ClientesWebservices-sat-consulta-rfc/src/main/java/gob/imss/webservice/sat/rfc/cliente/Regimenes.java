
package gob.imss.webservice.sat.rfc.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Regimenes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Regimenes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="c_Regimen" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="d_Regimen" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Alta_Reg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Baja_Reg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Efec_A_Reg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Efec_B_Reg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Regimenes", propOrder = {
    "cRegimen",
    "dRegimen",
    "fAltaReg",
    "fBajaReg",
    "fEfecAReg",
    "fEfecBReg"
})
public class Regimenes {

    @XmlElement(name = "c_Regimen", required = true, nillable = true)
    protected String cRegimen;
    @XmlElement(name = "d_Regimen", required = true, nillable = true)
    protected String dRegimen;
    @XmlElement(name = "f_Alta_Reg", required = true, nillable = true)
    protected String fAltaReg;
    @XmlElement(name = "f_Baja_Reg", required = true, nillable = true)
    protected String fBajaReg;
    @XmlElement(name = "f_Efec_A_Reg", required = true, nillable = true)
    protected String fEfecAReg;
    @XmlElement(name = "f_Efec_B_Reg", required = true, nillable = true)
    protected String fEfecBReg;

    /**
     * Gets the value of the cRegimen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCRegimen() {
        return cRegimen;
    }

    /**
     * Sets the value of the cRegimen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCRegimen(String value) {
        this.cRegimen = value;
    }

    /**
     * Gets the value of the dRegimen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDRegimen() {
        return dRegimen;
    }

    /**
     * Sets the value of the dRegimen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDRegimen(String value) {
        this.dRegimen = value;
    }

    /**
     * Gets the value of the fAltaReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAltaReg() {
        return fAltaReg;
    }

    /**
     * Sets the value of the fAltaReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAltaReg(String value) {
        this.fAltaReg = value;
    }

    /**
     * Gets the value of the fBajaReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFBajaReg() {
        return fBajaReg;
    }

    /**
     * Sets the value of the fBajaReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFBajaReg(String value) {
        this.fBajaReg = value;
    }

    /**
     * Gets the value of the fEfecAReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFEfecAReg() {
        return fEfecAReg;
    }

    /**
     * Sets the value of the fEfecAReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFEfecAReg(String value) {
        this.fEfecAReg = value;
    }

    /**
     * Gets the value of the fEfecBReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFEfecBReg() {
        return fEfecBReg;
    }

    /**
     * Sets the value of the fEfecBReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFEfecBReg(String value) {
        this.fEfecBReg = value;
    }

}
