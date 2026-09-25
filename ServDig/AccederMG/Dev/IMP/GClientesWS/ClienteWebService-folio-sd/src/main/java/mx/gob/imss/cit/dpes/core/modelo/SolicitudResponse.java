
package mx.gob.imss.cit.dpes.core.modelo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SolicitudResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolicitudResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SolicitudDTO" type="{java:mx.gob.imss.cit.dpes.core.modelo.dto}SolicitudDTO"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SolicitudResponse", namespace = "java:mx.gob.imss.cit.dpes.core.modelo.osb", propOrder = {
    "solicitudDTO"
})
public class SolicitudResponse {

    @XmlElement(name = "SolicitudDTO", required = true, nillable = true)
    protected SolicitudDTO solicitudDTO;

    /**
     * Gets the value of the solicitudDTO property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudDTO }
     *     
     */
    public SolicitudDTO getSolicitudDTO() {
        return solicitudDTO;
    }

    /**
     * Sets the value of the solicitudDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudDTO }
     *     
     */
    public void setSolicitudDTO(SolicitudDTO value) {
        this.solicitudDTO = value;
    }

}
