
package mx.gob.imss.cit.clienteServiciosComunes.mediosContacto.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MedContactoRepreLegalDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MedContactoRepreLegalDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoContacto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DesFormaContacto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Rfc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MedContactoRepreLegalDTO", propOrder = {
    "tipoContacto",
    "desFormaContacto",
    "rfc"
})
public class MedContactoRepreLegalDTO {

    @XmlElement(name = "TipoContacto", required = true, nillable = true)
    protected String tipoContacto;
    @XmlElement(name = "DesFormaContacto", required = true, nillable = true)
    protected String desFormaContacto;
    @XmlElement(name = "Rfc", required = true, nillable = true)
    protected String rfc;

    /**
     * Gets the value of the tipoContacto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoContacto() {
        return tipoContacto;
    }

    /**
     * Sets the value of the tipoContacto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoContacto(String value) {
        this.tipoContacto = value;
    }

    /**
     * Gets the value of the desFormaContacto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesFormaContacto() {
        return desFormaContacto;
    }

    /**
     * Sets the value of the desFormaContacto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesFormaContacto(String value) {
        this.desFormaContacto = value;
    }

    /**
     * Gets the value of the rfc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * Sets the value of the rfc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRfc(String value) {
        this.rfc = value;
    }

}
