
package mx.com.metatrust.idtrust.wsidtrust;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GeneraCvesResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeneraCvesResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultado" type="{http://idtrust.metatrust.com.mx/WsIDTrust.xsd}ResultadoType"/>
 *         &lt;element name="pubKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="privKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneraCvesResponseType", propOrder = {
    "resultado",
    "pubKey",
    "privKey"
})
public class GeneraCvesResponseType {

    @XmlElement(required = true)
    protected ResultadoType resultado;
    @XmlElement(required = true)
    protected String pubKey;
    protected String privKey;

    /**
     * Gets the value of the resultado property.
     * 
     * @return
     *     possible object is
     *     {@link ResultadoType }
     *     
     */
    public ResultadoType getResultado() {
        return resultado;
    }

    /**
     * Sets the value of the resultado property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultadoType }
     *     
     */
    public void setResultado(ResultadoType value) {
        this.resultado = value;
    }

    /**
     * Gets the value of the pubKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPubKey() {
        return pubKey;
    }

    /**
     * Sets the value of the pubKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPubKey(String value) {
        this.pubKey = value;
    }

    /**
     * Gets the value of the privKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrivKey() {
        return privKey;
    }

    /**
     * Sets the value of the privKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrivKey(String value) {
        this.privKey = value;
    }

}
