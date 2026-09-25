
package mx.gob.imss.cit.clienteswebservices.vigencia.grupoFamilarNss;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nss" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cpid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInfo", propOrder = {
    "nss",
    "cpid"
})
public class GetInfo {

    protected String nss;
    @XmlElement(name = "Cpid")
    protected String cpid;

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
     * Gets the value of the cpid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpid() {
        return cpid;
    }

    /**
     * Sets the value of the cpid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpid(String value) {
        this.cpid = value;
    }

}
