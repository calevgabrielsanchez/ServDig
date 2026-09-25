
package mx.com.metatrust.idtrust.wsidtrust;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CertificadoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CertificadoResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultado" type="{http://idtrust.metatrust.com.mx/WsIDTrust.xsd}ResultadoType"/>
 *         &lt;element name="noSerie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certificado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CertificadoResponseType", propOrder = {
    "resultado",
    "noSerie",
    "certificado"
})
public class CertificadoResponseType {

    @XmlElement(required = true)
    protected ResultadoType resultado;
    protected String noSerie;
    protected String certificado;

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
     * Gets the value of the noSerie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoSerie() {
        return noSerie;
    }

    /**
     * Sets the value of the noSerie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoSerie(String value) {
        this.noSerie = value;
    }

    /**
     * Gets the value of the certificado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificado() {
        return certificado;
    }

    /**
     * Sets the value of the certificado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificado(String value) {
        this.certificado = value;
    }

}
