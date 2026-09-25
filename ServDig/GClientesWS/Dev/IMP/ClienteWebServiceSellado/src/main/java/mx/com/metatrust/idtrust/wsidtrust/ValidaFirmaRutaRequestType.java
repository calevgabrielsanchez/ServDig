
package mx.com.metatrust.idtrust.wsidtrust;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidaFirmaRutaRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidaFirmaRutaRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="certificado" type="{http://idtrust.metatrust.com.mx/WsIDTrust.xsd}CertVal"/>
 *         &lt;element name="ruta" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "ValidaFirmaRutaRequestType", propOrder = {
    "certificado",
    "ruta",
    "firma",
    "tipoCertReq"
})
public class ValidaFirmaRutaRequestType {

    @XmlElement(required = true)
    protected CertVal certificado;
    @XmlElement(required = true)
    protected String ruta;
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
     * Gets the value of the ruta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * Sets the value of the ruta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuta(String value) {
        this.ruta = value;
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
