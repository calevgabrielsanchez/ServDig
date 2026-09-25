
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for subdelegacionDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="subdelegacionDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveSubelegacion" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="nombreSubelegacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subdelegacionDTO", propOrder = {
    "cveSubelegacion",
    "nombreSubelegacion"
})
public class SubdelegacionDTO {

    protected long cveSubelegacion;
    protected String nombreSubelegacion;

    /**
     * Gets the value of the cveSubelegacion property.
     * 
     */
    public long getCveSubelegacion() {
        return cveSubelegacion;
    }

    /**
     * Sets the value of the cveSubelegacion property.
     * 
     */
    public void setCveSubelegacion(long value) {
        this.cveSubelegacion = value;
    }

    /**
     * Gets the value of the nombreSubelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreSubelegacion() {
        return nombreSubelegacion;
    }

    /**
     * Sets the value of the nombreSubelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreSubelegacion(String value) {
        this.nombreSubelegacion = value;
    }

}
