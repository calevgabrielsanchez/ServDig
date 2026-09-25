
package mx.gob.imss.webservices.patrones;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PatronActivoVo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PatronActivoVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveIdPatronGeneral" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="cveIdModalidad" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatronActivoVo", propOrder = {
    "cveIdPatronGeneral",
    "cveIdModalidad"
})
public class PatronActivoVo {

    @XmlElement(required = true, nillable = true)
    protected BigInteger cveIdPatronGeneral;
    @XmlElement(required = true, nillable = true)
    protected BigInteger cveIdModalidad;

    /**
     * Gets the value of the cveIdPatronGeneral property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCveIdPatronGeneral() {
        return cveIdPatronGeneral;
    }

    /**
     * Sets the value of the cveIdPatronGeneral property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCveIdPatronGeneral(BigInteger value) {
        this.cveIdPatronGeneral = value;
    }

    /**
     * Gets the value of the cveIdModalidad property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCveIdModalidad() {
        return cveIdModalidad;
    }

    /**
     * Sets the value of the cveIdModalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCveIdModalidad(BigInteger value) {
        this.cveIdModalidad = value;
    }

}
