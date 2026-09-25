
package mx.com.metatrust.doctrust.wsfirmaelectronicaseg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidaCMSV2ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidaCMSV2ResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultado" type="{http://doctrust.metatrust.com.mx/WsFirmaElectronicaSeg.xsd}ResultadoType"/>
 *         &lt;element name="jsonSalida" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidaCMSV2ResponseType", propOrder = {
    "resultado",
    "jsonSalida"
})
public class ValidaCMSV2ResponseType {

    @XmlElement(required = true)
    protected ResultadoType resultado;
    @XmlElement(required = true)
    protected String jsonSalida;

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
     * Gets the value of the jsonSalida property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJsonSalida() {
        return jsonSalida;
    }

    /**
     * Sets the value of the jsonSalida property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJsonSalida(String value) {
        this.jsonSalida = value;
    }

}
