
package mx.com.metatrust.idtrust.wsidtrust;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidaFirmaCMSHashRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidaFirmaCMSHashRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="certificado" type="{http://idtrust.metatrust.com.mx/WsIDTrust.xsd}CertVal" minOccurs="0"/>
 *         &lt;element name="hash" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="algDig" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contenedor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoCertReq" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidaFirmaCMSHashRequestType", propOrder = {
    "certificado",
    "hash",
    "algDig",
    "contenedor",
    "tipoCertReq"
})
public class ValidaFirmaCMSHashRequestType {

    protected CertVal certificado;
    @XmlElement(required = true)
    protected byte[] hash;
    @XmlElement(required = true)
    protected String algDig;
    @XmlElement(required = true)
    protected String contenedor;
    @XmlElement(defaultValue = "1")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger tipoCertReq;

    /**
     * Gets the value of the certificado property.
     * 
     * @return
     *     possible object is
     *     {@link CertVal }
     *     
     */
    public CertVal getCertificado() {
        return certificado;
    }

    /**
     * Sets the value of the certificado property.
     * 
     * @param value
     *     allowed object is
     *     {@link CertVal }
     *     
     */
    public void setCertificado(CertVal value) {
        this.certificado = value;
    }

    /**
     * Gets the value of the hash property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getHash() {
        return hash;
    }

    /**
     * Sets the value of the hash property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setHash(byte[] value) {
        this.hash = ((byte[]) value);
    }

    /**
     * Gets the value of the algDig property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlgDig() {
        return algDig;
    }

    /**
     * Sets the value of the algDig property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlgDig(String value) {
        this.algDig = value;
    }

    /**
     * Gets the value of the contenedor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContenedor() {
        return contenedor;
    }

    /**
     * Sets the value of the contenedor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContenedor(String value) {
        this.contenedor = value;
    }

    /**
     * Gets the value of the tipoCertReq property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTipoCertReq() {
        return tipoCertReq;
    }

    /**
     * Sets the value of the tipoCertReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTipoCertReq(BigInteger value) {
        this.tipoCertReq = value;
    }

}
