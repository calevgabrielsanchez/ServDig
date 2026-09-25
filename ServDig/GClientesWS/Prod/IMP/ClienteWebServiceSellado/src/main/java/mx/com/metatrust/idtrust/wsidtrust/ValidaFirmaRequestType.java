
package mx.com.metatrust.idtrust.wsidtrust;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidaFirmaRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidaFirmaRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="certificado" type="{http://idtrust.metatrust.com.mx/WsIDTrust.xsd}CertVal"/>
 *         &lt;element name="cadenaOriginal" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="firma" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
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
@XmlType(name = "ValidaFirmaRequestType", propOrder = {
    "certificado",
    "cadenaOriginal",
    "firma",
    "tipoCertReq"
})
public class ValidaFirmaRequestType {

    @XmlElement(required = true)
    protected CertVal certificado;
    @XmlElement(required = true)
    protected byte[] cadenaOriginal;
    @XmlElement(required = true)
    protected byte[] firma;
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

    /**
     * Gets the value of the firma property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFirma() {
        return firma;
    }

    /**
     * Sets the value of the firma property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFirma(byte[] value) {
        this.firma = ((byte[]) value);
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
