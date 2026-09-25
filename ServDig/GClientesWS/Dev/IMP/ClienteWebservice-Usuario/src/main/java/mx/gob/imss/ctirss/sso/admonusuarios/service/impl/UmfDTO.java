
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for umfDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="umfDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveUmf" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nombreUmf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "umfDTO", propOrder = {
    "cveUmf",
    "nombreUmf"
})
public class UmfDTO {

    protected Long cveUmf;
    protected String nombreUmf;

    /**
     * Gets the value of the cveUmf property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCveUmf() {
        return cveUmf;
    }

    /**
     * Sets the value of the cveUmf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCveUmf(Long value) {
        this.cveUmf = value;
    }

    /**
     * Gets the value of the nombreUmf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreUmf() {
        return nombreUmf;
    }

    /**
     * Sets the value of the nombreUmf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreUmf(String value) {
        this.nombreUmf = value;
    }

}
