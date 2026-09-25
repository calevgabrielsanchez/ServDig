
package mx.gob.imss.distss.derechohabientes.adimss.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nss" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="calidad" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nss",
    "calidad"
})
@XmlRootElement(name = "getFotografiaAsegurado")
public class GetFotografiaAsegurado {

    @XmlElement(required = true)
    protected String nss;
    protected int calidad;

    /**
     * Gets the value of the nss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNss() {
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
    public void setNss(String value) {
        this.nss = value;
    }

    /**
     * Gets the value of the calidad property.
     * 
     */
    public int getCalidad() {
        return calidad;
    }

    /**
     * Sets the value of the calidad property.
     * 
     */
    public void setCalidad(int value) {
        this.calidad = value;
    }

}
