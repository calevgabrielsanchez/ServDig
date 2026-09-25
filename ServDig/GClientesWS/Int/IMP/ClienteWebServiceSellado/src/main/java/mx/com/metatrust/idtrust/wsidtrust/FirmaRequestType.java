
package mx.com.metatrust.idtrust.wsidtrust;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FirmaRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FirmaRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rfc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroSerie" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="algHash" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cadenaOriginal" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FirmaRequestType", propOrder = {
    "id",
    "rfc",
    "numeroSerie",
    "algHash",
    "cadenaOriginal"
})
public class FirmaRequestType {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String rfc;
    @XmlElement(required = true)
    protected String numeroSerie;
    @XmlElement(required = true)
    protected String algHash;
    @XmlElement(required = true)
    protected byte[] cadenaOriginal;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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

    /**
     * Gets the value of the numeroSerie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroSerie() {
        return numeroSerie;
    }

    /**
     * Sets the value of the numeroSerie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroSerie(String value) {
        this.numeroSerie = value;
    }

    /**
     * Gets the value of the algHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlgHash() {
        return algHash;
    }

    /**
     * Sets the value of the algHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlgHash(String value) {
        this.algHash = value;
    }

    /**
     * Gets the value of the cadenaOriginal property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     * Sets the value of the cadenaOriginal property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setCadenaOriginal(byte[] value) {
        this.cadenaOriginal = ((byte[]) value);
    }

}
