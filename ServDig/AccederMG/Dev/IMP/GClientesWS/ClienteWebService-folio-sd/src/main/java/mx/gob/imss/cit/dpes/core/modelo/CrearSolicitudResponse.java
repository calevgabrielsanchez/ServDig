
package mx.gob.imss.cit.dpes.core.modelo;

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
 *         &lt;element name="SolicitudResponse" type="{java:mx.gob.imss.cit.dpes.core.modelo.osb}SolicitudResponse"/>
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
    "solicitudResponse"
})
@XmlRootElement(name = "crearSolicitudResponse", namespace = "http://www.openuri.org/")
public class CrearSolicitudResponse {

    @XmlElement(name = "SolicitudResponse", namespace = "http://www.openuri.org/", required = true)
    protected SolicitudResponse solicitudResponse;

    /**
     * Gets the value of the solicitudResponse property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudResponse }
     *     
     */
    public SolicitudResponse getSolicitudResponse() {
        return solicitudResponse;
    }

    /**
     * Sets the value of the solicitudResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudResponse }
     *     
     */
    public void setSolicitudResponse(SolicitudResponse value) {
        this.solicitudResponse = value;
    }

}
