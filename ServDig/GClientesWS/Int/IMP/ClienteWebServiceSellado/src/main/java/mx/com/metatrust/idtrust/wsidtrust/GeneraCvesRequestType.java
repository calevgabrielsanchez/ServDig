
package mx.com.metatrust.idtrust.wsidtrust;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GeneraCvesRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeneraCvesRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ftoPubKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tamLlave" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="almInterno" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="nombreDistinguido" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraCvesRequestType", propOrder = {
    "id",
    "ftoPubKey",
    "tamLlave",
    "tipo",
    "almInterno",
    "nombreDistinguido"
})
public class GeneraCvesRequestType {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String ftoPubKey;
    @XmlElement(required = true, defaultValue = "2048")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger tamLlave;
    @XmlElement(required = true)
    protected String tipo;
    protected boolean almInterno;
    @XmlElement(required = true)
    protected String nombreDistinguido;

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
     * Gets the value of the ftoPubKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFtoPubKey() {
        return ftoPubKey;
    }

    /**
     * Sets the value of the ftoPubKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFtoPubKey(String value) {
        this.ftoPubKey = value;
    }

    /**
     * Gets the value of the tamLlave property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTamLlave() {
        return tamLlave;
    }

    /**
     * Sets the value of the tamLlave property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTamLlave(BigInteger value) {
        this.tamLlave = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the almInterno property.
     * 
     */
    public boolean isAlmInterno() {
        return almInterno;
    }

    /**
     * Sets the value of the almInterno property.
     * 
     */
    public void setAlmInterno(boolean value) {
        this.almInterno = value;
    }

    /**
     * Gets the value of the nombreDistinguido property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreDistinguido() {
        return nombreDistinguido;
    }

    /**
     * Sets the value of the nombreDistinguido property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreDistinguido(String value) {
        this.nombreDistinguido = value;
    }

}
