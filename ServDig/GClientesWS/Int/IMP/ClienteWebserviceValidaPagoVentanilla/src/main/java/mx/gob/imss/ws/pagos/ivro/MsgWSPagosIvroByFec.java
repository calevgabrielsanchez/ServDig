
package mx.gob.imss.ws.pagos.ivro;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for msgWSPagosIvroByFec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="msgWSPagosIvroByFec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NSS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FECHA_INICIO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FECHA_FIN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "msgWSPagosIvroByFec", propOrder = {
    "nss",
    "fechainicio",
    "fechafin"
})
public class MsgWSPagosIvroByFec {

    @XmlElement(name = "NSS", required = true)
    protected String nss;
    @XmlElement(name = "FECHA_INICIO", required = true)
    protected String fechainicio;
    @XmlElement(name = "FECHA_FIN", required = true)
    protected String fechafin;

    /**
     * Gets the value of the nss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNSS() {
        return nss;
    }

    /**
     * Sets the value of the nss property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNSS(String value) {
        this.nss = value;
    }

    /**
     * Gets the value of the fechainicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAINICIO() {
        return fechainicio;
    }

    /**
     * Sets the value of the fechainicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAINICIO(String value) {
        this.fechainicio = value;
    }

    /**
     * Gets the value of the fechafin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAFIN() {
        return fechafin;
    }

    /**
     * Sets the value of the fechafin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAFIN(String value) {
        this.fechafin = value;
    }

}
